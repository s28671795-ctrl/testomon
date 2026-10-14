/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.util.concurrent.TimeoutException;
import scala.concurrent.Await$;
import scala.concurrent.Awaitable;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A;Q!\u0002\u0004\t\u0002-1Q!\u0004\u0004\t\u00029AQaE\u0001\u0005\u0002QAQ!F\u0001\u0005\u0006YAQAR\u0001\u0005\u0006\u001d\u000bQ!Q<bSRT!a\u0002\u0005\u0002\u0015\r|gnY;se\u0016tGOC\u0001\n\u0003\u0015\u00198-\u00197b\u0007\u0001\u0001\"\u0001D\u0001\u000e\u0003\u0019\u0011Q!Q<bSR\u001c\"!A\b\u0011\u0005A\tR\"\u0001\u0005\n\u0005IA!AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u0017\u0005)!/Z1esV\u0011q\u0003\t\u000b\u00041iIcBA\r\u001b\u0019\u0001AQaG\u0002A\u0002q\t\u0011\"Y<bSR\f'\r\\3\u0011\u00071ir$\u0003\u0002\u001f\r\tI\u0011i^1ji\u0006\u0014G.\u001a\t\u00033\u0001\"Q!I\u0002C\u0002\t\u0012\u0011\u0001V\t\u0003G\u0019\u0002\"\u0001\u0005\u0013\n\u0005\u0015B!a\u0002(pi\"Lgn\u001a\t\u0003!\u001dJ!\u0001\u000b\u0005\u0003\u0007\u0005s\u0017\u0010C\u0003+\u0007\u0001\u00071&\u0001\u0004bi6{7\u000f\u001e\t\u0003Y=j\u0011!\f\u0006\u0003]\u0019\t\u0001\u0002Z;sCRLwN\\\u0005\u0003a5\u0012\u0001\u0002R;sCRLwN\u001c\u0015\u0004\u0007Ib\u0004c\u0001\t4k%\u0011A\u0007\u0003\u0002\u0007i\"\u0014xn^:\u0011\u0005YJdB\u0001\t8\u0013\tA\u0004\"A\u0004qC\u000e\\\u0017mZ3\n\u0005iZ$\u0001F%oi\u0016\u0014(/\u001e9uK\u0012,\u0005pY3qi&|gN\u0003\u00029\u0011\r\nQ\u0007K\u0002\u0004}\u0015\u00032\u0001E\u001a@!\t\u0001%I\u0004\u0002\r\u0003&\u0011\u0001HB\u0005\u0003\u0007\u0012\u0013\u0001\u0003V5nK>,H/\u0012=dKB$\u0018n\u001c8\u000b\u0005a21%A \u0002\rI,7/\u001e7u+\tA%\nF\u0002J\u00176\u0003\"!\u0007&\u0005\u000b\u0005\"!\u0019\u0001\u0012\t\u000bm!\u0001\u0019\u0001'\u0011\u00071i\u0012\nC\u0003+\t\u0001\u00071\u0006K\u0002\u0005eqB3\u0001\u0002 F\u0001")
public final class Await {
    public static <T> T result(Awaitable<T> awaitable, Duration atMost) throws TimeoutException, InterruptedException {
        return Await$.MODULE$.result(awaitable, atMost);
    }

    public static <T> Awaitable<T> ready(Awaitable<T> awaitable, Duration atMost) throws TimeoutException, InterruptedException {
        return Await$.MODULE$.ready(awaitable, atMost);
    }
}

