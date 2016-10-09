package com.cout970.editor.util

import java.util.*

/**
 * Created by cout970 on 09/06/2016.
 */
class IteratorToEnumeration<T>(val it: Iterator<T>) : Enumeration<T> {

    override fun nextElement(): T? = it.next()

    override fun hasMoreElements(): Boolean = it.hasNext()
}