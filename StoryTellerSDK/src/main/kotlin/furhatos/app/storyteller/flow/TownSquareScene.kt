package furhatos.app.storyteller.flow

import furhatos.app.storyteller.nlu.AskAboutBrother
import furhatos.app.storyteller.nlu.AskMerchantAboutCult
import furhatos.app.storyteller.nlu.GoToAlley
import furhatos.app.storyteller.nlu.KillJester
import furhatos.app.storyteller.nlu.ListenToPreacher
import furhatos.app.storyteller.nlu.PleaseRepeat
import furhatos.app.storyteller.nlu.RequestGodExplanation
import furhatos.app.storyteller.nlu.TalkToJester
import furhatos.app.storyteller.nlu.TalkToMerchant
import furhatos.app.storyteller.utils.JokeManager
import furhatos.app.storyteller.utils.NoMoreJokesException
import furhatos.app.storyteller.utils.StoryCharacter
import furhatos.app.storyteller.utils.changeCharacter
import furhatos.app.storyteller.utils.getAskForActionPhrase
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val TownSquareOptions: State = state(Interaction) {

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
        goto(alleyArrival(EnteredAlleyFrom.TOWN_SQUARE))
    }
}

val TownSquareIdle = state(parent = TownSquareOptions) {

    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        val notVisited = setOf(
                Interactions.JESTER,
                Interactions.MERCHANT,
                Interactions.PREACHER).minus(visited).toList()

        val optionStrings = notVisited.map { it.name.toLowerCase() }

        val optionPresentation = when (notVisited.size) {
            3 -> "There is a ${optionStrings[0]}, a ${optionStrings[1]} and a ${optionStrings[2]} in the square. "
            2 -> "There is also a ${optionStrings[0]} and a ${optionStrings[1]} in the square. "
            1 -> "There is also a ${optionStrings[0]} in the square. "
            else -> ""
        }.plus("Behind you is the alley where you were before.")

        delay(600)
        furhat.say(optionPresentation)
        delay(300)
        furhat.ask(getAskForActionPhrase())
    }

    onReentry {
        furhat.ask(getAskForActionPhrase())
    }
}

val TownSquareArrival = state(parent = TownSquareOptions) {

    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)

        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask(getAskForActionPhrase())
    }

    onReentry {
        furhat.ask(getAskForActionPhrase())
    }

    onResponse <PleaseRepeat> {
        furhat.say(dialogStrings["onArrival"]!!)
        furhat.ask(getAskForActionPhrase())
    }
}

val TalkingToJester = state(parent = TownSquareOptions) {

    onEntry {
        furhat.say(dialogStrings["jesterOnEntry"]!!)
        changeCharacter(furhat, StoryCharacter.JESTER)
        delay(600)

        furhat.ask("Here comes my moment! Would you like to hear a joke?")

    }

    onReentry {
        random(
                {furhat.ask("How about a joke or two?")},
                {furhat.ask("Care for a joke?")},
                {furhat.ask("Can I interest you in a joke?")}
                )
    }

    onResponse<KillJester> {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)

        furhat.say("The jester is our friend. He's here to help us.")

        changeCharacter(furhat, StoryCharacter.JESTER)
        delay(600)

        reentry()
    }

    onResponse <PleaseRepeat> {
        furhat.say(dialogStrings["jesterOnEntry"]!!)
        random(
                {furhat.ask("Finally some attention! Care for a joke?")},
                {furhat.ask("Here comes my moment! Can I interest you in a joke?")}
        )
    }

    onResponse<Yes> {

        try {
            val joke = jokeManager.getNextJoke()
            furhat.say(joke)
            if (jokeManager.hasMoreJokes()) {
                random(
                        furhat.ask("How about another one?"),
                        furhat.ask("Care to hear one more?"),
                        furhat.ask("I have more of those in store, want to hear one?")
                        )
            } else {
                furhat.say("That was my last one! Fare well!")
                jokeManager.reset()
                visited.add(Interactions.JESTER)
                changeCharacter(furhat, StoryCharacter.NARRATOR)
                delay(600)
                furhat.say("You thank the friendly jester for his time and take your leave.")
                goto(TownSquareIdle)
            }
        } catch (e: NoMoreJokesException) {
            furhat.say("I am afraid that I am all out of jokes!")
            visited.add(Interactions.JESTER)
            changeCharacter(furhat, StoryCharacter.NARRATOR)
            delay(600)
            furhat.say("That will be enough with the jokes for now.")
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
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry1"]!!)

        changeCharacter(furhat, StoryCharacter.MERCHANT)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry2"]!!)

        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry3"]!!)

        changeCharacter(furhat, StoryCharacter.MERCHANT)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry4"]!!)
        delay(300)
        random(
                furhat.ask("Would you like to buy something?"),
                furhat.ask("What will it be then?"),
                furhat.ask("Go on, want to buy something or not?")
        )
    }

    onReentry {
        random(
                furhat.ask("Would you like to buy something?"),
                furhat.ask("What will it be then?"),
                furhat.ask("Go on, want to buy something or not?")
        )
    }

    onResponse <PleaseRepeat> {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry1"]!!)

        changeCharacter(furhat, StoryCharacter.MERCHANT)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry2"]!!)

        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry3"]!!)

        changeCharacter(furhat, StoryCharacter.MERCHANT)
        delay(600)
        furhat.say(dialogStrings["merchantOnEntry4"]!!)
        furhat.ask("Would you like to buy something?")
    }

    onResponse<No> {
        furhat.say("Get going then.")
        visited.add(Interactions.MERCHANT)
        goto(TownSquareIdle)
    }

    onResponse<Yes> {
        changeCharacter(furhat, StoryCharacter.MERCHANT)
        delay(600)
        furhat.say(dialogStrings["buyFromMerchant1"]!!)

        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(400)
        furhat.say(dialogStrings["buyFromMerchant2"]!!)

        visited.add(Interactions.MERCHANT)
        goto(TownSquareIdle)
    }

    onResponse<AskMerchantAboutCult> {
        furhat.say("Don't ask me anything more! If you really have to know, talk to the " +
                "man preaching over there!")
        furhat.ask("Will you buy something or not?")
    }

    onResponse<AskAboutBrother> {
        furhat.say("I cannot tell you more... just let me know if you see him, okay?")
        furhat.ask("Will you buy something or not? I don't have all day.")
    }
}

val ListeningToPreacher = state(parent = TownSquareOptions) {

    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["preacherOnEntry"]!!)

        changeCharacter(furhat, StoryCharacter.PREACHER)
        delay(600)
        furhat.ask("Are you a believer? A child of Galos?")
    }

    onReentry {
        furhat.ask("Are you a believer? A child of Galos?")
    }

    onResponse <PleaseRepeat> {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["preacherOnEntry"]!!)

        changeCharacter(furhat, StoryCharacter.PREACHER)
        delay(600)
        furhat.ask("Are you a believer? A child of Galos?")
    }

    onResponse<Yes> {
        changeCharacter(furhat, StoryCharacter.PREACHER)
        delay(600)
        furhat.say("I knew it! I could see it in your eyes!")
        goto(ReceivingPassword)

    }

    onResponse<No> {
        changeCharacter(furhat, StoryCharacter.PREACHER)
        delay(600)
        furhat.say("It matters not. In time you will inevitably reckon the greatness of our lord.")
        goto(ReceivingPassword)

    }

    onResponse<RequestGodExplanation> {
        changeCharacter(furhat, StoryCharacter.PREACHER)
        furhat.say(dialogStrings["godExplanation"]!!)
        delay(500)
        furhat.ask("I ask you again, are you a follower?")
    }
}

val ReceivingPassword = state(parent = TownSquareOptions) {
    onEntry {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say(dialogStrings["receivePassword1"]!!)

        changeCharacter(furhat, StoryCharacter.PREACHER)
        delay(600)
        furhat.say(PollyVoice.Justin().whisper(dialogStrings["receivePassword2"]!!))
        delay(200)
        furhat.say(PollyVoice.Justin().whisper(
                "Dawn ${furhat.voice.pause("300ms")} " +
                        "is ${furhat.voice.pause("300ms")} " +
                        "breaking ${furhat.voice.pause("300ms")}"
        )
        )
        delay(300)
        furhat.say(PollyVoice.Justin().whisper(dialogStrings["receivePassword3"]!!))

        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say("A bit shook from the interaction, you leave the preacher and his surrounding crowd. " +
                "You remember seeing the tattooed man disappearing into the tavern he spoke about. Maybe you " +
                "need to make your way back there in order to find out more.")

        visited.add(Interactions.PREACHER)
        goto(TownSquareIdle)
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
                "Have you not heard? Is your mind still shrouded in darkness? Our lord Galos has illuminated " +
                "the chosen people of Millstone with his power.",
        "receivePassword1" to
                "He moves closer to you and whispers in your ear, too quietly for anyone else to hear.",
        "receivePassword2" to
                "There is a gathering tonight. Go to the Hidden Goat Tavern and tell the man behind the bar the following: ",
        "receivePassword3" to "There you will find out more.",
        "merchantOnEntry1" to
                "You approach the merchant stand and find a young woman there selling meat and cheese. She looks at " +
                "you with despair in her eyes.",
        "merchantOnEntry2" to
                "You haven't seen my brother, have you? He has been gone for several days " +
                "and I am beginning to worry... I wish he never would have gotten involved with those damned cultists!",
        "merchantOnEntry3" to
                "She suddenly looks startled, as if she had just caught herself saying something forbidden.",
        "merchantOnEntry4" to
                "Never mind anything that I said!",
        "buyFromMerchant1" to
                "Here you go, some vension and a wheel of cheddar. Have a good day.",
        "buyFromMerchant2" to
                "You pay the merchant."
)

private val jokeManager = JokeManager()

private enum class Interactions {
    JESTER,
    MERCHANT,
    PREACHER
}

private val visited = mutableSetOf<Interactions>()
