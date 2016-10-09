package com.cout970.editor.export

import com.cout970.editor.Editor
import com.cout970.editor.config.Config
import com.cout970.editor.util.Log
import java.io.File
import java.nio.file.Files
import java.util.*

/**
 * Created by cout970 on 22/06/2016.
 */

var timer = 0.0

fun backupTick(){
    timer += Editor.gameLoop.timer.delta
    if(timer > Config.backupInterval){
        timer = 0.0
        if(Editor.historyTable.changes){
            Editor.historyTable.changes = false
            makeBackup()
        }
    }
}

fun makeBackup() {
    val output = File(File("./backups").apply { mkdirs() }, "backup-last.mcm")

    MCMExportHandler.export(output, Editor.modelTree, true)

    val time = GregorianCalendar.getInstance()
    val outputName = "./backups/backup_${time.get(Calendar.YEAR)}-${time.get(Calendar.MONTH)}-${time.get(Calendar.DAY_OF_MONTH)}_${time.get(Calendar.HOUR_OF_DAY)}-${time.get(Calendar.MINUTE)}-${time.get(Calendar.SECOND)}.mcm"
    Files.copy(output.toPath(), File(outputName).toPath())

    Log.info("Stored Backup at ${File(outputName).absolutePath}")
}
