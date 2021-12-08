package furhatos.app.storyteller.flow

import furhatos.flow.kotlin.*

val TavernScene : State = state(Interaction) {

    onEntry {
        furhat.say()

    }
}


private val dialogStrings = mapOf(
        "onEntry" to
                ""
)