package com.cout970.editor.util

import com.cout970.editor.renderer.util.Quad
import org.joml.Matrix4f
import java.util.*

/**
 * Created by cout970 on 15/06/2016.
 */

fun transform(quads: List<Quad>, matrix: Matrix4f): List<Quad> {
    val list = LinkedList<Quad>()
    for (i in quads) {
        list.add(Quad(
                i.a.transform(matrix),
                i.b.transform(matrix),
                i.c.transform(matrix),
                i.d.transform(matrix)
        ))
    }
    return list
}