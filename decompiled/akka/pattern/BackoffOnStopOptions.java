/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.annotation.DoNotInherit;
import akka.pattern.ExtendedBackoffOptions;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592qa\u0001\u0003\u0011\u0002G\u0005\u0012\u0002C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003\u0018\u0001\u0019\u0005\u0001D\u0001\u000bCC\u000e\\wN\u001a4P]N#x\u000e](qi&|gn\u001d\u0006\u0003\u000b\u0019\tq\u0001]1ui\u0016\u0014hNC\u0001\b\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001A\u0003\t\u0011\u0005-qQ\"\u0001\u0007\u000b\u00035\tQa]2bY\u0006L!a\u0004\u0007\u0003\r\u0005s\u0017PU3g!\r\t\"\u0003F\u0007\u0002\t%\u00111\u0003\u0002\u0002\u0017\u000bb$XM\u001c3fI\n\u000b7m[8gM>\u0003H/[8ogB\u0011\u0011\u0003A\u0001\u001co&$\b\u000eR3gCVdGo\u0015;paBLgnZ*ue\u0006$XmZ=\u0016\u0003Q\tAc^5uQ\u001aKg.\u00197Ti>\u0004X*Z:tC\u001e,GC\u0001\u000b\u001a\u0011\u0015Q\"\u00011\u0001\u001c\u0003II7OR5oC2\u001cFo\u001c9NKN\u001c\u0018mZ3\u0011\t-ab$I\u0005\u0003;1\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005-y\u0012B\u0001\u0011\r\u0005\r\te.\u001f\t\u0003\u0017\tJ!a\t\u0007\u0003\u000f\t{w\u000e\\3b]&\u0012\u0001!J\u0005\u0003M\u0011\u0011\u0001DQ1dW>4gm\u00148Ti>\u0004x\n\u001d;j_:\u001c\u0018*\u001c9mQ\t\u0001\u0001\u0006\u0005\u0002*Y5\t!F\u0003\u0002,\r\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u00055R#\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
public interface BackoffOnStopOptions
extends ExtendedBackoffOptions<BackoffOnStopOptions> {
    public BackoffOnStopOptions withDefaultStoppingStrategy();

    public BackoffOnStopOptions withFinalStopMessage(Function1<Object, Object> var1);
}

