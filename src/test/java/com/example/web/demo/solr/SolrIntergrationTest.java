package com.example.web.demo.solr;

import com.example.web.demo.solr.bean.FileBean;
import com.example.web.demo.tika.TikaExtract;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;

class SolrIntergrationTest {
    @Test
    void solrSetGet() throws SolrServerException, IOException, TikaException, SAXException {
        SolrIntergration solrClient = new SolrIntergration("http://172.28.128.4:8983/solr/sample");

        List<FileBean> files =  solrClient.getDocuments("id:1234");

        if (files.size() != 0){
            assertTrue(solrClient.deleteSolrDocumentById("1234"));
        }

        String fileName = "demo.txt";
        String substring = "ありのままの姿見せるのよ";
        InputStream stream = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);
        String content = TikaExtract.extractContentParser(stream);
        assertThat(content, containsString(substring));

        FileBean file = new FileBean("1234", "demo.txt", content);

        assertTrue(solrClient.addFileBean(file));
        files =  solrClient.getDocuments("id:1234");
        assertEquals(1, files.size());
        FileBean re = files.get(0);
        assertEquals("1234", re.getId());
        assertEquals(fileName, re.getName());
        assertThat(re.getContent(), containsString(substring));
        stream.close();

    }
}