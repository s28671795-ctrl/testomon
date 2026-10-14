/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.types.resources.FileResource
 */
package liquibase.integration.ant.type;

import liquibase.serializer.ChangeLogSerializer;
import org.apache.tools.ant.types.resources.FileResource;

public class ChangeLogOutputFile {
    private FileResource outputFile;
    private String encoding;
    private ChangeLogSerializer changeLogSerializer;

    public FileResource getOutputFile() {
        return this.outputFile;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public ChangeLogSerializer getChangeLogSerializer() {
        return this.changeLogSerializer;
    }

    public void setOutputFile(FileResource outputFile) {
        this.outputFile = outputFile;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setChangeLogSerializer(ChangeLogSerializer changeLogSerializer) {
        this.changeLogSerializer = changeLogSerializer;
    }
}

