/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.Props;
import akka.pattern.BackoffOnFailureOptions;
import akka.pattern.BackoffOnStopOptions;
import akka.pattern.BackoffOpts$;
import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t<Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQ!G\u0001\u0005\u0002\u0011CQAU\u0001\u0005\u0002MCQAU\u0001\u0005\u0002q\u000b1BQ1dW>4gm\u00149ug*\u0011\u0011BC\u0001\ba\u0006$H/\u001a:o\u0015\u0005Y\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u000f\u00035\t\u0001BA\u0006CC\u000e\\wN\u001a4PaR\u001c8CA\u0001\u0012!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!D\u0001\n_:4\u0015-\u001b7ve\u0016$ba\u0007\u0010'guz\u0004C\u0001\b\u001d\u0013\ti\u0002BA\fCC\u000e\\wN\u001a4P]\u001a\u000b\u0017\u000e\\;sK>\u0003H/[8og\")qd\u0001a\u0001A\u0005Q1\r[5mIB\u0013x\u000e]:\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0005\rR\u0011!B1di>\u0014\u0018BA\u0013#\u0005\u0015\u0001&o\u001c9t\u0011\u001593\u00011\u0001)\u0003%\u0019\u0007.\u001b7e\u001d\u0006lW\r\u0005\u0002*a9\u0011!F\f\t\u0003WMi\u0011\u0001\f\u0006\u0003[1\ta\u0001\u0010:p_Rt\u0014BA\u0018\u0014\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011G\r\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005=\u001a\u0002\"\u0002\u001b\u0004\u0001\u0004)\u0014AC7j]\n\u000b7m[8gMB\u0011agO\u0007\u0002o)\u0011\u0001(O\u0001\tIV\u0014\u0018\r^5p]*\u0011!hE\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001\u001f8\u000591\u0015N\\5uK\u0012+(/\u0019;j_:DQAP\u0002A\u0002U\n!\"\\1y\u0005\u0006\u001c7n\u001c4g\u0011\u0015\u00015\u00011\u0001B\u00031\u0011\u0018M\u001c3p[\u001a\u000b7\r^8s!\t\u0011\")\u0003\u0002D'\t1Ai\\;cY\u0016$baG#G\u000fB\u000b\u0006\"B\u0010\u0005\u0001\u0004\u0001\u0003\"B\u0014\u0005\u0001\u0004A\u0003\"\u0002\u001b\u0005\u0001\u0004A\u0005CA%O\u001b\u0005Q%BA&M\u0003\u0011!\u0018.\\3\u000b\u00035\u000bAA[1wC&\u0011qJ\u0013\u0002\t\tV\u0014\u0018\r^5p]\")a\b\u0002a\u0001\u0011\")\u0001\t\u0002a\u0001\u0003\u00061qN\\*u_B$b\u0001V,Y3j[\u0006C\u0001\bV\u0013\t1\u0006B\u0001\u000bCC\u000e\\wN\u001a4P]N#x\u000e](qi&|gn\u001d\u0005\u0006?\u0015\u0001\r\u0001\t\u0005\u0006O\u0015\u0001\r\u0001\u000b\u0005\u0006i\u0015\u0001\r!\u000e\u0005\u0006}\u0015\u0001\r!\u000e\u0005\u0006\u0001\u0016\u0001\r!\u0011\u000b\u0007)vsv\fY1\t\u000b}1\u0001\u0019\u0001\u0011\t\u000b\u001d2\u0001\u0019\u0001\u0015\t\u000bQ2\u0001\u0019\u0001%\t\u000by2\u0001\u0019\u0001%\t\u000b\u00013\u0001\u0019A!")
public final class BackoffOpts {
    public static BackoffOnStopOptions onStop(Props childProps, String childName, Duration minBackoff, Duration maxBackoff, double randomFactor) {
        return BackoffOpts$.MODULE$.onStop(childProps, childName, minBackoff, maxBackoff, randomFactor);
    }

    public static BackoffOnStopOptions onStop(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        return BackoffOpts$.MODULE$.onStop(childProps, childName, minBackoff, maxBackoff, randomFactor);
    }

    public static BackoffOnFailureOptions onFailure(Props childProps, String childName, Duration minBackoff, Duration maxBackoff, double randomFactor) {
        return BackoffOpts$.MODULE$.onFailure(childProps, childName, minBackoff, maxBackoff, randomFactor);
    }

    public static BackoffOnFailureOptions onFailure(Props childProps, String childName, FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        return BackoffOpts$.MODULE$.onFailure(childProps, childName, minBackoff, maxBackoff, randomFactor);
    }
}

