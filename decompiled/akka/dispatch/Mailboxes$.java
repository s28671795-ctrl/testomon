/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

public final class Mailboxes$ {
    public static final Mailboxes$ MODULE$ = new Mailboxes$();

    public final String DefaultMailboxId() {
        return "akka.actor.default-mailbox";
    }

    public final String NoMailboxRequirement() {
        return "";
    }

    public final String BoundedCapacityPrefix() {
        return "bounded-capacity:";
    }

    private Mailboxes$() {
    }
}

