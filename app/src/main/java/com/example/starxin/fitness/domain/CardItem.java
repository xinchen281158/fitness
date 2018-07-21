package com.example.starxin.fitness.domain;

/**
 * Created by StarXin on 2018/4/26.
 */

public class CardItem {
    private String image;//图片
    private String text;
    private String avatar;
    private String name;
    private boolean zan;

    @Override
    public String toString() {
        return "CardItem{" +
                "image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", zan=" + zan +
                '}';
    }

    public boolean isZan() {
        return zan;
    }

    public void setZan(boolean zan) {
        this.zan = zan;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }
}
