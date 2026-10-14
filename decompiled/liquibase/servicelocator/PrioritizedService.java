/*
 * Decompiled with CFR 0.152.
 */
package liquibase.servicelocator;

import java.util.Comparator;

public interface PrioritizedService {
    public static final Comparator<PrioritizedService> COMPARATOR = (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority());
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_DATABASE = 5;

    public int getPriority();
}

