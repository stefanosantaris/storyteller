package furhatos.app.storyteller.flow

import furhatos.flow.kotlin.*

val TavernArrival = state(parent = Interaction) {
    onEntry {
        if (users.current.visitedTavern == false) {
            furhat.say(dialogStrings["onFirstArrival1"]!!)
            furhat.say(dialogStrings["onFirstArrival2"]!!)
            users.current.visitedTavern = true
        } else {
            furhat.say(dialogStrings["onReArrival"]!!)
        }
        random(
            {furhat.ask("What do you intend to do?")},
            {furhat.ask("What would you like to do?")},
            {furhat.ask("What do you do?")})
    }

    onReentry {
        random(
            {furhat.ask("What do you intend to do?")},
            {furhat.ask("What would you like to do?")},
            {furhat.ask("What do you do?")})
    }

    onResponse{
        random(
            {furhat.say("Sorry, I didn't understand that.")},
            {furhat.say("Sorry, I didn't get that.")},
            {furhat.say("Sorry could you repeat that?")}
        )
        random(
            {furhat.ask("What do you intend to do?")},
            {furhat.ask("What would you like to do?")},
            {furhat.ask("What do you do?")})
        reentry()
    }

    onNoResponse {
        random(
            {furhat.ask("What do you intend to do?")},
            {furhat.ask("What would you like to do?")},
            {furhat.ask("What do you do?")})
        reentry()
    }
}


private val dialogStrings = mapOf(
        "onFirstArrival1" to
                "You decide to enter the tavern with the name \"\"The Hidden Goat Tavern\". As you open the heavy wooden tavern's door, the smell of " +
                "smoke and beer hits you. You briefly scan the room for the man with the weird tattoo on his arm, but you cannot find him.",
        "onFirstArrival2" to
                "Instead, you see two shady looking men whispering to each other at one of the taverns tables. Behind the bar, a big dangerous looking man is " +
                "observing you, while he pretends to clean counter.",
        "onReArrival" to
                "You enter \"\"The Hidden Goat Tavern\". The bartender is still looking at you grimly, while the two men in the corner are still " +
                "whispering with each other."
)