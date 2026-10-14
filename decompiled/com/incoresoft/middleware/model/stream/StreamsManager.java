/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.stream;

import com.incoresoft.middleware.model.base.ItemManager;
import com.incoresoft.middleware.model.folders.Folder;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamArchive;
import com.incoresoft.middleware.model.stream.StreamListener;
import com.incoresoft.middleware.model.user.User;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface StreamsManager
extends ItemManager<Stream> {
    public Stream get(UUID var1);

    public List<Folder> getStreamFolders();

    public Folder getStreamFolder(int var1);

    public StreamArchive getStreamsArchive();

    public Collection<? extends Stream> getAll(User var1);

    public String createSnapshot(UUID var1);

    default public void addListener(StreamListener listener) {
    }

    default public void removeListener(StreamListener listener) {
    }
}

