/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.EarliestFirstSystemMessageList;
import akka.dispatch.sysmsg.LatestFirstSystemMessageList$;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.SystemMessageList$;
import scala.Predef$;

public final class EarliestFirstSystemMessageList$ {
    public static final EarliestFirstSystemMessageList$ MODULE$ = new EarliestFirstSystemMessageList$();

    public final boolean isEmpty$extension(SystemMessage $this) {
        return $this == null;
    }

    public final boolean nonEmpty$extension(SystemMessage $this) {
        return $this != null;
    }

    public final int size$extension(SystemMessage $this) {
        return SystemMessageList$.MODULE$.sizeInner($this, 0);
    }

    public final SystemMessage tail$extension(SystemMessage $this) {
        return $this.next();
    }

    public final SystemMessage reverse$extension(SystemMessage $this) {
        return SystemMessageList$.MODULE$.reverseInner($this, null);
    }

    public final SystemMessage $colon$colon$extension(SystemMessage $this, SystemMessage msg) {
        Predef$.MODULE$.assert(msg != null);
        msg.next_$eq($this);
        return msg;
    }

    public final SystemMessage reversePrepend$extension(SystemMessage $this, SystemMessage other) {
        SystemMessage remaining = other;
        SystemMessage result = $this;
        while (LatestFirstSystemMessageList$.MODULE$.nonEmpty$extension(remaining)) {
            SystemMessage msg = remaining;
            remaining = LatestFirstSystemMessageList$.MODULE$.tail$extension(remaining);
            result = this.$colon$colon$extension(result, msg);
        }
        return result;
    }

    public final SystemMessage reverse_$colon$colon$colon$extension(SystemMessage $this, SystemMessage other) {
        return this.reversePrepend$extension($this, other);
    }

    public final int hashCode$extension(SystemMessage $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(SystemMessage $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof EarliestFirstSystemMessageList)) return false;
        boolean bl = true;
        if (!bl) return false;
        SystemMessage systemMessage = x$1 == null ? null : ((EarliestFirstSystemMessageList)x$1).head();
        SystemMessage systemMessage2 = $this;
        SystemMessage systemMessage3 = systemMessage;
        if (systemMessage2 != null) {
            if (!systemMessage2.equals(systemMessage3)) return false;
            return true;
        }
        if (systemMessage3 == null) return true;
        return false;
    }

    private EarliestFirstSystemMessageList$() {
    }
}

