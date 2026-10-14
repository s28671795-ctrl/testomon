/*
 * Decompiled with CFR 0.152.
 */
package liquibase.io;

import java.io.IOException;
import java.io.OutputStream;
import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.io.OutputFileHandler;
import liquibase.resource.OpenOptions;
import liquibase.resource.PathHandlerFactory;

public class StandardOutputFileHandler
implements OutputFileHandler {
    protected OutputStream outputStream;

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void create(String outputFile, CommandScope commandScope) throws IOException {
        PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
        this.outputStream = pathHandlerFactory.openResourceOutputStream(outputFile, new OpenOptions());
        commandScope.setOutput(this.outputStream);
    }

    @Override
    public void close() throws IOException {
        if (this.outputStream != null) {
            this.outputStream.flush();
            this.outputStream.close();
        }
    }
}

