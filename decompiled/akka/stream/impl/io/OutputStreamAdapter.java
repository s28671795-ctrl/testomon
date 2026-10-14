/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.stream.impl.io.OutputStreamSourceStage;
import akka.stream.impl.io.OutputStreamSourceStage$Close$;
import akka.stream.stage.AsyncCallback;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import scala.Option;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.concurrent.Await$;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Numeric$IntIsIntegral$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005y4QAC\u0006\u0001#MA\u0001b\u0007\u0001\u0003\u0002\u0003\u0006I!\b\u0005\tK\u0001\u0011\t\u0011)A\u0005M!Aa\b\u0001B\u0001B\u0003%q\bC\u0003I\u0001\u0011\u0005\u0011\n\u0003\u0004P\u0001\u0001&I\u0001\u0015\u0005\u0006I\u0002!\t%\u001a\u0005\u0006I\u0002!\t\u0005\u001c\u0005\u0006s\u0002!\tE\u001f\u0005\u0006y\u0002!\tE\u001f\u0002\u0014\u001fV$\b/\u001e;TiJ,\u0017-\\!eCB$XM\u001d\u0006\u0003\u00195\t!![8\u000b\u00059y\u0011\u0001B5na2T!\u0001E\t\u0002\rM$(/Z1n\u0015\u0005\u0011\u0012\u0001B1lW\u0006\u001c\"\u0001\u0001\u000b\u0011\u0005UIR\"\u0001\f\u000b\u000519\"\"\u0001\r\u0002\t)\fg/Y\u0005\u00035Y\u0011AbT;uaV$8\u000b\u001e:fC6\f\u0011#\u001e8gk24\u0017\u000e\u001c7fI\u0012+W.\u00198e\u0007\u0001\u0001\"AH\u0012\u000e\u0003}Q!\u0001I\u0011\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002#/\u0005!Q\u000f^5m\u0013\t!sDA\u0005TK6\f\u0007\u000f[8sK\u0006Y1/\u001a8e)>\u001cF/Y4f!\r9#\u0006L\u0007\u0002Q)\u0011\u0011fD\u0001\u0006gR\fw-Z\u0005\u0003W!\u0012Q\"Q:z]\u000e\u001c\u0015\r\u001c7cC\u000e\\\u0007CA\u0017<\u001d\tq\u0013H\u0004\u00020q9\u0011\u0001g\u000e\b\u0003cYr!AM\u001b\u000e\u0003MR!\u0001\u000e\u000f\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0012B\u0001\t\u0012\u0013\tqq\"\u0003\u0002\r\u001b%\u0011!hC\u0001\u0018\u001fV$\b/\u001e;TiJ,\u0017-\\*pkJ\u001cWm\u0015;bO\u0016L!\u0001P\u001f\u0003+\u0005#\u0017\r\u001d;feR{7\u000b^1hK6+7o]1hK*\u0011!hC\u0001\roJLG/\u001a+j[\u0016|W\u000f\u001e\t\u0003\u0001\u001ak\u0011!\u0011\u0006\u0003\u0005\u000e\u000b\u0001\u0002Z;sCRLwN\u001c\u0006\u0003A\u0011S\u0011!R\u0001\u0006g\u000e\fG.Y\u0005\u0003\u000f\u0006\u0013aBR5oSR,G)\u001e:bi&|g.\u0001\u0004=S:LGO\u0010\u000b\u0005\u00152ke\n\u0005\u0002L\u00015\t1\u0002C\u0003\u001c\t\u0001\u0007Q\u0004C\u0003&\t\u0001\u0007a\u0005C\u0003?\t\u0001\u0007q(\u0001\u0005tK:$G)\u0019;b)\t\tV\u000b\u0005\u0002S'6\tA)\u0003\u0002U\t\n!QK\\5u\u0011\u00151V\u00011\u0001X\u0003\u0011!\u0017\r^1\u0011\u0005aSV\"A-\u000b\u0005\t\n\u0012BA.Z\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\u0015\u0004\u000bu\u001b\u0007c\u0001*_A&\u0011q\f\u0012\u0002\u0007i\"\u0014xn^:\u0011\u0005U\t\u0017B\u00012\u0017\u0005-Iu*\u0012=dKB$\u0018n\u001c8$\u0003\u0001\fQa\u001e:ji\u0016$\"!\u00154\t\u000b\u001d4\u0001\u0019\u00015\u0002\u0003\t\u0004\"AU5\n\u0005)$%aA%oi\"\u001aa!X2\u0015\tEkGO\u001e\u0005\u0006O\u001e\u0001\rA\u001c\t\u0004%>\f\u0018B\u00019E\u0005\u0015\t%O]1z!\t\u0011&/\u0003\u0002t\t\n!!)\u001f;f\u0011\u0015)x\u00011\u0001i\u0003\rygM\u001a\u0005\u0006o\u001e\u0001\r\u0001[\u0001\u0004Y\u0016t\u0007fA\u0004^G\u0006)a\r\\;tQR\t\u0011\u000bK\u0002\t;\u000e\fQa\u00197pg\u0016D3!C/d\u0001")
public class OutputStreamAdapter
extends OutputStream {
    private final Semaphore unfulfilledDemand;
    private final AsyncCallback<OutputStreamSourceStage.AdapterToStageMessage> sendToStage;
    private final FiniteDuration writeTimeout;

    private void sendData(ByteString data) throws IOException {
        if (!this.unfulfilledDemand.tryAcquire(this.writeTimeout.toMillis(), TimeUnit.MILLISECONDS)) {
            throw new IOException("Timed out trying to write data to stream");
        }
        try {
            Await$.MODULE$.result(this.sendToStage.invokeWithFeedback(new OutputStreamSourceStage.Send(data)), this.writeTimeout);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                throw new IOException(e);
            }
            throw throwable;
        }
    }

    @Override
    public void write(int b) throws IOException {
        this.sendData(ByteString$.MODULE$.apply(ScalaRunTime$.MODULE$.wrapIntArray(new int[]{b}), Numeric$IntIsIntegral$.MODULE$));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (ArrayOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.byteArrayOps(b))) {
            this.sendData(ByteString$.MODULE$.fromArray(b, off, len));
            return;
        }
    }

    @Override
    public void flush() throws IOException {
    }

    @Override
    public void close() throws IOException {
        try {
            Await$.MODULE$.result(this.sendToStage.invokeWithFeedback(OutputStreamSourceStage$Close$.MODULE$), this.writeTimeout);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                throw new IOException(e);
            }
            throw throwable;
        }
    }

    public OutputStreamAdapter(Semaphore unfulfilledDemand, AsyncCallback<OutputStreamSourceStage.AdapterToStageMessage> sendToStage, FiniteDuration writeTimeout) {
        this.unfulfilledDemand = unfulfilledDemand;
        this.sendToStage = sendToStage;
        this.writeTimeout = writeTimeout;
    }
}

