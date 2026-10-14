/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.collection.StringOps$;
import scala.reflect.NameTransformer;
import scala.runtime.ScalaRunTime$;

public final class NameTransformer$ {
    public static final NameTransformer$ MODULE$ = new NameTransformer$();
    private static final int nops = 128;
    private static final int ncodes = 676;
    private static final String[] op2code = new String[nops];
    private static final NameTransformer.OpCodes[] code2op = new NameTransformer.OpCodes[ncodes];

    static {
        MODULE$.enterOp('~', "$tilde");
        MODULE$.enterOp('=', "$eq");
        MODULE$.enterOp('<', "$less");
        MODULE$.enterOp('>', "$greater");
        MODULE$.enterOp('!', "$bang");
        MODULE$.enterOp('#', "$hash");
        MODULE$.enterOp('%', "$percent");
        MODULE$.enterOp('^', "$up");
        MODULE$.enterOp('&', "$amp");
        MODULE$.enterOp('|', "$bar");
        MODULE$.enterOp('*', "$times");
        MODULE$.enterOp('/', "$div");
        MODULE$.enterOp('+', "$plus");
        MODULE$.enterOp('-', "$minus");
        MODULE$.enterOp(':', "$colon");
        MODULE$.enterOp('\\', "$bslash");
        MODULE$.enterOp('?', "$qmark");
        MODULE$.enterOp('@', "$at");
    }

    public final String NAME_JOIN_STRING() {
        return "$";
    }

    public final String MODULE_SUFFIX_STRING() {
        return "$";
    }

    public final String MODULE_INSTANCE_NAME() {
        return "MODULE$";
    }

    public final String LOCAL_SUFFIX_STRING() {
        return " ";
    }

    public final String LAZY_LOCAL_SUFFIX_STRING() {
        return "$lzy";
    }

    public final String MODULE_VAR_SUFFIX_STRING() {
        return "$module";
    }

    public final String SETTER_SUFFIX_STRING() {
        return "_$eq";
    }

    public final String TRAIT_SETTER_SEPARATOR_STRING() {
        return "$_setter_$";
    }

    private void enterOp(char op, String code) {
        NameTransformer$.op2code[op] = code;
        int c = (code.charAt(1) - 97) * 26 + code.charAt(2) - 97;
        NameTransformer$.code2op[c] = new NameTransformer.OpCodes(op, code, code2op[c]);
    }

    public String encode(String name) {
        scala.collection.mutable.StringBuilder buf = null;
        int len = name.length();
        for (int i = 0; i < len; ++i) {
            char c = name.charAt(i);
            if (c < nops && op2code[c] != null) {
                if (buf == null) {
                    buf = new scala.collection.mutable.StringBuilder();
                    buf.append(name.substring(0, i));
                }
                buf.append(op2code[c]);
                continue;
            }
            if (!Character.isJavaIdentifierPart(c)) {
                if (buf == null) {
                    buf = new scala.collection.mutable.StringBuilder();
                    buf.append(name.substring(0, i));
                }
                buf.append(StringOps$.MODULE$.format$extension("$u%04X", ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{(int)c})));
                continue;
            }
            if (buf == null) continue;
            buf.append(c);
        }
        if (buf == null) {
            return name;
        }
        return buf.toString();
    }

    public String decode(String name0) {
        String name = name0.endsWith("<init>") ? new StringBuilder(4).append(StringOps$.MODULE$.stripSuffix$extension(name0, "<init>")).append("this").toString() : name0;
        scala.collection.mutable.StringBuilder buf = null;
        int len = name.length();
        int i = 0;
        while (i < len) {
            char ch1;
            NameTransformer.OpCodes ops = null;
            boolean unicode = false;
            char c = name.charAt(i);
            if (c == '$' && i + 2 < len && 'a' <= (ch1 = name.charAt(i + 1)) && ch1 <= 'z') {
                char ch2 = name.charAt(i + 2);
                if ('a' <= ch2 && ch2 <= 'z') {
                    for (ops = code2op[(ch1 - 97) * 26 + ch2 - 97]; ops != null && !name.startsWith(ops.code(), i); ops = ops.next()) {
                    }
                    if (ops != null) {
                        if (buf == null) {
                            buf = new scala.collection.mutable.StringBuilder();
                            buf.append(name.substring(0, i));
                        }
                        buf.append(ops.op());
                        i += ops.code().length();
                    }
                } else if (len - i >= 6 && ch1 == 'u' && (Character.isDigit(ch2) || 'A' <= ch2 && ch2 <= 'F')) {
                    try {
                        char str = (char)Integer.parseInt(name.substring(i + 2, i + 6), 16);
                        if (buf == null) {
                            buf = new scala.collection.mutable.StringBuilder();
                            buf.append(name.substring(0, i));
                        }
                        buf.append(str);
                        i += 6;
                        unicode = true;
                    }
                    catch (NumberFormatException numberFormatException) {}
                }
            }
            if (ops != null || unicode) continue;
            if (buf != null) {
                buf.append(c);
            }
            ++i;
        }
        if (buf == null) {
            return name;
        }
        return buf.toString();
    }

    private NameTransformer$() {
    }
}

