package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Talk to the woman leaning against the wall
 */
class Woman : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Woman", "woman", "Women", "women", "Worman", "worman", "Wolman", "wolman")
    }
}

class TalkToWoman(val woman: Woman? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I talk to the @woman", "I will talk to the @woman", "I like to talk to the @woman", "I go to the @woman",
            "talk to the @woman", "talk with the @woman", "@woman", "go to @woman")
    }
}