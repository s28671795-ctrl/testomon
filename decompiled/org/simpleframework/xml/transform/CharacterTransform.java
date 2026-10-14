/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.InvalidFormatException;
import org.simpleframework.xml.transform.Transform;

class CharacterTransform
implements Transform<Character> {
    CharacterTransform() {
    }

    @Override
    public Character read(String value) throws Exception {
        if (value.length() != 1) {
            throw new InvalidFormatException("Cannot convert '%s' to a character", value);
        }
        return Character.valueOf(value.charAt(0));
    }

    @Override
    public String write(Character value) throws Exception {
        return value.toString();
    }
}

