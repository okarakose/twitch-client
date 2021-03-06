package com.github.frozencure.helix.webhook

import com.github.frozencure.helix.http.model.array.HelixArrayDTO
import com.github.frozencure.helix.http.model.array.ScrollableResponse
import com.github.frozencure.helix.webhook.model.Webhook
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking

/**
 * A collection response that holds one or multiple webhook resources as payload.
 * The resource collection can be split into multiple pages.
 * @constructor Creates a new webhooks response object.
 */
class WebhooksResponse(httpResponse: HttpResponse, httpClient: HttpClient) :
    ScrollableResponse<Webhook>(httpResponse, httpClient) {
    override val helixArrayDTO: HelixArrayDTO<Webhook> = runBlocking {
        httpResponse.receive<HelixArrayDTO<Webhook>>()
    }

    override suspend fun nextPage(): WebhooksResponse? =
        nextPageHttpResponse(cursorKey = "after")?.let {
            WebhooksResponse(
                it, httpClient
            )
        }
}
