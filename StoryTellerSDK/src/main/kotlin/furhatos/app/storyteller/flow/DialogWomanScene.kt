package furhatos.app.storyteller.flow
import furhatos.app.storyteller.nlu.AskAboutTavern
import furhatos.app.storyteller.nlu.FollowMan
import furhatos.app.storyteller.nlu.LeaveToTavern
import furhatos.app.storyteller.nlu.LeaveToTownSquare
import furhatos.app.storyteller.nlu.IamCop
import furhatos.app.storyteller.utils.StoryCharacter
import furhatos.app.storyteller.utils.changeCharacter
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.utterance
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.gestures.Gestures
import furhatos.nlu.NullIntent

val WomanOptions: State = state(Interaction) {

    onResponse<LeaveToTavern> {
        goto(TavernArrival)
    }

    onResponse<LeaveToTownSquare> {
        goto(TownSquareArrival)
    }

    onResponse<IamCop> {
        goto(DialogWomanAnswer_Cop)
    }

    onResponse<FollowMan> {
        goto(DialogWomanAnswer_TattooMan)
    }

    onResponse<AskAboutTavern> {
        goto(DialogWomanAnswer_InfoAboutTavern)
    }

    onResponse {
        random(
            { furhat.say("Could you speak up a bit! I can barely understand you.") },
            { furhat.say("You need to speak a bit clearer. I can not understand what you are saying.") },
            { furhat.say("What did you say?") }
        )
        reentry()
    }

    onNoResponse {
        random(
            { furhat.say("Hello? I am talking to you") },
            { furhat.say("Please answer me") },
            { furhat.say("Why don't you answer me?") }
        )
        reentry()
    }
}

val IntroDialogWoman: State = state(Interaction) {
    onEntry {
        if (users.current.visitedWoman != true) {
            furhat.say(utterance {
                +"You are approaching the woman who is leaning against a wall smoking. "
                +delay(100)
                +"As you take a closer look at her, you notice that she seems anxious, and is even shaking slightly. "
                +delay(100)
                +"Once she notices you, she seems freaked out. Maybe she knows something about what is going on in town, "
                +"or about the man you just saw entering."
            })
            goto(DialogWoman_1)
        } else {
            random(
                { furhat.say("As you approach the woman again, she spots you and rolls her eyes.") },
                { furhat.say("You approach the woman who is leaning against the wall once again. As she notices you, she seems upset.") })
            goto(DialogWoman_1)
        }
    }
}

val DialogWoman_1: State = state(parent = WomanOptions) {
    onEntry {
        // change voice and mask
        changeCharacter(furhat, StoryCharacter.ALLEY_WOMAN)
        delay(600)

        if (users.current.visitedWoman != true) {
            users.current.visitedWoman = true
            furhat.ask("Who are you? What do you want?")
        } else {
            random(
                { furhat.say(utterance {
                    + "Not you again!"
                    + blocking { furhat.gesture(Gestures.ExpressDisgust, async = false) } }) },
                { furhat.say(utterance {
                    + "It is you again!"
                    + blocking { furhat.gesture(Gestures.BrowFrown, async = false) } }) },
                { furhat.say(utterance {
                    + "Seriously, you again?"
                    + blocking { furhat.gesture(Gestures.BrowFrown, async = false) } }) }
            )
            goto(DialogWomanAnswer_1_preacherHint)
        }
    }

    onReentry {
        random(
            { furhat.ask(utterance {
                + "I don't think I can help you."
                +blocking { furhat.gesture(Gestures.ExpressFear, async = false)
                + "What do you want?" } }) },
            { furhat.ask("Why are you still here? What do you want?") })
    }

    onResponse(intent = NullIntent) {
        goto(DialogWomanAnswer_1_a)
    }
}

val DialogWomanAnswer_1_a: State = state(WomanOptions) {
    onEntry {
        furhat.ask(utterance {
            +"I cannot talk to you!"
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"You need to leave now! If they see me with you, we will both be in trouble!"
        })
    }

    onReentry {
        furhat.listen(3000)
    }

    onResponse(intent = NullIntent) {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}

val DialogWomanAnswer_1_preacherHint: State = state(WomanOptions) {
    onEntry {
        furhat.say(utterance {
            +"As I already said, I cannot tell you anything!"
            +blocking {
                furhat.gesture(Gestures.GazeAway, async = true)
            }
            +delay(200)
            +"Leave right now, or we will both be in danger! If you really want to know more, talk to the preacher in the town square."
        })
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say("As she seems reluctant to talk to you, you decide to leave her alone.")
        goto(AlleyIdle)
    }
}

val DialogWomanAnswer_Cop: State = state(DialogWomanAnswer_1_preacherHint) {
    onEntry {
        furhat.say(utterance {
            + blocking { furhat.gesture(Gestures.ExpressFear, async = false) }
            +"Look, you need to leave right now. If they find out you are with the watchmen, they will kill you!"
            +"If you really want to know more, talk to the preacher on the town square!"
            +"I cannot tell you anything more than that."
        })

        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say("As she seems reluctant to talk to you, you decide to leave her alone.")

        goto(AlleyIdle)
    }

    onReentry {
        furhat.listen(6000)
    }
}

val DialogWomanAnswer_TattooMan: State = state(WomanOptions) {
    onEntry {
        furhat.ask(utterance {
            +"I have seen no man!"
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"You need to leave now! If they see me with you, we will both be in trouble!"
        })
    }

    onReentry {
        furhat.listen(3000)
    }

    onResponse(intent = NullIntent) {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}

val DialogWomanAnswer_InfoAboutTavern: State = state(WomanOptions) {
    onEntry {
        furhat.ask(utterance {
            +"Look, this is not a good place."
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"If I were you, I would leave and NOT go in there!"
        })
    }

    onReentry {
        furhat.listen(3000)
    }

    onResponse(intent = NullIntent) {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}
