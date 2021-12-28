package furhatos.app.storyteller.utils.emotion

import java.util.ArrayDeque

object EmotionStorage{
    val emotionQueue: ArrayDeque<EmotionRecord> = ArrayDeque<EmotionRecord>(100)

    fun getLatestEmotion(): EmotionRecord {
        return emotionQueue.first()
    }

    fun getLatestEmotions(number:Int): List<Any> {
        val emotions =  emotionQueue.toArray()
        return emotions.take(number)

    }
}