/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.nonstop.NonStopException
 *  org.terracotta.toolkit.rejoin.RejoinException
 */
package org.terracotta.modules.ehcache.store;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.SearchException;
import net.sf.ehcache.store.ElementValueComparator;
import net.sf.ehcache.store.Policy;
import net.sf.ehcache.store.StoreListener;
import net.sf.ehcache.store.StoreQuery;
import net.sf.ehcache.store.TerracottaStore;
import net.sf.ehcache.terracotta.TerracottaNotRunningException;
import net.sf.ehcache.writer.CacheWriterManager;
import net.sf.ehcache.writer.writebehind.WriteBehind;
import org.terracotta.context.annotations.ContextChild;
import org.terracotta.modules.ehcache.store.ClusteredStoreExceptionHandler;
import org.terracotta.toolkit.nonstop.NonStopException;
import org.terracotta.toolkit.rejoin.RejoinException;

public class ClusteredSafeStore
implements TerracottaStore {
    private static final ClusteredStoreExceptionHandler EXCEPTION_HANDLER = new ClusteredSafeStoreExceptionHandler();
    @ContextChild
    private final TerracottaStore delegateClusteredStore;

    public ClusteredSafeStore(TerracottaStore delegateClusteredStore) {
        this.delegateClusteredStore = delegateClusteredStore;
    }

    public static void main(String[] args2) {
        Class[] classes;
        PrintStream out = System.out;
        for (Class c : classes = new Class[]{TerracottaStore.class}) {
            for (Method m4 : c.getMethods()) {
                int i;
                out.println("/**");
                out.println("* {@inheritDoc}");
                out.println("*/");
                out.print("public " + m4.getReturnType().getSimpleName() + " " + m4.getName() + "(");
                Class<?>[] params = m4.getParameterTypes();
                for (int i2 = 0; i2 < params.length; ++i2) {
                    out.print(params[i2].getSimpleName() + " arg" + i2);
                    if (i2 >= params.length - 1) continue;
                    out.print(", ");
                }
                out.print(")");
                ArrayList exceptions = new ArrayList();
                exceptions.add(NonStopException.class);
                exceptions.add(RejoinException.class);
                for (Class<?> e : m4.getExceptionTypes()) {
                    exceptions.add(e);
                }
                if (exceptions.size() > 0) {
                    out.print(" throws ");
                }
                for (i = 0; i < exceptions.size(); ++i) {
                    out.print(((Class)exceptions.get(i)).getSimpleName());
                    if (i >= exceptions.size() - 1) continue;
                    out.print(", ");
                }
                out.println(" {");
                out.println("    // THIS IS GENERATED CODE -- DO NOT HAND MODIFY!");
                out.println("    try {");
                out.print("        ");
                if (m4.getReturnType() != Void.TYPE) {
                    out.print("return ");
                }
                out.print("this.delegateClusteredStore." + m4.getName() + "(");
                for (i = 0; i < params.length; ++i) {
                    out.print("arg" + i);
                    if (i >= params.length - 1) continue;
                    out.print(", ");
                }
                out.println(");");
                if (exceptions.size() > 0) {
                    for (i = 0; i < exceptions.size(); ++i) {
                        Class e = (Class)exceptions.get(i);
                        out.println("    } catch(" + e.getSimpleName() + " e) {");
                        out.println("      throw e;");
                        if (i >= exceptions.size() - 1) continue;
                    }
                }
                out.println("    } catch (Throwable t) {");
                out.println("        EXCEPTION_HANDLER.handleException(t);");
                out.println("        throw new CacheException(\"Uncaught exception in " + m4.getName() + "() - \" + t.getMessage(), t);");
                out.println("    }");
                out.println("}");
                out.println("");
            }
        }
    }

    @Override
    public WriteBehind createWriteBehind() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element unsafeGet(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.unsafeGet(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in unsafeGet() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void quickClear() throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.quickClear();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in quickClear() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public int quickSize() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.quickSize();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in quickSize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Set getLocalKeys() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getLocalKeys();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getLocalKeys() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public CacheConfiguration.TransactionalMode getTransactionalMode() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getTransactionalMode();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getTransactionalMode() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element get(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.get(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in get() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean put(Element arg0) throws NonStopException, RejoinException, CacheException {
        try {
            return this.delegateClusteredStore.put(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (CacheException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in put() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element replace(Element arg0) throws NonStopException, RejoinException, NullPointerException {
        try {
            return this.delegateClusteredStore.replace(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (NullPointerException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in replace() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean replace(Element arg0, Element arg1, ElementValueComparator arg2) throws NonStopException, RejoinException, NullPointerException, IllegalArgumentException {
        try {
            return this.delegateClusteredStore.replace(arg0, arg1, arg2);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (NullPointerException e) {
            throw e;
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in replace() - " + t2.getMessage(), t2);
        }
    }

    public void putAll(Collection arg0) throws NonStopException, RejoinException, CacheException {
        try {
            this.delegateClusteredStore.putAll(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (CacheException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in putAll() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element remove(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.remove(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in remove() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void flush() throws NonStopException, RejoinException, IOException {
        try {
            this.delegateClusteredStore.flush();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (IOException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in flush() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean containsKey(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.containsKey(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in containsKey() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public int getSize() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getSize();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getSize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void removeAll() throws NonStopException, RejoinException, CacheException {
        try {
            this.delegateClusteredStore.removeAll();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (CacheException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in removeAll() - " + t2.getMessage(), t2);
        }
    }

    public void removeAll(Collection arg0) throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.removeAll(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in removeAll() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element removeElement(Element arg0, ElementValueComparator arg1) throws NonStopException, RejoinException, NullPointerException {
        try {
            return this.delegateClusteredStore.removeElement(arg0, arg1);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (NullPointerException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in removeElement() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element putIfAbsent(Element arg0) throws NonStopException, RejoinException, NullPointerException {
        try {
            return this.delegateClusteredStore.putIfAbsent(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (NullPointerException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in putIfAbsent() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void addStoreListener(StoreListener arg0) throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.addStoreListener(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in addStoreListener() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void removeStoreListener(StoreListener arg0) throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.removeStoreListener(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in removeStoreListener() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean putWithWriter(Element arg0, CacheWriterManager arg1) throws NonStopException, RejoinException, CacheException {
        try {
            return this.delegateClusteredStore.putWithWriter(arg0, arg1);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (CacheException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in putWithWriter() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element getQuiet(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getQuiet(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getQuiet() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Element removeWithWriter(Object arg0, CacheWriterManager arg1) throws NonStopException, RejoinException, CacheException {
        try {
            return this.delegateClusteredStore.removeWithWriter(arg0, arg1);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (CacheException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in removeWithWriter() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public int getInMemorySize() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getInMemorySize();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getInMemorySize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public int getOffHeapSize() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getOffHeapSize();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getOffHeapSize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public int getOnDiskSize() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getOnDiskSize();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getOnDiskSize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public int getTerracottaClusteredSize() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getTerracottaClusteredSize();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getTerracottaClusteredSize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public long getInMemorySizeInBytes() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getInMemorySizeInBytes();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getInMemorySizeInBytes() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public long getOffHeapSizeInBytes() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getOffHeapSizeInBytes();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getOffHeapSizeInBytes() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public long getOnDiskSizeInBytes() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getOnDiskSizeInBytes();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getOnDiskSizeInBytes() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean hasAbortedSizeOf() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.hasAbortedSizeOf();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in hasAbortedSizeOf() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean containsKeyOnDisk(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.containsKeyOnDisk(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in containsKeyOnDisk() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean containsKeyOffHeap(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.containsKeyOffHeap(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in containsKeyOffHeap() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean containsKeyInMemory(Object arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.containsKeyInMemory(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in containsKeyInMemory() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void expireElements() throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.expireElements();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in expireElements() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean bufferFull() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.bufferFull();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in bufferFull() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Policy getInMemoryEvictionPolicy() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getInMemoryEvictionPolicy();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getInMemoryEvictionPolicy() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void setInMemoryEvictionPolicy(Policy arg0) throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.setInMemoryEvictionPolicy(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in setInMemoryEvictionPolicy() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Object getInternalContext() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getInternalContext();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getInternalContext() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean isCacheCoherent() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.isCacheCoherent();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in isCacheCoherent() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean isClusterCoherent() throws NonStopException, RejoinException, TerracottaNotRunningException {
        try {
            return this.delegateClusteredStore.isClusterCoherent();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (TerracottaNotRunningException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in isClusterCoherent() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public boolean isNodeCoherent() throws NonStopException, RejoinException, TerracottaNotRunningException {
        try {
            return this.delegateClusteredStore.isNodeCoherent();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (TerracottaNotRunningException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in isNodeCoherent() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void setNodeCoherent(boolean arg0) throws NonStopException, RejoinException, UnsupportedOperationException, TerracottaNotRunningException {
        try {
            this.delegateClusteredStore.setNodeCoherent(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (UnsupportedOperationException e) {
            throw e;
        }
        catch (TerracottaNotRunningException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in setNodeCoherent() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void waitUntilClusterCoherent() throws NonStopException, RejoinException, UnsupportedOperationException, TerracottaNotRunningException, InterruptedException {
        try {
            this.delegateClusteredStore.waitUntilClusterCoherent();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (UnsupportedOperationException e) {
            throw e;
        }
        catch (TerracottaNotRunningException e) {
            throw e;
        }
        catch (InterruptedException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in waitUntilClusterCoherent() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Object getMBean() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getMBean();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getMBean() - " + t2.getMessage(), t2);
        }
    }

    public void setAttributeExtractors(Map arg0) throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.setAttributeExtractors(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in setAttributeExtractors() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Results executeQuery(StoreQuery arg0) throws NonStopException, RejoinException, SearchException {
        try {
            return this.delegateClusteredStore.executeQuery(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (SearchException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in executeQuery() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Set<Attribute> getSearchAttributes() {
        try {
            return this.delegateClusteredStore.getSearchAttributes();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getSearchAttributes() - " + t2.getMessage(), t2);
        }
    }

    public Attribute getSearchAttribute(String arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getSearchAttribute(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getSearchAttribute() - " + t2.getMessage(), t2);
        }
    }

    public Map getAllQuiet(Collection arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getAllQuiet(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getAllQuiet() - " + t2.getMessage(), t2);
        }
    }

    public Map getAll(Collection arg0) throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getAll(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getAll() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void dispose() throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.dispose();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in dispose() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public List getKeys() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getKeys();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getKeys() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public Status getStatus() throws NonStopException, RejoinException {
        try {
            return this.delegateClusteredStore.getStatus();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in getStatus() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void recalculateSize(Object arg0) throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.recalculateSize(arg0);
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in recalculateSize() - " + t2.getMessage(), t2);
        }
    }

    @Override
    public void notifyCacheEventListenersChanged() throws NonStopException, RejoinException {
        try {
            this.delegateClusteredStore.notifyCacheEventListenersChanged();
        }
        catch (NonStopException e) {
            throw e;
        }
        catch (RejoinException e) {
            throw e;
        }
        catch (Throwable t2) {
            EXCEPTION_HANDLER.handleException(t2);
            throw new CacheException("Uncaught exception in notifyCacheEventListenersChanged() - " + t2.getMessage(), t2);
        }
    }

    private static class ClusteredSafeStoreExceptionHandler
    implements ClusteredStoreExceptionHandler {
        private ClusteredSafeStoreExceptionHandler() {
        }

        @Override
        public void handleException(Throwable t2) {
            if (t2.getClass().getSimpleName().equals("TCNotRunningException")) {
                throw new TerracottaNotRunningException("Clustered Cache is probably shutdown or Terracotta backend is down.", t2);
            }
        }
    }
}

