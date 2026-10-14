/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

public final class ClassValueCompat$ {
    public static final ClassValueCompat$ MODULE$ = new ClassValueCompat$();
    private static final boolean scala$runtime$ClassValueCompat$$classValueAvailable = ClassValueCompat$.liftedTree1$1();

    public boolean scala$runtime$ClassValueCompat$$classValueAvailable() {
        return scala$runtime$ClassValueCompat$$classValueAvailable;
    }

    private static final /* synthetic */ boolean liftedTree1$1() {
        try {
            Class.forName("java.lang.ClassValue", false, Object.class.getClassLoader());
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }

    private ClassValueCompat$() {
    }
}

