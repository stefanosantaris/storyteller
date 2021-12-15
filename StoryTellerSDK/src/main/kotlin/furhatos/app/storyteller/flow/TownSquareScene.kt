package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*
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

    onResponse<GoToAlley> {
        goto(AlleyArrival)
    }

}

val TownSquareArrival = state(parent = TownSquareOptions) {
    onEntry {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask("What do you do?")
    }
}

val TalkingToJester = state(parent = TownSquareOptions) {

    var jokeCounter = 0
    val jokes = listOf(
            "Why was the knight fighting the tournament with a " +
                    "sword made from cheddar cheese? " +
                    "Because the cheese was extra sharp!",
            "What did the king say when he heard that the peasants were revolting? " +
                    "He said he agrees because they never bathe and always stink.",
            "Why was the court jester almost executed? " +
                    "Because the Queen got the joke at the last moment!"
    )



    onEntry {
        furhat.say(dialogStrings["jesterOnEntry"]!!)
        furhat.ask("An audience! How about a joke or two?")
    }

    onResponse<Yes> {

        if (jokeCounter > jokes.size) {
            furhat.say("I am afraid that I am all out of jokes!")
            furhat.ask("What do you do?")
        }

        furhat.say(jokes[jokeCounter])
        jokeCounter += 1

        if (jokeCounter < jokes.size) {
            furhat.ask("How about another one?")
        } else {
            furhat.say("That was my last one!")
            furhat.ask("What do you do?")
        }

    }

    onResponse<No> {
        furhat.say("Tis' a pity! Fare thee well!")
        jokeCounter = 0
        furhat.ask("What do you do?")
    }

}

val TalkingToMerchant = state(parent = TownSquareOptions) {

    onEntry {
        furhat.say(dialogStrings["merchantOnEntry"]!!)
        furhat.ask("Would you like to buy something?")
    }

    onResponse<No> {
        furhat.say("Get going then.")
        furhat.say("What do you do?")
    }

    onResponse<Yes> {
        furhat.say(dialogStrings["buyFromMerchant"]!!)
        furhat.ask("What do you do?")
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

    onResponse<Yes> {
        furhat.say("I knew it! I could see it in your eyes!")
        furhat.say(dialogStrings["receivePassword"]!!)
        furhat.ask("What do you do?")
    }

    onResponse<No> {
        furhat.say("It matters not. In time you will inevitably reckon the greatness of our lord.")
        furhat.say(dialogStrings["receivePassword"]!!)
        furhat.ask("What do you do?")
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
                "and a woman selling potions in a market stand. A jester is trying to perform a show " +
                "on the far side of the square. However, no one seems to be watching.",
        "jesterOnEntry" to
                "The jester is juggling with some kegels but seems to keep dropping them. It becomes obvious " +
                "why he has not drawn a crowd. He sees you approaching and puts the kegels down.",
        "preacherOnEntry" to
                "The preacher is speaking about a new god, and the power that comes from worshiping him. " +
                "With him, he has a man and a woman that he claims to have cured from blindness and sickness. " +
                "He notices you in the crowd and turns to you.",
        "godExplanation" to
                "Have you not heard? Is your mind still shrouded in darkness? Our lord Xoros has illuminated " +
                "the chosen people of Millstone with his ",
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