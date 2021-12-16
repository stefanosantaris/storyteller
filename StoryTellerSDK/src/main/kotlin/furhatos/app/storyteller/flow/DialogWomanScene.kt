package furhatos.app.storyteller.flow
import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures

val LeaveScene : State = state(Interaction){
    onButton("Leave and go into the tavern"){
        furhat.voice = PollyVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTavern> {
        furhat.voice = PollyVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onButton("Leave and go to the town square"){
        furhat.voice = PollyVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        random(
            {furhat.say("Could you speak up a bit! I can barely understand you.")},
            {furhat.say("Boy you need to speak a bit clearer. I can not understand what you are saying.")},
            {furhat.say("What did you say?")}
        )
        reentry()
    }

    onNoResponse {
        random(
            {furhat.say("Hello? I am talking to you")},
            {furhat.say("What is it?")},
            {furhat.say("Why don't you answer me?")}
        )
        reentry()
    }
}

val IntroDialogWoman : State = state(Interaction) {
    onEntry {
        furhat.say(utterance {
            +"You are approaching the woman who is leaning against a wall smoking. "
            +delay(100)
            +"As you take a closer look at her, you notice that she seems anxious. You even note that she is slightly shaking. "
            +delay(100)
            +"Once she notices you, she seems freaked out."
        })
        goto(DialogWoman_1)
    }
}

val  DialogWoman_1 : State = state(LeaveScene) {
    onEntry {
        // change voice and mask
        furhat.voice = PollyVoice.Raveena()
        furhat.setMask("adult")
        furhat.setCharacter("Isabel")

        delay(600)

        furhat.say(utterance {
            +blocking {
                furhat.gesture(Gestures.ExpressDisgust, async = false)
            }
            + "What do you want?"})
        furhat.ask("You need to leave!")
    }

    onReentry {
        random(
            {furhat.ask(utterance{
                + "Why don't you leave?"
                +blocking {
                    furhat.gesture(Gestures.ExpressDisgust, async = false)
                + "What do you want?"}})},
            {furhat.ask("Why are you still here? What do you want?")})
    }

    onButton("Calm down, I am a cop. Why are you so afraid?"){
        goto(DialogWomanAnswer_1_a)
    }

    onResponse<DialogWomanAnswer_1_a> {
        goto(DialogWomanAnswer_1_a)
    }

    onButton("Relax! I don’t want to harm you. What is this place here?"){
        goto(DialogWomanAnswer_1_preacherHint)
    }

    onResponse<DialogWomanAnswer_1_b> {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}


val  DialogWomanAnswer_1_a : State = state(LeaveScene) {
    onEntry {
        furhat.ask(utterance {
            +"You need to leave!"
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"If they see me with you, we will both be in trouble!"
        })
    }

    onButton("Who are them?"){
        goto(DialogWomanAnswer_1_preacherHint)
    }

    onResponse<DialogWomanAnswer_1_a_a> {
        goto(DialogWomanAnswer_1_preacherHint)
    }

    onReentry {
        furhat.listen(4000)
    }
}

val  DialogWomanAnswer_1_preacherHint : State = state(LeaveScene) {
    onEntry {
        furhat.ask(utterance {
            +"I cannot tell you!"
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"Leave right now, or we will both be in danger! If you really want to know more, talk to the preacher on the market"
        })
    }

    onReentry {
        furhat.listen(4000)
    }
}

