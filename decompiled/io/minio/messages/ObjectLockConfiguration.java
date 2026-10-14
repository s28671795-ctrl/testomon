/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.RetentionDuration;
import io.minio.messages.RetentionMode;
import io.minio.messages.Rule;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ObjectLockConfiguration", strict=false)
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class ObjectLockConfiguration {
    @Element(name="ObjectLockEnabled")
    private String objectLockEnabled = "Enabled";
    @Element(name="Rule", required=false)
    private Rule rule;

    public ObjectLockConfiguration() {
    }

    public ObjectLockConfiguration(RetentionMode mode, RetentionDuration duration) throws IllegalArgumentException {
        this.rule = new Rule(mode, duration);
    }

    public RetentionMode mode() {
        return this.rule != null ? this.rule.mode() : null;
    }

    public RetentionDuration duration() {
        return this.rule != null ? this.rule.duration() : null;
    }
}

