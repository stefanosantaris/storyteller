package furhatos.app.storyteller.utils

import furhatos.flow.kotlin.utterance

class JokeManager {
    var jokeCounter = 0
    private val jokes = listOf(
            Joke(
                    setup = "Why was the knight fighting the tournament with a " +
                            "sword made from cheddar cheese?",
                    punchline = "Because the cheese was extra sharp!"
            ),
            Joke(
                    setup = "What did the king say when he heard that the peasants were revolting?",
                    punchline = "He said he agrees because they never bathe and always stink."
            ),
            Joke(
                    setup = "Why was the court jester almost executed? ",
                    punchline = "Because the Queen got the joke at the last moment!"
            )
    )

    private fun getJokeUtterance(joke: Joke) = utterance {
        joke.setup + delay(1500) + joke.punchline
    }

    fun getNextJoke() = utterance {
        if (!hasMoreJokes()) {
            throw NoMoreJokesException()
        }

        getJokeUtterance(jokes[jokeCounter])
        jokeCounter++
    }

    fun hasMoreJokes(): Boolean {
        return jokeCounter < jokes.size - 1
    }

    fun reset() {
        jokeCounter = 0
    }


}

private data class Joke(val setup: String, val punchline: String)

class NoMoreJokesException : Exception()


