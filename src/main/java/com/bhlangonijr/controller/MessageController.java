package com.bhlangonijr.controller;

import com.bhlangonijr.domain.Message;
import com.bhlangonijr.domain.Response;
import com.bhlangonijr.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public String listMessages(Model model){
        model.addAttribute("messages", messageService.getAllMessages());
        return "messages";
    }

    @RequestMapping("/message/{id}")
    public String showMessage(@PathVariable String id, Model model){
        model.addAttribute("message", messageService.getById(id));
        return "messageshow";
    }

    @RequestMapping("/message/edit/{id}")
    public String editMessage(@PathVariable String id, Model model){
        model.addAttribute("message", messageService.getById(id));
        return "messageform";
    }

    @RequestMapping("/message/new")
    public String newMessage(Model model){
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @RequestMapping(value = "/message/save", method = RequestMethod.POST)
    public String saveMessage(Message message) throws Exception {
        if (message.getId() == null) {
            message.setId(UUID.randomUUID().toString());
        }
        log.info("sending message {}", message);
        Response response = messageService.send(message);
        return "redirect:/message/" + message.getId();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        log.error("Request {} raised {}", req.getRequestURL(), exception);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL().toString());
        mav.setViewName("error");
        return mav;
    }
}