package Model.Service

import Model.Data.Base.APIReference
import Model.Data.Monster.Monster
import Model.Data.Spell.Spell
import Model.Service.ContentService.ContentRequest
import Model.Service.ContentService.ContentService
import Model.Service.ContentService.ContentServiceException
import Model.Service.ContentService.ContentServiceImpl
import Model.Storage.ILocalStorage
import TestHelper.Resource.LoadTestResource
import TestHelper.Resource.getTestResource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ContentServiceTest {

    val testMonster = getTestResource(LoadTestResource.blackDragon) as Monster
    val testSpell = getTestResource(LoadTestResource.acidArrow) as Spell

    val storage = mock(ILocalStorage::class.java)

    @BeforeEach
    fun before() {
        // disables testing storage
        `when`(storage.getSpellByName(Mockito.anyString())).thenReturn(null)
        `when`(storage.getMonsterByName(Mockito.anyString())).thenReturn(null)
    }

    fun mockClientReturnsBody(body : String) : HttpClient {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(200)
        `when`(mockResponse.body()).thenReturn(body)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java),Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        return mockClient
    }

    @Test
    fun `Monster request by name returns monster`() = runTest {
        lateinit var responseBody : String
        getTestResource(LoadTestResource.blackDragon) {
            responseBody = it
        }

        val mockClient = mockClientReturnsBody(responseBody)

        val service : ContentService = ContentServiceImpl(mockClient, storage)

        val request = ContentRequest.RequestByName(testMonster.name!!, Monster::class)

        val actual = service.getContentAsync(request)

        assertNotNull(actual)
        assertEquals(testMonster, actual)
    }

    @Test
    fun `Spell request by name returns spell`() = runTest {
        lateinit var responseBody : String
        getTestResource(LoadTestResource.acidArrow) {
            responseBody = it
        }

        val mockClient = mockClientReturnsBody(responseBody)

        val service : ContentService = ContentServiceImpl(mockClient, storage)

        val request = ContentRequest.RequestByName(testSpell.name!!, Spell::class)

        val actual = service.getContentAsync(request)

        assertNotNull(actual)
        assertEquals(testSpell, actual)
    }

    @Test
    fun `Monster request by reference returns monster`() = runTest {
        lateinit var responseBody : String
        getTestResource(LoadTestResource.blackDragon) {
            responseBody = it
        }

        val mockClient = mockClientReturnsBody(responseBody)

        val service : ContentService = ContentServiceImpl(mockClient, storage)

        val reference = APIReference(
            index = testMonster.index!!,
            name = testMonster.name!!,
            url = testMonster.url!!
        )

        val request = ContentRequest.RequestByReference(reference, Monster::class)

        val actual = service.getContentAsync(request)

        assertNotNull(actual)
        assertEquals(testMonster, actual)
    }

    @Test
    fun `Spell request by reference returns spell`() = runTest {
        lateinit var responseBody : String
        getTestResource(LoadTestResource.acidArrow) {
            responseBody = it
        }

        val mockClient = mockClientReturnsBody(responseBody)

        val service : ContentService = ContentServiceImpl(mockClient, storage)

        val reference = APIReference(
            index = testSpell.index!!,
            name = testSpell.name!!,
            url = testSpell.url!!
        )

        val request = ContentRequest.RequestByReference(reference, Spell::class)

        val actual = service.getContentAsync(request)

        assertNotNull(actual)
        assertEquals(testSpell, actual)
    }

    @Test
    fun `Should throw connection error on client send throw`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java),Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenThrow(InterruptedException("foo"))

        val service : ContentService = ContentServiceImpl(mockClient, storage)
        var request = ContentRequest.RequestByName(testMonster.name!!, Monster::class)
        assertThrows<ContentServiceException.ConnectionException> {
            service.getContentAsync(request)
        }
    }

    @Test
    fun `Request by name should return serialization exception`() = runTest {
        val mockClient = mockClientReturnsBody("{\"error\":\"Not found")

        val service : ContentService = ContentServiceImpl(mockClient, storage)

        var request = ContentRequest.RequestByName("foo", Monster::class)
        assertThrows<ContentServiceException.SerializationException> {
            service.getContentAsync(request)
        }

        request = ContentRequest.RequestByName("foo", Spell::class)
        assertThrows<ContentServiceException.SerializationException> {
            service.getContentAsync(request)
        }
    }

    @Test
    fun `Get by name should return content not found exception`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(404)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service : ContentService = ContentServiceImpl(mockClient, storage)

        var request = ContentRequest.RequestByName("foo", Monster::class)
        assertThrows<ContentServiceException.ContentNotFoundException> {
            service.getContentAsync(request)
        }

        request = ContentRequest.RequestByName("foo", Spell::class)
        assertThrows<ContentServiceException.ContentNotFoundException> {
            service.getContentAsync(request)
        }
    }

    @Test
    fun `Get by reference should return content not found exception`() = runTest {
        val mockClient = mock(HttpClient::class.java)

        val mockResponse = mock(HttpResponse::class.java)

        `when`(mockResponse.statusCode()).thenReturn(404)

        `when`(mockClient.send(Mockito.any(HttpRequest::class.java), Mockito.eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockResponse as HttpResponse<String>?)

        val service : ContentService = ContentServiceImpl(mockClient, storage)
        assertThrows<ContentServiceException.ContentNotFoundException> {
            val reference = APIReference(
                index = testMonster.index!!,
                name = testMonster.name!!,
                url = testMonster.url!!
            )
            val request = ContentRequest.RequestByReference(reference, Monster::class)
            service.getContentAsync(request)
        }

        assertThrows<ContentServiceException.ContentNotFoundException> {
            val reference = APIReference(
                index = testSpell.index!!,
                name = testSpell.name!!,
                url = testSpell.url!!
            )
            val request = ContentRequest.RequestByReference(reference, Spell::class)
            service.getContentAsync(request)
        }
    }

    @Test
    fun `Get by reference throws on null url`() = runTest {
        val mockClient = mock(HttpClient::class.java)
        val service : ContentService = ContentServiceImpl(mockClient, storage)
        assertThrows<ContentServiceException.BadURLException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
            )
            val request = ContentRequest.RequestByReference(reference, Monster::class)
            service.getContentAsync(request)
        }
        assertThrows<ContentServiceException.BadURLException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
            )
            val request = ContentRequest.RequestByReference(reference, Spell::class)
            service.getContentAsync(request)
        }
    }

    @Test
    fun `Get by reference throws on bad url`() = runTest {
        val mockClient = mock(HttpClient::class.java)
        val service : ContentService = ContentServiceImpl(mockClient, storage)
        assertThrows<ContentServiceException.BadURLException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
                url = "/bar/barbar/foo"
            )
            val request = ContentRequest.RequestByReference(reference, Monster::class)
            service.getContentAsync(request)
        }
        assertThrows<ContentServiceException.BadURLException> {
            val reference = APIReference(
                index = "foo",
                name = "foo.txt",
                url = "/bar/barbar/foo"
            )
            val request = ContentRequest.RequestByReference(reference, Spell::class)
            service.getContentAsync(request)
        }
    }
}