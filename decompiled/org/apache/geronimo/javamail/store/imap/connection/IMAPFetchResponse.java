/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBody;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodySection;
import org.apache.geronimo.javamail.store.imap.connection.IMAPBodyStructure;
import org.apache.geronimo.javamail.store.imap.connection.IMAPEnvelope;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchDataItem;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFlags;
import org.apache.geronimo.javamail.store.imap.connection.IMAPInternalDate;
import org.apache.geronimo.javamail.store.imap.connection.IMAPInternetHeader;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMessageSize;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMessageText;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUid;
import org.apache.geronimo.javamail.store.imap.connection.IMAPUntaggedResponse;

public class IMAPFetchResponse
extends IMAPUntaggedResponse {
    protected List dataItems = new ArrayList();
    public int sequenceNumber;

    public IMAPFetchResponse(int sequenceNumber, byte[] data, IMAPResponseTokenizer source2) throws MessagingException {
        super("FETCH", data);
        this.sequenceNumber = sequenceNumber;
        source2.checkLeftParen();
        while (source2.notListEnd()) {
            String itemName = source2.readAtom(true).toUpperCase();
            if (itemName.equals("ENVELOPE")) {
                this.dataItems.add(new IMAPEnvelope(source2));
                continue;
            }
            if (itemName.equals("BODYSTRUCTURE")) {
                this.dataItems.add(new IMAPBodyStructure(source2));
                continue;
            }
            if (itemName.equals("FLAGS")) {
                this.dataItems.add(new IMAPFlags(source2));
                continue;
            }
            if (itemName.equals("INTERNALDATE")) {
                this.dataItems.add(new IMAPInternalDate(source2));
                continue;
            }
            if (itemName.equals("UID")) {
                this.dataItems.add(new IMAPUid(sequenceNumber, source2));
                continue;
            }
            if (itemName.equals("RFC822")) {
                itemName = source2.readAtom(false).toUpperCase();
                if (itemName.equals(".SIZE")) {
                    this.dataItems.add(new IMAPMessageSize(source2));
                    continue;
                }
                if (itemName.equals(".HEADER")) {
                    this.dataItems.add(new IMAPInternetHeader(source2.readByteArray()));
                    continue;
                }
                if (!itemName.equals(".TEXT")) continue;
                this.dataItems.add(new IMAPMessageText(source2.readByteArray()));
                continue;
            }
            if (!itemName.equals("BODY")) continue;
            IMAPBodySection section = new IMAPBodySection(source2);
            switch (section.section) {
                case 0: {
                    this.dataItems.add(new IMAPBody(section, source2.readByteArray()));
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    this.dataItems.add(new IMAPInternetHeader(section, source2.readByteArray()));
                    break;
                }
                case 4: {
                    this.dataItems.add(new IMAPMessageText(section, source2.readByteArray()));
                }
            }
        }
        source2.checkRightParen();
    }

    public int getSequenceNumber() {
        return this.sequenceNumber;
    }

    public int getCount() {
        return this.dataItems.size();
    }

    public List getDataItems() {
        return this.dataItems;
    }

    public IMAPFetchDataItem getDataItem(int type) {
        for (int i = 0; i < this.dataItems.size(); ++i) {
            IMAPFetchDataItem item = (IMAPFetchDataItem)this.dataItems.get(i);
            if (!item.isType(type)) continue;
            return item;
        }
        return null;
    }
}

