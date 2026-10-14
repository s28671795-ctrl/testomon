/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ad;

import java.util.Collection;
import org.ldaptive.LdapAttribute;
import org.ldaptive.ad.transcode.UnicodePwdValueTranscoder;

public class UnicodePwdAttribute
extends LdapAttribute {
    private static final String ATTR_NAME = "unicodePwd";
    private static final UnicodePwdValueTranscoder TRANSCODER = new UnicodePwdValueTranscoder();

    public UnicodePwdAttribute() {
        this.setName(ATTR_NAME);
        this.setBinary(true);
    }

    public UnicodePwdAttribute(String ... values2) {
        this();
        this.addStringValues(values2);
    }

    @Override
    public Collection<String> getStringValues() {
        return this.getValues(TRANSCODER.decoder());
    }

    @Override
    public void addStringValues(String ... value) {
        this.addValues(TRANSCODER.encoder(), value);
    }
}

