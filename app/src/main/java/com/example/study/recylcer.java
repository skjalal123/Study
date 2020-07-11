package com.example.study;

public class recylcer {
    public String label;
    private String title, author;
    int price;
    private float courseRating;

    public recylcer(String label, String title, String author, int price, float courseRating) {
        this.label = label;
        this.title = title;
        this.author = author;
        this.price = price;
        this.courseRating = courseRating;
    }

    public recylcer() {
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getCourseRating() {
        return courseRating;
    }

    public void setCourseRating() {
        this.courseRating = courseRating;
    }
}
