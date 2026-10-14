/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.TopologicalSort;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.component.DumpableCollection;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configurations
extends AbstractList<Configuration>
implements Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(Configurations.class);
    private static final AutoLock __lock = new AutoLock();
    private static final List<Configuration> __known = new ArrayList<Configuration>();
    private static final List<Configuration> __unavailable = new ArrayList<Configuration>();
    private static final Set<String> __knownByClassName = new HashSet<String>();
    protected List<Configuration> _configurations = new ArrayList<Configuration>();

    public static List<Configuration> getKnown() {
        try (AutoLock l = __lock.lock();){
            if (__known.isEmpty()) {
                TypeUtil.serviceProviderStream(ServiceLoader.load(Configuration.class)).forEach(provider -> {
                    try {
                        Configuration configuration = (Configuration)provider.get();
                        if (!configuration.isAvailable()) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Configuration unavailable: {}", (Object)configuration);
                            }
                            __unavailable.add(configuration);
                            return;
                        }
                        __known.add(configuration);
                        __knownByClassName.add(configuration.getClass().getName());
                    }
                    catch (Throwable e) {
                        LOG.warn("Unable to get known Configuration", e);
                    }
                });
                Configurations.sort(__known);
                if (LOG.isDebugEnabled()) {
                    for (Configuration c : __known) {
                        LOG.debug("known {}", (Object)c);
                    }
                    LOG.debug("Known Configurations {}", (Object)__knownByClassName);
                }
            }
            List<Configuration> list = __known;
            return list;
        }
    }

    public static void setKnown(String ... classes) {
        try (AutoLock l = __lock.lock();){
            if (!__known.isEmpty()) {
                throw new IllegalStateException("Known configuration classes already set");
            }
            for (String c : classes) {
                try {
                    Class clazz = Loader.loadClass(c);
                    Configuration configuration = (Configuration)clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
                    if (!configuration.isAvailable()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.warn("Configuration unavailable: {}", (Object)configuration);
                        }
                        __unavailable.add(configuration);
                        continue;
                    }
                    __known.add((Configuration)clazz.getConstructor(new Class[0]).newInstance(new Object[0]));
                    __knownByClassName.add(c);
                }
                catch (Exception e) {
                    LOG.warn("Problem loading known class", e);
                }
            }
            Configurations.sort(__known);
            if (LOG.isDebugEnabled()) {
                for (Configuration c : __known) {
                    LOG.debug("known {}", (Object)c);
                }
                LOG.debug("Known Configurations {}", (Object)__knownByClassName);
            }
        }
    }

    static void cleanKnown() {
        try (AutoLock l = __lock.lock();){
            __known.clear();
            __unavailable.clear();
        }
    }

    public static Configurations setServerDefault(Server server) {
        Configurations configurations = server.getBean(Configurations.class);
        if (configurations != null) {
            return configurations;
        }
        configurations = Configurations.getServerDefault(server);
        server.addBean(configurations);
        server.setAttribute("org.eclipse.jetty.webapp.configuration", null);
        return configurations;
    }

    public static Configurations getServerDefault(Server server) {
        Configurations configurations = null;
        if (server != null) {
            configurations = server.getBean(Configurations.class);
            if (configurations != null) {
                configurations = new Configurations(configurations);
            } else {
                Object attr = server.getAttribute("org.eclipse.jetty.webapp.configuration");
                LOG.debug("{} attr({})= {}", server, "org.eclipse.jetty.webapp.configuration", attr);
                if (attr instanceof Configurations) {
                    configurations = new Configurations((Configurations)attr);
                } else if (attr instanceof String[]) {
                    configurations = new Configurations((String[])attr);
                }
            }
        }
        if (configurations == null) {
            configurations = new Configurations((String[])Configurations.getKnown().stream().filter(c -> c.isEnabledByDefault()).map(c -> c.getClass().getName()).toArray(String[]::new));
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("default configurations for {}: {}", (Object)server, (Object)configurations);
        }
        return configurations;
    }

    public Configurations() {
    }

    protected static Configuration newConfiguration(String classname) {
        if (LOG.isDebugEnabled() && !__knownByClassName.contains(classname)) {
            LOG.warn("Unknown configuration {}. Not declared for ServiceLoader!", (Object)classname);
        }
        try {
            Class clazz = Loader.loadClass(classname);
            return (Configuration)clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Configurations(String ... classes) {
        this.add(classes);
    }

    public Configurations(List<String> classes) {
        this.add(classes.toArray(new String[classes.size()]));
    }

    public Configurations(Configurations classlist) {
        this((String[])classlist._configurations.stream().map(c -> c.getClass().getName()).toArray(String[]::new));
    }

    @Override
    public boolean add(Configuration configuration) {
        return this.addConfiguration(configuration);
    }

    public void add(Configuration ... configurations) {
        for (Configuration configuration : configurations) {
            this.addConfiguration(configuration);
        }
    }

    public void add(String ... configClass) {
        for (String name : configClass) {
            this.addConfiguration(Configurations.newConfiguration(name));
        }
    }

    public <T> T get(Class<? extends T> configClass) {
        for (Configuration configuration : this._configurations) {
            if (!configClass.isAssignableFrom(configuration.getClass())) continue;
            return (T)configuration;
        }
        return null;
    }

    public <T> List<T> getConfigurations(Class<? extends T> configClass) {
        ArrayList<Configuration> list = new ArrayList<Configuration>();
        for (Configuration configuration : this._configurations) {
            if (!configClass.isAssignableFrom(configuration.getClass())) continue;
            list.add(configuration);
        }
        return list;
    }

    @Override
    public void clear() {
        this._configurations.clear();
    }

    public void set(Configuration ... configurations) {
        this.clear();
        this.add(configurations);
    }

    public void set(String ... configClass) {
        this.clear();
        this.add(configClass);
    }

    public void remove(Configuration ... configurations) {
        List names = Arrays.asList(configurations).stream().map(c -> c.getClass().getName()).collect(Collectors.toList());
        ListIterator<Configuration> i = this._configurations.listIterator();
        while (i.hasNext()) {
            Configuration configuration = i.next();
            if (!names.contains(configuration.getClass().getName())) continue;
            i.remove();
        }
    }

    public void remove(Class<? extends Configuration> ... configClass) {
        List names = Arrays.asList(configClass).stream().map(c -> c.getName()).collect(Collectors.toList());
        ListIterator<Configuration> i = this._configurations.listIterator();
        while (i.hasNext()) {
            Configuration configuration = i.next();
            if (!names.contains(configuration.getClass().getName())) continue;
            i.remove();
        }
    }

    public void remove(String ... configClass) {
        List<String> names = Arrays.asList(configClass);
        ListIterator<Configuration> i = this._configurations.listIterator();
        while (i.hasNext()) {
            Configuration configuration = i.next();
            if (!names.contains(configuration.getClass().getName())) continue;
            i.remove();
        }
    }

    @Override
    public int size() {
        return this._configurations.size();
    }

    public String[] toArray() {
        return (String[])this._configurations.stream().map(c -> c.getClass().getName()).toArray(String[]::new);
    }

    public void sort() {
        Configurations.sort(this._configurations);
        if (LOG.isDebugEnabled()) {
            for (Configuration c : this._configurations) {
                LOG.debug("sorted {}", (Object)c);
            }
        }
    }

    public static void sort(List<Configuration> configurations) {
        HashMap<String, Configuration> byName = new HashMap<String, Configuration>();
        HashMap<String, List> replacedBy = new HashMap<String, List>();
        TopologicalSort<Configuration> sort = new TopologicalSort<Configuration>();
        for (Configuration c : configurations) {
            byName.put(c.getClass().getName(), c);
            if (c.replaces() == null) continue;
            replacedBy.computeIfAbsent(c.replaces().getName(), key -> new ArrayList()).add(c);
        }
        for (Configuration c : configurations) {
            for (String b : c.getDependencies()) {
                Configuration before = (Configuration)byName.get(b);
                if (before != null) {
                    sort.addBeforeAfter(before, c);
                }
                if (!replacedBy.containsKey(b)) continue;
                ((List)replacedBy.get(b)).forEach(bc -> sort.addBeforeAfter((Configuration)bc, c));
            }
            for (String a : c.getDependents()) {
                Configuration after = (Configuration)byName.get(a);
                if (after != null) {
                    sort.addBeforeAfter(c, after);
                }
                if (!replacedBy.containsKey(a)) continue;
                ((List)replacedBy.get(a)).forEach(ac -> sort.addBeforeAfter(c, (Configuration)ac));
            }
        }
        sort.sort(configurations);
    }

    public List<Configuration> getConfigurations() {
        return Collections.unmodifiableList(this._configurations);
    }

    @Override
    public Configuration get(int index) {
        return this._configurations.get(index);
    }

    @Override
    public Iterator<Configuration> iterator() {
        return this.getConfigurations().iterator();
    }

    private boolean addConfiguration(Configuration configuration) {
        Configuration c;
        ListIterator<Configuration> i;
        Class<? extends Configuration> replaces;
        String name = configuration.getClass().getName();
        if (LOG.isDebugEnabled() && !__knownByClassName.contains(name)) {
            LOG.warn("Unknown configuration {}. Not declared for ServiceLoader!", (Object)name);
        }
        if ((replaces = configuration.replaces()) != null) {
            i = this._configurations.listIterator();
            while (i.hasNext()) {
                c = i.next();
                if (!c.getClass().getName().equals(replaces.getName()) && (c.replaces() == null || !c.replaces().getName().equals(replaces.getName()))) continue;
                i.remove();
                break;
            }
        }
        i = this._configurations.listIterator();
        while (i.hasNext()) {
            c = i.next();
            Class<? extends Configuration> r = c.replaces();
            if (r != null && r.getName().equals(configuration.getClass().getName())) {
                return false;
            }
            if (!c.getClass().getName().equals(configuration.getClass().getName())) continue;
            return false;
        }
        return this._configurations.add(configuration);
    }

    @Override
    public String toString() {
        return String.format("%s@%x", this.getClass(), this.hashCode());
    }

    public void preConfigure(WebAppContext webapp) throws Exception {
        for (int i = 0; i < this._configurations.size(); ++i) {
            Configuration configuration = this._configurations.get(i);
            LOG.debug("preConfigure with {}", (Object)configuration);
            configuration.preConfigure(webapp);
            if (this._configurations.get(i) == configuration) continue;
            throw new ConcurrentModificationException("Cannot change prior configuration");
        }
    }

    public boolean configure(WebAppContext webapp) throws Exception {
        for (Configuration configuration : this._configurations) {
            LOG.debug("configure {}", (Object)configuration);
            configuration.configure(webapp);
            if (!configuration.abort(webapp)) continue;
            return false;
        }
        return true;
    }

    public void postConfigure(WebAppContext webapp) throws Exception {
        for (Configuration configuration : this._configurations) {
            LOG.debug("postConfigure {}", (Object)configuration);
            configuration.postConfigure(webapp);
        }
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObjects(out, indent, this, new DumpableCollection("Known", Configurations.getKnown()), new DumpableCollection("Unavailable", __unavailable));
    }
}

