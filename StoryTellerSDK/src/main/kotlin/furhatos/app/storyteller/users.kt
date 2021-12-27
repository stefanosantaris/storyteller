package furhatos.app.storyteller.flow

import furhatos.flow.kotlin.UserDataDelegate
import furhatos.records.User

var User.name: String? by UserDataDelegate()
var User.hasPlayed: Boolean? by UserDataDelegate()
var User.wantsPlay: Boolean? by UserDataDelegate()

/*
Scene memory
 */
var User.visitedAlley : Boolean? by UserDataDelegate()
var User.visitedTavern : Boolean? by UserDataDelegate()
var User.visitedTownSquare : Boolean? by UserDataDelegate()
var User.visitedWoman : Boolean? by UserDataDelegate()
var User.visitedBasement : Boolean? by UserDataDelegate()

var User.talkedToBartender : Boolean? by UserDataDelegate()
var User.talkedToWhisperingMen : Boolean? by UserDataDelegate()
