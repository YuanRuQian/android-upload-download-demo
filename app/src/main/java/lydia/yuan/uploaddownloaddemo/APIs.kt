package lydia.yuan.uploaddownloaddemo

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter

fun downloadPdf(context: Context, url: String, title: String, setDownloadInProgress: (Boolean) -> Unit) {
    setDownloadInProgress(true)
    val downloadUri: Uri = Uri.parse(url)
    val request = DownloadManager.Request(downloadUri)

    request.setTitle("Downloading $title")
    request.setDescription("Downloading $title from $url")
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${title}_${System.currentTimeMillis()}.pdf")

    request.allowScanningByMediaScanner()
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

    // Enqueue a new download and save the referenceId
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val downloadReference = downloadManager.enqueue(request)
    Log.d("Download", "Download Reference: $downloadReference from $url")

    // FIXME: Register a BroadcastReceiver to listen for download completion
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadReference == downloadId) {
                    // Download completed
                    setDownloadInProgress(false)
                    context.unregisterReceiver(this)  // Unregister receiver once download is complete
                    Log.d("Download", "Download complete for reference: $downloadReference")
                }
            }
        }
    }
    val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
    context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
}
