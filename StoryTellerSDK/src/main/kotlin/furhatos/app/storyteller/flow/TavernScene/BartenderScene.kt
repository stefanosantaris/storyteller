package furhatos.app.storyteller.flow.TavernScene

import furhatos.app.storyteller.flow.*
import furhatos.app.storyteller.flow.BasementScene.BasementIntro
import furhatos.app.storyteller.nlu.*
import furhatos.app.storyteller.utils.StoryCharacter
import furhatos.app.storyteller.utils.changeCharacter
import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.users
import furhatos.flow.kotlin.utterance
import furhatos.gestures.Gestures
import furhatos.nlu.NullIntent
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val IntroBartender: State = state(Interaction) {
    onEntry {
        changeCharacter(furhat,StoryCharacter.NARRATOR)
        
        if (users.current.visitedBasement == true) {
            random(
                {furhat.say("As you walk to the bartender again, he looks at you, nodding.")},
                {furhat.say("While you approach the bartender again, he looks at you and smiles.")},
                {furhat.say("You decide to go to the bartender again. As he sees you, he smiles.")}
            )
        } else if (users.current.talkedToBartender == false) {
            furhat.say(utterance {
                +"While approaching, the bartender critically examines you."
                +delay(100)
                +"He is a tall, muscular man with a hard face and plenty of tattoos."
                +"You get the feeling that insulting him could end badly."
            })
        } else {
            random(
                { furhat.say("As you walk to the bartender again, he looks up at you and frowns.") },
                { furhat.say("While you approach the bartender again, he looks at you annoyed.") },
                { furhat.say("You decide to go to the bartender again. As he sees you, he shakes his head.") })
        }
        goto(DialogBartender_1)
    }
}

val DialogBartender_1 = state(parent = TavernOptions) {
    onEntry {
        // change voice and mask
        changeCharacter(furhat,StoryCharacter.BARTENDER)
        delay(600)

        if (users.current.visitedBasement == true) {
            furhat.say("Is it you again? Feel free to go into the basement so you can join the others.")
            goto(BasementIntro)
        } else if (users.current.talkedToBartender != true) {
            users.current.talkedToBartender = true
            furhat.say(utterance {
                + blocking { furhat.gesture(Gestures.ExpressDisgust, async = true) }
                + "What do you want little rascal?"
            })
            furhat.ask("You don’t look like you should be in a bar like this. Why are you here?")
        } else {
            furhat.say(utterance {
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "You again?"
            })
            furhat.ask("Didn't I tell you to leave, little rascal? What do you want?")
        }
    }

    onReentry {
        random(
            { furhat.ask("So what do you want rascal?") },
            { furhat.ask("So what do you want?") },
            { furhat.ask("So tell me, rascal, what do you want from me?") })
    }

    onResponse<BuyDrink> {
        random(

                { furhat.say("I'm not serving scum like you.") },
                { furhat.say("Would I give my fine beverage to a rat? Hah! Forget it.") }
        )
        furhat.ask("Something else, punk?")
    }

    onResponse<ExpressFear> {
        random(
            { furhat.ask(utterance {
                + "Haha"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "You seem way to afraid to be in a place like this. I think it is best if you leave." }) },
            { furhat.ask(utterance {
                + "Look at you!"
                + blocking { furhat.gesture(Gestures.Oh, async = false) }
                + "Are you afraid? I think it is best if you leave this place. You should not be here" }) },
            { furhat.ask(utterance {
                + "What a fraidy-cat you are!"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "I think you should leave this place and go home to your mummy." }) }
        )
    }

    onResponse<ExpressInsult> {
        goto(DialogBartender_FightScene)
    }

    onResponse<IamCop> {
        random(
            { furhat.ask(utterance {
                + "So the sheriff sent you?"
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "Well first of all, I don't like cops to sniff around my tavern. And secondly, I have done nothing!" }) },
            { furhat.ask(utterance {
                + "You are a cop?"
                + blocking { furhat.gesture(Gestures.ExpressAnger, async = false) }
                + "I have done nothing criminal! What do you want here?" }) },
            { furhat.ask(utterance {
                + "Wait, you are a watchman?"
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "What do you want in my tavern? Why are you sniffing around here?" }) }
        )
    }

    onResponse<FollowMan> {
        random(
            { furhat.ask(utterance {
                + "Man, I don't know what you have been drinking."
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "But I haven't seen anyone coming in here for several hours." }) },
            { furhat.ask(utterance {
                + "Haha"
                + blocking { furhat.gesture(Gestures.Wink, async = false) }
                + "You think I'm stupid, is that it? No one has come in here for hours!" }) },
            { furhat.ask(utterance {
                + "What have you been smoking? "
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "Nobody has entered my tavern for hours." }) }
        )
    }

    onResponse<AskForCult> {
        random(
            { furhat.ask(utterance {
                + blocking { furhat.gesture(Gestures.ExpressDisgust, async = false) }
                + "What are you talking about?"
                + "I don't know anything about a cult!" }) },
            { furhat.ask(utterance {
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "What do you mean? I have never heard anything about a cult!" }) },
            { furhat.ask(utterance {
                + "What are you talking about?"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "A cult? That is insane. I have never heard anything about a cult" }) }
        )
    }

    onResponse<HintAtPassword> {
        random(
            { furhat.ask(utterance {
                + "Hmm"
                + blocking { furhat.gesture(Gestures.GazeAway, async = false) }
                + "I might be able to tell you more if you tell me the password." }) },
            { furhat.ask(utterance {
                + "You might be at the right place."
                + blocking { furhat.gesture(Gestures.Surprise, async = false) }
                + "Tell me the password and I can tell you more." }) }
        )
    }

    onResponse<TellPassword> {
        furhat.say(utterance {
            + blocking { furhat.gesture(Gestures.Oh, async = false) }
            + "Oh! I apologize, I didn't realize that you were a member of the circle."
            + "Please follow me, I will lead you to the others."
        })
        goto(BasementIntro)
    }

    onNoResponse {
        random(
            { furhat.ask("Why don't you answer me? Are you too afraid to speak?") },
            { furhat.ask("Are you just going to stand there?") },
            { furhat.ask("Are you afraid or why don't you say something?") })
    }

    onResponse(intent = NullIntent) {
        if (timeToLeave(furhat)) {
            goto(TavernIdle)
        }

        random(
            { furhat.ask("I can not help you. It's better you leave my tavern.") },
            { furhat.ask("Look, I don't know what you are searching for, but you will not find it here. Just leave.") },
            { furhat.ask("Look, I can not help you. I think it is best you leave my tavern.") })
    }
}

val DialogBartender_FightScene: State = state(parent = TavernOptions) {
    onEntry {
        random(
            { furhat.ask(utterance {
                + "You dare to insult me?"
                + blocking { furhat.gesture(Gestures.ExpressAnger, async = false) }
                + "Do you want a fight?" }) },
            { furhat.ask(utterance {
                + "Are you seriously insulting me?"
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "We can go out fighting if you want." }) },
            { furhat.ask(utterance {
                + "You dare insult me?"
                + blocking { furhat.gesture(Gestures.ExpressAnger, async = false) }
                + "If you want, we can go outside and fight this out." }) }
        )
    }
    onNoResponse {
        random(
            { furhat.ask("Answer! Do you want to fight me?") },
            { furhat.ask("Answer me! Do you dare to fight me or not?") },
            { furhat.ask("Are you too afraid to answer? Do you wanna fight me or not?") }
        )
    }

    onResponse<Yes> {
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        delay(600)
        furhat.say("You decide to go out with the bartender to start a fight.")
        furhat.say("As you step into the alley, the woman who was leaning against the wall shouts:")

        changeCharacter(furhat, StoryCharacter.ALLEY_WOMAN)
        delay(600)
        furhat.say("\"Careful, behind you!\"")

        changeCharacter(furhat,StoryCharacter.NARRATOR)
        delay(600)

        furhat.say("You immediately turn around, but the bartender is too fast. The last thing you see is a club aiming for your head.")
        furhat.say("Then, it gets dark...")

        delay(1000)
        furhat.say("Unfortunately, you lost the game.")

        goto(Idle)
    }

    onResponse<No> {
        furhat.say("That is smart of you. You better behave yourself in my tavern!")
        goto(DialogBartender_1)
    }

    onResponse {
        random(
            { furhat.ask("Man speak up! Do you want to fight me?") },
            { furhat.ask("What did you say? Do you dare to fight me or not?") },
            { furhat.ask("Speak up! Do you wanna fight me or not?") }
        )
    }
}

private var responseCounter = 0

private fun timeToLeave(furhat: Furhat): Boolean {
    return if (responseCounter == 2) {
        responseCounter = 0
        changeCharacter(furhat, StoryCharacter.NARRATOR)
        furhat.say(
            listOf(
                    "You decide to leave the bartender alone for now.",
                    "You think it might be best to leave the bartender alone, at least for now."
            ).shuffled()[0]
        )
        true
    } else {
        responseCounter++
        false
    }
}