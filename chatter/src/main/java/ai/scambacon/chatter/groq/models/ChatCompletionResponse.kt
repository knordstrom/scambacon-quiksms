package ai.scambacon.chatter.groq.models
import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionResponse(val choices: List<ChatChoice> = emptyList()) {
}
