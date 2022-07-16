import androidx.compose.runtime.mutableStateOf


enum class State {
    ERROR,
    SUCCESS,
    START
}

var state = mutableStateOf(State.START)