package ai.scambacon.chatter.groq.models
import kotlinx.serialization.Serializable

@Serializable
data class ChatChoice(val message: ChatMessage) {
}