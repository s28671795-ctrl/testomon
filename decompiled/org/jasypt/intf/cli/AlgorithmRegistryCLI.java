/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.intf.cli;

import java.util.Set;
import org.jasypt.registry.AlgorithmRegistry;

public final class AlgorithmRegistryCLI {
    public static void main(String[] args2) {
        try {
            Set digestAlgos = AlgorithmRegistry.getAllDigestAlgorithms();
            Set pbeAlgos = AlgorithmRegistry.getAllPBEAlgorithms();
            System.out.println();
            System.out.println("DIGEST ALGORITHMS:   " + digestAlgos);
            System.out.println();
            System.out.println("PBE ALGORITHMS:      " + pbeAlgos);
            System.out.println();
        }
        catch (Throwable t2) {
            t2.printStackTrace(System.err);
        }
    }

    private AlgorithmRegistryCLI() {
    }
}

