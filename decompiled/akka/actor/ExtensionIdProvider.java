/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Extension;
import akka.actor.ExtensionId;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00052qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001CA\nFqR,gn]5p]&#\u0007K]8wS\u0012,'O\u0003\u0002\u0005\u000b\u0005)\u0011m\u0019;pe*\ta!\u0001\u0003bW.\f7\u0001A\n\u0003\u0001%\u0001\"AC\u0007\u000e\u0003-Q\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001d-\u0011a!\u00118z%\u00164\u0017A\u00027p_.,\b/F\u0001\u0012a\t\u0011\u0002\u0004E\u0002\u0014)Yi\u0011aA\u0005\u0003+\r\u00111\"\u0012=uK:\u001c\u0018n\u001c8JIB\u0011q\u0003\u0007\u0007\u0001\t%I\u0012!!A\u0001\u0002\u000b\u0005!DA\u0002`IE\n\"a\u0007\u0010\u0011\u0005)a\u0012BA\u000f\f\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aE\u0010\n\u0005\u0001\u001a!!C#yi\u0016t7/[8o\u0001")
public interface ExtensionIdProvider {
    public ExtensionId<? extends Extension> lookup();
}

