package helix.http.model

import kotlinx.serialization.Serializable

@Serializable
data class Pagination(val cursor: String? = null) {

    fun asPair(): Pair<String, String?> = Pair("cursor", cursor)
}