package com.example.web.demo.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

public class Post {
    @Field
    private String id;
    @Field
    private String title;
    @Field(child = true)
    private List<Comment> comments;

    public Post(String id, String title, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.comments = comments;
    }

    public Post(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
