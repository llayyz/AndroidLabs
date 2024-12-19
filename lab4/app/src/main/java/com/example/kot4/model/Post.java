package com.example.kot4.model;

import java.io.Serializable;

public class Post implements Serializable {
    private String userId, id, title, body;

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
