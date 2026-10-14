/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.annotation.DoNotInherit;
import akka.event.LogMarker$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.Map;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}a\u0001B\u000f\u001f\u0001\rB\u0001B\u000b\u0001\u0003\u0006\u0004%\ta\u000b\u0005\to\u0001\u0011\t\u0011)A\u0005Y!A\u0001\b\u0001BC\u0002\u0013\u0005\u0011\b\u0003\u0005A\u0001\t\u0005\t\u0015!\u0003;\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u0015\t\u0005\u0001\"\u0001H\u0011\u0015I\u0005\u0001\"\u0001K\u0011\u0015A\u0006\u0001\"\u0011Z\u000f\u0015\tg\u0004#\u0001c\r\u0015ib\u0004#\u0001d\u0011\u0015\t%\u0002\"\u0001e\u0011!)'B1A\u0005\u0006\u00012\u0007B\u00026\u000bA\u00035q\rC\u0003l\u0015\u0011\u0005A\u000eC\u0003l\u0015\u0011\u0005a\u000eC\u0003r\u0015\u0011\u0005!\u000fC\u0003r\u0015\u0011\u0005A\u000f\u0003\u0005y\u0015\t\u0007IQ\u0001\u0011z\u0011\u0019Q(\u0002)A\u0007\u0007\u001e11P\u0003E\u0001Aq4aA \u0006\t\u0002\u0001z\bBB!\u0016\t\u0003\t\t\u0001C\u0005\u0002\u0004U\u0011\r\u0011\"\u0001\u0002\u0006!A\u00111B\u000b!\u0002\u0013\t9\u0001C\u0005\u0002\u000eU\u0011\r\u0011\"\u0001\u0002\u0006!A\u0011qB\u000b!\u0002\u0013\t9\u0001C\u0005\u0002\u0012U\u0011\r\u0011\"\u0001\u0002\u0006!A\u00111C\u000b!\u0002\u0013\t9AA\u0005M_\u001el\u0015M]6fe*\u0011q\u0004I\u0001\u0006KZ,g\u000e\u001e\u0006\u0002C\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u0013\u0011\u0005\u0015BS\"\u0001\u0014\u000b\u0003\u001d\nQa]2bY\u0006L!!\u000b\u0014\u0003\r\u0005s\u0017PU3g\u0003\u0011q\u0017-\\3\u0016\u00031\u0002\"!\f\u001b\u000f\u00059\u0012\u0004CA\u0018'\u001b\u0005\u0001$BA\u0019#\u0003\u0019a$o\\8u}%\u00111GJ\u0001\u0007!J,G-\u001a4\n\u0005U2$AB*ue&twM\u0003\u00024M\u0005)a.Y7fA\u0005Q\u0001O]8qKJ$\u0018.Z:\u0016\u0003i\u0002B!L\u001e-{%\u0011AH\u000e\u0002\u0004\u001b\u0006\u0004\bCA\u0013?\u0013\tydEA\u0002B]f\f1\u0002\u001d:pa\u0016\u0014H/[3tA\u00051A(\u001b8jiz\"2aQ#G!\t!\u0005!D\u0001\u001f\u0011\u0015QS\u00011\u0001-\u0011\u0015AT\u00011\u0001;)\t\u0019\u0005\nC\u0003+\r\u0001\u0007A&A\u0007hKR\u0004&o\u001c9feRLWm]\u000b\u0002\u0017B!A*\u0015\u0017S\u001b\u0005i%B\u0001(P\u0003\u0011)H/\u001b7\u000b\u0003A\u000bAA[1wC&\u0011A(\u0014\t\u0003'Zk\u0011\u0001\u0016\u0006\u0003+>\u000bA\u0001\\1oO&\u0011q\u000b\u0016\u0002\u0007\u001f\nTWm\u0019;\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012\u0001\f\u0015\u0003\u0001m\u0003\"\u0001X0\u000e\u0003uS!A\u0018\u0011\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002a;\naAi\u001c(pi&s\u0007.\u001a:ji\u0006IAj\\4NCJ\\WM\u001d\t\u0003\t*\u0019\"A\u0003\u0013\u0015\u0003\t\fa!\u0014#D\u0017\u0016LX#A4\u0010\u0003!\f\u0013![\u0001\u0007[\u0006\u00148.\u001a:\u0002\u000f5#5iS3zA\u0005)\u0011\r\u001d9msR\u00111)\u001c\u0005\u0006U9\u0001\r\u0001\f\u000b\u0004\u0007>\u0004\b\"\u0002\u0016\u0010\u0001\u0004a\u0003\"\u0002\u001d\u0010\u0001\u0004Q\u0014AB2sK\u0006$X\r\u0006\u0002Dg\")!\u0006\u0005a\u0001YQ\u00191)\u001e<\t\u000b)\n\u0002\u0019\u0001\u0017\t\u000ba\n\u0002\u0019A<\u0011\t1\u000bF&P\u0001\t'\u0016\u001cWO]5usV\t1)A\u0005TK\u000e,(/\u001b;zA\u0005Q\u0001K]8qKJ$\u0018.Z:\u0011\u0005u,R\"\u0001\u0006\u0003\u0015A\u0013x\u000e]3si&,7o\u0005\u0002\u0016IQ\tA0\u0001\u0007NKN\u001c\u0018mZ3DY\u0006\u001c8/\u0006\u0002\u0002\bA\u00191+!\u0003\n\u0005U\"\u0016!D'fgN\fw-Z\"mCN\u001c\b%A\u0007SK6|G/Z!eIJ,7o]\u0001\u000f%\u0016lw\u000e^3BI\u0012\u0014Xm]:!\u0003A\u0011V-\\8uK\u0006#GM]3tgVKG-A\tSK6|G/Z!eIJ,7o]+jI\u0002B3!FA\f!\ra\u0016\u0011D\u0005\u0004\u00037i&aC%oi\u0016\u0014h.\u00197Ba&D3\u0001FA\f\u0001")
@DoNotInherit
public class LogMarker {
    private final String name;
    private final scala.collection.immutable.Map<String, Object> properties;

    public static LogMarker create(String name, java.util.Map<String, Object> properties) {
        return LogMarker$.MODULE$.create(name, properties);
    }

    public static LogMarker create(String name) {
        return LogMarker$.MODULE$.create(name);
    }

    public static LogMarker apply(String name, scala.collection.immutable.Map<String, Object> properties) {
        return LogMarker$.MODULE$.apply(name, properties);
    }

    public static LogMarker apply(String name) {
        return LogMarker$.MODULE$.apply(name);
    }

    public String name() {
        return this.name;
    }

    public scala.collection.immutable.Map<String, Object> properties() {
        return this.properties;
    }

    public java.util.Map<String, Object> getProperties() {
        return package$JavaConverters$.MODULE$.MapHasAsJava((Map)this.properties().map((Function1<Tuple2, Tuple2> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                String k = (String)tuple2._1();
                Object v = tuple2._2();
                return new Tuple2(k, v);
            }
            throw new MatchError(tuple2);
        })).asJava();
    }

    public String toString() {
        return new StringBuilder(12).append("LogMarker(").append(this.name()).append(",").append(this.properties()).append(")").toString();
    }

    public LogMarker(String name, scala.collection.immutable.Map<String, Object> properties) {
        this.name = name;
        this.properties = properties;
    }

    public LogMarker(String name) {
        this(name, (scala.collection.immutable.Map<String, Object>)Predef$.MODULE$.Map().empty());
    }
}

