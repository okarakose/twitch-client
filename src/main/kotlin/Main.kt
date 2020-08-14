import helix.auth.AuthService
import helix.auth.model.AuthScope
import helix.auth.model.request.OauthAuthorizeRequestModel
import helix.auth.oauth.OAuthConfig
import helix.channels.ChannelService
import helix.http.credentials.DefaultApiSettings
import helix.http.credentials.OauthApiCredentials
import helix.moderation.ModerationService
import helix.streams.StreamService
import helix.users.UserService
import io.ktor.client.engine.apache.ApacheEngineConfig
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Properties
import kotlinx.serialization.UnstableDefault

@OptIn(UnstableDefault::class)
@ImplicitReflectionSerializer
fun main() {
//    authenticateUser()
    val authConfig = OAuthConfig()
    authConfig.token = "7omkmgbiz7ynbx5cwxod8263tysjmu"
    authConfig.clientId = "nyufzvu4k8h80iq0r7ya4zx1fsas7d"
    val streamService = StreamService(ApacheEngineConfig(), authConfig)
    val userService = UserService(ApacheEngineConfig(), authConfig)
    runBlocking {
        val currentUser = userService.getUser().resource
        currentUser?.let {
            val result = userService.getUserActiveExtensions()
            print(result.resource.panelExtensions)
        }
    }

}


@ImplicitReflectionSerializer
fun authenticateUser() {
    val requestModel = OauthAuthorizeRequestModel(
        "nyufzvu4k8h80iq0r7ya4zx1fsas7d", // client-id
        "http://localhost", // redirect-URI
        "token", // response type
        AuthScope.values().toList()
    )
    val authService = AuthService(ApacheEngineConfig())
    runBlocking {
        val response = authService.authorizeAppForUser(requestModel)
        println(response)
    }
}

