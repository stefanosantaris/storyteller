package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Try to bribe men
 */
class Good : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Cheese",
            "cheese",
            "Chess",
            "chess",
            "Chest",
            "chest",
            "Money",
            "money",
            "Gold",
            "gold",
            "Treasure",
            "treasure",
            "Cheddar",
            "cheddar",
            "Wheel",
            "wheel",
            "Wheel of cheddar",
            "wheel of cheddar",
            "Vension",
            "vension",
            "Vision",
            "vision",
            "pay"
        )
    }
}

class Bribe(val good: Good? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I can give you some @good", "give you @good", "Would you like some @good", "Would you like @good", "like some @good",
        "@good", "I have some @good", "I have @good", "have @good", "how about some @good", "how about @good", "can I bribe you with @good",
        "bribe you with @good", "bribe", "Bribe", "with @good", "about @good")
    }
}