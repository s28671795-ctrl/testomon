/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jooq.Commit;
import org.jooq.Configuration;
import org.jooq.ContentType;
import org.jooq.DSLContext;
import org.jooq.File;
import org.jooq.Files;
import org.jooq.Meta;
import org.jooq.Source;
import org.jooq.Version;
import org.jooq.exception.DataMigrationException;
import org.jooq.impl.AbstractNode;
import org.jooq.impl.FilesImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class CommitImpl
extends AbstractNode<Commit>
implements Commit {
    private final Configuration configuration;
    private final DSLContext ctx;
    private final List<? extends Commit> parents;
    private final Map<String, File> delta;
    private final Map<String, File> files;

    CommitImpl(Configuration configuration, String id, String message, List<? extends Commit> parents, Collection<? extends File> delta) {
        super(id, message);
        this.configuration = configuration;
        this.ctx = configuration.dsl();
        this.parents = parents;
        this.delta = CommitImpl.map(delta, false);
        this.files = this.initFiles();
    }

    private static final Map<String, File> map(Collection<? extends File> list, boolean applyDeletions) {
        return CommitImpl.apply(new LinkedHashMap<String, File>(), list, applyDeletions);
    }

    private static final Map<String, File> apply(Map<String, File> result, Collection<? extends File> list, boolean applyDeletions) {
        for (File file : list) {
            CommitImpl.apply(result, file, applyDeletions);
        }
        return result;
    }

    private static final Map<String, File> apply(Map<String, File> result, File file, boolean applyDeletions) {
        if (applyDeletions && file.content() == null) {
            result.remove(file.path());
        } else {
            result.put(file.path(), file);
        }
        return result;
    }

    private final Map<String, File> initFiles() {
        if (this.parents.isEmpty()) {
            return this.delta;
        }
        Commit parent = this.parents.get(0);
        return CommitImpl.apply(CommitImpl.map(parent.files(), true), this.delta(), true);
    }

    @Override
    public final List<Commit> parents() {
        return Collections.unmodifiableList(this.parents);
    }

    @Override
    public final Collection<File> delta() {
        return this.delta.values();
    }

    @Override
    public final Collection<File> files() {
        return this.files.values();
    }

    private static final Collection<Source> sources(Collection<File> files) {
        return Tools.map(files, f -> Source.of(f.content()));
    }

    @Override
    public final Collection<Source> sources() {
        return CommitImpl.sources(this.files());
    }

    @Override
    public final Commit commit(String newId, File ... newFiles) {
        return this.commit(newId, "", newFiles);
    }

    @Override
    public final Commit commit(String newId, Collection<? extends File> newFiles) {
        return this.commit(newId, "", newFiles);
    }

    @Override
    public final Commit commit(String newId, String newMessage, File ... newFiles) {
        return this.commit(newId, newMessage, Arrays.asList(newFiles));
    }

    @Override
    public final Commit commit(String newId, String newMessage, Collection<? extends File> newFiles) {
        return new CommitImpl(this.configuration, newId, newMessage, Arrays.asList(this), newFiles);
    }

    @Override
    public final Commit merge(String newId, Commit with, File ... newFiles) {
        return this.merge(newId, null, with, Arrays.asList(newFiles));
    }

    @Override
    public final Commit merge(String newId, Commit with, Collection<? extends File> newFiles) {
        return this.merge(newId, null, with, newFiles);
    }

    @Override
    public final Commit merge(String newId, String newMessage, Commit with, File ... newFiles) {
        return this.merge(newId, newMessage, with, Arrays.asList(newFiles));
    }

    @Override
    public final Commit merge(String newId, String newMessage, Commit with, Collection<? extends File> newFiles) {
        return new CommitImpl(this.configuration, newId, newMessage, Arrays.asList(this, with), newFiles);
    }

    @Override
    public final Version version() {
        return ((Commit)this.root()).migrateTo(this).to();
    }

    @Override
    public final Meta meta() {
        return this.version().meta();
    }

    @Override
    public final Files migrateTo(Commit resultCommit) {
        Commit ancestor = this.commonAncestor(resultCommit);
        return this.migrateTo0(resultCommit);
    }

    private final Files migrateTo0(Commit resultCommit) {
        LinkedHashMap<Object, File> history = new LinkedHashMap<Object, File>();
        HashMap<String, Object> historyKeys = new HashMap<String, Object>();
        LinkedHashMap<String, File> result = new LinkedHashMap<String, File>();
        LinkedHashMap<String, File> tempHistory = new LinkedHashMap<String, File>();
        HashMap<String, String> tempHistoryKeys = new HashMap<String, String>();
        ArrayDeque<Commit> commitHistory = new ArrayDeque<Commit>();
        CommitImpl.history(commitHistory, new HashSet<Commit>(), Arrays.asList(resultCommit));
        boolean recordingResult = false;
        boolean hasDeletions = false;
        for (Commit commit : commitHistory) {
            Object key2;
            ArrayList<File> commitFiles = new ArrayList<File>(commit.delta());
            Iterator deletions = commitFiles.iterator();
            while (deletions.hasNext()) {
                Object remove;
                File file2 = (File)deletions.next();
                if (file2.content() != null) continue;
                hasDeletions |= true;
                String path = file2.path();
                String tempKey = (String)tempHistoryKeys.remove(path);
                String tempRemove = tempKey != null ? tempKey : path;
                key2 = (String)historyKeys.remove(path);
                Object object = remove = key2 != null ? key2 : path;
                if (recordingResult && result.remove(tempRemove) == null && file2.type() == ContentType.INCREMENT && history.containsKey(tempRemove)) {
                    result.put(tempRemove, file2);
                } else if (recordingResult && result.remove(remove) == null && file2.type() == ContentType.SCHEMA && history.containsKey(remove)) {
                    result.put((String)remove, file2);
                } else {
                    history.remove(tempRemove);
                }
                tempHistory.remove(path);
                deletions.remove();
            }
            Iterator increments = commitFiles.iterator();
            while (increments.hasNext()) {
                File oldFile;
                File file3 = (File)increments.next();
                if (file3.type() != ContentType.INCREMENT) continue;
                String path = file3.path();
                File file4 = oldFile = recordingResult ? (File)history.get(path) : history.put(path, file3);
                if (oldFile == null && !tempHistory.isEmpty() && !result.containsKey(path)) {
                    CommitImpl.move(tempHistory, result, tempHistoryKeys);
                }
                if (recordingResult) {
                    result.put(path, file3);
                }
                increments.remove();
            }
            Iterator schemas = commitFiles.iterator();
            while (schemas.hasNext()) {
                File file5 = (File)schemas.next();
                if (file5.type() != ContentType.SCHEMA) continue;
                String path = file5.path();
                key2 = commit.id() + "-" + path;
                if (recordingResult) {
                    tempHistory.put(path, file5);
                    tempHistoryKeys.put(path, (String)key2);
                } else {
                    history.put(key2, file5);
                    historyKeys.put(path, key2);
                }
                schemas.remove();
            }
            recordingResult |= this.id().equals(commit.id());
        }
        CommitImpl.move(tempHistory, result, tempHistoryKeys);
        Iterator it = result.entrySet().iterator();
        while (it.hasNext()) {
            File historicFile;
            Map.Entry entry = it.next();
            String path = (String)entry.getKey();
            File file6 = (File)entry.getValue();
            if (file6.type() != ContentType.INCREMENT || (historicFile = (File)history.get(path)) == null) continue;
            if (!StringUtils.equals(historicFile.content(), file6.content())) {
                throw new DataMigrationException("Cannot edit increment file that has already been applied: " + String.valueOf(file6));
            }
            it.remove();
        }
        if (hasDeletions) {
            LinkedHashMap<String, List<String>> keys2 = new LinkedHashMap<String, List<String>>();
            LinkedHashSet<String> remove = new LinkedHashSet<String>();
            result.forEach((key, file) -> {
                if (file.type() == ContentType.SCHEMA) {
                    keys2.computeIfAbsent(file.path(), p -> new ArrayList()).add(key);
                } else {
                    CommitImpl.moveAllButLast(keys2, remove);
                }
            });
            CommitImpl.moveAllButLast(keys2, remove);
            for (String r : remove) {
                result.remove(r);
            }
        }
        HashMap<String, File> versionFiles = new HashMap<String, File>();
        Version from = CommitImpl.version(this.ctx.migrations().version("init"), this.id(), versionFiles, history.values());
        Version to = CommitImpl.version(from, resultCommit.id(), versionFiles, result.values());
        return new FilesImpl(from, to, result.values());
    }

    private static final void history(Deque<Commit> commitHistory, Set<Commit> set, List<Commit> commits) {
        for (Commit commit : commits) {
            if (!set.add(commit)) continue;
            commitHistory.push(commit);
        }
        LinkedHashSet p = new LinkedHashSet();
        for (Commit commit : commits) {
            p.addAll(commit.parents());
        }
        if (!p.isEmpty()) {
            ArrayList<Commit> arrayList = new ArrayList<Commit>(p);
            Collections.reverse(arrayList);
            CommitImpl.history(commitHistory, set, arrayList);
        }
    }

    private static final Version version(Version from, String newId, Map<String, File> files, Collection<File> result) {
        Version to = from;
        ArrayList<File> list = new ArrayList<File>(result);
        for (int j = 0; j < list.size(); ++j) {
            File file = (File)list.get(j);
            String commitId = newId + "-" + file.path();
            to = file.type() == ContentType.SCHEMA ? to.commit(commitId, CommitImpl.sources(CommitImpl.apply(files, file, true).values()).toArray(Tools.EMPTY_SOURCE)) : to.apply(commitId, file.content());
        }
        return to;
    }

    private static final void moveAllButLast(Map<String, List<String>> keys2, Set<String> remove) {
        for (List<String> k : keys2.values()) {
            if (k.size() <= 1) continue;
            remove.addAll(k.subList(0, k.size() - 1));
        }
        keys2.clear();
    }

    private static final void move(Map<String, File> files, Map<String, File> result, Map<String, String> keys2) {
        for (File file : files.values()) {
            result.put(keys2.get(file.path()), file);
        }
        files.clear();
    }

    public int hashCode() {
        return this.id().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Commit) {
            return this.id().equals(((Commit)obj).id());
        }
        return false;
    }

    public String toString() {
        return StringUtils.isBlank(this.message()) ? this.id() : this.id() + " - " + this.message();
    }
}

