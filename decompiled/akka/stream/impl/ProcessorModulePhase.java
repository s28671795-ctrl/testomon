/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.ProcessorModule;
import akka.stream.impl.StreamLayout;
import org.reactivestreams.Processor;
import org.reactivestreams.Publisher;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-a!\u0002\u0006\f\u0005=\t\u0002\"B\u0014\u0001\t\u0003I\u0003\"B\u0016\u0001\t\u0003b\u0003\"\u0003\u001d\u0001\u0001\u0004\u0005\t\u0015)\u0003\u001d\u0011\u0015I\u0004\u0001\"\u0011;\u0011\u00151\u0006\u0001\"\u0011X\u0011\u00151\u0006\u0001\"\u0011h\u0011\u0015y\u0007\u0001\"\u0011q\u0011\u00151\b\u0001\"\u0011x\u0011\u0015a\b\u0001\"\u0011~\u0005Q\u0001&o\\2fgN|'/T8ek2,\u0007\u000b[1tK*\u0011A\"D\u0001\u0005S6\u0004HN\u0003\u0002\u000f\u001f\u000511\u000f\u001e:fC6T\u0011\u0001E\u0001\u0005C.\\\u0017mE\u0002\u0001%a\u0001\"a\u0005\f\u000e\u0003QQ\u0011!F\u0001\u0006g\u000e\fG.Y\u0005\u0003/Q\u0011a!\u00118z%\u00164\u0007cA\r\u001b95\t1\"\u0003\u0002\u001c\u0017\tY\u0001\u000b[1tK&\u001bH.\u00198e!\u0011i\"\u0005\n\u0013\u000e\u0003yQ!a\b\u0011\u0002\u001fI,\u0017m\u0019;jm\u0016\u001cHO]3b[NT\u0011!I\u0001\u0004_J<\u0017BA\u0012\u001f\u0005%\u0001&o\\2fgN|'\u000f\u0005\u0002\u0014K%\u0011a\u0005\u0006\u0002\u0004\u0003:L\u0018A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003)\u0002\"!\u0007\u0001\u0002\t9\fW.Z\u000b\u0002[A\u0011a&\u000e\b\u0003_M\u0002\"\u0001\r\u000b\u000e\u0003ER!A\r\u0015\u0002\rq\u0012xn\u001c;?\u0013\t!D#\u0001\u0004Qe\u0016$WMZ\u0005\u0003m]\u0012aa\u0015;sS:<'B\u0001\u001b\u0015\u0003%\u0001(o\\2fgN|'/A\tnCR,'/[1mSj,\u0017\t^8nS\u000e$2a\u000f R!\u0011\u0019B\b\b\u0013\n\u0005u\"\"A\u0002+va2,'\u0007C\u0003@\t\u0001\u0007\u0001)A\u0002n_\u0012\u0004B!\u0011&NI9\u0011!\t\u0013\b\u0003\u0007\u001es!\u0001\u0012$\u000f\u0005A*\u0015\"\u0001\t\n\u00059y\u0011B\u0001\u0007\u000e\u0013\tI5\"\u0001\u0007TiJ,\u0017-\u001c'bs>,H/\u0003\u0002L\u0019\na\u0011\t^8nS\u000elu\u000eZ;mK*\u0011\u0011j\u0003\t\u0003\u001d>k\u0011!D\u0005\u0003!6\u0011Qa\u00155ba\u0016DQA\u0015\u0003A\u0002M\u000b!\"\u0019;ue&\u0014W\u000f^3t!\tqE+\u0003\u0002V\u001b\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0015\u0005\u001c8/[4o!>\u0014H\u000f\u0006\u0003Y7\u0002,\u0007CA\nZ\u0013\tQFC\u0001\u0003V]&$\b\"\u0002/\u0006\u0001\u0004i\u0016AA5o!\tqe,\u0003\u0002`\u001b\t1\u0011J\u001c)peRDQ!Y\u0003A\u0002\t\fAa\u001d7piB\u00111cY\u0005\u0003IR\u00111!\u00138u\u0011\u00151W\u00011\u0001\u001d\u0003\u0015awnZ5d)\u0011A\u0006.\u001c8\t\u000b%4\u0001\u0019\u00016\u0002\u0007=,H\u000f\u0005\u0002OW&\u0011A.\u0004\u0002\b\u001fV$\bk\u001c:u\u0011\u0015\tg\u00011\u0001c\u0011\u00151g\u00011\u0001\u001d\u0003=\u0019'/Z1uKB+(\r\\5tQ\u0016\u0014HcA9ukB\u0019QD\u001d\u0013\n\u0005Mt\"!\u0003)vE2L7\u000f[3s\u0011\u0015Iw\u00011\u0001k\u0011\u00151w\u00011\u0001\u001d\u00035!\u0018m[3Qk\nd\u0017n\u001d5feR!\u0001\f_=|\u0011\u0015\t\u0007\u00021\u0001c\u0011\u0015Q\b\u00021\u0001r\u0003%\u0001XO\u00197jg\",'\u000fC\u0003S\u0011\u0001\u00071+A\u0007p]&\u001bH.\u00198e%\u0016\fG-\u001f\u000b\u00021\"\u0012\u0001a \t\u0005\u0003\u0003\t9!\u0004\u0002\u0002\u0004)\u0019\u0011QA\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\n\u0005\r!aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class ProcessorModulePhase
implements PhaseIsland<Processor<Object, Object>> {
    private Processor<Object, Object> processor;

    @Override
    public String name() {
        return "ProcessorModulePhase";
    }

    @Override
    public Tuple2<Processor<Object, Object>, Object> materializeAtomic(StreamLayout.AtomicModule<Shape, Object> mod, Attributes attributes) {
        Tuple2<Processor<Object, Object>, Object> procAndMat = ((ProcessorModule)mod).createProcessor().apply();
        this.processor = procAndMat._1();
        return procAndMat;
    }

    @Override
    public void assignPort(InPort in, int slot, Processor<Object, Object> logic) {
    }

    @Override
    public void assignPort(OutPort out, int slot, Processor<Object, Object> logic) {
    }

    @Override
    public Publisher<Object> createPublisher(OutPort out, Processor<Object, Object> logic) {
        return logic;
    }

    @Override
    public void takePublisher(int slot, Publisher<Object> publisher, Attributes attributes) {
        publisher.subscribe(this.processor);
    }

    @Override
    public void onIslandReady() {
    }
}

