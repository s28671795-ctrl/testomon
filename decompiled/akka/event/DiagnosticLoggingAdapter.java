/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.Logging$;
import akka.event.LoggingAdapter;
import akka.util.ccompat.package$JavaConverters$;
import java.util.Map;
import scala.$less$colon$less$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A3q!\u0003\u0006\u0011\u0002\u0007\u0005q\u0002C\u0003\u001b\u0001\u0011\u00051\u0004C\u0004 \u0001\u0001\u0007I\u0011\u0002\u0011\t\u000f!\u0002\u0001\u0019!C\u0005S!)A\u0006\u0001C![!)A\u0006\u0001C\u0001c!)1\u0007\u0001C\u0001i!)1\n\u0001C\u0001\u0019\")q\n\u0001C\u00017\tAB)[1h]>\u001cH/[2M_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u000b\u0005-a\u0011!B3wK:$(\"A\u0007\u0002\t\u0005\\7.Y\u0002\u0001'\r\u0001\u0001C\u0006\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0011\u0005]AR\"\u0001\u0006\n\u0005eQ!A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003q\u0001\"!E\u000f\n\u0005y\u0011\"\u0001B+oSR\fAaX7eGV\t\u0011\u0005\u0005\u0002#K9\u0011qcI\u0005\u0003I)\tq\u0001T8hO&tw-\u0003\u0002'O\t\u0019Q\nR\"\u000b\u0005\u0011R\u0011\u0001C0nI\u000e|F%Z9\u0015\u0005qQ\u0003bB\u0016\u0004\u0003\u0003\u0005\r!I\u0001\u0004q\u0012\n\u0014aA7eGV\ta\u0006\u0005\u00020a5\t\u0001!\u0003\u0002'1Q\u0011AD\r\u0005\u0006Y\u0015\u0001\rAL\u0001\u0007O\u0016$X\nR\"\u0016\u0003U\u0002BAN\u001e>\u00116\tqG\u0003\u00029s\u0005!Q\u000f^5m\u0015\u0005Q\u0014\u0001\u00026bm\u0006L!\u0001P\u001c\u0003\u00075\u000b\u0007\u000f\u0005\u0002?\u000b:\u0011qh\u0011\t\u0003\u0001Ji\u0011!\u0011\u0006\u0003\u0005:\ta\u0001\u0010:p_Rt\u0014B\u0001#\u0013\u0003\u0019\u0001&/\u001a3fM&\u0011ai\u0012\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0011\u0013\u0002CA\tJ\u0013\tQ%CA\u0002B]f\faa]3u\u001b\u0012\u001bEC\u0001\u000fN\u0011\u0015qu\u00011\u00016\u0003\u0011QW\nZ2\u0002\u0011\rdW-\u0019:N\t\u000e\u0003")
public interface DiagnosticLoggingAdapter
extends LoggingAdapter {
    public scala.collection.immutable.Map<String, Object> akka$event$DiagnosticLoggingAdapter$$_mdc();

    public void akka$event$DiagnosticLoggingAdapter$$_mdc_$eq(scala.collection.immutable.Map<String, Object> var1);

    public static /* synthetic */ scala.collection.immutable.Map mdc$(DiagnosticLoggingAdapter $this) {
        return $this.mdc();
    }

    @Override
    default public scala.collection.immutable.Map<String, Object> mdc() {
        return this.akka$event$DiagnosticLoggingAdapter$$_mdc();
    }

    public static /* synthetic */ void mdc$(DiagnosticLoggingAdapter $this, scala.collection.immutable.Map mdc) {
        $this.mdc(mdc);
    }

    default public void mdc(scala.collection.immutable.Map<String, Object> mdc) {
        this.akka$event$DiagnosticLoggingAdapter$$_mdc_$eq(mdc != null ? mdc : Logging$.MODULE$.emptyMDC());
    }

    public static /* synthetic */ Map getMDC$(DiagnosticLoggingAdapter $this) {
        return $this.getMDC();
    }

    default public Map<String, Object> getMDC() {
        return package$JavaConverters$.MODULE$.MapHasAsJava(this.mdc()).asJava();
    }

    public static /* synthetic */ void setMDC$(DiagnosticLoggingAdapter $this, Map jMdc) {
        $this.setMDC(jMdc);
    }

    default public void setMDC(Map<String, Object> jMdc) {
        this.mdc(jMdc != null ? package$JavaConverters$.MODULE$.MapHasAsScala(jMdc).asScala().toMap($less$colon$less$.MODULE$.refl()) : Logging$.MODULE$.emptyMDC());
    }

    public static /* synthetic */ void clearMDC$(DiagnosticLoggingAdapter $this) {
        $this.clearMDC();
    }

    default public void clearMDC() {
        this.mdc(Logging$.MODULE$.emptyMDC());
    }

    public static void $init$(DiagnosticLoggingAdapter $this) {
        $this.akka$event$DiagnosticLoggingAdapter$$_mdc_$eq(Logging$.MODULE$.emptyMDC());
    }
}

