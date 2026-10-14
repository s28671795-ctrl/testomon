/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.NoSerializationVerificationNeeded;
import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005-2\u0001\u0002B\u0003\u0011\u0002G\u0005Q!\u0003\u0005\u0006-\u00011\t\u0001\u0007\u0005\u0006C\u00011\tA\t\u0005\u0006I\u00011\t!\n\u0002\u0014\u0007\"\fgN\\3m%\u0016<\u0017n\u001d;sCRLwN\u001c\u0006\u0003\r\u001d\t!![8\u000b\u0003!\tA!Y6lCN\u0019\u0001A\u0003\t\u0011\u0005-qQ\"\u0001\u0007\u000b\u00035\tQa]2bY\u0006L!a\u0004\u0007\u0003\r\u0005s\u0017PU3g!\t\tB#D\u0001\u0013\u0015\t\u0019r!A\u0003bGR|'/\u0003\u0002\u0016%\t\tcj\\*fe&\fG.\u001b>bi&|gNV3sS\u001aL7-\u0019;j_:tU-\u001a3fI\u0006qQM\\1cY\u0016Le\u000e^3sKN$8\u0001\u0001\u000b\u00033q\u0001\"a\u0003\u000e\n\u0005ma!\u0001B+oSRDQ!H\u0001A\u0002y\t!a\u001c9\u0011\u0005-y\u0012B\u0001\u0011\r\u0005\rIe\u000e^\u0001\u0010I&\u001c\u0018M\u00197f\u0013:$XM]3tiR\u0011\u0011d\t\u0005\u0006;\t\u0001\rAH\u0001\u000fG\u0006t7-\u001a7B]\u0012\u001cEn\\:f)\tIb\u0005C\u0003(\u0007\u0001\u0007\u0001&A\u0004b]\u0012$\u0006.\u001a8\u0011\u0007-I\u0013$\u0003\u0002+\u0019\tIa)\u001e8di&|g\u000e\r")
public interface ChannelRegistration
extends NoSerializationVerificationNeeded {
    public void enableInterest(int var1);

    public void disableInterest(int var1);

    public void cancelAndClose(Function0<BoxedUnit> var1);
}

