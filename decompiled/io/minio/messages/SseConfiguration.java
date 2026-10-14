/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.SseAlgorithm;
import io.minio.messages.SseConfigurationRule;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ServerSideEncryptionConfiguration")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class SseConfiguration {
    @Element(name="Rule", required=false)
    private SseConfigurationRule rule;

    public SseConfiguration(@Nullable @Element(name="Rule", required=false) SseConfigurationRule rule) {
        this.rule = rule;
    }

    public static SseConfiguration newConfigWithSseS3Rule() {
        return new SseConfiguration(new SseConfigurationRule(SseAlgorithm.AES256, null));
    }

    public static SseConfiguration newConfigWithSseKmsRule(@Nullable String kmsMasterKeyId) {
        return new SseConfiguration(new SseConfigurationRule(SseAlgorithm.AWS_KMS, kmsMasterKeyId));
    }

    public SseConfigurationRule rule() {
        return this.rule;
    }
}

