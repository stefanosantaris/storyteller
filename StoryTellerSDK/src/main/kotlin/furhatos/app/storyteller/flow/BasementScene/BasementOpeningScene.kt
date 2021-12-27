package furhatos.app.storyteller.flow.BasementScene

import furhatos.app.storyteller.flow.Interaction
import furhatos.flow.kotlin.*



val BasementOptions = state(parent = Interaction) {

    onResponse<ChooseWest> {
        goto(alleyArrival(EnteredAlleyFrom.TAVERN))
    }

    onResponse<ChooseNorth> {
        goto(IntroBartender)
    }

    onResponse<ChooseEast> {
        goto(IntroWhisperingMen)
    }
}

val BasementArrival = state(parent = TavernOptions) {
    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(300)
        if (users.current.visitedTavern != true) {
            furhat.say(dialogStrings["onFirstArrival1"]!!)
            furhat.say(dialogStrings["onFirstArrival2"]!!)
            furhat.say(dialogStrings["onFirstArrival3"]!!)
            furhat.say(dialogStrings["onFirstArrival4"]!!)
            users.current.visitedTavern = true
        } else {
            furhat.say(dialogStrings["onReArrival"]!!)
        }
        furhat.ask(getAskForActionPhrase())
    }

    onReentry {
        furhat.ask(getAskForActionPhrase())
    }


    onResponse(intent = NullIntent) {
        furhat.say(getDidNotUnderstandPhrase())
        furhat.ask(getAskForActionPhrase())
        reentry()
    }

    onNoResponse {
        furhat.ask(getAskForActionPhrase())
        reentry()
    }
}

private val dialogStrings = mapOf(
    "onFirstArrival1" to
            "You decide to enter the tavern with the name \"The Hidden Goat Tavern\".",
    "onFirstArrival2" to
            "As you open the heavy wooden tavern's door, the smell of " +
            "smoke and beer hits you. You briefly scan the room for the man with the weird tattoo on his arm, but you cannot find him.",
    "onFirstArrival3" to
            "Instead, you see two shady looking men whispering to each other at one of the tavern's tables.",
    "onFirstArrival4" to
            "Behind the bar, a big dangerous looking man is observing you, while he pretends to clean the counter.",
    "onReArrival" to
            "You enter \"The Hidden Goat Tavern\". Back in the bar, the bartender is still looking at you grimly, while the two men in the corner are still " +
            "whispering with each other."
)