/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.LineNumbers;
import akka.util.LineNumbers$NoSourceInfo$;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.Tuple2$mcII$sp;
import scala.collection.IndexedSeqOps;
import scala.collection.IterableOnceOps;
import scala.math.Ordering$Int$;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;
import scala.util.control.NonFatal$;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class LineNumbers$ {
    public static final LineNumbers$ MODULE$ = new LineNumbers$();

    public LineNumbers.Result apply(Object obj) {
        return this.forObject(obj);
    }

    public LineNumbers.Result for(Object obj) {
        return this.apply(obj);
    }

    public String prettyName(Object obj) {
        LineNumbers.Result result = this.apply(obj);
        if (LineNumbers$NoSourceInfo$.MODULE$.equals(result)) {
            return obj.getClass().getName();
        }
        if (result instanceof LineNumbers.UnknownSourceFormat) {
            LineNumbers.UnknownSourceFormat unknownSourceFormat = (LineNumbers.UnknownSourceFormat)result;
            String msg = unknownSourceFormat.explanation();
            return new StringBuilder(2).append(obj.getClass().getName()).append("(").append(msg).append(")").toString();
        }
        if (result instanceof LineNumbers.SourceFile) {
            LineNumbers.SourceFile sourceFile = (LineNumbers.SourceFile)result;
            String f = sourceFile.filename();
            return new StringBuilder(2).append(obj.getClass().getName()).append("(").append(f).append(")").toString();
        }
        if (result instanceof LineNumbers.SourceFileLines) {
            LineNumbers.SourceFileLines sourceFileLines = (LineNumbers.SourceFileLines)result;
            return new StringBuilder(1).append(obj.getClass().getPackage().getName()).append("/").append(sourceFileLines).toString();
        }
        throw new MatchError(result);
    }

    private final boolean debug() {
        return false;
    }

    private LineNumbers.Result forObject(Object obj) {
        Some some;
        Tuple2 tuple2;
        Option option = this.getStreamForClass(obj.getClass()).orElse((Function0<Option> & Serializable)() -> MODULE$.getStreamForLambda(obj));
        if (None$.MODULE$.equals(option)) {
            return LineNumbers$NoSourceInfo$.MODULE$;
        }
        if (option instanceof Some && (tuple2 = (Tuple2)(some = (Some)option).value()) != null) {
            InputStream stream = (InputStream)tuple2._1();
            Option filter2 = (Option)tuple2._2();
            return this.getInfo(stream, filter2);
        }
        throw new MatchError(option);
    }

    private LineNumbers.Result getInfo(InputStream stream, Option<String> filter2) {
        LineNumbers.Result result;
        DataInputStream dis;
        block16: {
            dis = new DataInputStream(stream);
            try {
                try {
                    Some some;
                    Tuple2 tuple2;
                    this.skipID(dis);
                    this.skipVersion(dis);
                    LineNumbers.Constants constants = this.getConstants(dis);
                    this.skipClassInfo(dis, constants);
                    this.skipInterfaceInfo(dis, constants);
                    this.skipFields(dis, constants);
                    Option<Tuple2<Object, Object>> lines = this.readMethods(dis, filter2, constants);
                    Option<String> source2 = this.readAttributes(dis, constants);
                    if (source2.isEmpty()) {
                        result = LineNumbers$NoSourceInfo$.MODULE$;
                        break block16;
                    }
                    Option<Tuple2<Object, Object>> option = lines;
                    if (None$.MODULE$.equals(option)) {
                        result = new LineNumbers.SourceFile(source2.get());
                        break block16;
                    }
                    if (option instanceof Some && (tuple2 = (Tuple2)(some = (Some)option).value()) != null) {
                        int from = tuple2._1$mcI$sp();
                        int to = tuple2._2$mcI$sp();
                        result = new LineNumbers.SourceFileLines(source2.get(), from, to);
                        break block16;
                    }
                    throw new MatchError(option);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        result = new LineNumbers.UnknownSourceFormat(new StringBuilder(13).append("parse error: ").append(ex.getMessage()).toString());
                        break block16;
                    }
                    throw throwable;
                }
            }
            catch (Throwable throwable) {
                block17: {
                    try {
                        dis.close();
                    }
                    catch (Throwable throwable3) {
                        Option<Throwable> option;
                        Throwable throwable4 = throwable3;
                        if (throwable4 instanceof InterruptedException) {
                            InterruptedException interruptedException = (InterruptedException)throwable4;
                            throw interruptedException;
                        }
                        if (throwable4 != null && !(option = NonFatal$.MODULE$.unapply(throwable4)).isEmpty()) {
                            break block17;
                        }
                        throw throwable3;
                    }
                }
                throw throwable;
            }
        }
        LineNumbers.UnknownSourceFormat unknownSourceFormat = result;
        try {
            dis.close();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable5 = throwable;
            if (throwable5 instanceof InterruptedException) {
                InterruptedException interruptedException = (InterruptedException)throwable5;
                throw interruptedException;
            }
            if (throwable5 != null && !(option = NonFatal$.MODULE$.unapply(throwable5)).isEmpty()) {
            }
            throw throwable;
        }
        return unknownSourceFormat;
    }

    private Option<Tuple2<InputStream, None$>> getStreamForClass(Class<?> c) {
        String resource = new StringBuilder(6).append(c.getName().replace('.', '/')).append(".class").toString();
        ClassLoader cl = c.getClassLoader();
        InputStream r = cl.getResourceAsStream(resource);
        return Option$.MODULE$.apply(r).map((Function1<InputStream, Tuple2> & Serializable)x$1 -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(x$1), None$.MODULE$));
    }

    private Option<Tuple2<InputStream, Some<String>>> getStreamForLambda(Object l) {
        Option option;
        try {
            Class<?> c = l.getClass();
            Method writeReplace2 = c.getDeclaredMethod("writeReplace", new Class[0]);
            writeReplace2.setAccessible(true);
            Object object = writeReplace2.invoke(l, new Object[0]);
            if (object instanceof SerializedLambda) {
                SerializedLambda serializedLambda = (SerializedLambda)object;
                option = Option$.MODULE$.apply(c.getClassLoader().getResourceAsStream(new StringBuilder(6).append(serializedLambda.getImplClass()).append(".class").toString())).map((Function1<InputStream, Tuple2> & Serializable)x$2 -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(x$2), new Some<String>(serializedLambda.getImplMethodName())));
            } else {
                option = None$.MODULE$;
            }
        }
        catch (Throwable throwable) {
            Option<Throwable> option2;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option2 = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                option = None$.MODULE$;
            }
            throw throwable;
        }
        return option;
    }

    private void skipID(DataInputStream d) {
        int magic = d.readInt();
        if (magic != -889275714) {
            throw new IllegalArgumentException("not a Java class file");
        }
    }

    private void skipVersion(DataInputStream d) {
        short minor = d.readShort();
        short major = d.readShort();
    }

    private LineNumbers.Constants getConstants(DataInputStream d) {
        int count = d.readUnsignedShort();
        LineNumbers.Constants c = new LineNumbers.Constants(count);
        while (!c.isDone()) {
            c.readOne(d);
        }
        c.resolve();
        return c;
    }

    private void skipClassInfo(DataInputStream d, LineNumbers.Constants c) {
        this.akka$util$LineNumbers$$skip(d, 2);
        int name = d.readUnsignedShort();
        this.akka$util$LineNumbers$$skip(d, 2);
    }

    private void skipInterfaceInfo(DataInputStream d, LineNumbers.Constants c) {
        int count = d.readUnsignedShort();
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), count).foreach$mVc$sp(x$3 -> {
            int n = x$3;
            int intf = d.readUnsignedShort();
        });
    }

    private void skipFields(DataInputStream d, LineNumbers.Constants c) {
        int count = d.readUnsignedShort();
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), count).foreach$mVc$sp(x$4 -> {
            int n = x$4;
            MODULE$.skipMethodOrField(d, c);
        });
    }

    private void skipMethodOrField(DataInputStream d, LineNumbers.Constants c) {
        this.akka$util$LineNumbers$$skip(d, 2);
        int name = d.readUnsignedShort();
        this.akka$util$LineNumbers$$skip(d, 2);
        int attributes = d.readUnsignedShort();
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), attributes).foreach$mVc$sp(x$5 -> {
            int n = x$5;
            MODULE$.skipAttribute(d);
        });
    }

    private void skipAttribute(DataInputStream d) {
        this.akka$util$LineNumbers$$skip(d, 2);
        int length = d.readInt();
        this.akka$util$LineNumbers$$skip(d, length);
    }

    private Option<Tuple2<Object, Object>> readMethods(DataInputStream d, Option<String> filter2, LineNumbers.Constants c) {
        int count = d.readUnsignedShort();
        if (c.contains("Code") && c.contains("LineNumberTable")) {
            Tuple2<Integer, Integer> tuple2 = ((IterableOnceOps)RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), count).map((Function1<Object, Option> & Serializable)x$6 -> LineNumbers$.MODULE$.readMethod(d, c.apply("Code"), c.apply("LineNumberTable"), filter2, c)).flatten(Predef$.MODULE$.$conforms())).foldLeft(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(Integer.MAX_VALUE)), BoxesRunTime.boxToInteger(0)), (Function2<Tuple2, Tuple2, Tuple2> & Serializable)(x0$1, x1$1) -> {
                Tuple2<Tuple2, Tuple2> tuple2 = new Tuple2<Tuple2, Tuple2>((Tuple2)x0$1, (Tuple2)x1$1);
                if (tuple2 != null) {
                    Tuple2 tuple22 = tuple2._1();
                    Tuple2 tuple23 = tuple2._2();
                    if (tuple22 != null) {
                        int low = tuple22._1$mcI$sp();
                        int high = tuple22._2$mcI$sp();
                        if (tuple23 != null) {
                            int start2 = tuple23._1$mcI$sp();
                            int end = tuple23._2$mcI$sp();
                            return new Tuple2$mcII$sp(Math.min(low, start2), Math.max(high, end));
                        }
                    }
                }
                throw new MatchError(tuple2);
            });
            if (tuple2 != null) {
                int n = tuple2._1$mcI$sp();
                int n2 = tuple2._2$mcI$sp();
                if (Integer.MAX_VALUE == n && 0 == n2) {
                    return None$.MODULE$;
                }
            }
            return new Some<Tuple2<Object, Object>>(tuple2);
        }
        RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), count).foreach$mVc$sp(x$7 -> {
            int n = x$7;
            MODULE$.skipMethodOrField(d, c);
        });
        return None$.MODULE$;
    }

    private Option<Tuple2<Object, Object>> readMethod(DataInputStream d, int codeTag, int lineNumberTableTag, Option<String> filter2, LineNumbers.Constants c) {
        this.akka$util$LineNumbers$$skip(d, 2);
        int name = d.readUnsignedShort();
        this.akka$util$LineNumbers$$skip(d, 2);
        Object attributes = RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), d.readUnsignedShort()).map((Function1<Object, Option> & Serializable)x$10 -> LineNumbers$.$anonfun$readMethod$1(d, codeTag, filter2, c, name, lineNumberTableTag, BoxesRunTime.unboxToInt(x$10)));
        return ((IndexedSeqOps)attributes.flatten(Predef$.MODULE$.$conforms())).headOption();
    }

    private Option<String> readAttributes(DataInputStream d, LineNumbers.Constants c) {
        int count = d.readUnsignedShort();
        if (c.contains("SourceFile")) {
            int s2 = c.apply("SourceFile");
            Object attributes = RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), count).map((Function1<Object, Option> & Serializable)x$11 -> LineNumbers$.$anonfun$readAttributes$1(d, s2, c, BoxesRunTime.unboxToInt(x$11)));
            return ((IndexedSeqOps)attributes.flatten(Predef$.MODULE$.$conforms())).headOption();
        }
        return None$.MODULE$;
    }

    public void akka$util$LineNumbers$$skip(DataInputStream d, int length) {
        if (d.skipBytes(length) != length) {
            throw new IllegalArgumentException("class file ends prematurely");
        }
    }

    public static final /* synthetic */ Option $anonfun$readMethod$2(DataInputStream d$5, int lineNumberTableTag$1, int x$9) {
        int n = x$9;
        int tag = d$5.readUnsignedShort();
        int length = d$5.readInt();
        if (tag != lineNumberTableTag$1) {
            MODULE$.akka$util$LineNumbers$$skip(d$5, length);
            return None$.MODULE$;
        }
        Object lines = RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), d$5.readUnsignedShort()).map(x$8 -> {
            int n = x$8;
            MODULE$.akka$util$LineNumbers$$skip(d$5, 2);
            return d$5.readUnsignedShort();
        });
        return new Some(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(lines.min(Ordering$Int$.MODULE$)), lines.max(Ordering$Int$.MODULE$)));
    }

    public static final /* synthetic */ Option $anonfun$readMethod$1(DataInputStream d$5, int codeTag$1, Option filter$2, LineNumbers.Constants c$3, int name$1, int lineNumberTableTag$1, int x$10) {
        block3: {
            int length;
            block2: {
                int n = x$10;
                int tag = d$5.readUnsignedShort();
                length = d$5.readInt();
                if (tag != codeTag$1) break block2;
                if (!filter$2.isDefined()) break block3;
                String string = c$3.apply(name$1);
                Object a = filter$2.get();
                if (!(string == null ? a != null : !string.equals(a))) break block3;
            }
            MODULE$.akka$util$LineNumbers$$skip(d$5, length);
            return None$.MODULE$;
        }
        MODULE$.akka$util$LineNumbers$$skip(d$5, 4);
        MODULE$.akka$util$LineNumbers$$skip(d$5, d$5.readInt());
        MODULE$.akka$util$LineNumbers$$skip(d$5, 8 * d$5.readUnsignedShort());
        Object possibleLines = RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), d$5.readUnsignedShort()).map((Function1<Object, Option> & Serializable)x$9 -> LineNumbers$.$anonfun$readMethod$2(d$5, lineNumberTableTag$1, BoxesRunTime.unboxToInt(x$9)));
        return ((IndexedSeqOps)possibleLines.flatten(Predef$.MODULE$.$conforms())).headOption();
    }

    public static final /* synthetic */ Option $anonfun$readAttributes$1(DataInputStream d$6, int s$1, LineNumbers.Constants c$4, int x$11) {
        int n = x$11;
        int tag = d$6.readUnsignedShort();
        int length = d$6.readInt();
        if (tag != s$1) {
            MODULE$.akka$util$LineNumbers$$skip(d$6, length);
            return None$.MODULE$;
        }
        int name = d$6.readUnsignedShort();
        return new Some<String>(c$4.apply(name));
    }

    private LineNumbers$() {
    }
}

