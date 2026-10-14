/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.GranteeType;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Grantee")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class Grantee {
    @Element(name="DisplayName", required=false)
    private String displayName;
    @Element(name="EmailAddress", required=false)
    private String emailAddress;
    @Element(name="ID", required=false)
    private String id;
    @Element(name="Type")
    private GranteeType type;
    @Element(name="URI", required=false)
    private String uri;

    public Grantee(@Nonnull GranteeType type, @Nullable String displayName, @Nullable String emailAddress, @Nullable String id, @Nullable String uri) {
        this.type = Objects.requireNonNull(type, "Type must not be null");
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.id = id;
        this.uri = uri;
    }
}

