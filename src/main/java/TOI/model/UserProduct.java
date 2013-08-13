package TOI.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-11
 * Time: 上午11:58
 * To change this template use File | Settings | File Templates.
 */
public class UserProduct {
    User user;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;
    List<String> pics;
    int pid;
    String tid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPics() {
        StringBuilder sb=new StringBuilder();
        for(String pic:pics)
        sb.append(pic+",");
        return sb.toString();
    }
    public List<String> getPicList() {

        return this.pics;
    }


    public void setPics(List<String> pics) {
//        Collections.addAll(this.pics, pics.split(","));
        this.pics=pics;
    }
    public void setPics(String pics) {
        List<String> tmp=new ArrayList<String>();
        Collections.addAll(tmp, pics.split(","));
        this.pics=tmp;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
