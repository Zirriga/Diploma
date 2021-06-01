package com.zirriga.ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Screen extends JFrame {
    private String[] languages = {"RU","EN"};
    private String[] inputMethods = {"Button", "Hot keys"};
    private JPanel TopPanel;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JList listCommands;
    private JTextField textAction;
    private JTextField textCommand;
    private JCheckBox usedCheckBox;
    private JButton createButton;
    private JButton changeButton;
    private JComboBox comboBoxLanguage = new JComboBox(languages);
    private JComboBox comboBoxInputMethod = new JComboBox(inputMethods);
    private JPanel panelMain;
    private ArrayList<Commands> commands;
    private DefaultListModel listCommandsModel;

    Screen() {
        super("ZirrigaVoice");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        commands = new ArrayList<Commands>();
        listCommandsModel = new DefaultListModel();
        listCommands.setModel(listCommandsModel);
        changeButton.setEnabled(false);
        comboBoxLanguage.setEditable(false);
        comboBoxInputMethod.setEditable(false);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Commands c = new Commands(
                        textAction.getText(),
                        textCommand.getText(),
                        usedCheckBox.isSelected()
                );
                commands.add(c);
                refreshCommandsList();
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int commandsNumber = listCommands.getSelectedIndex();
                if (commandsNumber >= 0) {
                    Commands c = commands.get(commandsNumber);
                    c.setAction(textAction.getText());
                    c.setCommand(textCommand.getText());
                    c.setUsed(usedCheckBox.isSelected());
                    refreshCommandsList();
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
                    textAction.setText(c.getAction());
                    textCommand.setText(c.getCommand());
                    usedCheckBox.setSelected(c.getUsed());
                    changeButton.setEnabled(true);
                }
            }
        });

    }

    public void refreshCommandsList() {
        listCommandsModel.removeAllElements();
        System.out.println("all removed");
        for (Commands c : commands) {
            System.out.println("ADDING COMMAND TO LIST ACTION:" + c.getAction() + " COMMAND:" + c.getCommand());
            listCommandsModel.addElement(c.getAction());
        }
    }

    public void addCommand(Commands c) {
        commands.add(c);
        refreshCommandsList();
    }

    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.setVisible(true);

        com.zirriga.ui.Commands test1 = new Commands("Action1", "test1", true);
        com.zirriga.ui.Commands test2 = new Commands("Action2", "test2", false);
        screen.addCommand(test1);
        screen.addCommand(test2);
    }
}
