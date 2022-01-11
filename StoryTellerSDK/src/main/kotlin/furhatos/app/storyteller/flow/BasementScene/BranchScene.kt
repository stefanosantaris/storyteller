package furhatos.app.storyteller.flow.BasementScene

import furhatos.app.storyteller.flow.Idle
import furhatos.app.storyteller.flow.Interaction
import furhatos.app.storyteller.flow.TavernArrival
import furhatos.app.storyteller.flow.visitedBasement
import furhatos.app.storyteller.nlu.*
import furhatos.app.storyteller.utils.*
import furhatos.flow.kotlin.*
import furhatos.nlu.NullIntent

/*
The user enters the corridor and comes to a branch-off where he/she can decide between three directions to go
 */
val BranchOffScene = state(Interaction) {
    onEntry {
        furhat.say(dialogStrings["onArrival1"]!!)
        furhat.say(dialogStrings["onArrival2"]!!)
        furhat.say(dialogStrings["onArrival3"]!!)
        furhat.say(dialogStrings["onArrival4"]!!)
        furhat.say(dialogStrings["onArrival5"]!!)

        furhat.ask("Which corridor do you decide to enter?")
    }

    onReentry {
        random(
            {furhat.ask("Where would you like to go?")},
            {furhat.ask("Which corridor would you like to enter?")},
            {furhat.ask("Which corridor do you decide to enter?")}
        )
    }

    onResponse<GoLeft> {
        furhat.say("You decide to take the left corridor with the word \"West\" written above it.")
        furhat.say("As you walk down the corridor, you notice that it turns and twist a lot. After a short while, you get lost.")
        furhat.say("You try to go back to where you started, but suddenly you have the impression that the pathway has changed.")
        furhat.say("Just when you were about to break down in despair, you reach the room with the three corridors where you started initially.")
        reentry()
    }

    onResponse<GoStraight> {
        furhat.say("You decide to take the corridor straight ahead with the words \"North\" written above it.")
        furhat.say("As you walk down the corridor, you see more words written on the walls and in the ceiling.")
        furhat.say("You decipher sentences like: \"Where the sun never sets\", and: \"There shall be no hope without light\".")
        furhat.say("A cold shiver overcomes you and you start to doubt whether this was the right direction to go.")

        val turnAround: Boolean? = furhat.askYN(utterance {
            +delay(100)
            +"Do you want to go back?"
        })

        if (turnAround == true) {
            furhat.say("You decide to turn around and head back.")
            furhat.say("After a short while, you see the room with the three corridors where you started initially.")
            reentry()

        } else {
            furhat.say("You decide to continue on the current path.")
            furhat.say("After a while there are no words written on the corridor walls anymore, but you notice that it is getting colder and colder as you continue.")
            furhat.say("In order to keep yourself warm, you decide to hold the torch a bit closer to your body.")
            furhat.say("Suddenly, you fall to the ground as you trip over a rope down by your ankles. What you hear next, is a \"click\" sound, and you see the walls closing in towards you.")
            furhat.say("You try to get up and start running back, but the walls are coming towards you too fast...")

            delay(600)
            furhat.say("Shortly after getting back on your feet, you die from the walls crushing you.")

            delay(1000)
            furhat.say("Unfortunately, you lost the game.")

            goto(Idle)
        }
    }

    onResponse<GoRight> {
        furhat.say("You decide to take the corridor to your right with the word \"East\" written above it.")
        goto(RitualEndScene)
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
    "onArrival1" to
            "Brave as you are, you decide to enter the dark long corridor, equipped only with the torch that the barman handed you earlier.",
    "onArrival2" to
            "You notice that it gets colder as you continue to walk down the path, and water is dripping down from the corridor's ceiling from time to time.",
    "onArrival3" to
            "After a while, you see that the corridor branches off into three new paths: one to your left, one straight ahead and one to your right.",
    "onArrival4" to
            "In the ceiling you can see the words: \"Find us where the dawn is breaking\" written.",
    "onArrival5" to
            "Furthermore, you notice the words: \"West\", written over the corridor to your left: \"North\", written over the corridor that is straight ahead, and: \"East\", written over the corridor to you right."

)