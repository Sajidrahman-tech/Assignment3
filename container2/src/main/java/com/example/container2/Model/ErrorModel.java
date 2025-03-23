package com.example.container2.Model;

public class ErrorModel {
    String file;
    String error;

    public ErrorModel(String file, String error) {
        this.file = file;
        this.error = error;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
