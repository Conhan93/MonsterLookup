package Service

import Model.Base.Base

interface ContentService {

    /**
     * Gets the content model from local storage or the API
     *
     * @param contentRequest the name or reference along with
     * a class parameter
     *
     * @return The model class as nullable Base class
     */
    suspend fun getContentAsync(contentRequest : ContentRequest) : Base?

    /**
     * Processes a list of requests with individual callbacks per request
     *
     * @param contentRequests a list of requests, can mix different types of
     * content requests
     * @param onRequestFail function to be called when an error is thrown
     * @param onRequestSucceed function called when request succeeded, result
     * of request is passed as argument
     */
    suspend fun getContentAsync(
        contentRequests: List<ContentRequest>,
        onRequestFail : ((Throwable) -> Unit)? = null,
        onRequestSucceed : (Base?) -> Unit
    ) {
        try {
            contentRequests.forEach { request ->
                getContentAsync(request).let(onRequestSucceed)
            }
        } catch (e : Exception) { onRequestFail?.let { it(e) }}
    }
}