/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.GIOP.Version;

public final class TagGroupTaggedComponent
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public Version group_version;
    public String group_domain_id = "";
    public long object_group_id;
    public int object_group_ref_version;

    public TagGroupTaggedComponent() {
    }

    public TagGroupTaggedComponent(Version group_version, String group_domain_id, long object_group_id, int object_group_ref_version) {
        this.group_version = group_version;
        this.group_domain_id = group_domain_id;
        this.object_group_id = object_group_id;
        this.object_group_ref_version = object_group_ref_version;
    }
}

