package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*

val TownSquareScene : State = state(Interaction) {

    onEntry {
        furhat.say(dialogStrings["onEntry"]!!)
        furhat.ask("What do you do?")
    }

    onResponse<TalkToJester> {
        goto(TalkingToJester)
    }

    onResponse<TalkToMerchant> {
        goto(TalkingToMerchant)
    }

    onResponse<ListenToPreacher> {
        goto(ListenToPreacher)
    }

    onResponse<GoToAlley> {
        goto(AlleyScene)
    }

}

val TalkingToJester = state(parent = TownSquareScene) {


}

val TalkingToMerchant = state(parent = TownSquareScene) {

}

val ListenToPreacher = state(parent = TownSquareScene) {

}

private val dialogStrings = mapOf(
        "onEntry" to
                "You approach the town square and find it to be quite busy with people. " +
                "Upon entering you notice a preacher standing in a corner speaking to a small crowd " +
                "and a woman selling potions in a market stand. A jester is trying to perform a show " +
                "on the far side of the square. However, no one seems to be watching."
)
