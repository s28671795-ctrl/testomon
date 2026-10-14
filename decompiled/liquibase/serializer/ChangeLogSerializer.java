/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.serializer.LiquibaseSerializer;
import liquibase.servicelocator.PrioritizedService;

public interface ChangeLogSerializer
extends LiquibaseSerializer,
PrioritizedService {
    public <T extends ChangeLogChild> void write(List<T> var1, OutputStream var2) throws IOException;

    public void append(ChangeSet var1, File var2) throws IOException;
}

