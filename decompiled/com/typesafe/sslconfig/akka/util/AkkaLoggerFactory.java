/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.akka.util;

import akka.actor.ActorSystem;
import akka.event.DummyClassForStringSources;
import com.typesafe.sslconfig.akka.util.AkkaLoggerBridge;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000593A!\u0002\u0004\u0003#!AQ\u0004\u0001B\u0001B\u0003%a\u0004C\u0003&\u0001\u0011\u0005a\u0005C\u0003+\u0001\u0011\u00053\u0006C\u0003+\u0001\u0011\u0005\u0003JA\tBW.\fGj\\4hKJ4\u0015m\u0019;pefT!a\u0002\u0005\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u0013)\tA!Y6lC*\u00111\u0002D\u0001\ngNd7m\u001c8gS\u001eT!!\u0004\b\u0002\u0011QL\b/Z:bM\u0016T\u0011aD\u0001\u0004G>l7\u0001A\n\u0004\u0001IA\u0002CA\n\u0017\u001b\u0005!\"\"A\u000b\u0002\u000bM\u001c\u0017\r\\1\n\u0005]!\"AB!osJ+g\r\u0005\u0002\u001a75\t!D\u0003\u0002\b\u0015%\u0011AD\u0007\u0002\u000e\u0019><w-\u001a:GC\u000e$xN]=\u0002\rML8\u000f^3n!\ty2%D\u0001!\u0015\t\t#%A\u0003bGR|'OC\u0001\n\u0013\t!\u0003EA\u0006BGR|'oU=ti\u0016l\u0017A\u0002\u001fj]&$h\b\u0006\u0002(SA\u0011\u0001\u0006A\u0007\u0002\r!)QD\u0001a\u0001=\u0005)\u0011\r\u001d9msR\u0011Af\f\t\u000335J!A\f\u000e\u0003\u00199{G)\u001a9t\u0019><w-\u001a:\t\u000bA\u001a\u0001\u0019A\u0019\u0002\u000b\rd\u0017M\u001f>1\u0005Iz\u0004cA\u001a;{9\u0011A\u0007\u000f\t\u0003kQi\u0011A\u000e\u0006\u0003oA\ta\u0001\u0010:p_Rt\u0014BA\u001d\u0015\u0003\u0019\u0001&/\u001a3fM&\u00111\b\u0010\u0002\u0006\u00072\f7o\u001d\u0006\u0003sQ\u0001\"AP \r\u0001\u0011I\u0001iLA\u0001\u0002\u0003\u0015\t!\u0011\u0002\u0004?\u0012\n\u0014C\u0001\"F!\t\u00192)\u0003\u0002E)\t9aj\u001c;iS:<\u0007CA\nG\u0013\t9ECA\u0002B]f$\"\u0001L%\t\u000b)#\u0001\u0019A&\u0002\t9\fW.\u001a\t\u0003g1K!!\u0014\u001f\u0003\rM#(/\u001b8h\u0001")
public final class AkkaLoggerFactory
implements LoggerFactory {
    private final ActorSystem system;

    @Override
    public NoDepsLogger apply(Class<?> clazz) {
        return new AkkaLoggerBridge(this.system.eventStream(), clazz);
    }

    @Override
    public NoDepsLogger apply(String name) {
        return new AkkaLoggerBridge(this.system.eventStream(), name, DummyClassForStringSources.class);
    }

    public AkkaLoggerFactory(ActorSystem system) {
        this.system = system;
    }
}

