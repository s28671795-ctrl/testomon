/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap;

import org.apache.geronimo.javamail.store.imap.IMAPFolder;
import org.apache.geronimo.javamail.store.imap.IMAPStore;
import org.apache.geronimo.javamail.store.imap.connection.IMAPNamespace;

public class IMAPNamespaceFolder
extends IMAPFolder {
    IMAPNamespaceFolder(IMAPStore store, IMAPNamespace namespace) {
        super(store, namespace.prefix, namespace.separator);
    }

    protected String getMailBoxName() {
        if (this.separator == '\u0000') {
            return this.fullname;
        }
        return this.fullname + this.separator;
    }
}

