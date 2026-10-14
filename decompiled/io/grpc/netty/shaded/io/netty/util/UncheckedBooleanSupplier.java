/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.netty.shaded.io.netty.util;

import io.grpc.netty.shaded.io.netty.util.BooleanSupplier;

public interface UncheckedBooleanSupplier
extends BooleanSupplier {
    public static final UncheckedBooleanSupplier FALSE_SUPPLIER = new UncheckedBooleanSupplier(){

        @Override
        public boolean get() {
            return false;
        }
    };
    public static final UncheckedBooleanSupplier TRUE_SUPPLIER = new UncheckedBooleanSupplier(){

        @Override
        public boolean get() {
            return true;
        }
    };

    @Override
    public boolean get();
}

