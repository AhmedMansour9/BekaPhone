package com.bekya.bekyaa.Model;

public class Gallery {

    String img,link,cit_ar,cit_en,cit_id,title;

    public Gallery(){}

    public Gallery(String img, String link, String cit_ar, String cit_en, String cit_id, String title) {
        this.img = img;
        this.link = link;
        this.cit_ar = cit_ar;
        this.cit_en = cit_en;
        this.cit_id = cit_id;
        this.title = title;
    }

    public String getCit_ar() {
        return cit_ar;
    }

    public void setCit_ar(String cit_ar) {
        this.cit_ar = cit_ar;
    }

    public String getCit_en() {
        return cit_en;
    }

    public void setCit_en(String cit_en) {
        this.cit_en = cit_en;
    }

    public String getCit_id() {
        return cit_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCit_id(String cit_id) {
        this.cit_id = cit_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
