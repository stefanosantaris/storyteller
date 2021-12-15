package furhatos.app.storyteller.flow
import furhatos.app.storyteller.robotName
import furhatos.flow.kotlin.*

val FetchUserName : State = state(Interaction) {
    onEntry {
        if(users.current.name == null) {
            random(
                { furhat.ask("And what about you? What is your name?") },
                { furhat.ask("And what is your name?") },
                { furhat.ask("And your name is what?") }
            )
        }
        else {
            goto(Idle)
        }
    }

    onReentry {
        if(users.current.name == null) {
            random(
                { furhat.ask("What is your name?") },
                { furhat.ask("What was your name?") },
                { furhat.ask("Your name is what?") }
            )
        }
        else {
            goto(Idle)
        }
    }

    onNoResponse {
        if(users.current.name == null) {
            random(
                { furhat.say("Hello? Sorry, you might have missed that.")},
                { furhat.say("Hello, somebody out there?") },
                { furhat.say("Did you say something?")}
            )
            furhat.ask("My name is $robotName, what is yours?")
        }
    }
}

val SelfPresent : State = state{
    onEntry {
        random(
            {furhat.say("Hi there. It is a pleasure to meet you. I am $robotName.")},
            {furhat.say("Hello my friend. Glad to meet you! My name is $robotName.")},
            {furhat.say("Hi there. What a pleasure meeting you. My name is $robotName.")}
        )
        goto(FetchUserName)
    }
}

val PresentGame : State = state{
    onEntry {
        random(
            {furhat.say("Hi there. It is a pleasure to meet you. I am $robotName.")},
            {furhat.say("Hello my friend. Glad to meet you! My name is $robotName.")},
            {furhat.say("Hi there. What a pleasure meeting you. My name is $robotName.")}
        )
        goto(FetchUserName)
    }
}