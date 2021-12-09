package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.EnterTavern
import furhatos.app.storyteller.nlu.GoToTownSquare
import furhatos.app.storyteller.nlu.TalkToWoman
import furhatos.flow.kotlin.*

val AlleyOptions : State = state(Interaction) {

    onResponse<GoToTownSquare> {
        goto(TownSquareArrival)
    }

    onResponse<EnterTavern> {
        goto(TavernArrival)
    }

    onResponse<TalkToWoman> {
        goto(TalkingToWoman)
    }

}

val AlleyArrival = state(parent = AlleyOptions) {
    onEntry {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }
}

val TalkingToWoman = state(parent = AlleyOptions) {
    onEntry {

    }
}

private val dialogStrings = mapOf(
        "onArrival" to
                "Above the door where you say the man disappear there is a sign with the words " +
                "\"The Hidden Goat Tavern\". Next to the door a woman is standing, looking around nervously. " +
                "Up ahead you hear noises from a town square."
)