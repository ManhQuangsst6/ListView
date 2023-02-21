package com.example.listview;

public class Contact {
    private  int id;
    private  String name;
    private  int tuoi;
    private String img;

    public Contact(int id, String img,String name, int tuoi) {
        this.id = id;
        this.name = name;
        this.tuoi = tuoi;
        this.img=img;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getName() {
        return name;
    }

    public int getTuoi() {
        return tuoi;
    }

    public String getImg( ){return img;}
    public void setImg(String name) {
        this.img = name;
    }


}
