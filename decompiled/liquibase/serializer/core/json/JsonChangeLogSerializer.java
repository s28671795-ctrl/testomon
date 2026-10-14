/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.json;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import liquibase.GlobalConfiguration;
import liquibase.changelog.ChangeLogChild;
import liquibase.serializer.core.yaml.YamlChangeLogSerializer;
import liquibase.util.StringUtil;

public class JsonChangeLogSerializer
extends YamlChangeLogSerializer {
    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        writer.write("{ \"databaseChangeLog\": [\n");
        int i = 0;
        for (ChangeLogChild child : children) {
            String serialized = this.serialize(child, true);
            if (++i < children.size()) {
                serialized = serialized.replaceFirst("}\\s*$", "},\n");
            }
            writer.write(StringUtil.indent(serialized, 2));
            writer.write("\n");
        }
        writer.write("]}");
        writer.flush();
    }

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"json"};
    }
}

