package com.example.web.demo.solr;

import com.example.web.demo.solr.bean.*;
import com.example.web.demo.tika.TikaExtract;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    @Test
    void solrNested() throws Exception {
        SolrIntergration solrClient = new SolrIntergration("http://172.28.128.4:8983/solr/nested");

        HttpSolrClient client = solrClient.getSolrClient();

//        List<FileBean> fileBeans = new ArrayList<>();
//        fileBeans.add(new FileBean("1", "This is the first", "This is content", "child"));
//        fileBeans.add(new FileBean("2", "This is the second", "This is content second", "child"));
//
//        solrClient.addBean(new ParentBean("1234", "Parent", fileBeans));
//        solrClient.addBean(new ParentBean("1235", "This is a bean", new ArrayList<>()));

//        Collection<SolrInputDocument> batch = new ArrayList<SolrInputDocument>();
////
//        // Parent Doc 1, a person mamed John Jones
//        SolrInputDocument person1 = new SolrInputDocument();
//        person1.addField( "id",            "john_jones" );
//        person1.addField( "content_type",  "person"     );
//        // "_t" suffix tells Solr that it's text
//        person1.addField( "first_name_t",  "John"       );
//        person1.addField( "last_name_t",   "Jones"      );
//        // states and history used in edismax examples
//        person1.addField( "states_t",      "California Nevada Idaho Maine" );
//        person1.addField( "history_t",     "safe accident accident accident accident accident" );
//
//        // child docs, the vehicles he owns
//        SolrInputDocument p1_car1 = new SolrInputDocument();
//        p1_car1.addField( "id",            "jj_car1"    );
//        p1_car1.addField( "content_type",  "car"        );
//        // For cars "make" is an alias for "manufacturer"
//        p1_car1.addField( "make_t",        "Honda"      );
//        p1_car1.addField( "model_t",       "Accord"     );
//
//        SolrInputDocument p1_car2 = new SolrInputDocument();
//        p1_car2.addField( "id",            "jj_car2"    );
//        p1_car2.addField( "content_type",  "car"        );
//        p1_car2.addField( "make_t",        "Nissan"     );
//        p1_car2.addField( "model_t",       "Maxima"     );
//
//        SolrInputDocument p1_bike1 = new SolrInputDocument();
//        p1_bike1.addField( "id",           "jj_bike1"   );
//        p1_bike1.addField( "content_type", "bike"       );
//        p1_bike1.addField( "make_t",       "Yamaha"     );
//        p1_bike1.addField( "model_t",      "Passion"    );
//
//        SolrInputDocument p1_bike2 = new SolrInputDocument();
//        p1_bike2.addField( "id",           "jj_bike2"   );
//        p1_bike2.addField( "content_type", "bike"       );
//        p1_bike2.addField( "make_t",       "Peugeot"    );
//        p1_bike2.addField( "model_t",      "Vivacity"   );
//
//        // Add children to parent
//        person1.addChildDocument( p1_car1  );
//        person1.addChildDocument( p1_car2  );
//        person1.addChildDocument( p1_bike1 );
//        person1.addChildDocument( p1_bike2 );
//
//        // Add parent to batch
//        batch.add( person1 );
//
//
//        // Parent Doc 2, person mamed Satish Smith
//        SolrInputDocument person2 = new SolrInputDocument();
//        person2.addField( "id",           "satish_smith" );
//        person2.addField( "content_type", "person"       );
//        person2.addField( "first_name_t", "Satish"       );
//        person2.addField( "last_name_t",  "Smith"        );
//        person2.addField( "states_t",     "California Texas California Maine Vermont Connecticut" );
//        person2.addField( "history_t",    "safe safe safe safe safe safe safe safe accident" );
//
//        // Vehicles (child docs)
//        SolrInputDocument p2_car1 = new SolrInputDocument();
//        p2_car1.addField( "id",            "ss_car1"     );
//        p2_car1.addField( "content_type",  "car"         );
//        p2_car1.addField( "make_t",        "Peugeot"     );
//        p2_car1.addField( "model_t",       "iOn"         );
//        SolrInputDocument p2_bike1 = new SolrInputDocument();
//        p2_bike1.addField( "id",           "ss_bike1"    );
//        p2_bike1.addField( "content_type", "bike"        );
//        p2_bike1.addField( "make_t",       "Honda"       );
//        p2_bike1.addField( "model_t",      "Spree"       );
//        // link objects and add to batch
//        person2.addChildDocument( p2_car1  );
//        person2.addChildDocument( p2_bike1 );
//        batch.add( person2 );
//
//        client.add(batch);
//        client.commit();

        SolrQuery solrQuery = new SolrQuery("*:*");
//
        QueryResponse response = client.query(solrQuery);

        Map<String,String> params = new LinkedHashMap<>();
        params.put( "parent_filter", "content_type:person" );
        params.put( "child_filter",
                "(content_type:car AND make_t:Honda)"
                        + " OR (content_type:bike AND make_t:Peugeot)" );
        // Use second form of doQuery to pass more arguments:
        // doQuery( solr, description, queryStr, optFilter, optFields, extraParamsMap )
        doQuery(client,
                "People who own a Honda car and Peugeot bike, and include the *matching* vehicles in results",
                "*:*",                                                             // query
                "{!parent which=$parent_filter v=$child_filter}",                  // filter
                "*,[child parentFilter=$parent_filter childFilter=$child_filter]", // fields
                params );

//        solrClient.getDocuments("{!parent which='-_nest_path_:* *:*'}name:second");


        System.out.println("ddd");

    }

    @Test

    void solrArticle() throws IOException, SolrServerException {
        SolrIntergration solrClient = new SolrIntergration("http://172.28.128.4:8983/solr/fucked");

        HttpSolrClient client = solrClient.getSolrClient();

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("3", "Lovely recipe"));
        comments.add(new Comment("4", "A-"));
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("2", "Cookies", comments));
        posts.add(new Post("5", "Cakes"));
        List<String> tags = new ArrayList<>();
        tags.add("cooking");
        tags.add("meetup");

        solrClient.addBean(new Article("1", "Cooking Recommendations", tags, posts));

        List<Comment> comments1 = new ArrayList<>();
        comments1.add(new Comment("8", "I am interested"));
        comments1.add(new Comment("9", "How large is the team?"));
        List<Post> posts1 = new ArrayList<>();
        posts1.add(new Post("7", "Search Engineer", comments1));
        posts1.add(new Post("10", "Low level Engineer"));
        List<String> tags1 = new ArrayList<>();
        tags1.add("professional");
        tags1.add("jobs");

        solrClient.addBean(new Article("6", "For Hire", tags1, posts1));

    }

    static void doQuery( HttpSolrClient solr, String description, String queryStr, String optFilter ) throws Exception {
        doQuery( solr, description, queryStr, optFilter, null, null );
    }
    static void doQuery( HttpSolrClient solr, String description, String queryStr, String optFilter,
                         String optFields, Map<String,String>extraParams ) throws Exception {
        // Setup Query
        SolrQuery q = new SolrQuery( queryStr );
        System.out.println();
        System.out.println( "Test: " + description );
        System.out.println( "\tSearch: " + queryStr );
        if ( null!=optFilter ) {
            q.addFilterQuery( optFilter );
            System.out.println( "\tFilter: " + optFilter );
        }
        if ( null!=optFields ) {
            // Use setParam instead of addField
            q.setParam( "fl", optFields );  // childFilter=doc_type:chapter limit=100
            System.out.println( "\tFields: " + optFields );
        }
        else {
            q.addField( "*" );  // childFilter=doc_type:chapter limit=100
        }
        if ( null!=extraParams ) {
            for ( Map.Entry<String,String> param : extraParams.entrySet() ) {
                // Could use q.setParam which allows you to pass in multiple strings
                q.set( param.getKey(), param.getValue() );
                System.out.println( "\tParam: " + param.getKey() + "=" + param.getValue() );
            }
        }

        // Run and show results
        QueryResponse rsp = solr.query( q );
        SolrDocumentList docs = rsp.getResults();
        long numFound = docs.getNumFound();
        System.out.println( "Matched: " + numFound );
        int docCounter = 0;
        for (SolrDocument doc : docs) {
            docCounter++;
            System.out.println( "Doc # " + docCounter );
            for ( Map.Entry<String, Object> field : doc.entrySet() ) {
                String name = field.getKey();
                Object value = field.getValue();
                System.out.println( "\t" + name + "=" + value );
            }
            List<SolrDocument> childDocs = doc.getChildDocuments();
            // TODO: make this recursive, for grandchildren, etc.
            if ( null!=childDocs ) {
                for ( SolrDocument child : childDocs ) {
                    System.out.println( "\tChild doc:" );
                    for ( Map.Entry<String, Object> field : child.entrySet() ) {
                        String name = field.getKey();
                        Object value = field.getValue();
                        System.out.println( "\t\t" + name + "=" + value );
                    }
                }
            }
        }
    }
}