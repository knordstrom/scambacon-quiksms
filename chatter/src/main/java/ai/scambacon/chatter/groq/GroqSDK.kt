package ai.scambacon.chatter.groq

interface GroqSDK {

    fun getResponse(text: String): String?
}