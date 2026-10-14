/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.util;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3Q\u0001C\u0005\u0002\u0002IAQ!\u0007\u0001\u0005\u0002iAQ!\b\u0001\u0007\u0002yAQA\t\u0001\u0007\u0002\rBQ\u0001\u000e\u0001\u0007\u0002UBQa\u000e\u0001\u0007\u0002aBQA\u000f\u0001\u0007\u0002mBQA\u000f\u0001\u0007\u0002u\u0012ABT8EKB\u001cHj\\4hKJT!AC\u0006\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u00195\t\u0011b]:mG>tg-[4\u000b\u00059y\u0011\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003A\t1aY8n\u0007\u0001\u0019\"\u0001A\n\u0011\u0005Q9R\"A\u000b\u000b\u0003Y\tQa]2bY\u0006L!\u0001G\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t1\u0004\u0005\u0002\u001d\u00015\t\u0011\"\u0001\bjg\u0012+'-^4F]\u0006\u0014G.\u001a3\u0016\u0003}\u0001\"\u0001\u0006\u0011\n\u0005\u0005*\"a\u0002\"p_2,\u0017M\\\u0001\u0006I\u0016\u0014Wo\u001a\u000b\u0003I\u001d\u0002\"\u0001F\u0013\n\u0005\u0019*\"\u0001B+oSRDQ\u0001K\u0002A\u0002%\n1!\\:h!\tQ\u0013G\u0004\u0002,_A\u0011A&F\u0007\u0002[)\u0011a&E\u0001\u0007yI|w\u000e\u001e \n\u0005A*\u0012A\u0002)sK\u0012,g-\u0003\u00023g\t11\u000b\u001e:j]\u001eT!\u0001M\u000b\u0002\t%tgm\u001c\u000b\u0003IYBQ\u0001\u000b\u0003A\u0002%\nAa^1s]R\u0011A%\u000f\u0005\u0006Q\u0015\u0001\r!K\u0001\u0006KJ\u0014xN\u001d\u000b\u0003IqBQ\u0001\u000b\u0004A\u0002%\"2\u0001\n @\u0011\u0015As\u00011\u0001*\u0011\u0015\u0001u\u00011\u0001B\u0003%!\bN]8xC\ndW\r\u0005\u0002C\u000f:\u00111)\u0012\b\u0003Y\u0011K\u0011AF\u0005\u0003\rV\tq\u0001]1dW\u0006<W-\u0003\u0002I\u0013\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\rV\u0001")
public abstract class NoDepsLogger {
    public abstract boolean isDebugEnabled();

    public abstract void debug(String var1);

    public abstract void info(String var1);

    public abstract void warn(String var1);

    public abstract void error(String var1);

    public abstract void error(String var1, Throwable var2);
}

