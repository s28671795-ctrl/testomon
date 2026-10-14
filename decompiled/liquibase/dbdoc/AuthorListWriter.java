/*
 * Decompiled with CFR 0.152.
 */
package liquibase.dbdoc;

import liquibase.dbdoc.HTMLListWriter;
import liquibase.resource.Resource;

public class AuthorListWriter
extends HTMLListWriter {
    public AuthorListWriter(Resource outputDir) {
        super("All Authors", "authors.html", "authors", outputDir);
    }
}

