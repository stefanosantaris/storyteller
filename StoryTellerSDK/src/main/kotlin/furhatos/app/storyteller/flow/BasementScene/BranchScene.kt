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
        furhat.say("You decide to take the left corridor with the words: \"West\", written above.")
        furhat.say("As you walk down the corridor, you notice that the corridor turns and twist a lot. After a short while, you get lost.")
        furhat.say("You try to go back to where you started, but suddenly you have the impression that the pathways changed.")
        furhat.say("Just before you were about to break down in despair, you reach the room with the three corridors where you started initially.")
        reentry()
    }

    onResponse<GoStraight> {
        furhat.say("You decide to take the corridor straight ahead with the words: \"North\", written above.")
        furhat.say("As you walk down the corridor, you see more words written on the corridor's walls and ceiling.")
        furhat.say("You decipher sentences like: \"Where the sun never sets\", and: \"There shall not be hope without light\".")
        furhat.say("A cold shiver overcomes you and you start to doubt whether this is the right direction to go.")

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
            furhat.say("After a while there are no words written on the corridor's walls anymore, but you notice that it is getting colder and colder as you continue walking.")
            furhat.say("In order to keep yourself warm, you decide to keep the torch a bit closer to your body.")
            furhat.say("Suddenly, you fall as you trip over a rope close to the ground. What you hear next, is a \"click\" sound, and you see the walls coming closer towards you.")
            furhat.say("You try to get up and start running back, but the walls are coming towards you too fast...")

            delay(600)
            furhat.say("Shortly after you started running, you die from the walls crushing you.")

            delay(1000)
            furhat.say("Unfortunately, you lost the game.")

            goto(Idle)
        }
    }

    onResponse<GoRight> {
        furhat.say("You decide to take the corridor to your right with the words: \"East\", written above.")
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
            "Brave as you are, you decide to enter the dark long corridor, equipped only with the torch the barmen handed you earlier.",
    "onArrival2" to
            "You notice that it gets colder as you continue to walk down the path and water is dripping down from the corridor's ceiling from time to time.",
    "onArrival3" to
            "After a while, you see that the corridor branches off into three new corridors, one to your left, one straight ahead and one to your right.",
    "onArrival4" to
            "On the ceiling you can see the words: \"Find us where the dawn is breaking\" written.",
    "onArrival5" to
            "Furthermore, you notice the words: \"West\", written over the corridor to your left: \"North\", written over the corridor that is straight ahead, and: \"East\", written over the corridor to you right."

)