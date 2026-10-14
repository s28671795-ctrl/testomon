/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.phase;

import java.util.Comparator;
import org.apache.cxf.phase.Phase;

public class PhaseComparator
implements Comparator<Phase> {
    @Override
    public int compare(Phase o1, Phase o2) {
        return o1.getPriority() - o2.getPriority();
    }
}

