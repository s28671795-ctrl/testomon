/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.phase;

import java.util.SortedSet;
import org.apache.cxf.phase.Phase;

public interface PhaseManager {
    public SortedSet<Phase> getInPhases();

    public SortedSet<Phase> getOutPhases();
}

