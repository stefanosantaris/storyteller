package furhatos.app.storyteller.flow.TavernScene

import furhatos.app.storyteller.flow.*
import furhatos.app.storyteller.nlu.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val IntroBartender : State = state(Interaction) {
    onEntry {
        if ( users.current.talkedToBartender == false) {
            furhat.say(utterance {
                +"While approaching, the bartender critically examines you."
                +delay(100)
                +"He is a tall, muscular man with a hard face and plenty of tattoos."
            })
        } else {
            random(
                {furhat.say("As you walk to the bartender again, he looks up at you and frowns.")},
                {furhat.say("While you approach the bartender again, he looks at you annoyed.")},
                {furhat.say("You decide to go to the bartender again. As he sees you, he shakes his head.")})
        }
        goto(DialogBartender_1)
    }
}

val DialogBartender_1 = state(parent = TavernOptions) {
    onEntry {
        // change voice and mask
        furhat.voice = PollyVoice.Russell()
        furhat.setCharacter("Brooklyn")

        delay(600)

        if ( users.current.talkedToBartender == false) {
            users.current.talkedToBartender = true
            furhat.say(utterance {
                + blocking {furhat.gesture(Gestures.ExpressDisgust, async = false)}
                + "What do you want little rascal?"
            })
            furhat.ask("You don’t look like you should be in a bar like this. Why are you here?")
        } else {
            furhat.say(utterance {
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "You again?"
            })
            furhat.ask("Didn't I tell you to leave, little rascal? What do you want?")
        }
    }

    onReentry {
        random(
            {furhat.ask("So what do you want rascal?")},
            {furhat.ask("So what do you want?")},
            {furhat.ask("So tell me, rascal, what do you want from me?")})
    }

    onResponse<ExpressFear> {
        random(
            {furhat.ask(utterance {
                + "Haha"
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "You seem way to afraid to be in a place like this. I think it is best if you leave."})},
            {furhat.ask(utterance {
                + "Look at you!"
                + blocking {furhat.gesture(Gestures.Oh, async = false)}
                + "Are you afraid? I think it is best if you leave this place. You should not be here"})},
            {furhat.ask(utterance {
                + "What a fraidy-cat you are!"
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "I think you should leave this place and go home to your mummy."})}
        )
    }

    onResponse<ExpressInsult> {
        goto(DialogBartender_FightScene)
    }

    onResponse<IamCop> {
        random(
            {furhat.ask(utterance {
                + "So you are a cop?"
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "Well first of all, I don't like cops to sniff around my tavern. And secondly, I have done nothing!"})},
            {furhat.ask(utterance {
                + "You are a cop?"
                + blocking {furhat.gesture(Gestures.ExpressAnger, async = false)}
                + "I have done nothing criminal! What do you want here?"})},
            {furhat.ask(utterance {
                + "Wait, you are a cop?"
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "What do you want in my tavern? Why are you sniffing around here?"})}
        )
    }

    onResponse<FollowMan> {
        random(
            {furhat.ask(utterance {
                + "Man, I don't know what you have been drinking."
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "But I have seen nobody coming here since a few hours."})},
            {furhat.ask(utterance {
                + "Haha"
                + blocking {furhat.gesture(Gestures.Wink, async = false)}
                + "You want to fool me, right? I haven't seen anyone coming in here since hours!"})},
            {furhat.ask(utterance {
                + "What have you been smoking?"
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "Nobody has entered my tavern for hours."})}
        )
    }

    onResponse<AskForCult> {
        random(
            {furhat.ask(utterance {
                + blocking {furhat.gesture(Gestures.ExpressDisgust, async = false)}
                + "What are you talking about?"
                + "I don't know anything about a cult!"})},
            {furhat.ask(utterance {
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "What do you mean? I have never heard anything about a cult!"})},
            {furhat.ask(utterance {
                + "What are you talking about?"
                + blocking {furhat.gesture(Gestures.Shake, async = false)}
                + "A cult? That is insane. I have never heard anything about a cult"})}
        )
    }

    onResponse<HintAtPassword> {
        random(
            {furhat.ask(utterance {
                + "Hmm"
                + blocking {furhat.gesture(Gestures.GazeAway, async = false)}
                + "I might be able to tell you more if you tell me the password."})},
            {furhat.ask(utterance {
                + "You might be at the right place."
                + blocking {furhat.gesture(Gestures.Surprise, async = false)}
                + "Tell me the password and I can tell you more."})}
        )
    }

    onResponse<TellPassword> {
        furhat.say(utterance {
            + blocking {furhat.gesture(Gestures.Oh, async = false)}
            + "Oh! Sorry Sir, I did not know that you are a member of the circle."
            + "Please follow me, I will lead you to the others"
        })
        goto(BasementIntro)
    }

    onNoResponse {
        random(
            {furhat.ask("Why don't you answer me? Are you too afraid to speak?")},
            {furhat.ask("Answer me!")},
            {furhat.ask("Are you afraid or why don't you say something?")})
    }

    onResponse<TalkToWhisperingMen> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(IntroWhisperingMen)
    }

    onResponse<LeaveToAlley> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(alleyArrival(EnteredAlleyFrom.TAVERN))
    }

    onResponse<LeaveToTownSquare> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        goto(TownSquareArrival)
    }

    onResponse {
        random(
            {furhat.ask("I can not help you. It's better you leave my tavern.")},
            {furhat.ask("Look, I don't know what you are searching for, but you will not find it here. Just leave.")},
            {furhat.ask("Look, I can not help you. I think it is best you leave my tavern.")})
    }
}

val DialogBartender_FightScene : State = state(parent = TavernOptions) {
    onEntry {
        random(
            {furhat.ask(utterance {
                + "You dare to insult me?"
                + blocking {furhat.gesture(Gestures.ExpressAnger, async = false)}
                + "Do you want a fight?"})},
            {furhat.ask(utterance {
                + "Are you seriously insulting me?"
                + blocking {furhat.gesture(Gestures.BrowFrown, async = false)}
                + "We can go out fighting if you want?"})},
            {furhat.ask(utterance {
                + "You dare insulting me?"
                + blocking {furhat.gesture(Gestures.ExpressAnger, async = false)}
                + "If you want, we can go out and fight this out."})}
        )
    }
    onNoResponse {
        random(
            {furhat.ask("Answer! Do you want to fight me?")},
            {furhat.ask("Answer me! Do you dare to fight me or not?")},
            {furhat.ask("Are you too afraid to answer? Do you wanna fight me or not?")}
        )
    }

    onResponse<Yes> {
        furhat.voice = PollyNeuralVoice.Joey()
        furhat.setCharacter("Jamie")
        delay(600)

        furhat.say("You decide to go out with the bartender to start a fight.")
        furhat.say("As you step into the alley, the woman who was leaning against the wall shouts:")
        furhat.voice = PollyNeuralVoice.Kimberly()
        delay(600)
        furhat.say("\"Careful, behind you!\"")
        furhat.voice = PollyNeuralVoice.Joey()
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
            {furhat.ask("Man speak up! Do you want to fight me?")},
            {furhat.ask("What did you say? Do you dare to fight me or not?")},
            {furhat.ask("Speak up! Do you wanna fight me or not?")}
        )
    }
}

