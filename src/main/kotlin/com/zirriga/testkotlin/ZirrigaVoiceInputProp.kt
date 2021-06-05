package com.zirriga.testkotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.zirriga.ui.Properties


class ZirrigaVoiceInputProp: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        var properties = Properties();
        properties.screenVisual()
    }

}