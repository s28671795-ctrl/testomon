/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.ldaptive.LdapUtils;
import org.ldaptive.Message;
import org.ldaptive.asn1.AbstractParseHandler;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.IntegerType;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.control.ControlFactory;
import org.ldaptive.control.ResponseControl;

public abstract class AbstractMessage
implements Message {
    private int messageID;
    private List<ResponseControl> controls = new ArrayList<ResponseControl>();

    @Override
    public int getMessageID() {
        return this.messageID;
    }

    public void setMessageID(int id) {
        this.messageID = id;
    }

    @Override
    public ResponseControl[] getControls() {
        return this.controls != null ? this.controls.toArray(new ResponseControl[0]) : null;
    }

    public void addControls(ResponseControl ... cntrls) {
        for (ResponseControl c : cntrls) {
            this.controls.add(c);
        }
    }

    protected <T extends Message> void copyValues(T message) {
        this.setMessageID(message.getMessageID());
        this.addControls(message.getControls());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractMessage) {
            AbstractMessage v = (AbstractMessage)o;
            return LdapUtils.areEqual(this.getMessageID(), v.getMessageID()) && LdapUtils.areEqual(this.getControls(), v.getControls());
        }
        return false;
    }

    public abstract int hashCode();

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "messageID=" + this.messageID + ", " + "controls=" + this.controls;
    }

    protected static abstract class AbstractBuilder<B, T extends AbstractMessage> {
        protected final T object;

        protected AbstractBuilder(T t2) {
            this.object = t2;
        }

        protected abstract B self();

        public B messageID(int id) {
            ((AbstractMessage)this.object).setMessageID(id);
            return this.self();
        }

        public B controls(ResponseControl ... controls) {
            ((AbstractMessage)this.object).addControls(controls);
            return this.self();
        }

        public T build() {
            return this.object;
        }
    }

    protected static class ControlParser {
        private static final DERPath CRITICAL_PATH = new DERPath("/BOOL[1]");
        private static final DERPath OID_PATH = new DERPath("/OCTSTR[0]");
        private static final DERPath VALUE_PATH = new DERPath("/OCTSTR[1]");
        private static final DERPath ALT_VALUE_PATH = new DERPath("/OCTSTR[2]");
        private final DERParser parser = new DERParser();
        private Boolean critical;
        private String oid;
        private DERBuffer value;

        public ControlParser() {
            this.parser.registerHandler(CRITICAL_PATH, (p, e) -> {
                this.critical = BooleanType.decode(e);
            });
            this.parser.registerHandler(OID_PATH, (p, e) -> {
                this.oid = OctetStringType.decode(e);
            });
            this.parser.registerHandler(VALUE_PATH, (p, e) -> {
                this.value = e.slice();
            });
            this.parser.registerHandler(ALT_VALUE_PATH, (p, e) -> {
                if (this.value == null) {
                    this.value = e.slice();
                }
            });
        }

        public void parse(DERBuffer buffer) {
            this.parser.parse(buffer);
        }

        public Optional<Boolean> getCritical() {
            return Optional.ofNullable(this.critical);
        }

        public Optional<String> getOid() {
            return Optional.ofNullable(this.oid);
        }

        public Optional<DERBuffer> getValue() {
            return Optional.ofNullable(this.value);
        }
    }

    protected static class ControlsHandler
    extends AbstractParseHandler<AbstractMessage> {
        public static final DERPath PATH = new DERPath("/SEQ/CTX(0)/SEQ");

        public ControlsHandler(AbstractMessage response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ControlParser p = new ControlParser();
            p.parse(encoded);
            ((AbstractMessage)this.getObject()).addControls(ControlFactory.createResponseControl(p.getOid().isPresent() ? p.getOid().get() : null, p.getCritical().isPresent() ? p.getCritical().get() : false, p.getValue().isPresent() ? p.getValue().get() : null));
        }
    }

    protected static class MessageIDHandler
    extends AbstractParseHandler<AbstractMessage> {
        public static final DERPath PATH = new DERPath("/SEQ/INT[0]");

        public MessageIDHandler(AbstractMessage response) {
            super(response);
        }

        @Override
        public void handle(DERParser parser, DERBuffer encoded) {
            ((AbstractMessage)this.getObject()).setMessageID(IntegerType.decodeUnsignedPrimitive(encoded));
        }
    }
}

