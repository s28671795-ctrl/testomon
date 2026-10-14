/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.jetbrains.annotations.ApiStatus;
import org.jooq.FileComparator;
import org.jooq.FlywayFileComparator;
import org.jooq.Source;
import org.jooq.exception.IOException;
import org.jooq.tools.JooqLogger;

@ApiStatus.Internal
public final class FilePattern {
    private static final JooqLogger log = JooqLogger.getLogger(FilePattern.class);
    private final Sort sort;
    private final Comparator<File> comparator;
    private final File basedir;
    private final String pattern;
    private final String encoding;
    private final Pattern regexForMatches;
    private final Pattern regexForLoad;

    public FilePattern() {
        this(null, null, "**", "UTF-8");
    }

    private FilePattern(Sort sort, File basedir, String pattern, String encoding) {
        this.sort = sort;
        this.comparator = FilePattern.fileComparator(sort);
        this.basedir = basedir == null ? new File(".") : basedir;
        this.pattern = pattern;
        this.encoding = encoding;
        this.regexForMatches = Pattern.compile("^" + this.regex() + "$");
        this.regexForLoad = Pattern.compile("^.*?" + this.regex() + "$");
    }

    public final Sort sort() {
        return this.sort;
    }

    public final FilePattern sort(Sort newSort) {
        return new FilePattern(newSort, this.basedir, this.pattern, this.encoding);
    }

    public final File basedir() {
        return this.basedir;
    }

    public final FilePattern basedir(File newBasedir) {
        return new FilePattern(this.sort, newBasedir, this.pattern, this.encoding);
    }

    public final String pattern() {
        return this.pattern;
    }

    public final FilePattern pattern(String newPattern) {
        return new FilePattern(this.sort, this.basedir, newPattern, this.encoding);
    }

    public final String encoding() {
        return this.encoding;
    }

    public final FilePattern encoding(String newEncoding) {
        return new FilePattern(this.sort, this.basedir, this.pattern, newEncoding);
    }

    private static final Comparator<File> fileComparator(Sort sort) {
        if (sort == null) {
            sort = Sort.SEMANTIC;
        }
        switch (sort) {
            case ALPHANUMERIC: {
                return Comparator.naturalOrder();
            }
            case NONE: {
                return null;
            }
            case FLYWAY: {
                return FlywayFileComparator.INSTANCE;
            }
            case SEMANTIC: {
                return FileComparator.INSTANCE;
            }
        }
        throw new IllegalArgumentException("Unsupported sort: " + String.valueOf((Object)sort));
    }

    public final boolean matches(String path) {
        return this.regexForMatches.matcher(path.replace("\\", "/")).matches();
    }

    public final List<Source> collect() {
        ArrayList<Source> list = new ArrayList<Source>();
        this.load(list::add);
        return list;
    }

    public final void load(Consumer<Source> loader) {
        boolean loaded;
        block17: {
            loaded = false;
            URL url2 = null;
            try {
                url2 = FilePattern.class.getResource(this.pattern);
            }
            catch (Exception exception) {
                // empty catch block
            }
            File file = null;
            try {
                if (url2 != null) {
                    log.info("Reading from classpath: " + this.pattern);
                    try (InputStream is = FilePattern.class.getResourceAsStream(this.pattern);){
                        loader.accept(Source.of(Source.of(is).readString()));
                        loaded = true;
                        break block17;
                    }
                }
                file = new File(this.pattern);
                if (file.exists()) {
                    this.load(file, this.comparator, null, loader);
                    loaded = true;
                } else if (!this.pattern.contains("*") && !this.pattern.contains("?")) {
                    this.load(new File(this.basedir, this.pattern), this.comparator, null, loader);
                    loaded = true;
                } else {
                    String prefix = this.pattern.replaceAll("[^\\/]*?[*?].*", "");
                    file = new File(prefix);
                    if (!file.isAbsolute()) {
                        file = new File(this.basedir, prefix).getAbsoluteFile();
                    }
                    this.load(file, this.comparator, this.regexForLoad, loader);
                    loaded = true;
                }
            }
            catch (java.io.IOException e) {
                throw new IOException("Error while loading pattern", e);
            }
        }
        if (!loaded) {
            log.error("Could not find source(s) : " + this.pattern);
        }
    }

    private String regex() {
        return this.pattern.replace("\\", "/").replace(".", "\\.").replace("?", "[^/]").replace("**", ".+?").replace("*", "[^/]*");
    }

    private final void load(File file, Comparator<File> fileComparator, Pattern regex2, Consumer<Source> loader) throws java.io.IOException {
        if (file.isFile()) {
            if (regex2 == null || regex2.matcher(file.getCanonicalPath().replace("\\", "/")).matches()) {
                log.info("Reading from: " + String.valueOf(file) + " [*]");
                this.load0(file, loader);
            }
        } else if (file.isDirectory()) {
            log.info("Reading from: " + String.valueOf(file));
            File[] files = file.listFiles();
            if (files != null) {
                if (fileComparator != null) {
                    Arrays.sort(files, fileComparator);
                }
                for (File f : files) {
                    this.load(f, this.comparator, regex2, loader);
                }
            }
        }
    }

    private final void load0(File file, Consumer<Source> loader) {
        try {
            loader.accept(Source.of(file, this.encoding));
        }
        catch (RuntimeException e) {
            log.error("Error while loading file: " + String.valueOf(file));
            throw e;
        }
    }

    public String toString() {
        return this.pattern;
    }

    public static enum Sort {
        SEMANTIC,
        ALPHANUMERIC,
        FLYWAY,
        NONE;


        public static final Sort of(String sort) {
            if ("alphanumeric".equals(sort)) {
                return ALPHANUMERIC;
            }
            if ("none".equals(sort)) {
                return NONE;
            }
            if ("flyway".equals(sort)) {
                return FLYWAY;
            }
            return SEMANTIC;
        }
    }
}

