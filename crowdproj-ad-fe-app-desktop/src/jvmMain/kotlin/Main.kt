import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication

fun main() =
    singleWindowApplication(
        title = "CrowdProj Ad App",
        state = WindowState(size = DpSize(500.dp, 800.dp))
    ) {
        CwpAdMain()
    }
