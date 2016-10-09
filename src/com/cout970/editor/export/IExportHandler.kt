package com.cout970.editor.export

import com.cout970.editor.modeltree.ModelTree
import java.io.File

/**
 * Created by cout970 on 12/06/2016.
 */
interface IExportHandler {

    fun export(output: File, model: ModelTree, backup: Boolean): Boolean
}