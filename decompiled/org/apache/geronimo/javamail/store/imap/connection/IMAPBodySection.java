/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.util.ResponseFormatException;

public class IMAPBodySection {
    public static final int BODY = 0;
    public static final int HEADERS = 1;
    public static final int HEADERSUBSET = 2;
    public static final int MIME = 3;
    public static final int TEXT = 4;
    public String partNumber = "1";
    public String sectionName = "";
    public int section;
    public int start = -1;
    public int length = -1;
    public List headers = null;

    public IMAPBodySection(int section) {
        this.section = section;
        this.partNumber = "1";
        this.start = -1;
        this.length = -1;
    }

    public IMAPBodySection(IMAPResponseTokenizer source2) throws MessagingException {
        if (!source2.peek(false, true).isType(91)) {
            this.section = 0;
            return;
        }
        IMAPResponseTokenizer.Token token = source2.next(false, true);
        token = source2.next(false, true);
        if (token.isType(-4)) {
            token = this.parsePartNumber(token, source2);
        }
        if (token.isType(-1)) {
            token = this.parseSectionName(token, source2);
        }
        if (token.isType(40)) {
            token = this.parseHeaderList(source2);
        }
        if (!token.isType(93)) {
            throw new ResponseFormatException("Invalid section identifier on FETCH response");
        }
        this.parseSubstringValues(source2);
        if (this.sectionName.equals("")) {
            this.section = 0;
        } else if (this.sectionName.equals("HEADER")) {
            this.section = 1;
        } else if (this.sectionName.equals("HEADER.FIELDS")) {
            this.section = 2;
        } else if (this.sectionName.equals("HEADER.FIELDS.NOT")) {
            this.section = 2;
        } else if (this.sectionName.equals("TEXT")) {
            this.section = 4;
        } else if (this.sectionName.equals("MIME")) {
            this.section = 3;
        }
    }

    private IMAPResponseTokenizer.Token parsePartNumber(IMAPResponseTokenizer.Token token, IMAPResponseTokenizer source2) throws MessagingException {
        StringBuffer part = new StringBuffer(token.getValue());
        token = source2.next(false, true);
        while (true) {
            if (!token.isType(46)) {
                this.partNumber = part.toString();
                return token;
            }
            token = source2.next(false, true);
            if (!token.isType(-4)) break;
            part.append('.');
            part.append(token.getValue());
            token = source2.next(false, true);
        }
        this.partNumber = part.toString();
        return token;
    }

    private IMAPResponseTokenizer.Token parseSectionName(IMAPResponseTokenizer.Token token, IMAPResponseTokenizer source2) throws MessagingException {
        StringBuffer part = new StringBuffer(token.getValue());
        token = source2.next(false, true);
        while (true) {
            if (!token.isType(46)) {
                this.sectionName = part.toString();
                return token;
            }
            part.append('.');
            part.append(source2.readString());
            token = source2.next(false, true);
        }
    }

    private IMAPResponseTokenizer.Token parseHeaderList(IMAPResponseTokenizer source2) throws MessagingException {
        this.headers = new ArrayList();
        while (source2.notListEnd()) {
            String value = source2.readString();
            this.headers.add(value);
        }
        source2.next();
        return source2.next(false, true);
    }

    private void parseSubstringValues(IMAPResponseTokenizer source2) throws MessagingException {
        if (!source2.peek(false, true).isType(60)) {
            return;
        }
        source2.next(false, true);
        this.start = source2.next(false, true).getInteger();
        source2.next(false, true);
        this.length = source2.next(false, true).getInteger();
        source2.next(false, true);
    }
}

