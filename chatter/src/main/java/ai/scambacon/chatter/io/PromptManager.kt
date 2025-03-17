package ai.scambacon.chatter.io

interface PromptManager {

    fun getPrompt(key: String, context: Map<String, String>): String
}
