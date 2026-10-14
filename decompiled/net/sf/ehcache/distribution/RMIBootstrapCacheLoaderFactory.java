/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.Properties;
import net.sf.ehcache.bootstrap.BootstrapCacheLoaderFactory;
import net.sf.ehcache.distribution.RMIBootstrapCacheLoader;
import net.sf.ehcache.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMIBootstrapCacheLoaderFactory
extends BootstrapCacheLoaderFactory<RMIBootstrapCacheLoader> {
    public static final String MAXIMUM_CHUNK_SIZE_BYTES = "maximumChunkSizeBytes";
    protected static final int DEFAULT_MAXIMUM_CHUNK_SIZE_BYTES = 5000000;
    protected static final int ONE_HUNDRED_MB = 100000000;
    protected static final int FIVE_KB = 5000;
    private static final Logger LOG = LoggerFactory.getLogger(RMIBootstrapCacheLoaderFactory.class.getName());

    @Override
    public RMIBootstrapCacheLoader createBootstrapCacheLoader(Properties properties) {
        boolean bootstrapAsynchronously = this.extractBootstrapAsynchronously(properties);
        int maximumChunkSizeBytes = this.extractMaximumChunkSizeBytes(properties);
        return new RMIBootstrapCacheLoader(bootstrapAsynchronously, maximumChunkSizeBytes);
    }

    protected int extractMaximumChunkSizeBytes(Properties properties) {
        int maximumChunkSizeBytes;
        block5: {
            String maximumChunkSizeBytesString = PropertyUtil.extractAndLogProperty(MAXIMUM_CHUNK_SIZE_BYTES, properties);
            if (maximumChunkSizeBytesString != null) {
                try {
                    int maximumChunkSizeBytesCandidate = Integer.parseInt(maximumChunkSizeBytesString);
                    if (maximumChunkSizeBytesCandidate < 5000 || maximumChunkSizeBytesCandidate > 100000000) {
                        LOG.warn("Trying to set the chunk size to an unreasonable number. Using the default instead.");
                        maximumChunkSizeBytes = 5000000;
                        break block5;
                    }
                    maximumChunkSizeBytes = maximumChunkSizeBytesCandidate;
                }
                catch (NumberFormatException e) {
                    LOG.warn("Number format exception trying to set chunk size. Using the default instead.");
                    maximumChunkSizeBytes = 5000000;
                }
            } else {
                maximumChunkSizeBytes = 5000000;
            }
        }
        return maximumChunkSizeBytes;
    }
}

