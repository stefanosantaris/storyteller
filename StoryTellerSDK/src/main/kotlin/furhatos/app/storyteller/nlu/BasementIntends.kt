package furhatos.app.storyteller.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

/*
Express that you want to enter the corridor
 */
class Corridor : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Corridor",
            "corridor",
            "Tunnel",
            "tunnel",
            "Gang",
            "gang",
            "Gangway",
            "gangway",
            "Hallway",
            "hallway",
            "Channel",
            "channel",
            "Dark",
            "dark",
            "Darkness",
            "darkness"
        )
    }
}

class EnterCorridor(val corridor : Corridor? = null, val wantTo: WantTo? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Enter the @corridor", "enter the @corridor", "Walk into the @corridor", "walk into the @corridor", "walk into @corridor",
        "into the @corridor", "into @corridor", "@corridor", "go into the @corridor", "go into @corridor", "dark @corridor",
        "go ahead", "enter", "enter it", "yes", "@wantTo @corridor", "@wantTo the darkness")}
}

/*
Express that you want to leave
 */
class Upstairs : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Tavern",
            "tavern",
            "Bar",
            "bar",
            "Upstairs",
            "upstairs",
            "Up",
            "up",
            "Back",
            "back",
            "Back up",
            "back up"
        )
    }
}

class GoBack(val upstairs : Upstairs? = null, val wantTo: WantTo? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("go @upstairs", "leave to @upstairs", "@wantTo @upstairs", "@wantTo follow the barmen", "@wantTo leave",
        "follow the barmen")}
}

/*
Express that you would like to enter a corridor
 */
class Enter : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "I would like to go to",
            "I'd like to go to",
            "I would like to enter",
            "I'd like to enter",
            "I want to go to",
            "I want to enter",
            "Enter",
            "enter",
            "Go to",
            "go to",
            "Take the",
            "take the",
            "Take",
            "take",
            "Walk to",
            "walk to",
            "Walk into",
            "walk into",
            "Go",
            "go",
            "Walk",
            "walk"
        )
    }
}

class Left : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Left",
            "left",
            "West",
            "west",
            "Where west is written",
            "where west is written",
            "To my left",
            "to my left",
            "To the one on the left",
            "to the one on the left",
            "The left one",
            "the left one"
        )
    }
}

class Straight : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Straight ahead",
            "straight ahead",
            "North",
            "north",
            "Where north is written",
            "where north is written",
            "Ahead",
            "ahead",
            "In front of me",
            "in front of me",
            "Straight",
            "straight"
        )
    }
}

class Right : EnumEntity(stemming = false, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Right",
            "right",
            "East",
            "east",
            "Where east is written",
            "where east is written",
            "To my right",
            "to my right",
            "To the one on the right",
            "to the one on the right",
            "The right one",
            "the right one",
            "Beast",
            "beast",
            "eastern",
            "eastern",
            "the eastern corridor"
        )
    }
}

class GoLeft(val left : Left? = null, val enter: Enter? = null, val corridor : Corridor? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@enter @left @corridor", "@enter the @left @corridor", "@enter @corridor @left",
            "@enter the @corridor @left", "@enter @left", "@enter the @left", "@left", "@left @corridor",
            "@corridor @left", "the @left @corridor")}
}

class GoStraight(val straight : Straight? = null, val enter: Enter? = null, val corridor : Corridor? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@enter @straight @corridor", "@enter the @straight @corridor", "@enter @corridor @straight",
            "@enter the @corridor @straight", "@enter @straight", "@enter the @straight", "@straight", "@straight @corridor",
            "@corridor @straight", "the @straight @corridor")}
}

class GoRight(val right : Right? = null, val enter: Enter? = null, val corridor : Corridor? = null): Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@enter @right @corridor", "@enter the @right @corridor", "@enter @corridor @right",
        "@enter the @corridor @right", "@enter @right", "@enter the @right", "@right", "@right @corridor",
        "@corridor @right", "the @right @corridor")}
}

