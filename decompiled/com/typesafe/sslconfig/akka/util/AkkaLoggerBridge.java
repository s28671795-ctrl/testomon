/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.akka.util;

import akka.event.EventStream;
import akka.event.Logging;
import akka.event.Logging$Error$;
import com.typesafe.sslconfig.util.NoDepsLogger;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m4A\u0001D\u0007\u00011!Aa\u0004\u0001B\u0001B\u0003%q\u0004\u0003\u0005'\u0001\t\u0005\t\u0015!\u0003(\u0011!!\u0004A!A!\u0002\u0013)\u0004\"B#\u0001\t\u00031\u0005\"B#\u0001\t\u0003\u0001\u0006\"B-\u0001\t\u0003R\u0006\"\u00020\u0001\t\u0003z\u0006\"B3\u0001\t\u00032\u0007\"\u00025\u0001\t\u0003J\u0007\"B6\u0001\t\u0003b\u0007\"B6\u0001\t\u0003r'\u0001E!lW\u0006dunZ4fe\n\u0013\u0018\u000eZ4f\u0015\tqq\"\u0001\u0003vi&d'B\u0001\t\u0012\u0003\u0011\t7n[1\u000b\u0005I\u0019\u0012!C:tY\u000e|gNZ5h\u0015\t!R#\u0001\u0005usB,7/\u00194f\u0015\u00051\u0012aA2p[\u000e\u00011C\u0001\u0001\u001a!\tQB$D\u0001\u001c\u0015\tq\u0011#\u0003\u0002\u001e7\taaj\u001c#faNdunZ4fe\u0006\u0019!-^:\u0011\u0005\u0001\"S\"A\u0011\u000b\u0005\t\u001a\u0013!B3wK:$(\"\u0001\t\n\u0005\u0015\n#aC#wK:$8\u000b\u001e:fC6\f\u0011\u0002\\8h'>,(oY3\u0011\u0005!\ndBA\u00150!\tQS&D\u0001,\u0015\tas#\u0001\u0004=e>|GO\u0010\u0006\u0002]\u0005)1oY1mC&\u0011\u0001'L\u0001\u0007!J,G-\u001a4\n\u0005I\u001a$AB*ue&twM\u0003\u00021[\u0005AAn\\4DY\u0006\u001c8\u000f\r\u00027wA\u0019\u0001fN\u001d\n\u0005a\u001a$!B\"mCN\u001c\bC\u0001\u001e<\u0019\u0001!\u0011\u0002P\u0002\u0002\u0002\u0003\u0005)\u0011A\u001f\u0003\u0007}##'\u0005\u0002?\u0005B\u0011q\bQ\u0007\u0002[%\u0011\u0011)\f\u0002\b\u001d>$\b.\u001b8h!\ty4)\u0003\u0002E[\t\u0019\u0011I\\=\u0002\rqJg.\u001b;?)\u00119\u0015JS&\u0011\u0005!\u0003Q\"A\u0007\t\u000by!\u0001\u0019A\u0010\t\u000b\u0019\"\u0001\u0019A\u0014\t\u000bQ\"\u0001\u0019\u0001'1\u00055{\u0005c\u0001\u00158\u001dB\u0011!h\u0014\u0003\ny-\u000b\t\u0011!A\u0003\u0002u\"2aR)S\u0011\u0015qR\u00011\u0001 \u0011\u0015\u0019V\u00011\u0001U\u0003\u0015\u0019G.\u0019>{a\t)v\u000bE\u0002)oY\u0003\"AO,\u0005\u0013a\u0013\u0016\u0011!A\u0001\u0006\u0003i$aA0%g\u0005q\u0011n\u001d#fEV<WI\\1cY\u0016$W#A.\u0011\u0005}b\u0016BA/.\u0005\u001d\u0011un\u001c7fC:\fQ\u0001Z3ck\u001e$\"\u0001Y2\u0011\u0005}\n\u0017B\u00012.\u0005\u0011)f.\u001b;\t\u000b\u0011<\u0001\u0019A\u0014\u0002\u00075\u001cx-\u0001\u0003j]\u001a|GC\u00011h\u0011\u0015!\u0007\u00021\u0001(\u0003\u00119\u0018M\u001d8\u0015\u0005\u0001T\u0007\"\u00023\n\u0001\u00049\u0013!B3se>\u0014HC\u00011n\u0011\u0015!'\u00021\u0001()\r\u0001w\u000e\u001d\u0005\u0006I.\u0001\ra\n\u0005\u0006c.\u0001\rA]\u0001\ni\"\u0014xn^1cY\u0016\u0004\"a\u001d=\u000f\u0005Q4hB\u0001\u0016v\u0013\u0005q\u0013BA<.\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u001f>\u0003\u0013QC'o\\<bE2,'BA<.\u0001")
public class AkkaLoggerBridge
extends NoDepsLogger {
    private final EventStream bus;
    private final String logSource;
    private final Class<?> logClass;

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String msg) {
        this.bus.publish(new Logging.Debug(this.logSource, this.logClass, msg));
    }

    @Override
    public void info(String msg) {
        this.bus.publish(new Logging.Info(this.logSource, this.logClass, msg));
    }

    @Override
    public void warn(String msg) {
        this.bus.publish(new Logging.Warning(this.logSource, this.logClass, msg));
    }

    @Override
    public void error(String msg) {
        this.bus.publish(Logging$Error$.MODULE$.apply(this.logSource, this.logClass, msg));
    }

    @Override
    public void error(String msg, Throwable throwable) {
        this.bus.publish(Logging$Error$.MODULE$.apply(this.logSource, this.logClass, msg));
    }

    public AkkaLoggerBridge(EventStream bus, String logSource, Class<?> logClass) {
        this.bus = bus;
        this.logSource = logSource;
        this.logClass = logClass;
    }

    public AkkaLoggerBridge(EventStream bus, Class<?> clazz) {
        this(bus, clazz.getCanonicalName(), clazz);
    }
}

