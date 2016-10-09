package com.cout970.editor.export

import com.cout970.editor.Editor
import com.cout970.editor.modeltree.AbstractTreeNode
import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import com.cout970.editor.modeltree.ModelTree
import com.cout970.editor.util.Log
import com.cout970.gl.resource.internal.ResourceLocation
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.file.Files
import java.util.zip.ZipInputStream

/**
 * Created by cout970 on 14/06/2016.
 */
object MCMImportHandler : IImportHandler {

    val TEMP_TEXTURE_PATH = "./model_texture.png"

    override fun import(input: File, model: ModelTree): Boolean {
        try {
            val zip = ZipInputStream(FileInputStream(input))
            var entry = zip.nextEntry
            var found = false
            while (entry != null) {
                if (entry.name == "model.json") {
                    try {
                        val reader = InputStreamReader(zip)
                        val parser = JsonParser()
                        val json = parser.parse(reader)

                        val tree = getModelFromJson(json.asJsonObject.get("Model"))
                        model.loadModel(tree)
                        Log.info("Loaded model from save: ${input.absolutePath}")
                        found = true
                    } catch(e: Exception) {
                        Log.printStackTrace(e)
                    }
                } else if (entry.name == "texture.png") {
                    try {
                        val output = File(TEMP_TEXTURE_PATH)
                        if(output.exists()){
                            output.renameTo(File(TEMP_TEXTURE_PATH+".old"))
                            output.delete()
                        }
                        Files.copy(zip, output.toPath())
                        Editor.textureManager.modelTextureLocation = ResourceLocation(Editor.resourceManager.EXTERNAL_RESOURCE, TEMP_TEXTURE_PATH)
                        Editor.textureManager.refreshMainTexture()
                        Log.info("Loaded image from save: ${input.absolutePath}, stored in: $TEMP_TEXTURE_PATH")
                    } catch(e: Exception) {
                        Log.printStackTrace(e)
                    }
                }else{
                    Log.info("Skipping entry in save file, entry name: ${entry.name}")
                }
                entry = zip.nextEntry
            }
            zip.close()
            return found
        } catch(e: Exception) {
            Log.printStackTrace(e)
            return false
        }
    }

    private fun getModelFromJson(json: JsonElement): AbstractTreeNode {
        if (json.isJsonObject) {
            val obj = json.asJsonObject
            val type = obj.get("Type")
            if (type.asString == "Cube") {
                val cube = Cube()
                cube.name_ = obj["Name"].asString
                cube.flipUV = obj["FlipUV"].asBoolean
                cube.visible = obj["Visible"].asBoolean
                cube.size.set(toVector3(obj["Size"].asJsonArray))
                cube.position.set(toVector3(obj["Position"].asJsonArray))
                cube.rotationPoint.set(toVector3(obj["RotationPoint"].asJsonArray))
                cube.rotation.set(toVector3(obj["Rotation"].asJsonArray))
                cube.textureOffset.set(toVector2(obj["TextureOffset"].asJsonArray))
                return cube
            } else if (type.asString == "Group") {
                val group = Group()
                group.name_ = obj["Name"].asString
                group.size.set(toVector3(obj["Size"].asJsonArray))
                group.position.set(toVector3(obj["Position"].asJsonArray))
                group.rotation.set(toVector3(obj["Rotation"].asJsonArray))
                group.rotationPoint.set(toVector3(obj["RotationPoint"].asJsonArray))
                val comps = obj["Components"].asJsonArray
                for (i in comps) {
                    val part = getModelFromJson(i)
                    group.subParts.add(part)
                    part.parent = group
                }
                return group
            }
        }
        throw ModelImportException("Invalid value at: " + json)
    }

    private fun toVector3(array: JsonArray): Vector3 {
        return Vector3(array.get(0).asDouble, array.get(1).asDouble, array.get(2).asDouble)
    }

    private fun toVector2(array: JsonArray): Vector2 {
        return Vector2(array.get(0).asDouble, array.get(1).asDouble)
    }
}