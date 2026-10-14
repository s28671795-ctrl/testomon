/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import liquibase.serializer.LiquibaseSerializable;
import liquibase.servicelocator.PrioritizedService;

public interface LiquibaseSerializer
extends PrioritizedService {
    public String[] getValidFileExtensions();

    public String serialize(LiquibaseSerializable var1, boolean var2);
}

