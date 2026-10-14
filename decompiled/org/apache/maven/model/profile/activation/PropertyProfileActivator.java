/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile.activation;

import org.apache.maven.model.Activation;
import org.apache.maven.model.ActivationProperty;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.profile.ProfileActivationContext;
import org.apache.maven.model.profile.activation.ProfileActivator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.StringUtils;

@Component(role=ProfileActivator.class, hint="property")
public class PropertyProfileActivator
implements ProfileActivator {
    @Override
    public boolean isActive(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        String propValue;
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        ActivationProperty property = activation.getProperty();
        if (property == null) {
            return false;
        }
        String name = property.getName();
        boolean reverseName = false;
        if (name != null && name.startsWith("!")) {
            reverseName = true;
            name = name.substring(1);
        }
        if (name == null || name.length() <= 0) {
            problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage("The property name is required to activate the profile " + profile.getId()).setLocation(property.getLocation("")));
            return false;
        }
        String sysValue = context.getUserProperties().get(name);
        if (sysValue == null) {
            sysValue = context.getSystemProperties().get(name);
        }
        if (StringUtils.isNotEmpty(propValue = property.getValue())) {
            boolean reverseValue = false;
            if (propValue.startsWith("!")) {
                reverseValue = true;
                propValue = propValue.substring(1);
            }
            boolean result = propValue.equals(sysValue);
            return reverseValue ? !result : result;
        }
        boolean result = StringUtils.isNotEmpty(sysValue);
        return reverseName ? !result : result;
    }

    @Override
    public boolean presentInConfig(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        ActivationProperty property = activation.getProperty();
        return property != null;
    }
}

