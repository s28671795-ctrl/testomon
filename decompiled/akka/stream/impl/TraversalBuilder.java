/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.DoNotInherit;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.Shape;
import akka.stream.SourceShape;
import akka.stream.impl.GraphStageTag$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.impl.fusing.GraphStages;
import scala.Function1;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\t5sAB\u0012%\u0011\u0003A#F\u0002\u0004-I!\u0005\u0001&\f\u0005\u0006i\u0005!\tAN\u0003\u0005o\u0005\u0001\u0001(\u0002\u0003B\u0003\u0001\u0011\u0005bB#\u0002\u0005\u0004%IA\u0012\u0005\u0007\u0015\u0006\u0001\u000b\u0011B$\t\r-\u000bA\u0011\u0001\u0013M\u0011\u0019i\u0016\u0001\"\u0001)=\"Q\u00111R\u0001\u0012\u0002\u0013\u0005\u0001&!$\t\u0011\u0005\u0005\u0016\u0001\"\u0001)\u0003GC\u0001\"a/\u0002\t\u0003!\u0013Q\u0018\u0005\u000b\u0003\u0013\f\u0011\u0013!C\u0001I\u0005-\u0007\u0002CAh\u0003\u0011\u0005A%!5\t\u0015\u0005m\u0017!%A\u0005\u0002\u0011\nY\rC\u0004\u0002^\u0006!\t!a8\t\u000f\t=\u0012\u0001\"\u0001\u00032\u0019AA\u0006\nI\u0001\u0004CA\u0003\rC\u0003b#\u0011\u0005!\rC\u0003d#\u0019\u0005A\rC\u0003v#\u0019\u0005a\u000fC\u0003{#\u0019E1\u0010C\u0004\u0002\u0004E!\t!!\u0002\t\ru\fb\u0011AA\u0005\u0011\u001d\tY!\u0005D\u0001\u0003\u001bAq!a\t\u0012\r\u0003\t)\u0003C\u0004\u00020E1\t!!\r\t\u000f\u0005=\u0012C\"\u0001\u0002<!9\u0011qH\t\u0007\u0002\u0005\u0005\u0003bBA##\u0019\u0005\u0011q\t\u0005\b\u0003\u001f\nb\u0011AA)\u0011\u001d\t\u0019&\u0005D\u0001\u0003+Bq!a\u0016\u0012\t\u0003\tI\u0006C\u0004\u0002bE1\t!!\u0016\t\u000f\u0005\r\u0014C\"\u0001\u0002f\u0005\u0001BK]1wKJ\u001c\u0018\r\u001c\"vS2$WM\u001d\u0006\u0003K\u0019\nA![7qY*\u0011q\u0005K\u0001\u0007gR\u0014X-Y7\u000b\u0003%\nA!Y6lCB\u00111&A\u0007\u0002I\t\u0001BK]1wKJ\u001c\u0018\r\u001c\"vS2$WM]\n\u0003\u00039\u0002\"a\f\u001a\u000e\u0003AR\u0011!M\u0001\u0006g\u000e\fG.Y\u0005\u0003gA\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0003)\u0012A\"\u00118z\rVt7\r^5p]F\u0002BaL\u001d<}%\u0011!\b\r\u0002\n\rVt7\r^5p]F\u0002\"a\f\u001f\n\u0005u\u0002$a\u0002(pi\"Lgn\u001a\t\u0003_}J!\u0001\u0011\u0019\u0003\u0007\u0005s\u0017P\u0001\u0007B]f4UO\\2uS>t'\u0007E\u00030\u0007nZd(\u0003\u0002Ea\tIa)\u001e8di&|gNM\u0001\u0015G\u0006\u001c\u0007.\u001a3F[B$\u0018pQ8na2,G/\u001a3\u0016\u0003\u001d\u0003\"a\u000b%\n\u0005%##!G\"p[BdW\r^3e)J\fg/\u001a:tC2\u0014U/\u001b7eKJ\fQcY1dQ\u0016$W)\u001c9us\u000e{W\u000e\u001d7fi\u0016$\u0007%A\u0005j]&$8\u000b[1qKR\u0011Q\n\u0015\t\u0003_9K!a\u0014\u0019\u0003\tUs\u0017\u000e\u001e\u0005\u0006#\u001e\u0001\rAU\u0001\u0006g\"\f\u0007/\u001a\t\u0003'Rk\u0011AJ\u0005\u0003+\u001a\u0012Qa\u00155ba\u0016D#aB,\u0011\u0005a[V\"A-\u000b\u0005iC\u0013AC1o]>$\u0018\r^5p]&\u0011A,\u0017\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.A\u0003f[B$\u0018\u0010F\u0002`\u0003\u000f\u0003\"aK\t\u0014\u0005Eq\u0013A\u0002\u0013j]&$H\u0005F\u0001N\u0003\r\tG\r\u001a\u000b\u0005?\u0016<\u0007\u000eC\u0003g'\u0001\u0007q,A\u0005tk\nlw\u000eZ;mK\")\u0011k\u0005a\u0001%\")\u0011n\u0005a\u0001U\u0006Q1m\\7cS:,W*\u0019;\u0011\u0005-$aB\u00017\u0001\u001d\tiGO\u0004\u0002og:\u0011qN]\u0007\u0002a*\u0011\u0011/N\u0001\u0007yI|w\u000e\u001e \n\u0003%J!a\n\u0015\n\u0005\u00152\u0013\u0001\u0004;sC:\u001chm\u001c:n\u001b\u0006$HCA0x\u0011\u0015AH\u00031\u0001z\u0003\u00051\u0007CA6\u0004\u0003UIg\u000e^3s]\u0006d7+\u001a;BiR\u0014\u0018NY;uKN$\"a\u0018?\t\u000bu,\u0002\u0019\u0001@\u0002\u0015\u0005$HO]5ckR,7\u000f\u0005\u0002T\u007f&\u0019\u0011\u0011\u0001\u0014\u0003\u0015\u0005#HO]5ckR,7/A\u0007tKR\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0004?\u0006\u001d\u0001\"B?\u0017\u0001\u0004qX#\u0001@\u0002\t]L'/\u001a\u000b\u0006?\u0006=\u0011\u0011\u0004\u0005\b\u0003#A\u0002\u0019AA\n\u0003\ryW\u000f\u001e\t\u0004'\u0006U\u0011bAA\fM\t9q*\u001e;Q_J$\bbBA\u000e1\u0001\u0007\u0011QD\u0001\u0003S:\u00042aUA\u0010\u0013\r\t\tC\n\u0002\u0007\u0013:\u0004vN\u001d;\u0002\u001d=4gm]3u\u001f\u001alu\u000eZ;mKR!\u0011qEA\u0017!\ry\u0013\u0011F\u0005\u0004\u0003W\u0001$aA%oi\"9\u0011\u0011C\rA\u0002\u0005M\u0011!C5t+:<\u0018N]3e)\u0011\t\u0019$!\u000f\u0011\u0007=\n)$C\u0002\u00028A\u0012qAQ8pY\u0016\fg\u000eC\u0004\u0002\u0012i\u0001\r!a\u0005\u0015\t\u0005M\u0012Q\b\u0005\b\u00037Y\u0002\u0019AA\u000f\u0003!ygMZ:fi>3G\u0003BA\u0014\u0003\u0007Bq!a\u0007\u001d\u0001\u0004\ti\"\u0001\u0004bgNLwM\u001c\u000b\u0006?\u0006%\u00131\n\u0005\b\u0003#i\u0002\u0019AA\n\u0011\u001d\ti%\ba\u0001\u0003O\tAB]3mCRLg/Z*m_R\f1#[:Ue\u00064XM]:bY\u000e{W\u000e\u001d7fi\u0016,\"!a\r\u0002\u000f%t7\u000b\\8ugV\u0011\u0011qE\u0001\niJ\fg/\u001a:tC2,\"!a\u0017\u0011\u0007-\ni&C\u0002\u0002`\u0011\u0012\u0011\u0002\u0016:bm\u0016\u00148/\u00197\u0002\u0017Utw/\u001b:fI>+Ho]\u0001\u000b[\u0006\\W-S:mC:$GcA0\u0002h!9\u0011\u0011\u000e\u0012A\u0002\u0005-\u0014!C5tY\u0006tG\rV1h!\rY\u0013QN\u0005\u0004\u0003_\"#!C%tY\u0006tG\rV1hS!\t\u00121\u000f%\u0002x\u0005m\u0014bAA;I\t1\u0012\t^8nS\u000e$&/\u0019<feN\fGNQ;jY\u0012,'/C\u0002\u0002z\u0011\u0012\u0011dQ8na>\u001c\u0018\u000e^3Ue\u00064XM]:bY\n+\u0018\u000e\u001c3fe&\u0019\u0011Q\u0010\u0013\u0003-1Kg.Z1s)J\fg/\u001a:tC2\u0014U/\u001b7eKJD3!EAA!\rA\u00161Q\u0005\u0004\u0003\u000bK&\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\bbB?\t!\u0003\u0005\rA \u0015\u0003\u0011]\u000bq\"Z7qif$C-\u001a4bk2$H%M\u000b\u0003\u0003\u001fS3A`AIW\t\t\u0019\n\u0005\u0003\u0002\u0016\u0006uUBAAL\u0015\u0011\tI*a'\u0002\u0013Ut7\r[3dW\u0016$'B\u0001.1\u0013\u0011\ty*a&\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0004bi>l\u0017n\u0019\u000b\u0006?\u0006\u0015\u0016q\u0017\u0005\b\u0003OS\u0001\u0019AAU\u0003\u0019iw\u000eZ;mKB1\u00111VAY%zr1\u0001\\AW\u0013\r\ty\u000bJ\u0001\r'R\u0014X-Y7MCf|W\u000f^\u0005\u0005\u0003g\u000b)L\u0001\u0007Bi>l\u0017nY'pIVdWMC\u0002\u00020\u0012BQ! \u0006A\u0002yD#AC,\u0002\u001dA\u0014\u0018N\u001c;Ue\u00064XM]:bYR)Q*a0\u0002D\"9\u0011\u0011Y\u0006A\u0002\u0005m\u0013!\u0001;\t\u0013\u0005\u00157\u0002%AA\u0002\u0005\u001d\u0012AB5oI\u0016tG\u000f\u000b\u0002\f/\u0006A\u0002O]5oiR\u0013\u0018M^3sg\u0006dG\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u00055'\u0006BA\u0014\u0003#\u000b1\u0002\u001d:j]R<\u0016N]5oOR1\u0011qEAj\u0003+Dq!!1\u000e\u0001\u0004\tY\u0006C\u0005\u0002X6\u0001\n\u00111\u0001\u0002(\u0005A!-Y:f'2|G\u000f\u000b\u0002\u000e/\u0006)\u0002O]5oi^K'/\u001b8hI\u0011,g-Y;mi\u0012\u0012\u0014aD4fiNKgn\u001a7f'>,(oY3\u0016\t\u0005\u0005(\u0011\u0002\u000b\u0005\u0003G\u0014)\u0002\u0005\u0004\u0002f\u0006-\u0018q^\u0007\u0003\u0003OT1!!;)\u0003\u0011)H/\u001b7\n\t\u00055\u0018q\u001d\u0002\n\u001fB$\u0018n\u001c8WC2\u0004b!!=\u0002\u0000\n\u0015a\u0002BAz\u0003st1\u0001\\A{\u0013\r\t9\u0010J\u0001\u0007MV\u001c\u0018N\\4\n\t\u0005m\u0018Q`\u0001\f\u000fJ\f\u0007\u000f[*uC\u001e,7OC\u0002\u0002x\u0012JAA!\u0001\u0003\u0004\ta1+\u001b8hY\u0016\u001cv.\u001e:dK*!\u00111`A\u007f!\u0011\u00119A!\u0003\r\u0001\u00119!1B\bC\u0002\t5!!A!\u0012\u0007\t=a\bE\u00020\u0005#I1Aa\u00051\u0005\u0011qU\u000f\u001c7\t\u000f\t]q\u00021\u0001\u0003\u001a\u0005)qM]1qQB\"!1\u0004B\u0015!\u001d\u0019&Q\u0004B\u0011\u0005OI1Aa\b'\u0005\u00159%/\u00199i!\u0015\u0019&1\u0005B\u0003\u0013\r\u0011)C\n\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0003\u0003\b\t%B\u0001\u0004B\u0016\u0005+\t\t\u0011!A\u0003\u0002\t5\"aA0%cE\u00111HP\u0001\u000eSN,U\u000e\u001d;z'>,(oY3\u0015\t\u0005M\"1\u0007\u0005\b\u0005/\u0001\u0002\u0019\u0001B\u001ba\u0011\u00119D!\u0012\u0011\u000fM\u0013iB!\u000f\u0003DA\"!1\bB !\u0015\u0019&1\u0005B\u001f!\u0011\u00119Aa\u0010\u0005\u0019\t\u0005#1GA\u0001\u0002\u0003\u0015\tA!\f\u0003\u0007}##\u0007\u0005\u0003\u0003\b\t\u0015C\u0001\u0004B$\u0005g\t\t\u0011!A\u0003\u0002\t5\"aA0%g!\u0012\u0011a\u0016\u0015\u0003\u0001]\u0003")
@DoNotInherit
public interface TraversalBuilder {
    public static boolean isEmptySource(Graph<SourceShape<?>, ?> graph) {
        return TraversalBuilder$.MODULE$.isEmptySource(graph);
    }

    public static GraphStages.SingleSource getSingleSource(Graph graph) {
        return TraversalBuilder$.MODULE$.getSingleSource(graph);
    }

    public TraversalBuilder add(TraversalBuilder var1, Shape var2, Function2<Nothing$, Nothing$, Object> var3);

    public TraversalBuilder transformMat(Function1<Nothing$, Object> var1);

    public TraversalBuilder internalSetAttributes(Attributes var1);

    public static /* synthetic */ TraversalBuilder setAttributes$(TraversalBuilder $this, Attributes attributes) {
        return $this.setAttributes(attributes);
    }

    default public TraversalBuilder setAttributes(Attributes attributes) {
        if (attributes.isAsync()) {
            return this.makeIsland(GraphStageTag$.MODULE$).internalSetAttributes(attributes);
        }
        return this.internalSetAttributes(attributes);
    }

    public Attributes attributes();

    public TraversalBuilder wire(OutPort var1, InPort var2);

    public int offsetOfModule(OutPort var1);

    public boolean isUnwired(OutPort var1);

    public boolean isUnwired(InPort var1);

    public int offsetOf(InPort var1);

    public TraversalBuilder assign(OutPort var1, int var2);

    public boolean isTraversalComplete();

    public int inSlots();

    public static /* synthetic */ Traversal traversal$(TraversalBuilder $this) {
        return $this.traversal();
    }

    default public Traversal traversal() {
        throw new IllegalStateException("Traversal can be only acquired from a completed builder");
    }

    public int unwiredOuts();

    public TraversalBuilder makeIsland(IslandTag var1);

    public static void $init$(TraversalBuilder $this) {
    }
}

