/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.annotation.InternalApi;
import akka.event.ActorWithLogClass$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005-f!B\r\u001b\u0005rq\u0002\u0002C\u001b\u0001\u0005+\u0007I\u0011\u0001\u001c\t\u0011q\u0002!\u0011#Q\u0001\n]B\u0001\"\u0010\u0001\u0003\u0016\u0004%\tA\u0010\u0005\t\u0019\u0002\u0011\t\u0012)A\u0005\u007f!)A\u000b\u0001C\u0001+\"9a\fAA\u0001\n\u0003y\u0006b\u00022\u0001#\u0003%\ta\u0019\u0005\b]\u0002\t\n\u0011\"\u0001p\u0011\u001dY\b!!A\u0005BqD\u0011\"!\u0001\u0001\u0003\u0003%\t!a\u0001\t\u0013\u0005-\u0001!!A\u0005\u0002\u00055\u0001\"CA\n\u0001\u0005\u0005I\u0011IA\u000b\u0011%\t\u0019\u0003AA\u0001\n\u0003\t)\u0003C\u0005\u00020\u0001\t\t\u0011\"\u0011\u00022!I\u0011Q\u0007\u0001\u0002\u0002\u0013\u0005\u0013q\u0007\u0005\n\u0003s\u0001\u0011\u0011!C!\u0003wA\u0011\"!\u0010\u0001\u0003\u0003%\t%a\u0010\b\u0015\u0005=#$!A\t\u0002q\t\tFB\u0005\u001a5\u0005\u0005\t\u0012\u0001\u000f\u0002T!1Ak\u0005C\u0001\u0003gB\u0011\"!\u000f\u0014\u0003\u0003%)%a\u000f\t\u0013\u0005U4#!A\u0005\u0002\u0006]\u0004\"CAC'\u0005\u0005I\u0011QAD\u0011%\t\tkEA\u0001\n\u0013\t\u0019KA\tBGR|'oV5uQ2{wm\u00117bgNT!a\u0007\u000f\u0002\u000b\u00154XM\u001c;\u000b\u0003u\tA!Y6lCN!\u0001aH\u0013)!\t\u00013%D\u0001\"\u0015\u0005\u0011\u0013!B:dC2\f\u0017B\u0001\u0013\"\u0005\u0019\te.\u001f*fMB\u0011\u0001EJ\u0005\u0003O\u0005\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002*e9\u0011!\u0006\r\b\u0003W=j\u0011\u0001\f\u0006\u0003[9\na\u0001\u0010:p_Rt4\u0001A\u0005\u0002E%\u0011\u0011'I\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0019DG\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u00022C\u0005)\u0011m\u0019;peV\tq\u0007\u0005\u00029u5\t\u0011H\u0003\u000269%\u00111(\u000f\u0002\u0006\u0003\u000e$xN]\u0001\u0007C\u000e$xN\u001d\u0011\u0002\u00111|wm\u00117bgN,\u0012a\u0010\u0019\u0003\u0001*\u00032!Q#I\u001d\t\u00115\t\u0005\u0002,C%\u0011A)I\u0001\u0007!J,G-\u001a4\n\u0005\u0019;%!B\"mCN\u001c(B\u0001#\"!\tI%\n\u0004\u0001\u0005\u0013-#\u0011\u0011!A\u0001\u0006\u0003i%aA0%i\u0005IAn\\4DY\u0006\u001c8\u000fI\t\u0003\u001dF\u0003\"\u0001I(\n\u0005A\u000b#a\u0002(pi\"Lgn\u001a\t\u0003AIK!aU\u0011\u0003\u0007\u0005s\u00170\u0001\u0004=S:LGO\u0010\u000b\u0004-bK\u0006CA,\u0001\u001b\u0005Q\u0002\"B\u001b\u0006\u0001\u00049\u0004\"B\u001f\u0006\u0001\u0004Q\u0006GA.^!\r\tU\t\u0018\t\u0003\u0013v#\u0011bS-\u0002\u0002\u0003\u0005)\u0011A'\u0002\t\r|\u0007/\u001f\u000b\u0004-\u0002\f\u0007bB\u001b\u0007!\u0003\u0005\ra\u000e\u0005\b{\u0019\u0001\n\u00111\u0001[\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\u0012\u0001\u001a\u0016\u0003o\u0015\\\u0013A\u001a\t\u0003O2l\u0011\u0001\u001b\u0006\u0003S*\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005-\f\u0013AC1o]>$\u0018\r^5p]&\u0011Q\u000e\u001b\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0002aB\u0012\u0011O\u001f\u0016\u0003e\u0016\u00042a\u001d=z\u001b\u0005!(BA;w\u0003\u0011a\u0017M\\4\u000b\u0003]\fAA[1wC&\u0011a\t\u001e\t\u0003\u0013j$\u0011b\u0013\u0005\u0002\u0002\u0003\u0005)\u0011A'\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005i\bCA:\u007f\u0013\tyHO\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003\u000b\u00012\u0001IA\u0004\u0013\r\tI!\t\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0004#\u0006=\u0001\"CA\t\u0017\u0005\u0005\t\u0019AA\u0003\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0003\t\u0006\u00033\ty\"U\u0007\u0003\u00037Q1!!\b\"\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003C\tYB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA\u0014\u0003[\u00012\u0001IA\u0015\u0013\r\tY#\t\u0002\b\u0005>|G.Z1o\u0011!\t\t\"DA\u0001\u0002\u0004\t\u0016A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$2!`A\u001a\u0011%\t\tBDA\u0001\u0002\u0004\t)!\u0001\u0005iCND7i\u001c3f)\t\t)!\u0001\u0005u_N#(/\u001b8h)\u0005i\u0018AB3rk\u0006d7\u000f\u0006\u0003\u0002(\u0005\u0005\u0003\u0002CA\t#\u0005\u0005\t\u0019A))\u0007\u0001\t)\u0005\u0005\u0003\u0002H\u0005-SBAA%\u0015\tYG$\u0003\u0003\u0002N\u0005%#aC%oi\u0016\u0014h.\u00197Ba&\f\u0011#Q2u_J<\u0016\u000e\u001e5M_\u001e\u001cE.Y:t!\t96cE\u0003\u0014\u0003+\nI\u0007\u0005\u0005\u0002X\u0005us'!\u0019W\u001b\t\tIFC\u0002\u0002\\\u0005\nqA];oi&lW-\u0003\u0003\u0002`\u0005e#!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeA\"\u00111MA4!\u0011\tU)!\u001a\u0011\u0007%\u000b9\u0007B\u0005L'\u0005\u0005\t\u0011!B\u0001\u001bB!\u00111NA9\u001b\t\tiGC\u0002\u0002pY\f!![8\n\u0007M\ni\u0007\u0006\u0002\u0002R\u0005)\u0011\r\u001d9msR)a+!\u001f\u0002|!)QG\u0006a\u0001o!1QH\u0006a\u0001\u0003{\u0002D!a \u0002\u0004B!\u0011)RAA!\rI\u00151\u0011\u0003\u000b\u0017\u0006m\u0014\u0011!A\u0001\u0006\u0003i\u0015aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u0013\u000bi\nE\u0003!\u0003\u0017\u000by)C\u0002\u0002\u000e\u0006\u0012aa\u00149uS>t\u0007C\u0002\u0011\u0002\u0012^\n)*C\u0002\u0002\u0014\u0006\u0012a\u0001V;qY\u0016\u0014\u0004\u0007BAL\u00037\u0003B!Q#\u0002\u001aB\u0019\u0011*a'\u0005\u0013-;\u0012\u0011!A\u0001\u0006\u0003i\u0005\u0002CAP/\u0005\u0005\t\u0019\u0001,\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002&B\u00191/a*\n\u0007\u0005%FO\u0001\u0004PE*,7\r\u001e")
@InternalApi
public final class ActorWithLogClass
implements Product,
Serializable {
    private final Actor actor;
    private final Class<?> logClass;

    public static Option<Tuple2<Actor, Class<?>>> unapply(ActorWithLogClass x$0) {
        return ActorWithLogClass$.MODULE$.unapply(x$0);
    }

    public static ActorWithLogClass apply(Actor actor, Class<?> logClass) {
        return ActorWithLogClass$.MODULE$.apply(actor, logClass);
    }

    public static Function1<Tuple2<Actor, Class<?>>, ActorWithLogClass> tupled() {
        return ActorWithLogClass$.MODULE$.tupled();
    }

    public static Function1<Actor, Function1<Class<?>, ActorWithLogClass>> curried() {
        return ActorWithLogClass$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Actor actor() {
        return this.actor;
    }

    public Class<?> logClass() {
        return this.logClass;
    }

    public ActorWithLogClass copy(Actor actor, Class<?> logClass) {
        return new ActorWithLogClass(actor, logClass);
    }

    public Actor copy$default$1() {
        return this.actor();
    }

    public Class<?> copy$default$2() {
        return this.logClass();
    }

    @Override
    public String productPrefix() {
        return "ActorWithLogClass";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.actor();
            }
            case 1: {
                return this.logClass();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof ActorWithLogClass;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "actor";
            }
            case 1: {
                return "logClass";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof ActorWithLogClass)) return false;
        boolean bl = true;
        if (!bl) return false;
        ActorWithLogClass actorWithLogClass = (ActorWithLogClass)x$1;
        Actor actor = this.actor();
        Actor actor2 = actorWithLogClass.actor();
        if (actor == null) {
            if (actor2 != null) {
                return false;
            }
        } else if (!actor.equals(actor2)) return false;
        Class<?> clazz = this.logClass();
        Class<?> clazz2 = actorWithLogClass.logClass();
        if (clazz == null) {
            if (clazz2 == null) return true;
            return false;
        } else {
            if (!clazz.equals(clazz2)) return false;
            return true;
        }
    }

    public ActorWithLogClass(Actor actor, Class<?> logClass) {
        this.actor = actor;
        this.logClass = logClass;
        Product.$init$(this);
    }
}

