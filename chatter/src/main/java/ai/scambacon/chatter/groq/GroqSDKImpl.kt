package ai.scambacon.chatter.groq
import ai.scambacon.chatter.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.lang.Exception
import ai.scambacon.chatter.groq.models.*
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import java.io.File
import java.util.Properties


import javax.inject.Inject

class GroqSDKImpl @Inject constructor(
    val apiKey: String? = null,
    val model: String? = null
): GroqSDK {

    private val properties = loadProperties()

    private fun loadProperties(): Properties {
        val properties = Properties()
        val inputStream = object {}.javaClass.getResourceAsStream("/groq.properties")
        inputStream?.use {
            properties.load(it)
        } ?: throw Exception("Could not find properties file for API key")

        val myProperty = properties.getProperty("GROQ_API_KEY")
        println("The value of my.property.name is: $myProperty")

        return properties
    }

    val APIKey: String = apiKey?: properties.getProperty("GROQ_API_KEY")
    val groqModel: String = model?: properties.getProperty("GROQ_MODEL")

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    override fun getResponse(text: String): String? {

        val requestData = ChatCompletionRequest(
            model = groqModel,
            messages = listOf(ChatMessage("user", text))
        )

        var returnVal: String? = null
        runBlocking {
            try {
                val response: HttpResponse = client.post("https://api.groq.com/openai/v1/chat/completions") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $APIKey")
                        append(HttpHeaders.ContentType, ContentType.Application.Json)
                    }
                    setBody(requestData)
                }
                val chatCompletionResponse: ChatCompletionResponse = response.body()
                returnVal = chatCompletionResponse.choices.firstOrNull()?.message?.content
            } catch (e: Exception) {
                println("Error: ${e.message}")
                e.printStackTrace()
            } finally {
                client.close()
            }
        }

        return returnVal
    }
}