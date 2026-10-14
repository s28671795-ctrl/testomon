/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.extended;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.ParseHandler;
import org.ldaptive.asn1.UuidType;
import org.ldaptive.extended.IntermediateResponse;

public class SyncInfoMessage
extends IntermediateResponse {
    public static final String OID = "1.3.6.1.4.1.4203.1.9.1.4";
    private static final int HASH_CODE_SEED = 10321;
    private static final DERPath NEW_COOKIE_PATH = new DERPath("/CTX(0)");
    private static final DERPath REFRESH_DELETE_PATH = new DERPath("/CTX(1)");
    private static final DERPath REFRESH_DELETE_COOKIE_PATH = new DERPath("/CTX(1)/OCTSTR[0]");
    private static final DERPath REFRESH_DELETE_DONE_PATH = new DERPath("/CTX(1)/BOOL[1]");
    private static final DERPath REFRESH_PRESENT_PATH = new DERPath("/CTX(2)");
    private static final DERPath REFRESH_PRESENT_COOKIE_PATH = new DERPath("/CTX(2)/OCTSTR[0]");
    private static final DERPath REFRESH_PRESENT_DONE_PATH = new DERPath("/CTX(2)/BOOL[1]");
    private static final DERPath SYNC_ID_SET_PATH = new DERPath("/CTX(3)");
    private static final DERPath SYNC_ID_SET_COOKIE_PATH = new DERPath("/CTX(3)/OCTSTR[0]");
    private static final DERPath SYNC_ID_SET_DELETES_PATH = new DERPath("/CTX(3)/BOOL[1]");
    private static final DERPath SYNC_ID_SET_UUIDS_PATH = new DERPath("/CTX(3)/SET/OCTSTR");
    private Type messageType;
    private byte[] cookie;
    private boolean refreshDone = true;
    private boolean refreshDeletes;
    private Set<UUID> entryUuids = new HashSet<UUID>();

    protected SyncInfoMessage() {
    }

    public SyncInfoMessage(DERBuffer buffer) {
        super(buffer);
    }

    @Override
    protected ParseHandler getResponseValueParseHandler() {
        return (parser, encoded) -> {
            DERParser p = new DERParser();
            p.registerHandler(NEW_COOKIE_PATH, new NewCookieHandler(this));
            p.registerHandler(REFRESH_DELETE_PATH, new RefreshDeleteHandler(this));
            p.registerHandler(REFRESH_DELETE_COOKIE_PATH, new RefreshDeleteCookieHandler(this));
            p.registerHandler(REFRESH_DELETE_DONE_PATH, new RefreshDeleteDoneHandler(this));
            p.registerHandler(REFRESH_PRESENT_PATH, new RefreshPresentHandler(this));
            p.registerHandler(REFRESH_PRESENT_COOKIE_PATH, new RefreshPresentCookieHandler(this));
            p.registerHandler(REFRESH_PRESENT_DONE_PATH, new RefreshPresentDoneHandler(this));
            p.registerHandler(SYNC_ID_SET_PATH, new SyncIdSetHandler(this));
            p.registerHandler(SYNC_ID_SET_COOKIE_PATH, new SyncIdSetCookieHandler(this));
            p.registerHandler(SYNC_ID_SET_DELETES_PATH, new SyncIdSetDeletesHandler(this));
            p.registerHandler(SYNC_ID_SET_UUIDS_PATH, new SyncIdSetUuidsHandler(this));
            p.parse(encoded);
        };
    }

    public Type getMessageType() {
        return this.messageType;
    }

    public void setMessageType(Type type) {
        this.messageType = type;
    }

    public byte[] getCookie() {
        return this.cookie;
    }

    public void setCookie(byte[] value) {
        this.cookie = value;
    }

    public boolean getRefreshDone() {
        return this.refreshDone;
    }

    public void setRefreshDone(boolean b) {
        this.refreshDone = b;
    }

    public boolean getRefreshDeletes() {
        return this.refreshDeletes;
    }

    public void setRefreshDeletes(boolean b) {
        this.refreshDeletes = b;
    }

    public Set<UUID> getEntryUuids() {
        return this.entryUuids;
    }

    public void addEntryUuids(UUID ... uuids) {
        for (UUID id : uuids) {
            this.entryUuids.add(id);
        }
    }

    public void setEntryUuids(Set<UUID> uuids) {
        this.entryUuids = uuids;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SyncInfoMessage && super.equals(o)) {
            SyncInfoMessage v = (SyncInfoMessage)o;
            return LdapUtils.areEqual((Object)this.messageType, (Object)v.messageType) && LdapUtils.areEqual(this.cookie, v.cookie) && LdapUtils.areEqual(this.refreshDone, v.refreshDone) && LdapUtils.areEqual(this.refreshDeletes, v.refreshDeletes) && LdapUtils.areEqual(this.entryUuids, v.entryUuids);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10321, new Object[]{this.getMessageID(), this.getControls(), this.getResponseName(), this.getResponseValue(), this.messageType, this.cookie, this.refreshDone, this.refreshDeletes, this.entryUuids});
    }

    @Override
    public String toString() {
        return super.toString() + ", " + "messageType=" + (Object)((Object)this.messageType) + ", " + "cookie=" + LdapUtils.base64Encode(this.cookie) + ", " + "refreshDone=" + this.refreshDone + ", " + "refreshDeletes=" + this.refreshDeletes + ", " + "entryUuids=" + this.entryUuids;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends IntermediateResponse.Builder {
        protected Builder() {
            super(new SyncInfoMessage());
        }

        protected Builder(SyncInfoMessage m4) {
            super(m4);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder type(Type t2) {
            ((SyncInfoMessage)this.object).messageType = t2;
            return this;
        }

        public Builder cookie(byte[] b) {
            ((SyncInfoMessage)this.object).cookie = b;
            return this;
        }

        public Builder refreshDone(boolean b) {
            ((SyncInfoMessage)this.object).refreshDone = b;
            return this;
        }

        public Builder refreshDeletes(boolean b) {
            ((SyncInfoMessage)this.object).refreshDeletes = b;
            return this;
        }

        public Builder uuids(UUID ... uuids) {
            ((SyncInfoMessage)this.object).addEntryUuids(uuids);
            return this;
        }
    }

    private static class SyncIdSetUuidsHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        SyncIdSetUuidsHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).getEntryUuids().add(UuidType.decode(encoded));
        }
    }

    private static class SyncIdSetDeletesHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        SyncIdSetDeletesHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setRefreshDeletes(BooleanType.decode(encoded));
        }
    }

    private static class SyncIdSetCookieHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        SyncIdSetCookieHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((SyncInfoMessage)this.getObject()).setCookie(cookie);
            }
        }
    }

    private static class SyncIdSetHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        SyncIdSetHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setMessageType(Type.SYNC_ID_SET);
        }
    }

    private static class RefreshPresentDoneHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        RefreshPresentDoneHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setRefreshDone(BooleanType.decode(encoded));
        }
    }

    private static class RefreshPresentCookieHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        RefreshPresentCookieHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((SyncInfoMessage)this.getObject()).setCookie(cookie);
            }
        }
    }

    private static class RefreshPresentHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        RefreshPresentHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setMessageType(Type.REFRESH_PRESENT);
        }
    }

    private static class RefreshDeleteDoneHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        RefreshDeleteDoneHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setRefreshDone(BooleanType.decode(encoded));
        }
    }

    private static class RefreshDeleteCookieHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        RefreshDeleteCookieHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((SyncInfoMessage)this.getObject()).setCookie(cookie);
            }
        }
    }

    private static class RefreshDeleteHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        RefreshDeleteHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setMessageType(Type.REFRESH_DELETE);
        }
    }

    private static class NewCookieHandler
    extends AbstractParseHandler<SyncInfoMessage> {
        NewCookieHandler(SyncInfoMessage message) {
            super(message);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((SyncInfoMessage)this.getObject()).setMessageType(Type.NEW_COOKIE);
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((SyncInfoMessage)this.getObject()).setCookie(cookie);
            }
        }
    }

    public static enum Type {
        NEW_COOKIE,
        REFRESH_DELETE,
        REFRESH_PRESENT,
        SYNC_ID_SET;

    }
}

