package com.cout970.editor.export

import com.cout970.editor.modeltree.Cube
import com.cout970.editor.modeltree.Group
import com.cout970.editor.modeltree.ModelTree
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import org.w3c.dom.Document
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.awt.Dimension
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import java.util.zip.ZipException
import java.util.zip.ZipFile
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

/**
 * Created by cout970 on 12/06/2016.
 */
object TechneImportHandler : IImportHandler {

    val cubeTypes = Arrays.asList(
            "d9e621f7-957f-4b77-b1ae-20dcd0da7751",
            "de81aa14-bd60-4228-8d8d-5238bcd3caaa")!!

    override fun import(input: File, model: ModelTree): Boolean {
        if (!input.exists() || !input.name.contains(".tcn")) {
            throw FileNotFoundException("Error with file: " + input)
        }
        Log.info("Importing Techne model from file: ${input.absolutePath}")
        try {
            var found = false
            val zipFile = ZipFile(input)
            for (i in zipFile.entries()) {
                if (i.name == "model.xml") {
                    found = true
                    val documentBuilderFactory = DocumentBuilderFactory.newInstance()
                    val documentBuilder = documentBuilderFactory.newDocumentBuilder()
                    val document = documentBuilder.parse(InputSource(zipFile.getInputStream(i)))
                    val tree = Group()
                    loadModel(tree, document, input)
                    model.loadModel(tree)
                } else {
                    Log.info("Skipping entry in save file, entry name: ${i.name}")
                }
            }
            return found
        } catch (e: ZipException) {
            Log.printStackTrace(e)
            throw ModelImportException("Model $input is not a valid zip file")
        } catch (e: IOException) {
            Log.printStackTrace(e)
            throw ModelImportException("Model $input could not be read")
        } catch (e: ParserConfigurationException) {
            Log.printStackTrace(e)
        } catch (e: SAXException) {
            Log.printStackTrace(e)
            throw ModelImportException("Model $input contains invalid XML")
        }
        return false
    }

    private fun loadModel(tree: Group, document: Document, input: File) {

        val parts = LinkedList<Cube>()

        val nodeListModel = document.getElementsByTagName("Model")
        if (nodeListModel.length < 1) {
            throw ModelImportException("Model $input contains no Model tag")
        }

        val modelAttributes = nodeListModel.item(0).attributes ?: throw ModelImportException("Model $input contains a Model tag with no attributes")

        val textureDim = document.getElementsByTagName("TextureSize")
        var textureDims: Dimension? = null
        if (textureDim.length > 0) {
            try {
                val tmp = textureDim.item(0).textContent.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (tmp.size == 2) {
                    textureDims = Dimension(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]))
                }
            } catch (e: NumberFormatException) {
                throw ModelImportException("Model $input contains a TextureSize tag with invalid data")
            }
        }
        if (textureDims != null && textureDims.height == textureDims.width) {
            TextureLoader.textureScale = textureDims.getWidth()
        }

        val shapes = document.getElementsByTagName("Shape")

        for (i in 0..shapes.length - 1) {
            val shape = shapes.item(i)
            val shapeAttributes = shape.attributes ?: throw ModelImportException("Shape #" + (i + 1) + " in " + input + " has no attributes")

            val name = shapeAttributes.getNamedItem("name")
            var shapeName: String? = null
            if (name != null) {
                shapeName = name.nodeValue
            }
            if (shapeName == null) {
                shapeName = "Shape #" + (i + 1)
            }

            var shapeType: String? = null
            val type = shapeAttributes.getNamedItem("type")
            if (type != null) {
                shapeType = type.nodeValue
            }
            if (shapeType != null && !cubeTypes.contains(shapeType)) {
                Log.error("Model shape [$shapeName] in $input is not a cube, ignoring")
                continue
            }

            try {
                var mirrored = false
                var offset = arrayOfNulls<String>(3)
                var position = arrayOfNulls<String>(3)
                var rotation = arrayOfNulls<String>(3)
                var size = arrayOfNulls<String>(3)
                var textureOffset = arrayOfNulls<String>(2)

                val shapeChildren = shape.childNodes
                for (j in 0..shapeChildren.length - 1) {
                    val shapeChild = shapeChildren.item(j)

                    val shapeChildName = shapeChild.nodeName
                    var shapeChildValue: String? = shapeChild.textContent
                    if (shapeChildValue != null) {
                        shapeChildValue = shapeChildValue.trim { it <= ' ' }

                        when (shapeChildName) {
                            "IsMirrored" -> mirrored = shapeChildValue != "False"
                            "Offset" -> offset = shapeChildValue.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            "Position" -> position = shapeChildValue.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            "Rotation" -> rotation = shapeChildValue.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            "Size" -> size = shapeChildValue.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                            "TextureOffset" -> textureOffset = shapeChildValue.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        }
                    }
                }

                val cubeSize = Vector3(Integer.parseInt(size[0]), Integer.parseInt(size[1]), Integer.parseInt(size[2]))
                val cubeOffset = Vector3(java.lang.Float.parseFloat(offset[0]).toDouble(), (-java.lang.Float.parseFloat(offset[1])).toDouble(), java.lang.Float.parseFloat(offset[2]).toDouble())
                val cubePosition = Vector3(java.lang.Float.parseFloat(position[0]).toDouble(), -cubeSize.y - java.lang.Float.parseFloat(position[1]), java.lang.Float.parseFloat(position[2]).toDouble())
                val cubePosition0 = Vector3(java.lang.Float.parseFloat(position[0]).toDouble(), (-java.lang.Float.parseFloat(position[1])).toDouble(), java.lang.Float.parseFloat(position[2]).toDouble())
                val cubeTextureOffset = Vector2(Integer.parseInt(textureOffset[0]), Integer.parseInt(textureOffset[1]))
                val cubeRotation = Vector3((-java.lang.Float.parseFloat(rotation[0])), java.lang.Float.parseFloat(rotation[1]), (-java.lang.Float.parseFloat(rotation[2])))

                val cube = Cube()

                cube.name_ = shapeName
                cube.size.set(cubeSize)
                cube.position.set(cubePosition.copy().add(cubeOffset).add(8, 24, 8))
                cube.rotationPoint.set(cubePosition0.copy().add(8, 24, 8))
                cube.rotation.set(cubeRotation)
                cube.textureOffset.set(cubeTextureOffset)
                cube.flipUV = mirrored

                parts.add(cube)
            } catch (e: NumberFormatException) {
                Log.error("Model shape [$shapeName] in $input contains malformed integers within its data, ignoring")
                e.printStackTrace()
            }

        }
        for (i in parts) {
            i.parent = tree
            tree.subParts.add(i)
        }
    }
}