package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.EnterTavern
import furhatos.app.storyteller.nlu.LeaveToTownSquare
import furhatos.app.storyteller.nlu.TalkToWoman
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice

val AlleyOptions : State = state(Interaction) {

    onResponse<LeaveToTownSquare> {
        goto(TownSquareArrival)
    }

    onResponse<EnterTavern> {
        goto(TavernArrival)
    }

    onResponse<TalkToWoman> {
        goto(IntroDialogWoman)
    }
}

fun alleyArrival(enteredFrom: EnteredAlleyFrom? = null): State = state(AlleyOptions) {
    onEntry {

        furhat.voice = PollyNeuralVoice.Joey()
        furhat.character = "Jamie"
        delay(600)

        when (enteredFrom) {
            EnteredAlleyFrom.TAVERN -> {
                furhat.say(dialogStrings["onReArrivalFromTavern1"]!!)
                furhat.say(dialogStrings["onReArrivalFromTavern2"]!!)
            }
            EnteredAlleyFrom.TOWN_SQUARE -> {
                furhat.say(dialogStrings["onReArrivalFromTownSquare1"]!!)
                furhat.say(dialogStrings["onReArrivalFromTownSquare2"]!!)
            }
            else -> {
                furhat.say(dialogStrings["onArrival1"]!!)
                furhat.say(dialogStrings["onArrival2"]!!)
                furhat.say(dialogStrings["onArrival3"]!!)
                users.current.visitedAlley = true
            }
        }

        random(
            {furhat.ask("What would you like to do?")},
            {furhat.ask("What do you do?")},
            {furhat.ask("What do you plan to do?")}
        )
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
        "onArrival1" to
                "Above the door where you saw the man disappear there is a sign with the words \"The Hidden Goat Tavern\".",
        "onArrival2" to
                "Next to the door a woman is standing, looking around nervously.",
        "onArrival3" to
                "Up ahead you hear noises from a town square.",
        "onReArrivalFromTavern1" to
                "As you walk back outside into the alley again, you see that the woman is still standing there, leaning against the wall.",
        "onReArrivalFromTavern2" to
                "Up ahead you hear noises from a town square.",
        "onReArrivalFromTownSquare1" to
                "As you return to the alley, you see that the woman leaning against the tavern's wall is still there.",
        "onReArrivalFromTTownSquare2" to
                "Next to her is the door to \"The Hidden Goat Tavern\", where you saw the strange man disappear."
)

enum class EnteredAlleyFrom {
    TOWN_SQUARE, TAVERN
}