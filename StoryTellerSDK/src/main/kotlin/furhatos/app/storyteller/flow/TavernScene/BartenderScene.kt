package furhatos.app.storyteller.flow.TavernScene

import furhatos.app.storyteller.flow.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures

val IntroBartender : State = state(Interaction) {
    onEntry {
        furhat.say(utterance {
            +"While approaching, the bartender critically examines you."
            +delay(100)
            +"He is a tall, muscular man with a wild beard and plenty of tattoos."
        })
        goto(DialogBartender_1)
    }
}

val DialogBartender_1 = state(parent = TavernOptions) {
    onEntry {
        // change voice and mask
        furhat.voice = PollyVoice.Russell()
        furhat.setCharacter("Brooklyn")

        delay(600)

        furhat.say(utterance {
            +blocking {
                furhat.gesture(Gestures.ExpressDisgust, async = false)
            }
            + "What do you want little rascal?"})
        furhat.ask("You don’t look like you should be in a bar like this. Why are you here?")
    }
}