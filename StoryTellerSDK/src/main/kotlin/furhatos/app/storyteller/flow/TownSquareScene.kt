package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.*
import furhatos.app.storyteller.utils.JokeManager
import furhatos.app.storyteller.utils.NoMoreJokesException
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val TownSquareOptions : State = state(Interaction) {

    onResponse<TalkToJester> {
        goto(TalkingToJester)
    }

    onResponse<TalkToMerchant> {
        goto(TalkingToMerchant)
    }

    onResponse<ListenToPreacher> {
        goto(ListeningToPreacher)
    }

    onResponse<LeaveToAlley> {
        goto(alleyArrival(EnteredAlleyFrom.TOWN_SQUARE))
    }

}

val TownSquareIdle = state(parent = TownSquareOptions) {

    onEntry {
        val notVisited = setOf(
                Interactions.JESTER,
                Interactions.MERCHANT,
                Interactions.PREACHER).minus(visited).toList()

        val optionStrings = notVisited.map { it.name.toLowerCase() }

        val optionPresentation = when(notVisited.size) {
            3 -> "There is a ${optionStrings[0]}, a ${optionStrings[1]} and a ${optionStrings[2]} in the square. "
            2 -> "There is also a ${optionStrings[0]} and a ${optionStrings[1]} in the square. "
            1 -> "There is also a ${optionStrings[0]} in the square. "
            else -> ""
        }.plus("Behind you is the alley where you were before.")

        furhat.say(optionPresentation)
        furhat.ask("What do you do?")
    }

    onReentry {
        furhat.ask("What do you do?")
    }

}

val TownSquareArrival = state(parent = TownSquareOptions) {

    onEntry {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.character = "Jamie"
        delay(600)

        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }

    onReentry {
        furhat.ask("What do you do?")
    }

    onResponse <PleaseRepeat> {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }
}

val TalkingToJester = state(parent = TownSquareOptions) {

    onEntry {
        furhat.say(dialogStrings["jesterOnEntry"]!!)
        furhat.ask("\"An audience! How about a joke or two?\"")
    }

    onReentry {
        furhat.ask("\"How about a joke or two?\"")
    }

    onResponse <PleaseRepeat> {
        furhat.say(dialogStrings["jesterOnEntry"]!!)
        furhat.ask("\"An audience! How about a joke or two?\"")
    }

    onResponse<Yes> {

        try {
            val joke = jokeManager.getNextJoke()
            furhat.say(joke)
            if (jokeManager.hasMoreJokes()) {
                furhat.ask("How about another one?")
            } else {
                furhat.say("That was my last one!")
                jokeManager.reset()
                visited.add(Interactions.JESTER)
                goto(TownSquareIdle)
            }

        } catch (e: NoMoreJokesException) {
            furhat.say("I am afraid that I am all out of jokes!")
            visited.add(Interactions.JESTER)
            goto(TownSquareIdle)
        }

    }

    onResponse<No> {
        furhat.say("Tis' a pity! Fare thee well!")
        jokeManager.reset()
        visited.add(Interactions.JESTER)
        goto(TownSquareIdle)
    }

}


val TalkingToMerchant = state(parent = TownSquareOptions) {

    onEntry {
        furhat.say(dialogStrings["merchantOnEntry"]!!)
        furhat.ask("Would you like to buy something?")
    }

    onReentry {
        furhat.ask("Would you like to buy something?")
    }

    onResponse <PleaseRepeat> {
        furhat.say(dialogStrings["merchantOnEntry"]!!)
        furhat.ask("Would you like to buy something?")
    }

    onResponse<No> {
        furhat.say("Get going then.")
        visited.add(Interactions.MERCHANT)
        goto(TownSquareIdle)
    }

    onResponse<Yes> {
        furhat.say(dialogStrings["buyFromMerchant"]!!)
        visited.add(Interactions.MERCHANT)
        goto(TownSquareIdle)
    }

    onResponse<AskMerchantAboutCult> {
        furhat.say("Don't ask me anything more! If you really have to know, talk to the" +
                "man preaching over there!")
        furhat.ask("Will you buy something or not?")
    }

    onResponse<AskAboutBrother> {
        furhat.say("I cannot tell you more... just let me know if you see him, okay?")
        furhat.say("Will you buy something or not? I don't have all day.")
    }
}

val ListeningToPreacher = state(parent = TownSquareOptions) {

    onEntry {
        furhat.say(dialogStrings["preacherOnEntry"]!!)
        furhat.ask("Are you a believer? A child of Xoros?")
    }

    onReentry {
        furhat.ask("Are you a believer? A child of Xoros?")
    }

    onResponse <PleaseRepeat> {
        furhat.say(dialogStrings["preacherOnEntry"]!!)
        furhat.ask("Are you a believer? A child of Xoros?")
    }

    onResponse<Yes> {
        furhat.say("I knew it! I could see it in your eyes!")
        furhat.say(dialogStrings["receivePassword"]!!)
        visited.add(Interactions.PREACHER)
        goto(TownSquareIdle)
    }

    onResponse<No> {
        furhat.say("It matters not. In time you will inevitably reckon the greatness of our lord.")
        furhat.say(dialogStrings["receivePassword"]!!)
        visited.add(Interactions.PREACHER)
        goto(TownSquareIdle)
    }

    onResponse<RequestGodExplanation> {
        furhat.say(dialogStrings["godExplanation"]!!)
        furhat.ask("I ask you again, are you a follower?")
    }

}


private val dialogStrings = mapOf(
        "onArrival" to
                "You approach the town square and find it to be quite busy with people. " +
                "Upon entering you notice a preacher standing in a corner speaking to a small crowd " +
                "and a woman selling food in a market stand. A jester is trying to perform a show " +
                "on the far side of the square. However, no one seems to be watching.",
        "jesterOnEntry" to
                "The jester is juggling with some kegels but seems to keep dropping them. It becomes obvious " +
                "why he has not drawn a crowd. He sees you approaching and puts the kegels down. ",
        "preacherOnEntry" to
                "The preacher is speaking about a new god, and the power that comes from worshiping him. " +
                "With him, he has a man and a woman that he claims to have cured from blindness and sickness. " +
                "He notices you in the crowd and turns to you.",
        "godExplanation" to
                "Have you not heard? Is your mind still shrouded in darkness? Our lord Xoros has illuminated " +
                "the chosen people of Millstone with his power.",
        "receivePassword" to
                "He moves closer to you and whispers in your ear, too quietly for anyone else to hear. There is a gathering tonight. " +
                "Go to the Hidden Goat Tavern and tell the man behind the bar the following. \"Dawn is breaking\". There you will find out more.",
        "merchantOnEntry" to
                "You approach the merchant stand and find that a young woman is standing there selling meat and cheese. She looks at " +
                "you with despair in her eyes. \"You haven't seen my brother, have you?\", she asks. \"He has been gone for several days " +
                "and I am beginning to worry... I wish he never would have gotten involved with those damn cultists!\". " +
                "She suddenly looks startled, as if she had just caught herself saying something forbidden. " +
                "\"Never mind anything that I said!\"",
        "buyFromMerchant" to
                "Here you go, some vension and a wheel of cheddar. Have a good day. You pay the merchant."
)


private val jokeManager = JokeManager()

private enum class Interactions {
    JESTER, MERCHANT, PREACHER
}

private val interactionStrings = mapOf(
        Interactions.JESTER to "jester",
        Interactions.MERCHANT to "merchant",
        Interactions.PREACHER to "preacher"
)

private val visited = mutableSetOf<Interactions>()
