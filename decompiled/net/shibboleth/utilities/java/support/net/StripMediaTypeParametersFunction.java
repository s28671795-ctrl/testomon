/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Function;
import com.google.common.net.MediaType;

public class StripMediaTypeParametersFunction
implements Function<MediaType, MediaType> {
    @Override
    public MediaType apply(MediaType input) {
        if (input == null) {
            return null;
        }
        return input.withoutParameters();
    }
}

