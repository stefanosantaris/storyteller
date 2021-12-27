package furhatos.app.storyteller.flow

import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val OpeningScene: State = state(Interaction) {
    onEntry {
        users.current.hasPlayed = true
        furhat.say(dialogStrings["onEntry1"]!!)
        furhat.say(dialogStrings["onEntry2"]!!)
        furhat.say(dialogStrings["onEntry3"]!!)
        furhat.say(dialogStrings["onEntry4"]!!)
        furhat.say(dialogStrings["onEntry5"]!!)
        goto(alleyArrival())
    }
}

private val dialogStrings = mapOf(
        "onEntry1" to
                "Recently there has been word of strange proceedings going on in the town of Millstone.",
        "onEntry2" to
                "Rumors of an emerging cult has spread around the area, and as a detective of the county watchmen " +
                "you have been ordered to go there to investigate.",
        "onEntry3" to
                "Upon entering through the town gates, you see a man with a strange mark tattooed on his forearm.",
        "onEntry4" to
                "Recognizing the mark from a conversation you had with the sheriff before leaving, you decide to follow the man " +
                "as he starts to make his way along the streets of Millstone.",
        "onEntry5" to
                "After a while, you arrive at a dark alley where the man slips in to a door."
)