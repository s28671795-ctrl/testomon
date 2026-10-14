/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.io.InputStream;
import java.io.Reader;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.w3c.dom.Document;

public interface ParserPool {
    @Nonnull
    public DocumentBuilder getBuilder() throws XMLParserException;

    public void returnBuilder(@Nullable DocumentBuilder var1);

    @Nonnull
    public Document newDocument() throws XMLParserException;

    @Nonnull
    public Document parse(@Nonnull InputStream var1) throws XMLParserException;

    @Nonnull
    public Document parse(@Nonnull Reader var1) throws XMLParserException;
}

