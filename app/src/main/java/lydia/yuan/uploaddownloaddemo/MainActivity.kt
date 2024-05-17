package lydia.yuan.uploaddownloaddemo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import lydia.yuan.uploaddownloaddemo.ui.theme.UploadDownloadDemoTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UploadDownloadDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DownloadPage(innerPadding, this)
                }
            }
        }
    }
}

@Composable
fun DownloadPage(innerPadding: PaddingValues, context: Context) {
    val (downloadInProgress, setDownloadInProgress) = remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Button(onClick = { downloadPdf(context, "https://link.testfile.org/PDF100MB", "Download Test", setDownloadInProgress) }, enabled = !downloadInProgress) {
            Text("Download")
        }
    }
}