package furhatos.app.storyteller

import furhatos.app.storyteller.flow.Idle
import furhatos.skills.Skill
import furhatos.flow.kotlin.Flow

class StorytellerSkill : Skill() {
    override fun start() {
        Flow().run(Idle)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
