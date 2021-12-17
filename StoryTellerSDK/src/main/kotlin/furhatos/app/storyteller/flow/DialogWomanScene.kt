package furhatos.app.storyteller.flow
import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures

val LeaveScene : State = state(Interaction){
    onButton("Leave and go into the tavern"){
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTavern> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onButton("Leave and go to the town square"){
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        random(
            {furhat.say("Could you speak up a bit! I can barely understand you.")},
            {furhat.say("You need to speak a bit clearer. I can not understand what you are saying.")},
            {furhat.say("What did you say?")}
        )
        reentry()
    }

    onNoResponse {
        random(
            {furhat.say("Hello? I am talking to you")},
            {furhat.say("Please answer me")},
            {furhat.say("Why don't you answer me?")}
        )
        reentry()
    }
}

val IntroDialogWoman : State = state(Interaction) {
    onEntry {
        if (users.current.visitedWoman == false) {
            furhat.say(utterance {
                +"You are approaching the woman who is leaning against a wall smoking. "
                +delay(100)
                +"As you take a closer look at her, you notice that she seems anxious. You even note that she is slightly shaking. "
                +delay(100)
                +"Once she notices you, she seems freaked out."
            })
            goto(DialogWoman_1)
        } else {
            random(
                {furhat.say("As you approach the woman again, she spots you and rolls her eyes.")},
                {furhat.say("You again approach the woman who is leaning against the wall. As she notices you, she seems upset.")})
            goto(DialogWoman_1)
        }
    }
}

val  DialogWoman_1 : State = state(parent = LeaveScene) {
    onEntry {
        // change voice and mask
        furhat.voice = PollyNeuralVoice.Kimberly()
        furhat.setMask("adult")
        furhat.setCharacter("Isabel")

        delay(600)

        if (users.current.visitedWoman == false) {
            users.current.visitedWoman = true
            furhat.say(utterance {
                +blocking {
                    furhat.gesture(Gestures.GazeAway, async = false)
                }
                +"What do you want?"
            })
            furhat.ask("You need to leave!")
        } else {
            random(
                {furhat.say(utterance {
                    + "Not you again!"
                    + blocking {furhat.gesture(Gestures.ExpressDisgust, async = false)}})},
                {furhat.say(utterance {
                    + "It is you again!"
                    + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}})},
                {furhat.say(utterance {
                    + "Seriously, you again?"
                    + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}})}
            )
            goto(DialogWomanAnswer_1_preacherHint)
        }
    }

    onReentry {
        random(
            {furhat.ask(utterance{
                + "Why don't you leave?"
                +blocking {furhat.gesture(Gestures.ExpressFear, async = false)
                + "What do you want?"}})},
            {furhat.ask("Why are you still here? What do you want?")})
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

    onResponse<LeaveToTavern> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        goto(DialogWomanAnswer_1_a)
    }
}


val  DialogWomanAnswer_1_a : State = state(LeaveScene) {
    onEntry {
        furhat.ask(utterance {
            +"I can not talk to you!"
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"You need to leave now! If they see me with you, we will both be in trouble!"
        })
    }

    onReentry {
        furhat.listen(6000)
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

    onResponse<LeaveToTavern> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}

val  DialogWomanAnswer_1_preacherHint : State = state(LeaveScene) {
    onEntry {
        furhat.ask(utterance {
            +"As I already told you, I cannot tell you anything!"
            +blocking {
                furhat.gesture(Gestures.GazeAway, async = false)
            }
            +"Leave right now, or we will both be in danger! If you really want to know more, talk to the preacher at the town square."
        })
    }

    onReentry {
        furhat.listen(6000)
    }

    onNoResponse {
        random(
            {furhat.say("Please go now!")},
            {furhat.say("You need to leave, now!")},
            {furhat.say("Why are you still here? You need to leave. Now!")})
        reentry()
    }

    onResponse<LeaveToTavern> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        random(
            {furhat.say(utterance{
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "Please go now!"})},
            {furhat.say(utterance{
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "You need to leave, now!"})},
            {furhat.say(utterance{
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "Why are you still here? You need to leave. Now!"})})
        reentry()
    }
}

val DialogWomanAnswer_Cop : State = state(DialogWomanAnswer_1_preacherHint) {
    onEntry {
        furhat.ask(utterance {
            + blocking { furhat.gesture(Gestures.ExpressFear, async = false) }
            +"Look, you need to leave right now. If they find out you are a cop, they will kill you!"
            +"If you really want to know more, talk to the preacher on the town square!"
        })
    }
}

val  DialogWomanAnswer_TattooMan : State = state(LeaveScene) {
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
        furhat.listen(6000)
    }

    onResponse<IamCop> {
        goto(DialogWomanAnswer_Cop)
    }

    onResponse<AskAboutTavern> {
        goto(DialogWomanAnswer_InfoAboutTavern)
    }

    onResponse<LeaveToTavern> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}

val  DialogWomanAnswer_InfoAboutTavern : State = state(LeaveScene) {
    onEntry {
        furhat.ask(utterance {
            +"Look, this place is not a good place."
            +blocking {
                furhat.gesture(Gestures.Shake, async = false)
            }
            +"If I were you, I would leave and NOT go in there!"
        })
    }

    onReentry {
        furhat.listen(6000)
    }

    onResponse<IamCop> {
        goto(DialogWomanAnswer_Cop)
    }

    onResponse<FollowMan> {
        goto(DialogWomanAnswer_TattooMan)
    }

    onResponse<LeaveToTavern> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TavernArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        goto(DialogWomanAnswer_1_preacherHint)
    }
}


