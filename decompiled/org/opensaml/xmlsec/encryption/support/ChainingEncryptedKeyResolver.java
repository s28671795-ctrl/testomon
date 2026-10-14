/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.support;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.support.AbstractEncryptedKeyResolver;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingEncryptedKeyResolver
extends AbstractEncryptedKeyResolver {
    @Nonnull
    @NonnullElements
    private final List<EncryptedKeyResolver> resolvers;
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ChainingEncryptedKeyResolver.class);

    public ChainingEncryptedKeyResolver(@Nonnull List<EncryptedKeyResolver> encKeyResolvers) {
        Constraint.isNotNull(encKeyResolvers, "List of EncryptedKeyResolvers cannot be null");
        this.resolvers = new ArrayList<EncryptedKeyResolver>(Collections2.filter(encKeyResolvers, Predicates.notNull()));
    }

    public ChainingEncryptedKeyResolver(@Nonnull List<EncryptedKeyResolver> encKeyResolvers, @Nullable Set<String> recipients) {
        super(recipients);
        Constraint.isNotNull(encKeyResolvers, "List of EncryptedKeyResolvers cannot be null");
        this.resolvers = new ArrayList<EncryptedKeyResolver>(Collections2.filter(encKeyResolvers, Predicates.notNull()));
    }

    public ChainingEncryptedKeyResolver(@Nonnull List<EncryptedKeyResolver> encKeyResolvers, @Nullable String recipient) {
        this(encKeyResolvers, Collections.singleton(recipient));
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public List<EncryptedKeyResolver> getResolverChain() {
        return ImmutableList.copyOf(this.resolvers);
    }

    @Override
    @Nonnull
    public Iterable<EncryptedKey> resolve(@Nonnull EncryptedData encryptedData) {
        if (this.resolvers.isEmpty()) {
            this.log.warn("Chaining encrypted key resolver resolution was attempted with an empty resolver chain");
            throw new IllegalStateException("The resolver chain is empty");
        }
        return new ChainingIterable(this, encryptedData);
    }

    public class ChainingIterator
    implements Iterator<EncryptedKey> {
        private final Logger log = LoggerFactory.getLogger(ChainingIterator.class);
        private final ChainingEncryptedKeyResolver parent;
        private final EncryptedData encryptedData;
        private final Iterator<EncryptedKeyResolver> resolverIterator;
        private Iterator<EncryptedKey> keyIterator;
        private EncryptedKeyResolver currentResolver;
        private EncryptedKey nextKey;

        public ChainingIterator(@Nonnull ChainingEncryptedKeyResolver resolver2, EncryptedData encData) {
            this.parent = resolver2;
            this.encryptedData = encData;
            this.resolverIterator = this.parent.getResolverChain().iterator();
            this.keyIterator = this.getNextKeyIterator();
            this.nextKey = null;
        }

        @Override
        public boolean hasNext() {
            if (this.nextKey != null) {
                return true;
            }
            this.nextKey = this.getNextKey();
            return this.nextKey != null;
        }

        @Override
        public EncryptedKey next() {
            if (this.nextKey != null) {
                EncryptedKey tempKey = this.nextKey;
                this.nextKey = null;
                return tempKey;
            }
            EncryptedKey tempKey = this.getNextKey();
            if (tempKey != null) {
                return tempKey;
            }
            throw new NoSuchElementException("No more EncryptedKey elements are available");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported by this iterator");
        }

        @Nullable
        private Iterator<EncryptedKey> getNextKeyIterator() {
            if (this.resolverIterator.hasNext()) {
                this.currentResolver = this.resolverIterator.next();
                this.log.debug("Getting key iterator from next resolver: {}", (Object)this.currentResolver.getClass().toString());
                return this.currentResolver.resolve(this.encryptedData).iterator();
            }
            this.log.debug("No more resolvers available in the resolver chain");
            this.currentResolver = null;
            return null;
        }

        @Nullable
        private EncryptedKey getNextKey() {
            EncryptedKey tempKey;
            if (this.keyIterator != null) {
                while (this.keyIterator.hasNext()) {
                    tempKey = this.keyIterator.next();
                    if (!this.parent.matchRecipient(tempKey.getRecipient())) continue;
                    this.log.debug("Found matching encrypted key: {}", (Object)tempKey.toString());
                    return tempKey;
                }
            }
            this.keyIterator = this.getNextKeyIterator();
            while (this.keyIterator != null) {
                while (this.keyIterator.hasNext()) {
                    tempKey = this.keyIterator.next();
                    if (!this.parent.matchRecipient(tempKey.getRecipient())) continue;
                    this.log.debug("Found matching encrypted key: {}", (Object)tempKey.toString());
                    return tempKey;
                }
                this.keyIterator = this.getNextKeyIterator();
            }
            return null;
        }
    }

    public class ChainingIterable
    implements Iterable<EncryptedKey> {
        private final ChainingEncryptedKeyResolver parent;
        private final EncryptedData encryptedData;

        public ChainingIterable(@Nonnull ChainingEncryptedKeyResolver resolver2, EncryptedData encData) {
            this.parent = resolver2;
            this.encryptedData = encData;
        }

        @Override
        @Nonnull
        public Iterator<EncryptedKey> iterator() {
            return new ChainingIterator(this.parent, this.encryptedData);
        }
    }
}

