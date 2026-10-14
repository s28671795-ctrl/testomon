/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import java.io.IOException;
import java.io.OutputStream;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.servicelocator.PrioritizedService;
import liquibase.snapshot.DatabaseSnapshot;

public interface SnapshotSerializer
extends PrioritizedService {
    public String[] getValidFileExtensions();

    public String serialize(LiquibaseSerializable var1, boolean var2);

    public void write(DatabaseSnapshot var1, OutputStream var2) throws IOException;
}

