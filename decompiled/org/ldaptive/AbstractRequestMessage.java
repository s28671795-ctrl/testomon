/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Arrays;
import org.ldaptive.LdapUtils;
import org.ldaptive.Request;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.control.RequestControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRequestMessage
implements Request {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RequestControl[] controls;

    public RequestControl[] getControls() {
        return this.controls;
    }

    public void setControls(RequestControl ... cntrls) {
        this.controls = cntrls;
    }

    @Override
    public byte[] encode(int id) {
        DEREncoder[] requestEncoders = this.getRequestEncoders(id);
        DEREncoder controlEncoder = this.getControlEncoder();
        DEREncoder[] encoders = controlEncoder != null ? LdapUtils.concatArrays(requestEncoders, new DEREncoder[][]{{controlEncoder}}) : requestEncoders;
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, encoders);
        return se.encode();
    }

    protected abstract DEREncoder[] getRequestEncoders(int var1);

    private DEREncoder getControlEncoder() {
        if (this.controls == null || this.controls.length == 0) {
            return null;
        }
        DEREncoder[] controlEncoders = new DEREncoder[this.controls.length];
        for (int i = 0; i < this.controls.length; ++i) {
            controlEncoders[i] = this.controls[i].hasValue() ? new ConstructedDEREncoder(UniversalDERTag.SEQ, new OctetStringType(this.controls[i].getOID()), new BooleanType(this.controls[i].getCriticality()), new OctetStringType(this.controls[i].encode())) : new ConstructedDEREncoder(UniversalDERTag.SEQ, new OctetStringType(this.controls[i].getOID()), new BooleanType(this.controls[i].getCriticality()));
        }
        return new ConstructedDEREncoder(new ContextDERTag(0, true), controlEncoders);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "controls=" + Arrays.toString(this.controls);
    }

    protected static abstract class AbstractBuilder<B, T extends AbstractRequestMessage> {
        protected final T object;

        protected AbstractBuilder(T t2) {
            this.object = t2;
        }

        protected abstract B self();

        public B controls(RequestControl ... cntrls) {
            ((AbstractRequestMessage)this.object).setControls(cntrls);
            return this.self();
        }

        public T build() {
            return this.object;
        }
    }
}

