package subscriptions

import com.github.frozencure.helix.subscriptions.SubscriptionService
import com.github.frozencure.helix.subscriptions.SubscriptionsResponse
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import util.HttpClientMockBuilder

class `Given GET subscriptions is called and single subscription is retrieved` {

    private val broadcasterId: Long = 123

    private val subscriptionResponse = runBlocking<SubscriptionsResponse> {
        SubscriptionService(HttpClientMockBuilder.withJsonContent(SubscriptionsTestData.SINGLE_SUBSCRIPTION))
            .getSubscriptions(broadcasterId)
    }

    @Test
    fun `then request has broadcaster ID as parameter`() =
        assert(subscriptionResponse.httpResponse.request.url.parameters["broadcaster_id"] == broadcasterId.toString())

    @Test
    fun `then a single subscription is returned`() =
        assert(subscriptionResponse.resources.size == 1)

    @Test
    fun `then subscription does not have gifter ID`() =
        assert(subscriptionResponse.resources.first().gifterId == null)


    @Test
    fun `then subscription does not have gifter name`() =
        assert(subscriptionResponse.resources.first().gifterName == null)

}


class `Given GET subscriptions is called and single subscription with gifter is retrieved` {

    private val broadcasterId: Long = 123

    private val subscriptionResponse = runBlocking<SubscriptionsResponse> {
        SubscriptionService(HttpClientMockBuilder.withJsonContent(SubscriptionsTestData.SINGLE_SUBSCRIPTION_WITH_GIFTER))
            .getSubscriptions(broadcasterId)
    }

    @Test
    fun `then request has broadcaster ID as parameter`() =
        assert(subscriptionResponse.httpResponse.request.url.parameters["broadcaster_id"] == broadcasterId.toString())

    @Test
    fun `then a single subscription is returned`() =
        assert(subscriptionResponse.resources.size == 1)

    @Test
    fun `then subscription does not have gifter ID`() =
        assert(subscriptionResponse.resources.first().gifterId != null)


    @Test
    fun `then subscription does not have gifter name`() =
        assert(subscriptionResponse.resources.first().gifterName != null)

}


class `Given GET subscriptions is called and multiple subscriptions are retrieved` {

    private val broadcasterId: Long = 123

    private val subscriptionResponse = runBlocking<SubscriptionsResponse> {
        SubscriptionService(HttpClientMockBuilder.withJsonContent(SubscriptionsTestData.MULTIPLE_SUBSCRIPTIONS))
            .getSubscriptions(broadcasterId)
    }

    @Test
    fun `then request has broadcaster ID as parameter`() =
        assert(subscriptionResponse.httpResponse.request.url.parameters["broadcaster_id"] == broadcasterId.toString())

    @Test
    fun `then subscriptions are returned`() =
        assert(subscriptionResponse.resources.size == 2)


    @Test
    fun `then pagination is retrieved`() =
        assert(subscriptionResponse.pagination != null)

    class `And next page is retrieved` {

        private val broadcasterId: Long = 123

        private val subscriptionResponse = runBlocking<SubscriptionsResponse?> {
            SubscriptionService(HttpClientMockBuilder.withJsonContent(SubscriptionsTestData.MULTIPLE_SUBSCRIPTIONS))
                .getSubscriptions(broadcasterId).nextPage()
        }

        @Test
        fun `then request has cursor as parameter`() =
            assert(subscriptionResponse?.httpResponse?.request?.url?.parameters?.get("cursor") != null)

        @Test
        fun `then subscriptions are returned`() =
            assert(subscriptionResponse?.resources?.size == 2)

    }

}