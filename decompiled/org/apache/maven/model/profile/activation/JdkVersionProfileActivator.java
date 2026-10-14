/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile.activation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.maven.model.Activation;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.profile.ProfileActivationContext;
import org.apache.maven.model.profile.activation.ProfileActivator;
import org.codehaus.plexus.component.annotations.Component;

@Component(role=ProfileActivator.class, hint="jdk-version")
public class JdkVersionProfileActivator
implements ProfileActivator {
    @Override
    public boolean isActive(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        String jdk = activation.getJdk();
        if (jdk == null) {
            return false;
        }
        String version = context.getSystemProperties().get("java.version");
        if (version == null || version.length() <= 0) {
            problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage("Failed to determine Java version for profile " + profile.getId()).setLocation(activation.getLocation("jdk")));
            return false;
        }
        if (jdk.startsWith("!")) {
            return !version.startsWith(jdk.substring(1));
        }
        if (JdkVersionProfileActivator.isRange(jdk)) {
            return JdkVersionProfileActivator.isInRange(version, JdkVersionProfileActivator.getRange(jdk));
        }
        return version.startsWith(jdk);
    }

    @Override
    public boolean presentInConfig(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        String jdk = activation.getJdk();
        return jdk != null;
    }

    private static boolean isInRange(String value, List<RangeValue> range) {
        int leftRelation = JdkVersionProfileActivator.getRelationOrder(value, range.get(0), true);
        if (leftRelation == 0) {
            return true;
        }
        if (leftRelation < 0) {
            return false;
        }
        return JdkVersionProfileActivator.getRelationOrder(value, range.get(1), false) <= 0;
    }

    private static int getRelationOrder(String value, RangeValue rangeValue, boolean isLeft) {
        if (rangeValue.value.length() <= 0) {
            return isLeft ? 1 : -1;
        }
        value = value.replaceAll("[^0-9\\.\\-\\_]", "");
        ArrayList<String> valueTokens = new ArrayList<String>(Arrays.asList(value.split("[\\.\\-\\_]")));
        ArrayList<String> rangeValueTokens = new ArrayList<String>(Arrays.asList(rangeValue.value.split("\\.")));
        JdkVersionProfileActivator.addZeroTokens(valueTokens, 3);
        JdkVersionProfileActivator.addZeroTokens(rangeValueTokens, 3);
        for (int i = 0; i < 3; ++i) {
            int y;
            int x = Integer.parseInt((String)valueTokens.get(i));
            if (x < (y = Integer.parseInt((String)rangeValueTokens.get(i)))) {
                return -1;
            }
            if (x <= y) continue;
            return 1;
        }
        if (!rangeValue.closed) {
            return isLeft ? -1 : 1;
        }
        return 0;
    }

    private static void addZeroTokens(List<String> tokens, int max) {
        while (tokens.size() < max) {
            tokens.add("0");
        }
    }

    private static boolean isRange(String value) {
        return value.startsWith("[") || value.startsWith("(");
    }

    private static List<RangeValue> getRange(String range) {
        ArrayList<RangeValue> ranges = new ArrayList<RangeValue>();
        for (String token : range.split(",")) {
            if (token.startsWith("[")) {
                ranges.add(new RangeValue(token.replace("[", ""), true));
                continue;
            }
            if (token.startsWith("(")) {
                ranges.add(new RangeValue(token.replace("(", ""), false));
                continue;
            }
            if (token.endsWith("]")) {
                ranges.add(new RangeValue(token.replace("]", ""), true));
                continue;
            }
            if (token.endsWith(")")) {
                ranges.add(new RangeValue(token.replace(")", ""), false));
                continue;
            }
            if (token.length() > 0) continue;
            ranges.add(new RangeValue("", false));
        }
        if (ranges.size() < 2) {
            ranges.add(new RangeValue("99999999", false));
        }
        return ranges;
    }

    private static class RangeValue {
        private String value;
        private boolean closed;

        RangeValue(String value, boolean closed) {
            this.value = value.trim();
            this.closed = closed;
        }

        public String toString() {
            return this.value;
        }
    }
}

