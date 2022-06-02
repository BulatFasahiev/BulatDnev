package com.example.bulatdnev.ui.mark.modal;

public class MarkModal {
    private String lesson;
    private long time;
    private String mark;
    private String teacher;
    private String Uid;



    public MarkModal() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public MarkModal(String lesson,
                     long time,
                     String mark,
                     String teacher,
                     String Uid) {

        this.lesson = lesson;
        this.time = time;
        this.mark = mark;
        this.teacher = teacher;
        this.Uid = Uid;


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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}

