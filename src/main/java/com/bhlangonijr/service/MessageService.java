package com.bhlangonijr.service;

import com.bhlangonijr.domain.Message;
import com.bhlangonijr.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {

    @Autowired
    RuleService ruleService;

    final Map<String, Message> messageMap = new HashMap<>();

    public Response send(Message message) throws Exception {
        Response response = new Response();
        ruleService.execute(Arrays.asList(message, response));
        if (response.getSuccess()) {
            messageMap.put(response.getMessageId(), message);
        }
        return response;
    }

    public List<Message> getAllMessages() {
        return new ArrayList<>(messageMap.values());
    }

    public Message getMessage(String id) {
        return messageMap.get(id);
    }

}
