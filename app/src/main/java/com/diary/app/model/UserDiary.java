package com.diary.app.model;

public class UserDiary {
    private String name;
    private String email;
    private String hp;

    public UserDiary() {
    }

    public UserDiary(String name, String email, String hp) {
        this.name = name;
        this.email = email;
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }
}
