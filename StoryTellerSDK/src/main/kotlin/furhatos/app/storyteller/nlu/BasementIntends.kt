package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Express that you want to take the left tunnel
 */
class TalkingMen : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Man",
            "man",
            "Men",
            "men",
            "Talking man",
            "talking man",
            "Talking men",
            "talking men",
            "Man talking",
            "man talking",
            "Men talking",
            "men talking",
            "Man whispering",
            "man whispering",
            "Men whispering",
            "men whispering",
            "Man at the table",
            "man at the table",
            "Men at the table",
            "men at the table"
        )
    }
}

class TalkToWhisperingMen(val talkingMen : TalkingMen? = null, val wantTo : WantTo? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@wantTo @talkingMen", "@wantTo the @talkingMen", "@wantTo a @talkingMen", "@talkingMen", "@wantTo two @talkingMen", "two @talkingMen")
    }
}