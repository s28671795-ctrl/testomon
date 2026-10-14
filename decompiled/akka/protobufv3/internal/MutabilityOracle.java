/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

interface MutabilityOracle {
    public static final MutabilityOracle IMMUTABLE = new MutabilityOracle(){

        @Override
        public void ensureMutable() {
            throw new UnsupportedOperationException();
        }
    };

    public void ensureMutable();
}

