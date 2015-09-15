package com.bhlangonijr.service;

import com.bhlangonijr.domain.Message;
import com.bhlangonijr.domain.Response;
import com.bhlangonijr.domain.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private RuleService ruleService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message/send", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response> sendMessage(@RequestBody Message message) {
        log.info("incoming message: " + message);
        try {
            Response response = messageService.send(message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending message", e);
            return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/message/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Message>> getAllMessages() {
        try {
            return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving message", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/rule/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveRule(@RequestBody Rule rule) {
        try {
            log.info("saving rule: " + rule);
            ruleService.upInsertRule(rule);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error saving rule", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/rule/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> deleteRule(@PathVariable("id") String id) {
        try {
            ruleService.deleteRule(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting rule", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}
