package com.example.web.demo.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

import java.util.List;

public class Article {
    @Field
    private String id;
    @Field
    private String titles;
    @Field
    private List<String> tags;
    @Field(child = true)
    private List<Post> posts;

    public Article(String id, String titles, List<String> tags, List<Post> posts) {
        this.id = id;
        this.titles = titles;
        this.tags = tags;
        this.posts = posts;
    }
}
