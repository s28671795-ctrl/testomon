/*
 * Decompiled with CFR 0.152.
 */
package liquibase.resource;

import java.io.IOException;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.logging.Logger;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StringUtil;

public class SearchPathResourceAccessor
extends CompositeResourceAccessor {
    public SearchPathResourceAccessor(ResourceAccessor ... defaultAccessors) {
        this(GlobalConfiguration.SEARCH_PATH.getCurrentValue(), defaultAccessors);
    }

    public SearchPathResourceAccessor(String searchPath, ResourceAccessor ... defaultAccessors) {
        super(new ResourceAccessor[0]);
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        if (searchPath == null) {
            for (ResourceAccessor accessor : defaultAccessors) {
                this.addResourceAccessor(accessor);
            }
        } else {
            PathHandlerFactory parserFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
            StringUtil.splitAndTrim(searchPath, ",").forEach(path -> {
                try {
                    this.addResourceAccessor(parserFactory.getResourceAccessor((String)path));
                }
                catch (IOException e) {
                    Scope.getCurrentScope().getUI().sendMessage(e.getMessage());
                    log.severe(e.getMessage(), e);
                }
            });
        }
        StringBuilder logMessage = new StringBuilder("Overall search path: " + System.lineSeparator());
        for (String location : this.describeLocations()) {
            logMessage.append("  - ").append(location).append(System.lineSeparator());
        }
        log.fine(logMessage.toString().trim());
    }

    public SearchPathResourceAccessor addResourceAccessor(String root) throws IOException {
        PathHandlerFactory parserFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
        return (SearchPathResourceAccessor)super.addResourceAccessor(parserFactory.getResourceAccessor(root));
    }
}

