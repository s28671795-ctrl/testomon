/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.saml.common.binding.EndpointResolver;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEndpointResolver<EndpointType extends Endpoint>
extends AbstractIdentifiedInitializableComponent
implements EndpointResolver<EndpointType> {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(AbstractEndpointResolver.class);

    public AbstractEndpointResolver() {
        super.setId(this.getClass().getName());
    }

    @Override
    @Nonnull
    @NonnullElements
    public Iterable<EndpointType> resolve(@Nullable CriteriaSet criteria) throws ResolverException {
        this.validateCriteria(criteria);
        if (this.canUseRequestedEndpoint(criteria)) {
            Object endpoint = criteria.get(EndpointCriterion.class).getEndpoint();
            if (this.doCheckEndpoint(criteria, endpoint)) {
                return Collections.singletonList(endpoint);
            }
            this.log.debug("{} Requested endpoint was rejected by extended validation process", (Object)this.getLogPrefix());
            return Collections.emptyList();
        }
        List<EndpointType> candidates = this.getCandidatesFromMetadata(criteria);
        Iterator<EndpointType> i = candidates.iterator();
        while (i.hasNext()) {
            if (this.doCheckEndpoint(criteria, (Endpoint)i.next())) continue;
            i.remove();
        }
        this.log.debug("{} {} endpoints remain after filtering process", (Object)this.getLogPrefix(), (Object)candidates.size());
        return candidates;
    }

    @Override
    @Nullable
    public EndpointType resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
        this.validateCriteria(criteria);
        if (this.canUseRequestedEndpoint(criteria)) {
            Object endpoint = criteria.get(EndpointCriterion.class).getEndpoint();
            if (this.doCheckEndpoint(criteria, endpoint)) {
                return endpoint;
            }
            this.log.debug("{} Requested endpoint was rejected by extended validation process", (Object)this.getLogPrefix());
            return null;
        }
        for (Endpoint candidate : this.getCandidatesFromMetadata(criteria)) {
            if (!this.doCheckEndpoint(criteria, candidate)) continue;
            return (EndpointType)candidate;
        }
        this.log.debug("{} No candidate endpoints met criteria", (Object)this.getLogPrefix());
        return null;
    }

    protected boolean doCheckEndpoint(@Nonnull CriteriaSet criteria, @Nonnull EndpointType endpoint) {
        return true;
    }

    private void validateCriteria(@Nullable CriteriaSet criteria) throws ResolverException {
        if (criteria == null) {
            throw new ResolverException("CriteriaSet cannot be null");
        }
        EndpointCriterion epCriterion = criteria.get(EndpointCriterion.class);
        if (epCriterion == null) {
            throw new ResolverException("EndpointCriterion not supplied");
        }
    }

    private boolean canUseRequestedEndpoint(@Nonnull CriteriaSet criteria) {
        Object requestedEndpoint;
        EndpointCriterion epc = criteria.get(EndpointCriterion.class);
        return epc.isTrusted() && (requestedEndpoint = epc.getEndpoint()).getBinding() != null && (requestedEndpoint.getLocation() != null || requestedEndpoint.getResponseLocation() != null);
    }

    @Nonnull
    @NonnullElements
    private List<EndpointType> getCandidatesFromMetadata(@Nonnull CriteriaSet criteria) {
        List<Endpoint> endpoints;
        RoleDescriptorCriterion role = criteria.get(RoleDescriptorCriterion.class);
        if (role == null) {
            this.log.debug("{} No metadata supplied, no candidate endpoints to return", (Object)this.getLogPrefix());
            return new ArrayList();
        }
        EndpointCriterion epCriterion = criteria.get(EndpointCriterion.class);
        QName endpointType = epCriterion.getEndpoint().getSchemaType();
        if (endpointType == null) {
            endpointType = epCriterion.getEndpoint().getElementQName();
        }
        if ((endpoints = role.getRole().getEndpoints(endpointType)).isEmpty()) {
            this.log.debug("{} No endpoints in metadata of type {}", (Object)this.getLogPrefix(), (Object)endpointType);
        } else {
            this.log.debug("{} Returning {} candidate endpoints of type {}", this.getLogPrefix(), endpoints.size(), endpointType);
        }
        return this.sortCandidates(endpoints);
    }

    @Nonnull
    @NonnullElements
    private List<EndpointType> sortCandidates(@Nonnull @NonnullElements List<Endpoint> candidates) {
        Endpoint hardDefault = null;
        Endpoint softDefault = null;
        LinkedList<Endpoint> toReturn = new LinkedList<Endpoint>();
        for (Endpoint endpoint : candidates) {
            if (hardDefault == null && endpoint instanceof IndexedEndpoint) {
                Boolean flag = ((IndexedEndpoint)endpoint).isDefault();
                if (flag != null) {
                    if (flag.booleanValue()) {
                        hardDefault = endpoint;
                        if (softDefault == null) continue;
                        toReturn.addFirst(softDefault);
                        softDefault = null;
                        continue;
                    }
                    toReturn.addLast(endpoint);
                    continue;
                }
                if (hardDefault == null && softDefault == null) {
                    softDefault = endpoint;
                    continue;
                }
                toReturn.addLast(endpoint);
                continue;
            }
            toReturn.addLast(endpoint);
        }
        if (hardDefault != null) {
            toReturn.addFirst(hardDefault);
        } else if (softDefault != null) {
            toReturn.addFirst(softDefault);
        }
        return toReturn;
    }

    @Nonnull
    protected String getLogPrefix() {
        return "Endpoint Resolver " + this.getId() + ":";
    }
}

