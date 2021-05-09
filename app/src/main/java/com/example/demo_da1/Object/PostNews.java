package com.example.demo_da1.Object;

import com.google.gson.annotations.SerializedName;

public class PostNews {
    public String id,tieude,duongdan,anh,thoigian;

    public PostNews(String id, String tieude, String duongdan, String anh, String thoigian) {
        this.id = id;
        this.tieude = tieude;
        this.duongdan = duongdan;
        this.anh = anh;
        this.thoigian = thoigian;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PostNews{" +
                "tieude='" + tieude + '\'' +
                ", duongdan='" + duongdan + '\'' +
                ", anh='" + anh + '\'' +
                ", thoigian='" + thoigian + '\'' +
                '}';
    }

    public PostNews(String tieude, String duongdan, String anh, String thoigian) {
        this.tieude = tieude;
        this.duongdan = duongdan;
        this.anh = anh;
        this.thoigian = thoigian;
    }
@SerializedName("body")
    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
}
