package com.zirriga.testkotlin

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.IdeActions.ACTION_UNDO
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.Messages.showMessageDialog
import com.zirriga.myideademokotlin.CommandsFileHandler
import com.zirriga.myideademokotlin.IDEActionsParser
import com.zirriga.myideademokotlin.MicrophoneRecognition
import com.zirriga.ui.Commands
import com.zirriga.ui.Screen
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList

class ActionHandler : AnAction() {
    var INPUTMETHOD = ""
    private var commands: ArrayList<Commands>? = ArrayList()

    private var actionsPropsIDE: Map<String, String> =
        HashMap()

    private fun readActionsFile() {
        actionsPropsIDE = IDEActionsParser.init()
        var commandsfromhandler = CommandsFileHandler.getAllCommands()

        for (i in 0 until commandsfromhandler.size) {
            val c = Commands(
                CommandsFileHandler.getAllCommands()[i].name,
                CommandsFileHandler.getAllCommands()[i].action,
                CommandsFileHandler.getAllCommands()[i].command,
                CommandsFileHandler.getAllCommands()[i].used
            )
            commands?.add(c)
        }
        var fileUser =
            File("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserProp.json")

        fun getFileFromResource(): String {
            var inputStream: FileInputStream? = null
            inputStream = try {
                FileInputStream(fileUser)
            } catch (e: Exception) {
                throw IllegalArgumentException("file not found! ")
            }
            return BufferedReader(
                InputStreamReader(inputStream, StandardCharsets.UTF_8)
            ).lines()
                .collect(Collectors.joining("\n"))
        }

        fun init() {
            val jsonString = getFileFromResource()
            val obj = JSONObject(jsonString)
            INPUTMETHOD = obj.getString("INPUT_METHOD")
        }
        init()
    }

    override fun actionPerformed(e: AnActionEvent) {
        readActionsFile()
        if (INPUTMETHOD == "Button") {
            try {
                MicrophoneRecognition.main(null)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            println(MicrophoneRecognition.resultVoice)
            for (i in 0 until commands?.size!!) {
                if (MicrophoneRecognition.resultVoice.equals(commands!![i].command) && commands!![i].used == true) {
                    print(commands!![i].action)
                    ActionManager.getInstance().getAction((commands!![i].action)).actionPerformed(e);
                    break
                }
            }
            MicrophoneRecognition.resultVoice = ""
            commands!!.clear()
        }
    }
}