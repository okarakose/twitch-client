package helix.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ExtensionType {
    @SerialName("component")
    COMPONENT,

    @SerialName("mobile")
    MOBILE,

    @SerialName("panel")
    PANEL,

    @SerialName("overlay")
    OVERLAY
}