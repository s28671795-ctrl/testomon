/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.AkkaException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592A!\u0002\u0004\u0001\u0013!Aa\u0002\u0001B\u0001B\u0003%q\u0002\u0003\u0005\u001d\u0001\t\u0005\t\u0015!\u0003\u001e\u0011\u00151\u0003\u0001\"\u0001(\u0011\u00151\u0003\u0001\"\u0001,\u0005Y\u0019uN\u001c4jOV\u0014\u0018\r^5p]\u0016C8-\u001a9uS>t'\"A\u0004\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001!\u0002\u0005\u0002\f\u00195\ta!\u0003\u0002\u000e\r\ti\u0011i[6b\u000bb\u001cW\r\u001d;j_:\fq!\\3tg\u0006<W\r\u0005\u0002\u001139\u0011\u0011c\u0006\t\u0003%Ui\u0011a\u0005\u0006\u0003)!\ta\u0001\u0010:p_Rt$\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\n\u0005a)\u0012A\u0002)sK\u0012,g-\u0003\u0002\u001b7\t11\u000b\u001e:j]\u001eT!\u0001G\u000b\u0002\u000b\r\fWo]3\u0011\u0005y\u0019cBA\u0010\"\u001d\t\u0011\u0002%C\u0001\u0017\u0013\t\u0011S#A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0011*#!\u0003+ie><\u0018M\u00197f\u0015\t\u0011S#\u0001\u0004=S:LGO\u0010\u000b\u0004Q%R\u0003CA\u0006\u0001\u0011\u0015q1\u00011\u0001\u0010\u0011\u0015a2\u00011\u0001\u001e)\tAC\u0006C\u0003.\t\u0001\u0007q\"A\u0002ng\u001e\u0004")
public class ConfigurationException
extends AkkaException {
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(String msg) {
        this(msg, null);
    }
}

