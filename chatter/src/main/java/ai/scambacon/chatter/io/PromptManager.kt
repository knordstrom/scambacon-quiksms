package ai.scambacon.chatter.io

import dagger.Module
import dagger.Provides

@Module
interface PromptManager {

    @Provides
    fun getPrompt(key: String, context: Map<String, String>): String
}
