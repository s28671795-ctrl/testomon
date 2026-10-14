/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.util.ConcurrentModificationException;

public final class MutationTracker$ {
    public static final MutationTracker$ MODULE$ = new MutationTracker$();

    public void checkMutations(int expectedCount, int actualCount, String message) throws ConcurrentModificationException {
        if (actualCount != expectedCount) {
            throw new ConcurrentModificationException(message);
        }
    }

    public void checkMutationsForIteration(int expectedCount, int actualCount) throws ConcurrentModificationException {
        this.checkMutations(expectedCount, actualCount, "mutation occurred during iteration");
    }

    private MutationTracker$() {
    }
}

