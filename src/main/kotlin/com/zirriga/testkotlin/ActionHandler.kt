package com.zirriga.testkotlin

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.IdeActions.ACTION_UNDO
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.Messages.showMessageDialog
import com.zirriga.myideademokotlin.MicrophoneRecognition
import com.zirriga.ui.Screen
import java.io.File
import java.io.InputStream
import java.lang.Exception
import java.util.*

class ActionHandler : AnAction() {
    var listOfActions: MutableList<String> = mutableListOf()
    var doAction = ACTION_UNDO

    private fun readActionsFile() {
        val lineList: MutableList<String> = mutableListOf()
        val inputStream: InputStream = File("C:\\Users\\Polya\\IdeaProjects\\test_test_gradle\\src\\main\\resources\\ActionsFile\\ListOfIdeActions.txt").inputStream()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }
        lineList.forEach(){
            val rawListOfActions = it.split(" = ") as MutableList<String>
            listOfActions.addAll(rawListOfActions)
            }
        for (i in 0..listOfActions.size-1){
            listOfActions[i] = listOfActions[i].replace(Regex("[;\"]"), "")
        }
        }

    val map = mapOf("test" to 1, "test" to 2, "test" to 3)




    override fun actionPerformed(e: AnActionEvent) {
        readActionsFile()

        val DialogWrapperCall = MyIdeaDataDialogWrapper()
        println(DialogWrapperCall.actionSaved)
        println(DialogWrapperCall.phraseSaved)

        for (i in 0..listOfActions.size-1){
            if(DialogWrapperCall.actionSaved == listOfActions[i]){
                doAction = listOfActions[i-1]
            }
        }

        //Messages.showMessageDialog("hello", "Zirriga", Messages.getQuestionIcon())

        println(doAction)
        println(MicrophoneRecognition.resultVoice)
        try {
            MicrophoneRecognition.main(null)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        if (DialogWrapperCall.phraseSaved == MicrophoneRecognition.resultVoice) {ActionManager.getInstance().getAction((DialogWrapperCall.actionSaved)).actionPerformed(e);}
    }
/*
    companion object {
        lateinit var listOfActions: Array<String>
        lateinit var map: Any
    }

 */
}
//Messages.showMessageDialog(e.project, "Here you can see instructions", "Options", Messages.getInformationIcon())
//ActionManager.getInstance().getAction(ACTION_UNDO).actionPerformed(e)
//if (DialogWrapperCall.phraseSaved == doPhrase) {ActionManager.getInstance().getAction(doAction).actionPerformed(e);}