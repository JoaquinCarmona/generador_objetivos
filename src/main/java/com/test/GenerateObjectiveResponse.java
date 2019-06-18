package com.test;


public class GenerateObjectiveResponse {
    private String message;
    private String objective;

    // getters y setters son necesarios para que el bind por JSON funcione
    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getObjective(){
        return objective;
    }

    public void setObjective(String objective){
        this.objective = objective;
    }
}
