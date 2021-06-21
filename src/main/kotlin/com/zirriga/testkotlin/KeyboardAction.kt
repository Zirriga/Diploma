package com.zirriga.testkotlin


import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent

import com.intellij.openapi.actionSystem.AnAction
import com.sun.istack.NotNull
import com.zirriga.myideademokotlin.CommandsFileHandler
import com.zirriga.myideademokotlin.IDEActionsParser
import com.zirriga.myideademokotlin.MicrophoneRecognition
import com.zirriga.ui.Commands
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.util.HashMap
import java.util.stream.Collectors

class KeyboardAction : AnAction() {

    override fun update(e: AnActionEvent) {
        // Using the event, evaluate the context, and enable or disable the action.
    }

    var INPUTMETHOD = ""

    var fileUser =
        File("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserProp.json")

    override fun actionPerformed(@NotNull e: AnActionEvent) {
        // Using the event, implement an action. For example, create and show a dialog.
        var iff = false;
        var userActions: MutableMap<String, String> = mutableMapOf()
        var commands: ArrayList<Commands>? = ArrayList()
        var actionsPropsIDE: Map<String, String> =
            HashMap()
        //fun readActionsFile() {
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
        fun getFileFromResource(): String {
            var inputStream: FileInputStream? = null
            inputStream = try {
                FileInputStream(fileUser)
            } catch (e: Exception) {
                throw IllegalArgumentException("file not found! ")
            }
            // the stream holding the file content
            return BufferedReader(
                InputStreamReader(inputStream, StandardCharsets.UTF_8)
            ).lines()
                .collect(Collectors.joining("\n"))
        }

        //public static void init() {
        fun init() {
            val jsonString = getFileFromResource()
            val obj = JSONObject(jsonString)
            INPUTMETHOD = obj.getString("INPUT_METHOD")
        }
        init()
        println(INPUTMETHOD)
        if (INPUTMETHOD == "Hotkey (Alt+V)") {
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