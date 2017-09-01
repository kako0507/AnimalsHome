package com.james.animalshome;

/**
 * Created by James on 2017/8/31.
 */

public class Animals {
    String TAG = Animals.class.getSimpleName();
    String name;
    String pic;
    String tid;
    String acceptnum;
    String webId;

    public Animals(String name, String pic, String tid, String acceptnum, String id) {
        this.name = name;
        this.pic = pic;
        this.tid = tid;
        this.acceptnum = acceptnum;
        this.webId = id;
    }

    public String getName() {
        return name;
    }

    public String getPic() {
        return pic;
    }

    public String getTid() {
        return tid;
    }

    public String getAcceptnum() {
        return acceptnum;
    }

    public String getWebId() {
        return webId;
    }

}
