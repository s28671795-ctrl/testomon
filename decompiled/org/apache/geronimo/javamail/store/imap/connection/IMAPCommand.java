/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Quota;
import javax.mail.UIDFolder;
import javax.mail.search.AddressTerm;
import javax.mail.search.AndTerm;
import javax.mail.search.BodyTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.FromTerm;
import javax.mail.search.HeaderTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.NotTerm;
import javax.mail.search.OrTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.RecipientStringTerm;
import javax.mail.search.RecipientTerm;
import javax.mail.search.SearchException;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SizeTerm;
import javax.mail.search.StringTerm;
import javax.mail.search.SubjectTerm;
import org.apache.geronimo.javamail.store.imap.ACL;
import org.apache.geronimo.javamail.store.imap.IMAPFolder;
import org.apache.geronimo.javamail.store.imap.connection.IMAPConnection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPDateFormat;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSearchDateFormat;
import org.apache.geronimo.javamail.store.imap.connection.IMAPTaggedResponse;
import org.apache.geronimo.javamail.util.CommandFailedException;

public class IMAPCommand {
    public static final char[] encodingTable = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
    protected boolean needWhiteSpace = false;
    protected DataOutputStream out;
    protected ByteArrayOutputStream sink;
    protected List segments = null;
    protected String tag;
    protected static int tagCounter = 0;

    public IMAPCommand() {
        try {
            this.sink = new ByteArrayOutputStream();
            this.out = new DataOutputStream(this.sink);
            this.out.writeBytes(this.getTag());
            this.out.write(32);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public IMAPCommand(String command) {
        this();
        this.append(command);
    }

    public String getTag() {
        if (this.tag == null) {
            this.tag = "a" + tagCounter++;
        }
        return this.tag;
    }

    private void saveCurrentSegment() {
        try {
            this.out.flush();
            byte[] segment = this.sink.toByteArray();
            this.sink.reset();
            if (this.segments == null) {
                this.segments = new ArrayList();
            }
            this.segments.add(segment);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void writeTo(OutputStream outStream, IMAPConnection connection) throws IOException, MessagingException {
        if (this.segments == null) {
            this.out.flush();
            this.sink.writeTo(outStream);
            outStream.write(13);
            outStream.write(10);
        } else {
            for (int i = 0; i < this.segments.size(); ++i) {
                outStream.write((byte[])this.segments.get(i));
                IMAPTaggedResponse response = connection.receiveResponse();
                if (response.isContinuation()) continue;
                throw new CommandFailedException("Error response received on a IMAP continued command:  " + response);
            }
            this.out.flush();
            this.sink.writeTo(outStream);
            outStream.write(13);
            outStream.write(10);
        }
    }

    public void append(String value) {
        try {
            this.out.writeBytes(value);
            this.needWhiteSpace = true;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendString(String value) {
        try {
            this.appendString(value.getBytes("ISO8859-1"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    public void appendQuotedString(String value) {
        try {
            this.appendQuotedString(value.getBytes("ISO8859-1"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    public void appendEncodedString(String value) {
        value = this.encode(value);
        try {
            this.appendString(value.getBytes("ISO8859-1"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    public String encode(String original) {
        byte[] buffer = new byte[4];
        int bufferCount = 0;
        StringBuffer result = new StringBuffer();
        boolean encoding = false;
        for (int i = 0; i < original.length(); ++i) {
            char ch = original.charAt(i);
            if (encoding) {
                if (ch > '\u001f' && ch < '\u007f') {
                    IMAPCommand.encode(buffer, bufferCount, result);
                    result.append('-');
                    encoding = false;
                } else {
                    buffer[++bufferCount] = (byte)(ch >> 8);
                    buffer[++bufferCount] = (byte)(ch & 0xFF);
                    if (bufferCount < 3) continue;
                    bufferCount = IMAPCommand.encode(buffer, bufferCount, result);
                    continue;
                }
            }
            if (ch == '&') {
                result.append('&');
                result.append('-');
                continue;
            }
            if (ch > '\u001f' && ch < '\u007f') {
                result.append(ch);
                continue;
            }
            result.append('&');
            buffer[0] = (byte)(ch >> 8);
            buffer[1] = (byte)(ch & 0xFF);
            bufferCount = 2;
            encoding = true;
        }
        if (encoding) {
            IMAPCommand.encode(buffer, bufferCount, result);
            result.append('-');
        }
        return result.toString();
    }

    protected static int encode(byte[] buffer, int count, StringBuffer result) {
        byte b1 = 0;
        byte b2 = 0;
        byte b3 = 0;
        switch (count) {
            case 0: {
                return 0;
            }
            case 1: {
                b1 = buffer[0];
                result.append(encodingTable[b1 >>> 2 & 0x3F]);
                result.append(encodingTable[b1 << 4 & 0x30]);
                return 0;
            }
            case 2: {
                b1 = buffer[0];
                b2 = buffer[1];
                result.append(encodingTable[b1 >>> 2 & 0x3F]);
                result.append(encodingTable[(b1 << 4 & 0x30) + (b2 >>> 4 & 0xF)]);
                result.append(encodingTable[b2 << 2 & 0x3C]);
                return 0;
            }
            case 3: 
            case 4: {
                b1 = buffer[0];
                b2 = buffer[1];
                b3 = buffer[2];
                result.append(encodingTable[b1 >>> 2 & 0x3F]);
                result.append(encodingTable[(b1 << 4 & 0x30) + (b2 >>> 4 & 0xF)]);
                result.append(encodingTable[(b2 << 2 & 0x3C) + (b3 >>> 6 & 3)]);
                result.append(encodingTable[b3 & 0x3F]);
                if (count == 4) {
                    buffer[0] = buffer[4];
                    return 1;
                }
                return 0;
            }
        }
        return 0;
    }

    public void appendString(String value, String charset) throws MessagingException {
        if (charset == null) {
            try {
                this.appendString(value.getBytes("ISO8859-1"));
            }
            catch (UnsupportedEncodingException e) {}
        } else {
            try {
                this.appendString(value.getBytes(charset));
                throw new MessagingException("Invalid text encoding");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
        }
    }

    public void appendString(byte[] value) {
        switch (IMAPResponseTokenizer.getEncoding(value)) {
            case -3: {
                this.appendLiteral(value);
                break;
            }
            case -2: {
                this.appendQuotedString(value);
                break;
            }
            case -1: {
                this.appendAtom(value);
            }
        }
    }

    public void appendInteger(int value) {
        this.appendAtom(Integer.toString(value));
    }

    public void appendLong(long value) {
        this.appendAtom(Long.toString(value));
    }

    public void appendAtom(String value) {
        try {
            this.appendAtom(value.getBytes("ISO8859-1"));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    public void appendAtom(byte[] value) {
        try {
            this.conditionalWhitespace();
            this.out.write(value);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendLiteral(byte[] value) {
        try {
            this.appendLiteralHeader(value.length);
            this.out.write(value);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    protected void appendLiteralHeader(int size) {
        try {
            this.conditionalWhitespace();
            this.out.writeByte(123);
            this.out.writeBytes(Integer.toString(size));
            this.out.writeBytes("}\r\n");
            this.saveCurrentSegment();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendLiteral(ByteArrayOutputStream value) {
        try {
            this.appendLiteralHeader(value.size());
            value.writeTo(this.out);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendQuotedString(byte[] value) {
        try {
            this.conditionalWhitespace();
            this.out.writeByte(34);
            for (int i = 0; i < value.length; ++i) {
                byte ch = value[i];
                if (ch == 34 || ch == 92) {
                    this.out.writeByte(92);
                }
                this.out.writeByte(ch);
            }
            this.out.writeByte(34);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void startList() {
        try {
            this.conditionalWhitespace();
            this.out.writeByte(40);
            this.needWhiteSpace = false;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void endList() {
        try {
            this.out.writeByte(41);
            this.needWhiteSpace = true;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    protected void conditionalWhitespace() {
        try {
            if (this.needWhiteSpace) {
                this.out.writeByte(32);
            }
            this.needWhiteSpace = true;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendBodySection(String section, String partName) {
        try {
            if (section == null) {
                this.appendBodySection(partName);
                return;
            }
            this.out.writeByte(91);
            this.out.writeBytes(section);
            if (partName != null) {
                this.out.writeByte(46);
                this.out.writeBytes(partName);
            }
            this.out.writeByte(93);
            this.needWhiteSpace = true;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendBodySection(String partName) {
        try {
            this.out.writeByte(91);
            this.out.writeBytes(partName);
            this.out.writeByte(93);
            this.needWhiteSpace = true;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void appendFlags(Flags flags) {
        this.startList();
        Flags.Flag[] systemFlags = flags.getSystemFlags();
        for (int i = 0; i < systemFlags.length; ++i) {
            Flags.Flag flag = systemFlags[i];
            if (flag == Flags.Flag.ANSWERED) {
                this.appendAtom("\\Answered");
                continue;
            }
            if (flag == Flags.Flag.DELETED) {
                this.appendAtom("\\Deleted");
                continue;
            }
            if (flag == Flags.Flag.DRAFT) {
                this.appendAtom("\\Draft");
                continue;
            }
            if (flag == Flags.Flag.FLAGGED) {
                this.appendAtom("\\Flagged");
                continue;
            }
            if (flag == Flags.Flag.RECENT) {
                this.appendAtom("\\Recent");
                continue;
            }
            if (flag != Flags.Flag.SEEN) continue;
            this.appendAtom("\\Seen");
        }
        String[] userFlags = flags.getUserFlags();
        for (int i = 0; i < userFlags.length; ++i) {
            this.appendAtom(userFlags[i]);
        }
        this.endList();
    }

    public void appendDate(Date d) {
        IMAPDateFormat formatter = new IMAPDateFormat();
        this.appendString(formatter.format(d));
    }

    public void appendSearchDate(Date d) {
        IMAPSearchDateFormat formatter = new IMAPSearchDateFormat();
        this.appendString(formatter.format(d));
    }

    public void appendSearchTerm(SearchTerm term, String charset) throws MessagingException {
        if (term instanceof FlagTerm) {
            this.appendFlag((FlagTerm)term, charset);
        } else if (term instanceof AndTerm) {
            this.appendAnd((AndTerm)term, charset);
        } else if (term instanceof OrTerm) {
            this.appendOr((OrTerm)term, charset);
        } else if (term instanceof NotTerm) {
            this.appendNot((NotTerm)term, charset);
        } else if (term instanceof FromTerm) {
            this.appendFrom((FromTerm)term, charset);
        } else if (term instanceof FromStringTerm) {
            this.appendFrom((FromStringTerm)term, charset);
        } else if (term instanceof HeaderTerm) {
            this.appendHeader((HeaderTerm)term, charset);
        } else if (term instanceof RecipientTerm) {
            this.appendRecipient((RecipientTerm)term, charset);
        } else if (term instanceof RecipientStringTerm) {
            this.appendRecipient((RecipientStringTerm)term, charset);
        } else if (term instanceof SubjectTerm) {
            this.appendSubject((SubjectTerm)term, charset);
        } else if (term instanceof BodyTerm) {
            this.appendBody((BodyTerm)term, charset);
        } else if (term instanceof SizeTerm) {
            this.appendSize((SizeTerm)term, charset);
        } else if (term instanceof SentDateTerm) {
            this.appendSentDate((SentDateTerm)term, charset);
        } else if (term instanceof ReceivedDateTerm) {
            this.appendReceivedDate((ReceivedDateTerm)term, charset);
        } else if (term instanceof MessageIDTerm) {
            this.appendMessageID((MessageIDTerm)term, charset);
        } else {
            throw new SearchException("Unsupported search type");
        }
    }

    protected void appendFlag(FlagTerm term, String charset) {
        Flags.Flag flag;
        int i;
        boolean set = term.getTestSet();
        Flags flags = term.getFlags();
        Flags.Flag[] systemFlags = flags.getSystemFlags();
        String[] userFlags = flags.getUserFlags();
        if (systemFlags.length == 0 && userFlags.length == 0) {
            return;
        }
        if (set) {
            for (i = 0; i < systemFlags.length; ++i) {
                flag = systemFlags[i];
                if (flag == Flags.Flag.ANSWERED) {
                    this.appendAtom("ANSWERED");
                    continue;
                }
                if (flag == Flags.Flag.DELETED) {
                    this.appendAtom("DELETED");
                    continue;
                }
                if (flag == Flags.Flag.DRAFT) {
                    this.appendAtom("DRAFT");
                    continue;
                }
                if (flag == Flags.Flag.FLAGGED) {
                    this.appendAtom("FLAGGED");
                    continue;
                }
                if (flag == Flags.Flag.RECENT) {
                    this.appendAtom("RECENT");
                    continue;
                }
                if (flag != Flags.Flag.SEEN) continue;
                this.appendAtom("SEEN");
            }
        } else {
            for (i = 0; i < systemFlags.length; ++i) {
                flag = systemFlags[i];
                if (flag == Flags.Flag.ANSWERED) {
                    this.appendAtom("UNANSWERED");
                    continue;
                }
                if (flag == Flags.Flag.DELETED) {
                    this.appendAtom("UNDELETED");
                    continue;
                }
                if (flag == Flags.Flag.DRAFT) {
                    this.appendAtom("UNDRAFT");
                    continue;
                }
                if (flag == Flags.Flag.FLAGGED) {
                    this.appendAtom("UNFLAGGED");
                    continue;
                }
                if (flag == Flags.Flag.RECENT) {
                    this.appendAtom("OLD");
                    continue;
                }
                if (flag != Flags.Flag.SEEN) continue;
                this.appendAtom("UNSEEN");
            }
        }
        for (i = 0; i < userFlags.length; ++i) {
            this.appendAtom(set ? "KEYWORD" : "UNKEYWORD");
            this.appendAtom(userFlags[i]);
        }
    }

    protected void appendAnd(AndTerm term, String charset) throws MessagingException {
        SearchTerm[] terms = term.getTerms();
        for (int i = 0; i < terms.length; ++i) {
            this.appendSearchTerm(terms[i], charset);
        }
    }

    protected void appendOr(OrTerm term, String charset) throws MessagingException {
        SearchTerm[] terms = term.getTerms();
        if (terms.length == 1) {
            this.appendSearchTerm(terms[0], charset);
            return;
        }
        if (terms.length > 2) {
            SearchTerm current = terms[0];
            for (int i = 1; i < terms.length; ++i) {
                current = new OrTerm(current, terms[i]);
            }
            terms = ((OrTerm)current).getTerms();
        }
        this.appendAtom("OR");
        this.startList();
        this.appendSearchTerm(terms[0], charset);
        this.endList();
        this.startList();
        this.appendSearchTerm(terms[0], charset);
        this.endList();
    }

    protected void appendNot(NotTerm term, String charset) throws MessagingException {
        this.appendAtom("NOT");
        this.startList();
        this.appendSearchTerm(term.getTerm(), charset);
        this.endList();
    }

    protected void appendFrom(FromTerm term, String charset) throws MessagingException {
        this.appendAtom("FROM");
        this.appendString(term.getAddress().toString(), charset);
    }

    protected void appendFrom(FromStringTerm term, String charset) throws MessagingException {
        this.appendAtom("FROM");
        this.appendString(term.getPattern(), charset);
    }

    protected void appendRecipient(RecipientTerm term, String charset) throws MessagingException {
        this.appendAtom(this.recipientType(term.getRecipientType()));
        this.appendString(term.getAddress().toString(), charset);
    }

    protected void appendRecipient(RecipientStringTerm term, String charset) throws MessagingException {
        this.appendAtom(this.recipientType(term.getRecipientType()));
        this.appendString(term.getPattern(), charset);
    }

    protected String recipientType(Message.RecipientType type) throws MessagingException {
        if (type == Message.RecipientType.TO) {
            return "TO";
        }
        if (type == Message.RecipientType.CC) {
            return "CC";
        }
        if (type == Message.RecipientType.BCC) {
            return "BCC";
        }
        throw new SearchException("Unsupported RecipientType");
    }

    protected void appendHeader(HeaderTerm term, String charset) throws MessagingException {
        this.appendAtom("HEADER");
        this.appendString(term.getHeaderName());
        this.appendString(term.getPattern(), charset);
    }

    protected void appendSubject(SubjectTerm term, String charset) throws MessagingException {
        this.appendAtom("SUBJECT");
        this.appendString(term.getPattern(), charset);
    }

    protected void appendBody(BodyTerm term, String charset) throws MessagingException {
        this.appendAtom("BODY");
        this.appendString(term.getPattern(), charset);
    }

    protected void appendSize(SizeTerm term, String charset) throws MessagingException {
        if (term.getComparison() == 5) {
            this.appendAtom("LARGER");
            this.appendInteger(term.getNumber());
        } else if (term.getComparison() == 2) {
            this.appendAtom("SMALLER");
            this.appendInteger(term.getNumber());
        } else if (term.getComparison() == 3) {
            this.appendAtom("NOT");
            this.appendAtom("LARGER");
            this.appendInteger(term.getNumber());
            this.appendAtom("NOT");
            this.appendAtom("SMALLER");
            this.appendInteger(term.getNumber());
        } else if (term.getComparison() == 4) {
            this.appendAtom("OR");
            this.appendAtom("LARGER");
            this.appendInteger(term.getNumber());
            this.appendAtom("SMALLER");
            this.appendInteger(term.getNumber());
        } else if (term.getComparison() == 1) {
            this.appendAtom("NOT");
            this.appendAtom("LARGER");
            this.appendInteger(term.getNumber());
        } else if (term.getComparison() == 6) {
            this.appendAtom("NOT");
            this.appendAtom("SMALLER");
            this.appendInteger(term.getNumber());
        }
    }

    protected void appendMessageID(MessageIDTerm term, String charset) throws MessagingException {
        this.appendAtom("HEADER");
        this.appendString("Message-ID");
        this.appendString(term.getPattern(), charset);
    }

    protected void appendSentDate(SentDateTerm term, String charset) throws MessagingException {
        Date date = term.getDate();
        switch (term.getComparison()) {
            case 3: {
                this.appendAtom("SENTON");
                this.appendSearchDate(date);
                break;
            }
            case 2: {
                this.appendAtom("SENTBEFORE");
                this.appendSearchDate(date);
                break;
            }
            case 5: {
                this.appendAtom("SENTSINCE");
                this.appendSearchDate(date);
                break;
            }
            case 6: {
                this.appendAtom("OR");
                this.appendAtom("SENTSINCE");
                this.appendSearchDate(date);
                this.appendAtom("SENTON");
                this.appendSearchDate(date);
                break;
            }
            case 1: {
                this.appendAtom("OR");
                this.appendAtom("SENTBEFORE");
                this.appendSearchDate(date);
                this.appendAtom("SENTON");
                this.appendSearchDate(date);
                break;
            }
            case 4: {
                this.appendAtom("NOT");
                this.appendAtom("SENTON");
                this.appendSearchDate(date);
                break;
            }
            default: {
                throw new SearchException("Unsupported date comparison type");
            }
        }
    }

    protected void appendReceivedDate(ReceivedDateTerm term, String charset) throws MessagingException {
        Date date = term.getDate();
        switch (term.getComparison()) {
            case 3: {
                this.appendAtom("ON");
                this.appendSearchDate(date);
                break;
            }
            case 2: {
                this.appendAtom("BEFORE");
                this.appendSearchDate(date);
                break;
            }
            case 5: {
                this.appendAtom("SINCE");
                this.appendSearchDate(date);
                break;
            }
            case 6: {
                this.appendAtom("OR");
                this.appendAtom("SINCE");
                this.appendSearchDate(date);
                this.appendAtom("ON");
                this.appendSearchDate(date);
                break;
            }
            case 1: {
                this.appendAtom("OR");
                this.appendAtom("BEFORE");
                this.appendSearchDate(date);
                this.appendAtom("ON");
                this.appendSearchDate(date);
                break;
            }
            case 4: {
                this.appendAtom("NOT");
                this.appendAtom("ON");
                this.appendSearchDate(date);
                break;
            }
            default: {
                throw new SearchException("Unsupported date comparison type");
            }
        }
    }

    public static boolean checkSearchEncoding(SearchTerm term) {
        if (term instanceof StringTerm) {
            return IMAPCommand.checkStringEncoding(((StringTerm)term).getPattern());
        }
        if (term instanceof AddressTerm) {
            return IMAPCommand.checkStringEncoding(((AddressTerm)term).getAddress().toString());
        }
        if (term instanceof NotTerm) {
            return IMAPCommand.checkSearchEncoding(((NotTerm)term).getTerm());
        }
        if (term instanceof AndTerm) {
            return IMAPCommand.checkSearchEncoding(((AndTerm)term).getTerms());
        }
        if (term instanceof OrTerm) {
            return IMAPCommand.checkSearchEncoding(((OrTerm)term).getTerms());
        }
        return false;
    }

    public static boolean checkSearchEncoding(SearchTerm[] terms) {
        for (int i = 0; i < terms.length; ++i) {
            if (!IMAPCommand.checkSearchEncoding(terms[i])) continue;
            return true;
        }
        return false;
    }

    public static boolean checkStringEncoding(String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            if (s2.charAt(i) <= '\u007f') continue;
            return true;
        }
        return false;
    }

    public void appendFetchProfile(FetchProfile profile) throws MessagingException {
        this.startList();
        if (profile.contains(UIDFolder.FetchProfileItem.UID)) {
            this.appendAtom("UID");
        }
        if (profile.contains(FetchProfile.Item.ENVELOPE)) {
            this.appendAtom("ENVELOPE");
            this.appendAtom("INTERNALDATE");
            this.appendAtom("RFC822.SIZE");
        }
        if (profile.contains(FetchProfile.Item.FLAGS)) {
            this.appendAtom("FLAGS");
        }
        if (profile.contains(FetchProfile.Item.CONTENT_INFO)) {
            this.appendAtom("BODYSTRUCTURE");
        }
        if (profile.contains(IMAPFolder.FetchProfileItem.SIZE)) {
            this.appendAtom("RFC822.SIZE");
        }
        if (profile.contains(IMAPFolder.FetchProfileItem.HEADERS)) {
            this.appendAtom("BODY.PEEK[HEADER]");
        } else {
            String[] headers = profile.getHeaderNames();
            if (headers.length > 0) {
                this.appendAtom("BODY.PEEK[HEADER.FIELDS]");
                this.startList();
                for (int i = 0; i < headers.length; ++i) {
                    this.appendAtom(headers[i]);
                }
                this.endList();
            }
        }
        this.endList();
    }

    public void appendACL(ACL acl) {
        this.appendACL(acl, null);
    }

    public void appendACL(ACL acl, String modifier) {
        this.appendString(acl.getName());
        String rights = acl.getRights().toString();
        if (modifier != null) {
            rights = modifier + rights;
        }
        this.appendString(rights);
    }

    public void appendQuota(Quota quota) {
        this.appendString(quota.quotaRoot);
        this.startList();
        for (int i = 0; i < quota.resources.length; ++i) {
            this.appendQuotaResource(quota.resources[i]);
        }
        this.endList();
    }

    public void appendQuotaResource(Quota.Resource resource) {
        this.appendAtom(resource.name);
        this.appendLong(resource.limit);
    }
}

