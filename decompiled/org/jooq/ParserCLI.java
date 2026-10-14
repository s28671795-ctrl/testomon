/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.CLIUtil;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParseNameCase;
import org.jooq.conf.ParseUnknownFunctions;
import org.jooq.conf.ParseUnsupportedSyntax;
import org.jooq.conf.ParseWithMetaLookups;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderOptionalKeyword;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.conf.Transformation;
import org.jooq.impl.DSL;
import org.jooq.impl.ParserException;
import org.jooq.tools.StringUtils;

public final class ParserCLI {
    private static final Pattern FLAG = Pattern.compile("^/([\\w\\-]+)(?:\\s+([^\\r\\n]+))?\\s*$");

    public static final void main(String ... args2) throws Exception {
        CLIUtil.main("https://www.jooq.org/doc/latest/manual/sql-building/sql-parser/sql-parser-cli/", () -> {
            Settings settings = new Settings();
            Args a = ParserCLI.parse(args2);
            ParserCLI.settings(a, settings);
            DSLContext ctx = ParserCLI.ctx(a, settings);
            if (a.interactive || args2 == null || args2.length == 0) {
                ParserCLI.interactiveMode(ctx, a);
            } else if (!a.done) {
                if (a.toDialect == null || a.sql == null) {
                    System.out.println("Mandatory arguments: -T and -s. Use -h for help");
                    throw new RuntimeException();
                }
                ParserCLI.render(ctx, a);
            }
        });
    }

    private static final DSLContext ctx(Args a, Settings settings) {
        return DSL.using(a.toDialect, settings);
    }

    private static final void settings(Args a, Settings settings) {
        if (a.formatted != null) {
            settings.setRenderFormatted(a.formatted);
        }
        if (a.keywords != null) {
            settings.setRenderKeywordCase(a.keywords);
        }
        if (a.name != null) {
            settings.setRenderNameCase(a.name);
        }
        if (a.quoted != null) {
            settings.setRenderQuotedNames(a.quoted);
        }
        if (a.fromDialect != null) {
            settings.setParseDialect(a.fromDialect);
        }
        if (a.parseDateFormat != null) {
            settings.setParseDateFormat(a.parseDateFormat);
        }
        if (a.parseIgnoreComments != null) {
            settings.setParseIgnoreComments(a.parseIgnoreComments);
        }
        if (a.parseIgnoreCommentStart != null) {
            settings.setParseIgnoreCommentStart(a.parseIgnoreCommentStart);
        }
        if (a.parseIgnoreCommentStop != null) {
            settings.setParseIgnoreCommentStop(a.parseIgnoreCommentStop);
        }
        if (a.parseLocale != null) {
            settings.setParseLocale(a.parseLocale);
        }
        if (a.parseNameCase != null) {
            settings.setParseNameCase(a.parseNameCase);
        }
        if (a.parseNamedParamPrefix != null) {
            settings.setParseNamedParamPrefix(a.parseNamedParamPrefix);
        }
        if (a.parseRetainCommentsBetweenQueries != null) {
            settings.setParseRetainCommentsBetweenQueries(a.parseRetainCommentsBetweenQueries);
        }
        if (a.parseSetCommands != null) {
            settings.setParseSetCommands(a.parseSetCommands);
        }
        if (a.parseTimestampFormat != null) {
            settings.setParseTimestampFormat(a.parseTimestampFormat);
        }
        if (a.parseUnknownFunctions != null) {
            settings.setParseUnknownFunctions(a.parseUnknownFunctions);
        }
        if (a.renderCoalesceToEmptyStringInConcat != null) {
            settings.setRenderCoalesceToEmptyStringInConcat(a.renderCoalesceToEmptyStringInConcat);
        }
        if (a.renderOptionalInnerKeyword != null) {
            settings.setRenderOptionalInnerKeyword(a.renderOptionalInnerKeyword);
        }
        if (a.renderOptionalOuterKeyword != null) {
            settings.setRenderOptionalOuterKeyword(a.renderOptionalOuterKeyword);
        }
        if (a.renderOptionalAsKeywordForFieldAliases != null) {
            settings.setRenderOptionalAsKeywordForFieldAliases(a.renderOptionalAsKeywordForFieldAliases);
        }
        if (a.renderOptionalAsKeywordForTableAliases != null) {
            settings.setRenderOptionalAsKeywordForTableAliases(a.renderOptionalAsKeywordForTableAliases);
        }
        if (a.transformPatterns != null) {
            settings.setTransformPatterns(a.transformPatterns);
        }
        if (a.transformAnsiJoinToTableLists != null) {
            settings.setTransformAnsiJoinToTableLists(a.transformAnsiJoinToTableLists);
        }
        if (a.transformTableListsToAnsiJoin != null) {
            settings.setTransformTableListsToAnsiJoin(a.transformTableListsToAnsiJoin);
        }
        if (a.transformUnneededArithmetic != null) {
            settings.setTransformUnneededArithmeticExpressions(a.transformUnneededArithmetic);
        }
        if (a.transformQualify != null) {
            settings.setTransformRownum(a.transformQualify);
        }
        if (a.transformRownum != null) {
            settings.setTransformRownum(a.transformRownum);
        }
        if (a.transformGroupByColumnIndex != null) {
            settings.setTransformGroupByColumnIndex(a.transformGroupByColumnIndex);
        }
        if (a.transformInlineCTE != null) {
            settings.setTransformInlineCTE(a.transformInlineCTE);
        }
    }

    private static final <E extends Enum<E>> void parseInteractive(Class<E> type, String arg, Consumer<? super E> onSuccess, Runnable display) {
        try {
            if (arg != null) {
                onSuccess.accept(Enum.valueOf(type, arg.toUpperCase()));
            }
            display.run();
        }
        catch (IllegalArgumentException e) {
            ParserCLI.invalid(arg, type);
        }
    }

    private static final void interactiveMode(DSLContext ctx, Args a) {
        Scanner scan = new Scanner(System.in);
        System.out.print("> ");
        do {
            String line = scan.nextLine();
            a.history.add(line);
            boolean leadingSlash = line.matches("^/[^/*].*$");
            if (a.sql == null && leadingSlash) {
                if ("/q".equals(line) || "/quit".equals(line) || "/e".equals(line) || "/exit".equals(line)) {
                    System.out.println("Bye");
                    break;
                }
                if ("/?".equals(line) || "/h".equals(line) || "/help".equals(line)) {
                    ParserCLI.helpInteractive();
                } else if ("/d".equals(line) || "/display".equals(line)) {
                    ParserCLI.displayArguments(a);
                } else {
                    Matcher matcher = FLAG.matcher(line);
                    if (matcher.find()) {
                        String flag = matcher.group(1);
                        String arg = matcher.group(2);
                        if (flag != null) {
                            if ("f".equals(flag) || "formatted".equals(flag)) {
                                if (arg != null) {
                                    a.formatted = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayFormatted(a);
                            } else if ("k".equals(flag) || "keyword".equals(flag)) {
                                ParserCLI.parseInteractive(RenderKeywordCase.class, arg, e -> {
                                    a.keywords = e;
                                }, () -> ParserCLI.displayKeywords(a));
                            } else if ("i".equals(flag) || "identifier".equals(flag)) {
                                ParserCLI.parseInteractive(RenderNameCase.class, arg, e -> {
                                    a.name = e;
                                }, () -> ParserCLI.displayIdentifiers(a));
                            } else if ("Q".equals(flag) || "quoted".equals(flag)) {
                                ParserCLI.parseInteractive(RenderQuotedNames.class, arg, e -> {
                                    a.quoted = e;
                                }, () -> ParserCLI.displayQuoted(a));
                            } else if ("F".equals(flag) || "from-dialect".equals(flag)) {
                                ParserCLI.parseInteractive(SQLDialect.class, arg, e -> {
                                    a.fromDialect = e;
                                }, () -> ParserCLI.displayFromDialect(a));
                            } else if ("S".equals(flag) || "schema".equals(flag)) {
                                a.schema = arg;
                                ParserCLI.displaySchema(a);
                            } else if ("render-coalesce-to-empty-string-in-concat".equals(flag)) {
                                if (arg != null) {
                                    a.renderCoalesceToEmptyStringInConcat = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayRenderCoalesceToEmptyStringInConcat(a);
                            } else if ("parse-date-format".equals(flag)) {
                                if (arg != null) {
                                    a.parseDateFormat = arg;
                                }
                                ParserCLI.displayParseDateFormat(a);
                            } else if ("parse-ignore-comments".equals(flag)) {
                                if (arg != null) {
                                    a.parseIgnoreComments = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayParseIgnoreComments(a);
                            } else if ("parse-ignore-comment-start".equals(flag)) {
                                if (arg != null) {
                                    a.parseIgnoreCommentStart = arg;
                                }
                                ParserCLI.displayParseIgnoreCommentStart(a);
                            } else if ("parse-ignore-comment-stop".equals(flag)) {
                                if (arg != null) {
                                    a.parseIgnoreCommentStop = arg;
                                }
                                ParserCLI.displayParseIgnoreCommentStop(a);
                            } else if ("parse-locale".equals(flag)) {
                                if (arg != null) {
                                    a.parseLocale = Locale.forLanguageTag(arg);
                                }
                                ParserCLI.displayParseLocale(a);
                            } else if ("parse-name-case".equals(flag)) {
                                ParserCLI.parseInteractive(ParseNameCase.class, arg, e -> {
                                    a.parseNameCase = e;
                                }, () -> ParserCLI.displayParseNameCase(a));
                            } else if ("parse-named-param-prefix".equals(flag)) {
                                if (arg != null) {
                                    a.parseNamedParamPrefix = arg;
                                }
                                ParserCLI.displayParseNamedParamPrefix(a);
                            } else if ("parse-retain-comments-between-queries".equals(flag)) {
                                if (arg != null) {
                                    a.parseRetainCommentsBetweenQueries = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayParseRetainCommentsBetweenQueries(a);
                            } else if ("parse-set-commands".equals(flag)) {
                                if (arg != null) {
                                    a.parseSetCommands = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayParseSetCommands(a);
                            } else if ("parse-timestamp-format".equals(flag)) {
                                if (arg != null) {
                                    a.parseTimestampFormat = arg;
                                }
                                ParserCLI.displayParseTimestampFormat(a);
                            } else if ("parse-unknown-functions".equals(flag)) {
                                ParserCLI.parseInteractive(ParseUnknownFunctions.class, arg, e -> {
                                    a.parseUnknownFunctions = e;
                                }, () -> ParserCLI.displayParseUnknownFunctions(a));
                            } else if ("parse-unsupported-syntax".equals(flag)) {
                                ParserCLI.parseInteractive(ParseUnsupportedSyntax.class, arg, e -> {
                                    a.parseUnsupportedSyntax = e;
                                }, () -> ParserCLI.displayParseUnsupportedSyntax(a));
                            } else if ("render-optional-inner-keyword".equals(flag)) {
                                ParserCLI.parseInteractive(RenderOptionalKeyword.class, arg, e -> {
                                    a.renderOptionalInnerKeyword = e;
                                }, () -> ParserCLI.displayRenderOptionalInnerKeyword(a));
                            } else if ("render-optional-outer-keyword".equals(flag)) {
                                ParserCLI.parseInteractive(RenderOptionalKeyword.class, arg, e -> {
                                    a.renderOptionalOuterKeyword = e;
                                }, () -> ParserCLI.displayRenderOptionalOuterKeyword(a));
                            } else if ("render-optional-as-keyword-for-field-aliases".equals(flag)) {
                                ParserCLI.parseInteractive(RenderOptionalKeyword.class, arg, e -> {
                                    a.renderOptionalAsKeywordForFieldAliases = e;
                                }, () -> ParserCLI.displayRenderOptionalAsKeywordForFieldAliases(a));
                            } else if ("render-optional-as-keyword-for-table-aliases".equals(flag)) {
                                ParserCLI.parseInteractive(RenderOptionalKeyword.class, arg, e -> {
                                    a.renderOptionalAsKeywordForTableAliases = e;
                                }, () -> ParserCLI.displayRenderOptionalAsKeywordForTableAliases(a));
                            } else if ("transform-patterns".equals(flag)) {
                                if (arg != null) {
                                    a.transformPatterns = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayTransformPatterns(a);
                            } else if ("transform-ansi-join-to-table-lists".equals(flag)) {
                                if (arg != null) {
                                    a.transformAnsiJoinToTableLists = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayTransformAnsiJoinToTablesLists(a);
                            } else if ("transform-qualify".equals(flag)) {
                                ParserCLI.parseInteractive(Transformation.class, arg, e -> {
                                    a.transformQualify = e;
                                }, () -> ParserCLI.displayTransformQualify(a));
                            } else if ("transform-rownum".equals(flag)) {
                                ParserCLI.parseInteractive(Transformation.class, arg, e -> {
                                    a.transformRownum = e;
                                }, () -> ParserCLI.displayTransformRownum(a));
                            } else if ("transform-group-by-column-index".equals(flag)) {
                                ParserCLI.parseInteractive(Transformation.class, arg, e -> {
                                    a.transformGroupByColumnIndex = e;
                                }, () -> ParserCLI.displayTransformGroupByColumnIndex(a));
                            } else if ("transform-inline-cte".equals(flag)) {
                                ParserCLI.parseInteractive(Transformation.class, arg, e -> {
                                    a.transformInlineCTE = e;
                                }, () -> ParserCLI.displayTransformInlineCTE(a));
                            } else if ("transform-table-lists-to-ansi-join".equals(flag)) {
                                if (arg != null) {
                                    a.transformTableListsToAnsiJoin = Boolean.parseBoolean(arg.toLowerCase());
                                }
                                ParserCLI.displayTransformTableListsToAnsiJoin(a);
                            } else if ("transform-unneeded-arithmetic".equals(flag)) {
                                ParserCLI.parseInteractive(TransformUnneededArithmeticExpressions.class, arg, e -> {
                                    a.transformUnneededArithmetic = e;
                                }, () -> ParserCLI.displayTransformUnneededArithmetic(a));
                            } else if ("t".equals(flag) || "T".equals(flag) || "to-dialect".equals(flag)) {
                                ParserCLI.parseInteractive(SQLDialect.class, arg, e -> {
                                    a.toDialect = e;
                                }, () -> ParserCLI.displayToDialect(a));
                            }
                        }
                    } else {
                        System.out.println("Unrecognised command: " + line);
                        System.out.println("Type /h for help");
                    }
                }
                ParserCLI.settings(a, ctx.settings());
                ctx = ParserCLI.ctx(a, ctx.settings());
            }
            if (a.sql != null || !leadingSlash) {
                a.sql = a.sql == null ? line : a.sql + "\n" + line;
                if (a.sql.trim().endsWith(";")) {
                    ParserCLI.render(ctx, a);
                    a.sql = null;
                    System.out.println();
                }
            }
            System.out.print("> ");
        } while (scan.hasNextLine());
    }

    private static final void displayArguments(Args a) {
        ParserCLI.displayFormatted(a);
        ParserCLI.displayFromDialect(a);
        ParserCLI.displayToDialect(a);
        ParserCLI.displayKeywords(a);
        ParserCLI.displayIdentifiers(a);
        ParserCLI.displayQuoted(a);
        ParserCLI.displayTransformAnsiJoinToTablesLists(a);
        ParserCLI.displayTransformQualify(a);
        ParserCLI.displayTransformRownum(a);
        ParserCLI.displayTransformTableListsToAnsiJoin(a);
        ParserCLI.displayTransformUnneededArithmetic(a);
        ParserCLI.displayTransformGroupByColumnIndex(a);
        ParserCLI.displayTransformInlineCTE(a);
        ParserCLI.displaySchema(a);
    }

    private static void displaySchema(Args a) {
        System.out.println("Schema                             : " + a.schema);
    }

    private static void displayIdentifiers(Args a) {
        System.out.println("Identifiers                        : " + String.valueOf((Object)a.name));
    }

    private static void displayQuoted(Args a) {
        System.out.println("Quoted                             : " + String.valueOf((Object)a.quoted));
    }

    private static void displayKeywords(Args a) {
        System.out.println("Keywords                           : " + String.valueOf((Object)a.keywords));
    }

    private static void displayToDialect(Args a) {
        System.out.println("To dialect                         : " + String.valueOf((Object)a.toDialect));
    }

    private static void displayFromDialect(Args a) {
        System.out.println("From dialect                       : " + String.valueOf((Object)a.fromDialect));
    }

    private static void displayFormatted(Args a) {
        System.out.println("Formatted                          : " + a.formatted);
    }

    private static void displayParseDateFormat(Args a) {
        System.out.println("Parse date format                  : " + a.parseDateFormat);
    }

    private static void displayParseIgnoreComments(Args a) {
        System.out.println("Parse ignore comments              : " + a.parseIgnoreComments);
    }

    private static void displayParseIgnoreCommentStart(Args a) {
        System.out.println("Parse ignore comment start         : " + a.parseIgnoreCommentStart);
    }

    private static void displayParseIgnoreCommentStop(Args a) {
        System.out.println("Parse ignore comment stop          : " + a.parseIgnoreCommentStop);
    }

    private static void displayParseLocale(Args a) {
        System.out.println("Parse locale                       : " + String.valueOf(a.parseLocale));
    }

    private static void displayParseNameCase(Args a) {
        System.out.println("Parse name case                    : " + String.valueOf((Object)a.parseNameCase));
    }

    private static void displayParseNamedParamPrefix(Args a) {
        System.out.println("Parse named param prefix           : " + a.parseNamedParamPrefix);
    }

    private static void displayParseRetainCommentsBetweenQueries(Args a) {
        System.out.println("Retain comments between queries    : " + a.parseRetainCommentsBetweenQueries);
    }

    private static void displayParseSetCommands(Args a) {
        System.out.println("Parse set commands                 : " + a.parseSetCommands);
    }

    private static void displayParseTimestampFormat(Args a) {
        System.out.println("Parse timestamp format             : " + a.parseTimestampFormat);
    }

    private static void displayParseUnknownFunctions(Args a) {
        System.out.println("Parse unknown functions            : " + String.valueOf((Object)a.parseUnknownFunctions));
    }

    private static void displayParseUnsupportedSyntax(Args a) {
        System.out.println("Parse unsupported syntax           : " + String.valueOf((Object)a.parseUnsupportedSyntax));
    }

    private static void displayRenderCoalesceToEmptyStringInConcat(Args a) {
        System.out.println("Render COALESCE(X, '') in CONCAT   : " + a.renderCoalesceToEmptyStringInConcat);
    }

    private static void displayRenderOptionalInnerKeyword(Args a) {
        System.out.println("Render INNER keyword in INNER JOIN : " + String.valueOf((Object)a.renderOptionalInnerKeyword));
    }

    private static void displayRenderOptionalOuterKeyword(Args a) {
        System.out.println("Render OUTER keyword in OUTER JOIN : " + String.valueOf((Object)a.renderOptionalOuterKeyword));
    }

    private static void displayRenderOptionalAsKeywordForFieldAliases(Args a) {
        System.out.println("Render AS keyword to alias fields  :" + String.valueOf((Object)a.renderOptionalAsKeywordForFieldAliases));
    }

    private static void displayRenderOptionalAsKeywordForTableAliases(Args a) {
        System.out.println("Render AS keyword to alias tables  :" + String.valueOf((Object)a.renderOptionalAsKeywordForTableAliases));
    }

    private static void displayTransformPatterns(Args a) {
        System.out.println("Transform patterns                 : " + a.transformPatterns);
    }

    private static void displayTransformAnsiJoinToTablesLists(Args a) {
        System.out.println("Transform ANSI join to table lists : " + a.transformAnsiJoinToTableLists);
    }

    private static void displayTransformQualify(Args a) {
        System.out.println("Transform QUALIFY                  : " + String.valueOf((Object)a.transformQualify));
    }

    private static void displayTransformRownum(Args a) {
        System.out.println("Transform ROWNUM                   : " + String.valueOf((Object)a.transformRownum));
    }

    private static void displayTransformGroupByColumnIndex(Args a) {
        System.out.println("Transform GROUP BY <column index>  : " + String.valueOf((Object)a.transformGroupByColumnIndex));
    }

    private static void displayTransformInlineCTE(Args a) {
        System.out.println("Transform inline CTE               : " + String.valueOf((Object)a.transformInlineCTE));
    }

    private static void displayTransformTableListsToAnsiJoin(Args a) {
        System.out.println("Transform table lists to ANSI join : " + a.transformTableListsToAnsiJoin);
    }

    private static void displayTransformUnneededArithmetic(Args a) {
        System.out.println("Transform unneeded arithmetic      : " + String.valueOf((Object)a.transformUnneededArithmetic));
    }

    private static final void render(DSLContext ctx, Args a) {
        String sql = a.sql.trim();
        if (!StringUtils.isBlank(a.schema)) {
            DSLContext c = ctx;
            ctx = ctx.configuration().derive(() -> c.meta(a.schema)).deriveSettings(s2 -> s2.withParseWithMetaLookups(ParseWithMetaLookups.THROW_ON_FAILURE)).dsl();
        }
        try {
            if (sql.matches("^(?is:(?:BEGIN|DECLARE|DO|FOR|IF|LOOP|REPEAT|WHILE).*)$")) {
                System.out.println(ctx.render(ctx.parser().parseStatements(a.sql)));
            } else {
                System.out.println(ctx.render(ctx.parser().parse(a.sql)));
            }
        }
        catch (ParserException e1) {
            ParserException e = e1;
            if (!sql.matches("^(?is:(?:ALTER|BEGIN|COMMENT|CREATE|DECLARE|DELETE|DESCRIBE|DROP|GRANT|INSERT|MERGE|RENAME|REVOKE|SELECT|SET|SHOW|TRUNCATE|UPDATE|USE).*)$")) {
                try {
                    System.out.println(ctx.render(ctx.parser().parseField(a.sql)));
                }
                catch (ParserException e2) {
                    e = e1.position() >= e2.position() ? e1 : e2;
                }
            }
            System.out.println(e.getMessage());
        }
    }

    private static final <E extends Enum<E>> E parse(Class<E> type, String value) {
        try {
            return Enum.valueOf(type, value.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            ParserCLI.invalid(value, type);
            throw e;
        }
    }

    private static final <E extends Enum<E>> Args parse(String[] args2) {
        Args result = new Args();
        for (int i = 0; i < args2.length; ++i) {
            Class enumArgument = null;
            try {
                if ("-f".equals(args2[i]) || "--formatted".equals(args2[i])) {
                    result.formatted = true;
                    continue;
                }
                if ("-k".equals(args2[i]) || "--keyword".equals(args2[i])) {
                    enumArgument = RenderKeywordCase.class;
                    result.keywords = ParserCLI.parse(RenderKeywordCase.class, args2[++i]);
                    continue;
                }
                if ("-i".equals(args2[i]) || "--identifier".equals(args2[i])) {
                    enumArgument = RenderNameCase.class;
                    result.name = ParserCLI.parse(RenderNameCase.class, args2[++i]);
                    continue;
                }
                if ("-Q".equals(args2[i]) || "--quoted".equals(args2[i])) {
                    enumArgument = RenderQuotedNames.class;
                    result.quoted = ParserCLI.parse(RenderQuotedNames.class, args2[++i]);
                    continue;
                }
                if ("-F".equals(args2[i]) || "--from-dialect".equals(args2[i])) {
                    enumArgument = SQLDialect.class;
                    result.fromDialect = ParserCLI.parse(SQLDialect.class, args2[++i]);
                    continue;
                }
                if ("-t".equals(args2[i]) || "-T".equals(args2[i]) || "--to-dialect".equals(args2[i])) {
                    enumArgument = SQLDialect.class;
                    result.toDialect = ParserCLI.parse(SQLDialect.class, args2[++i]);
                    continue;
                }
                if ("--parse-date-format".equals(args2[i])) {
                    result.parseDateFormat = args2[++i];
                    continue;
                }
                if ("--parse-ignore-comments".equals(args2[i])) {
                    result.parseIgnoreComments = true;
                    continue;
                }
                if ("--parse-ignore-comment-start".equals(args2[i])) {
                    result.parseIgnoreCommentStart = args2[++i];
                    continue;
                }
                if ("--parse-ignore-comment-stop".equals(args2[i])) {
                    result.parseIgnoreCommentStop = args2[++i];
                    continue;
                }
                if ("--parse-locale".equals(args2[i])) {
                    result.parseLocale = Locale.forLanguageTag(args2[++i]);
                    continue;
                }
                if ("--parse-name-case".equals(args2[i])) {
                    enumArgument = ParseNameCase.class;
                    result.parseNameCase = ParserCLI.parse(ParseNameCase.class, args2[++i]);
                    continue;
                }
                if ("--parse-named-param-prefix".equals(args2[i])) {
                    result.parseNamedParamPrefix = args2[++i];
                    continue;
                }
                if ("--parse-set-commands".equals(args2[i])) {
                    result.parseSetCommands = true;
                    continue;
                }
                if ("--parse-retain-comments-between-queries".equals(args2[i])) {
                    result.parseRetainCommentsBetweenQueries = true;
                    continue;
                }
                if ("--parse-timestamp-format".equals(args2[i])) {
                    result.parseTimestampFormat = args2[++i];
                    continue;
                }
                if ("--parse-unknown-functions".equals(args2[i])) {
                    enumArgument = ParseUnknownFunctions.class;
                    result.parseUnknownFunctions = ParserCLI.parse(ParseUnknownFunctions.class, args2[++i]);
                    continue;
                }
                if ("--parse-unsupported-syntax".equals(args2[i])) {
                    enumArgument = ParseUnsupportedSyntax.class;
                    result.parseUnsupportedSyntax = ParserCLI.parse(ParseUnsupportedSyntax.class, args2[++i]);
                    continue;
                }
                if ("--render-coalesce-to-empty-string-in-concat".equals(args2[i])) {
                    result.renderCoalesceToEmptyStringInConcat = true;
                    continue;
                }
                if ("--render-optional-inner-keyword".equals(args2[i])) {
                    enumArgument = RenderOptionalKeyword.class;
                    result.renderOptionalInnerKeyword = ParserCLI.parse(RenderOptionalKeyword.class, args2[++i]);
                    continue;
                }
                if ("--render-optional-outer-keyword".equals(args2[i])) {
                    enumArgument = RenderOptionalKeyword.class;
                    result.renderOptionalOuterKeyword = ParserCLI.parse(RenderOptionalKeyword.class, args2[++i]);
                    continue;
                }
                if ("--render-optional-as-keyword-for-field-aliases".equals(args2[i])) {
                    enumArgument = RenderOptionalKeyword.class;
                    result.renderOptionalAsKeywordForFieldAliases = ParserCLI.parse(RenderOptionalKeyword.class, args2[++i]);
                    continue;
                }
                if ("--render-optional-as-keyword-for-table-aliases".equals(args2[i])) {
                    enumArgument = RenderOptionalKeyword.class;
                    result.renderOptionalAsKeywordForTableAliases = ParserCLI.parse(RenderOptionalKeyword.class, args2[++i]);
                    continue;
                }
                if ("--transform-patterns".equals(args2[i])) {
                    result.transformPatterns = true;
                    continue;
                }
                if ("--transform-ansi-join-to-table-lists".equals(args2[i])) {
                    result.transformAnsiJoinToTableLists = true;
                    continue;
                }
                if ("--transform-qualify".equals(args2[i])) {
                    enumArgument = Transformation.class;
                    result.transformQualify = ParserCLI.parse(Transformation.class, args2[++i]);
                    continue;
                }
                if ("--transform-rownum".equals(args2[i])) {
                    enumArgument = Transformation.class;
                    result.transformRownum = ParserCLI.parse(Transformation.class, args2[++i]);
                    continue;
                }
                if ("--transform-group-by-column-index".equals(args2[i])) {
                    enumArgument = Transformation.class;
                    result.transformGroupByColumnIndex = ParserCLI.parse(Transformation.class, args2[++i]);
                    continue;
                }
                if ("--transform-inline-cte".equals(args2[i])) {
                    enumArgument = Transformation.class;
                    result.transformInlineCTE = ParserCLI.parse(Transformation.class, args2[++i]);
                    continue;
                }
                if ("--transform-table-lists-to-ansi-join".equals(args2[i])) {
                    result.transformTableListsToAnsiJoin = true;
                    continue;
                }
                if ("--transform-unneeded-arithmetic".equals(args2[i])) {
                    enumArgument = TransformUnneededArithmeticExpressions.class;
                    result.transformUnneededArithmetic = ParserCLI.parse(TransformUnneededArithmeticExpressions.class, args2[++i]);
                    continue;
                }
                if ("-S".equals(args2[i]) || "--schema".equals(args2[i])) {
                    result.schema = args2[++i];
                    continue;
                }
                if ("-s".equals(args2[i]) || "--sql".equals(args2[i])) {
                    result.sql = args2[++i];
                    continue;
                }
                if ("-I".equals(args2[i]) || "--interactive".equals(args2[i])) {
                    result.interactive = true;
                    continue;
                }
                if ("-h".equals(args2[i]) || "--help".equals(args2[i])) {
                    ParserCLI.help();
                    result.done = true;
                    continue;
                }
                System.out.println("Unknown flag: " + args2[i] + ". Use -h or --help");
                throw new RuntimeException();
            }
            catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Flag " + args2[i - 1] + " requires <" + (enumArgument != null ? enumArgument.getName() : "Unknown") + "> argument");
                throw e;
            }
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
        System.out.println("  -f / --formatted                                                    Format output SQL");
        System.out.println("  -h / --help                                                         Display this help");
        System.out.println("  -k / --keyword                                  <RenderKeywordCase> Specify the output keyword case (org.jooq.conf.RenderKeywordCase)");
        System.out.println("  -i / --identifier                               <RenderNameCase>    Specify the output identifier case (org.jooq.conf.RenderNameCase)");
        System.out.println("  -Q / --quoted                                   <RenderQuotedNames> Specify the output identifier quoting (org.jooq.conf.RenderQuotedNames)");
        System.out.println("  -F / --from-dialect                             <SQLDialect>        Specify the input dialect (org.jooq.SQLDialect)");
        System.out.println("  -T / --to-dialect                               <SQLDialect>        Specify the output dialect (org.jooq.SQLDialect)");
        System.out.println("  -S / --schema                                   <String>            Specify the input SQL schema");
        System.out.println("  -s / --sql                                      <String>            Specify the input SQL string");
        System.out.println("");
        System.out.println("Additional flags:");
        System.out.println("  --parse-date-format                             <String>");
        System.out.println("  --parse-locale                                  <Locale>");
        System.out.println("  --parse-name-case                               <ParseNameCase>");
        System.out.println("  --parse-named-param-prefix                      <String>");
        System.out.println("  --parse-retain-comments-between-queries");
        System.out.println("  --parse-set-commands");
        System.out.println("  --parse-timestamp-format                        <String>");
        System.out.println("  --parse-unknown-functions                       <ParseUnknownFunctions>");
        System.out.println("  --parse-unsupported-syntax                      <ParseUnsupportedSyntax>");
        System.out.println("  --render-optional-inner-keyword                 <RenderOptionalKeyword>");
        System.out.println("  --render-optional-outer-keyword                 <RenderOptionalKeyword>");
        System.out.println("  --render-optional-as-keyword-for-field-aliases  <RenderOptionalKeyword>");
        System.out.println("  --render-optional-as-keyword-for-table-aliases  <RenderOptionalKeyword>");
        System.out.println("");
        System.out.println("Commercial distribution only features:");
        System.out.println("  --render-coalesce-to-empty-string-in-concat");
        System.out.println("  --transform-patterns");
        System.out.println("  --transform-ansi-join-to-table-lists");
        System.out.println("  --transform-qualify                             <Transformation>");
        System.out.println("  --transform-rownum                              <Transformation>");
        System.out.println("  --transform-group-by-column-index               <Transformation>");
        System.out.println("  --transform-inline-cte                          <Transformation>");
        System.out.println("  --transform-table-lists-to-ansi-join");
        System.out.println("  --transform-unneeded-arithmetic                 <TransformUnneededArithmeticExpressions>");
        System.out.println("");
        System.out.println("  -I / --interactive                                               Start interactive mode");
    }

    private static final void helpInteractive() {
        System.out.println("Usage:");
        System.out.println("  /d  or  /display                                                   Display arguments");
        System.out.println("  /f  or  /formatted                             <boolean>           Format output SQL");
        System.out.println("  /h  or  /help                                                      Display this help");
        System.out.println("  /k  or  /keyword                               <RenderKeywordCase> Specify the output keyword case (org.jooq.conf.RenderKeywordCase)");
        System.out.println("  /i  or  /identifier                            <RenderNameCase>    Specify the output identifier case (org.jooq.conf.RenderNameCase)");
        System.out.println("  /Q  or  /quoted                                <RenderQuotedNames> Specify the output identifier quoting (org.jooq.conf.RenderQuotedNames)");
        System.out.println("  /F  or  /from-dialect                          <SQLDialect>        Specify the input dialect (org.jooq.SQLDialect)");
        System.out.println("  /T  or  /to-dialect                            <SQLDialect>        Specify the output dialect (org.jooq.SQLDialect)");
        System.out.println("  /S  or  /schema                                <String>            Specify the input SQL schema");
        System.out.println("                                                 <String>            Specify the input SQL string");
        System.out.println("");
        System.out.println("Additional flags:");
        System.out.println("  /parse-date-format                             <String>");
        System.out.println("  /parse-locale                                  <Locale>");
        System.out.println("  /parse-name-case                               <ParseNameCase>");
        System.out.println("  /parse-named-param-prefix                      <String>");
        System.out.println("  /parse-retain-comments-between-queries         <boolean>");
        System.out.println("  /parse-set-commands                            <boolean>");
        System.out.println("  /parse-timestamp-format                        <String>");
        System.out.println("  /parse-unknown-functions                       <ParseUnknownFunctions>");
        System.out.println("  /parse-unsupported-syntax                      <ParseUnsupportedSyntax>");
        System.out.println("  /render-optional-inner-keyword                 <RenderOptionalKeyword>");
        System.out.println("  /render-optional-outer-keyword                 <RenderOptionalKeyword>");
        System.out.println("  /render-optional-as-keyword-for-field-aliases  <RenderOptionalKeyword>");
        System.out.println("  /render-optional-as-keyword-for-table-aliases  <RenderOptionalKeyword>");
        System.out.println("");
        System.out.println("Commercial distribution only features:");
        System.out.println("  /render-coalesce-to-empty-string-in-concat     <boolean>");
        System.out.println("  /transform-patterns                            <boolean>");
        System.out.println("  /transform-ansi-join-to-table-lists            <boolean>");
        System.out.println("  /transform-qualify                             <Transformation>");
        System.out.println("  /transform-rownum                              <Transformation>");
        System.out.println("  /transform-group-by-column-index               <Transformation>");
        System.out.println("  /transform-inline-cte                          <Transformation>");
        System.out.println("  /transform-table-lists-to-ansi-join            <boolean>");
        System.out.println("  /transform-unneeded-arithmetic                 <TransformUnneededArithmeticExpressions>");
        System.out.println("");
        System.out.println("  /q  or  /quit   Quit");
        System.out.println("  /e  or  /exit   Also quit");
    }

    public static final class Args {
        Settings d = new Settings();
        List<String> history = new ArrayList<String>();
        boolean interactive;
        boolean done;
        String schema;
        String sql;
        RenderKeywordCase keywords = RenderKeywordCase.LOWER;
        RenderNameCase name = RenderNameCase.LOWER;
        RenderQuotedNames quoted = RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED;
        SQLDialect toDialect = SQLDialect.DEFAULT;
        SQLDialect fromDialect = SQLDialect.DEFAULT;
        Boolean formatted = this.d.isRenderFormatted();
        Boolean renderCoalesceToEmptyStringInConcat = this.d.isRenderCoalesceToEmptyStringInConcat();
        RenderOptionalKeyword renderOptionalInnerKeyword = RenderOptionalKeyword.DEFAULT;
        RenderOptionalKeyword renderOptionalOuterKeyword = RenderOptionalKeyword.DEFAULT;
        RenderOptionalKeyword renderOptionalAsKeywordForFieldAliases = RenderOptionalKeyword.DEFAULT;
        RenderOptionalKeyword renderOptionalAsKeywordForTableAliases = RenderOptionalKeyword.DEFAULT;
        String parseDateFormat = this.d.getParseDateFormat();
        Boolean parseIgnoreComments = this.d.isParseIgnoreComments();
        String parseIgnoreCommentStart = this.d.getParseIgnoreCommentStart();
        String parseIgnoreCommentStop = this.d.getParseIgnoreCommentStop();
        Locale parseLocale = this.d.getParseLocale();
        ParseNameCase parseNameCase = this.d.getParseNameCase();
        String parseNamedParamPrefix = this.d.getParseNamedParamPrefix();
        Boolean parseRetainCommentsBetweenQueries = this.d.isParseRetainCommentsBetweenQueries();
        Boolean parseSetCommands = this.d.isParseSetCommands();
        String parseTimestampFormat = this.d.getParseTimestampFormat();
        ParseUnknownFunctions parseUnknownFunctions = this.d.getParseUnknownFunctions();
        ParseUnsupportedSyntax parseUnsupportedSyntax = this.d.getParseUnsupportedSyntax();
        Boolean transformPatterns = this.d.isTransformPatterns();
        Boolean transformAnsiJoinToTableLists = this.d.isTransformAnsiJoinToTableLists();
        Transformation transformQualify = this.d.getTransformQualify();
        Transformation transformRownum = this.d.getTransformRownum();
        Transformation transformGroupByColumnIndex = this.d.getTransformGroupByColumnIndex();
        Transformation transformInlineCTE = this.d.getTransformInlineCTE();
        Boolean transformTableListsToAnsiJoin = this.d.isTransformTableListsToAnsiJoin();
        TransformUnneededArithmeticExpressions transformUnneededArithmetic = TransformUnneededArithmeticExpressions.NEVER;
    }
}

