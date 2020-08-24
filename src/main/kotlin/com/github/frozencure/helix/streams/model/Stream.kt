package com.github.frozencure.helix.streams.model


import com.github.frozencure.helix.http.model.AbstractResource
import com.github.frozencure.helix.shared.InstantSerializer

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant


@Serializable
data class Stream(
    @SerialName("game_id")
    val gameId: String,
    @SerialName("id")
    val id: Long,
    @SerialName("language")
    val language: String,
    @SerialName("started_at")
    @Serializable(with = InstantSerializer::class)
    val startedAt: Instant,
    @SerialName("tag_ids")
    val tagIds: List<String>,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("user_id")
    val userId: Long,
    @SerialName("user_name")
    val userName: String,
    @SerialName("viewer_count")
    val viewerCount: Int
) : AbstractResource()