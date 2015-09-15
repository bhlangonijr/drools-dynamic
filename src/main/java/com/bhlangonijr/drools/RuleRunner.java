package com.bhlangonijr.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RuleRunner {

    private static final Logger log = LoggerFactory.getLogger(RuleRunner.class);

    private final KieSession kieSession;

    private RuleRunner(final KieSession kieSession) {
        this.kieSession = kieSession;
    }

    /**
     * Create a new instances of the rule runner with a set of rules
     *
     * @param rules
     * @return
     */
    public static RuleRunner createRuleRunner(List<String> rules) {
        KieServices kieServices = KieServices.Factory.get();
        KieRepository kr = kieServices.getRepository();
        KieFileSystem kfs = kieServices.newKieFileSystem();

        for (String rule: rules) {
            log.info("loading rule \n{}", rule);
            kfs.write("src/main/resources/drools-dynamic.drl", rule);
        }

        KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
        Results results = kieBuilder.getResults();

        if( results.hasMessages(Message.Level.ERROR) ){
            log.error("Error building rules", results.getMessages());
            throw new IllegalStateException( "Found errors while building rules " + results.getMessages());
        }
        KieContainer kieContainer = kieServices.newKieContainer(kr.getDefaultReleaseId());
        return new RuleRunner(kieContainer.newKieSession());

    }

    /**
     * Execute the rules from this container on the facts given as parameters
     *
     * @param facts
     * @throws Exception
     */
    public void runRules(List<Object> facts) throws Exception {
        final List<FactHandle> handles = new ArrayList<>();
        facts.forEach(fact -> handles.add(kieSession.insert(fact)));
        kieSession.fireAllRules();
        handles.forEach(kieSession::delete);
    }

    public void destroy() {
        kieSession.dispose();
    }

}
