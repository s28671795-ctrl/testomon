/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.ReplicationRule;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="ReplicationConfiguration")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class ReplicationConfiguration {
    @Element(name="Role", required=false)
    private String role;
    @ElementList(name="Rule", inline=true)
    private List<ReplicationRule> rules;

    public ReplicationConfiguration(@Nullable @Element(name="Role", required=false) String role, @Nonnull @ElementList(name="Rule", inline=true) List<ReplicationRule> rules) {
        this.role = role;
        this.rules = Collections.unmodifiableList(Objects.requireNonNull(rules, "Rules must not be null"));
        if (rules.isEmpty()) {
            throw new IllegalArgumentException("Rules must not be empty");
        }
        if (rules.size() > 1000) {
            throw new IllegalArgumentException("More than 1000 rules are not supported");
        }
    }

    public String role() {
        return this.role;
    }

    public List<ReplicationRule> rules() {
        return Collections.unmodifiableList(this.rules == null ? new LinkedList() : this.rules);
    }
}

