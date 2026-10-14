/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.ReactiveStreamsCompliance$;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t%rAB\u0015+\u0011\u0003a\u0003G\u0002\u00043U!\u0005Af\r\u0005\u0006u\u0005!\t\u0001\u0010\u0005\b{\u0005\u0011\r\u0011\"\u0002?\u0011\u0019\u0011\u0015\u0001)A\u0007\u007f!91)\u0001b\u0001\n\u000b!\u0005B\u0002%\u0002A\u00035Q\tC\u0004J\u0003\t\u0007IQ\u0001&\t\r9\u000b\u0001\u0015!\u0004L\u0011\u001dy\u0015A1A\u0005\u0006ACa\u0001V\u0001!\u0002\u001b\t\u0006bB+\u0002\u0005\u0004%)A\u0016\u0005\u00075\u0006\u0001\u000bQB,\t\u000fm\u000b!\u0019!C\u00039\"1\u0001-\u0001Q\u0001\u000euCq!Y\u0001C\u0002\u0013\u0015!\r\u0003\u0004g\u0003\u0001\u0006ia\u0019\u0005\u0006O\u0006!)\u0001\u001b\u0005\u0006k\u0006!)\u0001\u001b\u0005\u0006m\u0006!)\u0001\u001b\u0005\u0006o\u0006!)\u0001\u001b\u0005\u0006q\u0006!)\u0001\u001b\u0005\u0006s\u0006!)\u0001\u001b\u0005\u0006u\u0006!)a\u001f\u0005\b\u0003W\tAQAA\u0017\u0011\u001d\ti%\u0001C\u0003\u0003\u001fBq!a\u0017\u0002\t\u000b\ti\u0006C\u0004\u0002j\u0005!)!a\u001b\t\u000f\u0005E\u0014\u0001\"\u0002\u0002t!9\u0011qP\u0001\u0005\u0006\u0005\u0005e!CAG\u0003A\u0005\u0019\u0013EAH\r\u0019\t\u0019*\u0001\u0002\u0002\u0016\"Q\u00111V\u0010\u0003\u0002\u0003\u0006I!!\u0010\t\u0013\u0005=tD!A!\u0002\u0013I\u0007B\u0002\u001e \t\u0003\ti\u000bC\u0004\u0002B\u0006!)!a1\t\u000f\u0005M\u0017\u0001\"\u0002\u0002V\"9\u00111]\u0001\u0005\u0006\u0005\u0015\bbBAz\u0003\u0011\u0015\u0011Q\u001f\u0005\b\u0005\u0003\tAQ\u0001B\u0002\u0011\u001d\u0011\t\"\u0001C\u0003\u0005'\t\u0011DU3bGRLg/Z*ue\u0016\fWn]\"p[Bd\u0017.\u00198dK*\u00111\u0006L\u0001\u0005S6\u0004HN\u0003\u0002.]\u000511\u000f\u001e:fC6T\u0011aL\u0001\u0005C.\\\u0017\r\u0005\u00022\u00035\t!FA\rSK\u0006\u001cG/\u001b<f'R\u0014X-Y7t\u0007>l\u0007\u000f\\5b]\u000e,7CA\u00015!\t)\u0004(D\u00017\u0015\u00059\u0014!B:dC2\f\u0017BA\u001d7\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\u001a\u0001\u0001F\u00011\u00035\u001a\u0015M\u001c(piN+(m]2sS\n,G\u000b[3TC6,7+\u001e2tGJL'-\u001a:Nk2$\u0018\u000e\u001d7f)&lWm]\u000b\u0002\u007f=\t\u0001)I\u0001B\u00039\u001c\u0017M\u001c\u0011o_R\u00043/\u001e2tGJL'-\u001a\u0011uQ\u0016\u00043/Y7fAM,(m]2sS\n,'\u000fI7vYRL\u0007\u000f\\3!i&lWm\u001d\u0011)g\u0016,\u0007E]3bGRLg/Z\u0017tiJ,\u0017-\\:!gB,7-\u001b4jG\u0006$\u0018n\u001c8-AI,H.Z:!c9\n\u0004\u0007I1oI\u0002\u0012d&\r\u001a*\u00039\u001a\u0015M\u001c(piN+(m]2sS\n,G\u000b[3TC6,7+\u001e2tGJL'-\u001a:Nk2$\u0018\u000e\u001d7f)&lWm\u001d\u0011\u0002;M+\b\u000f]8siN|e\u000e\\=B'&tw\r\\3Tk\n\u001c8M]5cKJ,\u0012!R\b\u0002\r\u0006\nq)\u00010p]2L\be];qa>\u0014Ho\u001d\u0011p]\u0016\u00043/\u001e2tGJL'-\u001a:!Q]D\u0017n\u00195!SN\u0004\u0013\r\u001c7po\u0016$G\u0006I:fK\u0002\u0012X-Y2uSZ,Wf\u001d;sK\u0006l7\u000fI:qK\u000eLg-[2bi&|g\u000e\f\u0011sk2,\u0007%\r\u00182c%\nadU;qa>\u0014Ho](oYf\f5+\u001b8hY\u0016\u001cVOY:de&\u0014WM\u001d\u0011\u0002U9+XNY3s\u001f\u001a,E.Z7f]R\u001c\u0018J\u001c*fcV,7\u000f^'vgR\u0014U\rU8tSRLg/Z'tOV\t1jD\u0001MC\u0005i\u0015a\u0017+iK\u0002rW/\u001c2fe\u0002zg\r\t:fcV,7\u000f^3eA\u0015dW-\\3oiN\u0004S.^:uA\t,\u0007E\u0010\u00111A!\u001aX-\u001a\u0011sK\u0006\u001cG/\u001b<f[M$(/Z1ng\u0002\u001a\b/Z2jM&\u001c\u0017\r^5p]2\u0002#/\u001e7fAMr\u0013(K\u0001,\u001dVl'-\u001a:PM\u0016cW-\\3oiNLeNU3rk\u0016\u001cH/T;ti\n+\u0007k\\:ji&4X-T:hA\u0005Q2+\u001e2tGJL'-\u001a:NkN$hj\u001c;CK:+H\u000e\\'tOV\t\u0011kD\u0001SC\u0005\u0019\u0016!J*vEN\u001c'/\u001b2fe\u0002jWo\u001d;!]>$\bEY3!]VdG\u000e\f\u0011sk2,\u0007%\r\u0018:\u0003m\u0019VOY:de&\u0014WM]'vgRtu\u000e\u001e\"f\u001dVdG.T:hA\u0005IR\t_2faRLwN\\'vgRtu\u000e\u001e\"f\u001dVdG.T:h+\u00059v\"\u0001-\"\u0003e\u000bQ%\u0012=dKB$\u0018n\u001c8![V\u001cH\u000f\t8pi\u0002\u0012W\r\t8vY2d\u0003E];mK\u0002\u0012d&M\u001a\u00025\u0015C8-\u001a9uS>tW*^:u\u001d>$()\u001a(vY2l5o\u001a\u0011\u0002/\u0015cW-\\3oi6+8\u000f\u001e(pi\n+g*\u001e7m\u001bN<W#A/\u0010\u0003y\u000b\u0013aX\u0001$\u000b2,W.\u001a8uA5,8\u000f\u001e\u0011o_R\u0004#-\u001a\u0011ok2dG\u0006\t:vY\u0016\u0004#GL\u00194\u0003a)E.Z7f]RlUo\u001d;O_R\u0014UMT;mY6\u001bx\rI\u0001\u001d'V\u00147o\u0019:jaRLwN\\'vgRtu\u000e\u001e\"f\u001dVdG.T:h+\u0005\u0019w\"\u00013\"\u0003\u0015\f\u0001fU;cg\u000e\u0014\u0018\u000e\u001d;j_:\u0004S.^:uA9|G\u000f\t2fA9,H\u000e\u001c\u0017!eVdW\r\t\u001a/cM\nQdU;cg\u000e\u0014\u0018\u000e\u001d;j_:lUo\u001d;O_R\u0014UMT;mY6\u001bx\rI\u00011]Vl'-\u001a:PM\u0016cW-\\3oiNLeNU3rk\u0016\u001cH/T;ti\n+\u0007k\\:ji&4X-\u0012=dKB$\u0018n\u001c8\u0016\u0003%\u0004\"A\u001b:\u000f\u0005-\u0004hB\u00017p\u001b\u0005i'B\u00018<\u0003\u0019a$o\\8u}%\tq'\u0003\u0002rm\u00059\u0001/Y2lC\u001e,\u0017BA:u\u0005%!\u0006N]8xC\ndWM\u0003\u0002rm\u000514-\u00198O_R\u001cVOY:de&\u0014W\r\u00165f'\u0006lWmU;cg\u000e\u0014\u0018NY3s\u001bVdG/\u001b9mKRKW.Z:Fq\u000e,\u0007\u000f^5p]\u0006\u00013/\u001e2tGJL'-\u001a:NkN$hj\u001c;CK:+H\u000e\\#yG\u0016\u0004H/[8o\u0003})\u0007pY3qi&|g.T;ti:{GOQ3Ok2dW\t_2faRLwN\\\u0001\u001eK2,W.\u001a8u\u001bV\u001cHOT8u\u0005\u0016tU\u000f\u001c7Fq\u000e,\u0007\u000f^5p]\u0006\u00113/\u001e2tGJL\u0007\u000f^5p]6+8\u000f\u001e(pi\n+g*\u001e7m\u000bb\u001cW\r\u001d;j_:\f\u0011D]3kK\u000e$H)\u001e9mS\u000e\fG/Z*vEN\u001c'/\u001b2feV\u0019A0!\u0007\u0015\u0007u\f\t\u0001\u0005\u00026}&\u0011qP\u000e\u0002\u0005+:LG\u000fC\u0004\u0002\u0004]\u0001\r!!\u0002\u0002\u0015M,(m]2sS\n,'\u000f\u0005\u0004\u0002\b\u0005E\u0011QC\u0007\u0003\u0003\u0013QA!a\u0003\u0002\u000e\u0005y!/Z1di&4Xm\u001d;sK\u0006l7O\u0003\u0002\u0002\u0010\u0005\u0019qN]4\n\t\u0005M\u0011\u0011\u0002\u0002\u000b'V\u00147o\u0019:jE\u0016\u0014\b\u0003BA\f\u00033a\u0001\u0001B\u0004\u0002\u001c]\u0011\r!!\b\u0003\u0003Q\u000bB!a\b\u0002&A\u0019Q'!\t\n\u0007\u0005\rbGA\u0004O_RD\u0017N\\4\u0011\u0007U\n9#C\u0002\u0002*Y\u00121!\u00118z\u0003i\u0011XM[3di\u0006#G-\u001b;j_:\fGnU;cg\u000e\u0014\u0018NY3s+\u0011\ty#a\u000e\u0015\u000bu\f\t$!\u000f\t\u000f\u0005\r\u0001\u00041\u0001\u00024A1\u0011qAA\t\u0003k\u0001B!a\u0006\u00028\u00119\u00111\u0004\rC\u0002\u0005u\u0001bBA\u001e1\u0001\u0007\u0011QH\u0001\te\u0016TWm\u0019;peB!\u0011qHA$\u001d\u0011\t\t%a\u0011\u0011\u000514\u0014bAA#m\u00051\u0001K]3eK\u001aLA!!\u0013\u0002L\t11\u000b\u001e:j]\u001eT1!!\u00127\u0003q\u0011XM[3di\u0012+X\rV8O_:\u0004vn]5uSZ,G)Z7b]\u0012,B!!\u0015\u0002ZQ\u0019Q0a\u0015\t\u000f\u0005\r\u0011\u00041\u0001\u0002VA1\u0011qAA\t\u0003/\u0002B!a\u0006\u0002Z\u00119\u00111D\rC\u0002\u0005u\u0011\u0001\u0007:fcVL'/\u001a(p]:+H\u000e\\*vEN\u001c'/\u001b2feV!\u0011qLA4)\ri\u0018\u0011\r\u0005\b\u0003\u0007Q\u0002\u0019AA2!\u0019\t9!!\u0005\u0002fA!\u0011qCA4\t\u001d\tYB\u0007b\u0001\u0003;\tqC]3rk&\u0014XMT8o\u001dVdG.\u0012=dKB$\u0018n\u001c8\u0015\u0007u\fi\u0007\u0003\u0004\u0002pm\u0001\r![\u0001\u0006G\u0006,8/Z\u0001\u0016e\u0016\fX/\u001b:f\u001d>tg*\u001e7m\u000b2,W.\u001a8u+\u0011\t)(! \u0015\u0007u\f9\bC\u0004\u0002zq\u0001\r!a\u001f\u0002\u000f\u0015dW-\\3oiB!\u0011qCA?\t\u001d\tY\u0002\bb\u0001\u0003;\t!D]3rk&\u0014XMT8o\u001dVdGnU;cg\u000e\u0014\u0018\u000e\u001d;j_:$2!`AB\u0011\u001d\t))\ba\u0001\u0003\u000f\u000bAb];cg\u000e\u0014\u0018\u000e\u001d;j_:\u0004B!a\u0002\u0002\n&!\u00111RA\u0005\u00051\u0019VOY:de&\u0004H/[8o\u00055\u0019\u0006/Z2WS>d\u0017\r^5p]N\u0011a$[\u0015\u0003=}\u0011AcU5h]\u0006dG\u000b\u001b:fo\u0016C8-\u001a9uS>t7#B\u0010\u0002\u0018\u0006\u001d\u0006\u0003BAM\u0003Gk!!a'\u000b\t\u0005u\u0015qT\u0001\u0005Y\u0006twM\u0003\u0002\u0002\"\u0006!!.\u0019<b\u0013\u0011\t)+a'\u0003+%cG.Z4bYN#\u0018\r^3Fq\u000e,\u0007\u000f^5p]B\u0019\u0011\u0011\u0016\u0010\u000e\u0003\u0005\tq!\\3tg\u0006<W\r\u0006\u0004\u00020\u0006E\u00161\u0017\t\u0004\u0003S{\u0002bBAVE\u0001\u0007\u0011Q\b\u0005\u0007\u0003_\u0012\u0003\u0019A5)\u000f}\t9,!0\u0002@B\u0019Q'!/\n\u0007\u0005mfG\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!\u0001\u0006uef|e.\u0012:s_J,B!!2\u0002NR)Q0a2\u0002P\"9\u00111A\u0012A\u0002\u0005%\u0007CBA\u0004\u0003#\tY\r\u0005\u0003\u0002\u0018\u00055GaBA\u000eG\t\u0007\u0011Q\u0004\u0005\u0007\u0003#\u001c\u0003\u0019A5\u0002\u000b\u0015\u0014(o\u001c:\u0002\u0013Q\u0014\u0018p\u00148OKb$X\u0003BAl\u0003?$R!`Am\u0003CDq!a\u0001%\u0001\u0004\tY\u000e\u0005\u0004\u0002\b\u0005E\u0011Q\u001c\t\u0005\u0003/\ty\u000eB\u0004\u0002\u001c\u0011\u0012\r!!\b\t\u000f\u0005eD\u00051\u0001\u0002^\u0006qAO]=P]N+(m]2sS\n,W\u0003BAt\u0003_$R!`Au\u0003cDq!a\u0001&\u0001\u0004\tY\u000f\u0005\u0004\u0002\b\u0005E\u0011Q\u001e\t\u0005\u0003/\ty\u000fB\u0004\u0002\u001c\u0015\u0012\r!!\b\t\u000f\u0005\u0015U\u00051\u0001\u0002\b\u0006iAO]=P]\u000e{W\u000e\u001d7fi\u0016,B!a>\u0002\u0000R\u0019Q0!?\t\u000f\u0005\ra\u00051\u0001\u0002|B1\u0011qAA\t\u0003{\u0004B!a\u0006\u0002\u0000\u00129\u00111\u0004\u0014C\u0002\u0005u\u0011A\u0003;ssJ+\u0017/^3tiR)QP!\u0002\u0003\b!9\u0011QQ\u0014A\u0002\u0005\u001d\u0005b\u0002B\u0005O\u0001\u0007!1B\u0001\u0007I\u0016l\u0017M\u001c3\u0011\u0007U\u0012i!C\u0002\u0003\u0010Y\u0012A\u0001T8oO\u0006IAO]=DC:\u001cW\r\u001c\u000b\u0006{\nU!q\u0003\u0005\b\u0003\u000bC\u0003\u0019AAD\u0011\u0019\ty\u0007\u000ba\u0001S\"\u001a\u0011Aa\u0007\u0011\t\tu!1E\u0007\u0003\u0005?Q1A!\t/\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0005K\u0011yBA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u0003\u001c\u0001")
@InternalApi
public final class ReactiveStreamsCompliance {
    public static void tryCancel(Subscription subscription, Throwable cause) {
        ReactiveStreamsCompliance$.MODULE$.tryCancel(subscription, cause);
    }

    public static void tryRequest(Subscription subscription, long demand) {
        ReactiveStreamsCompliance$.MODULE$.tryRequest(subscription, demand);
    }

    public static <T> void tryOnComplete(Subscriber<T> subscriber) {
        ReactiveStreamsCompliance$.MODULE$.tryOnComplete(subscriber);
    }

    public static <T> void tryOnSubscribe(Subscriber<T> subscriber, Subscription subscription) {
        ReactiveStreamsCompliance$.MODULE$.tryOnSubscribe(subscriber, subscription);
    }

    public static <T> void tryOnNext(Subscriber<T> subscriber, T element) {
        ReactiveStreamsCompliance$.MODULE$.tryOnNext(subscriber, element);
    }

    public static <T> void tryOnError(Subscriber<T> subscriber, Throwable error) {
        ReactiveStreamsCompliance$.MODULE$.tryOnError(subscriber, error);
    }

    public static void requireNonNullSubscription(Subscription subscription) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscription(subscription);
    }

    public static <T> void requireNonNullElement(T element) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(element);
    }

    public static void requireNonNullException(Throwable cause) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullException(cause);
    }

    public static <T> void requireNonNullSubscriber(Subscriber<T> subscriber) {
        ReactiveStreamsCompliance$.MODULE$.requireNonNullSubscriber(subscriber);
    }

    public static <T> void rejectDueToNonPositiveDemand(Subscriber<T> subscriber) {
        ReactiveStreamsCompliance$.MODULE$.rejectDueToNonPositiveDemand(subscriber);
    }

    public static <T> void rejectAdditionalSubscriber(Subscriber<T> subscriber, String rejector) {
        ReactiveStreamsCompliance$.MODULE$.rejectAdditionalSubscriber(subscriber, rejector);
    }

    public static <T> void rejectDuplicateSubscriber(Subscriber<T> subscriber) {
        ReactiveStreamsCompliance$.MODULE$.rejectDuplicateSubscriber(subscriber);
    }

    public static Throwable subscriptionMustNotBeNullException() {
        return ReactiveStreamsCompliance$.MODULE$.subscriptionMustNotBeNullException();
    }

    public static Throwable elementMustNotBeNullException() {
        return ReactiveStreamsCompliance$.MODULE$.elementMustNotBeNullException();
    }

    public static Throwable exceptionMustNotBeNullException() {
        return ReactiveStreamsCompliance$.MODULE$.exceptionMustNotBeNullException();
    }

    public static Throwable subscriberMustNotBeNullException() {
        return ReactiveStreamsCompliance$.MODULE$.subscriberMustNotBeNullException();
    }

    public static Throwable canNotSubscribeTheSameSubscriberMultipleTimesException() {
        return ReactiveStreamsCompliance$.MODULE$.canNotSubscribeTheSameSubscriberMultipleTimesException();
    }

    public static Throwable numberOfElementsInRequestMustBePositiveException() {
        return ReactiveStreamsCompliance$.MODULE$.numberOfElementsInRequestMustBePositiveException();
    }

    public static String SubscriptionMustNotBeNullMsg() {
        return ReactiveStreamsCompliance$.MODULE$.SubscriptionMustNotBeNullMsg();
    }

    public static String ElementMustNotBeNullMsg() {
        return ReactiveStreamsCompliance$.MODULE$.ElementMustNotBeNullMsg();
    }

    public static String ExceptionMustNotBeNullMsg() {
        return ReactiveStreamsCompliance$.MODULE$.ExceptionMustNotBeNullMsg();
    }

    public static String SubscriberMustNotBeNullMsg() {
        return ReactiveStreamsCompliance$.MODULE$.SubscriberMustNotBeNullMsg();
    }

    public static String NumberOfElementsInRequestMustBePositiveMsg() {
        return ReactiveStreamsCompliance$.MODULE$.NumberOfElementsInRequestMustBePositiveMsg();
    }

    public static String SupportsOnlyASingleSubscriber() {
        return ReactiveStreamsCompliance$.MODULE$.SupportsOnlyASingleSubscriber();
    }

    public static String CanNotSubscribeTheSameSubscriberMultipleTimes() {
        return ReactiveStreamsCompliance$.MODULE$.CanNotSubscribeTheSameSubscriberMultipleTimes();
    }

    public static final class SignalThrewException
    extends IllegalStateException
    implements SpecViolation {
        private static final long serialVersionUID = 1L;

        public SignalThrewException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static interface SpecViolation {
    }
}

