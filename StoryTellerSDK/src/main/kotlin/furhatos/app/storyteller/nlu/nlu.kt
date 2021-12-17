package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Game Setup and Name Fetching
 */

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

// General

class PleaseRepeat: Intent()

// Alley

class TalkToMerchant: Intent()

class TalkToJester: Intent()

class ListenToPreacher: Intent()

class GoToAlley: Intent()

class RequestGodExplanation: Intent()

class AskMerchantAboutCult: Intent()

class AskAboutBrother: Intent()

// Tavern

class EnterTavern: Intent()
