/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.commons.compiler.InternalCompilerException;
import org.codehaus.commons.compiler.Location;
import org.codehaus.commons.compiler.WarningHandler;
import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.Java;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.Token;
import org.codehaus.janino.TokenStream;
import org.codehaus.janino.TokenStreamImpl;
import org.codehaus.janino.TokenType;

public class Parser {
    private final Scanner scanner;
    private final TokenStream tokenStream;
    @Nullable
    private String docComment;
    private static final String[] ACCESS_MODIFIER_KEYWORDS = new String[]{"public", "protected", "private", "static", "abstract", "final", "native", "synchronized", "transient", "volatile", "strictfp", "default", "transitive"};
    private boolean preferParametrizedTypes;
    private int sourceVersion = -1;
    @Nullable
    private WarningHandler warningHandler;
    private static final List<Set<String>> MUTUALLY_EXCLUSIVE_ACCESS_MODIFIERS = Arrays.asList(new HashSet<String>(Arrays.asList("public", "protected", "private")), new HashSet<String>(Arrays.asList("abstract", "final")));

    public Parser(Scanner scanner) {
        this(scanner, new TokenStreamImpl(scanner));
    }

    public Parser(Scanner scanner, TokenStream tokenStream) {
        this.scanner = scanner;
        this.scanner.setIgnoreWhiteSpace(true);
        this.tokenStream = tokenStream;
    }

    @Nullable
    public String doc() {
        String s2 = this.docComment;
        this.docComment = null;
        return s2;
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public Java.AbstractCompilationUnit parseAbstractCompilationUnit() throws CompileException, IOException {
        String docComment = this.doc();
        Java.Modifier[] modifiers = this.parseModifiers();
        Java.PackageDeclaration packageDeclaration = null;
        if (this.peek("package")) {
            packageDeclaration = this.parsePackageDeclarationRest(docComment, modifiers);
            docComment = this.doc();
            modifiers = this.parseModifiers();
        }
        ArrayList<Java.AbstractCompilationUnit.ImportDeclaration> l = new ArrayList<Java.AbstractCompilationUnit.ImportDeclaration>();
        while (this.peek("import")) {
            if (modifiers.length > 0) {
                this.warning("import.modifiers", "No modifiers allowed on import declarations");
            }
            if (docComment != null) {
                this.warning("import.doc_comment", "Doc comment on import declaration");
            }
            l.add(this.parseImportDeclaration());
            docComment = this.doc();
            modifiers = this.parseModifiers();
        }
        Java.AbstractCompilationUnit.ImportDeclaration[] importDeclarations = l.toArray(new Java.AbstractCompilationUnit.ImportDeclaration[l.size()]);
        if (this.peek("open", "module") != -1) {
            return new Java.ModularCompilationUnit(this.location().getFileName(), importDeclarations, this.parseModuleDeclarationRest(modifiers));
        }
        Java.CompilationUnit compilationUnit = new Java.CompilationUnit(this.location().getFileName(), importDeclarations);
        compilationUnit.setPackageDeclaration(packageDeclaration);
        if (this.peek(TokenType.END_OF_INPUT)) {
            return compilationUnit;
        }
        compilationUnit.addPackageMemberTypeDeclaration(this.parsePackageMemberTypeDeclarationRest(docComment, modifiers));
        while (!this.peek(TokenType.END_OF_INPUT)) {
            if (this.peekRead(";")) continue;
            compilationUnit.addPackageMemberTypeDeclaration(this.parsePackageMemberTypeDeclaration());
        }
        return compilationUnit;
    }

    public Java.ModuleDeclaration parseModuleDeclarationRest(Java.Modifier[] modifiers) throws CompileException, IOException {
        boolean isOpen = this.peekRead("open");
        this.read("module");
        String[] moduleName = this.parseQualifiedIdentifier();
        this.read("(");
        ArrayList<Java.UsesModuleDirective> moduleDirectives = new ArrayList<Java.UsesModuleDirective>();
        while (!this.peekRead(")")) {
            Java.Located md;
            switch (this.read("requires", "exports", "opens", "uses", "provides")) {
                case 0: {
                    md = new Java.RequiresModuleDirective(this.location(), this.parseModifiers(), this.parseQualifiedIdentifier());
                    break;
                }
                case 1: {
                    String[][] toModuleNames;
                    ArrayList<String[]> l;
                    String[] packageName = this.parseQualifiedIdentifier();
                    if (this.peekRead("to")) {
                        l = new ArrayList<String[]>();
                        l.add(this.parseQualifiedIdentifier());
                        while (this.peekRead(",")) {
                            l.add(this.parseQualifiedIdentifier());
                        }
                        toModuleNames = (String[][])l.toArray((T[])new String[l.size()][]);
                    } else {
                        toModuleNames = null;
                    }
                    md = new Java.ExportsModuleDirective(this.location(), packageName, toModuleNames);
                    break;
                }
                case 2: {
                    String[][] toModuleNames;
                    ArrayList<String[]> l;
                    String[] packageName = this.parseQualifiedIdentifier();
                    if (this.peekRead("to")) {
                        l = new ArrayList();
                        l.add(this.parseQualifiedIdentifier());
                        while (this.peekRead(",")) {
                            l.add(this.parseQualifiedIdentifier());
                        }
                        toModuleNames = (String[][])l.toArray((T[])new String[l.size()][]);
                    } else {
                        toModuleNames = null;
                    }
                    md = new Java.OpensModuleDirective(this.location(), packageName, toModuleNames);
                    break;
                }
                case 3: {
                    md = new Java.UsesModuleDirective(this.location(), this.parseQualifiedIdentifier());
                    break;
                }
                case 4: {
                    String[] typeName = this.parseQualifiedIdentifier();
                    this.read("with");
                    ArrayList<String[]> withTypeNames = new ArrayList<String[]>();
                    withTypeNames.add(this.parseQualifiedIdentifier());
                    while (this.peekRead(",")) {
                        withTypeNames.add(this.parseQualifiedIdentifier());
                    }
                    md = new Java.ProvidesModuleDirective(this.location(), typeName, (String[][])withTypeNames.toArray((T[])new String[withTypeNames.size()][]));
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
            this.read(";");
            moduleDirectives.add((Java.UsesModuleDirective)md);
        }
        return new Java.ModuleDeclaration(this.location(), modifiers, isOpen, moduleName, moduleDirectives.toArray(new Java.ModuleDirective[moduleDirectives.size()]));
    }

    public Java.PackageDeclaration parsePackageDeclaration() throws CompileException, IOException {
        return this.parsePackageDeclarationRest(this.doc(), this.parseModifiers());
    }

    public Java.PackageDeclaration parsePackageDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers) throws CompileException, IOException {
        this.packageModifiers(modifiers);
        this.read("package");
        Location loc = this.location();
        String packageName = Parser.join(this.parseQualifiedIdentifier(), ".");
        this.read(";");
        this.verifyStringIsConventionalPackageName(packageName, loc);
        return new Java.PackageDeclaration(loc, packageName);
    }

    public Java.AbstractCompilationUnit.ImportDeclaration parseImportDeclaration() throws CompileException, IOException {
        this.read("import");
        Java.AbstractCompilationUnit.ImportDeclaration importDeclaration = this.parseImportDeclarationBody();
        this.read(";");
        return importDeclaration;
    }

    public Java.AbstractCompilationUnit.ImportDeclaration parseImportDeclarationBody() throws CompileException, IOException {
        Location loc = this.location();
        boolean isStatic = this.peekRead("static");
        ArrayList<String> l = new ArrayList<String>();
        l.add(this.read(TokenType.IDENTIFIER));
        while (true) {
            if (!this.peek(".")) {
                String[] identifiers = l.toArray(new String[l.size()]);
                return isStatic ? new Java.AbstractCompilationUnit.SingleStaticImportDeclaration(loc, identifiers) : new Java.AbstractCompilationUnit.SingleTypeImportDeclaration(loc, identifiers);
            }
            this.read(".");
            if (this.peekRead("*")) {
                String[] identifiers = l.toArray(new String[l.size()]);
                return isStatic ? new Java.AbstractCompilationUnit.StaticImportOnDemandDeclaration(loc, identifiers) : new Java.AbstractCompilationUnit.TypeImportOnDemandDeclaration(loc, identifiers);
            }
            l.add(this.read(TokenType.IDENTIFIER));
        }
    }

    public String[] parseQualifiedIdentifier() throws CompileException, IOException {
        ArrayList<String> l = new ArrayList<String>();
        l.add(this.read(TokenType.IDENTIFIER));
        while (this.peek(".") && this.peekNextButOne().type == TokenType.IDENTIFIER) {
            this.read();
            l.add(this.read(TokenType.IDENTIFIER));
        }
        return l.toArray(new String[l.size()]);
    }

    public Java.PackageMemberTypeDeclaration parsePackageMemberTypeDeclaration() throws CompileException, IOException {
        return this.parsePackageMemberTypeDeclarationRest(this.doc(), this.parseModifiers());
    }

    private Java.PackageMemberTypeDeclaration parsePackageMemberTypeDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers) throws CompileException, IOException {
        switch (this.read("class", "enum", "interface", "@")) {
            case 0: {
                if (docComment == null) {
                    this.warning("CDCM", "Class doc comment missing");
                }
                return (Java.PackageMemberClassDeclaration)this.parseClassDeclarationRest(docComment, modifiers, ClassDeclarationContext.COMPILATION_UNIT);
            }
            case 1: {
                if (docComment == null) {
                    this.warning("EDCM", "Enum doc comment missing");
                }
                return (Java.PackageMemberEnumDeclaration)this.parseEnumDeclarationRest(docComment, modifiers, ClassDeclarationContext.COMPILATION_UNIT);
            }
            case 2: {
                if (docComment == null) {
                    this.warning("IDCM", "Interface doc comment missing");
                }
                return (Java.PackageMemberInterfaceDeclaration)this.parseInterfaceDeclarationRest(docComment, modifiers, InterfaceDeclarationContext.COMPILATION_UNIT);
            }
            case 3: {
                this.read("interface");
                if (docComment == null) {
                    this.warning("ATDCM", "Annotation type doc comment missing");
                }
                return (Java.PackageMemberAnnotationTypeDeclaration)this.parseAnnotationTypeDeclarationRest(docComment, modifiers, InterfaceDeclarationContext.COMPILATION_UNIT);
            }
        }
        throw new IllegalStateException();
    }

    public Java.Modifier[] parseModifiers() throws CompileException, IOException {
        Java.Modifier m4;
        ArrayList<Java.Modifier> result = new ArrayList<Java.Modifier>();
        while ((m4 = this.parseOptionalModifier()) != null) {
            result.add(m4);
        }
        return result.toArray(new Java.Modifier[result.size()]);
    }

    @Nullable
    public Java.Modifier parseOptionalModifier() throws CompileException, IOException {
        if (this.peek("@")) {
            if (this.peekNextButOne().value.equals("interface")) {
                return null;
            }
            return this.parseAnnotation();
        }
        int idx = this.peekRead(ACCESS_MODIFIER_KEYWORDS);
        if (idx == -1) {
            return null;
        }
        return new Java.AccessModifier(ACCESS_MODIFIER_KEYWORDS[idx], this.location());
    }

    private Java.Annotation parseAnnotation() throws CompileException, IOException {
        Java.ElementValuePair[] elementValuePairs;
        this.read("@");
        Java.ReferenceType type = new Java.ReferenceType(this.location(), new Java.Annotation[0], this.parseQualifiedIdentifier(), null);
        if (!this.peekRead("(")) {
            return new Java.MarkerAnnotation(type);
        }
        if (!this.peek(TokenType.IDENTIFIER) || !this.peekNextButOne("=")) {
            Java.ElementValue elementValue = this.parseElementValue();
            this.read(")");
            return new Java.SingleElementAnnotation(type, elementValue);
        }
        if (this.peekRead(")")) {
            elementValuePairs = new Java.ElementValuePair[]{};
        } else {
            ArrayList<Java.ElementValuePair> evps = new ArrayList<Java.ElementValuePair>();
            do {
                evps.add(this.parseElementValuePair());
            } while (this.read(",", ")") == 0);
            elementValuePairs = evps.toArray(new Java.ElementValuePair[evps.size()]);
        }
        return new Java.NormalAnnotation(type, elementValuePairs);
    }

    private Java.ElementValuePair parseElementValuePair() throws CompileException, IOException {
        String identifier = this.read(TokenType.IDENTIFIER);
        this.read("=");
        return new Java.ElementValuePair(identifier, this.parseElementValue());
    }

    private Java.ElementValue parseElementValue() throws CompileException, IOException {
        if (this.peek("@")) {
            return this.parseAnnotation();
        }
        if (this.peek("{")) {
            return this.parseElementValueArrayInitializer();
        }
        return this.parseConditionalAndExpression().toRvalueOrCompileException();
    }

    private Java.ElementValue parseElementValueArrayInitializer() throws CompileException, IOException {
        this.read("{");
        Location loc = this.location();
        ArrayList<Java.ElementValue> evs = new ArrayList<Java.ElementValue>();
        while (!this.peekRead("}")) {
            if (this.peekRead(",")) continue;
            evs.add(this.parseElementValue());
        }
        return new Java.ElementValueArrayInitializer(evs.toArray(new Java.ElementValue[evs.size()]), loc);
    }

    public Java.NamedClassDeclaration parseClassDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers, ClassDeclarationContext context) throws CompileException, IOException {
        Java.NamedClassDeclaration namedClassDeclaration;
        Location location = this.location();
        String className = this.read(TokenType.IDENTIFIER);
        this.verifyIdentifierIsConventionalClassOrInterfaceName(className, location);
        Java.TypeParameter[] typeParameters = this.parseTypeParametersOpt();
        Java.ReferenceType extendedType = null;
        if (this.peekRead("extends")) {
            extendedType = this.parseReferenceType();
        }
        Java.Type[] implementedTypes = new Java.ReferenceType[]{};
        if (this.peekRead("implements")) {
            implementedTypes = this.parseReferenceTypeList();
        }
        if (context == ClassDeclarationContext.COMPILATION_UNIT) {
            namedClassDeclaration = new Java.PackageMemberClassDeclaration(location, docComment, this.packageMemberClassModifiers(modifiers), className, typeParameters, extendedType, implementedTypes);
        } else if (context == ClassDeclarationContext.TYPE_DECLARATION) {
            namedClassDeclaration = new Java.MemberClassDeclaration(location, docComment, this.classModifiers(modifiers), className, typeParameters, extendedType, implementedTypes);
        } else if (context == ClassDeclarationContext.BLOCK) {
            namedClassDeclaration = new Java.LocalClassDeclaration(location, docComment, this.classModifiers(modifiers), className, typeParameters, extendedType, implementedTypes);
        } else {
            throw new InternalCompilerException("SNO: Class declaration in unexpected context " + (Object)((Object)context));
        }
        this.parseClassBody(namedClassDeclaration);
        return namedClassDeclaration;
    }

    public Java.EnumDeclaration parseEnumDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers, ClassDeclarationContext context) throws CompileException, IOException {
        Java.NamedClassDeclaration enumDeclaration;
        Location location = this.location();
        String enumName = this.read(TokenType.IDENTIFIER);
        this.verifyIdentifierIsConventionalClassOrInterfaceName(enumName, location);
        if (this.peekRead("<")) {
            throw this.compileException("Enum declaration must not have type parameters");
        }
        if (this.peekRead("extends")) {
            throw this.compileException("Enum declaration must not have an EXTENDS clause");
        }
        Java.Type[] implementedTypes = new Java.ReferenceType[]{};
        if (this.peekRead("implements")) {
            implementedTypes = this.parseReferenceTypeList();
        }
        if (context == ClassDeclarationContext.COMPILATION_UNIT) {
            enumDeclaration = new Java.PackageMemberEnumDeclaration(location, docComment, this.classModifiers(modifiers), enumName, implementedTypes);
        } else if (context == ClassDeclarationContext.TYPE_DECLARATION) {
            enumDeclaration = new Java.MemberEnumDeclaration(location, docComment, this.classModifiers(modifiers), enumName, implementedTypes);
        } else {
            throw new InternalCompilerException("SNO: Enum declaration in unexpected context " + (Object)((Object)context));
        }
        this.parseEnumBody((Java.EnumDeclaration)((Object)enumDeclaration));
        return enumDeclaration;
    }

    public void parseClassBody(Java.AbstractClassDeclaration classDeclaration) throws CompileException, IOException {
        this.read("{");
        while (!this.peekRead("}")) {
            this.parseClassBodyDeclaration(classDeclaration);
        }
        return;
    }

    public void parseEnumBody(Java.EnumDeclaration enumDeclaration) throws CompileException, IOException {
        this.read("{");
        while (this.peek(";", "}") == -1) {
            enumDeclaration.addConstant(this.parseEnumConstant());
            if (this.peekRead(",")) continue;
        }
        while (!this.peekRead("}")) {
            this.parseClassBodyDeclaration((Java.AbstractClassDeclaration)((Object)enumDeclaration));
        }
    }

    public Java.EnumConstant parseEnumConstant() throws CompileException, IOException {
        Java.EnumConstant result = new Java.EnumConstant(this.location(), this.doc(), this.enumConstantModifiers(this.parseModifiers()), this.read(TokenType.IDENTIFIER), this.peek("(") ? this.parseArguments() : null);
        if (this.peek("{")) {
            this.parseClassBody(result);
        }
        return result;
    }

    public void parseClassBodyDeclaration(Java.AbstractClassDeclaration classDeclaration) throws CompileException, IOException {
        if (this.peekRead(";")) {
            return;
        }
        String docComment = this.doc();
        Java.Modifier[] modifiers = this.parseModifiers();
        if (this.peek("{")) {
            if (Parser.hasAccessModifierOtherThan(modifiers, "static")) {
                throw this.compileException("Only access flag \"static\" allowed on initializer");
            }
            Java.Initializer initializer = new Java.Initializer(this.location(), modifiers, this.parseBlock());
            classDeclaration.addInitializer(initializer);
            return;
        }
        if (this.peekRead("void")) {
            Location location = this.location();
            String name = this.read(TokenType.IDENTIFIER);
            classDeclaration.addDeclaredMethod(this.parseMethodDeclarationRest(docComment, this.methodModifiers(modifiers), null, new Java.PrimitiveType(location, Java.Primitive.VOID), name, false, MethodDeclarationContext.CLASS_DECLARATION));
            return;
        }
        if (this.peekRead("class")) {
            if (docComment == null) {
                this.warning("MCDCM", "Member class doc comment missing");
            }
            classDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseClassDeclarationRest(docComment, this.classModifiers(modifiers), ClassDeclarationContext.TYPE_DECLARATION)));
            return;
        }
        if (this.peekRead("enum")) {
            if (docComment == null) {
                this.warning("MEDCM", "Member enum doc comment missing");
            }
            classDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseEnumDeclarationRest(docComment, this.classModifiers(modifiers), ClassDeclarationContext.TYPE_DECLARATION)));
            return;
        }
        if (this.peekRead("interface")) {
            if (docComment == null) {
                this.warning("MIDCM", "Member interface doc comment missing");
            }
            classDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseInterfaceDeclarationRest(docComment, this.interfaceModifiers(modifiers), InterfaceDeclarationContext.NAMED_TYPE_DECLARATION)));
            return;
        }
        if (this.peek("@") && this.peekNextButOne("interface")) {
            this.read();
            this.read();
            if (docComment == null) {
                this.warning("MATDCM", "Member annotation type doc comment missing", this.location());
            }
            classDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseInterfaceDeclarationRest(docComment, this.interfaceModifiers(modifiers), InterfaceDeclarationContext.NAMED_TYPE_DECLARATION)));
            return;
        }
        if (classDeclaration instanceof Java.NamedClassDeclaration && this.peek().value.equals(((Java.NamedClassDeclaration)classDeclaration).getName()) && this.peekNextButOne("(")) {
            if (docComment == null) {
                this.warning("CDCM", "Constructor doc comment missing", this.location());
            }
            classDeclaration.addConstructor(this.parseConstructorDeclarator(docComment, this.constructorModifiers(modifiers)));
            return;
        }
        Java.TypeParameter[] typeParameters = this.parseTypeParametersOpt();
        if (this.peekRead("void")) {
            String name = this.read(TokenType.IDENTIFIER);
            classDeclaration.addDeclaredMethod(this.parseMethodDeclarationRest(docComment, this.methodModifiers(modifiers), typeParameters, new Java.PrimitiveType(this.location(), Java.Primitive.VOID), name, false, MethodDeclarationContext.CLASS_DECLARATION));
            return;
        }
        Java.Type memberType = this.parseType();
        Location location = this.location();
        String memberName = this.read(TokenType.IDENTIFIER);
        if (this.peek("(")) {
            classDeclaration.addDeclaredMethod(this.parseMethodDeclarationRest(docComment, this.methodModifiers(modifiers), typeParameters, memberType, memberName, false, MethodDeclarationContext.CLASS_DECLARATION));
            return;
        }
        if (typeParameters != null) {
            throw this.compileException("Type parameters not allowed on field declaration");
        }
        if (docComment == null) {
            this.warning("FDCM", "Field doc comment missing", this.location());
        }
        Java.FieldDeclaration fd = new Java.FieldDeclaration(location, docComment, this.fieldModifiers(modifiers), memberType, this.parseFieldDeclarationRest(memberName));
        this.read(";");
        classDeclaration.addFieldDeclaration(fd);
    }

    public Java.InterfaceDeclaration parseInterfaceDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers, InterfaceDeclarationContext context) throws CompileException, IOException {
        Java.InterfaceDeclaration id;
        Location location = this.location();
        String interfaceName = this.read(TokenType.IDENTIFIER);
        this.verifyIdentifierIsConventionalClassOrInterfaceName(interfaceName, location);
        Java.TypeParameter[] typeParameters = this.parseTypeParametersOpt();
        Java.Type[] extendedTypes = new Java.ReferenceType[]{};
        if (this.peekRead("extends")) {
            extendedTypes = this.parseReferenceTypeList();
        }
        if (context == InterfaceDeclarationContext.COMPILATION_UNIT) {
            id = new Java.PackageMemberInterfaceDeclaration(location, docComment, this.packageMemberInterfaceModifiers(modifiers), interfaceName, typeParameters, extendedTypes);
        } else if (context == InterfaceDeclarationContext.NAMED_TYPE_DECLARATION) {
            id = new Java.MemberInterfaceDeclaration(location, docComment, this.interfaceModifiers(modifiers), interfaceName, typeParameters, extendedTypes);
        } else {
            throw new InternalCompilerException("SNO: Interface declaration in unexpected context " + (Object)((Object)context));
        }
        this.parseInterfaceBody(id);
        return id;
    }

    public Java.AnnotationTypeDeclaration parseAnnotationTypeDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers, InterfaceDeclarationContext context) throws CompileException, IOException {
        Java.InterfaceDeclaration atd;
        Location location = this.location();
        String annotationTypeName = this.read(TokenType.IDENTIFIER);
        this.verifyIdentifierIsConventionalClassOrInterfaceName(annotationTypeName, location);
        if (context == InterfaceDeclarationContext.COMPILATION_UNIT) {
            atd = new Java.PackageMemberAnnotationTypeDeclaration(location, docComment, this.packageMemberInterfaceModifiers(modifiers), annotationTypeName);
        } else if (context == InterfaceDeclarationContext.NAMED_TYPE_DECLARATION) {
            atd = new Java.MemberAnnotationTypeDeclaration(location, docComment, this.interfaceModifiers(modifiers), annotationTypeName);
        } else {
            throw new InternalCompilerException("SNO: Annotation type declaration in unexpected context " + (Object)((Object)context));
        }
        this.parseInterfaceBody(atd);
        return atd;
    }

    public void parseInterfaceBody(Java.InterfaceDeclaration interfaceDeclaration) throws CompileException, IOException {
        this.read("{");
        while (!this.peekRead("}")) {
            if (this.peekRead(";")) continue;
            String docComment = this.doc();
            Java.Modifier[] modifiers = this.parseModifiers();
            if (this.peekRead("void")) {
                interfaceDeclaration.addDeclaredMethod(this.parseMethodDeclarationRest(docComment, modifiers, null, new Java.PrimitiveType(this.location(), Java.Primitive.VOID), this.read(TokenType.IDENTIFIER), true, interfaceDeclaration instanceof Java.AnnotationTypeDeclaration ? MethodDeclarationContext.ANNOTATION_TYPE_DECLARATION : MethodDeclarationContext.INTERFACE_DECLARATION));
                continue;
            }
            if (this.peekRead("class")) {
                if (docComment == null) {
                    this.warning("MCDCM", "Member class doc comment missing", this.location());
                }
                if (Parser.hasAccessModifier(modifiers, "default")) {
                    throw this.compileException("Modifier \"default\" not allowed on member class declaration");
                }
                interfaceDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseClassDeclarationRest(docComment, this.classModifiers(modifiers), ClassDeclarationContext.TYPE_DECLARATION)));
                continue;
            }
            if (this.peekRead("enum")) {
                if (docComment == null) {
                    this.warning("MEDCM", "Member enum doc comment missing", this.location());
                }
                if (Parser.hasAccessModifier(modifiers, "default")) {
                    throw this.compileException("Modifier \"default\" not allowed on member enum declaration");
                }
                interfaceDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseClassDeclarationRest(docComment, this.classModifiers(modifiers), ClassDeclarationContext.TYPE_DECLARATION)));
                continue;
            }
            if (this.peekRead("interface")) {
                if (docComment == null) {
                    this.warning("MIDCM", "Member interface doc comment missing", this.location());
                }
                if (Parser.hasAccessModifier(modifiers, "default")) {
                    throw this.compileException("Modifier \"default\" not allowed on member interface declaration");
                }
                interfaceDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseInterfaceDeclarationRest(docComment, this.interfaceModifiers(modifiers), InterfaceDeclarationContext.NAMED_TYPE_DECLARATION)));
                continue;
            }
            if (this.peek("@") && this.peekNextButOne("interface")) {
                this.read();
                this.read();
                if (docComment == null) {
                    this.warning("MATDCM", "Member annotation type doc comment missing", this.location());
                }
                if (Parser.hasAccessModifier(modifiers, "default")) {
                    throw this.compileException("Modifier \"default\" not allowed on member annotation type declaration");
                }
                interfaceDeclaration.addMemberTypeDeclaration((Java.MemberTypeDeclaration)((Object)this.parseInterfaceDeclarationRest(docComment, this.interfaceModifiers(modifiers), InterfaceDeclarationContext.NAMED_TYPE_DECLARATION)));
                continue;
            }
            Java.TypeParameter[] typeParameters = this.parseTypeParametersOpt();
            if (this.peekRead("void")) {
                Location location = this.location();
                String name = this.read(TokenType.IDENTIFIER);
                interfaceDeclaration.addDeclaredMethod(this.parseMethodDeclarationRest(docComment, modifiers, typeParameters, new Java.PrimitiveType(location, Java.Primitive.VOID), name, true, interfaceDeclaration instanceof Java.AnnotationTypeDeclaration ? MethodDeclarationContext.ANNOTATION_TYPE_DECLARATION : MethodDeclarationContext.INTERFACE_DECLARATION));
                continue;
            }
            Java.Type memberType = this.parseType();
            String memberName = this.read(TokenType.IDENTIFIER);
            Location location = this.location();
            if (this.peek("(")) {
                if (this.getSourceVersion() < 8 && Parser.hasAccessModifier(modifiers, "static")) {
                    throw this.compileException("Static interface methods only available for source version 8+");
                }
                interfaceDeclaration.addDeclaredMethod(this.parseMethodDeclarationRest(docComment, modifiers, typeParameters, memberType, memberName, true, interfaceDeclaration instanceof Java.AnnotationTypeDeclaration ? MethodDeclarationContext.ANNOTATION_TYPE_DECLARATION : MethodDeclarationContext.INTERFACE_DECLARATION));
                continue;
            }
            if (typeParameters != null) {
                throw this.compileException("Type parameters not allowed with constant declaration");
            }
            if (docComment == null) {
                this.warning("CDCM", "Constant doc comment missing", this.location());
            }
            if (Parser.hasAccessModifier(modifiers, "default")) {
                throw this.compileException("Modifier \"default\" not allowed for constants");
            }
            Java.FieldDeclaration cd2 = new Java.FieldDeclaration(location, docComment, this.constantModifiers(modifiers), memberType, this.parseFieldDeclarationRest(memberName));
            interfaceDeclaration.addConstantDeclaration(cd2);
        }
    }

    public Java.ConstructorDeclarator parseConstructorDeclarator(@Nullable String docComment, Java.Modifier[] modifiers) throws CompileException, IOException {
        this.read(TokenType.IDENTIFIER);
        Java.FunctionDeclarator.FormalParameters formalParameters = this.parseFormalParameters();
        Java.Type[] thrownExceptions = this.peekRead("throws") ? this.parseReferenceTypeList() : new Java.ReferenceType[]{};
        Location location = this.location();
        this.read("{");
        Java.ConstructorInvocation constructorInvocation = null;
        ArrayList<Java.BlockStatement> statements = new ArrayList<Java.BlockStatement>();
        if (this.peek("this", "super", "new", "void", "byte", "char", "short", "int", "long", "float", "double", "boolean") != -1 || this.peekLiteral() || this.peek(TokenType.IDENTIFIER)) {
            Java.Atom a = this.parseExpressionOrType();
            if (a instanceof Java.ConstructorInvocation) {
                this.read(";");
                constructorInvocation = (Java.ConstructorInvocation)a;
            } else {
                Java.Statement s2;
                if (this.peek(TokenType.IDENTIFIER)) {
                    Java.Type variableType = a.toTypeOrCompileException();
                    s2 = new Java.LocalVariableDeclarationStatement(a.getLocation(), new Java.Modifier[0], variableType, this.parseVariableDeclarators());
                    this.read(";");
                } else {
                    s2 = new Java.ExpressionStatement(a.toRvalueOrCompileException());
                    this.read(";");
                }
                statements.add(s2);
            }
        }
        statements.addAll(this.parseBlockStatements());
        this.read("}");
        return new Java.ConstructorDeclarator(location, docComment, this.constructorModifiers(modifiers), formalParameters, thrownExceptions, constructorInvocation, statements);
    }

    public Java.MethodDeclarator parseMethodDeclaration() throws CompileException, IOException {
        return this.parseMethodDeclaration(false, MethodDeclarationContext.CLASS_DECLARATION);
    }

    public Java.MethodDeclarator parseMethodDeclaration(boolean allowDefaultClause, MethodDeclarationContext context) throws CompileException, IOException {
        return this.parseMethodDeclarationRest(this.doc(), this.parseModifiers(), this.parseTypeParametersOpt(), this.parseVoidOrType(), this.read(TokenType.IDENTIFIER), allowDefaultClause, context);
    }

    public Java.Type parseVoidOrType() throws CompileException, IOException {
        return this.peekRead("void") ? new Java.PrimitiveType(this.location(), Java.Primitive.VOID) : this.parseType();
    }

    public Java.MethodDeclarator parseMethodDeclarationRest(@Nullable String docComment, Java.Modifier[] modifiers, @Nullable Java.TypeParameter[] typeParameters, Java.Type type, String name, boolean allowDefaultClause, MethodDeclarationContext context) throws CompileException, IOException {
        List<Java.BlockStatement> statements;
        Java.ElementValue defaultValue;
        Location location = this.location();
        if (docComment == null) {
            this.warning("MDCM", "Method doc comment missing", location);
        }
        if (this.getSourceVersion() < 8 && Parser.hasAccessModifier(modifiers, "default")) {
            throw this.compileException("Default interface methods only available for source version 8+");
        }
        this.verifyIdentifierIsConventionalMethodName(name, location);
        Java.FunctionDeclarator.FormalParameters formalParameters = this.parseFormalParameters();
        for (int i = this.parseBracketsOpt(); i > 0; --i) {
            type = new Java.ArrayType(type);
        }
        Java.Type[] thrownExceptions = this.peekRead("throws") ? this.parseReferenceTypeList() : new Java.ReferenceType[]{};
        Java.ElementValue elementValue = defaultValue = allowDefaultClause && this.peekRead("default") ? this.parseElementValue() : null;
        if (this.peekRead(";")) {
            statements = null;
        } else {
            if (Parser.hasAccessModifier(modifiers, "abstract", "native")) {
                throw this.compileException("Abstract or native method must not have a body");
            }
            this.read("{");
            statements = this.parseBlockStatements();
            this.read("}");
        }
        return new Java.MethodDeclarator(location, docComment, context == MethodDeclarationContext.ANNOTATION_TYPE_DECLARATION ? this.annotationTypeElementModifiers(modifiers) : (context == MethodDeclarationContext.CLASS_DECLARATION ? this.methodModifiers(modifiers) : (context == MethodDeclarationContext.INTERFACE_DECLARATION ? this.interfaceMethodModifiers(modifiers) : new Java.Modifier[1])), typeParameters, type, name, formalParameters, thrownExceptions, defaultValue, statements);
    }

    private int getSourceVersion() {
        if (this.sourceVersion == -1) {
            this.sourceVersion = 11;
        }
        return this.sourceVersion;
    }

    public Java.ArrayInitializerOrRvalue parseVariableInitializer() throws CompileException, IOException {
        if (this.peek("{")) {
            return this.parseArrayInitializer();
        }
        return this.parseExpression();
    }

    public Java.ArrayInitializer parseArrayInitializer() throws CompileException, IOException {
        Location location = this.location();
        this.read("{");
        ArrayList<Java.ArrayInitializerOrRvalue> l = new ArrayList<Java.ArrayInitializerOrRvalue>();
        while (!this.peekRead("}")) {
            l.add(this.parseVariableInitializer());
            if (this.peekRead("}")) break;
            this.read(",");
        }
        return new Java.ArrayInitializer(location, l.toArray(new Java.ArrayInitializerOrRvalue[l.size()]));
    }

    public Java.FunctionDeclarator.FormalParameters parseFormalParameters() throws CompileException, IOException {
        this.read("(");
        if (this.peekRead(")")) {
            return new Java.FunctionDeclarator.FormalParameters(this.location());
        }
        Java.FunctionDeclarator.FormalParameters result = this.parseFormalParameterList();
        this.read(")");
        return result;
    }

    public Java.FunctionDeclarator.FormalParameters parseFormalParameterList() throws CompileException, IOException {
        ArrayList<Java.FunctionDeclarator.FormalParameter> l = new ArrayList<Java.FunctionDeclarator.FormalParameter>();
        boolean[] hasEllipsis = new boolean[1];
        do {
            if (hasEllipsis[0]) {
                throw this.compileException("Only the last parameter may have an ellipsis");
            }
            l.add(this.parseFormalParameter(hasEllipsis));
        } while (this.peekRead(","));
        return new Java.FunctionDeclarator.FormalParameters(this.location(), l.toArray(new Java.FunctionDeclarator.FormalParameter[l.size()]), hasEllipsis[0]);
    }

    public Java.FunctionDeclarator.FormalParameters parseFormalParameterListRest(Java.Type firstParameterType) throws CompileException, IOException {
        ArrayList<Java.FunctionDeclarator.FormalParameter> l = new ArrayList<Java.FunctionDeclarator.FormalParameter>();
        boolean[] hasEllipsis = new boolean[1];
        l.add(this.parseFormalParameterRest(new Java.Modifier[0], firstParameterType, hasEllipsis));
        while (this.peekRead(",")) {
            if (hasEllipsis[0]) {
                throw this.compileException("Only the last parameter may have an ellipsis");
            }
            l.add(this.parseFormalParameter(hasEllipsis));
        }
        return new Java.FunctionDeclarator.FormalParameters(this.location(), l.toArray(new Java.FunctionDeclarator.FormalParameter[l.size()]), hasEllipsis[0]);
    }

    public Java.FunctionDeclarator.FormalParameter parseFormalParameter(boolean[] hasEllipsis) throws CompileException, IOException {
        Java.Modifier[] modifiers = this.parseModifiers();
        if (Parser.hasAccessModifier(modifiers, "default")) {
            throw this.compileException("Modifier \"default\" not allowed on formal parameters");
        }
        return this.parseFormalParameterRest(modifiers, this.parseType(), hasEllipsis);
    }

    public Java.FunctionDeclarator.FormalParameter parseFormalParameterRest(Java.Modifier[] modifiers, Java.Type type, boolean[] hasEllipsis) throws CompileException, IOException {
        if (this.peekRead(".")) {
            this.read(".");
            this.read(".");
            hasEllipsis[0] = true;
        }
        Location location = this.location();
        String name = this.read(TokenType.IDENTIFIER);
        this.verifyIdentifierIsConventionalLocalVariableOrParameterName(name, location);
        for (int i = this.parseBracketsOpt(); i > 0; --i) {
            type = new Java.ArrayType(type);
        }
        return new Java.FunctionDeclarator.FormalParameter(location, modifiers, type, name);
    }

    public Java.CatchParameter parseCatchParameter() throws CompileException, IOException {
        Java.Modifier[] modifiers = this.parseModifiers();
        this.variableModifiers(modifiers);
        ArrayList<Java.ReferenceType> catchTypes = new ArrayList<Java.ReferenceType>();
        catchTypes.add(this.parseReferenceType());
        while (this.peekRead("|")) {
            catchTypes.add(this.parseReferenceType());
        }
        Location location = this.location();
        String name = this.read(TokenType.IDENTIFIER);
        this.verifyIdentifierIsConventionalLocalVariableOrParameterName(name, location);
        return new Java.CatchParameter(location, Parser.hasAccessModifier(modifiers, "final"), catchTypes.toArray(new Java.ReferenceType[catchTypes.size()]), name);
    }

    int parseBracketsOpt() throws CompileException, IOException {
        int res = 0;
        while (this.peek("[") && this.peekNextButOne("]")) {
            this.read();
            this.read();
            ++res;
        }
        return res;
    }

    public Java.Block parseMethodBody() throws CompileException, IOException {
        return this.parseBlock();
    }

    public Java.Block parseBlock() throws CompileException, IOException {
        this.read("{");
        Java.Block block = new Java.Block(this.location());
        block.addStatements(this.parseBlockStatements());
        this.read("}");
        return block;
    }

    public List<Java.BlockStatement> parseBlockStatements() throws CompileException, IOException {
        ArrayList<Java.BlockStatement> l = new ArrayList<Java.BlockStatement>();
        while (!(this.peek("}") || this.peek("case") || this.peek("default") || this.peek(TokenType.END_OF_INPUT))) {
            l.add(this.parseBlockStatement());
        }
        return l;
    }

    public Java.BlockStatement parseBlockStatement() throws CompileException, IOException {
        if (this.peek(TokenType.IDENTIFIER) && this.peekNextButOne(":") || this.peek("if", "for", "while", "do", "try", "switch", "synchronized", "return", "throw", "break", "continue", "assert") != -1 || this.peek("{", ";") != -1) {
            return this.parseStatement();
        }
        if (this.peekRead("class")) {
            String docComment = this.doc();
            if (docComment == null) {
                this.warning("LCDCM", "Local class doc comment missing", this.location());
            }
            Java.LocalClassDeclaration lcd = (Java.LocalClassDeclaration)this.parseClassDeclarationRest(docComment, new Java.Modifier[0], ClassDeclarationContext.BLOCK);
            return new Java.LocalClassDeclarationStatement(lcd);
        }
        if (this.peek("final", "@") != -1) {
            Java.LocalVariableDeclarationStatement lvds = new Java.LocalVariableDeclarationStatement(this.location(), this.variableModifiers(this.parseModifiers()), this.parseType(), this.parseVariableDeclarators());
            this.read(";");
            return lvds;
        }
        Java.Atom a = this.parseExpressionOrType();
        if (this.peekRead(";")) {
            return new Java.ExpressionStatement(a.toRvalueOrCompileException());
        }
        Java.Type variableType = a.toTypeOrCompileException();
        for (int i = this.parseBracketsOpt(); i > 0; --i) {
            variableType = new Java.ArrayType(variableType);
        }
        Java.LocalVariableDeclarationStatement lvds = new Java.LocalVariableDeclarationStatement(a.getLocation(), new Java.Modifier[0], variableType, this.parseVariableDeclarators());
        this.read(";");
        return lvds;
    }

    public Java.VariableDeclarator[] parseVariableDeclarators() throws CompileException, IOException {
        ArrayList<Java.VariableDeclarator> l = new ArrayList<Java.VariableDeclarator>();
        do {
            Java.VariableDeclarator vd = this.parseVariableDeclarator();
            this.verifyIdentifierIsConventionalLocalVariableOrParameterName(vd.name, vd.getLocation());
            l.add(vd);
        } while (this.peekRead(","));
        return l.toArray(new Java.VariableDeclarator[l.size()]);
    }

    public Java.VariableDeclarator[] parseFieldDeclarationRest(String name) throws CompileException, IOException {
        ArrayList<Java.VariableDeclarator> l = new ArrayList<Java.VariableDeclarator>();
        Java.VariableDeclarator vd = this.parseVariableDeclaratorRest(name);
        this.verifyIdentifierIsConventionalFieldName(vd.name, vd.getLocation());
        l.add(vd);
        while (this.peekRead(",")) {
            vd = this.parseVariableDeclarator();
            this.verifyIdentifierIsConventionalFieldName(vd.name, vd.getLocation());
            l.add(vd);
        }
        return l.toArray(new Java.VariableDeclarator[l.size()]);
    }

    public Java.VariableDeclarator parseVariableDeclarator() throws CompileException, IOException {
        return this.parseVariableDeclaratorRest(this.read(TokenType.IDENTIFIER));
    }

    public Java.VariableDeclarator parseVariableDeclaratorRest(String name) throws CompileException, IOException {
        Location loc = this.location();
        int brackets = this.parseBracketsOpt();
        Java.ArrayInitializerOrRvalue initializer = this.peekRead("=") ? this.parseVariableInitializer() : null;
        return new Java.VariableDeclarator(loc, name, brackets, initializer);
    }

    public Java.Statement parseStatement() throws CompileException, IOException {
        if (this.peek(TokenType.IDENTIFIER) && this.peekNextButOne(":")) {
            return this.parseLabeledStatement();
        }
        Java.Block stmt = this.peek("{") ? this.parseBlock() : (this.peek("if") ? this.parseIfStatement() : (this.peek("for") ? this.parseForStatement() : (this.peek("while") ? this.parseWhileStatement() : (this.peek("do") ? this.parseDoStatement() : (this.peek("try") ? this.parseTryStatement() : (this.peek("switch") ? this.parseSwitchStatement() : (this.peek("synchronized") ? this.parseSynchronizedStatement() : (this.peek("return") ? this.parseReturnStatement() : (this.peek("throw") ? this.parseThrowStatement() : (this.peek("break") ? this.parseBreakStatement() : (this.peek("continue") ? this.parseContinueStatement() : (this.peek("assert") ? this.parseAssertStatement() : (this.peek(";") ? this.parseEmptyStatement() : this.parseExpressionStatement())))))))))))));
        return stmt;
    }

    public Java.Statement parseLabeledStatement() throws CompileException, IOException {
        String label = this.read(TokenType.IDENTIFIER);
        this.read(":");
        return new Java.LabeledStatement(this.location(), label, this.parseStatement());
    }

    public Java.Statement parseIfStatement() throws CompileException, IOException {
        this.read("if");
        Location location = this.location();
        this.read("(");
        Java.Rvalue condition = this.parseExpression();
        this.read(")");
        Java.Statement thenStatement = this.parseStatement();
        Java.Statement elseStatement = this.peekRead("else") ? this.parseStatement() : null;
        return new Java.IfStatement(location, condition, thenStatement, elseStatement);
    }

    public Java.Statement parseForStatement() throws CompileException, IOException {
        this.read("for");
        Location forLocation = this.location();
        this.read("(");
        Java.Statement init2 = null;
        if (!this.peek(";")) {
            if (this.peek("final", "@", "byte", "short", "char", "int", "long", "float", "double", "boolean") != -1) {
                Java.Modifier[] modifiers = this.parseModifiers();
                Java.Type type = this.parseType();
                if (this.peek(TokenType.IDENTIFIER) && this.peekNextButOne(":")) {
                    String name = this.read(TokenType.IDENTIFIER);
                    Location nameLocation = this.location();
                    this.read(":");
                    Java.Rvalue expression = this.parseExpression();
                    this.read(")");
                    return new Java.ForEachStatement(forLocation, new Java.FunctionDeclarator.FormalParameter(nameLocation, modifiers, type, name), expression, this.parseStatement());
                }
                init2 = new Java.LocalVariableDeclarationStatement(this.location(), this.variableModifiers(modifiers), type, this.parseVariableDeclarators());
            } else {
                Java.Atom a = this.parseExpressionOrType();
                if (this.peek(TokenType.IDENTIFIER)) {
                    if (this.peekNextButOne(":")) {
                        String name = this.read(TokenType.IDENTIFIER);
                        Location nameLocation = this.location();
                        this.read(":");
                        Java.Rvalue expression = this.parseExpression();
                        this.read(")");
                        return new Java.ForEachStatement(forLocation, new Java.FunctionDeclarator.FormalParameter(nameLocation, new Java.Modifier[0], a.toTypeOrCompileException(), name), expression, this.parseStatement());
                    }
                    init2 = new Java.LocalVariableDeclarationStatement(this.location(), new Java.Modifier[0], a.toTypeOrCompileException(), this.parseVariableDeclarators());
                } else if (!this.peekRead(",")) {
                    init2 = new Java.ExpressionStatement(a.toRvalueOrCompileException());
                } else {
                    ArrayList<Java.ExpressionStatement> l = new ArrayList<Java.ExpressionStatement>();
                    l.add(new Java.ExpressionStatement(a.toRvalueOrCompileException()));
                    do {
                        l.add(new Java.ExpressionStatement(this.parseExpression()));
                    } while (this.peekRead(","));
                    Java.Block b = new Java.Block(a.getLocation());
                    b.addStatements(l);
                    init2 = b;
                }
            }
        }
        this.read(";");
        Java.Rvalue condition = this.peek(";") ? null : this.parseExpression();
        this.read(";");
        Java.Rvalue[] update = null;
        if (!this.peek(")")) {
            update = this.parseExpressionList();
        }
        this.read(")");
        return new Java.ForStatement(forLocation, init2, condition, update, this.parseStatement());
    }

    public Java.Statement parseWhileStatement() throws CompileException, IOException {
        Location location = this.location();
        this.read("while");
        this.read("(");
        Java.Rvalue condition = this.parseExpression();
        this.read(")");
        return new Java.WhileStatement(location, condition, this.parseStatement());
    }

    public Java.Statement parseDoStatement() throws CompileException, IOException {
        Location location = this.location();
        this.read("do");
        Java.Statement body2 = this.parseStatement();
        this.read("while");
        this.read("(");
        Java.Rvalue condition = this.parseExpression();
        this.read(")");
        this.read(";");
        return new Java.DoStatement(location, body2, condition);
    }

    public Java.Statement parseTryStatement() throws CompileException, IOException {
        Java.Block finallY;
        this.read("try");
        Location location = this.location();
        ArrayList<Java.TryStatement.Resource> resources = new ArrayList<Java.TryStatement.Resource>();
        if (this.peekRead("(")) {
            resources.add(this.parseResource());
            block4: while (true) {
                switch (this.read(";", ")")) {
                    case 0: {
                        if (this.peekRead(")")) break block4;
                        resources.add(this.parseResource());
                        continue block4;
                    }
                    case 1: {
                        break block4;
                    }
                    default: {
                        throw new AssertionError();
                    }
                }
                break;
            }
        }
        Java.Block body2 = this.parseBlock();
        ArrayList<Java.CatchClause> ccs = new ArrayList<Java.CatchClause>();
        while (this.peekRead("catch")) {
            Location loc = this.location();
            this.read("(");
            Java.CatchParameter catchParameter = this.parseCatchParameter();
            this.read(")");
            ccs.add(new Java.CatchClause(loc, catchParameter, this.parseBlock()));
        }
        Java.Block block = finallY = this.peekRead("finally") ? this.parseBlock() : null;
        if (resources.isEmpty() && ccs.isEmpty() && finallY == null) {
            throw this.compileException("\"try\" statement must have at least one resource, \"catch\" clause or \"finally\" clause");
        }
        return new Java.TryStatement(location, resources, body2, ccs, finallY);
    }

    private Java.TryStatement.Resource parseResource() throws CompileException, IOException {
        Location loc = this.location();
        Java.Modifier[] modifiers = this.parseModifiers();
        Java.Atom a = this.parseExpressionOrType();
        if (modifiers.length > 0 || this.peek(TokenType.IDENTIFIER)) {
            if (Parser.hasAccessModifier(modifiers, "default")) {
                throw this.compileException("Modifier \"default\" not allowed on resource");
            }
            return new Java.TryStatement.LocalVariableDeclaratorResource(loc, this.variableModifiers(modifiers), a.toTypeOrCompileException(), this.parseVariableDeclarator());
        }
        Java.Rvalue rv = a.toRvalueOrCompileException();
        if (!(rv instanceof Java.FieldAccess)) {
            this.compileException("Rvalue " + rv.getClass().getSimpleName() + " disallowed as a resource");
        }
        return new Java.TryStatement.VariableAccessResource(loc, rv);
    }

    public Java.Statement parseSwitchStatement() throws CompileException, IOException {
        Location location = this.location();
        this.read("switch");
        this.read("(");
        Java.Rvalue condition = this.parseExpression();
        this.read(")");
        this.read("{");
        ArrayList<Java.SwitchStatement.SwitchBlockStatementGroup> sbsgs = new ArrayList<Java.SwitchStatement.SwitchBlockStatementGroup>();
        while (!this.peekRead("}")) {
            Location location2 = this.location();
            boolean hasDefaultLabel = false;
            ArrayList<Java.Rvalue> caseLabels = new ArrayList<Java.Rvalue>();
            do {
                if (this.peekRead("case")) {
                    caseLabels.add(this.parseExpression());
                } else if (this.peekRead("default")) {
                    if (hasDefaultLabel) {
                        throw this.compileException("Duplicate \"default\" label");
                    }
                    hasDefaultLabel = true;
                } else {
                    throw this.compileException("\"case\" or \"default\" expected");
                }
                this.read(":");
            } while (this.peek("case", "default") != -1);
            Java.SwitchStatement.SwitchBlockStatementGroup sbsg = new Java.SwitchStatement.SwitchBlockStatementGroup(location2, caseLabels, hasDefaultLabel, this.parseBlockStatements());
            sbsgs.add(sbsg);
        }
        return new Java.SwitchStatement(location, condition, sbsgs);
    }

    public Java.Statement parseSynchronizedStatement() throws CompileException, IOException {
        Location location = this.location();
        this.read("synchronized");
        this.read("(");
        Java.Rvalue expression = this.parseExpression();
        this.read(")");
        return new Java.SynchronizedStatement(location, expression, this.parseBlock());
    }

    public Java.Statement parseReturnStatement() throws CompileException, IOException {
        this.read("return");
        Location location = this.location();
        Java.Rvalue returnValue = this.peek(";") ? null : this.parseExpression();
        this.read(";");
        return new Java.ReturnStatement(location, returnValue);
    }

    public Java.Statement parseThrowStatement() throws CompileException, IOException {
        this.read("throw");
        Location location = this.location();
        Java.Rvalue expression = this.parseExpression();
        this.read(";");
        return new Java.ThrowStatement(location, expression);
    }

    public Java.Statement parseBreakStatement() throws CompileException, IOException {
        this.read("break");
        Location location = this.location();
        String label = null;
        if (this.peek(TokenType.IDENTIFIER)) {
            label = this.read(TokenType.IDENTIFIER);
        }
        this.read(";");
        return new Java.BreakStatement(location, label);
    }

    public Java.Statement parseContinueStatement() throws CompileException, IOException {
        this.read("continue");
        Location location = this.location();
        String label = this.peekRead(TokenType.IDENTIFIER);
        this.read(";");
        return new Java.ContinueStatement(location, label);
    }

    public Java.Statement parseAssertStatement() throws CompileException, IOException {
        this.read("assert");
        Location loc = this.location();
        Java.Rvalue expression1 = this.parseExpression();
        Java.Rvalue expression2 = this.peekRead(":") ? this.parseExpression() : null;
        this.read(";");
        return new Java.AssertStatement(loc, expression1, expression2);
    }

    public Java.Statement parseEmptyStatement() throws CompileException, IOException {
        this.read(";");
        Location location = this.location();
        return new Java.EmptyStatement(location);
    }

    public Java.Rvalue[] parseExpressionList() throws CompileException, IOException {
        ArrayList<Java.Rvalue> l = new ArrayList<Java.Rvalue>();
        do {
            l.add(this.parseExpression());
        } while (this.peekRead(","));
        return l.toArray(new Java.Rvalue[l.size()]);
    }

    public Java.Type parseType() throws CompileException, IOException {
        Java.Type res;
        switch (this.peekRead("byte", "short", "char", "int", "long", "float", "double", "boolean")) {
            case 0: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.BYTE);
                break;
            }
            case 1: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.SHORT);
                break;
            }
            case 2: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.CHAR);
                break;
            }
            case 3: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.INT);
                break;
            }
            case 4: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.LONG);
                break;
            }
            case 5: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.FLOAT);
                break;
            }
            case 6: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.DOUBLE);
                break;
            }
            case 7: {
                res = new Java.PrimitiveType(this.location(), Java.Primitive.BOOLEAN);
                break;
            }
            case -1: {
                res = this.parseReferenceType();
                break;
            }
            default: {
                throw new AssertionError();
            }
        }
        for (int i = this.parseBracketsOpt(); i > 0; --i) {
            res = new Java.ArrayType(res);
        }
        return res;
    }

    public Java.ReferenceType parseReferenceType() throws CompileException, IOException {
        ArrayList<Java.Annotation> annotations = new ArrayList<Java.Annotation>();
        while (this.peek("@")) {
            annotations.add(this.parseAnnotation());
        }
        return new Java.ReferenceType(this.location(), annotations.toArray(new Java.Annotation[annotations.size()]), this.parseQualifiedIdentifier(), this.parseTypeArgumentsOpt());
    }

    @Nullable
    private Java.TypeParameter[] parseTypeParametersOpt() throws CompileException, IOException {
        if (!this.peekRead("<")) {
            return null;
        }
        ArrayList<Java.TypeParameter> l = new ArrayList<Java.TypeParameter>();
        l.add(this.parseTypeParameter());
        while (this.read(",", ">") == 0) {
            l.add(this.parseTypeParameter());
        }
        return l.toArray(new Java.TypeParameter[l.size()]);
    }

    private Java.TypeParameter parseTypeParameter() throws CompileException, IOException {
        String name = this.read(TokenType.IDENTIFIER);
        if (this.peekRead("extends")) {
            ArrayList<Java.ReferenceType> bound2 = new ArrayList<Java.ReferenceType>();
            bound2.add(this.parseReferenceType());
            while (this.peekRead("&")) {
                this.parseReferenceType();
            }
            return new Java.TypeParameter(name, bound2.toArray(new Java.ReferenceType[bound2.size()]));
        }
        return new Java.TypeParameter(name, null);
    }

    @Nullable
    private Java.TypeArgument[] parseTypeArgumentsOpt() throws CompileException, IOException {
        if (!this.peekRead("<")) {
            return null;
        }
        if (this.peekRead(">")) {
            return new Java.TypeArgument[0];
        }
        ArrayList<Java.TypeArgument> typeArguments = new ArrayList<Java.TypeArgument>();
        typeArguments.add(this.parseTypeArgument());
        while (this.read(">", ",") == 1) {
            typeArguments.add(this.parseTypeArgument());
        }
        return typeArguments.toArray(new Java.TypeArgument[typeArguments.size()]);
    }

    private Java.TypeArgument parseTypeArgument() throws CompileException, IOException {
        if (this.peekRead("?")) {
            return this.peekRead("extends") ? new Java.Wildcard(1, this.parseReferenceType()) : (this.peekRead("super") ? new Java.Wildcard(2, this.parseReferenceType()) : new Java.Wildcard());
        }
        Java.Type t2 = this.parseType();
        for (int i = this.parseBracketsOpt(); i > 0; --i) {
            t2 = new Java.ArrayType(t2);
        }
        if (!(t2 instanceof Java.TypeArgument)) {
            throw this.compileException("'" + t2 + "' is not a valid type argument");
        }
        return (Java.TypeArgument)((Object)t2);
    }

    public Java.ReferenceType[] parseReferenceTypeList() throws CompileException, IOException {
        ArrayList<Java.ReferenceType> l = new ArrayList<Java.ReferenceType>();
        l.add(this.parseReferenceType());
        while (this.peekRead(",")) {
            l.add(this.parseReferenceType());
        }
        return l.toArray(new Java.ReferenceType[l.size()]);
    }

    public Java.Rvalue parseExpression() throws CompileException, IOException {
        if (this.peek(TokenType.IDENTIFIER) && this.peekNextButOne("->")) {
            return this.parseLambdaExpression();
        }
        return this.parseAssignmentExpression().toRvalueOrCompileException();
    }

    public Java.Atom parseExpressionOrType() throws CompileException, IOException {
        if (this.peek(TokenType.IDENTIFIER) && this.peekNextButOne("->")) {
            return this.parseLambdaExpression();
        }
        this.preferParametrizedTypes = true;
        try {
            Java.Atom atom = this.parseAssignmentExpression();
            return atom;
        }
        finally {
            this.preferParametrizedTypes = false;
        }
    }

    public Java.Atom parseAssignmentExpression() throws CompileException, IOException {
        Java.Atom a = this.parseConditionalExpression();
        if (this.peek("=", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=") != -1) {
            Java.Lvalue lhs = a.toLvalueOrCompileException();
            Location location = this.location();
            String operator = this.read(TokenType.OPERATOR);
            Java.Rvalue rhs = this.parseAssignmentExpression().toRvalueOrCompileException();
            return new Java.Assignment(location, lhs, operator, rhs);
        }
        return a;
    }

    public Java.Atom parseConditionalExpression() throws CompileException, IOException {
        Java.Atom a = this.parseConditionalOrExpression();
        if (!this.peekRead("?")) {
            return a;
        }
        Location location = this.location();
        Java.Rvalue lhs = a.toRvalueOrCompileException();
        Java.Rvalue mhs = this.parseExpression();
        this.read(":");
        Java.Rvalue rhs = this.parseConditionalExpression().toRvalueOrCompileException();
        return new Java.ConditionalExpression(location, lhs, mhs, rhs);
    }

    public Java.Atom parseConditionalOrExpression() throws CompileException, IOException {
        Java.Atom a = this.parseConditionalAndExpression();
        while (this.peekRead("||")) {
            Location location = this.location();
            a = new Java.BinaryOperation(location, a.toRvalueOrCompileException(), "||", this.parseConditionalAndExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseConditionalAndExpression() throws CompileException, IOException {
        Java.Atom a = this.parseInclusiveOrExpression();
        while (this.peekRead("&&")) {
            Location location = this.location();
            a = new Java.BinaryOperation(location, a.toRvalueOrCompileException(), "&&", this.parseInclusiveOrExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseInclusiveOrExpression() throws CompileException, IOException {
        Java.Atom a = this.parseExclusiveOrExpression();
        while (this.peekRead("|")) {
            Location location = this.location();
            a = new Java.BinaryOperation(location, a.toRvalueOrCompileException(), "|", this.parseExclusiveOrExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseExclusiveOrExpression() throws CompileException, IOException {
        Java.Atom a = this.parseAndExpression();
        while (this.peekRead("^")) {
            Location location = this.location();
            a = new Java.BinaryOperation(location, a.toRvalueOrCompileException(), "^", this.parseAndExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseAndExpression() throws CompileException, IOException {
        Java.Atom a = this.parseEqualityExpression();
        while (this.peekRead("&")) {
            Location location = this.location();
            a = new Java.BinaryOperation(location, a.toRvalueOrCompileException(), "&", this.parseEqualityExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseEqualityExpression() throws CompileException, IOException {
        Java.Atom a = this.parseRelationalExpression();
        while (this.peek("==", "!=") != -1) {
            a = new Java.BinaryOperation(this.location(), a.toRvalueOrCompileException(), this.read().value, this.parseRelationalExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseRelationalExpression() throws CompileException, IOException {
        Java.Atom a = this.parseShiftExpression();
        while (true) {
            if (this.peekRead("instanceof")) {
                Location location = this.location();
                a = new Java.Instanceof(location, a.toRvalueOrCompileException(), this.parseType());
                continue;
            }
            if (this.peek("<", ">", "<=", ">=") == -1) break;
            if (this.preferParametrizedTypes && a instanceof Java.AmbiguousName && this.peek("<") && this.peekNextButOne("?")) {
                return new Java.ReferenceType(this.location(), new Java.Annotation[0], ((Java.AmbiguousName)a).identifiers, this.parseTypeArgumentsOpt());
            }
            String operator = this.read().value;
            Java.Atom rhs = this.parseShiftExpression();
            if (this.preferParametrizedTypes && "<".equals(operator) && this.peek("<", ">", ",") != -1 && a instanceof Java.AmbiguousName && rhs.toType() != null) {
                Java.Type firstTypeArgument;
                String[] identifiers = ((Java.AmbiguousName)a).identifiers;
                this.parseTypeArgumentsOpt();
                Java.Type t2 = rhs.toTypeOrCompileException();
                if (t2 instanceof Java.ArrayType) {
                    firstTypeArgument = (Java.ArrayType)t2;
                } else if (t2 instanceof Java.ReferenceType) {
                    firstTypeArgument = (Java.ReferenceType)t2;
                } else {
                    throw this.compileException("'" + t2 + "' is not a valid type argument");
                }
                ArrayList<Java.TypeArgument> typeArguments = new ArrayList<Java.TypeArgument>();
                typeArguments.add((Java.TypeArgument)((Object)firstTypeArgument));
                while (this.read(">", ",") == 1) {
                    typeArguments.add(this.parseTypeArgument());
                }
                return new Java.ReferenceType(this.location(), new Java.Annotation[0], identifiers, typeArguments.toArray(new Java.TypeArgument[typeArguments.size()]));
            }
            a = new Java.BinaryOperation(this.location(), a.toRvalueOrCompileException(), operator, rhs.toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseShiftExpression() throws CompileException, IOException {
        Java.Atom a = this.parseAdditiveExpression();
        while (this.peek("<<", ">>", ">>>") != -1) {
            a = new Java.BinaryOperation(this.location(), a.toRvalueOrCompileException(), this.read().value, this.parseAdditiveExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseAdditiveExpression() throws CompileException, IOException {
        Java.Atom a = this.parseMultiplicativeExpression();
        while (this.peek("+", "-") != -1) {
            a = new Java.BinaryOperation(this.location(), a.toRvalueOrCompileException(), this.read().value, this.parseMultiplicativeExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseMultiplicativeExpression() throws CompileException, IOException {
        Java.Atom a = this.parseUnaryExpression();
        while (this.peek("*", "/", "%") != -1) {
            a = new Java.BinaryOperation(this.location(), a.toRvalueOrCompileException(), this.read().value, this.parseUnaryExpression().toRvalueOrCompileException());
        }
        return a;
    }

    public Java.Atom parseUnaryExpression() throws CompileException, IOException {
        if (this.peek("++", "--") != -1) {
            return new Java.Crement(this.location(), this.read().value, this.parseUnaryExpression().toLvalueOrCompileException());
        }
        if (this.peek("+", "-", "~", "!") != -1) {
            return new Java.UnaryOperation(this.location(), this.read().value, this.parseUnaryExpression().toRvalueOrCompileException());
        }
        Java.Atom a = this.parsePrimary();
        while (this.peek(".", "[") != -1) {
            a = this.parseSelector(a);
        }
        if (this.peekRead("::")) {
            if (a instanceof Java.ArrayType) {
                this.read("new");
                return new Java.ArrayCreationReference(this.location(), (Java.ArrayType)a);
            }
            Java.TypeArgument[] typeArguments = this.parseTypeArgumentsOpt();
            switch (this.peek(TokenType.KEYWORD, TokenType.IDENTIFIER)) {
                case 0: {
                    this.read("new");
                    return new Java.ClassInstanceCreationReference(this.location(), a.toTypeOrCompileException(), typeArguments);
                }
                case 1: {
                    return new Java.MethodReference(this.location(), a, this.read(TokenType.IDENTIFIER));
                }
            }
            throw new AssertionError(this.peek());
        }
        while (this.peek("++", "--") != -1) {
            a = new Java.Crement(this.location(), a.toLvalueOrCompileException(), this.read().value);
        }
        return a;
    }

    public Java.Atom parsePrimary() throws CompileException, IOException {
        if (this.peekRead("(")) {
            Java.Atom a;
            if (this.peek("boolean", "char", "byte", "short", "int", "long", "float", "double") != -1 && !this.peekNextButOne(TokenType.IDENTIFIER)) {
                Java.Type type = this.parseType();
                int brackets = this.parseBracketsOpt();
                this.read(")");
                for (int i = 0; i < brackets; ++i) {
                    type = new Java.ArrayType(type);
                }
                return new Java.Cast(this.location(), type, this.parseUnaryExpression().toRvalueOrCompileException());
            }
            if (this.peekRead(")")) {
                Java.FormalLambdaParameters parameters = new Java.FormalLambdaParameters(new Java.FunctionDeclarator.FormalParameters(this.location()));
                Location loc = this.location();
                this.read("->");
                return new Java.LambdaExpression(loc, parameters, this.parseLambdaBody());
            }
            if (this.peek(TokenType.IDENTIFIER) && (this.peekNextButOne(",") || this.peekNextButOne(")"))) {
                ArrayList<String> l = new ArrayList<String>();
                l.add(this.read(TokenType.IDENTIFIER));
                while (this.peekRead(",")) {
                    l.add(this.read(TokenType.IDENTIFIER));
                }
                String[] names = l.toArray(new String[l.size()]);
                Location loc = this.location();
                if (this.peek(")") && this.peekNextButOne("->")) {
                    this.read();
                    this.read();
                    return new Java.LambdaExpression(loc, new Java.InferredLambdaParameters(names), this.parseLambdaBody());
                }
                if (names.length != 1) {
                    throw this.compileException("Lambda expected");
                }
                a = new Java.AmbiguousName(loc, new String[]{names[0]});
            } else {
                a = this.parseExpressionOrType();
            }
            if (this.peek(TokenType.IDENTIFIER)) {
                Java.FunctionDeclarator.FormalParameters fpl = this.parseFormalParameterListRest(a.toTypeOrCompileException());
                this.read(")");
                Java.FormalLambdaParameters parameters = new Java.FormalLambdaParameters(fpl);
                Location loc = this.location();
                this.read("->");
                return new Java.LambdaExpression(loc, parameters, this.parseLambdaBody());
            }
            this.read(")");
            if (this.peekLiteral() || this.peek(TokenType.IDENTIFIER) || this.peek("(", "~", "!") != -1 || this.peek("this", "super", "new") != -1) {
                return new Java.Cast(this.location(), a.toTypeOrCompileException(), this.parseUnaryExpression().toRvalueOrCompileException());
            }
            return new Java.ParenthesizedExpression(a.getLocation(), a.toRvalueOrCompileException());
        }
        if (this.peekLiteral()) {
            return this.parseLiteral();
        }
        if (this.peek(TokenType.IDENTIFIER)) {
            String[] qi = this.parseQualifiedIdentifier();
            if (this.peek("(")) {
                return new Java.MethodInvocation(this.location(), qi.length == 1 ? null : new Java.AmbiguousName(this.location(), qi, qi.length - 1), qi[qi.length - 1], this.parseArguments());
            }
            if (this.peek("[") && this.peekNextButOne("]")) {
                Java.Type res = new Java.ReferenceType(this.location(), new Java.Annotation[0], qi, null);
                int brackets = this.parseBracketsOpt();
                for (int i = 0; i < brackets; ++i) {
                    res = new Java.ArrayType(res);
                }
                if (this.peek(".") && this.peekNextButOne("class")) {
                    this.read();
                    Location location2 = this.location();
                    this.read();
                    return new Java.ClassLiteral(location2, res);
                }
                return res;
            }
            return new Java.AmbiguousName(this.location(), qi);
        }
        if (this.peekRead("this")) {
            Location location = this.location();
            if (this.peek("(")) {
                return new Java.AlternateConstructorInvocation(location, this.parseArguments());
            }
            return new Java.ThisReference(location);
        }
        if (this.peekRead("super")) {
            if (this.peek("(")) {
                return new Java.SuperConstructorInvocation(this.location(), null, this.parseArguments());
            }
            this.read(".");
            String name = this.read(TokenType.IDENTIFIER);
            if (this.peek("(")) {
                return new Java.SuperclassMethodInvocation(this.location(), name, this.parseArguments());
            }
            return new Java.SuperclassFieldAccessExpression(this.location(), null, name);
        }
        if (this.peekRead("new")) {
            Location location = this.location();
            Java.Type type = this.parseType();
            if (type instanceof Java.ArrayType) {
                return new Java.NewInitializedArray(location, (Java.ArrayType)type, this.parseArrayInitializer());
            }
            if (type instanceof Java.ReferenceType && this.peek("(")) {
                Java.Rvalue[] arguments = this.parseArguments();
                if (this.peek("{")) {
                    Java.AnonymousClassDeclaration anonymousClassDeclaration = new Java.AnonymousClassDeclaration(this.location(), type);
                    this.parseClassBody(anonymousClassDeclaration);
                    return new Java.NewAnonymousClassInstance(location, null, anonymousClassDeclaration, arguments);
                }
                return new Java.NewClassInstance(location, (Java.Rvalue)null, type, arguments);
            }
            return new Java.NewArray(location, type, this.parseDimExprs(), this.parseBracketsOpt());
        }
        if (this.peek("boolean", "char", "byte", "short", "int", "long", "float", "double") != -1) {
            Java.Type res = this.parseType();
            int brackets = this.parseBracketsOpt();
            for (int i = 0; i < brackets; ++i) {
                res = new Java.ArrayType(res);
            }
            if (this.peek(".") && this.peekNextButOne("class")) {
                this.read();
                Location location = this.location();
                this.read();
                return new Java.ClassLiteral(location, res);
            }
            return res;
        }
        if (this.peekRead("void")) {
            if (this.peek(".") && this.peekNextButOne("class")) {
                this.read();
                Location location = this.location();
                this.read();
                return new Java.ClassLiteral(location, new Java.PrimitiveType(location, Java.Primitive.VOID));
            }
            throw this.compileException("\"void\" encountered in wrong context");
        }
        throw this.compileException("Unexpected token \"" + this.read().value + "\" in primary");
    }

    public Java.Atom parseSelector(Java.Atom atom) throws CompileException, IOException {
        if (this.peekRead(".")) {
            this.parseTypeArgumentsOpt();
            if (this.peek().type == TokenType.IDENTIFIER) {
                String identifier = this.read(TokenType.IDENTIFIER);
                if (this.peek("(")) {
                    return new Java.MethodInvocation(this.location(), atom.toRvalueOrCompileException(), identifier, this.parseArguments());
                }
                return new Java.FieldAccessExpression(this.location(), atom.toRvalueOrCompileException(), identifier);
            }
            if (this.peekRead("this")) {
                Location location = this.location();
                return new Java.QualifiedThisReference(location, atom.toTypeOrCompileException());
            }
            if (this.peekRead("super")) {
                Location location = this.location();
                if (this.peek("(")) {
                    return new Java.SuperConstructorInvocation(location, atom.toRvalueOrCompileException(), this.parseArguments());
                }
                this.read(".");
                String identifier = this.read(TokenType.IDENTIFIER);
                if (this.peek("(")) {
                    throw this.compileException("Qualified superclass method invocation NYI");
                }
                return new Java.SuperclassFieldAccessExpression(location, atom.toTypeOrCompileException(), identifier);
            }
            if (this.peekRead("new")) {
                Java.Rvalue lhs = atom.toRvalueOrCompileException();
                Location location = this.location();
                String identifier = this.read(TokenType.IDENTIFIER);
                Java.RvalueMemberType type = new Java.RvalueMemberType(location, lhs, identifier);
                Java.Rvalue[] arguments = this.parseArguments();
                if (this.peek("{")) {
                    Java.AnonymousClassDeclaration anonymousClassDeclaration = new Java.AnonymousClassDeclaration(this.location(), type);
                    this.parseClassBody(anonymousClassDeclaration);
                    return new Java.NewAnonymousClassInstance(location, lhs, anonymousClassDeclaration, arguments);
                }
                return new Java.NewClassInstance(location, lhs, type, arguments);
            }
            if (this.peekRead("class")) {
                Location location = this.location();
                return new Java.ClassLiteral(location, atom.toTypeOrCompileException());
            }
            throw this.compileException("Unexpected selector '" + this.peek().value + "' after \".\"");
        }
        if (this.peekRead("[")) {
            Location location = this.location();
            Java.Rvalue index = this.parseExpression();
            this.read("]");
            return new Java.ArrayAccessExpression(location, atom.toRvalueOrCompileException(), index);
        }
        throw this.compileException("Unexpected token '" + this.peek().value + "' in selector");
    }

    public Java.Rvalue[] parseDimExprs() throws CompileException, IOException {
        ArrayList<Java.Rvalue> l = new ArrayList<Java.Rvalue>();
        l.add(this.parseDimExpr());
        while (this.peek("[") && !this.peekNextButOne("]")) {
            l.add(this.parseDimExpr());
        }
        return l.toArray(new Java.Rvalue[l.size()]);
    }

    public Java.Rvalue parseDimExpr() throws CompileException, IOException {
        this.read("[");
        Java.Rvalue res = this.parseExpression();
        this.read("]");
        return res;
    }

    public Java.Rvalue[] parseArguments() throws CompileException, IOException {
        this.read("(");
        if (this.peekRead(")")) {
            return new Java.Rvalue[0];
        }
        Java.Rvalue[] arguments = this.parseArgumentList();
        this.read(")");
        return arguments;
    }

    public Java.Rvalue[] parseArgumentList() throws CompileException, IOException {
        ArrayList<Java.Rvalue> l = new ArrayList<Java.Rvalue>();
        do {
            l.add(this.parseExpression());
        } while (this.peekRead(","));
        return l.toArray(new Java.Rvalue[l.size()]);
    }

    public Java.Rvalue parseLiteral() throws CompileException, IOException {
        Token t2 = this.read();
        switch (t2.type) {
            case INTEGER_LITERAL: {
                return new Java.IntegerLiteral(t2.getLocation(), t2.value);
            }
            case FLOATING_POINT_LITERAL: {
                return new Java.FloatingPointLiteral(t2.getLocation(), t2.value);
            }
            case BOOLEAN_LITERAL: {
                return new Java.BooleanLiteral(t2.getLocation(), t2.value);
            }
            case CHARACTER_LITERAL: {
                return new Java.CharacterLiteral(t2.getLocation(), t2.value);
            }
            case STRING_LITERAL: {
                return new Java.StringLiteral(t2.getLocation(), t2.value);
            }
            case NULL_LITERAL: {
                return new Java.NullLiteral(t2.getLocation());
            }
        }
        throw this.compileException("Literal expected");
    }

    private Java.LambdaExpression parseLambdaExpression() throws CompileException, IOException {
        Java.LambdaParameters parameters = this.parseLambdaParameters();
        Location loc = this.location();
        this.read("->");
        Java.LambdaBody body2 = this.parseLambdaBody();
        return new Java.LambdaExpression(loc, parameters, body2);
    }

    private Java.LambdaParameters parseLambdaParameters() throws CompileException, IOException {
        String identifier = this.peekRead(TokenType.IDENTIFIER);
        if (identifier != null) {
            return new Java.IdentifierLambdaParameters(identifier);
        }
        this.read("(");
        if (this.peekRead(")")) {
            return new Java.FormalLambdaParameters(new Java.FunctionDeclarator.FormalParameters(this.location()));
        }
        if (this.peek(TokenType.IDENTIFIER) && (this.peekNextButOne(",") || this.peekNextButOne(")"))) {
            ArrayList<String> names = new ArrayList<String>();
            names.add(this.read(TokenType.IDENTIFIER));
            while (this.peekRead(",")) {
                names.add(this.read(TokenType.IDENTIFIER));
            }
            this.read(")");
            return new Java.InferredLambdaParameters(names.toArray(new String[names.size()]));
        }
        Java.FunctionDeclarator.FormalParameters fpl = this.parseFormalParameterList();
        this.read(")");
        return new Java.FormalLambdaParameters(fpl);
    }

    private Java.LambdaBody parseLambdaBody() throws CompileException, IOException {
        return this.peek("{") ? new Java.BlockLambdaBody(this.parseBlock()) : new Java.ExpressionLambdaBody(this.parseExpression());
    }

    public Java.Statement parseExpressionStatement() throws CompileException, IOException {
        Java.Rvalue rv = this.parseExpression();
        this.read(";");
        return new Java.ExpressionStatement(rv);
    }

    public Location location() {
        return this.tokenStream.location();
    }

    public Token peek() throws CompileException, IOException {
        return this.tokenStream.peek();
    }

    public Token read() throws CompileException, IOException {
        return this.tokenStream.read();
    }

    public boolean peek(String suspected) throws CompileException, IOException {
        return this.tokenStream.peek(suspected);
    }

    public int peek(String ... suspected) throws CompileException, IOException {
        return this.tokenStream.peek(suspected);
    }

    public boolean peek(TokenType suspected) throws CompileException, IOException {
        return this.tokenStream.peek(suspected);
    }

    public int peek(TokenType ... suspected) throws CompileException, IOException {
        return this.tokenStream.peek(suspected);
    }

    public Token peekNextButOne() throws CompileException, IOException {
        return this.tokenStream.peekNextButOne();
    }

    public boolean peekNextButOne(String suspected) throws CompileException, IOException {
        return this.tokenStream.peekNextButOne(suspected);
    }

    public boolean peekNextButOne(TokenType suspected) throws CompileException, IOException {
        return this.tokenStream.peekNextButOne().type == suspected;
    }

    public void read(String expected) throws CompileException, IOException {
        this.tokenStream.read(expected);
    }

    public int read(String ... expected) throws CompileException, IOException {
        return this.tokenStream.read(expected);
    }

    public String read(TokenType expected) throws CompileException, IOException {
        return this.tokenStream.read(expected);
    }

    public boolean peekRead(String suspected) throws CompileException, IOException {
        return this.tokenStream.peekRead(suspected);
    }

    public int peekRead(String ... suspected) throws CompileException, IOException {
        return this.tokenStream.peekRead(suspected);
    }

    @Nullable
    public String peekRead(TokenType suspected) throws CompileException, IOException {
        return this.tokenStream.peekRead(suspected);
    }

    private boolean peekLiteral() throws CompileException, IOException {
        return this.peek(TokenType.INTEGER_LITERAL, TokenType.FLOATING_POINT_LITERAL, TokenType.BOOLEAN_LITERAL, TokenType.CHARACTER_LITERAL, TokenType.STRING_LITERAL, TokenType.NULL_LITERAL) != -1;
    }

    private void verifyStringIsConventionalPackageName(String s2, Location loc) throws CompileException {
        if (!Character.isLowerCase(s2.charAt(0))) {
            this.warning("UPN", "Package name \"" + s2 + "\" does not begin with a lower-case letter (see JLS7 6.8.1)", loc);
            return;
        }
        for (int i = 0; i < s2.length(); ++i) {
            char c = s2.charAt(i);
            if (Character.isLowerCase(c) || c == '_' || c == '.') continue;
            this.warning("PPN", "Poorly chosen package name \"" + s2 + "\" contains bad character '" + c + "'", loc);
            return;
        }
    }

    private void verifyIdentifierIsConventionalClassOrInterfaceName(String id, Location loc) throws CompileException {
        if (!Character.isUpperCase(id.charAt(0))) {
            this.warning("UCOIN1", "Class or interface name \"" + id + "\" does not begin with an upper-case letter (see JLS7 6.8.2)", loc);
            return;
        }
        for (int i = 0; i < id.length(); ++i) {
            char c = id.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) continue;
            this.warning("UCOIN", "Class or interface name \"" + id + "\" contains unconventional character \"" + c + "\" (see JLS7 6.8.2)", loc);
            return;
        }
    }

    private void verifyIdentifierIsConventionalMethodName(String id, Location loc) throws CompileException {
        if (!Character.isLowerCase(id.charAt(0))) {
            this.warning("UMN1", "Method name \"" + id + "\" does not begin with a lower-case letter (see JLS7 6.8.3)", loc);
            return;
        }
        for (int i = 0; i < id.length(); ++i) {
            char c = id.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) continue;
            this.warning("UMN", "Method name \"" + id + "\" contains unconventional character \"" + c + "\" (see JLS7 6.8.3)", loc);
            return;
        }
    }

    private void verifyIdentifierIsConventionalFieldName(String id, Location loc) throws CompileException {
        if (Character.isUpperCase(id.charAt(0))) {
            for (int i = 0; i < id.length(); ++i) {
                char c = id.charAt(i);
                if (Character.isUpperCase(c) || Character.isDigit(c) || c == '_') continue;
                this.warning("UCN", "Constant name \"" + id + "\" contains unconventional character \"" + c + "\" (see JLS7 6.8.5)", loc);
                return;
            }
        } else if (Character.isLowerCase(id.charAt(0))) {
            for (int i = 0; i < id.length(); ++i) {
                char c = id.charAt(i);
                if (Character.isLetter(c) || Character.isDigit(c)) continue;
                this.warning("UFN", "Field name \"" + id + "\" contains unconventional character \"" + c + "\" (see JLS7 6.8.4)", loc);
                return;
            }
        } else {
            this.warning("UFN1", "\"" + id + "\" is neither a conventional field name (JLS7 6.8.4) nor a conventional constant name (JLS7 6.8.5)", loc);
        }
    }

    private void verifyIdentifierIsConventionalLocalVariableOrParameterName(String id, Location loc) throws CompileException {
        if (!Character.isLowerCase(id.charAt(0))) {
            this.warning("ULVN1", "Local variable name \"" + id + "\" does not begin with a lower-case letter (see JLS7 6.8.6)", loc);
            return;
        }
        for (int i = 0; i < id.length(); ++i) {
            char c = id.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c)) continue;
            this.warning("ULVN", "Local variable name \"" + id + "\" contains unconventional character \"" + c + "\" (see JLS7 6.8.6)", loc);
            return;
        }
    }

    public void setSourceVersion(int version) {
        this.sourceVersion = version;
    }

    public void setWarningHandler(@Nullable WarningHandler warningHandler) {
        this.warningHandler = warningHandler;
        this.tokenStream.setWarningHandler(warningHandler);
    }

    private void warning(String handle2, String message) throws CompileException {
        this.warning(handle2, message, this.location());
    }

    private void warning(String handle2, String message, @Nullable Location location) throws CompileException {
        if (this.warningHandler != null) {
            this.warningHandler.handleWarning(handle2, message, location);
        }
    }

    protected final CompileException compileException(String message) {
        return Parser.compileException(message, this.location());
    }

    protected static CompileException compileException(String message, Location location) {
        return new CompileException(message, location);
    }

    private static String join(@Nullable String[] sa, String separator) {
        if (sa == null) {
            return "(null)";
        }
        if (sa.length == 0) {
            return "(zero length array)";
        }
        StringBuilder sb = new StringBuilder(sa[0]);
        for (int i = 1; i < sa.length; ++i) {
            sb.append(separator).append(sa[i]);
        }
        return sb.toString();
    }

    private static boolean hasAccessModifier(Java.Modifier[] modifiers, String ... keywords) {
        for (String kw : keywords) {
            for (Java.Modifier m4 : modifiers) {
                if (!(m4 instanceof Java.AccessModifier) || !kw.equals(((Java.AccessModifier)m4).keyword)) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean hasAccessModifierOtherThan(Java.Modifier[] modifiers, String ... keywords) {
        block0: for (Java.Modifier m4 : modifiers) {
            if (!(m4 instanceof Java.AccessModifier)) continue;
            for (String kw : keywords) {
                if (kw.equals(((Java.AccessModifier)m4).keyword)) continue block0;
            }
            return true;
        }
        return false;
    }

    private Java.Modifier[] packageModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, new String[0]);
    }

    private Java.Modifier[] classModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "protected", "private", "abstract", "static", "final", "strictfp");
    }

    private Java.Modifier[] packageMemberClassModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "abstract", "final", "strictfp");
    }

    private Java.Modifier[] fieldModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "protected", "private", "static", "final", "transient", "volatile");
    }

    private Java.Modifier[] methodModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "protected", "private", "abstract", "static", "final", "synchronized", "native", "strictfp");
    }

    private Java.Modifier[] variableModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "final");
    }

    private Java.Modifier[] constructorModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "protected", "private");
    }

    private Java.Modifier[] enumConstantModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "xxx");
    }

    private Java.Modifier[] interfaceModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "protected", "private", "abstract", "static", "strictfp");
    }

    private Java.Modifier[] packageMemberInterfaceModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "abstract", "strictfp");
    }

    private Java.Modifier[] constantModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "static", "final");
    }

    private Java.Modifier[] interfaceMethodModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "private", "abstract", "default", "static", "strictfp");
    }

    private Java.Modifier[] annotationTypeElementModifiers(Java.Modifier[] modifiers) throws CompileException {
        return this.checkModifiers(modifiers, "public", "abstract");
    }

    private Java.Modifier[] checkModifiers(Java.Modifier[] modifiers, String ... allowedKeywords) throws CompileException {
        HashSet<String> keywords = new HashSet<String>();
        for (Java.Modifier m4 : modifiers) {
            if (!(m4 instanceof Java.AccessModifier)) continue;
            Java.AccessModifier am = (Java.AccessModifier)m4;
            if (!keywords.add(am.keyword)) {
                throw Parser.compileException("Duplication access modifier \"" + am.keyword + "\"", am.getLocation());
            }
            for (Set<String> meams : MUTUALLY_EXCLUSIVE_ACCESS_MODIFIERS) {
                HashSet tmp = new HashSet(keywords);
                tmp.retainAll(meams);
                if (tmp.size() <= 1) continue;
                Object[] a = tmp.toArray(new String[tmp.size()]);
                Arrays.sort(a);
                throw Parser.compileException("Only one of " + Parser.join((String[])a, " ") + " is allowed", am.getLocation());
            }
        }
        for (String kw : allowedKeywords) {
            keywords.remove(kw);
        }
        if (!keywords.isEmpty()) {
            Object[] a = keywords.toArray(new String[keywords.size()]);
            Arrays.sort(a);
            throw this.compileException("Access modifier(s) " + Parser.join((String[])a, " ") + " not allowed in this context");
        }
        return modifiers;
    }

    public static enum InterfaceDeclarationContext {
        NAMED_TYPE_DECLARATION,
        COMPILATION_UNIT;

    }

    public static enum MethodDeclarationContext {
        CLASS_DECLARATION,
        INTERFACE_DECLARATION,
        ANNOTATION_TYPE_DECLARATION;

    }

    public static enum ClassDeclarationContext {
        BLOCK,
        TYPE_DECLARATION,
        COMPILATION_UNIT;

    }
}

