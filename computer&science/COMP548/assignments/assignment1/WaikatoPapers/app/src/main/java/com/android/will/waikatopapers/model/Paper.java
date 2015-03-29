package com.android.will.waikatopapers.model;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 29/03/15.
 */
public class Paper {

    private int thumbnail_id;
    private String id;
    private String title="";
    private String content="";
    private String lecturer="";
    private String level="";
    private String semester="";

    public Paper(String id) {
        this.id = id;
    }

    public Paper(String id, String title, String content, String lecturer, String level, String semester, int thumbnail_id) {
        this.title = title;
        this.content = content;
        this.lecturer = lecturer;
        this.level = level;
        this.semester = semester;
        this.thumbnail_id = thumbnail_id;
    }

    public int getThumbnail_id() {
        return thumbnail_id;
    }

    public void setThumbnail_id(int thumbnail_id) {
        this.thumbnail_id = thumbnail_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }


}
