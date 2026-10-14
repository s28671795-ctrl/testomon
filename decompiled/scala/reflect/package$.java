/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import java.lang.reflect.AccessibleObject;
import scala.reflect.ClassManifestFactory$;
import scala.reflect.ClassTag;

public final class package$ {
    public static final package$ MODULE$ = new package$();
    private static final ClassManifestFactory$ ClassManifest = ClassManifestFactory$.MODULE$;

    public ClassManifestFactory$ ClassManifest() {
        return ClassManifest;
    }

    public <T> ClassTag<T> classTag(ClassTag<T> ctag) {
        return ctag;
    }

    public <T extends AccessibleObject> T ensureAccessible(T m4) {
        if (!m4.isAccessible()) {
            try {
                m4.setAccessible(true);
            }
            catch (SecurityException securityException) {}
        }
        return m4;
    }

    private package$() {
    }
}

