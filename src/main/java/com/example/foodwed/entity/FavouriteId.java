package com.example.foodwed.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavouriteId implements Serializable {

    private String fvid;  // Thêm trường fvid vào khóa phức hợp
    private String userid;
    private String recipeid;

    // Constructor mặc định
    public FavouriteId() {}

    // Constructor với tất cả các trường
    public FavouriteId(String fvid, String userid, String recipeid) {
        this.fvid = fvid;
        this.userid = userid;
        this.recipeid = recipeid;
    }

    // Getter và setter cho fvid
    public String getFvid() {
        return fvid;
    }

    public void setFvid(String fvid) {
        this.fvid = fvid;
    }

    // Getter và setter cho userid
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    // Getter và setter cho recipeid
    public String getRecipeid() {
        return recipeid;
    }

    public void setRecipeid(String recipeid) {
        this.recipeid = recipeid;
    }

    // Override equals và hashCode để so sánh các đối tượng FavouriteId chính xác
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavouriteId that = (FavouriteId) o;
        return Objects.equals(fvid, that.fvid) &&
                Objects.equals(userid, that.userid) &&
                Objects.equals(recipeid, that.recipeid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fvid, userid, recipeid);
    }
}
