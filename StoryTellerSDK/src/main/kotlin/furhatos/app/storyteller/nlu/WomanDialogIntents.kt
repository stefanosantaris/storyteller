package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Dialogue possibilities
 */
class DialogWomanAnswer_1_a : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("calm down, I am a cop. Why are you so afraid", "I am a cop. Why are you so afraid?", "calm down",
            "cop", "I am a cop", "afraid", "why are you so afraid", "why")
    }
}

class DialogWomanAnswer_1_b : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("relax. I don’t want to harm you. What is this place here?", "relax", "I don’t want to harm you", "harm", "ham",
            "what is this place here", "place", "what", "watch")
    }
}

class DialogWomanAnswer_1_a_a : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("who are them", "who", "them", "warden")
    }
}

/*
Express that you are a police man
 */
class Cop : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Cop",
            "cop",
            "Copp",
            "copp",
            "Police",
            "police",
            "Policeman",
            "policeman",
            "Police man",
            "police man",
            "Police men",
            "police men",
            "Officer",
            "officer",
            "Police woman",
            "police woman",
            "Police women",
            "police women",
            "law",
            "Cup",
            "cup",
            "Sheriff",
            "sheriff",
            "Watchmen",
            "watchmen",
            "Watch man",
            "watch man",
            "Watch men",
            "watch men",
            "Detective",
            "detective"
        )
    }
}

class IamCop(val cop: Cop? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am a @cop", "a @cop", "I'm a @cop", "@cop", "as a @cop", "I am from the @cop", "relax I'm a @cop")
    }
}

/*
Express that you follow a man
 */

class TattooMan : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Man",
            "man",
            "Men",
            "men",
            "Tattoo",
            "tattoo",
            "Man with a tattoo",
            "man with a tattoo",
            "with a tattoo",
            "tattoo on his arm",
            "Tattoo on his arm"
        )
    }
}

class FollowMan(val tattooman: TattooMan? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Have you seen the @tattooman", "have you seen the @tattooman", "Have you seen a @tattooman", "have you seen a @tattooman",
        "I am looking for a @tattooman", "@tattooman", "I am following a @tattooman", "seen a @tattooman", "who was that @tattooman")
    }
}

/*
Ask about the tavern
 */
class WhatIsThisPlace : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "What do you know about",
            "what do you know about",
            "What is this",
            "what is this",
            "Can you tell me what this",
            "can you tell me what this",
            "tell me what is this"
        )
    }
}

class AskAboutTown() : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "What is going on here?",
                "What is going on in this town?",
                "What do you know about this town?",
                "Cult",
                "Do you know about the cult?",
                "What do you know about the cult?")
    }
}

class AskAboutTavern(val askplace: WhatIsThisPlace? = null, val tavern: Tavern? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@askplace @tavern", "@askplace", "What is this place", "what is this place")
    }
}
