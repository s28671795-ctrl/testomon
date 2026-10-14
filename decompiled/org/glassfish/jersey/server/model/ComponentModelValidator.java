/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.jersey.Severity;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.message.MessageBodyWorkers;
import org.glassfish.jersey.server.model.InvocableValidator;
import org.glassfish.jersey.server.model.ResourceMethodValidator;
import org.glassfish.jersey.server.model.ResourceModelComponent;
import org.glassfish.jersey.server.model.ResourceModelIssue;
import org.glassfish.jersey.server.model.ResourceModelVisitor;
import org.glassfish.jersey.server.model.ResourceValidator;
import org.glassfish.jersey.server.model.RuntimeResourceModelValidator;
import org.glassfish.jersey.server.model.internal.ModelErrors;
import org.glassfish.jersey.server.spi.internal.ValueParamProvider;

public final class ComponentModelValidator {
    private final List<ResourceModelIssue> issueList = new LinkedList<ResourceModelIssue>();
    private final List<ResourceModelVisitor> validators = new ArrayList<ResourceModelVisitor>();

    public ComponentModelValidator(Collection<ValueParamProvider> valueParamProviders, MessageBodyWorkers msgBodyWorkers) {
        this.validators.add(new ResourceValidator());
        this.validators.add(new RuntimeResourceModelValidator(msgBodyWorkers));
        this.validators.add(new ResourceMethodValidator(valueParamProviders));
        this.validators.add(new InvocableValidator());
    }

    public List<ResourceModelIssue> getIssueList() {
        return this.issueList;
    }

    public boolean fatalIssuesFound() {
        for (ResourceModelIssue issue : this.getIssueList()) {
            if (issue.getSeverity() != Severity.FATAL) continue;
            return true;
        }
        return false;
    }

    public void cleanIssueList() {
        this.issueList.clear();
    }

    public void validate(final ResourceModelComponent component) {
        Errors.process(new Runnable(){

            @Override
            public void run() {
                Errors.mark();
                ComponentModelValidator.this.validateWithErrors(component);
                ComponentModelValidator.this.issueList.addAll(ModelErrors.getErrorsAsResourceModelIssues(true));
                Errors.unmark();
            }
        });
    }

    private void validateWithErrors(ResourceModelComponent component) {
        for (ResourceModelVisitor validator : this.validators) {
            component.accept(validator);
        }
        List<? extends ResourceModelComponent> componentList = component.getComponents();
        if (null != componentList) {
            for (ResourceModelComponent resourceModelComponent : componentList) {
                this.validateWithErrors(resourceModelComponent);
            }
        }
    }
}

