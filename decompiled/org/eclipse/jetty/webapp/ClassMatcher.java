/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.eclipse.jetty.util.IncludeExcludeSet;
import org.eclipse.jetty.util.Index;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;

public class ClassMatcher
extends AbstractSet<String> {
    Map<String, Entry> _entries = new HashMap<String, Entry>();
    IncludeExcludeSet<Entry, String> _patterns = new IncludeExcludeSet(ByPackageOrName.class);
    IncludeExcludeSet<Entry, URI> _locations = new IncludeExcludeSet(ByLocationOrModule.class);

    public ClassMatcher() {
    }

    public ClassMatcher(ClassMatcher patterns) {
        if (patterns != null) {
            this.setAll(patterns.getPatterns());
        }
    }

    public ClassMatcher(String ... patterns) {
        if (patterns != null && patterns.length > 0) {
            this.setAll(patterns);
        }
    }

    public ClassMatcher(String pattern) {
        this.add(pattern);
    }

    public boolean include(String name) {
        if (name == null) {
            return false;
        }
        return this.add(this.newEntry(name, true));
    }

    public boolean include(String ... name) {
        boolean added = false;
        for (String n : name) {
            if (n == null) continue;
            added = this.add(this.newEntry(n, true)) || added;
        }
        return added;
    }

    public boolean exclude(String name) {
        if (name == null) {
            return false;
        }
        return this.add(this.newEntry(name, false));
    }

    public boolean exclude(String ... name) {
        boolean added = false;
        for (String n : name) {
            if (n == null) continue;
            added = this.add(this.newEntry(n, false)) || added;
        }
        return added;
    }

    @Override
    public boolean add(String pattern) {
        if (pattern == null) {
            return false;
        }
        return this.add(this.newEntry(pattern));
    }

    @Override
    public boolean add(String ... pattern) {
        boolean added = false;
        for (String p : pattern) {
            if (p == null) continue;
            added = this.add(this.newEntry(p)) || added;
        }
        return added;
    }

    @Override
    protected boolean add(Entry entry) {
        if (this._entries.containsKey(entry.getPattern())) {
            return false;
        }
        this._entries.put(entry.getPattern(), entry);
        if (entry instanceof LocationEntry || entry instanceof ModuleEntry) {
            if (entry.isInclusive()) {
                this._locations.include(entry);
            } else {
                this._locations.exclude(entry);
            }
        } else if (entry.isInclusive()) {
            this._patterns.include(entry);
        } else {
            this._patterns.exclude(entry);
        }
        return true;
    }

    protected Entry newEntry(String pattern) {
        if (pattern.startsWith("-")) {
            return this.newEntry(pattern.substring(1), false);
        }
        return this.newEntry(pattern, true);
    }

    protected Entry newEntry(String name, boolean inclusive) {
        if (name.startsWith("-")) {
            throw new IllegalStateException(name);
        }
        if (name.startsWith("file:")) {
            return new LocationEntry(name, inclusive);
        }
        if (name.startsWith("jrt:")) {
            return new ModuleEntry(name, inclusive);
        }
        if (name.endsWith(".")) {
            return new PackageEntry(name, inclusive);
        }
        return new ClassEntry(name, inclusive);
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof String)) {
            return false;
        }
        String pattern = (String)o;
        Entry entry = this._entries.remove(pattern);
        if (entry == null) {
            return false;
        }
        ArrayList<Entry> saved = new ArrayList<Entry>(this._entries.values());
        this.clear();
        for (Entry e : saved) {
            this.add(e);
        }
        return true;
    }

    @Override
    public void clear() {
        this._entries.clear();
        this._patterns.clear();
        this._locations.clear();
    }

    @Override
    public Iterator<String> iterator() {
        return this._entries.keySet().iterator();
    }

    @Override
    public int size() {
        return this._entries.size();
    }

    private void setAll(String[] classes) {
        this._entries.clear();
        this.addAll(classes);
    }

    private void addAll(String[] classes) {
        if (classes != null) {
            this.addAll(Arrays.asList(classes));
        }
    }

    public String[] getPatterns() {
        return this.toArray(new String[0]);
    }

    public String[] getInclusions() {
        return (String[])this._entries.values().stream().filter(Entry::isInclusive).map(Entry::getName).toArray(String[]::new);
    }

    public String[] getExclusions() {
        return (String[])this._entries.values().stream().filter(e -> !e.isInclusive()).map(Entry::getName).toArray(String[]::new);
    }

    public boolean match(String name) {
        return this._patterns.test(name);
    }

    public boolean match(Class<?> clazz) {
        try {
            return ClassMatcher.combine(this._patterns, clazz.getName(), this._locations, () -> TypeUtil.getLocationOfClass(clazz));
        }
        catch (Exception exception) {
            return false;
        }
    }

    public boolean match(String name, URL url2) {
        if (name.endsWith(".class")) {
            name = name.substring(0, name.length() - 6);
        }
        name = StringUtil.replace(name, '/', '.');
        return ClassMatcher.combine(this._patterns, name, this._locations, () -> {
            try {
                return URIUtil.getJarSource(url2.toURI());
            }
            catch (URISyntaxException ignored) {
                return null;
            }
        });
    }

    static boolean combine(IncludeExcludeSet<Entry, String> names, String name, IncludeExcludeSet<Entry, URI> locations, Supplier<URI> location) {
        Boolean byLocation;
        Boolean byName = names.isIncludedAndNotExcluded(name);
        if (Boolean.FALSE == byName) {
            return false;
        }
        URI uri = location.get();
        Boolean bl = byLocation = uri == null ? null : locations.isIncludedAndNotExcluded(uri);
        if (Boolean.FALSE == byLocation || locations.hasExcludes() && uri == null) {
            return false;
        }
        if (names.hasIncludes() || locations.hasIncludes()) {
            return byName == Boolean.TRUE || byLocation == Boolean.TRUE;
        }
        return true;
    }

    public static class ByPackageOrName
    extends AbstractSet<Entry>
    implements Predicate<String> {
        private final ByClass _byClass = new ByClass();
        private final ByPackage _byPackage = new ByPackage();

        @Override
        public boolean test(String name) {
            return this._byPackage.test(name) || this._byClass.test(name);
        }

        @Override
        public Iterator<Entry> iterator() {
            return this._byPackage.iterator();
        }

        @Override
        public int size() {
            return this._byPackage.size();
        }

        @Override
        public boolean add(Entry entry) {
            if (entry instanceof PackageEntry) {
                return this._byPackage.add(entry);
            }
            if (entry instanceof ClassEntry) {
                boolean added = this._byPackage.add(entry);
                added = this._byClass.add(entry) || added;
                return added;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            boolean removedPackage = this._byPackage.remove(o);
            boolean removedClass = this._byClass.remove(o);
            return removedPackage || removedClass;
        }

        @Override
        public void clear() {
            this._byPackage.clear();
            this._byClass.clear();
        }
    }

    public static class ByLocationOrModule
    extends AbstractSet<Entry>
    implements Predicate<URI> {
        private final ByLocation _byLocation = new ByLocation();
        private final ByModule _byModule = new ByModule();

        @Override
        public boolean test(URI name) {
            if (name == null || !name.isAbsolute()) {
                return false;
            }
            return this._byLocation.test(name) || this._byModule.test(name);
        }

        @Override
        public Iterator<Entry> iterator() {
            HashSet<Entry> entries = new HashSet<Entry>();
            entries.addAll(this._byLocation);
            entries.addAll(this._byModule);
            return entries.iterator();
        }

        @Override
        public int size() {
            return this._byLocation.size() + this._byModule.size();
        }

        @Override
        public boolean add(Entry entry) {
            if (entry instanceof LocationEntry) {
                return this._byLocation.add(entry);
            }
            if (entry instanceof ModuleEntry) {
                return this._byModule.add(entry);
            }
            throw new IllegalArgumentException(entry.toString());
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof LocationEntry) {
                return this._byLocation.remove(o);
            }
            if (o instanceof ModuleEntry) {
                return this._byModule.remove(o);
            }
            return false;
        }

        @Override
        public void clear() {
            this._byLocation.clear();
            this._byModule.clear();
        }
    }

    public static class Entry {
        private final String _pattern;
        private final String _name;
        private final boolean _inclusive;

        protected Entry(String name, boolean inclusive) {
            this._name = name;
            this._inclusive = inclusive;
            this._pattern = inclusive ? this._name : "-" + this._name;
        }

        public String getPattern() {
            return this._pattern;
        }

        public String getName() {
            return this._name;
        }

        public String toString() {
            return this._pattern;
        }

        public int hashCode() {
            return this._pattern.hashCode();
        }

        public boolean equals(Object o) {
            return o instanceof Entry && this._pattern.equals(((Entry)o)._pattern);
        }

        public boolean isInclusive() {
            return this._inclusive;
        }
    }

    private static class LocationEntry
    extends Entry {
        private final File _file;

        protected LocationEntry(String name, boolean inclusive) {
            super(name, inclusive);
            if (!this.getName().startsWith("file:")) {
                throw new IllegalArgumentException(name);
            }
            try {
                this._file = Resource.newResource(this.getName()).getFile();
            }
            catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public File getFile() {
            return this._file;
        }
    }

    private static class ModuleEntry
    extends Entry {
        private final String _module;

        protected ModuleEntry(String name, boolean inclusive) {
            super(name, inclusive);
            if (!this.getName().startsWith("jrt:")) {
                throw new IllegalArgumentException(name);
            }
            this._module = this.getName().split("/")[1];
        }

        public String getModule() {
            return this._module;
        }
    }

    private static class PackageEntry
    extends Entry {
        protected PackageEntry(String name, boolean inclusive) {
            super(name, inclusive);
        }
    }

    private static class ClassEntry
    extends Entry {
        protected ClassEntry(String name, boolean inclusive) {
            super(name, inclusive);
        }
    }

    public static class ByModule
    extends HashSet<Entry>
    implements Predicate<URI> {
        private final Index.Mutable<Entry> _entries = new Index.Builder().caseSensitive(true).mutable().build();

        @Override
        public boolean test(URI uri) {
            if (uri == null || !uri.isAbsolute()) {
                return false;
            }
            if (!uri.getScheme().equalsIgnoreCase("jrt")) {
                return false;
            }
            String module = uri.getPath();
            int end = module.indexOf(47, 1);
            if (end < 1) {
                end = module.length();
            }
            return this._entries.get(module, 1, end - 1) != null;
        }

        @Override
        public Iterator<Entry> iterator() {
            return this._entries.keySet().stream().map(this._entries::get).iterator();
        }

        @Override
        public int size() {
            return this._entries.size();
        }

        @Override
        public boolean add(Entry entry) {
            if (!(entry instanceof ModuleEntry)) {
                throw new IllegalArgumentException(entry.toString());
            }
            String module = ((ModuleEntry)entry).getModule();
            if (this._entries.get(module) != null) {
                return false;
            }
            this._entries.put(module, entry);
            return true;
        }

        @Override
        public boolean remove(Object entry) {
            if (!(entry instanceof Entry)) {
                return false;
            }
            return this._entries.remove(((Entry)entry).getName()) != null;
        }
    }

    public static class ByLocation
    extends HashSet<Entry>
    implements Predicate<URI> {
        @Override
        public boolean test(URI uri) {
            if (uri == null || !uri.isAbsolute()) {
                return false;
            }
            if (!uri.getScheme().equals("file")) {
                return false;
            }
            Path path = Paths.get(uri);
            for (Entry entry : this) {
                if (!(entry instanceof LocationEntry)) {
                    throw new IllegalStateException();
                }
                File file = ((LocationEntry)entry).getFile();
                if (!(file.isDirectory() ? path.startsWith(file.toPath()) : path.equals(file.toPath()))) continue;
                return true;
            }
            return false;
        }
    }

    public static class ByClass
    extends HashSet<Entry>
    implements Predicate<String> {
        private final Map<String, Entry> _entries = new HashMap<String, Entry>();

        @Override
        public boolean test(String name) {
            return this._entries.containsKey(name);
        }

        @Override
        public Iterator<Entry> iterator() {
            return this._entries.values().iterator();
        }

        @Override
        public int size() {
            return this._entries.size();
        }

        @Override
        public boolean add(Entry entry) {
            if (!(entry instanceof ClassEntry)) {
                throw new IllegalArgumentException(entry.toString());
            }
            return this._entries.put(entry.getName(), entry) == null;
        }

        @Override
        public boolean remove(Object entry) {
            if (!(entry instanceof Entry)) {
                return false;
            }
            return this._entries.remove(((Entry)entry).getName()) != null;
        }
    }

    public static class ByPackage
    extends AbstractSet<Entry>
    implements Predicate<String> {
        private final Index.Mutable<Entry> _entries = new Index.Builder().caseSensitive(true).mutable().build();

        @Override
        public boolean test(String name) {
            return this._entries.getBest(name) != null;
        }

        @Override
        public Iterator<Entry> iterator() {
            return this._entries.keySet().stream().map(this._entries::get).iterator();
        }

        @Override
        public int size() {
            return this._entries.size();
        }

        @Override
        public boolean isEmpty() {
            return this._entries.isEmpty();
        }

        @Override
        public boolean add(Entry entry) {
            Object name = entry.getName();
            if (entry instanceof ClassEntry) {
                name = (String)name + "$";
            } else {
                if (!(entry instanceof PackageEntry)) {
                    throw new IllegalArgumentException(entry.toString());
                }
                if (".".equals(name)) {
                    name = "";
                }
            }
            if (this._entries.get((String)name) != null) {
                return false;
            }
            return this._entries.put((String)name, entry);
        }

        @Override
        public boolean remove(Object entry) {
            if (!(entry instanceof Entry)) {
                return false;
            }
            return this._entries.remove(((Entry)entry).getName()) != null;
        }

        @Override
        public void clear() {
            this._entries.clear();
        }
    }
}

