package furhatos.app.storyteller

import furhatos.app.storyteller.flow.*
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class StorytellerSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
