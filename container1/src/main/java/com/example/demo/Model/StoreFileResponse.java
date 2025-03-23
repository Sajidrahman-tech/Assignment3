package com.example.demo.Model;

public class StoreFileResponse {

    private String file;
    private String message;

    public StoreFileResponse(String file, String message) {
        this.file = file;
        this.message = message;

    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
