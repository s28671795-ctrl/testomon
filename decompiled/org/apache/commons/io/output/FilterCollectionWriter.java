/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.io.IOExceptionList;
import org.apache.commons.io.IOIndexedException;

public class FilterCollectionWriter
extends Writer {
    protected final Collection<Writer> EMPTY_WRITERS = Collections.emptyList();
    protected final Collection<Writer> writers;

    protected FilterCollectionWriter(Collection<Writer> writers) {
        this.writers = writers == null ? this.EMPTY_WRITERS : writers;
    }

    protected FilterCollectionWriter(Writer ... writers) {
        this.writers = writers == null ? this.EMPTY_WRITERS : Arrays.asList(writers);
    }

    @Override
    public Writer append(char c) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.append(c);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
        return this;
    }

    @Override
    public Writer append(CharSequence csq) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.append(csq);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
        return this;
    }

    @Override
    public Writer append(CharSequence csq, int start2, int end) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.append(csq, start2, end);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
        return this;
    }

    @Override
    public void close() throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.close();
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }

    @Override
    public void flush() throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.flush();
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.write(cbuf, off, len);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }

    @Override
    public void write(char[] cbuf) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.write(cbuf);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }

    @Override
    public void write(int c) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.write(c);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }

    @Override
    public void write(String str) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.write(str);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        ArrayList<IOIndexedException> causeList = new ArrayList<IOIndexedException>();
        int i = 0;
        for (Writer w : this.writers) {
            if (w != null) {
                try {
                    w.write(str, off, len);
                }
                catch (IOException e) {
                    causeList.add(new IOIndexedException(i, (Throwable)e));
                }
            }
            ++i;
        }
        if (!causeList.isEmpty()) {
            throw new IOExceptionList(causeList);
        }
    }
}

