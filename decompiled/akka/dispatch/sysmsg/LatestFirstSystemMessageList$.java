/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.LatestFirstSystemMessageList;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.SystemMessageList$;
import scala.Predef$;

public final class LatestFirstSystemMessageList$ {
    public static final LatestFirstSystemMessageList$ MODULE$ = new LatestFirstSystemMessageList$();

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

    public final int hashCode$extension(SystemMessage $this) {
        return $this.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals$extension(SystemMessage $this, Object x$1) {
        Object object = x$1;
        if (!(object instanceof LatestFirstSystemMessageList)) return false;
        boolean bl = true;
        if (!bl) return false;
        SystemMessage systemMessage = x$1 == null ? null : ((LatestFirstSystemMessageList)x$1).head();
        SystemMessage systemMessage2 = $this;
        SystemMessage systemMessage3 = systemMessage;
        if (systemMessage2 != null) {
            if (!systemMessage2.equals(systemMessage3)) return false;
            return true;
        }
        if (systemMessage3 == null) return true;
        return false;
    }

    private LatestFirstSystemMessageList$() {
    }
}

