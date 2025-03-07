package ai.scambacon.chatter.io

import java.io.File
import java.net.URL
import javax.inject.Inject

class FilePromptManager @Inject constructor(): PromptManager {
    override fun getPrompt(key: String, context: Map<String, String>): String {
        val url: URL = ClassLoader.getSystemResource("prompts/prompt_${key}.txt")
        val contents = File(url.file).readText()

        val response = context.entries.fold(contents) { c, (k, v) ->
            c.replace("{{${k.uppercase()}}}", v)
        }
        return response
    }

}