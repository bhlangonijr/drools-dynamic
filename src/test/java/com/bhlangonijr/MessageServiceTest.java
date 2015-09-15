package com.bhlangonijr;

import com.bhlangonijr.domain.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext
public class MessageServiceTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testMessageSend() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("{\n" +
                "  \"to\" : \"me@earth\",\n" +
                "  \"from\" : \"you@earth\",\n" +
                "  \"text\" : \"hello there\"\n" +
                "}", headers);
        ResponseEntity<Response>  response = new TestRestTemplate().postForEntity(
                "http://localhost:" + port + "/message/send", request, Response.class);


        System.out.println("Response: " + response.getBody());

    }
}
