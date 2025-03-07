package ai.scambacon.chatter.groq

import org.junit.Test
import org.junit.Assert.*

class GroqSDKTest {

    @Test
    fun testGetResponse() {
        // Context of the app under test.
        val sdk = GroqSDKImpl()
        val response = sdk.getResponse("Hi how are you?")
        println("RESPONSE" + response)
        assertNotNull(response)
//        assert(response?.contains("I'm good, how are you?"))
    }

}