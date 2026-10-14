/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.SseAlgorithm;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="Rule")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class SseConfigurationRule {
    @Path(value="ApplyServerSideEncryptionByDefault")
    @Element(name="KMSMasterKeyID", required=false)
    private String kmsMasterKeyId;
    @Path(value="ApplyServerSideEncryptionByDefault")
    @Element(name="SSEAlgorithm")
    private SseAlgorithm sseAlgorithm;

    public SseConfigurationRule(@Nonnull @Element(name="SSEAlgorithm") SseAlgorithm sseAlgorithm, @Nullable @Element(name="KMSMasterKeyID", required=false) String kmsMasterKeyId) {
        this.sseAlgorithm = Objects.requireNonNull(sseAlgorithm, "SSE Algorithm must be provided");
        this.kmsMasterKeyId = kmsMasterKeyId;
    }

    public String kmsMasterKeyId() {
        return this.kmsMasterKeyId;
    }

    public SseAlgorithm sseAlgorithm() {
        return this.sseAlgorithm;
    }
}

