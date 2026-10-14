/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.Grant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="AccessControlList")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class AccessControlList {
    @ElementList(name="Grant", inline=true)
    private List<Grant> grants;

    public AccessControlList(@Nonnull List<Grant> grants) {
        Objects.requireNonNull(grants, "Grants must not be null");
        if (grants.size() == 0) {
            throw new IllegalArgumentException("Grants must not be empty");
        }
        this.grants = Collections.unmodifiableList(grants);
    }
}

