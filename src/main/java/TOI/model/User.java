package TOI.model;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-8
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class User {
    public String name;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String sessionKey;


}