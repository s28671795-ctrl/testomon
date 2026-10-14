/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  edu.umd.cs.findbugs.annotations.SuppressFBWarnings
 */
package io.minio.messages;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.minio.messages.Grantee;
import io.minio.messages.Permission;
import javax.annotation.Nullable;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Grant")
@SuppressFBWarnings(value={"URF_UNREAD_FIELD"})
public class Grant {
    @Element(name="Grantee", required=false)
    private Grantee grantee;
    @Element(name="Permission", required=false)
    private Permission permission;

    public Grant(@Nullable Grantee grantee, @Nullable Permission permission) {
        if (grantee == null && permission == null) {
            throw new IllegalArgumentException("Either Grantee or Permission must be provided");
        }
        this.grantee = grantee;
        this.permission = permission;
    }
}

