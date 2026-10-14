/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import scala.Function0;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Some;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.io.BufferedSource;
import scala.io.Codec;
import scala.io.Codec$;
import scala.io.Source;
import scala.runtime.BoxedUnit;
import scala.runtime.java8.JFunction0$mcV$sp;

public final class Source$ {
    public static final Source$ MODULE$ = new Source$();
    private static final int DefaultBufSize = 2048;

    public int DefaultBufSize() {
        return DefaultBufSize;
    }

    public BufferedSource stdin() {
        return this.fromInputStream(System.in, Codec$.MODULE$.fallbackSystemCodec());
    }

    public Source fromIterable(Iterable<Object> iterable) {
        return new Source(iterable){
            private final Iterator<Object> iter;

            public Iterator<Object> iter() {
                return this.iter;
            }
            {
                this.iter = iterable$1.iterator();
            }
        }.withReset((Function0<Source> & Serializable)() -> MODULE$.fromIterable(iterable));
    }

    public Source fromChar(char c) {
        return this.fromIterable(Predef$.MODULE$.wrapCharArray(new char[]{c}));
    }

    public Source fromChars(char[] chars) {
        return this.fromIterable(Predef$.MODULE$.wrapCharArray(chars));
    }

    public Source fromString(String s2) {
        return this.fromIterable(Predef$.MODULE$.wrapString(s2));
    }

    public BufferedSource fromFile(String name, Codec codec) {
        return this.fromFile(new File(name), codec);
    }

    public BufferedSource fromFile(String name, String enc) {
        return this.fromFile(name, Codec$.MODULE$.apply(enc));
    }

    public BufferedSource fromFile(URI uri, Codec codec) {
        return this.fromFile(new File(uri), codec);
    }

    public BufferedSource fromFile(URI uri, String enc) {
        return this.fromFile(uri, Codec$.MODULE$.apply(enc));
    }

    public BufferedSource fromFile(File file, Codec codec) {
        return this.fromFile(file, this.DefaultBufSize(), codec);
    }

    public BufferedSource fromFile(File file, String enc) {
        return this.fromFile(file, Codec$.MODULE$.apply(enc));
    }

    public BufferedSource fromFile(File file, String enc, int bufferSize) {
        return this.fromFile(file, bufferSize, Codec$.MODULE$.apply(enc));
    }

    /*
     * WARNING - void declaration
     */
    public BufferedSource fromFile(File file, int bufferSize, Codec codec) {
        void createBufferedSource_close;
        FileInputStream inputStream2 = new FileInputStream(file);
        JFunction0$mcV$sp jFunction0$mcV$sp = () -> inputStream2.close();
        Function0<BufferedSource> & Serializable createBufferedSource_reset = (Function0<BufferedSource> & Serializable)() -> MODULE$.fromFile(file, bufferSize, codec);
        Function0<BufferedSource> & Serializable createBufferedSource_resetFn = createBufferedSource_reset == null ? () -> Source$.$anonfun$createBufferedSource$1(inputStream2, bufferSize, null, (Function0)createBufferedSource_close, codec) : createBufferedSource_reset;
        Object var5_6 = null;
        jFunction0$mcV$sp = null;
        Object var7_7 = null;
        return (BufferedSource)((BufferedSource)new BufferedSource(inputStream2, bufferSize, codec).withReset((Function0<Source>)createBufferedSource_resetFn).withClose((Function0<BoxedUnit>)createBufferedSource_close)).withDescription(new StringBuilder(5).append("file:").append(file.getAbsolutePath()).toString());
    }

    public Source fromBytes(byte[] bytes, Codec codec) {
        return this.fromString(new String(bytes, codec.name()));
    }

    public Source fromBytes(byte[] bytes, String enc) {
        return this.fromBytes(bytes, Codec$.MODULE$.apply(enc));
    }

    public Source fromRawBytes(byte[] bytes) {
        return this.fromString(new String(bytes, Codec$.MODULE$.ISO8859().charSet()));
    }

    public BufferedSource fromURI(URI uri, Codec codec) {
        return this.fromFile(new File(uri), codec);
    }

    public BufferedSource fromURL(String s2, String enc) {
        return this.fromURL(s2, Codec$.MODULE$.apply(enc));
    }

    public BufferedSource fromURL(String s2, Codec codec) {
        return this.fromURL(new URL(s2), codec);
    }

    public BufferedSource fromURL(URL url2, String enc) {
        return this.fromURL(url2, Codec$.MODULE$.apply(enc));
    }

    public BufferedSource fromURL(URL url2, Codec codec) {
        return this.fromInputStream(url2.openStream(), codec);
    }

    public BufferedSource createBufferedSource(InputStream inputStream2, int bufferSize, Function0<Source> reset, Function0<BoxedUnit> close, Codec codec) {
        Function0<BufferedSource> & Serializable resetFn = reset == null ? () -> Source$.$anonfun$createBufferedSource$1(inputStream2, bufferSize, null, close, codec) : reset;
        return (BufferedSource)new BufferedSource(inputStream2, bufferSize, codec).withReset((Function0<Source>)resetFn).withClose(close);
    }

    public int createBufferedSource$default$2() {
        return this.DefaultBufSize();
    }

    public Function0<Source> createBufferedSource$default$3() {
        return null;
    }

    public Function0<BoxedUnit> createBufferedSource$default$4() {
        return null;
    }

    public BufferedSource fromInputStream(InputStream is, String enc) {
        return this.fromInputStream(is, Codec$.MODULE$.apply(enc));
    }

    public BufferedSource fromInputStream(InputStream is, Codec codec) {
        Function0<BufferedSource> & Serializable x$2 = (Function0<BufferedSource> & Serializable)() -> MODULE$.fromInputStream(is, codec);
        JFunction0$mcV$sp x$3 = () -> is.close();
        int x$4 = this.DefaultBufSize();
        Function0<BufferedSource> & Serializable createBufferedSource_resetFn = x$2 == null ? () -> Source$.$anonfun$createBufferedSource$1(is, x$4, null, x$3, codec) : x$2;
        return (BufferedSource)new BufferedSource(is, x$4, codec).withReset((Function0<Source>)createBufferedSource_resetFn).withClose(x$3);
    }

    public BufferedSource fromResource(String resource, ClassLoader classLoader, Codec codec) {
        Option<InputStream> option = Option$.MODULE$.apply(classLoader.getResourceAsStream(resource));
        if (option instanceof Some) {
            InputStream in = (InputStream)((Some)option).value();
            return this.fromInputStream(in, codec);
        }
        if (None$.MODULE$.equals(option)) {
            throw new FileNotFoundException(new StringBuilder(70).append("resource '").append(resource).append("' was not found in the classpath from the given classloader.").toString());
        }
        throw new MatchError(option);
    }

    public ClassLoader fromResource$default$2() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static final /* synthetic */ BufferedSource $anonfun$createBufferedSource$1(InputStream inputStream$2, int bufferSize$2, Function0 reset$1, Function0 close$1, Codec codec$2) {
        return MODULE$.createBufferedSource(inputStream$2, bufferSize$2, reset$1, close$1, codec$2);
    }

    private Source$() {
    }
}

