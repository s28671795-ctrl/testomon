/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import org.apache.geronimo.javamail.store.imap.connection.IMAPBodySection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;

public class IMAPFetchBodyPart
extends IMAPFetchDataItem {
    protected IMAPBodySection section;

    public IMAPFetchBodyPart(int type, IMAPBodySection section) {
        super(type);
        this.section = section;
    }

    public String getPartNumber() {
        return this.section.partNumber;
    }

    public int getSectionType() {
        return this.section.section;
    }

    public int getSubstringStart() {
        return this.section.start;
    }

    public int getSubstringLength() {
        return this.section.length;
    }
}

