/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import akka.util.ByteString;

public final class JsonFraming$ {
    public static final JsonFraming$ MODULE$ = new JsonFraming$();

    public Flow<ByteString, ByteString, NotUsed> objectScanner(int maximumObjectLength) {
        return akka.stream.scaladsl.JsonFraming$.MODULE$.objectScanner(maximumObjectLength).asJava();
    }

    private JsonFraming$() {
    }
}

