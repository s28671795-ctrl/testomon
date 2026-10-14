/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration;

import java.util.HashMap;
import java.util.Map;

public class IntegrationDetails {
    private String name;
    private Map<String, String> parameters = new HashMap<String, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public void setParameter(String key, String value) {
        this.parameters.put(key, value);
    }
}

