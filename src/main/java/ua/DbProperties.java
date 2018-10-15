package ua;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {
    private String url;
    private String user;
    private String password;

    public DbProperties() {
        try(InputStream is = getClass().getClassLoader().getResourceAsStream("db.properties")){
            Properties props = new Properties();
            props.load(is);
            url = props.getProperty("db_connection");
            user = props.getProperty("db_user");
            password = props.getProperty("db_password");
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
