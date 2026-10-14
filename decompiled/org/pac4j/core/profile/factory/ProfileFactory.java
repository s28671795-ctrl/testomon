/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.factory;

import java.util.function.Function;
import org.pac4j.core.profile.UserProfile;

@FunctionalInterface
public interface ProfileFactory
extends Function<Object[], UserProfile> {
}

