/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.pem.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import nl.altindag.ssl.util.internal.CollectorsUtils;

final class PemFormatter {
    private static final Pattern PEM_PATTERN = Pattern.compile("(-----BEGIN.*?-----)(.*?)(-----END.*?-----)");
    private static final String INNER_ENCRYPTED_HEADER = "Proc-Type: 4,ENCRYPTED";
    private static final Map<String, Integer> ENCRYPTION_ALGORITHMS_AND_SALT_TO_FIELD_LENGTH = new HashMap<String, Integer>();
    private static final String MAX_64_CHARACTER_LINE_SPLITTER = "(?<=\\G.{64})";
    private static final String EMPTY = "";

    private PemFormatter() {
    }

    static String reformatIfNeeded(String value) {
        Matcher certificateMatcher = PEM_PATTERN.matcher(value);
        ArrayList certificates = new ArrayList();
        while (certificateMatcher.find()) {
            String header = certificateMatcher.group(1);
            String body2 = certificateMatcher.group(2);
            String footer = certificateMatcher.group(3);
            List<String> innerEncryptionHeader = PemFormatter.extractInnerEncryptionHeaderIfPossible(body2);
            String certificateContent = body2.substring(String.join((CharSequence)EMPTY, innerEncryptionHeader).length());
            List certificateContainer = Stream.of(certificateContent.split(MAX_64_CHARACTER_LINE_SPLITTER)).collect(CollectorsUtils.toModifiableList());
            certificateContainer.add(0, header);
            certificateContainer.addAll(1, innerEncryptionHeader);
            certificateContainer.add(footer);
            certificates.addAll(certificateContainer);
        }
        return certificates.isEmpty() ? value : String.join((CharSequence)System.lineSeparator(), certificates);
    }

    private static List<String> extractInnerEncryptionHeaderIfPossible(String value) {
        if (!value.contains(INNER_ENCRYPTED_HEADER)) {
            return Collections.emptyList();
        }
        for (Map.Entry<String, Integer> encryptionAlgorithmAndSaltToFieldLength : ENCRYPTION_ALGORITHMS_AND_SALT_TO_FIELD_LENGTH.entrySet()) {
            if (!value.contains(encryptionAlgorithmAndSaltToFieldLength.getKey())) continue;
            String encryptionAlgorithmValue = value.substring(INNER_ENCRYPTED_HEADER.length(), INNER_ENCRYPTED_HEADER.length() + encryptionAlgorithmAndSaltToFieldLength.getValue());
            ArrayList<String> innerHeader = new ArrayList<String>();
            innerHeader.add(INNER_ENCRYPTED_HEADER);
            innerHeader.add(encryptionAlgorithmValue);
            innerHeader.add(EMPTY);
            return innerHeader;
        }
        throw new IllegalArgumentException(String.format("The provided encrypted private key is not supported. Supported formats are: [%s]", String.join((CharSequence)",", ENCRYPTION_ALGORITHMS_AND_SALT_TO_FIELD_LENGTH.keySet())));
    }

    static {
        ENCRYPTION_ALGORITHMS_AND_SALT_TO_FIELD_LENGTH.put("AES-256-CBC", 54);
        ENCRYPTION_ALGORITHMS_AND_SALT_TO_FIELD_LENGTH.put("DES-EDE3-CBC", 39);
    }
}

