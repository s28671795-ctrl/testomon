/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.nntp.newsrc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.geronimo.javamail.store.nntp.newsrc.NNTPNewsrcGroup;

public abstract class NNTPNewsrc {
    Map groups = new HashMap();
    boolean dirty = false;

    protected NNTPNewsrc() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load() {
        BufferedReader in = null;
        try {
            in = this.getInputReader();
            String line = in.readLine();
            while (line != null) {
                NNTPNewsrcGroup group = NNTPNewsrcGroup.parse(this, line);
                if (group != null) {
                    this.groups.put(group.getName(), group);
                }
                line = in.readLine();
            }
            in.close();
        }
        catch (IOException e) {
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {}
            }
        }
    }

    public void save() throws IOException {
        Writer out = this.getOutputWriter();
        for (NNTPNewsrcGroup group : this.groups.values()) {
            group.save(out);
        }
        out.close();
    }

    public abstract BufferedReader getInputReader() throws IOException;

    public abstract Writer getOutputWriter() throws IOException;

    public NNTPNewsrcGroup getGroup(String name) {
        NNTPNewsrcGroup group = (NNTPNewsrcGroup)this.groups.get(name);
        if (group == null) {
            group = new NNTPNewsrcGroup(this, name, null, false);
            this.groups.put(name, group);
            this.dirty = true;
        }
        return group;
    }

    public void setDirty() {
        this.dirty = true;
    }

    public void close() {
        if (this.dirty) {
            try {
                this.save();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public Iterator getGroups() {
        return this.groups.values().iterator();
    }
}

