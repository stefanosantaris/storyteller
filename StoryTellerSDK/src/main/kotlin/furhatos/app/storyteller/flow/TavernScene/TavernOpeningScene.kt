package furhatos.app.storyteller.flow

import furhatos.app.storyteller.flow.TavernScene.IntroBartender
import furhatos.app.storyteller.flow.TavernScene.IntroWhisperingMen
import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice

val TavernOptions = state(parent = Interaction) {
    onButton("Leave and go to the alley"){
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(AlleyArrival)
    }

    onResponse<LeaveToAlley> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(AlleyArrival)
    }

    onButton("Leave and go to the town square"){
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }
}

val TavernArrival = state(parent = TavernOptions) {
    onEntry {
        if (users.current.visitedTavern == false) {
            furhat.say(dialogStrings["onFirstArrival1"]!!)
            furhat.say(dialogStrings["onFirstArrival2"]!!)
            furhat.say(dialogStrings["onFirstArrival3"]!!)
            furhat.say(dialogStrings["onFirstArrival4"]!!)
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

    onResponse<TalkToBartender> {
        goto(IntroBartender)
    }

    onResponse<TalkToWhisperingMen> {
        goto(IntroWhisperingMen)
    }

    onResponse<LeaveToAlley> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(AlleyArrival)
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
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