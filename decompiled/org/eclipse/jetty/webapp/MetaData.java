/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.eclipse.jetty.util.resource.EmptyResource;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.webapp.AbsoluteOrdering;
import org.eclipse.jetty.webapp.DefaultsDescriptor;
import org.eclipse.jetty.webapp.Descriptor;
import org.eclipse.jetty.webapp.DescriptorProcessor;
import org.eclipse.jetty.webapp.DiscoveredAnnotation;
import org.eclipse.jetty.webapp.FragmentDescriptor;
import org.eclipse.jetty.webapp.Ordering;
import org.eclipse.jetty.webapp.Origin;
import org.eclipse.jetty.webapp.OverrideDescriptor;
import org.eclipse.jetty.webapp.RelativeOrdering;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaData {
    private static final Logger LOG = LoggerFactory.getLogger(MetaData.class);
    public static final String VALIDATE_XML = "org.eclipse.jetty.webapp.validateXml";
    public static final String ORDERED_LIBS = "jakarta.servlet.context.orderedLibs";
    public static final Resource NON_FRAG_RESOURCE = EmptyResource.INSTANCE;
    private final AutoLock _lock = new AutoLock();
    protected Map<String, OriginInfo> _origins = new HashMap<String, OriginInfo>();
    protected WebDescriptor _webDefaultsRoot;
    protected WebDescriptor _webXmlRoot;
    protected final List<WebDescriptor> _webOverrideRoots = new ArrayList<WebDescriptor>();
    protected boolean _metaDataComplete;
    protected final List<DescriptorProcessor> _descriptorProcessors = new ArrayList<DescriptorProcessor>();
    protected final List<FragmentDescriptor> _webFragmentRoots = new ArrayList<FragmentDescriptor>();
    protected final Map<String, FragmentDescriptor> _webFragmentNameMap = new HashMap<String, FragmentDescriptor>();
    protected final Map<Resource, FragmentDescriptor> _webFragmentResourceMap = new HashMap<Resource, FragmentDescriptor>();
    protected final Map<Resource, List<DiscoveredAnnotation>> _annotations = new HashMap<Resource, List<DiscoveredAnnotation>>();
    protected final List<Resource> _webInfClasses = new ArrayList<Resource>();
    protected final List<Resource> _webInfJars = new ArrayList<Resource>();
    protected final List<Resource> _orderedContainerResources = new ArrayList<Resource>();
    protected final List<Resource> _orderedWebInfResources = new ArrayList<Resource>();
    protected Ordering _ordering;
    protected boolean _allowDuplicateFragmentNames = false;
    protected boolean _validateXml = false;

    public void clear() {
        this._webDefaultsRoot = null;
        this._origins.clear();
        this._webXmlRoot = null;
        this._webOverrideRoots.clear();
        this._metaDataComplete = false;
        this._annotations.clear();
        this._descriptorProcessors.clear();
        this._webFragmentRoots.clear();
        this._webFragmentNameMap.clear();
        this._webFragmentResourceMap.clear();
        this._annotations.clear();
        this._webInfJars.clear();
        this._orderedWebInfResources.clear();
        this._orderedContainerResources.clear();
        this._ordering = null;
        this._allowDuplicateFragmentNames = false;
    }

    public void setDefaultsDescriptor(DefaultsDescriptor descriptor) throws Exception {
        this._webDefaultsRoot = descriptor;
        this._webDefaultsRoot.parse(WebDescriptor.getParser(this.isValidateXml()));
        if (this._webDefaultsRoot.isOrdered()) {
            Ordering ordering = this.getOrdering();
            if (ordering == null) {
                ordering = new AbsoluteOrdering(this);
            }
            List<String> order = this._webDefaultsRoot.getOrdering();
            for (String s2 : order) {
                if (s2.equalsIgnoreCase("others")) {
                    ((AbsoluteOrdering)ordering).addOthers();
                    continue;
                }
                ((AbsoluteOrdering)ordering).add(s2);
            }
            this.setOrdering(ordering);
        }
    }

    public void setWebDescriptor(WebDescriptor descriptor) throws Exception {
        this._webXmlRoot = descriptor;
        this._webXmlRoot.parse(WebDescriptor.getParser(this.isValidateXml()));
        this._metaDataComplete = WebDescriptor.isMetaDataComplete(this._webXmlRoot);
        if (this._webXmlRoot.isOrdered()) {
            Ordering ordering = this.getOrdering();
            if (ordering == null) {
                ordering = new AbsoluteOrdering(this);
            }
            List<String> order = this._webXmlRoot.getOrdering();
            for (String s2 : order) {
                if (s2.equalsIgnoreCase("others")) {
                    ((AbsoluteOrdering)ordering).addOthers();
                    continue;
                }
                ((AbsoluteOrdering)ordering).add(s2);
            }
            this.setOrdering(ordering);
        }
    }

    public void addOverrideDescriptor(OverrideDescriptor descriptor) throws Exception {
        descriptor.parse(WebDescriptor.getParser(this.isValidateXml()));
        switch (descriptor.getMetaDataComplete()) {
            case True: {
                this._metaDataComplete = true;
                break;
            }
            case False: {
                this._metaDataComplete = false;
                break;
            }
        }
        if (descriptor.isOrdered()) {
            Ordering ordering = this.getOrdering();
            if (ordering == null) {
                ordering = new AbsoluteOrdering(this);
            }
            List<String> order = descriptor.getOrdering();
            for (String s2 : order) {
                if (s2.equalsIgnoreCase("others")) {
                    ((AbsoluteOrdering)ordering).addOthers();
                    continue;
                }
                ((AbsoluteOrdering)ordering).add(s2);
            }
            this.setOrdering(ordering);
        }
        this._webOverrideRoots.add(descriptor);
    }

    public void addFragmentDescriptor(Resource jarResource, FragmentDescriptor descriptor) throws Exception {
        if (this._metaDataComplete) {
            return;
        }
        Objects.requireNonNull(jarResource);
        Objects.requireNonNull(descriptor);
        this._webFragmentResourceMap.put(jarResource, descriptor);
        this._webFragmentRoots.add(descriptor);
        descriptor.parse(WebDescriptor.getParser(this.isValidateXml()));
        if (descriptor.getName() != null) {
            Descriptor existing = this._webFragmentNameMap.get(descriptor.getName());
            if (existing != null && !this.isAllowDuplicateFragmentNames()) {
                throw new IllegalStateException("Duplicate fragment name: " + descriptor.getName() + " for " + existing.getResource() + " and " + descriptor.getResource());
            }
            this._webFragmentNameMap.put(descriptor.getName(), descriptor);
        }
        if (this._ordering == null && descriptor.isOrdered()) {
            this.setOrdering(new RelativeOrdering(this));
            return;
        }
        this.orderFragments();
    }

    public void addDiscoveredAnnotations(List<DiscoveredAnnotation> annotations) {
        if (annotations == null) {
            return;
        }
        for (DiscoveredAnnotation a : annotations) {
            this.addDiscoveredAnnotation(a);
        }
    }

    public void addDiscoveredAnnotation(DiscoveredAnnotation annotation) {
        if (annotation == null) {
            return;
        }
        try (AutoLock l = this._lock.lock();){
            Resource enclosingResource = EmptyResource.INSTANCE;
            Resource resource = annotation.getResource();
            if (resource != null) {
                enclosingResource = this.getEnclosingResource(this._webInfClasses, resource);
                if (enclosingResource == null) {
                    enclosingResource = this.getEnclosingResource(this._webInfJars, resource);
                }
                if (enclosingResource == null) {
                    enclosingResource = this.getEnclosingResource(this._orderedContainerResources, resource);
                }
                if (enclosingResource == null) {
                    enclosingResource = EmptyResource.INSTANCE;
                }
            }
            List list = this._annotations.computeIfAbsent(enclosingResource, k -> new ArrayList());
            list.add(annotation);
        }
    }

    private Resource getEnclosingResource(List<Resource> resources, Resource resource) {
        Resource enclosingResource = null;
        try {
            for (Resource r : resources) {
                if (!Resource.isContainedIn(resource, r)) continue;
                enclosingResource = r;
                break;
            }
            return enclosingResource;
        }
        catch (Exception e) {
            LOG.warn("Not contained within?", e);
            return null;
        }
    }

    public void addDescriptorProcessor(DescriptorProcessor p) {
        this._descriptorProcessors.add(p);
    }

    public void removeDescriptorProcessor(DescriptorProcessor p) {
        this._descriptorProcessors.remove(p);
    }

    public void orderFragments() {
        this._orderedWebInfResources.clear();
        if (this.getOrdering() != null) {
            this._orderedWebInfResources.addAll(this.getOrdering().order(this._webInfJars));
        }
    }

    public void resolve(WebAppContext context) throws Exception {
        LOG.debug("metadata resolve {}", (Object)context);
        this._origins.clear();
        List<Resource> orderedWebInfJars = null;
        if (this.isOrdered()) {
            orderedWebInfJars = this.getWebInfResources(true);
            ArrayList orderedLibs = new ArrayList();
            for (Resource resource : orderedWebInfJars) {
                String fullname = resource.getName();
                int i = fullname.indexOf(".jar");
                int j = fullname.lastIndexOf("/", i);
                orderedLibs.add(fullname.substring(j + 1, i + 4));
            }
            context.setAttribute(ORDERED_LIBS, Collections.unmodifiableList(orderedLibs));
        }
        if (this._webXmlRoot != null) {
            context.getServletContext().setEffectiveMajorVersion(this._webXmlRoot.getMajorVersion());
            context.getServletContext().setEffectiveMinorVersion(this._webXmlRoot.getMinorVersion());
        }
        for (DescriptorProcessor p : this._descriptorProcessors) {
            p.process(context, this.getDefaultsDescriptor());
            p.process(context, this.getWebDescriptor());
            for (WebDescriptor wd : this.getOverrideDescriptors()) {
                LOG.debug("process {} {} {}", context, p, wd);
                p.process(context, wd);
            }
        }
        ArrayList<Resource> resources = new ArrayList<Resource>();
        resources.add(EmptyResource.INSTANCE);
        resources.addAll(this._orderedContainerResources);
        resources.addAll(this._webInfClasses);
        resources.addAll(this.getWebInfResources(this.isOrdered()));
        for (Resource resource : resources) {
            List<DiscoveredAnnotation> annotations;
            FragmentDescriptor fd = this._webFragmentResourceMap.get(resource);
            if (fd != null) {
                for (DescriptorProcessor p : this._descriptorProcessors) {
                    LOG.debug("process {} {}", (Object)context, (Object)fd);
                    p.process(context, fd);
                }
            }
            if ((annotations = this._annotations.get(resource)) == null) continue;
            for (DiscoveredAnnotation a : annotations) {
                LOG.debug("apply {}", (Object)a);
                a.apply();
            }
        }
    }

    public boolean isDistributable() {
        boolean distributable = this._webDefaultsRoot != null && this._webDefaultsRoot.isDistributable() || this._webXmlRoot != null && this._webXmlRoot.isDistributable();
        for (WebDescriptor d : this._webOverrideRoots) {
            distributable &= d.isDistributable();
        }
        if (this.isOrdered()) {
            List<Resource> orderedResources = this.getWebInfResources(true);
            for (Resource r : orderedResources) {
                FragmentDescriptor d = this._webFragmentResourceMap.get(r);
                if (d == null) continue;
                distributable = distributable && d.isDistributable();
            }
        }
        return distributable;
    }

    public WebDescriptor getWebDescriptor() {
        return this._webXmlRoot;
    }

    public List<WebDescriptor> getOverrideDescriptors() {
        return this._webOverrideRoots;
    }

    public WebDescriptor getDefaultsDescriptor() {
        return this._webDefaultsRoot;
    }

    public boolean isOrdered() {
        return this.getOrdering() != null;
    }

    public Ordering getOrdering() {
        return this._ordering;
    }

    public void setOrdering(Ordering o) {
        this._ordering = o;
        this.orderFragments();
    }

    public FragmentDescriptor getFragmentDescriptor(String name) {
        return this._webFragmentNameMap.get(name);
    }

    public FragmentDescriptor getFragmentDescriptor(Resource descriptorResource) {
        return this._webFragmentRoots.stream().filter(d -> d.getResource().equals(descriptorResource)).findFirst().orElse(null);
    }

    public Resource getJarForFragmentName(String name) {
        FragmentDescriptor f = this.getFragmentDescriptor(name);
        if (f == null) {
            return null;
        }
        Resource jar = null;
        for (Map.Entry<Resource, FragmentDescriptor> entry : this._webFragmentResourceMap.entrySet()) {
            if (!entry.getValue().equals(f)) continue;
            jar = entry.getKey();
        }
        return jar;
    }

    public FragmentDescriptor getFragmentDescriptorForJar(Resource jar) {
        return this._webFragmentResourceMap.get(jar);
    }

    public Map<String, FragmentDescriptor> getNamedFragmentDescriptors() {
        return Collections.unmodifiableMap(this._webFragmentNameMap);
    }

    public Origin getOrigin(String name) {
        OriginInfo x = this._origins.get(name);
        if (x == null) {
            return Origin.NotSet;
        }
        return x.getOriginType();
    }

    public OriginInfo getOriginInfo(String name) {
        OriginInfo x = this._origins.get(name);
        if (x == null) {
            return null;
        }
        return x;
    }

    public Descriptor getOriginDescriptor(String name) {
        OriginInfo o = this._origins.get(name);
        if (o == null) {
            return null;
        }
        return o.getDescriptor();
    }

    public void setOrigin(String name, Descriptor d) {
        if (name == null) {
            return;
        }
        OriginInfo x = new OriginInfo(name, d);
        this._origins.put(name, x);
    }

    public void setOrigin(String name, Annotation annotation, Class<?> annotated) {
        if (name == null) {
            return;
        }
        OriginInfo x = new OriginInfo(name, annotation, annotated);
        this._origins.put(name, x);
    }

    public void setOriginAPI(String name) {
        if (name == null) {
            return;
        }
        OriginInfo x = new OriginInfo(name);
        this._origins.put(name, x);
    }

    public boolean isMetaDataComplete() {
        return this._metaDataComplete;
    }

    public void addWebInfResource(Resource newResource) {
        this._webInfJars.add(newResource);
    }

    public List<Resource> getWebInfResources(boolean withOrdering) {
        if (!withOrdering) {
            return Collections.unmodifiableList(this._webInfJars);
        }
        return Collections.unmodifiableList(this._orderedWebInfResources);
    }

    public List<Resource> getContainerResources() {
        return Collections.unmodifiableList(this._orderedContainerResources);
    }

    public void addContainerResource(Resource jar) {
        this._orderedContainerResources.add(jar);
    }

    public void setWebInfClassesResources(List<Resource> dirs) {
        this._webInfClasses.addAll(dirs);
    }

    public List<Resource> getWebInfClassesResources() {
        return Collections.unmodifiableList(this._webInfClasses);
    }

    public boolean isAllowDuplicateFragmentNames() {
        return this._allowDuplicateFragmentNames;
    }

    public void setAllowDuplicateFragmentNames(boolean allowDuplicateFragmentNames) {
        this._allowDuplicateFragmentNames = allowDuplicateFragmentNames;
    }

    public boolean isValidateXml() {
        return this._validateXml;
    }

    public void setValidateXml(boolean validateXml) {
        this._validateXml = validateXml;
    }

    public Map<String, OriginInfo> getOrigins() {
        return Collections.unmodifiableMap(this._origins);
    }

    public static enum Complete {
        NotSet,
        True,
        False;

    }

    public static class OriginInfo {
        private final String name;
        private final Origin origin;
        private final Descriptor descriptor;
        private final Annotation annotation;
        private final Class<?> annotated;

        public OriginInfo(String n, Annotation a, Class<?> ac) {
            if (Objects.isNull(n)) {
                throw new IllegalArgumentException("No name");
            }
            this.name = n;
            this.origin = Origin.of(a);
            this.descriptor = null;
            this.annotation = a;
            this.annotated = ac;
        }

        public OriginInfo(String n, Descriptor d) {
            if (Objects.isNull(n)) {
                throw new IllegalArgumentException("No name");
            }
            if (Objects.isNull(d)) {
                throw new IllegalArgumentException("No descriptor");
            }
            this.name = n;
            this.origin = Origin.of(d);
            this.descriptor = d;
            this.annotation = null;
            this.annotated = null;
        }

        public OriginInfo(String n) {
            if (Objects.isNull(n)) {
                throw new IllegalArgumentException("No name");
            }
            this.name = n;
            this.origin = Origin.API;
            this.annotation = null;
            this.descriptor = null;
            this.annotated = null;
        }

        public String getName() {
            return this.name;
        }

        public Origin getOriginType() {
            return this.origin;
        }

        public Descriptor getDescriptor() {
            return this.descriptor;
        }

        public String toString() {
            if (this.descriptor != null) {
                return this.descriptor.toString();
            }
            if (this.annotation != null) {
                return "@" + this.annotation.annotationType().getSimpleName() + "(" + this.annotated.getName() + ")";
            }
            return this.origin.toString();
        }
    }
}

