package com.zirriga.ui;

import com.zirriga.testkotlin.ActionHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class Screen extends JFrame {
    final String [] ideActions = {"dsfsdsdasdff", "dsfs111111", "22dsfsdf"};
    //public static String gAction = "test";
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
    private ArrayList<Commands> commands;
    private DefaultListModel listCommandsModel;

    public Screen() {
        super("ZirrigaVoice");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();
        commands = new ArrayList<>();
        listCommandsModel = new DefaultListModel();
        listCommands.setModel(listCommandsModel);
        changeButton.setEnabled(false);


        for(String i : ideActions){
            cbAction.addItem(i);
        }

        AutoCompletion.enable(cbAction);
/*
        cbAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                if (e.getSource() == cbAction) {
                    gAction = Objects.requireNonNull(cbAction.getSelectedItem()).toString();
                    System.out.println(gAction);
                }
            }
        });

 */

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Commands c = new Commands(
                        textName.getText(),
                        cbAction.getSelectedItem().toString(),
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
                    c.setName(textName.getText());
                    c.setAction(cbAction.getSelectedItem().toString());
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
                    textName.setText(c.getName());
                    cbAction.setSelectedItem(c.getAction());
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
            listCommandsModel.addElement(c.getName());
        }
    }

    public void addCommand(Commands c) {
        commands.add(c);
        refreshCommandsList();
    }

    public void screenVisual() {
        this.setVisible(true);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing");
                System.out.println(commands);
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        com.zirriga.ui.Commands test1 = new Commands("Testaction1", "Action1", "test1", true);
        com.zirriga.ui.Commands test2 = new Commands("Testaction2","Action2", "test2", false);
        this.addCommand(test1);
        this.addCommand(test2);
    }
}
