package com.springtester.springdemo.user;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "users")
public class UserProfile {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String userName;

    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String firstName;

    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String lastName;

    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName(){return this.userName;}
    public void setUserName(String userName) {this.userName = userName;}

    public String getPassword(){return this.password;}
    public void setPassword(String password) {this.password = password;}


    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("firstName", this.getFirstName())
                .append("lastName", this.getLastName())
                .append("userName", this.getUserName())
                .append("password", this.getPassword()).toString();
    }
}