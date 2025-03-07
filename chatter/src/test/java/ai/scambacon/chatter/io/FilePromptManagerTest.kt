package ai.scambacon.chatter.io

import org.junit.Test
import org.junit.Assert.*

class FilePromptManagerTest {
    @Test
    fun testPromptRetrievedAndInterpolated() {
        // Context of the app under test.
        val mgr = FilePromptManager()
        val prompt = mgr.getPrompt("initiate_conversation", mapOf("MESSAGE_TEXT" to "Hi how are you?"))
        println("PROMPT" + prompt)
        assertNotNull(prompt)
        assert(prompt.contains("Hi how are you?"))
    }
}