/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.BooleanTransform;
import org.simpleframework.xml.transform.ByteTransform;
import org.simpleframework.xml.transform.CharacterTransform;
import org.simpleframework.xml.transform.DoubleTransform;
import org.simpleframework.xml.transform.FloatTransform;
import org.simpleframework.xml.transform.IntegerTransform;
import org.simpleframework.xml.transform.LongTransform;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.ShortTransform;
import org.simpleframework.xml.transform.Transform;

class PrimitiveMatcher
implements Matcher {
    @Override
    public Transform match(Class type) throws Exception {
        if (type == Integer.TYPE) {
            return new IntegerTransform();
        }
        if (type == Boolean.TYPE) {
            return new BooleanTransform();
        }
        if (type == Long.TYPE) {
            return new LongTransform();
        }
        if (type == Double.TYPE) {
            return new DoubleTransform();
        }
        if (type == Float.TYPE) {
            return new FloatTransform();
        }
        if (type == Short.TYPE) {
            return new ShortTransform();
        }
        if (type == Byte.TYPE) {
            return new ByteTransform();
        }
        if (type == Character.TYPE) {
            return new CharacterTransform();
        }
        return null;
    }
}

