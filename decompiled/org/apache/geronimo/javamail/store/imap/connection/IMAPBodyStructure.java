/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ContentType;
import org.apache.geronimo.javamail.store.imap.connection.IMAPEnvelope;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.util.ResponseFormatException;

public class IMAPBodyStructure
extends IMAPFetchDataItem {
    public ContentType mimeType = new ContentType();
    public ContentDisposition disposition = null;
    public String contentID;
    public String contentDescription;
    public String transferEncoding;
    public int bodySize;
    public int lines = -1;
    public IMAPBodyStructure[] parts;
    public Map dispositionParameters;
    public List languages;
    public String md5Hash;
    public IMAPEnvelope nestedEnvelope;
    public IMAPBodyStructure nestedBody;

    public IMAPBodyStructure(IMAPResponseTokenizer source2) throws MessagingException {
        super(3);
        this.parseBodyStructure(source2);
    }

    protected void parseBodyStructure(IMAPResponseTokenizer source2) throws MessagingException {
        source2.checkLeftParen();
        if (source2.peek().getType() == 40) {
            this.parseMultipartBodyStructure(source2);
        } else {
            this.parseSinglepartBodyStructure(source2);
        }
    }

    protected void parseMultipartBodyStructure(IMAPResponseTokenizer source2) throws MessagingException {
        this.mimeType.setPrimaryType("multipart");
        ArrayList<IMAPBodyStructure> partList = new ArrayList<IMAPBodyStructure>();
        do {
            IMAPBodyStructure part = new IMAPBodyStructure(source2);
            partList.add(part);
        } while (source2.peek().getType() == 40);
        this.parts = partList.toArray(new IMAPBodyStructure[partList.size()]);
        this.mimeType.setSubType(source2.readString());
        if (source2.checkListEnd()) {
            return;
        }
        this.mimeType.setParameterList(source2.readParameterList());
        if (source2.checkListEnd()) {
            return;
        }
        this.parseMessageExtensions(source2);
    }

    protected void parseSinglepartBodyStructure(IMAPResponseTokenizer source2) throws MessagingException {
        this.mimeType.setPrimaryType(source2.readString());
        this.mimeType.setSubType(source2.readString());
        this.mimeType.setParameterList(source2.readParameterList());
        this.contentID = source2.readStringOrNil();
        this.contentDescription = source2.readStringOrNil();
        this.transferEncoding = source2.readStringOrNil();
        this.bodySize = source2.readInteger();
        if (this.mimeType.match("message/rfc822")) {
            this.nestedEnvelope = new IMAPEnvelope(source2);
            this.nestedBody = new IMAPBodyStructure(source2);
            this.lines = source2.readInteger();
        } else if (this.mimeType.match("text/*")) {
            this.lines = source2.readInteger();
        }
        if (source2.checkListEnd()) {
            return;
        }
        this.md5Hash = source2.readString();
        this.parseMessageExtensions(source2);
    }

    protected void parseMessageExtensions(IMAPResponseTokenizer source2) throws MessagingException {
        if (source2.checkListEnd()) {
            return;
        }
        this.disposition = new ContentDisposition();
        if (source2.peek(true).getType() == 40) {
            source2.checkLeftParen();
            this.disposition.setDisposition(source2.readString());
            this.disposition.setParameterList(source2.readParameterList());
            source2.checkRightParen();
        } else if (source2.peek(true) == IMAPResponseTokenizer.NIL) {
            source2.next();
        } else {
            throw new ResponseFormatException("Expecting NIL or '(' in response");
        }
        if (source2.checkListEnd()) {
            return;
        }
        this.languages = source2.readStringList();
        if (source2.checkListEnd()) {
            return;
        }
        source2.readStringList();
        while (source2.notListEnd()) {
            source2.skipExtensionItem();
        }
        source2.next();
    }

    public boolean isMultipart() {
        return this.parts != null;
    }

    public boolean isAttachedMessage() {
        return !this.isMultipart() && this.mimeType.match("message/rfc822");
    }
}

