package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val TownSquareOptions : State = state(Interaction) {

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
        goto(AlleyArrival)
    }

}

val TownSquareArrival = state(parent = TownSquareOptions) {
    onEntry {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }
}

val TalkingToJester = state(parent = TownSquareOptions) {

    onEntry {
        furhat.say(dialogStrings["jesterOnEntry"]!!)
        furhat.ask("Good sir! How about a joke or two?")
    }

    onResponse<Yes> {

    }

    onResponse<No> {

    }


}

val TalkingToMerchant = state(parent = TownSquareOptions) {

}

val ListenToPreacher = state(parent = TownSquareOptions) {

}

private val dialogStrings = mapOf(
        "onArrival" to
                "You approach the town square and find it to be quite busy with people. " +
                "Upon entering you notice a preacher standing in a corner speaking to a small crowd " +
                "and a woman selling potions in a market stand. A jester is trying to perform a show " +
                "on the far side of the square. However, no one seems to be watching.",
        "jesterOnEntry" to
                "The jester is juggling with some kegels but seems to keep dropping them. It becomes obvious " +
                "why he has not drawn a crowd. He sees you approaching and puts the kegels down."
)