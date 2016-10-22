package hackathon_16_npt.com.example.nishant.projects;

import java.io.Serializable;

/**
 * Created by Nishant on 10/18/2016.
 */
public class UserLogin implements Serializable {
    private String UserName;
    private String Password;

    @Override
    public String toString() {
        return "ResearchProject{" +
                "Username='" + UserName + '\'' +
                ", Password='" + Password + '\'' +

                '}';
    }

    public UserLogin() {

    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
