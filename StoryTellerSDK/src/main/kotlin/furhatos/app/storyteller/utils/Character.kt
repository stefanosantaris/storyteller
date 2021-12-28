package furhatos.app.storyteller.utils

import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice

fun changeCharacter(furhat: Furhat, characterEnum: StoryCharacter) {
    val character = characterTraits[characterEnum]
    character?.let {
        furhat.voice = it.voice
        furhat.character = it.name
        furhat.mask = it.mask
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
    StoryCharacter.NARRATOR to Character("Jamie", PollyNeuralVoice.Joey(), "adult"),
    StoryCharacter.ALLEY_WOMAN to Character("Isabel", PollyNeuralVoice.Kimberly(), "adult"),
    StoryCharacter.WHISPERING_MAN to Character("Fedora", PollyNeuralVoice.Brian(), null),
    StoryCharacter.BARTENDER to Character("Brooklyn", PollyVoice.Russell(), null),
    StoryCharacter.JESTER to Character("Hans", PollyVoice.Hans(), null),
    StoryCharacter.MERCHANT to Character("Astrid", PollyVoice.Astrid(), null),
    StoryCharacter.PREACHER to Character("Justin", PollyVoice.Justin(), null)
)

private data class Character(val name: String, val voice: Voice, val mask: String?)