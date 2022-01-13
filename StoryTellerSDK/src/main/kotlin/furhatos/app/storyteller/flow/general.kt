package furhatos.app.storyteller.flow
import furhatos.app.storyteller.flow.BasementScene.BranchOffScene
import furhatos.app.storyteller.nlu.TellNameBriefly
import furhatos.app.storyteller.robotName
import furhatos.app.storyteller.utils.StoryCharacter
import furhatos.app.storyteller.utils.changeCharacter
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.onUserEnter
import furhatos.flow.kotlin.onUserLeave
import furhatos.flow.kotlin.utterance
import furhatos.gestures.Gestures
import furhatos.nlu.common.TellName
import furhatos.records.User

val Idle: State = state {

    /*
    Initial state
     */
    init {
        // set voice and face
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        if (users.count > 0) {
            furhat.attend(users.random)
            initializeUserGameState(users.current)
            goto(SelfPresent)
        }
    }

    /*
    On entry
     */
    onEntry {
        if (users.count > 0) {
            if (users.current.name == null) {
                furhat.attend(users.current)
                goto(FetchUserName)
            } else {
                if (users.current.hasPlayed == false) {
                    if (users.current.wantsPlay == true) {
                        furhat.attend(users.current)
                        goto(PresentGame)
                    }
                } else {
                    furhat.attend(users.current)
                    val validate: Boolean? = furhat.askYN("Do you want to play again?")
                    if (validate == true) {
                        initializeUserGameState(users.current)
                        goto(OpeningScene)
                    }
                }
            }
        }
    }

    /*
    If a users enters the state
     */
    onUserEnter {
        furhat.attend(it)

        // set voice and face
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        initializeUserGameState(users.current)
        goto(SelfPresent)
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
            { furhat.say("Hello there! Lovely that you joined our small game.") },
            { furhat.say("Hello! Nice to have more people joining today!") },
            { furhat.say("Welcome! What a pleasure to see you.") }
        )
        furhat.glance(mainUser)
        furhat.attend(mainUser)

        reentry()
    }
}

val CatchName: State = state(Interaction) {
    /*
    Tell name in brief fashion
     */
    onResponse<TellNameBriefly> {
        users.current.name = "${it.intent.name}"
        users.current.wantsPlay = true
        users.current.hasPlayed = false
        initializeUserGameState(users.current)
        goto(process_name("${it.intent.name}"))
    }

    /*
    Tell your name in extensive fashion
     */
    onResponse<TellName> {
        users.current.name = "${it.intent.name}"
        users.current.wantsPlay = true
        users.current.hasPlayed = false
        initializeUserGameState(users.current)
        goto(process_name("${it.intent.name}"))
    }

    /*
    Tell new name
     */

    onResponse {
        if (users.current.name == null) {
            random(
                {
                    furhat.say(utterance {
                        +"Did you say:"
                        +delay(200)
                        +it.text
                        +"?"
                    })
                },
                {
                    furhat.say(utterance {
                        +"I heard:"
                        +delay(200)
                        +it.text
                        +"?"
                    })
                }
            )

            val validate: Boolean? = furhat.askYN(utterance {
                +delay(200)
                +"Is that your name?"
            })

            if (validate == true) {
                users.current.name = it.text
                users.current.wantsPlay = true
                initializeUserGameState(users.current)
                random(
                    {
                        furhat.say(utterance {
                            +"${users.current.name}, what a wonderful name I just learned."
                            +blocking {
                                furhat.gesture(Gestures.BigSmile, async = false)
                            }
                        })
                    },
                    {
                        furhat.say(utterance {
                            +"${users.current.name}... Ohh one more beautiful name I learned."
                            +blocking {
                                furhat.gesture(Gestures.Wink, async = false)
                            }
                        })
                    }
                )
                goto(PresentGame)
            } else {
                random(
                    { furhat.ask("So what is your name?") },
                    { furhat.ask("Okay, could you repeat your name then?") },
                    { furhat.ask("Oh sorry, so what is your name?") }
                )
            }
        } else {
            random(
                { furhat.ask("Sorry, what did you say?") },
                { furhat.ask("Sorry, I didn't understand that. Could you repeat that?") },
                { furhat.ask("Sorry, could you repeat that?") }
            )
        }
    }
}

/*
Process name of user
 */
fun process_name(userName: String): State = state {
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
        goto(PresentGame)
    }
}

fun initializeUserGameState(user: User) {
    user.visitedTavern = false
    user.visitedWoman = false
    user.visitedTownSquare = false
    user.visitedAlley = false
    user.talkedToBartender = false
    user.talkedToWhisperingMen = false
    user.visitedBasement = false
}