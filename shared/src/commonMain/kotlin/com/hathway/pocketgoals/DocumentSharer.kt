package com.hathway.pocketgoals


import androidx.compose.runtime.Composable

@Composable
expect fun rememberDocumentSharer(): DocumentSharer

interface DocumentSharer {
    /**
     * Spawns system context sheets to write file byte buffers directly to native user storage.
     */
    fun sharePdf(bytes: ByteArray, fileName: String)
}
