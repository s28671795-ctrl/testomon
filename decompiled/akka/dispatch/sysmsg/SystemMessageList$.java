/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.sysmsg;

import akka.dispatch.sysmsg.SystemMessage;

public final class SystemMessageList$ {
    public static final SystemMessageList$ MODULE$ = new SystemMessageList$();
    private static final SystemMessage LNil = null;
    private static final SystemMessage ENil = null;

    public final SystemMessage LNil() {
        return LNil;
    }

    public final SystemMessage ENil() {
        return ENil;
    }

    public int sizeInner(SystemMessage head, int acc) {
        while (head != null) {
            ++acc;
            head = head.next();
        }
        return acc;
    }

    public SystemMessage reverseInner(SystemMessage head, SystemMessage acc) {
        while (head != null) {
            SystemMessage next = head.next();
            head.next_$eq(acc);
            acc = head;
            head = next;
        }
        return acc;
    }

    private SystemMessageList$() {
    }
}

