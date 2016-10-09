package com.cout970.editor.export

import com.cout970.editor.modeltree.ModelTree
import java.io.File

/**
 * Created by cout970 on 12/06/2016.
 */
interface IImportHandler {

    fun import(input: File, model: ModelTree): Boolean
}