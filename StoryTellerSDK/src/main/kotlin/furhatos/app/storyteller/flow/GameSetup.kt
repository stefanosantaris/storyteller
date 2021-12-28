package furhatos.app.storyteller.flow
import furhatos.app.storyteller.robotName
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.utterance
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val FetchUserName: State = state(CatchName) {
    onEntry {
        if (users.current.name == null) {
            random(
                { furhat.ask("And what about you? What is your name?") },
                { furhat.ask("And what is your name?") },
                { furhat.ask("And your name is what?") })
        } else {
            goto(Idle)
        }
    }

    onReentry {
        if (users.current.name == null) {
            random(
                { furhat.ask("What is your name?") },
                { furhat.ask("What was your name?") },
                { furhat.ask("Your name is what?") })
        } else {
            goto(Idle)
        }
    }

    onNoResponse {
        if (users.current.name == null) {
            random(
                { furhat.say("Hello? Sorry, you might have missed that.") },
                { furhat.say("Hello, somebody out there?") },
                { furhat.say("Did you say something?") })
            furhat.ask("My name is $robotName, what is yours?")
        }
    }
}

val SelfPresent: State = state {
    onEntry {
        random(
            { furhat.say("Hi there. It is a pleasure to meet you. I am $robotName.") },
            { furhat.say("Hello my friend. Glad to meet you! My name is $robotName.") },
            { furhat.say("Hi there. What a pleasure meeting you. My name is $robotName.") })
        goto(FetchUserName)
    }
}

val PresentGame: State = state {
    var firstAnswer = false

    onEntry {
        firstAnswer = false
        furhat.ask("${users.current.name}, would you like to play a game?")
    }

    /*
    Positive response to game
     */
    onResponse<Yes> {
        if (!firstAnswer) {
            furhat.say(utterance {
                +"Awesome!"
                +blocking {
                    furhat.gesture(Gestures.BigSmile, async = false)
                }
                +"This game requires your imagination. I will walk you through an interactive story. In this story, you are the protagonist"
                +"and you can decide how you want to act."
                +blocking {
                    furhat.gesture(Gestures.Oh, async = false)
                }
            })
            firstAnswer = true
            furhat.ask("Are you ready to play this game with me?")
        } else {
            furhat.say(utterance {
                +"Wonderful!"
                +blocking {
                    furhat.gesture(Gestures.BigSmile, async = false)
                }
                + delay(200)
                +"Then let us start"
                + delay(500)
            })
            users.current.wantsPlay = true
            goto(OpeningScene)
        }
    }

    /*
    Negative response to game
     */
    onResponse<No> {
        random(
            { furhat.say("What a pity! Have a great day then.") },
            { furhat.say("That is sad. However, thank you for your time.") },
            { furhat.say("What a pity. Maybe next time.") }
        )
        users.current.wantsPlay = false
        goto(Idle)
    }
}