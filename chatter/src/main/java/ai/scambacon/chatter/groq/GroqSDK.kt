package ai.scambacon.chatter.groq

import dagger.Module
import dagger.Provides

@Module
interface GroqSDK {

    @Provides
    fun getResponse(text: String): String?
}