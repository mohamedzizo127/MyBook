package com.mybook.mohamed.mybook.Model;

public class BookDataObject {
    private String name,author;
    private int rate;
    private int id;
    private int isDone;
    private String date;
    private String filePath;

    public BookDataObject() {
    }

    public BookDataObject(String name, String author, int rate, int id, int isDone, String date, String filePath) {
        this.name = name;
        this.filePath = filePath;
        this.author = author;
        this.rate = rate;
        this.id = id;
        this.isDone = isDone;
        this.date = date;
    }

    public BookDataObject(String name, String author, int rate, int isDone, String date, String filePath) {
        this.name = name;
        this.filePath = filePath;
        this.author = author;
        this.rate = rate;
        this.isDone = isDone;
        this.date = date;

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String auther) {
        this.author = auther;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int done) {
        isDone = done;
    }
}
