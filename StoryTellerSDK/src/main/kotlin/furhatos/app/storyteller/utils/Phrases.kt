package furhatos.app.storyteller.utils

fun getAskForActionPhrase(): String {

    return listOf(
            "What do you do?",
            "What would you like to do?",
            "What do you want to do?").shuffled()[0]
}

fun getDidNotUnderstandPhrase(): String {

    return listOf(
            "Sorry, I didn't understand that.",
            "Sorry, I didn't get that.",
            "Sorry, could you repeat that?").shuffled()[0]
}

fun getDidNotHearPhrase(): String {

    return listOf(
        "Sorry, I might have missed that.",
        "Sorry, I didn't hear you.",
        "Sorry, could you repeat that?").shuffled()[0]

}
