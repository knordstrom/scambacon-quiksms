package ai.scambacon.chatter.io

import java.io.File
import java.lang.Exception
import java.net.URL
import javax.inject.Inject

class FilePromptManager @Inject constructor(): PromptManager {
    override fun getPrompt(key: String, context: Map<String, String>): String {
        val resourceName = "/prompts/prompt_${key}.txt"

        val inputStream = object {}.javaClass.getResourceAsStream(resourceName)
        val contents = inputStream?.use {
            it.readBytes().toString(Charsets.UTF_8)
        } ?: throw Exception("Could not find prompt $key")

        val response = context.entries.fold(contents) { c, (k, v) ->
            c.replace("{{${k.uppercase()}}}", v)
        }
        return response
    }

}