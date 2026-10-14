/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class SameFileAliasChecker
implements ContextHandler.AliasCheck {
    private static final Logger LOG = LoggerFactory.getLogger(SameFileAliasChecker.class);

    public SameFileAliasChecker() {
        LOG.warn("SameFileAliasChecker is deprecated");
    }

    @Override
    public boolean check(String pathInContext, Resource resource) {
        if (File.separatorChar != '/' && pathInContext.indexOf(File.separatorChar) >= 0) {
            return false;
        }
        if (!(resource instanceof PathResource)) {
            return false;
        }
        try {
            PathResource pathResource = (PathResource)resource;
            Path path = pathResource.getPath();
            Path alias = pathResource.getAliasPath();
            if (Files.isSameFile(path, alias)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Allow alias to same file {} --> {}", (Object)path, (Object)alias);
                }
                return true;
            }
        }
        catch (IOException e) {
            LOG.trace("IGNORED", e);
        }
        return false;
    }
}

