/*
 * Decompiled with CFR 0.152.
 */
package com.opencsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import org.apache.commons.lang3.ObjectUtils;

public class CSVIterator
implements Iterator<String[]> {
    private final CSVReader reader;
    private String[] nextLine;
    private Locale errorLocale = Locale.getDefault();

    public CSVIterator(CSVReader reader) throws IOException, CsvValidationException {
        this.reader = reader;
        this.nextLine = reader.readNext();
    }

    public void setErrorLocale(Locale errorLocale) {
        this.errorLocale = ObjectUtils.defaultIfNull(errorLocale, Locale.getDefault());
    }

    @Override
    public boolean hasNext() {
        return this.nextLine != null;
    }

    @Override
    public String[] next() {
        String[] temp = this.nextLine;
        try {
            this.nextLine = this.reader.readNext();
        }
        catch (CsvValidationException | IOException e) {
            NoSuchElementException nse = new NoSuchElementException(e.getLocalizedMessage());
            nse.initCause(e);
            throw nse;
        }
        return temp;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(ResourceBundle.getBundle("opencsv", this.errorLocale).getString("read.only.iterator"));
    }
}

