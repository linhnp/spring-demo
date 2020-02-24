package com.example.web.demo.solr.bean;

import org.apache.solr.client.solrj.beans.Field;

import java.io.File;
import java.util.List;

public class ParentBean {
    @Field
    private String id;
    @Field
    private String name;
    @Field(child = true)
    private List<FileBean> fileBeans;

    public ParentBean(String id, String name, List<FileBean> fileBeans) {
        this.id = id;
        this.name = name;
        this.fileBeans = fileBeans;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileBeans(List<FileBean> fileBeans) {
        this.fileBeans = fileBeans;
    }
}
