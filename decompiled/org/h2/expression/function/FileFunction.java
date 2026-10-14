/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.store.fs.FileUtils;
import org.h2.util.IOUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

public final class FileFunction
extends Function1_2 {
    public static final int FILE_READ = 0;
    public static final int FILE_WRITE = 1;
    private static final String[] NAMES = new String[]{"FILE_READ", "FILE_WRITE"};
    private final int function;

    public FileFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    @Override
    public Value getValue(SessionLocal var1_1) {
        var1_1.getUser().checkAdmin();
        var2_2 = this.left.getValue(var1_1);
        if (var2_2 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        switch (this.function) {
            case 0: {
                var3_3 = var2_2.getString();
                var4_5 = var1_1.getDatabase();
                try {
                    var5_7 = FileUtils.size(var3_3);
                    var8_11 = FileUtils.newInputStream(var3_3);
                    var9_13 = null;
                    try {
                        if (this.right == null) {
                            var7_16 /* !! */  = var4_5.getLobStorage().createBlob(var8_11, var5_7);
                        } else {
                            var10_20 = this.right.getValue(var1_1);
                            var11_23 = var10_20 == ValueNull.INSTANCE ? new InputStreamReader(var8_11) : new InputStreamReader(var8_11, var10_20.getString());
                            var7_16 /* !! */  = var4_5.getLobStorage().createClob(var11_23, var5_7);
                        }
                    }
                    catch (Throwable var10_22) {
                        var9_13 = var10_22;
                        throw var10_22;
                    }
                    finally {
                        if (var8_11 != null) {
                            if (var9_13 != null) {
                                try {
                                    var8_11.close();
                                }
                                catch (Throwable var10_21) {
                                    var9_13.addSuppressed(var10_21);
                                }
                            } else {
                                var8_11.close();
                            }
                        }
                    }
                    var2_2 = var1_1.addTemporaryLob(var7_16 /* !! */ );
                    break;
                }
                catch (IOException var5_8) {
                    throw DbException.convertIOException(var5_8, var3_3);
                }
            }
            case 1: {
                var3_4 = this.right.getValue(var1_1);
                if (var3_4 == ValueNull.INSTANCE) {
                    var2_2 = ValueNull.INSTANCE;
                    break;
                }
                var4_6 = var3_4.getString();
                try {
                    var5_9 = Files.newOutputStream(Paths.get(var4_6, new String[0]), new OpenOption[0]);
                    var6_26 = null;
                    var7_17 = var2_2.getInputStream();
                    var8_12 = null;
                    try {
                        var2_2 = ValueBigint.get(IOUtils.copy(var7_17, var5_9));
                    }
                    catch (Throwable var9_15) {
                        var8_12 = var9_15;
                        throw var9_15;
                    }
                    finally {
                        if (var7_17 != null) {
                            if (var8_12 != null) {
                                try {
                                    var7_17.close();
                                }
                                catch (Throwable var9_14) {
                                    var8_12.addSuppressed(var9_14);
                                }
                            } else {
                                var7_17.close();
                            }
                        }
                    }
                    if (var5_9 == null) break;
                    if (var6_26 == null) ** GOTO lbl76
                    try {
                        var5_9.close();
                    }
                    catch (Throwable var7_18) {
                        var6_26.addSuppressed(var7_18);
                    }
                    break;
lbl76:
                    // 1 sources

                    var5_9.close();
                    ** break;
                    catch (Throwable var7_19) {
                        try {
                            var6_26 = var7_19;
                            throw var7_19;
                        }
                        catch (Throwable var16_29) {
                            if (var5_9 != null) {
                                if (var6_26 != null) {
                                    try {
                                        var5_9.close();
                                    }
                                    catch (Throwable var17_30) {
                                        var6_26.addSuppressed(var17_30);
                                    }
                                } else {
                                    var5_9.close();
                                }
                            }
                            throw var16_29;
lbl93:
                            // 1 sources

                            break;
                        }
                    }
                }
                catch (IOException var5_10) {
                    throw DbException.convertIOException(var5_10, var4_6);
                }
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return var2_2;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        switch (this.function) {
            case 0: {
                this.type = this.right == null ? TypeInfo.getTypeInfo(7, Integer.MAX_VALUE, 0, null) : TypeInfo.getTypeInfo(3, Integer.MAX_VALUE, 0, null);
                break;
            }
            case 1: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: 
            case 8: {
                return false;
            }
            case 5: {
                if (this.function != 1) break;
                return false;
            }
        }
        return super.isEverything(expressionVisitor);
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

