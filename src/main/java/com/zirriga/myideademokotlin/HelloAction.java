package com.zirriga.myideademokotlin;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import static com.intellij.openapi.actionSystem.IdeActions.ACTION_REDO;
import static com.intellij.openapi.actionSystem.IdeActions.ACTION_UNDO;


public class HelloAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Messages.showMessageDialog("hello", "Zirriga", Messages.getQuestionIcon());
        //ActionManager.getInstance().getAction(ACTION_UNDO).actionPerformed(e);
        MicrophoneRecognition mICrEC = new MicrophoneRecognition();
        try {
            mICrEC.main(null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (mICrEC.resultVoice.equals("отмена")) {
            ActionManager.getInstance().getAction(ACTION_UNDO).actionPerformed(e);
       }
        if (mICrEC.resultVoice.equals("повтор")) {
            ActionManager.getInstance().getAction(ACTION_REDO).actionPerformed(e);
        }


      // System.out.println("привет");
    }

    @Override
    public boolean isDumbAware() {
        return false;
    }

}
