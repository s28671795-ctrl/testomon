/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.AbortIncompleteMultipartUpload;
import io.minio.messages.Expiration;
import io.minio.messages.NoncurrentVersionExpiration;
import io.minio.messages.NoncurrentVersionTransition;
import io.minio.messages.RuleFilter;
import io.minio.messages.Status;
import io.minio.messages.Transition;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Rule")
public class LifecycleRule {
    @Element(name="AbortIncompleteMultipartUpload", required=false)
    private AbortIncompleteMultipartUpload abortIncompleteMultipartUpload;
    @Element(name="Expiration", required=false)
    private Expiration expiration;
    @Element(name="Filter", required=false)
    private RuleFilter filter;
    @Element(name="ID", required=false)
    private String id;
    @Element(name="NoncurrentVersionExpiration", required=false)
    private NoncurrentVersionExpiration noncurrentVersionExpiration;
    @Element(name="NoncurrentVersionTransition", required=false)
    private NoncurrentVersionTransition noncurrentVersionTransition;
    @Element(name="Status")
    private Status status;
    @Element(name="Transition", required=false)
    private Transition transition;

    public LifecycleRule(@Nonnull @Element(name="Status") Status status, @Nullable @Element(name="AbortIncompleteMultipartUpload", required=false) AbortIncompleteMultipartUpload abortIncompleteMultipartUpload, @Nullable @Element(name="Expiration", required=false) Expiration expiration, @Nonnull @Element(name="Filter", required=false) RuleFilter filter2, @Nullable @Element(name="ID", required=false) String id, @Nullable @Element(name="NoncurrentVersionExpiration", required=false) NoncurrentVersionExpiration noncurrentVersionExpiration, @Nullable @Element(name="NoncurrentVersionTransition", required=false) NoncurrentVersionTransition noncurrentVersionTransition, @Nullable @Element(name="Transition", required=false) Transition transition) {
        if (abortIncompleteMultipartUpload == null && expiration == null && noncurrentVersionExpiration == null && noncurrentVersionTransition == null && transition == null) {
            throw new IllegalArgumentException("At least one of action (AbortIncompleteMultipartUpload, Expiration, NoncurrentVersionExpiration, NoncurrentVersionTransition or Transition) must be specified in a rule");
        }
        if (id != null) {
            if ((id = id.trim()).isEmpty()) {
                throw new IllegalArgumentException("ID must be non-empty string");
            }
            if (id.length() > 255) {
                throw new IllegalArgumentException("ID must be exceed 255 characters");
            }
        }
        this.abortIncompleteMultipartUpload = abortIncompleteMultipartUpload;
        this.expiration = expiration;
        this.filter = Objects.requireNonNull(filter2, "Filter must not be null");
        this.id = id;
        this.noncurrentVersionExpiration = noncurrentVersionExpiration;
        this.noncurrentVersionTransition = noncurrentVersionTransition;
        this.status = Objects.requireNonNull(status, "Status must not be null");
        this.transition = transition;
    }

    public AbortIncompleteMultipartUpload abortIncompleteMultipartUpload() {
        return this.abortIncompleteMultipartUpload;
    }

    public Expiration expiration() {
        return this.expiration;
    }

    public RuleFilter filter() {
        return this.filter;
    }

    public String id() {
        return this.id;
    }

    public NoncurrentVersionExpiration noncurrentVersionExpiration() {
        return this.noncurrentVersionExpiration;
    }

    public NoncurrentVersionTransition noncurrentVersionTransition() {
        return this.noncurrentVersionTransition;
    }

    public Status status() {
        return this.status;
    }

    public Transition transition() {
        return this.transition;
    }
}

