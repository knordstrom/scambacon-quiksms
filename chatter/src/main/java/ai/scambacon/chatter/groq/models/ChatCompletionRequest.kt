package ai.scambacon.chatter.groq.models
import kotlinx.serialization.Serializable

@Serializable
data class ChatCompletionRequest(val model: String, val messages: List<ChatMessage>)
