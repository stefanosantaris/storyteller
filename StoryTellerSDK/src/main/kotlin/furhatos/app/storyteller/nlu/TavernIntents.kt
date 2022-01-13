package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Express that you want to talk to the bartender
 */
class WantTo : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "I want to go to",
            "I would like to go to",
            "I'd like to go to",
            "Go to",
            "go to",
            "I want to talk to",
            "I would like to talk to",
            "I'd like to talk to",
            "Talk to",
            "talk to",
            "I want to visit",
            "I would like to visit",
            "I'd like to visit",
            "Visit",
            "visit"
        )
    }
}

class Bartender : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "counter",
            "barkeeper",
            "Bartender",
            "bartender",
            "Man behind the bar",
            "man behind the bar",
            "Men behind the bar",
            "men behind the bar",
            "Barman",
            "barman",
            "Barmen",
            "barmen",
            "Bar man",
            "bar man",
            "Bar men",
            "bar men",
            "Barkeeper",
            "barkeeper",
            "Dangerous looking man",
            "dangerous looking man",
            "Dangerous looking men",
            "dangerous looking men",
            "Dangerous man",
            "dangerous man",
            "Dangerous men",
            "dangerous men",
            "man behind counter"
        )
    }
}

class TalkToBartender(val bartender: Bartender? = null, val wantTo: WantTo? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@wantTo @bartender", "@wantTo the @bartender", "@wantTo a @bartender", "@bartender")
    }
}

/*
Express that you want to talk to the two men
 */
class TalkingMen : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Men",
            "men",
            "Talking men",
            "talking men",
            "Men talking",
            "men talking",
            "Men whispering",
            "men whispering",
            "Men at the table",
            "men at the table"
        )
    }
}

class TalkToWhisperingMen(val talkingMen: TalkingMen? = null, val wantTo: WantTo? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@wantTo @talkingMen", "@wantTo the @talkingMen", "@wantTo a @talkingMen", "@talkingMen", "@wantTo two @talkingMen", "two @talkingMen")
    }
}