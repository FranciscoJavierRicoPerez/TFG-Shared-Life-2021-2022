package net.tfg.sharedlife.exception;

public class HomeException extends Exception {
    private String message;
    public HomeException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
