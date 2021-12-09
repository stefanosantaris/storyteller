package furhatos.app.storyteller.flow

import furhatos.flow.kotlin.*

val TavernOptions : State = state(Interaction) {

    onEntry {
        furhat.say()

    }
}

val TavernArrival = state(parent = TavernOptions) {
    onEntry {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }
}


private val dialogStrings = mapOf(
        "onArrival" to
                ""
)