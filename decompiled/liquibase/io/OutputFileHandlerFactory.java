/*
 * Decompiled with CFR 0.152.
 */
package liquibase.io;

import liquibase.io.OutputFileHandler;
import liquibase.plugin.AbstractPluginFactory;

public class OutputFileHandlerFactory
extends AbstractPluginFactory<OutputFileHandler> {
    @Override
    protected Class<OutputFileHandler> getPluginClass() {
        return OutputFileHandler.class;
    }

    @Override
    protected int getPriority(OutputFileHandler obj, Object ... args2) {
        return obj.getPriority();
    }

    public OutputFileHandler getOutputFileHandler(String outputFile) {
        return (OutputFileHandler)this.getPlugin(outputFile);
    }

    private OutputFileHandlerFactory() {
    }
}

