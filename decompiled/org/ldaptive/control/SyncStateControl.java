/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

import java.util.UUID;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.UuidType;
import org.ldaptive.control.AbstractControl;
import org.ldaptive.control.ResponseControl;

public class SyncStateControl
extends AbstractControl
implements ResponseControl {
    public static final String OID = "1.3.6.1.4.1.4203.1.9.1.2";
    private static final int HASH_CODE_SEED = 751;
    private State syncState;
    private UUID entryUuid;
    private byte[] cookie;

    public SyncStateControl() {
        super(OID);
    }

    public SyncStateControl(boolean critical) {
        super(OID, critical);
    }

    public SyncStateControl(State state) {
        super(OID);
        this.setSyncState(state);
    }

    public SyncStateControl(State state, boolean critical) {
        super(OID, critical);
        this.setSyncState(state);
    }

    public SyncStateControl(State state, UUID uuid, boolean critical) {
        super(OID, critical);
        this.setSyncState(state);
        this.setEntryUuid(uuid);
    }

    public SyncStateControl(State state, UUID uuid, byte[] value, boolean critical) {
        super(OID, critical);
        this.setSyncState(state);
        this.setEntryUuid(uuid);
        this.setCookie(value);
    }

    public State getSyncState() {
        return this.syncState;
    }

    public void setSyncState(State state) {
        this.syncState = state;
    }

    public UUID getEntryUuid() {
        return this.entryUuid;
    }

    public void setEntryUuid(UUID uuid) {
        this.entryUuid = uuid;
    }

    public byte[] getCookie() {
        return this.cookie;
    }

    public void setCookie(byte[] value) {
        this.cookie = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SyncStateControl && super.equals(o)) {
            SyncStateControl v = (SyncStateControl)o;
            return LdapUtils.areEqual((Object)this.syncState, (Object)v.syncState) && LdapUtils.areEqual(this.entryUuid, v.entryUuid) && LdapUtils.areEqual(this.cookie, v.cookie);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(751, new Object[]{this.getOID(), this.getCriticality(), this.syncState, this.entryUuid, this.cookie});
    }

    @Override
    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "criticality=" + this.getCriticality() + ", " + "syncState=" + (Object)((Object)this.syncState) + ", " + "entryUuid=" + this.entryUuid + ", " + "cookie=" + LdapUtils.base64Encode(this.cookie) + "]";
    }

    @Override
    public void decode(DERBuffer encoded) {
        DERParser parser = new DERParser();
        parser.registerHandler(StateHandler.PATH, new StateHandler(this));
        parser.registerHandler(EntryUuidHandler.PATH, new EntryUuidHandler(this));
        parser.registerHandler(CookieHandler.PATH, new CookieHandler(this));
        parser.parse(encoded);
    }

    private static class CookieHandler
    extends AbstractParseHandler<SyncStateControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[2]");

        CookieHandler(SyncStateControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            byte[] cookie = encoded.getRemainingBytes();
            if (cookie != null && cookie.length > 0) {
                ((SyncStateControl)this.getObject()).setCookie(cookie);
            }
        }
    }

    private static class EntryUuidHandler
    extends AbstractParseHandler<SyncStateControl> {
        public static final DERPath PATH = new DERPath("/SEQ/OCTSTR[1]");

        EntryUuidHandler(SyncStateControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            if (encoded.hasRemaining()) {
                ((SyncStateControl)this.getObject()).setEntryUuid(UuidType.decode(encoded));
            }
        }
    }

    private static class StateHandler
    extends AbstractParseHandler<SyncStateControl> {
        public static final DERPath PATH = new DERPath("/SEQ/ENUM[0]");

        StateHandler(SyncStateControl control) {
            super(control);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            int stateValue = IntegerType.decode(encoded).intValue();
            State s2 = State.valueOf(stateValue);
            if (s2 == null) {
                throw new IllegalArgumentException("Unknown state value " + stateValue);
            }
            ((SyncStateControl)this.getObject()).setSyncState(s2);
        }
    }

    public static enum State {
        PRESET(0),
        ADD(1),
        MODIFY(2),
        DELETE(3);

        private final int value;

        private State(int i) {
            this.value = i;
        }

        public int value() {
            return this.value;
        }

        public static State valueOf(int i) {
            for (State s2 : State.values()) {
                if (s2.value() != i) continue;
                return s2;
            }
            return null;
        }
    }
}

