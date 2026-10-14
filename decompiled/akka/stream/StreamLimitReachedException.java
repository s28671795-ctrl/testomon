/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00152A\u0001B\u0003\u0001\u0015!A\u0011\u0004\u0001BC\u0002\u0013\u0005!\u0004\u0003\u0005 \u0001\t\u0005\t\u0015!\u0003\u001c\u0011\u0015\u0001\u0003\u0001\"\u0001\"\u0005m\u0019FO]3b[2KW.\u001b;SK\u0006\u001c\u0007.\u001a3Fq\u000e,\u0007\u000f^5p]*\u0011aaB\u0001\u0007gR\u0014X-Y7\u000b\u0003!\tA!Y6lC\u000e\u00011C\u0001\u0001\f!\taaC\u0004\u0002\u000e'9\u0011a\"E\u0007\u0002\u001f)\u0011\u0001#C\u0001\u0007yI|w\u000e\u001e \n\u0003I\tQa]2bY\u0006L!\u0001F\u000b\u0002\u000fA\f7m[1hK*\t!#\u0003\u0002\u00181\t\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\u0006\u0003)U\t\u0011A\\\u000b\u00027A\u0011A$H\u0007\u0002+%\u0011a$\u0006\u0002\u0005\u0019>tw-\u0001\u0002oA\u00051A(\u001b8jiz\"\"A\t\u0013\u0011\u0005\r\u0002Q\"A\u0003\t\u000be\u0019\u0001\u0019A\u000e")
public class StreamLimitReachedException
extends RuntimeException {
    private final long n;

    public long n() {
        return this.n;
    }

    public StreamLimitReachedException(long n) {
        this.n = n;
        super(new StringBuilder(17).append("limit of ").append(n).append(" reached").toString());
    }
}

