package Model.Service.ContentService

sealed class ContentServiceException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {
    class ConnectionException(message: String, cause: Throwable? = null) : ContentServiceException(message, cause)
    class SerializationException(message: String, cause: Throwable? = null) : ContentServiceException(message, cause)
    class ContentNotFoundException(message: String, cause: Throwable? = null) : ContentServiceException(message, cause)
    class BadURLException(message: String, cause: Throwable? = null) : ContentServiceException(message, cause)
}