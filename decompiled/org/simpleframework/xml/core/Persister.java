/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.EmptyMatcher;
import org.simpleframework.xml.core.Session;
import org.simpleframework.xml.core.SessionManager;
import org.simpleframework.xml.core.Source;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.core.Traverser;
import org.simpleframework.xml.filter.Filter;
import org.simpleframework.xml.filter.PlatformFilter;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.TreeStrategy;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeBuilder;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.transform.Matcher;

public class Persister
implements Serializer {
    private final SessionManager manager;
    private final Strategy strategy;
    private final Support support;
    private final Format format;

    public Persister() {
        this(new HashMap());
    }

    public Persister(Format format) {
        this((Strategy)new TreeStrategy(), format);
    }

    public Persister(Map filter2) {
        this(new PlatformFilter(filter2));
    }

    public Persister(Map filter2, Format format) {
        this(new PlatformFilter(filter2));
    }

    public Persister(Filter filter2) {
        this((Strategy)new TreeStrategy(), filter2);
    }

    public Persister(Filter filter2, Format format) {
        this((Strategy)new TreeStrategy(), filter2, format);
    }

    public Persister(Matcher matcher) {
        this((Strategy)new TreeStrategy(), matcher);
    }

    public Persister(Matcher matcher, Format format) {
        this((Strategy)new TreeStrategy(), matcher, format);
    }

    public Persister(Strategy strategy) {
        this(strategy, new HashMap());
    }

    public Persister(Strategy strategy, Format format) {
        this(strategy, new HashMap(), format);
    }

    public Persister(Filter filter2, Matcher matcher) {
        this((Strategy)new TreeStrategy(), filter2, matcher);
    }

    public Persister(Filter filter2, Matcher matcher, Format format) {
        this(new TreeStrategy(), filter2, matcher, format);
    }

    public Persister(Strategy strategy, Map data) {
        this(strategy, new PlatformFilter(data));
    }

    public Persister(Strategy strategy, Map data, Format format) {
        this(strategy, (Filter)new PlatformFilter(data), format);
    }

    public Persister(Strategy strategy, Filter filter2) {
        this(strategy, filter2, new Format());
    }

    public Persister(Strategy strategy, Filter filter2, Format format) {
        this(strategy, filter2, new EmptyMatcher(), format);
    }

    public Persister(Strategy strategy, Matcher matcher) {
        this(strategy, (Filter)new PlatformFilter(), matcher);
    }

    public Persister(Strategy strategy, Matcher matcher, Format format) {
        this(strategy, new PlatformFilter(), matcher, format);
    }

    public Persister(Strategy strategy, Filter filter2, Matcher matcher) {
        this(strategy, filter2, matcher, new Format());
    }

    public Persister(Strategy strategy, Filter filter2, Matcher matcher, Format format) {
        this.support = new Support(filter2, matcher, format);
        this.manager = new SessionManager();
        this.strategy = strategy;
        this.format = format;
    }

    @Override
    public <T> T read(Class<? extends T> type, String source2) throws Exception {
        return (T)this.read((T)type, source2, true);
    }

    @Override
    public <T> T read(Class<? extends T> type, File source2) throws Exception {
        return (T)this.read((T)type, source2, true);
    }

    @Override
    public <T> T read(Class<? extends T> type, InputStream source2) throws Exception {
        return (T)this.read((T)type, source2, true);
    }

    @Override
    public <T> T read(Class<? extends T> type, Reader source2) throws Exception {
        return (T)this.read((T)type, source2, true);
    }

    @Override
    public <T> T read(Class<? extends T> type, InputNode source2) throws Exception {
        return (T)this.read((T)type, source2, true);
    }

    @Override
    public <T> T read(Class<? extends T> type, String source2, boolean strict) throws Exception {
        return (T)this.read((T)type, (Reader)new StringReader(source2), strict);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T read(Class<? extends T> type, File source2, boolean strict) throws Exception {
        try (FileInputStream file = new FileInputStream(source2);){
            Class<? extends T> clazz = this.read((T)type, (InputStream)file, strict);
            return (T)clazz;
        }
    }

    @Override
    public <T> T read(Class<? extends T> type, InputStream source2, boolean strict) throws Exception {
        return (T)this.read((T)type, NodeBuilder.read(source2), strict);
    }

    @Override
    public <T> T read(Class<? extends T> type, Reader source2, boolean strict) throws Exception {
        return (T)this.read((T)type, NodeBuilder.read(source2), strict);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T read(Class<? extends T> type, InputNode node, boolean strict) throws Exception {
        Session session = this.manager.open(strict);
        try {
            Class<? extends T> clazz = this.read((T)type, node, session);
            return (T)clazz;
        }
        finally {
            this.manager.close();
        }
    }

    private <T> T read(Class<? extends T> type, InputNode node, Session session) throws Exception {
        return (T)this.read((T)type, node, (Context)new Source(this.strategy, this.support, session));
    }

    private <T> T read(Class<? extends T> type, InputNode node, Context context) throws Exception {
        return (T)new Traverser(context).read(node, type);
    }

    @Override
    public <T> T read(T value, String source2) throws Exception {
        return this.read(value, source2, true);
    }

    @Override
    public <T> T read(T value, File source2) throws Exception {
        return this.read(value, source2, true);
    }

    @Override
    public <T> T read(T value, InputStream source2) throws Exception {
        return this.read(value, source2, true);
    }

    @Override
    public <T> T read(T value, Reader source2) throws Exception {
        return this.read(value, source2, true);
    }

    @Override
    public <T> T read(T value, InputNode source2) throws Exception {
        return this.read(value, source2, true);
    }

    @Override
    public <T> T read(T value, String source2, boolean strict) throws Exception {
        return this.read(value, (Reader)new StringReader(source2), strict);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T read(T value, File source2, boolean strict) throws Exception {
        try (FileInputStream file = new FileInputStream(source2);){
            T t2 = this.read(value, (InputStream)file, strict);
            return t2;
        }
    }

    @Override
    public <T> T read(T value, InputStream source2, boolean strict) throws Exception {
        return this.read(value, NodeBuilder.read(source2), strict);
    }

    @Override
    public <T> T read(T value, Reader source2, boolean strict) throws Exception {
        return this.read(value, NodeBuilder.read(source2), strict);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T read(T value, InputNode node, boolean strict) throws Exception {
        Session session = this.manager.open(strict);
        try {
            T t2 = this.read(value, node, session);
            return t2;
        }
        finally {
            this.manager.close();
        }
    }

    private <T> T read(T value, InputNode node, Session session) throws Exception {
        return this.read(value, node, (Context)new Source(this.strategy, this.support, session));
    }

    private <T> T read(T value, InputNode node, Context context) throws Exception {
        return (T)new Traverser(context).read(node, value);
    }

    @Override
    public boolean validate(Class type, String source2) throws Exception {
        return this.validate(type, source2, true);
    }

    @Override
    public boolean validate(Class type, File source2) throws Exception {
        return this.validate(type, source2, true);
    }

    @Override
    public boolean validate(Class type, InputStream source2) throws Exception {
        return this.validate(type, source2, true);
    }

    @Override
    public boolean validate(Class type, Reader source2) throws Exception {
        return this.validate(type, source2, true);
    }

    @Override
    public boolean validate(Class type, InputNode source2) throws Exception {
        return this.validate(type, source2, true);
    }

    @Override
    public boolean validate(Class type, String source2, boolean strict) throws Exception {
        return this.validate(type, new StringReader(source2), strict);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean validate(Class type, File source2, boolean strict) throws Exception {
        try (FileInputStream file = new FileInputStream(source2);){
            boolean bl = this.validate(type, file, strict);
            return bl;
        }
    }

    @Override
    public boolean validate(Class type, InputStream source2, boolean strict) throws Exception {
        return this.validate(type, NodeBuilder.read(source2), strict);
    }

    @Override
    public boolean validate(Class type, Reader source2, boolean strict) throws Exception {
        return this.validate(type, NodeBuilder.read(source2), strict);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean validate(Class type, InputNode node, boolean strict) throws Exception {
        Session session = this.manager.open(strict);
        try {
            boolean bl = this.validate(type, node, session);
            return bl;
        }
        finally {
            this.manager.close();
        }
    }

    private boolean validate(Class type, InputNode node, Session session) throws Exception {
        return this.validate(type, node, new Source(this.strategy, this.support, session));
    }

    private boolean validate(Class type, InputNode node, Context context) throws Exception {
        return new Traverser(context).validate(node, type);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(Object source2, OutputNode root) throws Exception {
        Session session = this.manager.open();
        try {
            this.write(source2, root, session);
        }
        finally {
            this.manager.close();
        }
    }

    private void write(Object source2, OutputNode root, Session session) throws Exception {
        this.write(source2, root, new Source(this.strategy, this.support, session));
    }

    private void write(Object source2, OutputNode node, Context context) throws Exception {
        new Traverser(context).write(node, source2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(Object source2, File out) throws Exception {
        try (FileOutputStream file = new FileOutputStream(out);){
            this.write(source2, file);
        }
    }

    @Override
    public void write(Object source2, OutputStream out) throws Exception {
        this.write(source2, out, "utf-8");
    }

    public void write(Object source2, OutputStream out, String charset) throws Exception {
        this.write(source2, new OutputStreamWriter(out, charset));
    }

    @Override
    public void write(Object source2, Writer out) throws Exception {
        this.write(source2, NodeBuilder.write(out, this.format));
    }
}

