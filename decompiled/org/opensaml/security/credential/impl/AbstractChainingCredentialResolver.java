/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.impl.AbstractCredentialResolver;
import org.opensaml.security.credential.impl.ChainingCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractChainingCredentialResolver<ResolverType extends CredentialResolver>
extends AbstractCredentialResolver {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ChainingCredentialResolver.class);
    @Nonnull
    @NonnullElements
    private List<ResolverType> resolvers;

    public AbstractChainingCredentialResolver(@Nonnull List<ResolverType> credResolvers) {
        Constraint.isNotNull(credResolvers, "CredentialResolver list cannot be null");
        this.resolvers = new ArrayList<ResolverType>(Collections2.filter(credResolvers, Predicates.notNull()));
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<ResolverType> getResolverChain() {
        return ImmutableList.copyOf(this.resolvers);
    }

    @Override
    @Nonnull
    public Iterable<Credential> resolve(@Nullable CriteriaSet criteriaSet) throws ResolverException {
        if (this.resolvers.isEmpty()) {
            this.log.warn("Chaining credential resolver resolution was attempted with an empty resolver chain");
            throw new IllegalStateException("The resolver chain is empty");
        }
        return new CredentialIterable(this, criteriaSet);
    }

    public class CredentialIterator
    implements Iterator<Credential> {
        @Nonnull
        private final Logger log = LoggerFactory.getLogger(CredentialIterator.class);
        private AbstractChainingCredentialResolver<ResolverType> parent;
        private CriteriaSet critSet;
        private Iterator<ResolverType> resolverIterator;
        private Iterator<Credential> credentialIterator;
        private CredentialResolver currentResolver;
        private Credential nextCredential;

        public CredentialIterator(@Nullable AbstractChainingCredentialResolver<ResolverType> resolver2, CriteriaSet criteriaSet) {
            Constraint.isNotNull(resolver2, "Parent resolver cannot be null");
            this.parent = resolver2;
            this.critSet = criteriaSet;
            this.resolverIterator = this.parent.getResolverChain().iterator();
            this.credentialIterator = this.getNextCredentialIterator();
            this.nextCredential = null;
        }

        @Override
        public boolean hasNext() {
            if (this.nextCredential != null) {
                return true;
            }
            this.nextCredential = this.getNextCredential();
            return this.nextCredential != null;
        }

        @Override
        public Credential next() {
            if (this.nextCredential != null) {
                Credential tempCred = this.nextCredential;
                this.nextCredential = null;
                return tempCred;
            }
            Credential tempCred = this.getNextCredential();
            if (tempCred != null) {
                return tempCred;
            }
            throw new NoSuchElementException("No more Credential elements are available");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
        }

        @Nullable
        private Iterator<Credential> getNextCredentialIterator() {
            while (this.resolverIterator.hasNext()) {
                this.currentResolver = (CredentialResolver)this.resolverIterator.next();
                this.log.debug("Getting credential iterator from next resolver in chain: {}", (Object)this.currentResolver.getClass().toString());
                try {
                    return this.currentResolver.resolve(this.critSet).iterator();
                }
                catch (ResolverException e) {
                    this.log.error(String.format("Error resolving credentials from chaining resolver member '%s'", this.currentResolver.getClass().getName()), e);
                    if (!this.resolverIterator.hasNext()) continue;
                    this.log.error("Will attempt to resolve credentials from next member of resolver chain");
                }
            }
            this.log.debug("No more credential resolvers available in the resolver chain");
            this.currentResolver = null;
            return null;
        }

        @Nullable
        private Credential getNextCredential() {
            if (this.credentialIterator != null && this.credentialIterator.hasNext()) {
                return this.credentialIterator.next();
            }
            this.credentialIterator = this.getNextCredentialIterator();
            while (this.credentialIterator != null) {
                if (this.credentialIterator.hasNext()) {
                    return this.credentialIterator.next();
                }
                this.credentialIterator = this.getNextCredentialIterator();
            }
            return null;
        }
    }

    public class CredentialIterable
    implements Iterable<Credential> {
        private AbstractChainingCredentialResolver<ResolverType> parent;
        private CriteriaSet critSet;

        public CredentialIterable(@Nullable AbstractChainingCredentialResolver<ResolverType> resolver2, CriteriaSet criteriaSet) {
            this.parent = resolver2;
            this.critSet = criteriaSet;
        }

        @Override
        @Nonnull
        public Iterator<Credential> iterator() {
            return new CredentialIterator(this.parent, this.critSet);
        }
    }
}

