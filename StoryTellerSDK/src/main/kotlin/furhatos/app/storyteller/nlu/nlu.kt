package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

// General

class PleaseRepeat: Intent()

// Alley

class GoToAlley: Intent()

//class TalkToWoman: Intent()

class TalkToMerchant: Intent()

class TalkToJester: Intent()

class ListenToPreacher: Intent()

class GoToAlley: Intent()

class Name : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Stefanos", "Wille", "Joao", "Victor", "Philipp", "Katie", "Manuel", "Alex", "Sofia", "Olivia", "Liam", "Emma", "Noah", "Amelia",
            "Oliver", "Sophia", "Lucas", "Charlotte", "Levi", "James", "Dennis","Elsa", "Marcel-Robert", "Iolanda", "Thomas", "Manuel", "Arzu", "Emil", "Laura",
            "Edlidir", "Ola", "João", "Philip", "Kristin", "Isak", "Divya", "Alexander", "Mikael", "Miklovana", "Katie", "Elmira", "Ilaria")
    }
}

class TellNameBriefly(val name : Name? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@name",
            "I am @name")
    }
}

class Woman : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Woman", "woman", "Women", "women", "Worman", "worman", "Wolman", "wolman")
    }
}

class TalkToWoman(val woman : Woman? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I talk to the @woman", "I will talk to the @woman", "I like to talk to the @woman", "I go to the @woman",
        "talk to the @woman", "talk with the @woman", "@woman", "go to @woman")
    }
}
class RequestGodExplanation: Intent()

class AskMerchantAboutCult: Intent()

class AskAboutBrother: Intent()

// Tavern

class EnterTavern: Intent()
