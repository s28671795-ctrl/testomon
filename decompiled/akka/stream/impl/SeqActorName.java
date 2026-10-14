/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.DoNotInherit;
import akka.stream.impl.SeqActorName$;
import akka.stream.impl.SeqActorNameImpl;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r3a\u0001C\u0005\u0002\u00025y\u0001\"\u0002\f\u0001\t\u0003A\u0002\"B\u000e\u0001\r\u0003a\u0002\"\u0002\u0015\u0001\r\u0003IsAB\u001a\n\u0011\u0003iAG\u0002\u0004\t\u0013!\u0005Q\"\u000e\u0005\u0006-\u0015!\tA\u000e\u0005\u0006o\u0015!\t\u0001\u000f\u0002\r'\u0016\f\u0018i\u0019;pe:\u000bW.\u001a\u0006\u0003\u0015-\tA![7qY*\u0011A\"D\u0001\u0007gR\u0014X-Y7\u000b\u00039\tA!Y6lCN\u0011\u0001\u0001\u0005\t\u0003#Qi\u0011A\u0005\u0006\u0002'\u0005)1oY1mC&\u0011QC\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!\u0007\t\u00035\u0001i\u0011!C\u0001\u0005]\u0016DH\u000fF\u0001\u001e!\tqRE\u0004\u0002 GA\u0011\u0001EE\u0007\u0002C)\u0011!eF\u0001\u0007yI|w\u000e\u001e \n\u0005\u0011\u0012\u0012A\u0002)sK\u0012,g-\u0003\u0002'O\t11\u000b\u001e:j]\u001eT!\u0001\n\n\u0002\t\r|\u0007/\u001f\u000b\u00033)BQaK\u0002A\u0002u\tAA\\1nK\"\u0012\u0001!\f\t\u0003]Ej\u0011a\f\u0006\u0003a5\t!\"\u00198o_R\fG/[8o\u0013\t\u0011tF\u0001\u0007E_:{G/\u00138iKJLG/\u0001\u0007TKF\f5\r^8s\u001d\u0006lW\r\u0005\u0002\u001b\u000bM\u0011Q\u0001\u0005\u000b\u0002i\u0005)\u0011\r\u001d9msR\u0011\u0011\b\u0010\t\u00035iJ!aO\u0005\u0003!M+\u0017/Q2u_Jt\u0015-\\3J[Bd\u0007\"B\u001f\b\u0001\u0004i\u0012A\u00029sK\u001aL\u0007\u0010\u000b\u0002\u0006\u007fA\u0011a\u0006Q\u0005\u0003\u0003>\u00121\"\u00138uKJt\u0017\r\\!qS\"\u0012Aa\u0010")
@DoNotInherit
public abstract class SeqActorName {
    public static SeqActorNameImpl apply(String prefix) {
        return SeqActorName$.MODULE$.apply(prefix);
    }

    public abstract String next();

    public abstract SeqActorName copy(String var1);
}

