/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.SetupStage$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function0;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005e<Qa\u0002\u0005\t\n=1Q!\u0005\u0005\t\nIAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002qAQaP\u0001\u0005\u0002\u0001CQ!Z\u0001\u0005\u0002\u0019DQ!]\u0001\u0005\u0002I\f!bU3ukB\u001cF/Y4f\u0015\tI!\"\u0001\u0003j[Bd'BA\u0006\r\u0003\u0019\u0019HO]3b[*\tQ\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003!\u0005i\u0011\u0001\u0003\u0002\u000b'\u0016$X\u000f]*uC\u001e,7CA\u0001\u0014!\t!r#D\u0001\u0016\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012aD\u0001\u0014I\u0016dWmZ1uKR{7+\u001e2PkRdW\r^\u000b\u0003;9\"2AH\u00148%\ry2#\t\u0004\u0005A\r\u0001aD\u0001\u0007=e\u00164\u0017N\\3nK:$h\b\u0005\u0002#K5\t1E\u0003\u0002%\u0015\u0005)1\u000f^1hK&\u0011ae\t\u0002\n\u0013:D\u0015M\u001c3mKJDQ\u0001K\u0002A\u0002%\nAa\u001a:bEB\u0019AC\u000b\u0017\n\u0005-*\"!\u0003$v]\u000e$\u0018n\u001c81!\tic\u0006\u0004\u0001\u0005\u000b=\u001a!\u0019\u0001\u0019\u0003\u0003Q\u000b\"!\r\u001b\u0011\u0005Q\u0011\u0014BA\u001a\u0016\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001F\u001b\n\u0005Y*\"aA!os\")\u0001h\u0001a\u0001s\u0005I1/\u001e2PkRdW\r\u001e\t\u0004uub\u0003C\u0001\u0012<\u0013\ta4EA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0013\tq4HA\bTk\n\u001cv.\u001e:dK>+H\u000f\\3u\u0003A!W\r\\3hCR,Gk\\(vi2,G/\u0006\u0002B\u0015R)!\t\u0012(RAJ\u00191iE\u0011\u0007\t\u0001\"\u0001A\u0011\u0005\u0006\u000b\u0012\u0001\rAR\u0001\u0005aV\u001c\b\u000e\u0005\u0003\u0015\u000f&[\u0015B\u0001%\u0016\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002.\u0015\u0012)q\u0006\u0002b\u0001aA\u0011A\u0003T\u0005\u0003\u001bV\u0011A!\u00168ji\")q\n\u0002a\u0001!\u0006A1m\\7qY\u0016$X\rE\u0002\u0015U-CQA\u0015\u0003A\u0002M\u000bAAZ1jYB!Ac\u0012+L!\t)VL\u0004\u0002W7:\u0011qKW\u0007\u00021*\u0011\u0011LD\u0001\u0007yI|w\u000e\u001e \n\u0003YI!\u0001X\u000b\u0002\u000fA\f7m[1hK&\u0011al\u0018\u0002\n)\"\u0014xn^1cY\u0016T!\u0001X\u000b\t\u000b\u0005$\u0001\u0019\u00012\u0002\u0011M,(-\u00138mKR\u00042AO2J\u0013\t!7H\u0001\u0007Tk\n\u001c\u0016N\\6J]2,G/\u0001\neK2,w-\u0019;f)>\u001cVOY%oY\u0016$XCA4q)\tAWNE\u0002j')4A\u0001I\u0003\u0001QB\u0011!e[\u0005\u0003Y\u000e\u0012!bT;u\u0011\u0006tG\r\\3s\u0011\u0015\tW\u00011\u0001o!\rQ4m\u001c\t\u0003[A$QaL\u0003C\u0002A\nq\u0002Z3mK\u001e\fG/\u001a+p\u0013:dW\r\u001e\u000b\u0004gV<(c\u0001;\u0014U\u001a!\u0001E\u0002\u0001t\u0011\u00151h\u00011\u0001Q\u0003\u0011\u0001X\u000f\u001c7\t\u000ba4\u0001\u0019A*\u0002\r\r\fgnY3m\u0001")
public final class SetupStage {
    public static OutHandler delegateToInlet(Function0<BoxedUnit> pull, Function1<Throwable, BoxedUnit> cancel) {
        return SetupStage$.MODULE$.delegateToInlet(pull, cancel);
    }

    public static <T> OutHandler delegateToSubInlet(GraphStageLogic.SubSinkInlet<T> subInlet) {
        return SetupStage$.MODULE$.delegateToSubInlet(subInlet);
    }

    public static <T> InHandler delegateToOutlet(Function1<T, BoxedUnit> push, Function0<BoxedUnit> complete, Function1<Throwable, BoxedUnit> fail, GraphStageLogic.SubSinkInlet<T> subInlet) {
        return SetupStage$.MODULE$.delegateToOutlet(push, complete, fail, subInlet);
    }

    public static <T> InHandler delegateToSubOutlet(Function0<T> grab, GraphStageLogic.SubSourceOutlet<T> subOutlet) {
        return SetupStage$.MODULE$.delegateToSubOutlet(grab, subOutlet);
    }
}

