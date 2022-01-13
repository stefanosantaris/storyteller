package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

// General

class PleaseRepeat : Intent()

// Alley

class GoToAlley : Intent()

// Town square

class GoToTownSquare : Intent()

class TalkToMerchant : Intent()

class TalkToJester : Intent()

class ListenToPreacher : Intent()

class RequestGodExplanation : Intent()

class AskMerchantAboutCult : Intent()

class AskAboutBrother : Intent()

class KillJester : Intent()

// Tavern

class EnterTavern : Intent()

// Setup

/*
Game Setup and Name Fetching
 */
class Name : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Stefanos", "Wille", "Joao", "Victor", "Philipp", "Katie", "Manuel", "Alex", "Sofia", "Olivia", "Liam", "Emma", "Noah", "Amelia",
            "Oliver", "Sophia", "Lucas", "Charlotte", "Levi", "James", "Dennis", "Elsa", "Marcel-Robert", "Iolanda", "Thomas", "Manuel", "Arzu", "Emil", "Laura",
            "Edlidir", "Ola", "João", "Philip", "Kristin", "Isak", "Divya", "Alexander", "Mikael", "Miklovana", "Katie", "Elmira", "Ilaria")
    }
}

class TellNameBriefly(val name: Name? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@name",
            "I am @name")
    }
}

/*
Scene Changing Intends
 */

// Go to other scene
class Leave : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "leave and go into the",
            "go into the",
            "into the",
            "go to the",
            "go into",
            "go to",
            "go back",
            "go back to",
            "change",
            "change to",
            "change to the",
            "I want to go to the",
            "I want to go into the",
            "I want to leave to the",
            "leave to the",
            "leave to",
            "leave",
            "switch to",
            "switch"
        )
    }
}

// Leave to tavern
class Tavern : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Tavern",
            "tavern",
            "Bar",
            "bar"
        )
    }
}

class LeaveToTavern(val leave: Leave? = null, val tavern: Tavern? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@leave @tavern", "@tavern")
    }
}

// Leave to town square
class TownSquare : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Town square",
            "town square",
            "Townsquare",
            "townsquare",
            "Market",
            "market",
            "Market place",
            "market place"
        )
    }
}


class LeaveToTownSquare(val leave: Leave? = null, val townsquare: TownSquare? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@leave @townsquare", "@townsquare")
    }
}

// Leave to Alley
class Alley : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Alley",
            "alley",
            "Alli",
            "alli",
            "Ali",
            "ali",
            "Kelly",
            "l.a.",
            "LA"
        )
    }
}

class LeaveToAlley(val leave: Leave? = null, val alley: Alley? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@leave @alley", "@alley")
    }
}
