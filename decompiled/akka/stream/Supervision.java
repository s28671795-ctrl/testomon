/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.japi.function.Function;
import akka.stream.Supervision$;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005v!B\u001b7\u0011\u0003Yd!B\u001f7\u0011\u0003q\u0004\"B#\u0002\t\u00031eaB$\u0002!\u0003\r\n\u0003S\u0004\b\u0003s\t\u0001\u0012QA\u0018\r\u001d\tI#\u0001EA\u0003WAa!R\u0003\u0005\u0002\u00055\u0002bB0\u0006\u0003\u0003%\t\u0005\u0019\u0005\bS\u0016\t\t\u0011\"\u0001k\u0011!qW!!A\u0005\u0002\u0005E\u0002bB;\u0006\u0003\u0003%\tE\u001e\u0005\t{\u0016\t\t\u0011\"\u0001\u00026!I\u0011qA\u0003\u0002\u0002\u0013\u0005\u0013\u0011\u0002\u0005\n\u0003\u0017)\u0011\u0011!C!\u0003\u001bA\u0011\"a\u0004\u0006\u0003\u0003%I!!\u0005\t\u000f\u0005m\u0012\u0001\"\u0001\u0002>\u001d9\u0011qH\u0001\t\u0002\u0006}aaBA\r\u0003!\u0005\u00151\u0004\u0005\u0007\u000bF!\t!!\b\t\u000f}\u000b\u0012\u0011!C!A\"9\u0011.EA\u0001\n\u0003Q\u0007\u0002\u00038\u0012\u0003\u0003%\t!!\t\t\u000fU\f\u0012\u0011!C!m\"AQ0EA\u0001\n\u0003\t)\u0003C\u0005\u0002\bE\t\t\u0011\"\u0011\u0002\n!I\u00111B\t\u0002\u0002\u0013\u0005\u0013Q\u0002\u0005\n\u0003\u001f\t\u0012\u0011!C\u0005\u0003#Aq!!\u0011\u0002\t\u0003\t\u0019e\u0002\u0004\u0002F\u0005A\tI\u0018\u0004\u0006\u0015\u0006A\ti\u0013\u0005\u0006\u000bv!\t!\u0018\u0005\b?v\t\t\u0011\"\u0011a\u0011\u001dIW$!A\u0005\u0002)DqA\\\u000f\u0002\u0002\u0013\u0005q\u000eC\u0004v;\u0005\u0005I\u0011\t<\t\u000ful\u0012\u0011!C\u0001}\"I\u0011qA\u000f\u0002\u0002\u0013\u0005\u0013\u0011\u0002\u0005\n\u0003\u0017i\u0012\u0011!C!\u0003\u001bA\u0011\"a\u0004\u001e\u0003\u0003%I!!\u0005\t\u000f\u0005\u001d\u0013\u0001\"\u0001\u0002J\u00151\u00111J\u0001\u0001\u0003\u001bB\u0011\"a\u0019\u0002\u0005\u0004%\t!!\u001a\t\u0011\u0005u\u0014\u0001)A\u0005\u0003OB\u0011\"a \u0002\u0005\u0004%\t!!!\t\u0011\u0005\r\u0015\u0001)A\u0005\u0003_B\u0011\"!\"\u0002\u0005\u0004%\t!a\"\t\u0011\u00055\u0015\u0001)A\u0005\u0003\u0013C\u0011\"a$\u0002\u0005\u0004%\t!!!\t\u0011\u0005E\u0015\u0001)A\u0005\u0003_B\u0011\"a%\u0002\u0005\u0004%\t!!&\t\u0011\u0005m\u0015\u0001)A\u0005\u0003/C\u0011\"!(\u0002\u0005\u0004%\t!!!\t\u0011\u0005}\u0015\u0001)A\u0005\u0003_\n1bU;qKJ4\u0018n]5p]*\u0011q\u0007O\u0001\u0007gR\u0014X-Y7\u000b\u0003e\nA!Y6lC\u000e\u0001\u0001C\u0001\u001f\u0002\u001b\u00051$aC*va\u0016\u0014h/[:j_:\u001c\"!A \u0011\u0005\u0001\u001bU\"A!\u000b\u0003\t\u000bQa]2bY\u0006L!\u0001R!\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t1HA\u0005ESJ,7\r^5wKN\u00111aP\u0015\u0005\u0007u\tRAA\u0004SKN$\u0018M\u001d;\u0014\u000buyDJT)\u0011\u00055\u001bQ\"A\u0001\u0011\u0005\u0001{\u0015B\u0001)B\u0005\u001d\u0001&o\u001c3vGR\u0004\"A\u0015.\u000f\u0005MCfB\u0001+X\u001b\u0005)&B\u0001,;\u0003\u0019a$o\\8u}%\t!)\u0003\u0002Z\u0003\u00069\u0001/Y2lC\u001e,\u0017BA.]\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\tI\u0016\tF\u0001_!\tiU$A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0002CB\u0011!mZ\u0007\u0002G*\u0011A-Z\u0001\u0005Y\u0006twMC\u0001g\u0003\u0011Q\u0017M^1\n\u0005!\u001c'AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180F\u0001l!\t\u0001E.\u0003\u0002n\u0003\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0011\u0001o\u001d\t\u0003\u0001FL!A]!\u0003\u0007\u0005s\u0017\u0010C\u0004uC\u0005\u0005\t\u0019A6\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\u00059\bc\u0001=|a6\t\u0011P\u0003\u0002{\u0003\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005qL(\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2a`A\u0003!\r\u0001\u0015\u0011A\u0005\u0004\u0003\u0007\t%a\u0002\"p_2,\u0017M\u001c\u0005\bi\u000e\n\t\u00111\u0001q\u0003!A\u0017m\u001d5D_\u0012,G#A6\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!Y\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003'\u00012AYA\u000b\u0013\r\t9b\u0019\u0002\u0007\u001f\nTWm\u0019;\u0003\rI+7/^7f'\u0015\tr\b\u0014(R)\t\ty\u0002\u0005\u0002N#Q\u0019\u0001/a\t\t\u000fQ,\u0012\u0011!a\u0001WR\u0019q0a\n\t\u000fQ<\u0012\u0011!a\u0001a\n!1\u000b^8q'\u0015)q\b\u0014(R)\t\ty\u0003\u0005\u0002N\u000bQ\u0019\u0001/a\r\t\u000fQL\u0011\u0011!a\u0001WR\u0019q0a\u000e\t\u000fQ\\\u0011\u0011!a\u0001a\u0006!1\u000b^8q\u0003\u0011\u0019Ho\u001c9\u0016\u0005\u0005=\u0012A\u0002*fgVlW-\u0001\u0004sKN,X.Z\u000b\u0003\u0003?\tqAU3ti\u0006\u0014H/A\u0004sKN$\u0018M\u001d;\u0016\u0003y\u0013q\u0001R3dS\u0012,'\u000fE\u0004\u0002P\u0005]\u0013Q\f'\u000f\t\u0005E\u00131\u000b\t\u0003)\u0006K1!!\u0016B\u0003\u0019\u0001&/\u001a3fM&!\u0011\u0011LA.\u0005!1UO\\2uS>t'bAA+\u0003B\u0019!+a\u0018\n\u0007\u0005\u0005DLA\u0005UQJ|w/\u00192mK\u0006y1\u000f^8qa&tw\rR3dS\u0012,'/\u0006\u0002\u0002hI1\u0011\u0011NA7\u0003_2a!a\u001b\u0002\u0001\u0005\u001d$\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004CA')!\u001d\t\t(a\u001f\u0002^1k!!a\u001d\u000b\t\u0005U\u0014qO\u0001\tMVt7\r^5p]*\u0019\u0011\u0011\u0010\u001d\u0002\t)\f\u0007/[\u0005\u0005\u00033\n\u0019(\u0001\tti>\u0004\b/\u001b8h\t\u0016\u001c\u0017\u000eZ3sA\u0005\u0011r-\u001a;Ti>\u0004\b/\u001b8h\t\u0016\u001c\u0017\u000eZ3s+\t\ty'A\nhKR\u001cFo\u001c9qS:<G)Z2jI\u0016\u0014\b%A\bsKN,X.\u001b8h\t\u0016\u001c\u0017\u000eZ3s+\t\tII\u0005\u0004\u0002\f\u00065\u0014q\u000e\u0004\u0007\u0003W\n\u0001!!#\u0002!I,7/^7j]\u001e$UmY5eKJ\u0004\u0013AE4fiJ+7/^7j]\u001e$UmY5eKJ\f1cZ3u%\u0016\u001cX/\\5oO\u0012+7-\u001b3fe\u0002\n\u0011C]3ti\u0006\u0014H/\u001b8h\t\u0016\u001c\u0017\u000eZ3s+\t\t9J\u0005\u0004\u0002\u001a\u00065\u0014q\u000e\u0004\u0007\u0003W\n\u0001!a&\u0002%I,7\u000f^1si&tw\rR3dS\u0012,'\u000fI\u0001\u0015O\u0016$(+Z:uCJ$\u0018N\\4EK\u000eLG-\u001a:\u0002+\u001d,GOU3ti\u0006\u0014H/\u001b8h\t\u0016\u001c\u0017\u000eZ3sA\u0001")
public final class Supervision {
    public static Function<Throwable, Directive> getRestartingDecider() {
        return Supervision$.MODULE$.getRestartingDecider();
    }

    public static Function1<Throwable, Directive> restartingDecider() {
        return Supervision$.MODULE$.restartingDecider();
    }

    public static Function<Throwable, Directive> getResumingDecider() {
        return Supervision$.MODULE$.getResumingDecider();
    }

    public static Function1<Throwable, Directive> resumingDecider() {
        return Supervision$.MODULE$.resumingDecider();
    }

    public static Function<Throwable, Directive> getStoppingDecider() {
        return Supervision$.MODULE$.getStoppingDecider();
    }

    public static Function1<Throwable, Directive> stoppingDecider() {
        return Supervision$.MODULE$.stoppingDecider();
    }

    public static Supervision$Restart$ restart() {
        return Supervision$.MODULE$.restart();
    }

    public static Supervision$Resume$ resume() {
        return Supervision$.MODULE$.resume();
    }

    public static Supervision$Stop$ stop() {
        return Supervision$.MODULE$.stop();
    }

    public static interface Directive {
    }
}

