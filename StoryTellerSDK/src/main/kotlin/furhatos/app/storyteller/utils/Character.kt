package furhatos.app.storyteller.utils

import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice

fun changeCharacter(furhat: Furhat, characterEnum: StoryCharacter) {
    val character = characterTraits[characterEnum]
    character?.let {
        furhat.voice = it.voice
        furhat.setTexture(it.name)
        furhat.setModel(it.mask)
    }
}

enum class StoryCharacter {
    NARRATOR,
    ALLEY_WOMAN,
    WHISPERING_MAN,
    BARTENDER,
    JESTER,
    MERCHANT,
    PREACHER
}

private val characterTraits = mapOf(
    StoryCharacter.NARRATOR to Character("Marty", PollyNeuralVoice.Brian(), "adult"),
    StoryCharacter.ALLEY_WOMAN to Character("Arianna", PollyNeuralVoice.Kimberly(), "adult"),
    StoryCharacter.WHISPERING_MAN to Character("Geremy", PollyVoice.Matthew(), null),
    StoryCharacter.BARTENDER to Character("Jack", PollyVoice.Russell(), null),
    StoryCharacter.JESTER to Character("Fred", PollyVoice.Joey(), null),
    StoryCharacter.MERCHANT to Character("Ursula", PollyVoice.Amy(), null),
    StoryCharacter.PREACHER to Character("Barack", PollyVoice.Matthew(), null)
)

private data class Character(val name: String, val voice: Voice, val mask: String?)