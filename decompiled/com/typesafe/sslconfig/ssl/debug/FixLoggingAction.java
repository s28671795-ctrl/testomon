/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.debug;

import com.typesafe.sslconfig.ssl.MonkeyPatcher;
import com.typesafe.sslconfig.ssl.debug.ClassFinder;
import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u00154Q\u0001B\u0003\u0002\u0002AAQ!\f\u0001\u0005\u00029BQ\u0001\r\u0001\u0007\u0002EBQ!\u0010\u0001\u0005\u0002y\u0012\u0001CR5y\u0019><w-\u001b8h\u0003\u000e$\u0018n\u001c8\u000b\u0005\u00199\u0011!\u00023fEV<'B\u0001\u0005\n\u0003\r\u00198\u000f\u001c\u0006\u0003\u0015-\t\u0011b]:mG>tg-[4\u000b\u00051i\u0011\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u00039\t1aY8n\u0007\u0001\u0019R\u0001A\t\u001aK%\u0002\"AE\f\u000e\u0003MQ!\u0001F\u000b\u0002\t1\fgn\u001a\u0006\u0002-\u0005!!.\u0019<b\u0013\tA2C\u0001\u0004PE*,7\r\u001e\t\u00045uyR\"A\u000e\u000b\u0005q)\u0012\u0001C:fGV\u0014\u0018\u000e^=\n\u0005yY\"!\u0007)sSZLG.Z4fI\u0016C8-\u001a9uS>t\u0017i\u0019;j_:\u0004\"\u0001I\u0012\u000e\u0003\u0005R\u0011AI\u0001\u0006g\u000e\fG.Y\u0005\u0003I\u0005\u0012A!\u00168jiB\u0011aeJ\u0007\u0002\u000f%\u0011\u0001f\u0002\u0002\u000e\u001b>t7.Z=QCR\u001c\u0007.\u001a:\u0011\u0005)ZS\"A\u0003\n\u00051*!aC\"mCN\u001ch)\u001b8eKJ\fa\u0001P5oSRtD#A\u0018\u0011\u0005)\u0002\u0011A\u00038fo>\u0003H/[8ogV\t!\u0007\u0005\u00024u9\u0011A\u0007\u000f\t\u0003k\u0005j\u0011A\u000e\u0006\u0003o=\ta\u0001\u0010:p_Rt\u0014BA\u001d\"\u0003\u0019\u0001&/\u001a3fM&\u00111\b\u0010\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005e\n\u0013\u0001D5t-\u0006d\u0017\u000e\u001a$jK2$GcA C\u0015B\u0011\u0001\u0005Q\u0005\u0003\u0003\u0006\u0012qAQ8pY\u0016\fg\u000eC\u0003D\u0007\u0001\u0007A)A\u0003gS\u0016dG\r\u0005\u0002F\u00116\taI\u0003\u0002H'\u00059!/\u001a4mK\u000e$\u0018BA%G\u0005\u00151\u0015.\u001a7e\u0011\u0015Y5\u00011\u0001M\u0003-!WMZ5oK\u0012$\u0016\u0010]31\u00055\u0013\u0006cA\u001aO!&\u0011q\n\u0010\u0002\u0006\u00072\f7o\u001d\t\u0003#Jc\u0001\u0001B\u0005T\u0015\u0006\u0005\t\u0011!B\u0001)\n\u0019q\fJ\u0019\u0012\u0005UC\u0006C\u0001\u0011W\u0013\t9\u0016EA\u0004O_RD\u0017N\\4\u0011\u0005\u0001J\u0016B\u0001.\"\u0005\r\te.\u001f\u0015\u0007\u0001q{\u0006MY2\u0011\u0005\u0001j\u0016B\u00010\"\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005\t\u0017a\u001a$jq2{wmZ5oO\u0006\u001bG/[8oA!\f7\u000f\t2fK:\u0004C-\u001a9sK\u000e\fG/\u001a3!C:$\u0007\u0005Z8fg\u0002rw\u000e\u001e5j]\u001et\u0003\u0005\t)mK\u0006\u001cX\rI;tK\u0002\"\b.\u001a\u0011kCZ\f\u0007P\f8fi:\"WMY;hAML8\u000f^3nAA\u0014x\u000e]3sift\u0013!B:j]\u000e,\u0017%\u00013\u0002\u000bArCG\f\u0019")
public abstract class FixLoggingAction
implements PrivilegedExceptionAction<BoxedUnit>,
MonkeyPatcher,
ClassFinder {
    @Override
    public Set<Class<?>> findClasses() {
        return ClassFinder.findClasses$(this);
    }

    @Override
    public void monkeyPatchField(Field field2, Object newObject) {
        MonkeyPatcher.monkeyPatchField$(this, field2, newObject);
    }

    public abstract String newOptions();

    public boolean isValidField(Field field2, Class<?> definedType) {
        this.logger().warn("DebugConfiguration has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        return false;
    }

    public FixLoggingAction() {
        MonkeyPatcher.$init$(this);
        ClassFinder.$init$(this);
    }
}

