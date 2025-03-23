package com.example.container2.Model;

public class Output {
    String file;
    int sum;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


    public Output(String file, int sum) {
        this.file = file;
        this.sum = sum;
    }
}
