/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import io.minio.ObjectArgs;
import io.minio.S3Escaper;
import io.minio.ServerSideEncryption;
import io.minio.Time;
import io.minio.messages.Retention;
import io.minio.messages.Tags;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import okhttp3.HttpUrl;

public abstract class ObjectWriteArgs
extends ObjectArgs {
    public static final long MAX_OBJECT_SIZE = 0x50000000000L;
    public static final int MIN_MULTIPART_SIZE = 0x500000;
    public static final long MAX_PART_SIZE = 0x140000000L;
    public static final int MAX_MULTIPART_COUNT = 10000;
    protected Multimap<String, String> headers = Multimaps.unmodifiableMultimap(HashMultimap.create());
    protected Multimap<String, String> userMetadata = Multimaps.unmodifiableMultimap(HashMultimap.create());
    protected ServerSideEncryption sse;
    protected Tags tags = new Tags();
    protected Retention retention;
    protected boolean legalHold;

    public Multimap<String, String> headers() {
        return this.headers;
    }

    public Multimap<String, String> userMetadata() {
        return this.userMetadata;
    }

    public ServerSideEncryption sse() {
        return this.sse;
    }

    public Tags tags() {
        return this.tags;
    }

    public Retention retention() {
        return this.retention;
    }

    public boolean legalHold() {
        return this.legalHold;
    }

    public Multimap<String, String> genHeaders() {
        String tagging;
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.putAll(this.headers);
        headers.putAll(this.userMetadata);
        if (this.sse != null) {
            headers.putAll(Multimaps.forMap(this.sse.headers()));
        }
        if (!(tagging = this.tags.get().entrySet().stream().map(e -> S3Escaper.encode((String)e.getKey()) + "=" + S3Escaper.encode((String)e.getValue())).collect(Collectors.joining("&"))).isEmpty()) {
            headers.put("x-amz-tagging", tagging);
        }
        if (this.retention != null && this.retention.mode() != null) {
            headers.put("x-amz-object-lock-mode", this.retention.mode().name());
            headers.put("x-amz-object-lock-retain-until-date", this.retention.retainUntilDate().format(Time.RESPONSE_DATE_FORMAT));
        }
        if (this.legalHold) {
            headers.put("x-amz-object-lock-legal-hold", "ON");
        }
        return headers;
    }

    protected void validateSse(HttpUrl url2) {
        this.checkSse(this.sse, url2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObjectWriteArgs)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ObjectWriteArgs that = (ObjectWriteArgs)o;
        return this.legalHold == that.legalHold && Objects.equals(this.headers, that.headers) && Objects.equals(this.userMetadata, that.userMetadata) && Objects.equals(this.sse, that.sse) && Objects.equals(this.tags, that.tags) && Objects.equals(this.retention, that.retention);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.headers, this.userMetadata, this.sse, this.tags, this.retention, this.legalHold);
    }

    public static abstract class Builder<B extends Builder<B, A>, A extends ObjectWriteArgs>
    extends ObjectArgs.Builder<B, A> {
        public B headers(Map<String, String> headers) {
            Multimap<String, String> headersCopy = this.toMultimap(headers);
            this.operations.add(args2 -> {
                args2.headers = headersCopy;
            });
            return (B)this;
        }

        public B headers(Multimap<String, String> headers) {
            Multimap<String, String> headersCopy = this.copyMultimap(headers);
            this.operations.add(args2 -> {
                args2.headers = headersCopy;
            });
            return (B)this;
        }

        public B userMetadata(Map<String, String> userMetadata) {
            return this.userMetadata(userMetadata == null ? null : Multimaps.forMap(userMetadata));
        }

        public B userMetadata(Multimap<String, String> userMetadata) {
            HashMultimap<String, String> userMetadataCopy = HashMultimap.create();
            if (userMetadata != null) {
                for (String key : userMetadata.keySet()) {
                    userMetadataCopy.putAll((key.toLowerCase(Locale.US).startsWith("x-amz-meta-") ? "" : "x-amz-meta-") + key, userMetadata.get(key));
                }
            }
            Multimap finalUserMetadata = Multimaps.unmodifiableMultimap(userMetadataCopy);
            this.operations.add(args2 -> {
                args2.userMetadata = finalUserMetadata;
            });
            return (B)this;
        }

        public B sse(ServerSideEncryption sse) {
            this.operations.add(args2 -> {
                args2.sse = sse;
            });
            return (B)this;
        }

        public B tags(Map<String, String> map) {
            Tags tags = map == null ? new Tags() : Tags.newObjectTags(map);
            this.operations.add(args2 -> {
                args2.tags = tags;
            });
            return (B)this;
        }

        public B tags(Tags tags) {
            Tags tagsCopy = tags == null ? new Tags() : Tags.newObjectTags(tags.get());
            this.operations.add(args2 -> {
                args2.tags = tagsCopy;
            });
            return (B)this;
        }

        public B retention(Retention retention) {
            this.operations.add(args2 -> {
                args2.retention = retention;
            });
            return (B)this;
        }

        public B legalHold(boolean flag) {
            this.operations.add(args2 -> {
                args2.legalHold = flag;
            });
            return (B)this;
        }
    }
}

