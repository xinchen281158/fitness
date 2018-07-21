package com.example.starxin.fitness.domain;

/**
 * Created by StarXin on 2018/5/19.
 */

public class News {
    private String image;//图片
    private String title;//标题
    private String text;//内容
    private String time;//时间
    private String link;//链接
    private String tag;//标签
    private String body;//身材
    private String h;//H

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public News() {

    }

    @Override
    public String toString() {
        return "News{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", link='" + link + '\'' +
                ", tag='" + tag + '\'' +
                ", body='" + body + '\'' +
                ", h='" + h + '\'' +
                '}';
    }

    public News(String image, String title, String text, String time) {
        this.image = image;
        this.title = title;
        this.text = text;
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
