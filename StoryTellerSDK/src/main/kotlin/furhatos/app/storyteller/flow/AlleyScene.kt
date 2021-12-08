package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.EnterTavern
import furhatos.app.storyteller.nlu.GoToTownSquare
import furhatos.app.storyteller.nlu.TalkToWoman
import furhatos.flow.kotlin.*

val AlleyScene : State = state(Interaction) {

    onEntry {
        furhat.say(dialogStrings["onEntry"]!!)
        furhat.ask("What do you do?")
    }

    onResponse<GoToTownSquare> {
        goto(TownSquareScene)
    }

    onResponse<EnterTavern> {
        goto(TavernScene)
    }

    onResponse<TalkToWoman> {
        goto(TalkingToWoman)
    }

}

val TalkingToWoman = state(parent = AlleyScene) {
    onEntry {

    }
}

private val dialogStrings = mapOf(
        "onEntry" to
                "Above the door where you say the man disappear there is a sign with the words " +
                "\"The Hidden Goat Tavern\". Next to the door a woman is standing, looking around nervously. " +
                "Up ahead you hear noises from a town square."
)