package com.zirriga.ui;

import com.zirriga.myideademokotlin.CommandsFileHandler;
import com.zirriga.myideademokotlin.IDEActionsParser;
import com.zirriga.testkotlin.ActionHandler;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Screen extends JFrame {
    private List<String> ideActions = new ArrayList<>();
    private Map<String, String> actions = new HashMap<>();
    private JPanel TopPanel;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JList listCommands;
    private JTextField textCommand;
    private JCheckBox usedCheckBox;
    private JButton createButton;
    private JButton changeButton;
    private JPanel panelMain;
    private JTextField textName;
    private JComboBox cbAction;
    private JScrollPane scrollPane;
    private JButton deleteButton;
    private ArrayList<Commands> commands;
    private DefaultListModel listCommandsModel;

    public File fileUser = new File("C://Users//Polya//IdeaProjects//test_test_gradle//src//main//resources//ActionsFile//UserActions.txt");
    public boolean changeFlag = false;


    public Screen() throws IOException {
        super("ZirrigaVoice");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();

        commands = new ArrayList<>();
        listCommandsModel = new DefaultListModel();
        listCommands.setModel(listCommandsModel);
        changeButton.setEnabled(false);
        createButton.setEnabled(false);
        deleteButton.setEnabled(false);

        actions = IDEActionsParser.init();

        ideActions.addAll(actions.values());

        String[] array = new String[ideActions.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = ideActions.get(i);
        }

        for (String i : array) {
            cbAction.addItem(i);
        }

        AutoCompletion.enable(cbAction);

        textName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if (textName.getText().equals("") || textCommand.getText().equals("")) {
                    createButton.setEnabled(false);
                    changeButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    createButton.setEnabled(true);
                    if (changeFlag) {
                        changeButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    }
                }
            }
        });

        textCommand.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if (textCommand.getText().equals("") || textName.getText().equals("")) {
                    createButton.setEnabled(false);
                    changeButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    createButton.setEnabled(true);
                    if (changeFlag) {
                        changeButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    }
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Commands c = new Commands(
                        textName.getText(),
                        cbAction.getSelectedItem().toString(),
                        textCommand.getText().toLowerCase(Locale.ROOT),
                        usedCheckBox.isSelected()
                );
                changeFlag = false;
                commands.add(c);
                CommandsFileHandler.appendCommand2File(c);
                refreshCommandsList();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = listCommands.getSelectedIndex();
                listCommandsModel.remove(i);
                changeFlag = false;
                commands.remove(i);
                CommandsFileHandler.removeCommandFromFile(i);
                refreshCommandsList();
                changeFlag = false;
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int commandsNumber = listCommands.getSelectedIndex();
                if (commandsNumber >= 0) {
                    Commands c = commands.get(commandsNumber);
                    c.setName(textName.getText());
                    c.setAction(cbAction.getSelectedItem().toString());
                    c.setCommand(textCommand.getText().toLowerCase(Locale.ROOT));
                    c.setUsed(usedCheckBox.isSelected());
                    CommandsFileHandler.removeCommandFromFile(commandsNumber);
                    CommandsFileHandler.appendCommand2File(commands.get(commandsNumber));
                    refreshCommandsList();
                    changeFlag = false;
                }
            }
        });

        usedCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });

        listCommands.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int commandNumber = listCommands.getSelectedIndex();
                if (commandNumber >= 0) {
                    Commands c = commands.get(commandNumber);
                    textName.setText(c.getName());
                    cbAction.setSelectedItem(c.getAction());
                    textCommand.setText(c.getCommand());
                    usedCheckBox.setSelected(c.getUsed());
                    changeButton.setEnabled(true);
                    changeFlag = true;
                }
            }
        });

    }

    public void refreshCommandsList() {
        listCommandsModel.removeAllElements();
        System.out.println("all removed");
        for (Commands c : commands) {
           // System.out.println("ADDING COMMAND TO LIST ACTION:" + c.getAction() + " COMMAND:" + c.getCommand());
            listCommandsModel.addElement(c.getName());
        }
    }

    public void addCommand(Commands c) {
        commands.add(c);
        refreshCommandsList();
    }

    public void screenVisual() {
        this.setVisible(true);
        this.setSize(710, 500);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               // System.out.println("windowClosing");
               // System.out.println(commands);
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        for (int i = 0; i < CommandsFileHandler.getAllCommands().size(); i++) {
            Commands c = new Commands(
                    CommandsFileHandler.getAllCommands().get(i).getName(),
                    CommandsFileHandler.getAllCommands().get(i).getAction(),
                    CommandsFileHandler.getAllCommands().get(i).getCommand(),
                    CommandsFileHandler.getAllCommands().get(i).getUsed()
            );
            commands.add(c);
            refreshCommandsList();
        }
    }
}
