package furhatos.app.storyteller.utils.emotion

import java.util.ArrayDeque

object EmotionStorage{
    val emotionQueue: ArrayDeque<EmotionRecord> = ArrayDeque<EmotionRecord>(100)

    private fun getLatestEmotions(numEmotions:Int = 1): List<Any> {
        val emotions =  emotionQueue.toArray()
        return emotions.take(numEmotions)
    }

    fun getDominantEmotion(numEmotions:Int = 1): String? {
        val allEmotions = getLatestEmotions(numEmotions) as List<EmotionRecord>

        val numbersByElement = allEmotions.groupingBy { it.emotion }.eachCount()
        println(numbersByElement)

        return if (numbersByElement.isNotEmpty()) numbersByElement.maxBy { it.value }?.key as String else null
    }


}