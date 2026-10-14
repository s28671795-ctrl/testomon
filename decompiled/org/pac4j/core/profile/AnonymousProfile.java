/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import org.pac4j.core.profile.CommonProfile;

public class AnonymousProfile
extends CommonProfile {
    private static final long serialVersionUID = -7377022639833719511L;
    public static final AnonymousProfile INSTANCE = new AnonymousProfile();

    public AnonymousProfile() {
        this.setId("anonymous");
    }
}

