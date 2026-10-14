/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import org.glassfish.hk2.api.Unqualified;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

public class CacheKey {
    private final String removalName;
    private final Type lookupType;
    private final String name;
    private final Annotation[] qualifiers;
    private final Unqualified unqualified;
    private final int hashCode;

    public CacheKey(Type lookupType, String name, Unqualified unqualified, Annotation ... qualifiers) {
        this.lookupType = lookupType;
        Class<?> rawClass = ReflectionHelper.getRawClass(lookupType);
        this.removalName = rawClass != null ? rawClass.getName() : null;
        this.name = name;
        this.qualifiers = qualifiers.length > 0 ? qualifiers : null;
        this.unqualified = unqualified;
        int retVal = 0;
        if (lookupType != null) {
            retVal ^= lookupType.hashCode();
        }
        if (name != null) {
            retVal ^= name.hashCode();
        }
        for (Annotation anno : qualifiers) {
            retVal ^= anno.hashCode();
        }
        if (unqualified != null) {
            retVal ^= 0xFFFFFFFF;
            for (Class<? extends Annotation> clazz : unqualified.value()) {
                retVal ^= clazz.hashCode();
            }
        }
        this.hashCode = retVal;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof CacheKey)) {
            return false;
        }
        final CacheKey other = (CacheKey)o;
        if (this.hashCode != other.hashCode) {
            return false;
        }
        if (!GeneralUtilities.safeEquals(this.lookupType, other.lookupType)) {
            return false;
        }
        if (!GeneralUtilities.safeEquals(this.name, other.name)) {
            return false;
        }
        if (this.qualifiers != null) {
            if (other.qualifiers == null) {
                return false;
            }
            if (this.qualifiers.length != other.qualifiers.length) {
                return false;
            }
            boolean isEqual = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                @Override
                public Boolean run() {
                    for (int lcv = 0; lcv < CacheKey.this.qualifiers.length; ++lcv) {
                        if (GeneralUtilities.safeEquals(CacheKey.this.qualifiers[lcv], other.qualifiers[lcv])) continue;
                        return false;
                    }
                    return true;
                }
            });
            if (!isEqual) {
                return false;
            }
        } else if (other.qualifiers != null) {
            return false;
        }
        if (this.unqualified != null) {
            Class<? extends Annotation>[] otherClazzes;
            if (other.unqualified == null) {
                return false;
            }
            Class<? extends Annotation>[] myClazzes = this.unqualified.value();
            if (myClazzes.length != (otherClazzes = other.unqualified.value()).length) {
                return false;
            }
            for (int lcv = 0; lcv < myClazzes.length; ++lcv) {
                if (GeneralUtilities.safeEquals(myClazzes[lcv], otherClazzes[lcv])) continue;
                return false;
            }
        } else if (other.unqualified != null) {
            return false;
        }
        return true;
    }

    public boolean matchesRemovalName(String name) {
        if (this.removalName == null) {
            return false;
        }
        if (name == null) {
            return false;
        }
        return this.removalName.equals(name);
    }

    public String toString() {
        return "CacheKey(" + Pretty.type(this.lookupType) + "," + this.name + "," + (this.qualifiers == null ? 0 : this.qualifiers.length) + "," + System.identityHashCode(this) + "," + this.hashCode + ")";
    }
}

