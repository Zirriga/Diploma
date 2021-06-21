package com.zirriga.myideademokotlin;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class IDEActionsParser {


    private static String getFileFromResource() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//ListOfIdeActions.json");
        } catch (Exception e) {
            throw new IllegalArgumentException("file not found! ");
        }
        // the stream holding the file content
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }

    public static Map<String, String> init() {
        String jsonString = getFileFromResource();
        JSONObject ideActions = new JSONObject(jsonString);
        Map<String, String> actions = new HashMap<String, String>();
        Iterator<String> keys = ideActions.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            actions.put(key, ideActions.getString(key));
        }
        return actions;
    }
}
