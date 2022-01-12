package furhatos.app.storyteller.flow.TavernScene

import furhatos.app.storyteller.flow.Idle
import furhatos.app.storyteller.flow.Interaction
import furhatos.app.storyteller.flow.TavernIdle
import furhatos.app.storyteller.flow.TavernOptions
import furhatos.app.storyteller.flow.talkedToWhisperingMen
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

val IntroWhisperingMen: State = state(Interaction) {
    onEntry {
        if (users.current.talkedToWhisperingMen != true) {
            furhat.say(utterance {
                +"While you approach the two people sitting in their booth, they immediately stop talking and look suspiciously at you."
                +delay(100)
                +"You wonder if they might have seen the strange man entering. Perhaps they even know something about the rumors of a cult."
                +delay(100)
                +"Once you have arrived at their table, one of the men says:"
            })
        } else {
            random(
                { furhat.say("You walk again to the men sitting at one of the tavern's tables.") },
                { furhat.say("As you decide to approach the two men again, they look up at you.") },
                { furhat.say("You decide to go again to the two men sitting at one of the tavern's table.") })

            furhat.say("As you reach the table, one of the men says:")
        }
        goto(DialogWhisperingMen_1)
    }
}

val DialogWhisperingMen_1 = state(parent = TavernOptions) {
    onEntry {
        // change voice and mask
        changeCharacter(furhat, StoryCharacter.WHISPERING_MAN)
        delay(600)

        if (users.current.talkedToWhisperingMen != true) {
            users.current.talkedToWhisperingMen = true
            furhat.say(utterance {
                +"What do you want?"
                +blocking {
                    furhat.gesture(Gestures.ExpressDisgust, async = true)
                }
                +delay(300)
            })
            furhat.ask("Can't you see that we are talking?")
        } else {
            furhat.say(utterance {
                +"You again?"
                +blocking {
                    furhat.gesture(Gestures.Shake, async = false)
                }
            })
            furhat.ask("Can't you mind your own business? What do you want?")
        }
    }

    onReentry {
        random(
            { furhat.ask("Man, we are kind of in a conversation right now. So what do you want man?") },
            { furhat.ask("So what do you want? Tell us or leave us alone.") },
            { furhat.ask("So tell us, man, what do you want from us?") })
    }

    onResponse<ExpressFear> {
        random(
            { furhat.ask(utterance {
                + "Haha"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "You act like a chicken. You definitely do not belong here. I think it is best you leave." }) },
            { furhat.ask(utterance {
                + "Man, look at you."
                + blocking { furhat.gesture(Gestures.Oh, async = false) }
                + "Are you afraid? You better leave before something bad happens to you." }) },
            { furhat.ask(utterance {
                + "What a Milquetoast you are!"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "You better leave and go home, man." }) }
        )
    }

    onResponse<Bribe> {
        goto(DialogWhisperingMen_Bribing)
    }

    onResponse<ExpressInsult> {
        goto(DialogWhisperingMen_FightScene)
    }

    onResponse<IamCop> {
        random(
            {furhat.ask(utterance {
                + "You are a watchman?"
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "If there is one thing we don't like, it will be watchmen sniffing around in our local. So leave!"})},
            {furhat.ask(utterance {
                + "What? A watchman in our local?"
                + blocking {furhat.gesture(Gestures.ExpressAnger, async = false)}
                + "We don't like watchmen! What do you want here?"})},
            {furhat.ask(utterance {
                + "Wait, a watchman?"
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "What are you doing sniffing around in our favorite tavern? You better leave."})}
        )

        changeCharacter(furhat,StoryCharacter.NARRATOR)
        delay(600)
        random (
            furhat.say("Maybe some gold would change their disposition towards you."),
            furhat.say("Perhaps some gold would make them a bit friendlier.")
        )

        changeCharacter(furhat,StoryCharacter.WHISPERING_MAN)
        delay(600)

    }

    onResponse<FollowMan> {
        random(
            { furhat.ask(utterance {
                + "We may have seen someone."
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "But what do you care?" }) },
            { furhat.ask(utterance {
                + "Maybe we have seen someone, maybe not."
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "Why do you care?" }) },
            { furhat.ask(utterance {
                + "A man? Maybe."
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "But what do you care?" }) }
        )

        changeCharacter(furhat,StoryCharacter.NARRATOR)
        delay(600)
        random (
            furhat.say("Maybe some gold would refresh their memories."),
            furhat.say("Perhaps some gold could help them remember something.")
        )

        changeCharacter(furhat,StoryCharacter.WHISPERING_MAN)
        delay(600)

    }

    onResponse<AskForCult> {
        random(
            { furhat.ask(utterance {
                + "A cult?"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "We don't know anything about a cult." }) },
            { furhat.ask(utterance {
                + "What do you mean?"
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "We have never heard about a cult. Why do you ask?" }) },
            { furhat.ask(utterance {
                + "What are you talking about?"
                + blocking { furhat.gesture(Gestures.Shake, async = false) }
                + "A cult? That is insane. What do you want?" }) }
        )

        changeCharacter(furhat,StoryCharacter.NARRATOR)
        delay(600)
        random (
            furhat.say("Maybe some gold would refresh their memories."),
            furhat.say("Perhaps some gold could help them remember something.")
        )

        changeCharacter(furhat,StoryCharacter.WHISPERING_MAN)
        delay(600)
    }

    onResponse<HintAtPassword> {
        random(
            { furhat.ask(utterance {
                + "Hmmh"
                + blocking { furhat.gesture(Gestures.GazeAway, async = false) }
                + "we might be able to help you if you tell us the password." }) },
            { furhat.ask(utterance {
                + "You might be at the right place."
                + blocking { furhat.gesture(Gestures.Surprise, async = false) }
                + "Tell us the password and we can tell you more." }) }
        )
    }

    onResponse<TellPassword> {
        random(
            { furhat.say(utterance {
                + blocking { furhat.gesture(Gestures.Oh, async = false) }
                + "Ah! A member of the circle! Talk to the bartender and tell him the password."
                + "He will guide you to the others."
            }) },
            { furhat.say(utterance {
                + blocking { furhat.gesture(Gestures.Oh, async = false) }
                + "I see, you are a member. Go to the man behind the bar and tell him the password."
                + "He will guide you to the others."
            }) }
        )
        changeCharacter(furhat,StoryCharacter.NARRATOR)
        delay(600)
        goto(TavernIdle)
    }

    onNoResponse {
        random(
            { furhat.ask("Why don't you say anything? Are you too afraid to speak?") },
            { furhat.ask("Are you just gonna stand there?") },
            { furhat.ask("Are you afraid or why don't you speak with us?") })
    }

    onResponse(intent = NullIntent) {
        if (timeToLeave(furhat)) {
            goto(TavernIdle)
        }

        random(
            { furhat.ask("Look, we can not help you. It's better that you leave this tavern.") },
            { furhat.ask("Man, we don't know what you are talking about. You better leave.") },
            { furhat.ask("What are you talking about? It might be better that you leave.") })
    }
}

/*
Start a fight with the two men
 */
val DialogWhisperingMen_FightScene: State = state(parent = TavernOptions) {
    onEntry {
        random(
            { furhat.ask(utterance {
                + "You dare to insult us?"
                + blocking { furhat.gesture(Gestures.ExpressAnger, async = false) }
                + "Do you also dare to fight us?" }) },
            { furhat.ask(utterance {
                + "Are you seriously daring insulting us?"
                + blocking { furhat.gesture(Gestures.BrowFrown, async = false) }
                + "We can go out fighting if you want?" }) },
            { furhat.ask(utterance {
                + "How dare you insulting us?"
                + blocking { furhat.gesture(Gestures.ExpressAnger, async = false) }
                + "If you want, we can go out and fight this out." }) }
        )
    }
    onNoResponse {
        random(
            { furhat.ask("Answer! Do you want to fight us?") },
            { furhat.ask("You better say something! We can go out and fight this out if you want.") },
            { furhat.ask("Too afraid to answer? Are you brave enough for a fight or not?") }
        )
    }

    onResponse<Yes> {
        changeCharacter(furhat,StoryCharacter.NARRATOR)
        delay(600)

        furhat.say("You decide to go out with the two men to start a fight.")
        furhat.say("As you step into the alley, you notice that it already got dark.")
        furhat.say("Suddenly, one of the men grabs you from behind.")
        furhat.say("You try to get him off, but struggle to do so. Meanwhile you observe that the other man picks up a glass bottle that was standing besides the tavern's door.")
        delay(600)
        furhat.say("As he approaches you, you close your eyes. You feel an enormous pain towards you waste. Then...")
        delay(800)
        furhat.say("darkness.")
        delay(1000)

        furhat.say("Unfortunately, you lost the game.")
        goto(Idle)
    }

    onResponse<No> {
        furhat.say("That is smart of you. You better behave yourself next time!")
        goto(DialogWhisperingMen_1)
    }

    onResponse(intent = NullIntent) {
        random(
            { furhat.ask("Man speak up! Do you want to fight us?") },
            { furhat.ask("What did you say? Do you dare to fight us or not?") },
            { furhat.ask("Speak up! Do you wanna fight us or not?") }
        )
    }
}

/*
Bribing the two men
 */
val DialogWhisperingMen_Bribing: State = state(parent = TavernOptions) {
    onEntry {
        random(
            { furhat.ask(utterance {
                + "That sounds quite good, to be honest. Now we are talking, my friend."
                + blocking { furhat.gesture(Gestures.Wink, async = false) }
                + "Go to the man behind the bar and tell him the words: \"Dawn is breaking\". Then, follow him and you will find what you are searching for." }) },
            { furhat.ask(utterance {
                + "That is quite the right offer, my friend. Now we are talking."
                + blocking { furhat.gesture(Gestures.Wink, async = false) }
                + "Go to the bartender and say: \"Dawn is breaking\". Then follow him and you will find what you are searching for" }) }
        )
    }

    onReentry {
        random(
            { furhat.ask("Why are you still here? Talk to the bartender and he will guide you the way.") },
            { furhat.ask("Man, you should go now. And don't tell anyone what I just told you.") },
            { furhat.ask("Listen, you better go now. Talk to the bartender and he will guide you the way.") }
        )
    }

    onNoResponse {
        random(
            { furhat.ask("Go now. And don't tell anyone about this!") },
            { furhat.ask("Why are you still here? Go now. And remember, do not tell anyone about our little talk.") },
            { furhat.ask("Man, you need to go now. And remember, don't tell anyone about this!") }
        )
    }

    onResponse(intent = NullIntent) {
        if (timeToLeave(furhat)) {
            goto(TavernIdle)
        }

        random(
            { furhat.ask("Man, I cannot help you more than that. Go to the bartender and you will find what you are searching for.") },
            { furhat.ask("Listen, you should go now. As I told you, talk to the bartender and he will guide you the way.") },
            { furhat.ask("Listen, you need to leave now. Go to the bartender and he will show you the way.") }
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
                "You decide to leave the men alone for now.",
                "You think it might be best to leave the men alone, at least for now."
            ).shuffled()[0]
        )
        true
    } else {
        responseCounter++
        false
    }
}