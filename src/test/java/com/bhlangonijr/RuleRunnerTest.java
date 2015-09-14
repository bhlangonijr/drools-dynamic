package com.bhlangonijr;

import com.bhlangonijr.domain.Message;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RuleRunnerTest {

    @Test
    public void testRunningRule() throws Exception {

        String rule =
                "package com.bhlangonijr\n\n" +
                "import com.bhlangonijr.domain.Message \n\n" +
                "rule \"rule 1\" when \n" +
                "    m : Message( from.contains(\"@earth\")) \n" +
                "    then\n" +
                "      m.setText(\"changed\"); \n" +
                "      System.out.println( \"rule 1 fired!\" );\n" +
                "end";

        RuleRunner runner = RuleRunner.createRuleRunner(Arrays.asList(rule));
        long init = System.currentTimeMillis();
        for (int i = 1; i <= 100; i++) {
            Message message = new Message("me"+i+"@earth", "you@earth", "hello there " + i);
            runner.runRules(Arrays.asList(message));
            assertEquals("changed", message.getText());
        }
        System.out.println("Executed in " + (System.currentTimeMillis() - init) + " mls.");

    }

}
