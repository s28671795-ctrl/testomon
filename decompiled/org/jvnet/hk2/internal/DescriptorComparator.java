/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.io.Serializable;
import java.util.Comparator;
import org.glassfish.hk2.api.Descriptor;

public class DescriptorComparator
implements Comparator<Descriptor>,
Serializable {
    private static final long serialVersionUID = 4454509124508404602L;

    @Override
    public int compare(Descriptor o1, Descriptor o2) {
        long o2ServiceId;
        long o2LocatorId;
        int o2Ranking;
        int o1Ranking = o1.getRanking();
        if (o1Ranking < (o2Ranking = o2.getRanking())) {
            return 1;
        }
        if (o1Ranking > o2Ranking) {
            return -1;
        }
        long o1LocatorId = o1.getLocatorId();
        if (o1LocatorId < (o2LocatorId = o2.getLocatorId().longValue())) {
            return 1;
        }
        if (o1LocatorId > o2LocatorId) {
            return -1;
        }
        long o1ServiceId = o1.getServiceId();
        if (o1ServiceId > (o2ServiceId = o2.getServiceId().longValue())) {
            return 1;
        }
        if (o1ServiceId < o2ServiceId) {
            return -1;
        }
        return 0;
    }
}

