package furhatos.app.storyteller

import furhatos.app.storyteller.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

val objserv = "tcp://localhost:3333"
val socket: ZMQ.Socket = getConnectedSocket(ZMQ.REQ, objserv)

fun start_emotion_detector() {
    while (true) {
        socket.send("Emotion")
        val message = socket.recvStr()
        println(message)
        TimeUnit.SECONDS.sleep(1L)
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
