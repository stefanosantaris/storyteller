package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.EnterTavern
import furhatos.app.storyteller.nlu.GoToTownSquare
import furhatos.app.storyteller.nlu.LeaveToTownSquare
import furhatos.app.storyteller.nlu.TalkToWoman
import furhatos.app.storyteller.utils.StoryCharacter
import furhatos.app.storyteller.utils.changeCharacter
import furhatos.app.storyteller.utils.getAskForActionPhrase
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users

val AlleyOptions: State = state(Interaction) {

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

fun alleyArrival(enteredFrom: EnteredAlleyFrom? = null): State = state(AlleyOptions) {
    onEntry {

        changeCharacter(furhat, StoryCharacter.NARRATOR)
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

        furhat.ask(getAskForActionPhrase())
    }

    onReentry {
        furhat.ask(getAskForActionPhrase())
    }
}

val AlleyIdle = state(parent = AlleyOptions) {

    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)

        furhat.say(dialogStrings["alleyIdle"]!!)
        furhat.ask(getAskForActionPhrase())
    }

    onReentry {
        furhat.ask(getAskForActionPhrase())
    }
}

private val dialogStrings = mapOf(
        "onArrival1" to
                "Above the door where you saw the man disappear, there is a sign with the words: \"The Hidden Goat Tavern\".",
        "onArrival2" to
                "Next to the door a woman is standing, looking around nervously.",
        "onArrival3" to
                "Up ahead, you hear noises from a town square.",
        "onReArrivalFromTavern1" to
                "As you walk back outside into the alley again, you see that the woman is still standing there, leaning against the wall.",
        "onReArrivalFromTavern2" to
                "Up ahead, you hear noises from a town square.",
        "onReArrivalFromTownSquare1" to
                "As you return to the alley, you see that the woman leaning against the tavern's wall is still there.",
        "onReArrivalFromTownSquare2" to
                "Next to her is the door to \"The Hidden Goat Tavern\", where you saw the strange man disappear.",
        "alleyIdle" to
                "Next to the woman is the door to \"The Hidden Goat Tavern\", where you saw the strange man disappear. " +
                "Up ahead you hear noises from a town square."
)

enum class EnteredAlleyFrom {
    TOWN_SQUARE, TAVERN
}