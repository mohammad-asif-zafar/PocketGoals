package com.hathway.pocketgoals

import androidx.compose.runtime.Composable
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
actual fun rememberDocumentSharer(): DocumentSharer {
    val context = LocalContext.current
    return remember(context) { AndroidDocumentSharer(context) }
}

private class AndroidDocumentSharer(private val context: Context) : DocumentSharer {
    override fun sharePdf(bytes: ByteArray, fileName: String) {
        val cacheFile = File(context.cacheDir, fileName).apply {
            writeBytes(bytes)
        }
        val uri = FileProvider.getUriForFile(
            context, "${context.packageName}.fileprovider", cacheFile
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share Financial Statement"))
    }
}