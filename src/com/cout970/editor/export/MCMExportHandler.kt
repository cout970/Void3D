package com.cout970.editor.export

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.AbstractTreeNode
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import com.cout970.editor.modeltree.ModelTree
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import com.google.gson.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Created by cout970 on 14/06/2016.
 */
object MCMExportHandler : IExportHandler {

    override fun export(output: File, model: ModelTree, backup: Boolean): Boolean {
        try {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val json = JsonObject()

            json.addProperty("Version", "1.0.0")
            json.add("Model", getModelJson(model.root))

            val text = gson.toJson(json)
            val zip = ZipOutputStream(FileOutputStream(output))

            zip.putNextEntry(ZipEntry("model.json"))
            val writer = OutputStreamWriter(zip)
            writer.write(text)
            writer.flush()
            zip.closeEntry()
            if (!backup) {
                zip.putNextEntry(ZipEntry("texture.png"))
                val loc = TextureLoader.modelTexture!!.textures[0].location
                val input = Editor.resourceManager.getResource(loc).inputStream

                val buffer = ByteArray(1024)
                var length = input.read(buffer)
                while (length > 0) {
                    zip.write(buffer, 0, length)
                    length = input.read(buffer)
                }

                zip.closeEntry()
            }

            zip.close()
            return true
        } catch(e: Exception) {
            Log.printStackTrace(e)
            return false
        }
    }


    private fun getModelJson(tree: AbstractTreeNode): JsonElement? {
        val root = JsonObject()
        if (tree is Cube) {
            root.addProperty("Type", "Cube")
            root.addProperty("Name", tree.getName())
            root.addProperty("FlipUV", tree.flipUV)
            root.addProperty("Visible", tree.visible)
            root.add("Size", toArray(tree.size))
            root.add("Position", toArray(tree.position))
            root.add("RotationPoint", toArray(tree.rotationPoint))
            root.add("Rotation", toArray(tree.rotation))
            root.add("TextureOffset", toArray(tree.textureOffset))

        } else if (tree is Group) {
            root.addProperty("Type", "Group")
            root.addProperty("Name", tree.getName())
            root.add("Size", toArray(tree.size))
            root.add("Position", toArray(tree.position))
            root.add("RotationPoint", toArray(tree.rotationPoint))
            root.add("Rotation", toArray(tree.rotation))
            val array = JsonArray()
            for (i in tree.subParts) {
                array.add(getModelJson(i))
            }
            root.add("Components", array)
        }
        return root
    }

    private fun toArray(size: Vector3): JsonArray {
        val array = JsonArray()
        array.add(JsonPrimitive(size.x))
        array.add(JsonPrimitive(size.y))
        array.add(JsonPrimitive(size.z))
        return array
    }

    private fun toArray(size: Vector2): JsonArray {
        val array = JsonArray()
        array.add(JsonPrimitive(size.x))
        array.add(JsonPrimitive(size.y))
        return array
    }
}