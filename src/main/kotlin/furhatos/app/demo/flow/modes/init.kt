package furhatos.app.demo.flow

import furhatos.app.demo.personas.DefaultPhrases
import furhatos.app.demo.personas.Persona
import furhatos.app.demo.personas.Phrases
import furhatos.app.demo.personas.setPersona
import furhatos.app.demo.PRELOAD_WIKIDATA_ENTITIES
import furhatos.event.EventSystem
import furhatos.event.senses.SenseInteractionSpaces
import furhatos.event.senses.SenseUserEnter
import furhatos.event.senses.SenseUserLeave
import furhatos.flow.kotlin.*
import furhatos.nlu.wikidata.City
import furhatos.nlu.wikidata.Film
import furhatos.nlu.wikidata.MusicArtist
import furhatos.nlu.wikidata.MusicGenre
import furhatos.records.Ellipse
import furhatos.records.Location
import furhatos.records.Space
import furhatos.skills.EngagementPolicy
import furhatos.skills.SimpleEngagementPolicy
import furhatos.skills.UserManager
import furhatos.util.CommonUtils
import furhatos.util.Language
import java.util.*
import java.util.stream.Collectors

val init = state {
    // Currently using a button as an indicator for the operator ;-)
    if (PRELOAD_WIKIDATA_ENTITIES) {
        onButton("Preloading intents, hold on") {}
    }
    else {
        onButton("Loading ...") {}
    }

    onEntry {
        /* The WikiData entities used in chat are quite large and thus you
            probably don't want to preload them during development iterations,
            unless you are working on the chit chat.
         */
        if (PRELOAD_WIKIDATA_ENTITIES) {
            val lang = Language.ENGLISH_US
            City().preload(lang)
            Film().preload(lang)
            MusicArtist().preload(lang)
            MusicGenre().preload(lang)
        }

        users.setSimpleEngagementPolicy(1.2, 1.7, 3)
        furhat.setPersona(Persona.list.first(), initial = true)
        goto(Sleeping)
    }
}