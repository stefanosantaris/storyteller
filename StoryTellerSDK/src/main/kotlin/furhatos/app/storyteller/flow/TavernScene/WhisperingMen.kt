package furhatos.app.storyteller.flow.TavernScene

import furhatos.app.storyteller.flow.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures

val IntroWhisperingMen : State = state(Interaction) {
    onEntry {
        furhat.say(utterance {
            +"While you approach the two people sitting in their booth, they immediately stop talking and look suspiciously at you."
            +delay(100)
            +"Once you have arrived at their table, one of the men says:"
        })
        goto(DialogWhiserpingMen_1)
    }
}

val DialogWhiserpingMen_1 = state(parent = TavernOptions) {
    onEntry {
        // change voice and mask
        furhat.voice = PollyNeuralVoice.Brian()
        furhat.setCharacter("Fedora")

        delay(600)

        furhat.say(utterance {
            + "What do you want?"
            +blocking {
                furhat.gesture(Gestures.ExpressDisgust, async = false)
            }})
        furhat.ask("Can't you see that we are talking?")
    }
}