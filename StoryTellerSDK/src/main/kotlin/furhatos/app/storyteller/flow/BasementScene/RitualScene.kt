package furhatos.app.storyteller.flow.BasementScene

import furhatos.app.storyteller.flow.Idle
import furhatos.app.storyteller.flow.Interaction
import furhatos.flow.kotlin.*

/*
The user observes a big ritual taking place and heads out for help to arrest the cultists
 */
val RitualEndScene = state(Interaction) {
    onEntry {
        furhat.say(dialogStrings["onArrival1"]!!)
        furhat.say(dialogStrings["onArrival2"]!!)
        furhat.say(dialogStrings["onArrival3"]!!)
        furhat.say(dialogStrings["onArrival4"]!!)
        furhat.say(dialogStrings["onArrival5"]!!)
        furhat.say(dialogStrings["onArrival6"]!!)

        furhat.say("Congratulations! You are the hero of Millstone.")
        furhat.say("You have successfully solved the game.")
        goto(Idle)
    }
}

private val dialogStrings = mapOf(
    "onArrival1" to
            "After a while, you start to hear noises of people chanting. Then, you see lights at the end of the corridor.",
    "onArrival2" to
            "You decide to put out your torch so that nobody will see you. As you carefully move forward, " +
            "you see that the corridor leads to a big cave, where hundreds of cultists are standing around a big burning fire, dancing and chanting.",
    "onArrival3" to
            "You also see several priests in black robes standing near a pile of what you believe to be dead animal bodies.",
    "onArrival4" to
            "As two priests throw one of the dead bodies into the fire, the smell of burnt meat fills the air.",
    "onArrival5" to
            "You are certain that you have reached the headquarter of the worshippers. This is your chance to get rid of this god forsaken cult, once and for all.",
    "onArrival6" to
            "You head back immediately to notify the other sheriffs and watchmen of what you have found out. Together, you rush into the tavern and arrest " +
            "the cultists."
)