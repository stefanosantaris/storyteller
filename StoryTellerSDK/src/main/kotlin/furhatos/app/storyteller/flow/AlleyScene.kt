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
        goto(IntroDialogWoman)
    }
}

val AlleyArrival = state(AlleyOptions) {
    onEntry {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }

    onReentry {
        random(
            {furhat.ask("What would you like to do?")},
            {furhat.ask("What do you do?")},
            {furhat.ask("What do you plan to do?")}
        )
    }
}

private val dialogStrings = mapOf(
        "onArrival" to
                "Above the door where you say the man disappear there is a sign with the words " +
                "\"The Hidden Goat Tavern\". Next to the door a woman is standing, looking around nervously. " +
                "Up ahead you hear noises from a town square."
)