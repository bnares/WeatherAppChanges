package controllers;

public abstract class Weather {

    private String error;



    public String getError() {
        return error;
    }

    protected void setError(String error) {
        this.error = error;
    }

    
}
