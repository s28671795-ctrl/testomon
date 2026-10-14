/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e1\u0001BA\u0002\u0011\u0002G\u0005Qa\u0002\u0005\u0006\u001d\u00011\t\u0001\u0005\u0002$!J|G-^2fgB+8\u000f\u001b+j[\u0016|W\u000f^*f[\u0006tG/[2t\u001b\u0006LGNY8y\u0015\t!Q!\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u00051\u0011\u0001B1lW\u0006\u001c\"\u0001\u0001\u0005\u0011\u0005%aQ\"\u0001\u0006\u000b\u0003-\tQa]2bY\u0006L!!\u0004\u0006\u0003\r\u0005s\u0017PU3g\u0003-\u0001Xo\u001d5US6,w*\u001e;\u0004\u0001U\t\u0011\u0003\u0005\u0002\u0013/5\t1C\u0003\u0002\u0015+\u0005AA-\u001e:bi&|gN\u0003\u0002\u0017\u0015\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005a\u0019\"\u0001\u0003#ve\u0006$\u0018n\u001c8")
public interface ProducesPushTimeoutSemanticsMailbox {
    public Duration pushTimeOut();
}

