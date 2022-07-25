package State


sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Content<T>(val data: T) : State<T>()
    data class Error(val error : Throwable) : State<Nothing>()
}
