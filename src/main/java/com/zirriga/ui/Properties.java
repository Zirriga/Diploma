package com.zirriga.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Properties extends JFrame implements ActionListener {
    final String[] languages = {"ru-RU", "en-GB", "en-US", "fr-FR", "ar-IL", "it-IT", "bg-BG", "pl-PL", "yue-Hant-HK",
            "nl-BE", "af-ZA", "hi-IN", "es-CO", "uk-UA", "de-DE", "el-GR", "it-IT-", "ja-JP", "ko-KR", "pl-PL", "vi-VN"
    };
    final String[] inputMethods = {"Button", "Hotkey (Alt+V)"};

    public static String gInputMethod = "UserProp.LANGUAGE";
    public static String gLanguage = "ru-RU";
    public static String LANGUAGE = "ru-RU";
    public static String INPUT_METHOD = "Hotkey (Alt+V)";

    private JPanel panelTop;
    private JComboBox cbLanguage;
    private JComboBox cbInputMethod;
    private JPanel panelMain;
    private boolean fileRead = false;

    public static File fileUser = new File("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserProp.json");

    // Считываем json
    private static String getFileFromResource() {
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

    //public static void init() {
    public static void init() {
        String jsonString = getFileFromResource();
        JSONObject obj = new JSONObject(jsonString);
        LANGUAGE = obj.getString("LANGUAGE");
        INPUT_METHOD = obj.getString("INPUT_METHOD");
    }

    public Properties() throws IOException, ParseException {
        super("ZirrigaVoice");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();

        init();

        for (String i : languages) {
            cbLanguage.addItem(i);
        }

        for (String i : inputMethods) {
            cbInputMethod.addItem(i);
        }

        cbLanguage.addActionListener(this);
        cbInputMethod.addActionListener(this);

        AutoCompletion.enable(cbLanguage);
        AutoCompletion.enable(cbInputMethod);

        cbLanguage.setSelectedItem(LANGUAGE);
        cbInputMethod.setSelectedItem(INPUT_METHOD);

        fileRead = true;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gInputMethod = (String) cbInputMethod.getSelectedItem();
        gLanguage = (String) cbLanguage.getSelectedItem();
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);
        if (fileRead) {
            String jsonString = getFileFromResource();
            JSONObject obj = new JSONObject(jsonString);
            obj.put("LANGUAGE", gLanguage);
            obj.put("INPUT_METHOD", gInputMethod);
            String props2Save = obj.toString();

            loader.write(props2Save);
        }

    }

    public void screenVisual() throws IOException {
        this.setVisible(true);
        this.setSize(500, 300);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
    }

    public static class loader {

        public static void write(String gLanguage, String gInputMethod) {
            try (FileWriter writer = new FileWriter("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserProp.txt", false)) //перезаписывается
            {
                String vars = (gLanguage + " " + gInputMethod);
                writer.write(vars);
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        public static String read() {
            String prop = "";
            try (BufferedReader reader = new BufferedReader(new FileReader("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserProp.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    prop = (prop + line + " ");
                }
                return prop;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return "";
        }

        public static List<String> splitProps() {
            List<String> properties = Arrays.asList(read().split(" ").clone());
            return properties;
        }

        public static void write(String jsonContents) {
            try (FileWriter writer = new FileWriter("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserProp.json", false)) //перезаписывается
            {
                writer.write(jsonContents);
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
