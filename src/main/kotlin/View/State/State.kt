package View.State

sealed class State {
    object Loading : State()
    object Content : State()
    data class Error(val message : String, val cause : Throwable? = null) : State()
}