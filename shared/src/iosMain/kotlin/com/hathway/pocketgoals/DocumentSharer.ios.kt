package com.hathway.pocketgoals

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.Foundation.writeToURL
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@Composable
actual fun rememberDocumentSharer(): DocumentSharer {
    return remember { IosDocumentSharer() }
}

private class IosDocumentSharer : DocumentSharer {
    @OptIn(ExperimentalForeignApi::class)
    override fun sharePdf(bytes: ByteArray, fileName: String) {
        val nsData = bytes.usePinned { pinned ->
            NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
        }
        val tmpDir = NSTemporaryDirectory()
        val fileUrl = NSURL.fileURLWithPath(tmpDir + fileName)

        nsData.writeToURL(fileUrl, true)

        val activityItems = listOf(fileUrl)
        val activityVC = UIActivityViewController(activityItems, null)

        val rootVC = UIApplication.sharedApplication.keyWindow?.rootViewController
        rootVC?.presentViewController(activityVC, animated = true, completion = null)
    }
}