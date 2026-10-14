/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.common.binding.AbstractEndpointResolver;
import org.opensaml.saml.criterion.BindingCriterion;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultEndpointResolver<EndpointType extends Endpoint>
extends AbstractEndpointResolver<EndpointType> {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(DefaultEndpointResolver.class);

    @Override
    protected boolean doCheckEndpoint(@Nonnull CriteriaSet criteria, @Nonnull EndpointType endpoint) {
        BindingCriterion bindingCriterion = criteria.get(BindingCriterion.class);
        if (bindingCriterion != null && !this.checkBindingCriterion(bindingCriterion, endpoint)) {
            return false;
        }
        EndpointCriterion epCriterion = criteria.get(EndpointCriterion.class);
        return epCriterion == null || this.checkEndpointCriterion(epCriterion, endpoint);
    }

    private boolean checkBindingCriterion(@Nonnull BindingCriterion bindings, @Nonnull EndpointType endpoint) {
        if (endpoint.getBinding() != null && !bindings.getBindings().contains(endpoint.getBinding())) {
            this.log.debug("{} Candidate endpoint binding '{}' not permitted by input criteria", (Object)this.getLogPrefix(), (Object)endpoint.getBinding());
            return false;
        }
        return true;
    }

    private boolean checkEndpointCriterion(@Nonnull EndpointCriterion<EndpointType> comparison, @Nonnull EndpointType endpoint) {
        EndpointType comparisonEndpoint = comparison.getEndpoint();
        if (comparisonEndpoint == endpoint) {
            this.log.debug("{} Candidate endpoint was supplied by the criterion, skipping check", (Object)this.getLogPrefix());
            return true;
        }
        if (comparisonEndpoint.getBinding() != null && !Objects.equals(comparisonEndpoint.getBinding(), endpoint.getBinding())) {
            this.log.debug("{} Candidate endpoint binding '{}' did not match '{}'", this.getLogPrefix(), endpoint.getBinding(), comparisonEndpoint.getBinding());
            return false;
        }
        if (comparisonEndpoint.getLocation() != null && !Objects.equals(comparisonEndpoint.getLocation(), endpoint.getLocation()) && !Objects.equals(comparisonEndpoint.getLocation(), endpoint.getResponseLocation())) {
            this.log.debug("{} Neither candidate endpoint location '{}' nor response location '{}' matched '{}' ", this.getLogPrefix(), endpoint.getLocation(), endpoint.getResponseLocation(), comparisonEndpoint.getLocation());
            return false;
        }
        if (comparisonEndpoint instanceof IndexedEndpoint && ((IndexedEndpoint)comparisonEndpoint).getIndex() != null) {
            if (!(endpoint instanceof IndexedEndpoint)) {
                this.log.debug("{} Candidate endpoint was not indexed, so did not match", (Object)this.getLogPrefix());
                return false;
            }
            if (!Objects.equals(((IndexedEndpoint)comparisonEndpoint).getIndex(), ((IndexedEndpoint)endpoint).getIndex())) {
                this.log.debug("{} Candidate endpoint index {} did not match {}", this.getLogPrefix(), ((IndexedEndpoint)endpoint).getIndex(), ((IndexedEndpoint)comparisonEndpoint).getIndex());
                return false;
            }
        }
        return true;
    }
}

