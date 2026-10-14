/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser.core.json;

import liquibase.parser.core.yaml.YamlSnapshotParser;

public class JsonSnapshotParser
extends YamlSnapshotParser {
    @Override
    protected String[] getSupportedFileExtensions() {
        return new String[]{"json"};
    }
}

