package furhatos.app.storyteller.utils

fun getAskForActionPhrase(): String {

    return listOf(
            "What do you do?",
            "What would you like to do?",
            "What do you want to do?").shuffled()[0]

}