/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Commit;
import org.jooq.Commits;
import org.jooq.Configuration;
import org.jooq.File;
import org.jooq.impl.Migrations;
import org.jooq.impl.Tools;
import org.jooq.migrations.xml.jaxb.ChangeType;
import org.jooq.migrations.xml.jaxb.CommitType;
import org.jooq.migrations.xml.jaxb.FileType;
import org.jooq.migrations.xml.jaxb.MigrationsType;
import org.jooq.migrations.xml.jaxb.ParentType;

final class CommitsImpl
implements Commits {
    final Configuration configuration;
    final Commit root;
    final Map<String, Commit> commits;

    CommitsImpl(Configuration configuration, Commit root) {
        this.configuration = configuration;
        this.commits = new LinkedHashMap<String, Commit>();
        this.root = root;
        this.add(root);
    }

    @Override
    public void add(Commit commit) {
        this.commits.put(commit.id(), commit);
    }

    @Override
    public void addAll(Commit ... c) {
        this.addAll(Arrays.asList(c));
    }

    @Override
    public void addAll(Collection<? extends Commit> c) {
        for (Commit commit : c) {
            this.add(commit);
        }
    }

    @Override
    public final Commit root() {
        return this.root;
    }

    @Override
    public final Commit get(String id) {
        return this.commits.get(id);
    }

    @Override
    public final Iterator<Commit> iterator() {
        return this.commits.values().iterator();
    }

    @Override
    public final Commits load(MigrationsType migrations) {
        HashMap<String, CommitType> map = new HashMap<String, CommitType>();
        for (CommitType commit : migrations.getCommits()) {
            map.put(commit.getId(), commit);
        }
        for (CommitType commit : migrations.getCommits()) {
            this.load(map, commit);
        }
        return this;
    }

    private final Commit load(Map<String, CommitType> map, CommitType commit) {
        Commit result = this.commits.get(commit.getId());
        if (result != null) {
            return result;
        }
        Commit p1 = this.root;
        Commit p2 = null;
        List<ParentType> parents = commit.getParents();
        int size = parents.size();
        if (size > 0) {
            CommitType c1 = map.get(parents.get(0).getId());
            if (c1 == null) {
                throw new UnsupportedOperationException("Parent not found: " + parents.get(0).getId());
            }
            p1 = this.load(map, c1);
            if (size == 2) {
                CommitType c2 = map.get(parents.get(1).getId());
                if (c2 == null) {
                    throw new UnsupportedOperationException("Parent not found: " + parents.get(0).getId());
                }
                p2 = this.load(map, c2);
            } else if (size > 2) {
                throw new UnsupportedOperationException("Merging more than two parents not yet supported");
            }
        }
        result = p2 == null ? p1.commit(commit.getId(), commit.getMessage(), this.files(commit)) : p1.merge(commit.getId(), commit.getMessage(), p2, this.files(commit));
        this.commits.put(commit.getId(), result);
        return result;
    }

    private final List<File> files(CommitType commit) {
        return Tools.map(commit.getFiles(), f -> Migrations.file(f.getPath(), f.getChange() == ChangeType.DELETE ? null : f.getContent(), f.getContentType()));
    }

    @Override
    public final MigrationsType export() {
        return new MigrationsType().withCommits(Tools.map(this, commit -> new CommitType().withId(commit.id()).withMessage(commit.message()).withParents(Tools.map(commit.parents(), parent -> new ParentType().withId(parent.id()))).withFiles(Tools.map(commit.files(), file -> new FileType().withPath(file.path()).withContent(file.content()).withContentType(file.type()).withChange(file.content() == null ? ChangeType.DELETE : ChangeType.MODIFY)))));
    }

    public String toString() {
        return String.valueOf(this.commits.values());
    }
}

