package com.zirriga.ui;

public class Commands {
    private String action;
    private String command;
    private Boolean used;

    public Commands (String action, String command, Boolean used){
        this.action = action;
        this.command = command;
        this.used = used;
    }

    public String getAction(){
        return action;
    }

    public void setAction(String action){
        this.action = action;
    }
    //----------------------------------------------
    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }
    //----------------------------------------------
    public Boolean getUsed () {
        return used;
    }

    public void setUsed (Boolean used) {
        this.used = used;
    }
}
