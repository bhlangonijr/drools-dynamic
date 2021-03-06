package com.bhlangonijr.service;

import com.bhlangonijr.domain.Message;
import com.bhlangonijr.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    RuleService ruleService;

    //in memory database for messages
    final Map<String, Message> messageMap = new ConcurrentHashMap<>();

    /**
     * Send a message is only storing the message in the in memory database
     * in case it passes all the rules managed by {@link RuleService}
     *
     * @param message
     * @return
     * @throws Exception
     */
    public Response send(Message message) throws Exception {
        Response response = new Response();
        ruleService.execute(Arrays.asList(message, response));
        if (response.getSuccess()) {
            messageMap.put(message.getId(), message);
        }

        return response;
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messageMap.values());
    }

    public Message getById(String id) {
        return messageMap.get(id);
    }


}
