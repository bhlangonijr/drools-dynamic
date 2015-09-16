package com.bhlangonijr.service;

import com.bhlangonijr.domain.Rule;
import com.bhlangonijr.drools.RuleRunner;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RuleService {

    //in memory database for rules
    final Map<String, Rule> ruleMap = new ConcurrentHashMap<>();
    RuleRunner runner;
    long runnerLastHash;

    public void upInsertRule(Rule rule) {
        ruleMap.put(rule.getId(), rule);
    }

    public void deleteRule(String id) {
        ruleMap.remove(id);
    }

    public Rule getRule(String id) {
        return ruleMap.get(id);
    }

    public List<Rule> getAllRules() {
        return new ArrayList<>(ruleMap.values());
    }

    public void execute(List<Object> facts) throws Exception {
        getRuleRunner().runRules(facts);
    }

    private RuleRunner getRuleRunner() {
        if (runner == null || runnerLastHash != ruleMap.hashCode()) {
            runner = RuleRunner.createRuleRunner(getAllRules().stream().map(Rule::getScript).collect(Collectors.toList()));
            runnerLastHash = ruleMap.hashCode();
        }
        return runner;
    }


}
