/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.registry;

import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public final class AlgorithmRegistry {
    public static Set getAllDigestAlgorithms() {
        ArrayList<String> algos = new ArrayList<String>(Security.getAlgorithms("MessageDigest"));
        Collections.sort(algos);
        return Collections.unmodifiableSet(new LinkedHashSet<String>(algos));
    }

    public static Set getAllPBEAlgorithms() {
        ArrayList<String> algos = new ArrayList<String>(Security.getAlgorithms("Cipher"));
        Collections.sort(algos);
        LinkedHashSet<String> pbeAlgos = new LinkedHashSet<String>();
        for (String algo : algos) {
            if (algo == null || !algo.startsWith("PBE")) continue;
            pbeAlgos.add(algo);
        }
        return Collections.unmodifiableSet(pbeAlgos);
    }

    private AlgorithmRegistry() {
    }
}

