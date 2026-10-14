/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import java.io.IOException;
import java.util.Timer;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resource.Resource;
import org.joda.time.DateTime;
import org.opensaml.saml.metadata.resolver.impl.AbstractReloadingMetadataResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceBackedMetadataResolver
extends AbstractReloadingMetadataResolver {
    private final Logger log = LoggerFactory.getLogger(ResourceBackedMetadataResolver.class);
    private Resource metadataResource;

    public ResourceBackedMetadataResolver(Timer timer, Resource resource) throws IOException {
        super(timer);
        if (!resource.exists()) {
            throw new IOException("Resource " + resource.getDescription() + " does not exist.");
        }
        this.metadataResource = resource;
    }

    public ResourceBackedMetadataResolver(Resource resource) throws IOException {
        if (!resource.exists()) {
            throw new IOException("Resource " + resource.getDescription() + " does not exist.");
        }
        this.metadataResource = resource;
    }

    @Override
    protected void doDestroy() {
        this.metadataResource = null;
        super.doDestroy();
    }

    @Override
    protected String getMetadataIdentifier() {
        return this.metadataResource.getDescription();
    }

    @Override
    protected byte[] fetchMetadata() throws ResolverException {
        try {
            DateTime metadataUpdateTime = new DateTime(this.metadataResource.lastModified());
            this.log.debug("{} Resource {} was last modified {}", this.getLogPrefix(), this.metadataResource.getDescription(), metadataUpdateTime);
            if (this.getLastRefresh() == null || metadataUpdateTime.isAfter(this.getLastRefresh())) {
                return this.inputstreamToByteArray(this.metadataResource.getInputStream());
            }
            return null;
        }
        catch (IOException e) {
            String errorMsg = "Unable to read metadata file";
            this.log.error("{} Unable to read metadata file", (Object)this.getLogPrefix(), (Object)e);
            throw new ResolverException("Unable to read metadata file", e);
        }
    }
}

