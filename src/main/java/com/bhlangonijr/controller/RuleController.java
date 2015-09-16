package com.bhlangonijr.controller;

import com.bhlangonijr.domain.Rule;
import com.bhlangonijr.service.RuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@Controller
public class RuleController {

    private static final Logger log = LoggerFactory.getLogger(RuleController.class);

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = "/rules", method = RequestMethod.GET)
    public String listRule(Model model){
        model.addAttribute("rules", ruleService.getAllRules());
        return "rules";
    }

    @RequestMapping("/rule/{id}")
    public String showRule(@PathVariable String id, Model model){
        model.addAttribute("rule", ruleService.getRule(id));
        return "ruleshow";
    }

    @RequestMapping("/rule/edit/{id}")
    public String editRule(@PathVariable String id, Model model){
        model.addAttribute("rule", ruleService.getRule(id));
        return "ruleform";
    }

    @RequestMapping("/rule/new")
    public String newRule(Model model){
        model.addAttribute("rule", new Rule());
        return "ruleform";
    }

    @RequestMapping(value = "/rule/save", method = RequestMethod.POST)
    public String saveRule(Rule rule) throws Exception {
        if (rule.getId() == null) {
            rule.setId(UUID.randomUUID().toString());
        }
        log.info("saving rule {}", rule);
        ruleService.upInsertRule(rule);
        return "redirect:/rule/" + rule.getId();
    }

}
