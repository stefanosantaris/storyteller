package furhatos.app.storyteller.flow

import furhatos.flow.kotlin.*

val OpeningScene : State = state(Interaction) {

    onEntry {
        users.current.hasPlayed = true
        furhat.say(dialogStrings["onEntry"]!!)
        goto(AlleyArrival)
    }
}

private val dialogStrings = mapOf(
        "onEntry" to
                "Recently there has been word of strange proceedings going on in the town of Millstone. " +
                "Rumors of an emerging cult has spread around the area, and as a detective of the county watchmen " +
                "you have been ordered to go there to investigate. Upon entering through the town gates, you see a man " +
                "with a strange mark tattooed on his forearm. Recognizing the mark from a conversation you had with the " +
                "sheriff before leaving, you decide to follow the man as he starts to make his way along the streets of " +
                "Millstone. After a while, you arrive at a dark alley where the man slips in to a door."
)