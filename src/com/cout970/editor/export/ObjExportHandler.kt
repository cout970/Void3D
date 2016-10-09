package com.cout970.editor.export

import com.cout970.editor.modeltree.ModelTree
import com.cout970.editor.texture.TextureLoader
import com.cout970.editor.util.Log
import com.cout970.editor.util.transform
import com.cout970.gl.util.vector.Vector2
import com.cout970.gl.util.vector.Vector3
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import java.util.function.Function
import java.util.function.Predicate

/**
 * Created by cout970 on 12/06/2016.
 */
object ObjExportHandler : IExportHandler {

    private val vertex = LinkedList<Vector3>()
    private val vertexMap = LinkedHashSet<Vector3>()

    private val texCoords = LinkedList<Vector2>()
    private val texCoordsMap = LinkedHashSet<Vector2>()

    private val normals = LinkedList<Vector3>()
    private val normalsMap = LinkedHashSet<Vector3>()

    override fun export(output: File, model: ModelTree, backup: Boolean): Boolean {

        vertex.clear()
        vertexMap.clear()
        texCoords.clear()
        texCoordsMap.clear()
        normals.clear()
        normalsMap.clear()

        try {
            val names = mutableSetOf<String>()
            val objQuads = mutableMapOf<String, LinkedList<ObjQuad>>()
            model.iterate(Function {
                var name = it.getName().replace(" ", "_").toLowerCase()
                while (names.contains(name)) {
                    name += "_"
                }
                names.add(name)
                val quads = transform(it.getQuads(), it.getMatrix())
                for (q in quads) {
                    val quad = ObjQuad()

                    var i = 0
                    for (v in q.getVertex()) {

                        val vert = v.pos.copy().mul(1 / 16.0)
                        if (vertexMap.contains(vert)) {
                            quad.vertexIndices[i] = vertex.indexOf(vert) + 1
                        } else {
                            quad.vertexIndices[i] = vertex.size + 1
                            vertex.add(vert)
                            vertexMap.add(vert)
                        }

                        val textureSize = TextureLoader.textureScale
                        val tex = v.uv.copy().mul(1 / textureSize)
                        if (texCoordsMap.contains(tex)) {
                            quad.textureIndices[i] = texCoords.indexOf(tex) + 1
                        } else {
                            quad.textureIndices[i] = texCoords.size + 1
                            texCoords.add(tex)
                            texCoordsMap.add(tex)
                        }

                        val norm = v.normal.normalize()
                        if (normalsMap.contains(norm)) {
                            quad.normalIndices[i] = normals.indexOf(norm) + 1
                        } else {
                            quad.normalIndices[i] = normals.size + 1
                            normals.add(norm)
                            normalsMap.add(norm)
                        }
                        i++
                    }
                    var list = objQuads[name]
                    if (list == null) {
                        list = LinkedList<ObjQuad>()
                        objQuads.put(name, list)
                    }
                    list.add(quad)
                }
            }, Predicate { true })

            //fin de la conversion del modelo

            val sym = DecimalFormatSymbols()
            sym.decimalSeparator = '.'
            val format = DecimalFormat("####0.000000", sym)

            val writer = FileWriter(output)

            writer.write("mtllib materials.mtl\n")
            writer.write("o ${output.name.replace(".obj", "")}\n")
            for (a in vertex) {
                writer.write(String.format("v %s %s %s\n", format.format(a.x), format.format(a.y), format.format(a.z)))
            }
            writer.append('\n')
            for (a in texCoords) {
                writer.write(String.format("vt %s %s\n", format.format(a.x), format.format(a.y)))
            }
            writer.append('\n')
            for (a in normals) {
                writer.write(String.format("vn %s %s %s\n", format.format(a.x), format.format(a.y), format.format(a.z)))
            }
            writer.append('\n')
            writer.write("usemtl ${output.name.replace(".obj", "")}\n")
            writer.append('\n')
            for ((key, value) in objQuads) {
                writer.append("g $key\n")
                for (q in value) {
                    val a = q.vertexIndices
                    val b = q.textureIndices
                    val c = q.normalIndices
                    writer.write(String.format("f %d/%d/%d %d/%d/%d %d/%d/%d %d/%d/%d\n",
                            a[0], b[0], c[0], a[1], b[1], c[1],
                            a[2], b[2], c[2], a[3], b[3], c[3]))
                }
            }
            writer.flush()

            vertex.clear()
            vertexMap.clear()
            texCoords.clear()
            texCoordsMap.clear()
            normals.clear()
            normalsMap.clear()

            writer.close()
            return true
        } catch (e: IOException) {
            Log.printStackTrace(e)
        }

        return false
    }

    private class ObjQuad {
        var vertexIndices: IntArray
            internal set
        var textureIndices: IntArray
            internal set

        var normalIndices: IntArray
            internal set

        init {
            this.vertexIndices = IntArray(4)
            this.textureIndices = IntArray(4)
            this.normalIndices = IntArray(4)
        }
    }
}