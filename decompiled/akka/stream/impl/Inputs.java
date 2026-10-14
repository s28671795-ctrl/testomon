/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.SubReceive;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00013\u0001b\u0003\u0007\u0011\u0002\u0007\u0005\u0001C\u0005\u0005\u00063\u0001!\ta\u0007\u0005\u0006?\u00011\t\u0001\t\u0005\u0006K\u00011\t\u0001\t\u0005\u0006M\u00011\ta\n\u0005\u0006W\u00011\t\u0001\f\u0005\u0006a\u00011\ta\u0007\u0005\u0006c\u00011\tA\r\u0005\u0006m\u0001!\tA\r\u0005\u0006o\u00011\tA\r\u0005\u0006q\u00011\tA\r\u0002\u0007\u0013:\u0004X\u000f^:\u000b\u00055q\u0011\u0001B5na2T!a\u0004\t\u0002\rM$(/Z1n\u0015\u0005\t\u0012\u0001B1lW\u0006\u001c\"\u0001A\n\u0011\u0005Q9R\"A\u000b\u000b\u0003Y\tQa]2bY\u0006L!\u0001G\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uI\r\u0001A#\u0001\u000f\u0011\u0005Qi\u0012B\u0001\u0010\u0016\u0005\u0011)f.\u001b;\u0002\u00159+W\rZ:J]B,H/F\u0001\"!\t\u00113%D\u0001\r\u0013\t!CBA\u0007Ue\u0006t7OZ3s'R\fG/Z\u0001\u0015\u001d\u0016,Gm]%oaV$xJ]\"p[BdW\r^3\u0002'\u0011,\u0017/^3vK&s\u0007/\u001e;FY\u0016lWM\u001c;\u0015\u0003!\u0002\"\u0001F\u0015\n\u0005)*\"aA!os\u0006Q1/\u001e2sK\u000e,\u0017N^3\u0016\u00035\u0002\"A\t\u0018\n\u0005=b!AC*vEJ+7-Z5wK\u000611-\u00198dK2\f\u0001\"[:DY>\u001cX\rZ\u000b\u0002gA\u0011A\u0003N\u0005\u0003kU\u0011qAQ8pY\u0016\fg.\u0001\u0004jg>\u0003XM\\\u0001\u000fS:\u0004X\u000f^:EKBdW\r^3e\u0003=Ig\u000e];ug\u00063\u0018-\u001b7bE2,\u0007F\u0001\u0001;!\tYd(D\u0001=\u0015\ti\u0004#\u0001\u0006b]:|G/\u0019;j_:L!a\u0010\u001f\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public interface Inputs {
    public TransferState NeedsInput();

    public TransferState NeedsInputOrComplete();

    public Object dequeueInputElement();

    public SubReceive subreceive();

    public void cancel();

    public boolean isClosed();

    public static /* synthetic */ boolean isOpen$(Inputs $this) {
        return $this.isOpen();
    }

    default public boolean isOpen() {
        return !this.isClosed();
    }

    public boolean inputsDepleted();

    public boolean inputsAvailable();

    public static void $init$(Inputs $this) {
    }
}

