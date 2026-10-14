/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import liquibase.parser.LiquibaseParser;
import liquibase.serializer.LiquibaseSerializer;
import liquibase.servicelocator.PrioritizedService;

public interface NamespaceDetails
extends PrioritizedService {
    public static final int PRIORITY_EXTENSION = 5;

    public boolean supports(LiquibaseSerializer var1, String var2);

    public boolean supports(LiquibaseParser var1, String var2);

    public String getShortName(String var1);

    public String getSchemaUrl(String var1);

    public String[] getNamespaces();
}

