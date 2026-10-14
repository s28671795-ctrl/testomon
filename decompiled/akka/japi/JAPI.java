/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.Util;
import scala.collection.Seq;

public class JAPI {
    @SafeVarargs
    public static <T> Seq<T> seq(T ... TArray) {
        return Util.immutableSeq(TArray);
    }
}

