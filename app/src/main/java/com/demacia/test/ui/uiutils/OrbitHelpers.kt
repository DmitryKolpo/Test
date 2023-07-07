package com.demacia.test.ui.uiutils

import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

fun <STATE : Any, EFFECT : Any> ContainerHost<STATE, EFFECT>.postEffect(effect: EFFECT) {
    intent {
        postSideEffect(effect)
    }
}