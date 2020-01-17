package com.mlenterman.simpleevents;

public class Event{
    private String message;
    
    public Event(){
        this.message = "";
    }
    
    public Event(String message){
        this.message = message;
    }
    
    public String getMessage(){
        return message;
    }
    
    public void setMessage(String message){
        this.message = message;
    }
}
