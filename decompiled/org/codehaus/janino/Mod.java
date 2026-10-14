/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

public final class Mod {
    public static final short NONE = 0;
    public static final short PUBLIC = 1;
    public static final short PRIVATE = 2;
    public static final short PROTECTED = 4;
    public static final short PACKAGE = 0;
    public static final short PPP = 7;
    public static final short STATIC = 8;
    public static final short FINAL = 16;
    public static final short SUPER = 32;
    public static final short SYNCHRONIZED = 32;
    public static final short VOLATILE = 64;
    public static final short BRIDGE = 64;
    public static final short TRANSIENT = 128;
    public static final short VARARGS = 128;
    public static final short NATIVE = 256;
    public static final short INTERFACE = 512;
    public static final short ABSTRACT = 1024;
    public static final short STRICTFP = 2048;
    public static final short SYNTHETIC = 4096;
    public static final short ANNOTATION = 8192;
    public static final short ENUM = 16384;

    private Mod() {
    }

    public static boolean isPublicAccess(short sh) {
        return (sh & 7) == 1;
    }

    public static boolean isPrivateAccess(short sh) {
        return (sh & 7) == 2;
    }

    public static boolean isProtectedAccess(short sh) {
        return (sh & 7) == 4;
    }

    public static boolean isPackageAccess(short sh) {
        return (sh & 7) == 0;
    }

    public static short changeAccess(short modifiers, short newAccess) {
        return (short)(modifiers & 0xFFFFFFF8 | newAccess);
    }

    public static boolean isStatic(short sh) {
        return (sh & 8) != 0;
    }

    public static boolean isFinal(short sh) {
        return (sh & 0x10) != 0;
    }

    public static boolean isSuper(short sh) {
        return (sh & 0x20) != 0;
    }

    public static boolean isSynchronized(short sh) {
        return (sh & 0x20) != 0;
    }

    public static boolean isVolatile(short sh) {
        return (sh & 0x40) != 0;
    }

    public static boolean isBridge(short sh) {
        return (sh & 0x40) != 0;
    }

    public static boolean isTransient(short sh) {
        return (sh & 0x80) != 0;
    }

    public static boolean isVarargs(short sh) {
        return (sh & 0x80) != 0;
    }

    public static boolean isNative(short sh) {
        return (sh & 0x100) != 0;
    }

    public static boolean isInterface(short sh) {
        return (sh & 0x200) != 0;
    }

    public static boolean isAbstract(short sh) {
        return (sh & 0x400) != 0;
    }

    public static boolean isStrictfp(short sh) {
        return (sh & 0x800) != 0;
    }

    public static boolean isSynthetic(short sh) {
        return (sh & 0x1000) != 0;
    }

    public static boolean isAnnotation(short sh) {
        return (sh & 0x2000) != 0;
    }

    public static boolean isEnum(short sh) {
        return (sh & 0x4000) != 0;
    }
}

