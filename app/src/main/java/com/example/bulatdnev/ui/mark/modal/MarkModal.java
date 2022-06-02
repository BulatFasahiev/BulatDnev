package com.example.bulatdnev.ui.mark.modal;

public class MarkModal {
    private String lesson;
    private long time;
    private String mark;
    private String teacher;



    public MarkModal() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public MarkModal(String lesson,
                     long time,
                     String mark,
                     String teacher) {

        this.lesson = lesson;
        this.time = time;
        this.mark = mark;
        this.teacher = teacher;


    }


    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}

