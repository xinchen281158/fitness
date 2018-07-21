package com.example.starxin.fitness.domain;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Sean on 17/5/22.
 */

public class KeepNoteEntity extends BmobObject {

    private String date;//日期
    private Float exerciseDuration;//锻炼时长
    private Float runLength;// 跑步长度

    @Override
    public String toString() {
        return "KeepNoteEntity{" +
                "date='" + date + '\'' +
                ", exerciseDuration=" + exerciseDuration +
                ", runLength=" + runLength +
                ", sitUps=" + sitUps +
                ", sportsApparatusTimes=" + sportsApparatusTimes +
                ", others='" + others + '\'' +
                ", pictures=" + pictures +
                ", userId='" + userId + '\'' +
                '}';
    }

    private Integer sitUps;//仰卧起坐
    private Integer sportsApparatusTimes;// 力量器械组数
    private String others; //其他信息
    private List<String> pictures; // 图片，信息，目前只支持传一个
    private String userId; // 当前用户id。也就是这条记录属于哪个用户

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(Float exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public Float getRunLength() {
        return runLength;
    }

    public void setRunLength(Float runLength) {
        this.runLength = runLength;
    }

    public Integer getSitUps() {
        return sitUps;
    }

    public void setSitUps(Integer sitUps) {
        this.sitUps = sitUps;
    }

    public Integer getSportsApparatusTimes() {
        return sportsApparatusTimes;
    }

    public void setSportsApparatusTimes(Integer sportsApparatusTimes) {
        this.sportsApparatusTimes = sportsApparatusTimes;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
