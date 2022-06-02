package com.example.bulatdnev.ui.home.modal;

public class HomeModal {
    private String id;
    private String title;
    private long time;
    private String content;



    public HomeModal() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public HomeModal(String id,
                     String title,
                     long time,
                     String content) {

        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;


    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
