/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.CLIUtil;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

public final class DiffCLI {
    public static final void main(String ... args2) throws Exception {
        CLIUtil.main("https://www.jooq.org/doc/latest/manual/sql-building/schema-diff-cli/", () -> {
            Settings settings = new Settings();
            Args a = DiffCLI.parse(args2);
            DiffCLI.settings(a, settings);
            DSLContext ctx = DiffCLI.ctx(a, settings);
            if (!a.done) {
                if (a.toDialect == null || a.sql1 == null || a.sql2 == null) {
                    System.out.println("Mandatory arguments: -T and -1, -2. Use -h for help");
                    throw new RuntimeException();
                }
                DiffCLI.render(ctx, a);
            }
        });
    }

    private static final DSLContext ctx(Args a, Settings settings) {
        return DSL.using(a.toDialect, settings);
    }

    private static final void settings(Args a, Settings settings) {
        if (a.formatted) {
            settings.setRenderFormatted(true);
        }
        if (a.keywords != null) {
            settings.setRenderKeywordCase(a.keywords);
        }
        if (a.name != null) {
            settings.setRenderNameCase(a.name);
        }
        if (a.fromDialect != null) {
            settings.setParseDialect(a.fromDialect);
        }
    }

    private static final void render(DSLContext ctx, Args a) {
        String sql1 = a.sql1.trim();
        String sql2 = a.sql2.trim();
        System.out.println(ctx.render(ctx.meta(sql1).migrateTo(ctx.meta(sql2))));
    }

    private static final Args parse(String[] args2) {
        Args result = new Args();
        for (int i = 0; i < args2.length; ++i) {
            if ("-f".equals(args2[i]) || "--formatted".equals(args2[i])) {
                result.formatted = true;
                continue;
            }
            if ("-k".equals(args2[i]) || "--keyword".equals(args2[i])) {
                try {
                    result.keywords = RenderKeywordCase.valueOf(args2[++i].toUpperCase());
                    continue;
                }
                catch (IllegalArgumentException e) {
                    DiffCLI.invalid(args2[i], RenderKeywordCase.class);
                    throw e;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Flag -k / --keyword requires <RenderKeywordCase> argument");
                    throw e;
                }
            }
            if ("-i".equals(args2[i]) || "--identifier".equals(args2[i])) {
                try {
                    result.keywords = RenderKeywordCase.valueOf(args2[++i].toUpperCase());
                    continue;
                }
                catch (IllegalArgumentException e) {
                    DiffCLI.invalid(args2[i], RenderKeywordCase.class);
                    throw e;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Flag -i / --identifier requires <RenderNameCase> argument");
                    throw e;
                }
            }
            if ("-F".equals(args2[i]) || "--from-dialect".equals(args2[i])) {
                try {
                    result.fromDialect = SQLDialect.valueOf(args2[++i].toUpperCase());
                    continue;
                }
                catch (IllegalArgumentException e) {
                    DiffCLI.invalid(args2[i], SQLDialect.class);
                    throw e;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Flag -F / --from-dialect requires <SQLDialect> argument");
                    throw e;
                }
            }
            if ("-t".equals(args2[i]) || "-T".equals(args2[i]) || "--to-dialect".equals(args2[i])) {
                try {
                    result.toDialect = SQLDialect.valueOf(args2[++i].toUpperCase());
                    continue;
                }
                catch (IllegalArgumentException e) {
                    DiffCLI.invalid(args2[i], SQLDialect.class);
                    throw e;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Flag -T / --to-dialect requires <SQLDialect> argument");
                    throw e;
                }
            }
            if ("-1".equals(args2[i]) || "--sql1".equals(args2[i])) {
                try {
                    result.sql1 = args2[++i];
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Flag -1 / --sql1 requires <String> argument");
                    throw e;
                }
            }
            if ("-2".equals(args2[i]) || "--sql2".equals(args2[i])) {
                try {
                    result.sql2 = args2[++i];
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Flag -2 / --sql2 requires <String> argument");
                    throw e;
                }
            }
            if ("-h".equals(args2[i]) || "--help".equals(args2[i])) {
                DiffCLI.help();
                result.done = true;
                continue;
            }
            System.out.println("Unknown flag: " + args2[i] + ". Use -h or --help");
            throw new RuntimeException();
        }
        return result;
    }

    private static final void invalid(String string, Class<? extends Enum<?>> type) {
        System.out.println("Invalid " + type.getSimpleName() + ": " + string);
        System.out.println("Possible values:");
        for (Enum<?> e : type.getEnumConstants()) {
            System.out.println("  " + e.name());
        }
    }

    private static final void help() {
        System.out.println("Usage:");
        System.out.println("  -f / --formatted                        Format output SQL");
        System.out.println("  -h / --help                             Display this help");
        System.out.println("  -k / --keyword      <RenderKeywordCase> Specify the output keyword case (org.jooq.conf.RenderKeywordCase)");
        System.out.println("  -i / --identifier   <RenderNameCase>    Specify the output identifier case (org.jooq.conf.RenderNameCase)");
        System.out.println("  -F / --from-dialect <SQLDialect>        Specify the input dialect (org.jooq.SQLDialect)");
        System.out.println("  -T / --to-dialect   <SQLDialect>        Specify the output dialect (org.jooq.SQLDialect)");
        System.out.println("  -1 / --sql1         <String>            Specify the input SQL string 1 (from SQL)");
        System.out.println("  -2 / --sql2         <String>            Specify the input SQL string 2 (to SQL)");
    }

    public static final class Args {
        String sql1;
        String sql2;
        RenderKeywordCase keywords = RenderKeywordCase.LOWER;
        RenderNameCase name = RenderNameCase.LOWER;
        SQLDialect toDialect = SQLDialect.DEFAULT;
        SQLDialect fromDialect = SQLDialect.DEFAULT;
        boolean formatted;
        boolean done;
    }
}

