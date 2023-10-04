import CwpAdThemeColors.SURFACE
import CwpAdThemeColors.TOP_GRADIENT
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

//val store = CoroutineScope(SupervisorJob()).creaxteStore()

@Composable
fun CwpAdMain() {
    Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("The Composers Chat") },
                    backgroundColor = MaterialTheme.colors.background,
                )
            }) {
//            ChatApp(displayTextField = displayTextField)
        }
    }
}

@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(
            surface = Color(SURFACE),
            background = Color(TOP_GRADIENT.last()),
        ),
    ) {
        ProvideTextStyle(LocalTextStyle.current.copy(letterSpacing = 0.sp)) {
            content()
        }
    }
}

//fun CoroutineScope.createStore(): Store {
//    val mutableStateFlow = MutableStateFlow(State())
//    val channel: Channel<Action> = Channel(Channel.UNLIMITED)
//
//    return object : Store {
//        init {
//            launch {
//                channel.consumeAsFlow().collect { action ->
//                    mutableStateFlow.value = chatReducer(mutableStateFlow.value, action)
//                }
//            }
//        }
//
//        override fun send(action: Action) {
//            launch {
//                channel.send(action)
//            }
//        }
//
//        override val stateFlow: StateFlow<State> = mutableStateFlow
//    }
//}
