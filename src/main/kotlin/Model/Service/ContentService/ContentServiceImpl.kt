package Model.Service.ContentService

import Model.Data.Base.Base
import Model.Service.HTTPService
import Model.Storage.ILocalStorage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.http.HttpClient

import kotlin.reflect.KClass

class ContentServiceImpl(
    private val client : HttpClient = HttpClient.newHttpClient(),
    private val storage : ILocalStorage
) : ContentService, HTTPService {

    override suspend fun getContentAsync(contentRequest: ContentRequest): Base? {

        // check storage
        contentRequest.getName()?.let { name ->
            storage.getByName(name, contentRequest.getClass())?.let { return it  }
        }


        var returner : Base? = null

        val request = try {
            when (contentRequest) {
                is ContentRequest.RequestByName -> buildRequest(contentRequest.name, contentRequest.klass)
                is ContentRequest.RequestByReference -> buildRequest(contentRequest.ref, contentRequest.klass)
            }
        } catch (e: IllegalArgumentException) {
            throw ContentServiceException.BadURLException(e.message!!, e)
        }

        val type = when(contentRequest) {
            is ContentRequest.RequestByName -> contentRequest.klass
            is ContentRequest.RequestByReference -> contentRequest.klass
        }

        handleRequestAsync(
            request = request,
            client = client,
            onFail = { throw ContentServiceException.ConnectionException("Unable to send message", it) }
        ) {
            if (it.statusCode().equals(404))
                throw ContentServiceException.ContentNotFoundException("Content Not Found")

            val content = decode(it.body(), type)
                ?: throw ContentServiceException.SerializationException("Error decoding monster")

            storage.store(content)

            returner = content
        }

        return returner
    }


    private fun decode(jsonText : String ,klass : KClass<*>) : Base? {
        val decoder = Json { ignoreUnknownKeys = true }

        return try {
            when(klass) {
                Model.Data.Spell.Spell::class -> {
                    decoder.decodeFromString<Model.Data.Spell.Spell>(jsonText)
                }
                Model.Data.Monster.Monster::class -> {
                    decoder.decodeFromString<Model.Data.Monster.Monster>(jsonText)
                }
                else -> null
            }
        } catch (e : Exception) {
            return null
        }
    }
}