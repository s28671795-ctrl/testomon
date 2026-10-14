/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.internal.asm.$AnnotationVisitor;
import com.google.inject.internal.asm.$ClassReader;
import com.google.inject.internal.asm.$ClassVisitor;
import com.google.inject.internal.asm.$FieldVisitor;
import com.google.inject.internal.asm.$Label;
import com.google.inject.internal.asm.$MethodVisitor;
import com.google.inject.internal.asm.$Type;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

final class LineNumbers {
    private static final Logger logger = Logger.getLogger(LineNumbers.class.getName());
    private static volatile boolean alreadyLoggedReadingFailure;
    private static final int ASM_API_LEVEL = 589824;
    private final Class<?> type;
    private final Map<String, Integer> lines = Maps.newHashMap();
    private String source;
    private int firstLine = Integer.MAX_VALUE;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public LineNumbers(Class<?> type) throws IOException {
        this.type = type;
        if (!type.isArray()) {
            InputStream in = null;
            try {
                in = type.getResourceAsStream("/" + type.getName().replace('.', '/') + ".class");
            }
            catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
            if (in != null) {
                try {
                    new $ClassReader(in).accept(new LineNumberReader(), 4);
                }
                catch (Exception ignored) {
                    if (!alreadyLoggedReadingFailure) {
                        alreadyLoggedReadingFailure = true;
                        logger.log(Level.WARNING, "Failed loading line numbers. ASM is probably out of date. Further failures won't be logged.", ignored);
                    }
                }
                finally {
                    try {
                        in.close();
                    }
                    catch (IOException iOException) {}
                }
            }
        }
    }

    public String getSource() {
        return this.source;
    }

    public Integer getLineNumber(Member member) {
        Preconditions.checkArgument(this.type == member.getDeclaringClass(), "Member %s belongs to %s, not %s", (Object)member, member.getDeclaringClass(), this.type);
        return this.lines.get(this.memberKey(member));
    }

    public int getFirstLine() {
        return this.firstLine == Integer.MAX_VALUE ? 1 : this.firstLine;
    }

    private String memberKey(Member member) {
        Preconditions.checkNotNull(member, "member");
        if (member instanceof Field) {
            return member.getName();
        }
        if (member instanceof Method) {
            return member.getName() + $Type.getMethodDescriptor((Method)member);
        }
        if (member instanceof Constructor) {
            StringBuilder sb = new StringBuilder().append("<init>(");
            for (Class<?> param2 : ((Constructor)member).getParameterTypes()) {
                sb.append($Type.getDescriptor(param2));
            }
            return sb.append(")V").toString();
        }
        throw new IllegalArgumentException("Unsupported implementation class for Member, " + member.getClass());
    }

    private class LineNumberReader
    extends $ClassVisitor {
        private int line;
        private String pendingMethod;
        private String name;

        LineNumberReader() {
            super(589824);
            this.line = -1;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.name = name;
        }

        @Override
        public $MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if ((access & 2) != 0) {
                return null;
            }
            this.pendingMethod = name + desc;
            this.line = -1;
            return new LineNumberMethodVisitor();
        }

        @Override
        public void visitSource(String source2, String debug) {
            LineNumbers.this.source = source2;
        }

        public void visitLineNumber(int line, $Label start2) {
            if (line < LineNumbers.this.firstLine) {
                LineNumbers.this.firstLine = line;
            }
            this.line = line;
            if (this.pendingMethod != null) {
                LineNumbers.this.lines.put(this.pendingMethod, line);
                this.pendingMethod = null;
            }
        }

        @Override
        public $FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return null;
        }

        @Override
        public $AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            return new LineNumberAnnotationVisitor(this);
        }

        public $AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
            return new LineNumberAnnotationVisitor(this);
        }

        class LineNumberAnnotationVisitor
        extends $AnnotationVisitor {
            LineNumberAnnotationVisitor(LineNumberReader this$1) {
                super(589824);
            }

            @Override
            public $AnnotationVisitor visitAnnotation(String name, String desc) {
                return this;
            }

            @Override
            public $AnnotationVisitor visitArray(String name) {
                return this;
            }

            public void visitLocalVariable(String name, String desc, String signature, $Label start2, $Label end, int index) {
            }
        }

        class LineNumberMethodVisitor
        extends $MethodVisitor {
            LineNumberMethodVisitor() {
                super(589824);
            }

            @Override
            public $AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return new LineNumberAnnotationVisitor(LineNumberReader.this);
            }

            @Override
            public $AnnotationVisitor visitAnnotationDefault() {
                return new LineNumberAnnotationVisitor(LineNumberReader.this);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (opcode == 181 && LineNumberReader.this.name.equals(owner) && !LineNumbers.this.lines.containsKey(name) && LineNumberReader.this.line != -1) {
                    LineNumbers.this.lines.put(name, LineNumberReader.this.line);
                }
            }

            @Override
            public void visitLineNumber(int line, $Label start2) {
                LineNumberReader.this.visitLineNumber(line, start2);
            }
        }
    }
}

