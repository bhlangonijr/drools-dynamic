package com.bhlangonijr.domain;

import java.io.Serializable;
import java.util.Objects;

public class Rule implements Serializable {

    private String id;
    private String script;

    public Rule() {
    }

    public Rule(String id, String script) {
        this.id = id;
        this.script = script;
    }

    public String getId() {
        return id;
    }

    public String getScript() {
        return script;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(id, rule.id) &&
                Objects.equals(script, rule.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, script);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Rule{");
        sb.append("id='").append(id).append('\'');
        sb.append(", script='").append(script).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
