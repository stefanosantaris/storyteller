package furhatos.app.storyteller.nlu

import furhatos.nlu.Intent
import furhatos.util.Language

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

class LeaveToTavern : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("leave and go into the tavern", "tavern", "go into the tavern", "go to the tavern", "into the tavern")
    }
}

class LeaveToTownSquare : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("Leave and go to the town square", "go to the town square", "town square", "to the town square", "townsquare")
    }
}

