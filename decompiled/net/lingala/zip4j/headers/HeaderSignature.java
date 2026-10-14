/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.headers;

public enum HeaderSignature {
    LOCAL_FILE_HEADER(67324752L),
    EXTRA_DATA_RECORD(134695760L),
    CENTRAL_DIRECTORY(33639248L),
    END_OF_CENTRAL_DIRECTORY(101010256L),
    TEMPORARY_SPANNING_MARKER(808471376L),
    DIGITAL_SIGNATURE(84233040L),
    ARCEXTDATREC(134630224L),
    SPLIT_ZIP(134695760L),
    ZIP64_END_CENTRAL_DIRECTORY_LOCATOR(117853008L),
    ZIP64_END_CENTRAL_DIRECTORY_RECORD(101075792L),
    ZIP64_EXTRA_FIELD_SIGNATURE(1L),
    AES_EXTRA_DATA_RECORD(39169L);

    private long value;

    private HeaderSignature(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }
}

