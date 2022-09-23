package Service

import Model.Base.Base
import Model.Monster.Monster
import Model.Spell.Spell
import Storage.ILocalStorage
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
                Spell::class -> {
                    decoder.decodeFromString<Spell>(jsonText)
                }
                Monster::class -> {
                    decoder.decodeFromString<Monster>(jsonText)
                }
                else -> null
            }
        } catch (e : Exception) {
            return null
        }
    }
}