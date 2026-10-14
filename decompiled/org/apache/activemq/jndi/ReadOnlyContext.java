/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.jndi;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.LinkRef;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.naming.OperationNotSupportedException;
import javax.naming.Reference;
import javax.naming.spi.NamingManager;
import org.apache.activemq.jndi.NameParserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadOnlyContext
implements Context,
Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(ReadOnlyContext.class);
    public static final String SEPARATOR = "/";
    protected static final NameParser NAME_PARSER = new NameParserImpl();
    private static final long serialVersionUID = -5754338187296859149L;
    protected final Hashtable<String, Object> environment;
    protected final Map<String, Object> bindings;
    protected final Map<String, Object> treeBindings;
    private boolean frozen;
    private String nameInNamespace = "";

    public ReadOnlyContext() {
        this.environment = new Hashtable();
        this.bindings = new HashMap<String, Object>();
        this.treeBindings = new HashMap<String, Object>();
    }

    public ReadOnlyContext(Hashtable env) {
        this.environment = env == null ? new Hashtable() : new Hashtable(env);
        this.bindings = Collections.EMPTY_MAP;
        this.treeBindings = Collections.EMPTY_MAP;
    }

    public ReadOnlyContext(Hashtable environment, Map<String, Object> bindings) {
        this.environment = environment == null ? new Hashtable() : new Hashtable(environment);
        this.bindings = new HashMap<String, Object>();
        this.treeBindings = new HashMap<String, Object>();
        if (bindings != null) {
            for (Map.Entry<String, Object> binding : bindings.entrySet()) {
                try {
                    this.internalBind(binding.getKey(), binding.getValue());
                }
                catch (Throwable e) {
                    LOG.error("Failed to bind " + binding.getKey() + "=" + binding.getValue(), e);
                }
            }
        }
        this.frozen = true;
    }

    public ReadOnlyContext(Hashtable environment, Map bindings, String nameInNamespace) {
        this(environment, bindings);
        this.nameInNamespace = nameInNamespace;
    }

    protected ReadOnlyContext(ReadOnlyContext clone, Hashtable env) {
        this.bindings = clone.bindings;
        this.treeBindings = clone.treeBindings;
        this.environment = new Hashtable(env);
    }

    protected ReadOnlyContext(ReadOnlyContext clone, Hashtable<String, Object> env, String nameInNamespace) {
        this(clone, env);
        this.nameInNamespace = nameInNamespace;
    }

    public void freeze() {
        this.frozen = true;
    }

    boolean isFrozen() {
        return this.frozen;
    }

    protected Map<String, Object> internalBind(String name, Object value) throws NamingException {
        assert (name != null && name.length() > 0);
        assert (!this.frozen);
        HashMap<String, Object> newBindings = new HashMap<String, Object>();
        int pos = name.indexOf(47);
        if (pos == -1) {
            if (this.treeBindings.put(name, value) != null) {
                throw new NamingException("Something already bound at " + name);
            }
            this.bindings.put(name, value);
            newBindings.put(name, value);
        } else {
            String segment = name.substring(0, pos);
            assert (segment != null);
            assert (!segment.equals(""));
            Object o = this.treeBindings.get(segment);
            if (o == null) {
                o = this.newContext();
                this.treeBindings.put(segment, o);
                this.bindings.put(segment, o);
                newBindings.put(segment, o);
            } else if (!(o instanceof ReadOnlyContext)) {
                throw new NamingException("Something already bound where a subcontext should go");
            }
            ReadOnlyContext readOnlyContext = (ReadOnlyContext)o;
            String remainder = name.substring(pos + 1);
            Map<String, Object> subBindings = readOnlyContext.internalBind(remainder, value);
            for (Map.Entry<String, Object> entry : subBindings.entrySet()) {
                String subName = segment + SEPARATOR + entry.getKey();
                Object bound2 = entry.getValue();
                this.treeBindings.put(subName, bound2);
                newBindings.put(subName, bound2);
            }
        }
        return newBindings;
    }

    protected ReadOnlyContext newContext() {
        return new ReadOnlyContext();
    }

    @Override
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return this.environment.put(propName, propVal);
    }

    public Hashtable<String, Object> getEnvironment() throws NamingException {
        return (Hashtable)this.environment.clone();
    }

    @Override
    public Object removeFromEnvironment(String propName) throws NamingException {
        return this.environment.remove(propName);
    }

    @Override
    public Object lookup(String name) throws NamingException {
        if (name.length() == 0) {
            return this;
        }
        Object result = this.treeBindings.get(name);
        if (result == null) {
            result = this.bindings.get(name);
        }
        if (result == null) {
            int pos = name.indexOf(58);
            if (pos > 0) {
                String scheme2 = name.substring(0, pos);
                Context ctx = NamingManager.getURLContext(scheme2, this.environment);
                if (ctx == null) {
                    throw new NamingException("scheme " + scheme2 + " not recognized");
                }
                return ctx.lookup(name);
            }
            CompositeName path = new CompositeName(name);
            if (path.size() == 0) {
                return this;
            }
            String first = path.get(0);
            Object obj = this.bindings.get(first);
            if (obj == null) {
                throw new NameNotFoundException(name);
            }
            if (obj instanceof Context && path.size() > 1) {
                Context subContext = (Context)obj;
                obj = subContext.lookup(path.getSuffix(1));
            }
            return obj;
        }
        if (result instanceof LinkRef) {
            LinkRef ref = (LinkRef)result;
            result = this.lookup(ref.getLinkName());
        }
        if (result instanceof Reference) {
            try {
                result = NamingManager.getObjectInstance(result, null, null, this.environment);
            }
            catch (NamingException e) {
                throw e;
            }
            catch (Exception e) {
                throw (NamingException)new NamingException("could not look up : " + name).initCause(e);
            }
        }
        if (result instanceof ReadOnlyContext) {
            String prefix = this.getNameInNamespace();
            if (prefix.length() > 0) {
                prefix = prefix + SEPARATOR;
            }
            result = new ReadOnlyContext((ReadOnlyContext)result, this.environment, prefix + name);
        }
        return result;
    }

    @Override
    public Object lookup(Name name) throws NamingException {
        return this.lookup(name.toString());
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        return this.lookup(name);
    }

    @Override
    public Name composeName(Name name, Name prefix) throws NamingException {
        Name result = (Name)prefix.clone();
        result.addAll(name);
        return result;
    }

    @Override
    public String composeName(String name, String prefix) throws NamingException {
        CompositeName result = new CompositeName(prefix);
        result.addAll(new CompositeName(name));
        return result.toString();
    }

    public NamingEnumeration list(String name) throws NamingException {
        Object o = this.lookup(name);
        if (o == this) {
            return new ListEnumeration();
        }
        if (o instanceof Context) {
            return ((Context)o).list("");
        }
        throw new NotContextException();
    }

    public NamingEnumeration listBindings(String name) throws NamingException {
        Object o = this.lookup(name);
        if (o == this) {
            return new ListBindingEnumeration();
        }
        if (o instanceof Context) {
            return ((Context)o).listBindings("");
        }
        throw new NotContextException();
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return this.lookupLink(name.toString());
    }

    public NamingEnumeration list(Name name) throws NamingException {
        return this.list(name.toString());
    }

    public NamingEnumeration listBindings(Name name) throws NamingException {
        return this.listBindings(name.toString());
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void bind(String name, Object obj) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void close() throws NamingException {
    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void destroySubcontext(String name) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return this.nameInNamespace;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return NAME_PARSER;
    }

    @Override
    public NameParser getNameParser(String name) throws NamingException {
        return NAME_PARSER;
    }

    @Override
    public void rebind(Name name, Object obj) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void rebind(String name, Object obj) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void rename(Name oldName, Name newName) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void rename(String oldName, String newName) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void unbind(Name name) throws NamingException {
        throw new OperationNotSupportedException();
    }

    @Override
    public void unbind(String name) throws NamingException {
        throw new OperationNotSupportedException();
    }

    private class ListBindingEnumeration
    extends LocalNamingEnumeration {
        ListBindingEnumeration() {
        }

        public Object next() throws NamingException {
            return this.nextElement();
        }

        @Override
        public Object nextElement() {
            Map.Entry entry = this.getNext();
            return new Binding((String)entry.getKey(), entry.getValue());
        }
    }

    private class ListEnumeration
    extends LocalNamingEnumeration {
        ListEnumeration() {
        }

        public Object next() throws NamingException {
            return this.nextElement();
        }

        @Override
        public Object nextElement() {
            Map.Entry entry = this.getNext();
            return new NameClassPair((String)entry.getKey(), entry.getValue().getClass().getName());
        }
    }

    private abstract class LocalNamingEnumeration
    implements NamingEnumeration {
        private final Iterator i;

        private LocalNamingEnumeration() {
            this.i = ReadOnlyContext.this.bindings.entrySet().iterator();
        }

        @Override
        public boolean hasMore() throws NamingException {
            return this.i.hasNext();
        }

        @Override
        public boolean hasMoreElements() {
            return this.i.hasNext();
        }

        protected Map.Entry getNext() {
            return (Map.Entry)this.i.next();
        }

        @Override
        public void close() throws NamingException {
        }
    }
}

