package furhatos.app.storyteller.flow

import furhatos.app.storyteller.flow.TavernScene.IntroBartender
import furhatos.app.storyteller.flow.TavernScene.IntroWhisperingMen
import furhatos.app.storyteller.nlu.LeaveToAlley
import furhatos.app.storyteller.nlu.TalkToBartender
import furhatos.app.storyteller.nlu.TalkToWhisperingMen
import furhatos.app.storyteller.utils.StoryCharacter
import furhatos.app.storyteller.utils.changeCharacter
import furhatos.app.storyteller.utils.getAskForActionPhrase
import furhatos.app.storyteller.utils.getDidNotUnderstandPhrase
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.utterance
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.nlu.NullIntent

val TavernOptions = state(parent = Interaction) {

    onResponse<LeaveToAlley> {
        goto(alleyArrival(EnteredAlleyFrom.TAVERN))
    }

    onResponse<TalkToBartender> {
        goto(IntroBartender)
    }

    onResponse<TalkToWhisperingMen> {
        goto(IntroWhisperingMen)
    }
}

val TavernArrival = state(parent = TavernOptions) {
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

val TavernIdle = state(parent = TavernOptions) {

    onEntry {
        val whisperingMenString =
                if (users.current.talkedToWhisperingMen != true)
                    "There are two shady looking men sitting in a corner, whispering to each other."
                else
                    "The two men are still sitting at their table, throwing suspicious glances at you."
        val bartenderString =
                if (users.current.talkedToBartender != true)
                    "Behind the bar, a big dangerous looking man is observing you, while he pretends to clean the counter."
                else
                    "The bartender is looking at you with disgust." // TODO: Change if you can win bartenders trust
        val doorString = "Behind you is the door to the alley."

        furhat.say(utterance {
            +whisperingMenString
            +bartenderString
            +doorString
        })
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