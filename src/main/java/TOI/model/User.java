package TOI.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-8
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class User {
    public  int id;
    public String name;
    public int role;

    public String getVM() {
        return VM;
    }

    public void setVM(String VM) {
        this.VM = VM;
    }

    public String VM;
    public Date ctime;
    public Date mtime;


    //tb relate
  public      String  tbName;
    public   String tbToken;
    public   String  tbPicCategoryId;
    public String  tbExpressId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public String getTbToken() {
        return tbToken;
    }

    public void setTbToken(String tbToken) {
        this.tbToken = tbToken;
    }

    public String getTbPicCategoryId() {
        return tbPicCategoryId;
    }

    public void setTbPicCategoryId(String tbPicCategoryId) {
        this.tbPicCategoryId = tbPicCategoryId;
    }

    public String getTbExpressId() {
        return tbExpressId;
    }

    public void setTbExpressId(String tbExpressId) {
        this.tbExpressId = tbExpressId;
    }
}