package com.example.web.demo.solr;

import com.example.web.demo.solr.bean.FileBean;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public final class SolrIntergration {

    private HttpSolrClient solrClient;

    public SolrIntergration(String solrUrl) {
        this(solrUrl, false);
    }

    public SolrIntergration(String solrUrl, boolean xmlParser) {
        solrClient = new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        if (xmlParser) {
            solrClient.setParser(new XMLResponseParser());
        }

    }

    protected HttpSolrClient getSolrClient() {
        return solrClient;
    }

    protected void setSolrClient(HttpSolrClient solrClient) {
        this.solrClient = solrClient;
    }

    public boolean addFileBean(FileBean fileBean) throws SolrServerException, IOException {
        solrClient.addBean(fileBean);
        solrClient.commit();
        return true;
    }

    public boolean deleteSolrDocumentById(String documentId) throws SolrServerException, IOException {
        solrClient.deleteById(documentId);
        solrClient.commit();
        return true;
    }

    public boolean deleteSolrDocumentByQuery(String query) throws SolrServerException, IOException {
        solrClient.deleteByQuery(query);
        solrClient.commit();
        return true;
    }

    public List<FileBean> getDocuments(String query) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addField("id");
        solrQuery.addField("name");
        solrQuery.addField("content");

        QueryResponse response = solrClient.query(solrQuery);
        List<FileBean> files = response.getBeans(FileBean.class);

        return files;
    }
}
