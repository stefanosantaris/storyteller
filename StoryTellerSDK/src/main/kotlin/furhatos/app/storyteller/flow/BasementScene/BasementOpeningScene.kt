package furhatos.app.storyteller.flow.BasementScene

import furhatos.app.storyteller.flow.Interaction
import furhatos.app.storyteller.flow.TavernIdle
import furhatos.app.storyteller.flow.visitedBasement
import furhatos.app.storyteller.nlu.EnterCorridor
import furhatos.app.storyteller.nlu.GoBack
import furhatos.app.storyteller.utils.*
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent

/*
The user climbs down a steep wooden ladder into the tavern's basement.
 */
val BasementIntro = state(Interaction) {
    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        if (users.current.visitedBasement != true) {
            furhat.say(dialogStrings["onFirstArrival1"]!!)
            furhat.say(dialogStrings["onFirstArrival2"]!!)
            furhat.say(dialogStrings["onFirstArrival3"]!!)
            furhat.say(dialogStrings["onFirstArrival4"]!!)
            changeCharacter(furhat, StoryCharacter.BARTENDER)
            delay(600)
            furhat.say(dialogStrings["onFirstArrivalBartender"]!!)
            changeCharacter(furhat, StoryCharacter.NARRATOR)
            delay(600)
            furhat.say(dialogStrings["onFirstArrival5"]!!)
            users.current.visitedBasement = true
        } else {
            furhat.say(dialogStrings["onReArrival"]!!)
        }
        furhat.ask(getAskForActionPhrase())
    }

    onReentry {
        furhat.ask(getAskForActionPhrase())
    }

    onResponse<EnterCorridor> {
        goto(BranchOffScene)
    }

    onResponse<GoBack> {
        random(
            {furhat.say("You decide to climb the ladder back up again.")},
            {furhat.say("As the fear comes over you, you decide to follow the barmen back up again.")}
        )
        goto(TavernIdle)
    }

    onNoResponse {
        furhat.ask(getDidNotHearPhrase())
        reentry()
    }

    onResponse(intent = NullIntent) {
        furhat.say(getDidNotUnderstandPhrase())
        reentry()
    }
}

private val dialogStrings = mapOf(
    "onFirstArrival1" to
            "The barman directs you to follow him down a steep wooden ladder into the tavern's basement.",
    "onFirstArrival2" to
            "As you reach the end of the ladder, you see a room full of beer barrels and other supplies. " +
            "Although the basement is quite dark, you cannot find anything suspicious about this place.",
    "onFirstArrival3" to
            "Suddenly, the barman pulls on a torch that is connected to one of the basement's walls, " +
            "and a shelf full of supplies swings wide open. Behind the shelf, you can see a long corridor leading into the dark.",
    "onFirstArrival4" to
            "The barman hands you the torch and says:",
    "onFirstArrivalBartender" to
            "Remember my friend, dawn is breaking. Galos has awakened.",
    "onFirstArrival5" to
            "Then, the barman makes his way up the wooden ladder again. You look into the dark corridor and wonder whether or not you should enter it.",
    "onReArrival" to
            "You climb down the steep ladder into the basement again and pull on the torch that is connected to the basement's wall. " +
            "As the shelf swings wide open, you stand in front of the dark corridor."
)