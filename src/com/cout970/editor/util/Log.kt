package com.cout970.editor.util

import java.io.File
import java.io.PrintWriter
import java.nio.file.Files
import java.util.*

/**
 * Created by cout970 on 12/06/2016.
 */
object Log {

    private val logFile = File(File("./logs").apply { mkdirs() }, "log-current.txt")
    val writer = PrintWriter(logFile)

    fun debug(o: Any?) {
        print(o?.toString() ?: "null")
    }

    fun error(o: String) {
        print(o)
    }

    fun info(o: String) {
        //print(o)
    }

    private fun print(str: String) {

        val time = GregorianCalendar.getInstance()
        writer.print("[${time.get(Calendar.YEAR)}/${time.get(Calendar.MONTH)}/${time.get(Calendar.DAY_OF_MONTH)}" +
                ", ${time.get(Calendar.HOUR_OF_DAY)}:${time.get(Calendar.MINUTE)}:${time.get(Calendar.SECOND)}] ")
        println(str)
        writer.println(str)
    }

    fun storeLog() {
        val time = GregorianCalendar.getInstance()
        val outputName = "log_${time.get(Calendar.YEAR)}-${time.get(Calendar.MONTH)}-${time.get(Calendar.DAY_OF_MONTH)}_${time.get(Calendar.HOUR_OF_DAY)}-${time.get(Calendar.MINUTE)}-${time.get(Calendar.SECOND)}.txt"
        Files.copy(logFile.toPath(), File("./logs/$outputName").toPath())
    }

    fun printStackTrace(e:Exception){
        e.printStackTrace(writer)
        e.printStackTrace()
        writer.flush()
    }
}