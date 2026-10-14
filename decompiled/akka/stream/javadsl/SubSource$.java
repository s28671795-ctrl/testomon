/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.stream.javadsl.SubSource;

public final class SubSource$ {
    public static final SubSource$ MODULE$ = new SubSource$();

    public <U, T extends U, Mat> SubSource<U, Mat> upcast(SubSource<T, Mat> source2) {
        return source2;
    }

    private SubSource$() {
    }
}

