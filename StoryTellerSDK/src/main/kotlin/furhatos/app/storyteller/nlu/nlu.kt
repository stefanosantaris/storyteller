package furhatos.app.storyteller.nlu

import furhatos.nlu.Intent
import furhatos.util.Language

class GoToTownSquare: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Town square",
                "I want to go to the town square",
                "Go to town square",
                "Go to the square",
                "I want to go to the square",
                "Enter town square",
                "Enter square"
        )
    }
}

class EnterTavern: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Tavern",
                "I want to go to the tavern",
                "Go in to the tavern",
                "Go to the tavern",
                "I want to go to the tavern",
                "Enter tavern",
                "Enter bar",
                "Enter the door"
        )
    }
}

class TalkToWoman: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Talk to the woman",
                "Approach the woman",
                "Have a chat with the woman",
                "Ask woman"
        )
    }
}

class TalkToMerchant: Intent()

class TalkToJester: Intent()

class ListenToPreacher: Intent()

class GoToAlley: Intent()