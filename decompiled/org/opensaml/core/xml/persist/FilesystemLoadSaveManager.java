/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.persist;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.persist.XMLObjectLoadSaveManager;
import org.opensaml.core.xml.util.XMLObjectSource;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class FilesystemLoadSaveManager<T extends XMLObject>
implements XMLObjectLoadSaveManager<T> {
    private Logger log = LoggerFactory.getLogger(FilesystemLoadSaveManager.class);
    private File baseDirectory;
    private ParserPool parserPool;
    private FileFilter fileFilter;

    public FilesystemLoadSaveManager(@Nonnull String baseDir) {
        this(new File(Constraint.isNotNull(StringSupport.trimOrNull(baseDir), "Base directory string instance was null or empty")), null);
    }

    public FilesystemLoadSaveManager(@Nonnull File baseDir) {
        this(baseDir, null);
    }

    public FilesystemLoadSaveManager(@Nonnull String baseDir, @Nullable ParserPool pp) {
        this(new File(Constraint.isNotNull(StringSupport.trimOrNull(baseDir), "Base directory string instance was null or empty")), pp);
    }

    public FilesystemLoadSaveManager(@Nonnull File baseDir, @Nullable ParserPool pp) {
        this.baseDirectory = Constraint.isNotNull(baseDir, "Base directory File instance was null");
        Constraint.isTrue(this.baseDirectory.isAbsolute(), "Base directory specified was not an absolute path");
        if (this.baseDirectory.exists()) {
            Constraint.isTrue(this.baseDirectory.isDirectory(), "Existing base directory path was not a directory");
        } else {
            Constraint.isTrue(this.baseDirectory.mkdirs(), "Base directory did not exist and could not be created");
        }
        this.parserPool = pp;
        if (this.parserPool == null) {
            this.parserPool = Constraint.isNotNull(XMLObjectProviderRegistrySupport.getParserPool(), "Specified ParserPool was null and global ParserPool was not available");
        }
        this.fileFilter = new DefaultFileFilter();
    }

    @Override
    public Set<String> listKeys() throws IOException {
        File[] files = this.baseDirectory.listFiles(this.fileFilter);
        HashSet<String> keys2 = new HashSet<String>();
        for (File file : files) {
            keys2.add(file.getName());
        }
        return Collections.unmodifiableSet(keys2);
    }

    @Override
    public Iterable<Pair<String, T>> listAll() throws IOException {
        return new FileIterable(this.listKeys());
    }

    @Override
    public boolean exists(String key) throws IOException {
        return this.buildFile(key).exists();
    }

    /*
     * Exception decompiling
     */
    @Override
    public T load(String key) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void save(String key, T xmlObject) throws IOException {
        this.save(key, xmlObject, false);
    }

    @Override
    public void save(String key, T xmlObject, boolean overwrite) throws IOException {
        if (!overwrite && this.exists(key)) {
            throw new IOException(String.format("Target file already exists for key '%s' and overwrite not indicated", key));
        }
        File file = this.buildFile(key);
        try (FileOutputStream fos = new FileOutputStream(file);){
            List<XMLObjectSource> sources = xmlObject.getObjectMetadata().get(XMLObjectSource.class);
            if (sources.size() == 1) {
                this.log.debug("XMLObject contained 1 XMLObjectSource instance, persisting existing byte[]");
                XMLObjectSource source2 = sources.get(0);
                fos.write(source2.getObjectSource());
            } else {
                this.log.debug("XMLObject contained {} XMLObjectSource instances, persisting marshalled object", (Object)sources.size());
                try {
                    XMLObjectSupport.marshallToOutputStream(xmlObject, fos);
                }
                catch (MarshallingException e) {
                    throw new IOException(String.format("Error saving target file: %s", file.getAbsolutePath()), e);
                }
            }
            fos.flush();
        }
    }

    @Override
    public boolean remove(String key) throws IOException {
        File file = this.buildFile(key);
        if (file.exists()) {
            boolean success = file.delete();
            if (success) {
                return true;
            }
            throw new IOException(String.format("Error removing target file: %s", file.getAbsolutePath()));
        }
        return false;
    }

    @Override
    public boolean updateKey(String currentKey, String newKey) throws IOException {
        File currentFile = this.buildFile(currentKey);
        if (!currentFile.exists()) {
            return false;
        }
        File newFile = this.buildFile(newKey);
        if (newFile.exists()) {
            throw new IOException(String.format("Specified new key already exists: %s", newKey));
        }
        Files.move(currentFile, newFile);
        return true;
    }

    protected File buildFile(String key) throws IOException {
        File path = new File(this.baseDirectory, Constraint.isNotNull(StringSupport.trimOrNull(key), "Input key was null or empty"));
        if (path.exists() && !path.isFile()) {
            throw new IOException(String.format("Path exists based on specified key, but is not a file: %s", path.getAbsolutePath()));
        }
        return path;
    }

    private class FileIterator
    implements Iterator<Pair<String, T>> {
        private Iterator<String> keysIter;
        private Pair<String, T> current;

        public FileIterator(Collection<String> filenames) {
            HashSet<String> keys2 = new HashSet<String>();
            keys2.addAll(Collections2.filter(filenames, Predicates.notNull()));
            this.keysIter = keys2.iterator();
        }

        @Override
        public boolean hasNext() {
            if (this.current != null) {
                return true;
            }
            this.current = this.getNext();
            return this.current != null;
        }

        @Override
        public Pair<String, T> next() {
            if (this.current != null) {
                Pair temp = this.current;
                this.current = null;
                return temp;
            }
            Pair temp = this.getNext();
            if (temp != null) {
                return temp;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private Pair<String, T> getNext() {
            while (this.keysIter.hasNext()) {
                String key = this.keysIter.next();
                try {
                    Object xmlObject = FilesystemLoadSaveManager.this.load(key);
                    if (xmlObject != null) {
                        return new Pair(key, xmlObject);
                    }
                    FilesystemLoadSaveManager.this.log.warn("Target file with key '{}' was removed since iterator creation, skipping", (Object)key);
                }
                catch (IOException e) {
                    FilesystemLoadSaveManager.this.log.warn("Error loading target file with key '{}'", (Object)key, (Object)e);
                }
            }
            return null;
        }
    }

    private class FileIterable
    implements Iterable<Pair<String, T>> {
        private Set<String> keys = new HashSet<String>();

        public FileIterable(Collection<String> filenames) {
            this.keys.addAll(Collections2.filter(filenames, Predicates.notNull()));
        }

        @Override
        public Iterator<Pair<String, T>> iterator() {
            return new FileIterator(this.keys);
        }
    }

    public static class DefaultFileFilter
    implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            if (pathname == null) {
                return false;
            }
            return pathname.isFile();
        }
    }
}

