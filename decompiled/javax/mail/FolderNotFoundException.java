/*
 * Decompiled with CFR 0.152.
 */
package javax.mail;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class FolderNotFoundException
extends MessagingException {
    private transient Folder folder;
    private static final long serialVersionUID = 472612108891249403L;

    public FolderNotFoundException() {
    }

    public FolderNotFoundException(Folder folder) {
        this.folder = folder;
    }

    public FolderNotFoundException(Folder folder, String s2) {
        super(s2);
        this.folder = folder;
    }

    public FolderNotFoundException(Folder folder, String s2, Exception e) {
        super(s2, e);
        this.folder = folder;
    }

    public FolderNotFoundException(String s2, Folder folder) {
        super(s2);
        this.folder = folder;
    }

    public Folder getFolder() {
        return this.folder;
    }
}

