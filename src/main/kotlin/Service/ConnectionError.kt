package Service

class ConnectionException : Exception {
    constructor(message : String) : super(message)
    constructor(message : String, cause : Throwable) : super(message, cause)
}