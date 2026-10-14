/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import org.ldaptive.LdapUtils;

public enum ReturnAttributes {
    ALL(new String[]{"*", "+"}),
    ALL_USER(new String[]{"*"}),
    ALL_OPERATIONAL(new String[]{"+"}),
    NONE(new String[]{"1.1"});

    private final String[] value;

    private ReturnAttributes(String[] s2) {
        this.value = s2;
    }

    public String[] value() {
        return this.value;
    }

    public boolean equalsAttributes(String ... attrs) {
        return Arrays.equals(this.value, attrs);
    }

    public String[] add(String ... attrs) {
        return LdapUtils.concatArrays(this.value, new String[][]{attrs});
    }

    public static String[] parse(String ... attrs) {
        if (attrs == null || attrs.length == 0) {
            return ALL_USER.value();
        }
        return attrs;
    }
}

