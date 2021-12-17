package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.EnterTavern
import furhatos.app.storyteller.nlu.LeaveToTownSquare
import furhatos.app.storyteller.nlu.TalkToWoman
import furhatos.flow.kotlin.*

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

val AlleyArrival = state(AlleyOptions) {
    onEntry {
        if (users.current.visitedAlley == false) {
            furhat.say(dialogStrings["onArrival1"]!!)
            furhat.say(dialogStrings["onArrival2"]!!)
            furhat.say(dialogStrings["onArrival3"]!!)
            users.current.visitedAlley = true
        } else {
            furhat.say(dialogStrings["onReArrival1"]!!)
            furhat.say(dialogStrings["onReArrival2"]!!)
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
                "Above the door where you say the man disappear there is a sign with the words \"The Hidden Goat Tavern\".",
        "onArrival2" to
                "Next to the door a woman is standing, looking around nervously.",
        "onArrival3" to
                "Up ahead you hear noises from a town square.",
        "onReArrival1" to
                "As you walk into the alley again, you still see the woman leaning against the tavern's wall.",
        "onReArrival2" to
                "Up ahead you still hear noises from a town square and \"The Hidden Goat Tavern\" is just in front of you."
)