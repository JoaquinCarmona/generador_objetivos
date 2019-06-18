package com.test;


public class GenerateObjectiveRequest {
    private String[] roles;
    private String activity;

    // getters y setters son necesarios para que el bind por JSON funcione
    public String[] getRoles(){
        return roles;
    }

    public void setRoles(String[] roles){
        this.roles = roles;
    }

    public String getActivity(){
        return activity;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }
}
