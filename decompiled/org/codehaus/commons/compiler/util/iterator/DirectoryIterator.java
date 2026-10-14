/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.codehaus.commons.compiler.util.Producer;
import org.codehaus.commons.compiler.util.iterator.MultiDimensionalIterator;
import org.codehaus.commons.compiler.util.iterator.ProducerIterator;
import org.codehaus.commons.nullanalysis.Nullable;

public class DirectoryIterator
extends ProducerIterator<File> {
    public DirectoryIterator(final File rootDirectory, final FilenameFilter directoryNameFilter, final FilenameFilter fileNameFilter) {
        super(new Producer<File>(){
            private final List<State> stateStack;
            {
                this.stateStack = DirectoryIterator.newArrayList(new State(rootDirectory));
            }

            @Override
            @Nullable
            public File produce() {
                while (!this.stateStack.isEmpty()) {
                    State state = this.stateStack.get(this.stateStack.size() - 1);
                    if (state.directories.hasNext()) {
                        this.stateStack.add(new State(state.directories.next()));
                        continue;
                    }
                    if (state.files.hasNext()) {
                        File file = state.files.next();
                        return file;
                    }
                    this.stateStack.remove(this.stateStack.size() - 1);
                }
                return null;
            }

            class State {
                final Iterator<File> directories;
                final Iterator<File> files;

                State(File dir) {
                    File[] entries = dir.listFiles();
                    if (entries == null) {
                        throw new DirectoryNotListableException(dir.getPath());
                    }
                    ArrayList<File> directoryList = new ArrayList<File>();
                    ArrayList<File> fileList = new ArrayList<File>();
                    for (File entry : entries) {
                        if (entry.isDirectory()) {
                            if (!directoryNameFilter.accept(dir, entry.getName())) continue;
                            directoryList.add(entry);
                            continue;
                        }
                        if (!entry.isFile() || !fileNameFilter.accept(dir, entry.getName())) continue;
                        fileList.add(entry);
                    }
                    this.directories = directoryList.iterator();
                    this.files = fileList.iterator();
                }
            }
        });
    }

    public static Iterator<File> traverseDirectories(File[] rootDirectories, FilenameFilter directoryNameFilter, FilenameFilter fileNameFilter) {
        ArrayList<DirectoryIterator> result = new ArrayList<DirectoryIterator>();
        for (File rootDirectory : rootDirectories) {
            result.add(new DirectoryIterator(rootDirectory, directoryNameFilter, fileNameFilter));
        }
        return new MultiDimensionalIterator<File>(result.iterator(), 2);
    }

    private static <T> ArrayList<T> newArrayList(T initialElement) {
        ArrayList<T> result = new ArrayList<T>();
        result.add(initialElement);
        return result;
    }

    public static class DirectoryNotListableException
    extends RuntimeException {
        public DirectoryNotListableException(String message) {
            super(message);
        }
    }
}

