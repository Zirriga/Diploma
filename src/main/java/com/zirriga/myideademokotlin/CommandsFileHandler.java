package com.zirriga.myideademokotlin;

import com.zirriga.ui.Commands;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CommandsFileHandler {
    public static String path = "C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserCommands.json";

    public static void appendCommand2File(Commands command) {
        String jsonString = getFileFromResource();
        JSONArray obj = new JSONArray(jsonString);
        JSONObject value2Append = new JSONObject(command);
        obj.put(value2Append);
        write2File(obj.toString());
    }

    public static void removeCommandFromFile(int index) { //удаление команды
        String jsonString = getFileFromResource();
        JSONArray obj = new JSONArray(jsonString);
        obj.remove(index);
        write2File(obj.toString());
    }

    public static List<Commands> getAllCommands() { //получает все команды
        List<Commands> commands = new ArrayList<>();
        String jsonString = getFileFromResource();
        JSONArray objArr = new JSONArray(jsonString);
        for (int i = 0; i < objArr.length(); i++) {
            JSONObject obj = objArr.getJSONObject(i);
            Commands command = new Commands(obj.getString("name"),
                    obj.getString("action"),
                    obj.getString("command"),
                    obj.getBoolean("used"));
            commands.add(command);
        }
        return commands;
    }

    public static void writeAllCommands2File(List<Commands> commands) { //полностью переписывает файл
        JSONArray objArr = new JSONArray(commands);
        write2File(objArr.toString());
    }

    private static String getFileFromResource() {
        File fileUser = new File(path);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("file not found! ");
        }
        // the stream holding the file content
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }

    public static void write2File(String jsonContents) {
        try (FileWriter writer = new FileWriter(path, false)) //перезаписывается
        {
            writer.write(jsonContents);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
