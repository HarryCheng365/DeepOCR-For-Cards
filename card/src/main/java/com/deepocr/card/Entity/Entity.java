package com.deepocr.card.Entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@javax.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="date",columnDefinition = "varchar(255) CHARACTER SET utf8 DEFAULT NULL")
    private String date;
    @Column(name="feedback",columnDefinition = "varchar(255) CHARACTER SET utf8 DEFAULT NULL")
    private String feedback;
    @Column(name="guid",columnDefinition = "varchar(255) CHARACTER SET utf8 DEFAULT NULL")
    private String guid;
    @Column(name="path",columnDefinition = "varchar(255) CHARACTER SET utf8 DEFAULT NULL")
    private String path;
    @Column(name="user",columnDefinition="int(11) default -1")
    private int user=0;




    public Entity(){

    }

    public Entity(String feedback, String guid,String path, int user  ){

        if(feedback==null||feedback.equals(""))
            this.feedback="null";
        else
            this.feedback=feedback;
        Date temp =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date=sdf.format(temp);
        this.guid=guid;
        this.path=path;
        this.user=user;




    }

    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getGuid(){
        return this.guid;
    }
    public void setGuid(String guid){
        this.guid=guid;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date=date;
    }
    public int getUser(){
        return this.user;
    }
    public void setUser(int user){
        this.user=user;
    }
    public String getPath(){
        return this.path;
    }
    public void setPath(String path){
        this.path=path;
    }
    public String getFeedback(){
        return this.feedback;
    }
    public void setFeedback(String feedback){
        this.feedback=feedback;
    }

}
