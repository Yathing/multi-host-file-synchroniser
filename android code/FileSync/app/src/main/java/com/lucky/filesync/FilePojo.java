package com.lucky.filesync;

public class FilePojo {
    private  String size;
    private  String date;
    private  String filename;
    private  int key;
    private  String numbers;

    public FilePojo(String size, String date, String filename, int key, String numbers) {
        this.size = size;
        this.date = date;
        this.filename = filename;
        this.key = key;
        this.numbers = numbers;
    }

    public String getSize() {
        return size;
    }

    public String getDate() {
        return date;
    }

    public String getFilename() {
        return filename;
    }

    public int getKey() {
        return key;
    }

    public String getNumbers() {
        return numbers;
    }


}
