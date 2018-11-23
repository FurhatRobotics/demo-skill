package furhatos.app.demo.flow.autoBehavior

import furhatos.app.demo.util.*
import furhatos.flow.kotlin.*

fun autoBehavior(shouldReset : Boolean = true) : State = state {
    onEntry {
        if (shouldReset) {
            furhat.attendNobody()
        }
    }

    onEvent<LookStraight> {
        goto(attendingLocation(randomMovements = it.randomMovements))
    }

    onEvent<StopAutoBehavior> {
        goto(autoBehavior(shouldReset = false))
    }

    onEvent<LookAround> {
        goto(lookingAround())
    }

    onEvent<AttendUsers> {
        goto(attendingUsers(it.shouldAlterAttentionOnSpeech))
    }

    onEvent<AttendLocation> {
        goto(attendingLocation(location = it.location))
    }
}

