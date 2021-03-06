package streams

import com.github.frozencure.helix.streams.StreamService
import com.github.frozencure.helix.streams.tags.StreamTagsResponse
import com.github.frozencure.helix.streams.tags.model.ReplaceTagsRequestModel
import io.ktor.client.statement.*
import io.ktor.content.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import util.HttpClientMockBuilder

class `Given GET all stream tags is called` {

    private val tagIds = listOf("621fb5bf-5498-4d8f-b4ac-db4d40d401bf", "7b49f69a-5d95-4c94-b7e3-66e2c0c6f6c6")

    private val streamTagsResponse = runBlocking<StreamTagsResponse> {
        StreamService(HttpClientMockBuilder.withJsonContent(StreamsTestData.MULTIPLE_STREAM_TAGS_WITH_PAGINATION))
            .getAllStreamTags(tagIds = tagIds)
    }

    @Test
    fun `then multiple stream tags are retrieved`() =
        assert(streamTagsResponse.resources.size == 2)


    @Test
    fun `then pagination exists`() =
        assert(streamTagsResponse.pagination != null)

    @Test
    fun `then request has tags as parameters`() =
        assert(streamTagsResponse.httpResponse.request.url.parameters.getAll("tag_id") == tagIds)

    @Test
    fun `then request has first as parameter`() =
        assert(streamTagsResponse.httpResponse.request.url.parameters["first"] == 100.toString())


    class `And next page is retrieved` {

        private val streamTagsResponse = runBlocking<StreamTagsResponse?> {
            StreamService(HttpClientMockBuilder.withJsonContent(StreamsTestData.MULTIPLE_STREAM_TAGS_WITH_PAGINATION))
                .getAllStreamTags().nextPage()
        }


        @Test
        fun `then multiple stream tags are retrieved`() =
            assert(streamTagsResponse?.resources?.size == 2)


        @Test
        fun `then pagination exists`() =
            assert(streamTagsResponse?.httpResponse?.request?.url?.parameters?.get("after") != null)

    }
}


class `Given GET stream tags for broadcaster is called` {

    private val broadcasterId: Long = 12345

    private val streamTagsResponse = runBlocking<StreamTagsResponse> {
        StreamService(HttpClientMockBuilder.withJsonContent(StreamsTestData.MULTIPLE_STREAM_TAGS_WITH_PAGINATION))
            .getStreamTags(broadcasterId)
    }

    @Test
    fun `then multiple stream tags are retrieved`() =
        assert(streamTagsResponse.resources.size == 2)


    @Test
    fun `then pagination exists`() =
        assert(streamTagsResponse.pagination != null)

    @Test
    fun `then request has broadcaster id as parameter`() =
        assert(streamTagsResponse.httpResponse.request.url.parameters["broadcaster_id"] == broadcasterId.toString())


    class `And next page is retrieved` {

        private val broadcasterId: Long = 12345

        private val streamTagsResponse = runBlocking<StreamTagsResponse?> {
            StreamService(HttpClientMockBuilder.withJsonContent(StreamsTestData.MULTIPLE_STREAM_TAGS_WITH_PAGINATION))
                .getStreamTags(broadcasterId).nextPage()
        }


        @Test
        fun `then multiple stream tags are retrieved`() =
            assert(streamTagsResponse?.resources?.size == 2)


        @Test
        fun `then pagination exists`() =
            assert(streamTagsResponse?.httpResponse?.request?.url?.parameters?.get("after") != null)

    }
}

class `Given PUT replace stream tags is called` {

    private val tagIds = listOf("621fb5bf-5498-4d8f-b4ac-db4d40d401bf", "7b49f69a-5d95-4c94-b7e3-66e2c0c6f6c6")

    private val broadcasterId: Long = 12345

    private val httpResponse = runBlocking<HttpResponse> {
        StreamService(
            HttpClientMockBuilder.withStatusResponse(HttpStatusCode.NoContent)
        )
            .updateStreamTags(broadcasterId, tagIds)
    }

    @Test
    fun `then request has broadcaster id as parameter`() =
        assert(httpResponse.request.url.parameters["broadcaster_id"] == broadcasterId.toString())

    @Test
    fun `then request has tag IDs as body`() =
        assert(
            (httpResponse.request.content as TextContent).text == Json.encodeToString(
                ReplaceTagsRequestModel.serializer(), ReplaceTagsRequestModel(tagIds)
            )
        )


}