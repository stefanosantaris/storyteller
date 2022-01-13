package furhatos.app.storyteller.utils

import furhatos.flow.kotlin.Utterance
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

    private fun getJokeUtterance(joke: Joke): Utterance {

        return utterance {
            +joke.setup
            +delay(1000)
            +joke.punchline
            +delay(500)
        }
    }

    fun getNextJoke(): Utterance {
        if (!hasMoreJokes()) {
            throw NoMoreJokesException()
        }

        val nextJoke = getJokeUtterance(jokes[jokeCounter])

        jokeCounter++

        return nextJoke
    }

    fun hasMoreJokes(): Boolean {
        return jokeCounter < jokes.size
    }

    fun reset() {
        jokeCounter = 0
    }
}

private data class Joke(val setup: String, val punchline: String)

class NoMoreJokesException : Exception()
