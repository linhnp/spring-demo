package com.example.web.demo.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

public class Comment {
    @Field
    private String id;
    @Field
    private String content;

    public Comment(String id, String content) {
        this.id = id;
        this.content = content;
    }
}
