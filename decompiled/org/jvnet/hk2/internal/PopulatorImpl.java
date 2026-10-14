/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorFileFinder;
import org.glassfish.hk2.api.DescriptorFileFinderInformation;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Populator;
import org.glassfish.hk2.api.PopulatorPostProcessor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ClasspathDescriptorFileFinder;
import org.glassfish.hk2.utilities.DescriptorImpl;
import org.jvnet.hk2.internal.Collector;

public class PopulatorImpl
implements Populator {
    private final ServiceLocator serviceLocator;
    private final DynamicConfigurationService dcs;

    PopulatorImpl(ServiceLocator serviceLocator, DynamicConfigurationService dcs) {
        this.serviceLocator = serviceLocator;
        this.dcs = dcs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ActiveDescriptor<?>> populate(DescriptorFileFinder fileFinder, PopulatorPostProcessor ... postProcessors) throws IOException {
        List<InputStream> descriptorFileInputStreams;
        LinkedList descriptors = new LinkedList();
        if (fileFinder == null && (fileFinder = this.serviceLocator.getService(DescriptorFileFinder.class, new Annotation[0])) == null) {
            return descriptors;
        }
        if (postProcessors == null) {
            postProcessors = new PopulatorPostProcessor[]{};
        }
        List<String> descriptorInformation = null;
        try {
            DescriptorFileFinderInformation dffi;
            descriptorFileInputStreams = fileFinder.findDescriptorFiles();
            if (fileFinder instanceof DescriptorFileFinderInformation && (descriptorInformation = (dffi = (DescriptorFileFinderInformation)((Object)fileFinder)).getDescriptorFileInformation()) != null && descriptorInformation.size() != descriptorFileInputStreams.size()) {
                throw new IOException("The DescriptorFileFinder implementation " + fileFinder.getClass().getName() + " also implements DescriptorFileFinderInformation, however the cardinality of the list returned from getDescriptorFileInformation (" + descriptorInformation.size() + ") does not equal the cardinality of the list returned from findDescriptorFiles (" + descriptorFileInputStreams.size() + ")");
            }
        }
        catch (IOException ioe) {
            throw ioe;
        }
        catch (Throwable th) {
            throw new MultiException(th);
        }
        Collector collector = new Collector();
        DynamicConfiguration config = this.dcs.createDynamicConfiguration();
        int lcv = 0;
        for (InputStream is : descriptorFileInputStreams) {
            String identifier = descriptorInformation == null ? null : descriptorInformation.get(lcv);
            ++lcv;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is));){
                boolean readOne = false;
                do {
                    DescriptorImpl descriptorImpl = new DescriptorImpl();
                    try {
                        readOne = descriptorImpl.readObject(br);
                    }
                    catch (IOException ioe) {
                        if (identifier != null) {
                            collector.addThrowable(new IOException("InputStream with identifier \"" + identifier + "\" failed", ioe));
                        }
                        collector.addThrowable(ioe);
                    }
                    if (!readOne) continue;
                    for (PopulatorPostProcessor pp : postProcessors) {
                        try {
                            descriptorImpl = pp.process(this.serviceLocator, descriptorImpl);
                        }
                        catch (Throwable th) {
                            if (identifier != null) {
                                collector.addThrowable(new IOException("InputStream with identifier \"" + identifier + "\" failed", th));
                            } else {
                                collector.addThrowable(th);
                            }
                            descriptorImpl = null;
                        }
                        if (descriptorImpl == null) break;
                    }
                    if (descriptorImpl == null) continue;
                    descriptors.add(config.bind(descriptorImpl, false));
                } while (readOne);
            }
        }
        collector.throwIfErrors();
        config.commit();
        return descriptors;
    }

    @Override
    public List<ActiveDescriptor<?>> populate() throws IOException {
        return this.populate(new ClasspathDescriptorFileFinder(), new PopulatorPostProcessor[0]);
    }
}

