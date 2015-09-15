package com.bhlangonijr.service;

import com.bhlangonijr.domain.Rule;
import com.bhlangonijr.drools.RuleRunner;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RuleService {

    final Map<String, Rule> ruleMap = new HashMap<>();
    RuleRunner runner;
    long runnerLastHash;

    public void upInsertRule(Rule rule) {
        ruleMap.put(rule.getId(), rule);
    }

    public void deleteRule(String id) {
        ruleMap.remove(id);
    }

    public Optional<Rule> getRule(String id) {
        return Optional.ofNullable(ruleMap.get(id));
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
