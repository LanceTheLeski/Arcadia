package com.springtester.springdemo.chat;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "chatLog")
public class Chat {
    private int currid;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String user;

    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String  message;

    @Column
    @NotFound(action = NotFoundAction.IGNORE)
    private String date;

    public int getId() { return this.id; }
    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate(){return this.date;}
    public void setDate(String date) {this.date = date;}


    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("user", this.getUser())
                .append("message", this.getMessage())
                .append("date", this.getDate()).toString();
    }
}