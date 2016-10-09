package com.cout970.editor.texture

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.util.Log
import com.cout970.gl.window.Window
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.nio.IntBuffer
import java.util.*
import java.util.function.Function
import java.util.function.Predicate
import javax.imageio.ImageIO

/**
 * Created by cout970 on 22/06/2016.
 */
object TextureExportUtil {

    private var pixelBuffer: IntBuffer = BufferUtils.createIntBuffer(1)
    private var pixelValues: IntArray = IntArray(1)

    fun takeScreenshot(dir: File, window: Window): Boolean {
        try {
            val width = window.size.xi
            val height = window.size.yi
            dir.mkdirs()

            val size = width * height

            if (pixelBuffer.capacity() < size) {
                pixelBuffer = BufferUtils.createIntBuffer(size)
                pixelValues = IntArray(size)
            }

            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1)
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1)
            pixelBuffer.clear()

            GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer)

            pixelBuffer.get(pixelValues)
            swapPixels(pixelValues, width, height)

            val image = BufferedImage(width, height, 1)

            image.setRGB(0, 0, width, height, pixelValues, 0, width)

            val time = GregorianCalendar.getInstance()
            val outputName = "screenshot_${time.get(Calendar.YEAR)}-${time.get(Calendar.MONTH)}-${time.get(Calendar.DAY_OF_MONTH)}_${time.get(Calendar.HOUR_OF_DAY)}-${time.get(Calendar.MINUTE)}-${time.get(Calendar.SECOND)}.png"
            ImageIO.write(image, "png", File(dir, outputName))
            return true
        } catch (exception: Exception) {
            Log.printStackTrace(exception)
        }
        return false
    }

    fun swapPixels(pixels: IntArray, width: Int, height: Int) {
        val array = IntArray(width)
        val i = height / 2

        for (j in 0..i - 1) {
            System.arraycopy(pixels, j * width, array, 0, width)
            System.arraycopy(pixels, (height - 1 - j) * width, pixels, j * width, width)
            System.arraycopy(array, 0, pixels, (height - 1 - j) * width, width)
        }
    }

    fun exportTexture(file: File) {
        try {
            val size = TextureLoader.textureScale.toInt()
            val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB_PRE)

            val g = image.createGraphics()

            g.color = Color(0f, 0f, 0f, 0f)
            g.fillRect(0, 0, size, size)

            val colors = arrayOf(Color(0xFF0000), Color(0x8B0000), Color(0x006400), Color(0x008000), Color(0x00008B), Color(0x0000FF))
            Editor.modelTree.iterate(Function { tree ->

                var color = 0
                val quads = ArrayList(tree.getQuads())
                for (q in quads) {
                    val a = q.a.uv.copy()
                    val b = q.b.uv.copy()
                    val c = q.c.uv.copy()
                    val d = q.d.uv.copy()
                    g.color = colors[color]
                    g.fillPolygon(
                            intArrayOf(
                                    StrictMath.rint(a.x).toInt(),
                                    StrictMath.rint(b.x).toInt(),
                                    StrictMath.rint(c.x).toInt(),
                                    StrictMath.rint(d.x).toInt()),
                            intArrayOf(StrictMath.rint(a.y).toInt(),
                                    StrictMath.rint(b.y).toInt(),
                                    StrictMath.rint(c.y).toInt(),
                                    StrictMath.rint(d.y).toInt()), 4)
                    color++
                }
            }, Predicate { it is Cube })

            ImageIO.write(image, "png", file)
        } catch (e: Exception){
            Log.printStackTrace(e)
        }
    }
}