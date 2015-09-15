package com.bhlangonijr;

import com.bhlangonijr.domain.Message;
import com.bhlangonijr.domain.Response;
import com.bhlangonijr.domain.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext
public class MessageServiceTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    public void testHappyPathMessageSend() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 1) Create and save a rule which is simply verifying if message was source by ´@earth´
        // If true we just respond copying in the text field of Response object
        // the message payload appended with ´- has been processed´
        Rule rule = new Rule("rule01", "" +
                "package com.bhlangonijr\n\n" +
                "import com.bhlangonijr.domain.Message \n\n" +
                "import com.bhlangonijr.domain.Response \n\n" +
                "rule \"rule 1\" when \n" +
                "     m : Message( from.contains(\"@earth\"))" +
                "     r: Response( ) \n" +
                "    then\n" +
                "      r.setSuccess(true); \n" +
                "      r.setText(m.getText() + \" - has been processed\"); \n" +
                "      System.out.println( \"rule 1 fired!\" );\n" +
                "end");
        ResponseEntity<String> response1 = new TestRestTemplate().postForEntity(
                "http://localhost:" + port + "/rule/save", rule, String.class);

        assertEquals(response1.getStatusCode(), HttpStatus.OK);
        System.out.println("Response1: " + response1.getBody());

        // 2) Send a message sourced by ´@earth´
        Message message = new Message("me@earth", "you@earth", "hello there");

        HttpEntity<Message> request3 = new HttpEntity<>(message, headers);
        ResponseEntity<Response>  response3 = new TestRestTemplate().postForEntity(
                "http://localhost:" + port + "/message/send", request3, Response.class);

        assertTrue(response3.getBody().getSuccess());
        assertEquals("hello there - has been processed", response3.getBody().getText());
        System.out.println("Response2: " + response3.getBody());

        // 3) now check if the message that we just sent is there
        ResponseEntity<Message[]> response4 = new TestRestTemplate().getForEntity(
                "http://localhost:" + port + "/message/all", Message[].class);

        assertEquals(1, response4.getBody().length);
        assertEquals(message, response4.getBody()[0]);

        System.out.println("Response3: " + response4.getBody()[0]);
    }
}
