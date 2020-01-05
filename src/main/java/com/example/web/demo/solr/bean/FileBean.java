package com.example.web.demo.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

public class FileBean {
    private String id;
    private String name;
    private String content;

    public FileBean() {
    }

    public FileBean(String id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    @Field("id")
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Field("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    @Field("content")
    public void setContent(String content) {
        this.content = content;
    }
}
