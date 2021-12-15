package furhatos.app.storyteller.flow
import furhatos.app.storyteller.nlu.TellNameBriefly
import furhatos.app.storyteller.robotName
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.TellName
import furhatos.util.*

val Idle: State = state {

    init {
        furhat.setVoice(Language.ENGLISH_US, Gender.MALE)
        if (users.count > 0) {
            furhat.attend(users.random)
            goto(OpeningScene)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(SelfPresent)
        //goto(OpeningScene)
    }
}

val Interaction: State = state {

    /*
    Define behavior for people leaving the scene
     */
    onUserLeave(instant = true) {
        if (users.count > 0) {
            if (it == users.current) {
                furhat.attend(users.other)
                goto(OpeningScene)
            } else {
                furhat.glance(it)
            }
        } else {
            goto(Idle)
        }
    }

    /*
    Define behavior when additional user enters
     */
    onUserEnter(instant = true) {
        val mainUser = users.current

        // attend new entering user
        furhat.attend(it)
        random(
            {furhat.say("Hello there! Lovely that you joined our small game.")},
            {furhat.say("Hello! Nice to have more people joining today!")},
            {furhat.say("Welcome! What a pleasure to see you.")}
        )
        furhat.glance(mainUser)
        furhat.attend(mainUser)

        reentry()
    }

    /*
    Tell name in brief fashion
     */
    onResponse<TellNameBriefly> {
        users.current.name = "${it.intent.name}"
        goto(process_name("${it.intent.name}"))
    }

    /*
    Tell your name in extensive fashion
     */
    onResponse<TellName> {
        users.current.name = "${it.intent.name}"
        goto(process_name("${it.intent.name}"))
    }
}

/*
Process name of user
 */
fun process_name(userName : String) : State = state {
    onEntry {
        if (userName == robotName) {
            random(
                {
                    furhat.say(utterance {
                        +"Your name is also ${users.current.name}?"
                        +blocking {
                            furhat.gesture(Gestures.Surprise, async = false)
                        }
                        +"What a funny coincidence!"
                    })
                },
                {
                    furhat.say(utterance {
                        +"What? Your name is also ${users.current.name}?"
                        +blocking {
                            furhat.gesture(Gestures.BigSmile, async = false)
                        }
                        +"How funny!"
                    })
                },
                {
                    furhat.say(utterance {
                        +"We are both named ${users.current.name}?"
                        +blocking {
                            furhat.gesture(Gestures.Surprise, async = false)
                        }
                        +"What a funny coincidence!"
                    })
                }
            )
        } else {
            random(
                {
                    furhat.say(utterance {
                        +"${users.current.name}, what a wonderful name"
                        +blocking {
                            furhat.gesture(Gestures.BigSmile, async = false)
                        }
                    })
                },
                {
                    furhat.say(utterance {
                        +"${users.current.name}... I like that name!"
                        +blocking {
                            furhat.gesture(Gestures.Wink, async = false)
                        }
                    })
                }
            )
        }
        goto(Idle)
    }
}