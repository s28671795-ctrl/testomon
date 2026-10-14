/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.LoggingErrorHandler;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ThreadSafe
public class BasicParserPool
extends AbstractInitializableComponent
implements ParserPool {
    private final Logger log = LoggerFactory.getLogger(BasicParserPool.class);
    private DocumentBuilderFactory builderFactory;
    private final Stack<SoftReference<DocumentBuilder>> builderPool = new Stack();
    private int maxPoolSize = 5;
    private Map<String, Object> builderAttributes = Collections.emptyMap();
    private boolean coalescing = true;
    private boolean expandEntityReferences = false;
    private Map<String, Boolean> builderFeatures = this.buildDefaultFeatures();
    private boolean ignoreComments = true;
    private boolean ignoreElementContentWhitespace = true;
    private boolean namespaceAware = true;
    private Schema schema = null;
    private boolean dtdValidating = false;
    private boolean xincludeAware = false;
    private EntityResolver entityResolver;
    private ErrorHandler errorHandler = new LoggingErrorHandler(this.log);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nonnull
    public DocumentBuilder getBuilder() throws XMLParserException {
        this.checkInitializedNotDestroyed();
        DocumentBuilder builder = null;
        Stack<SoftReference<DocumentBuilder>> stack = this.builderPool;
        synchronized (stack) {
            while (builder == null && !this.builderPool.isEmpty()) {
                builder = this.builderPool.pop().get();
            }
        }
        if (builder == null) {
            builder = this.createBuilder();
        }
        if (builder != null) {
            this.prepareBuilder(builder);
            return new DocumentBuilderProxy(builder, this);
        }
        throw new XMLParserException("Unable to obtain a DocumentBuilder");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void returnBuilder(@Nullable DocumentBuilder builder) {
        this.checkInitializedNotDestroyed();
        if (builder == null || !(builder instanceof DocumentBuilderProxy)) {
            return;
        }
        DocumentBuilderProxy proxiedBuilder = (DocumentBuilderProxy)builder;
        if (proxiedBuilder.getOwningPool() != this) {
            return;
        }
        DocumentBuilderProxy documentBuilderProxy = proxiedBuilder;
        synchronized (documentBuilderProxy) {
            if (proxiedBuilder.isReturned()) {
                return;
            }
            proxiedBuilder.setReturned(true);
        }
        DocumentBuilder unwrappedBuilder = proxiedBuilder.getProxiedBuilder();
        unwrappedBuilder.reset();
        SoftReference<DocumentBuilder> builderReference = new SoftReference<DocumentBuilder>(unwrappedBuilder);
        Stack<SoftReference<DocumentBuilder>> stack = this.builderPool;
        synchronized (stack) {
            if (this.builderPool.size() < this.maxPoolSize) {
                this.builderPool.push(builderReference);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Nonnull
    public Document newDocument() throws XMLParserException {
        Document document;
        this.checkInitializedNotDestroyed();
        DocumentBuilder builder = null;
        try {
            builder = this.getBuilder();
            document = builder.newDocument();
        }
        finally {
            this.returnBuilder(builder);
        }
        if (document == null) {
            throw new XMLParserException("DocumentBuilder returned a null Document");
        }
        return document;
    }

    @Override
    @Nonnull
    public Document parse(@Nonnull InputStream input) throws XMLParserException {
        this.checkInitializedNotDestroyed();
        Constraint.isNotNull(input, "Input stream can not be null");
        DocumentBuilder builder = this.getBuilder();
        try {
            Document document = builder.parse(input);
            if (document == null) {
                throw new XMLParserException("DocumentBuilder parsed a null Document");
            }
            Document document2 = document;
            return document2;
        }
        catch (SAXException e) {
            throw new XMLParserException("Unable to parse inputstream, it contained invalid XML", e);
        }
        catch (IOException e) {
            throw new XMLParserException("Unable to read data from input stream", e);
        }
        finally {
            this.returnBuilder(builder);
        }
    }

    @Override
    @Nonnull
    public Document parse(@Nonnull Reader input) throws XMLParserException {
        this.checkInitializedNotDestroyed();
        Constraint.isNotNull(input, "Input reader can not be null");
        DocumentBuilder builder = this.getBuilder();
        try {
            Document document = builder.parse(new InputSource(input));
            if (document == null) {
                throw new XMLParserException("DocumentBuilder parsed a null Document");
            }
            Document document2 = document;
            return document2;
        }
        catch (SAXException e) {
            throw new XMLParserException("Invalid XML", e);
        }
        catch (IOException e) {
            throw new XMLParserException("Unable to read XML from input stream", e);
        }
        finally {
            this.returnBuilder(builder);
        }
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int newSize) {
        this.checkNotInitializedNotDestroyed();
        this.maxPoolSize = (int)Constraint.isGreaterThan(0L, newSize, "New maximum pool size must be greater than 0");
    }

    @Nonnull
    @NonnullElements
    public Map<String, Object> getBuilderAttributes() {
        return Collections.unmodifiableMap(this.builderAttributes);
    }

    public void setBuilderAttributes(@Nullable @NullableElements Map<String, Object> newAttributes) {
        this.checkNotInitializedNotDestroyed();
        this.builderAttributes = newAttributes == null ? Collections.emptyMap() : new HashMap<String, Object>(Maps.filterKeys(newAttributes, Predicates.notNull()));
    }

    public boolean isCoalescing() {
        return this.coalescing;
    }

    public void setCoalescing(boolean isCoalescing) {
        this.checkNotInitializedNotDestroyed();
        this.coalescing = isCoalescing;
    }

    public boolean isExpandEntityReferences() {
        return this.expandEntityReferences;
    }

    public void setExpandEntityReferences(boolean expand) {
        this.checkNotInitializedNotDestroyed();
        this.expandEntityReferences = expand;
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    public Map<String, Boolean> getBuilderFeatures() {
        return this.builderFeatures;
    }

    public void setBuilderFeatures(@Nullable @NullableElements Map<String, Boolean> newFeatures) {
        this.checkNotInitializedNotDestroyed();
        this.builderFeatures = newFeatures == null ? Collections.emptyMap() : ImmutableMap.copyOf(Maps.filterKeys(newFeatures, Predicates.notNull()));
    }

    public boolean isIgnoreComments() {
        return this.ignoreComments;
    }

    public void setIgnoreComments(boolean ignore) {
        this.checkNotInitializedNotDestroyed();
        this.ignoreComments = ignore;
    }

    public boolean isIgnoreElementContentWhitespace() {
        return this.ignoreElementContentWhitespace;
    }

    public void setIgnoreElementContentWhitespace(boolean ignore) {
        this.checkNotInitializedNotDestroyed();
        this.ignoreElementContentWhitespace = ignore;
    }

    public boolean isNamespaceAware() {
        return this.namespaceAware;
    }

    public void setNamespaceAware(boolean isNamespaceAware) {
        this.checkNotInitializedNotDestroyed();
        this.namespaceAware = isNamespaceAware;
    }

    @Nullable
    public Schema getSchema() {
        return this.schema;
    }

    public void setSchema(@Nullable Schema newSchema) {
        this.checkNotInitializedNotDestroyed();
        this.schema = newSchema;
        if (this.schema != null) {
            this.setNamespaceAware(true);
            this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaSource");
            this.builderAttributes.remove("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
        }
    }

    @Nullable
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    public void setEntityResolver(@Nullable EntityResolver resolver2) {
        this.checkNotInitializedNotDestroyed();
        this.entityResolver = resolver2;
    }

    @Nonnull
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public void setErrorHandler(@Nonnull ErrorHandler handler) {
        this.checkNotInitializedNotDestroyed();
        this.errorHandler = Constraint.isNotNull(handler, "ErrorHandler may not be null");
    }

    public boolean isDTDValidating() {
        return this.dtdValidating;
    }

    public void setDTDValidating(boolean isValidating) {
        this.checkNotInitializedNotDestroyed();
        this.dtdValidating = isValidating;
    }

    public boolean isXincludeAware() {
        return this.xincludeAware;
    }

    public void setXincludeAware(boolean isXIncludeAware) {
        this.checkNotInitializedNotDestroyed();
        this.xincludeAware = isXIncludeAware;
    }

    protected int getPoolSize() {
        return this.builderPool.size();
    }

    @Nonnull
    protected DocumentBuilder createBuilder() throws XMLParserException {
        this.checkInitializedNotDestroyed();
        try {
            DocumentBuilder builder = this.builderFactory.newDocumentBuilder();
            return builder;
        }
        catch (ParserConfigurationException e) {
            this.log.debug("Unable to create new document builder", e);
            throw new XMLParserException("Unable to create new document builder", e);
        }
    }

    private void prepareBuilder(@Nonnull DocumentBuilder builder) {
        if (this.entityResolver != null) {
            builder.setEntityResolver(this.entityResolver);
        }
        if (this.errorHandler != null) {
            builder.setErrorHandler(this.errorHandler);
        }
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        try {
            DocumentBuilderFactory newFactory = DocumentBuilderFactory.newInstance();
            for (Map.Entry<String, Object> entry : this.builderAttributes.entrySet()) {
                newFactory.setAttribute(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Object> entry : this.builderFeatures.entrySet()) {
                if (entry.getKey() == null) continue;
                newFactory.setFeature(entry.getKey(), (Boolean)entry.getValue());
            }
            newFactory.setCoalescing(this.coalescing);
            newFactory.setExpandEntityReferences(this.expandEntityReferences);
            newFactory.setIgnoringComments(this.ignoreComments);
            newFactory.setIgnoringElementContentWhitespace(this.ignoreElementContentWhitespace);
            newFactory.setNamespaceAware(this.namespaceAware);
            newFactory.setSchema(this.schema);
            newFactory.setValidating(this.dtdValidating);
            newFactory.setXIncludeAware(this.xincludeAware);
            this.builderFactory = newFactory;
        }
        catch (ParserConfigurationException e) {
            throw new ComponentInitializationException("Unable to configure builder factory", e);
        }
    }

    @Override
    protected void doDestroy() {
        this.builderPool.clear();
        super.doDestroy();
    }

    private void checkInitializedNotDestroyed() {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
    }

    private void checkNotInitializedNotDestroyed() {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
    }

    protected Map<String, Boolean> buildDefaultFeatures() {
        HashMap<String, Boolean> features = new HashMap<String, Boolean>();
        features.put("http://javax.xml.XMLConstants/feature/secure-processing", true);
        features.put("http://apache.org/xml/features/disallow-doctype-decl", true);
        return features;
    }

    protected class DocumentBuilderProxy
    extends DocumentBuilder {
        private final DocumentBuilder builder;
        private final ParserPool owningPool;
        private boolean returned;

        public DocumentBuilderProxy(DocumentBuilder target, BasicParserPool owner) {
            this.owningPool = owner;
            this.builder = target;
            this.returned = false;
        }

        @Override
        public DOMImplementation getDOMImplementation() {
            this.checkValidState();
            return this.builder.getDOMImplementation();
        }

        @Override
        public Schema getSchema() {
            this.checkValidState();
            return this.builder.getSchema();
        }

        @Override
        public boolean isNamespaceAware() {
            this.checkValidState();
            return this.builder.isNamespaceAware();
        }

        @Override
        public boolean isValidating() {
            this.checkValidState();
            return this.builder.isValidating();
        }

        @Override
        public boolean isXIncludeAware() {
            this.checkValidState();
            return this.builder.isXIncludeAware();
        }

        @Override
        public Document newDocument() {
            this.checkValidState();
            return this.builder.newDocument();
        }

        @Override
        public Document parse(File f) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(f);
        }

        @Override
        public Document parse(InputSource is) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is);
        }

        @Override
        public Document parse(InputStream is) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is);
        }

        @Override
        public Document parse(InputStream is, String systemId) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(is, systemId);
        }

        @Override
        public Document parse(String uri) throws SAXException, IOException {
            this.checkValidState();
            return this.builder.parse(uri);
        }

        @Override
        public void reset() {
        }

        @Override
        public void setEntityResolver(EntityResolver er) {
            this.checkValidState();
        }

        @Override
        public void setErrorHandler(ErrorHandler eh) {
            this.checkValidState();
        }

        protected ParserPool getOwningPool() {
            return this.owningPool;
        }

        protected DocumentBuilder getProxiedBuilder() {
            return this.builder;
        }

        protected boolean isReturned() {
            return this.returned;
        }

        protected void setReturned(boolean isReturned) {
            this.returned = isReturned;
        }

        protected void checkValidState() {
            if (this.isReturned()) {
                throw new IllegalStateException("DocumentBuilderProxy has already been returned to its owning pool");
            }
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.owningPool.returnBuilder(this);
        }
    }
}

