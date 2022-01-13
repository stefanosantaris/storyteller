package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Express fear
 */
class Fear : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Sorry",
            "sorry",
            "Please",
            "please",
            "Excuse me",
            "excuse me",
            "I am sorry"
        )
    }
}

class ExpressFear(val fear: Fear? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I am @fear", "@fear don't hurt me", "@fear help me", "@fear", "I'm @fear", "@fear I need help", "help", "I don't want trouble",
        "I do not want trouble")
    }
}

/*
Express anger
 */
class Insult : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "ass licker",
            "arse face",
            "baby",
            "ball sack",
            "bandit",
            "bastard",
            "bitch",
            "shut up",
            "butt ass",
            "butt head",
            "butt",
            "butt hole",
            "butt kisser",
            "butt licker",
            "chicken",
            "clown",
            "cockroach",
            "cow",
            "crackhead",
            "creep",
            "cunt",
            "deadhead",
            "dick",
            "dick ass",
            "dick bag",
            "dick face",
            "donkey",
            "douche",
            "douche bag",
            "dumbass",
            "dumb",
            "dummy",
            "dum",
            "failure",
            "fart",
            "faggot",
            "fat ass",
            "fatty",
            "fuck",
            "fucker",
            "fuck ass",
            "garbage",
            "grinch",
            "horse ass",
            "idiot",
            "jackass",
            "jerk",
            "lunatic",
            "monkey",
            "motherfucker",
            "mongo",
            "nerd",
            "nut",
            "piss face",
            "prick",
            "quack",
            "retard",
            "shit",
            "shit ass",
            "son of a",
            "trash",
            "shut up",
            "rascal",
            "ugly",
            "fight"
        )
    }
}

class ExpressInsult(val insult: Insult? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("you @insult", "you little @insult", "@insult", "you are a @insult", "you're a @insult", "you call me @insult", "I show you @insult",
        "dirty bar", "dirty tavern", "shady bar", "shady tavern", "@insult bar", "@insult tavern", "you think you are", "do you want to fight")
    }
}

/*
Ask for cult
 */
class Cult : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Cult",
            "cult",
            "Kult",
            "kult",
            "Cool",
            "cool",
            "Kalt",
            "kalt"
        )
    }
}

class AskForCult(val cult: Cult? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("do you know something about a @cult", "know about a @cult", "have you heard something about a @cult",
            "heard of a @cult", "know about @cult", "heard about @cult", "about @cult", "about a @cult", "@cult")
    }
}

/*
Hint towards a password
 */
class Password : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Password",
            "password",
            "Passwords",
            "password",
            "Magic word",
            "magic word",
            "Magic Sentence",
            "magic sentence",
            "Secret word",
            "secret word",
            "Secret",
            "secret",
            "Secret sentence",
            "secret sentence"
        )
    }
}

class HintAtPassword(val password: Password? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I can tell you @password", "tell you @password", "say @password", "@password")
    }
}

/*
Tell password
 */
class TellPassword : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Dawn is breaking", "dawn is breaking", "Dawn is", "dawn is", "breaking", "Darkness", "darkness", "diners breaking")
    }
}

class Drink : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "beer",
                "drink",
                "vodka",
                "something strong",
                "liquor",
                "something for my nerves",
                "wine",
                "mead",
                "beverage"
        )
    }
}

class BuyDrink(val drink: Drink? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Buy a @drink",
                "Give me a @drink",
                "I will have a @drink",
                "I want to buy a @drink",
                "I would like to have a @drink",
                "A @drink please",
                "A @drink"
        )
    }
}