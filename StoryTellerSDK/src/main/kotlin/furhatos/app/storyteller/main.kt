package furhatos.app.storyteller

import furhatos.app.storyteller.flow.Idle
import furhatos.app.storyteller.utils.getConnectedSocket
import furhatos.app.storyteller.utils.emotion.EmotionRecord
import furhatos.app.storyteller.utils.emotion.EmotionStorage
import furhatos.skills.Skill
import furhatos.flow.kotlin.Flow
import org.zeromq.ZMQ
import java.time.ZoneOffset
import java.time.OffsetDateTime

val objserv = "tcp://localhost:3333"
val socket: ZMQ.Socket = getConnectedSocket(ZMQ.SUB, objserv)

fun start_emotion_detector() {
    while (true) {
        val queueMessage: String = socket.recvStr()
        // The first part contains the topic and the second part is the actual message
        val message: List<String> = queueMessage.split(' ')

        if (message.size > 1) {
            val emotion: String = message[1]
            // get current time in UTC, no millis needed
            val nowInUtc: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
            // Register the emotion record in the queue
            val record: EmotionRecord = EmotionRecord(emotion, nowInUtc)
            EmotionStorage.emotionQueue.push(record)


//            println(EmotionStorage.getLatestEmotion().emotion)
//            println("All EMOTIONS")
//            val allEmotions = EmotionStorage.getLatestEmotions(3)
//            for (record in allEmotions) {
//                val emotionRecord: EmotionRecord = record as EmotionRecord
//                println(emotionRecord.emotion)
//            }
        }

    }
}


class StorytellerSkill : Skill() {
    override fun start() {
        start_emotion_detector()
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
