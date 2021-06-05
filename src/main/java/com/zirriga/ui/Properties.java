package com.zirriga.ui;

import com.intellij.openapi.ui.ComboBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Properties extends JFrame implements ActionListener {
    final String [] languages = {"ru-RU", "en-GB", "en-US", "fr-FR", "ar-IL", "it-IT", "bg-BG", "pl-PL", "yue-Hant-HK", "nl-BE", "af-ZA", "hi-IN", "es-CO", "uk-UA"};
    final String [] inputMethods = {"button", "hot keys", "hot", "ho", "hor", "horse", "horses", "hses","button"};

    public static String gInputMethod = "test";
    public static String gLanguage = "ru-RU";

    private JPanel panelTop;
    private JComboBox cbLanguage;
    private JComboBox cbInputMethod;
    private JPanel panelMain;

    public Properties(){
        super("ZirrigaVoice");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.pack();

        for(String i : languages){
            cbLanguage.addItem(i);
        }

        for(String i : inputMethods){
            cbInputMethod.addItem(i);
        }

        cbLanguage.addActionListener(this);
        cbInputMethod.addActionListener(this);
        cbLanguage.setSelectedIndex(0);
        cbInputMethod.setSelectedIndex(0);
        AutoCompletion.enable(cbLanguage);
        AutoCompletion.enable(cbInputMethod);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cbInputMethod){
            gInputMethod = Objects.requireNonNull(cbInputMethod.getSelectedItem()).toString();
            System.out.println(gInputMethod);
        }
        if (e.getSource() == cbLanguage){
            gLanguage = Objects.requireNonNull(cbLanguage.getSelectedItem()).toString();
            System.out.println(gLanguage);
        }

    }

    public void screenVisual() {
        this.setVisible(true);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing");
                setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
    }
}
