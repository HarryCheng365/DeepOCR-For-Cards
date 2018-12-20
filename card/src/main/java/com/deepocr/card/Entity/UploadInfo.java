package com.deepocr.card.Entity;

import org.springframework.web.multipart.MultipartFile;

public class UploadInfo {
    private String guid;
    private int user;
    private MultipartFile file;

    public UploadInfo(){
    }
    public UploadInfo(String guid,int user,MultipartFile file){
        this.guid=guid;
        this.user=user;
        this.file=file;

    }
    public void setGuid(String guid){
        this.guid=guid;
    }
    public String getGuid(){
        return this.guid;
    }
    public void setUser(int user){
        this.user=user;

    }
    public int getUser(){
        return this.user;
    }
    public void setFile(MultipartFile file){
        this.file=file;
    }
    public MultipartFile getFile(){
        return this.file;
    }
}
