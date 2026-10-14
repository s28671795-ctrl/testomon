/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.LifecycleRule;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name="LifecycleConfiguration")
@Namespace(reference="http://s3.amazonaws.com/doc/2006-03-01/")
public class LifecycleConfiguration {
    @ElementList(name="Rule", inline=true)
    private List<LifecycleRule> rules;

    public LifecycleConfiguration(@Nonnull @ElementList(name="Rule", inline=true) List<LifecycleRule> rules) {
        this.rules = Collections.unmodifiableList(Objects.requireNonNull(rules, "Rules must not be null"));
        if (rules.isEmpty()) {
            throw new IllegalArgumentException("Rules must not be empty");
        }
    }

    public List<LifecycleRule> rules() {
        return this.rules;
    }
}

