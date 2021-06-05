package com.zirriga.testkotlin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.zirriga.ui.Screen
import org.apache.tools.ant.taskdefs.email.Message
import org.jdesktop.swingx.graphics.BlendComposite

class ZirrigaProperties: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        var screen = Screen();
        screen.screenVisual()
    }

    private fun getAction (e: AnActionEvent){
        //val name=
          //  Messages.showInputDialog(e.project, "Enter your action", "MyIdeaDemo Data", Messages.getQuestionIcon())
    }
}