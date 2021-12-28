package furhatos.app.storyteller.utils

import com.google.common.collect.EvictingQueue

object EmotionSingleton{
    val emotionQueue: EvictingQueue<EmotionRecord> = EvictingQueue.create<EmotionRecord>(100)

}