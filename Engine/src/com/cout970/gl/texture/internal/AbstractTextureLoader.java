package com.cout970.gl.texture.internal;

import com.cout970.gl.exception.TextureLoadException;
import com.cout970.gl.resource.IResource;
import com.cout970.gl.texture.ITexture;
import com.cout970.gl.texture.ITextureLoader;
import com.cout970.gl.util.IClosable;
import com.cout970.gl.util.vector.Vector2;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

/**
 * Created by cout970 on 26/04/2016.
 */
public abstract class AbstractTextureLoader implements ITextureLoader, IClosable {


    public TextureData loadTextureData(IResource res) {

        Vector2 size = new Vector2();
        ByteBuffer image;
        InputStream stream;
        try {
            stream = res.getInputStream();
            image = toBuffer(stream);
            image.flip();
            stream.close();
        } catch (IOException e) {
            throw new TextureLoadException(e.getMessage());
        }

        IntBuffer wBuf = BufferUtils.createIntBuffer(1);
        IntBuffer hBuf = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        if (stbi_info_from_memory(image, wBuf, hBuf, comp) == 0) {
            throw new TextureLoadException("Failed to read image information: image: " + res + ", error: " + stbi_failure_reason());
        }

        size.set(wBuf.get(0), hBuf.get(0));
        wBuf.rewind();
        hBuf.rewind();

        // Decode the image
        image = stbi_load_from_memory(image, wBuf, hBuf, comp, 0);
        if (image == null) {
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
        }

        size.set(wBuf.get(0), hBuf.get(0));

        int texID = glGenTextures();
        int components = comp.get(0);

        return new TextureData(texID, size, image, components);
    }

    @Override
    public ITexture loadTexture(IResource res) {

        TextureData data = loadTextureData(res);

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, data.getId());
        if (data.getComponents() == 3) {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, data.getSize().getXi(), data.getSize().getYi(), 0, GL_RGB, GL_UNSIGNED_BYTE, data.getData());
        } else {
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, data.getSize().getXi(), data.getSize().getYi(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getData());
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        if (useLinear(res)) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        } else {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        return new Texture(res.getLocation(), data.getSize(), data.getId());
    }

    protected abstract boolean useLinear(IResource res);

    @Override
    public ITexture loadTextureCube(IResource[] res) {
        int texID = glGenTextures();
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texID);

        for (int i = 0; i < res.length; i++) {
            TextureData data = loadTextureData(res[i]);
            if (data.getComponents() == 3) {
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGB, data.getSize().getXi(), data.getSize().getYi(), 0, GL_RGB, GL_UNSIGNED_BYTE, data.getData());
            } else {
                glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, data.getSize().getXi(), data.getSize().getYi(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getData());
            }
        }

        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        return new Texture(res[0].getLocation(), new Vector2(0,0), texID);
    }


    private ByteBuffer toBuffer(InputStream stream) throws IOException {
        ByteBuffer buffer = BufferUtils.createByteBuffer(1024);
        ReadableByteChannel rbc = Channels.newChannel(stream);
        int bytes;
        while ((bytes = rbc.read(buffer)) != -1) {
            if (buffer.remaining() == 0) {
                buffer = resizeBuffer(buffer, buffer.capacity() * 2);
            }
        }
        rbc.close();
        stream.close();
        return buffer;
    }

    private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        ByteBuffer newBuffer = createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
}
