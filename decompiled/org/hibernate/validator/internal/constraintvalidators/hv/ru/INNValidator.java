/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.hv.ru;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.ru.INN;
import org.hibernate.validator.internal.util.Contracts;

public class INNValidator
implements ConstraintValidator<INN, CharSequence> {
    private static final Pattern NUMBERS_ONLY_PATTERN = Pattern.compile("[0-9]+");
    private static final int RADIX = 10;
    private INNValidationAlgorithm innValidationAlgorithm;

    @Override
    public void initialize(INN constraintAnnotation) {
        this.innValidationAlgorithm = INNValidationAlgorithm.from(constraintAnnotation.type());
    }

    @Override
    public boolean isValid(CharSequence innCharSeq, ConstraintValidatorContext context) {
        if (innCharSeq == null) {
            return true;
        }
        String inn = innCharSeq.toString();
        boolean hasOnlyNumbers = NUMBERS_ONLY_PATTERN.matcher(inn).matches();
        if (!hasOnlyNumbers) {
            return false;
        }
        if (!this.innValidationAlgorithm.isValidLength(inn.length())) {
            return false;
        }
        int[] digits = inn.codePoints().map(symbol -> Character.digit(symbol, 10)).toArray();
        return this.innValidationAlgorithm.isValidChecksum(digits);
    }

    private static interface INNValidationAlgorithm {
        public boolean isValidLength(int var1);

        public boolean isValidChecksum(int[] var1);

        public static INNValidationAlgorithm from(INN.Type type) {
            Contracts.assertNotNull((Object)type);
            switch (type) {
                case JURIDICAL: {
                    return INNValidationAlgorithmImpl.JURIDICAL;
                }
                case INDIVIDUAL: {
                    return INNValidationAlgorithmImpl.INDIVIDUAL;
                }
            }
            return INNValidationAlgorithmImpl.ANY;
        }
    }

    private static enum INNValidationAlgorithmImpl implements INNValidationAlgorithm
    {
        INDIVIDUAL{

            @Override
            public boolean isValidLength(int length) {
                return 12 == length;
            }

            @Override
            public boolean isValidChecksum(int[] digits) {
                return INNValidationAlgorithmImpl.checkChecksumPersonalINN(digits);
            }
        }
        ,
        JURIDICAL{

            @Override
            public boolean isValidLength(int length) {
                return 10 == length;
            }

            @Override
            public boolean isValidChecksum(int[] digits) {
                return INNValidationAlgorithmImpl.checkChecksumJuridicalINN(digits);
            }
        }
        ,
        ANY{

            @Override
            public boolean isValidLength(int length) {
                return 10 == length || 12 == length;
            }

            @Override
            public boolean isValidChecksum(int[] digits) {
                int length = digits.length;
                if (length == 12) {
                    return INNValidationAlgorithmImpl.checkChecksumPersonalINN(digits);
                }
                if (length == 10) {
                    return INNValidationAlgorithmImpl.checkChecksumJuridicalINN(digits);
                }
                throw new IllegalStateException("Invalid/unsupported inn value length");
            }
        };

        private static final int[] INDIVIDUAL_WEIGHTS_11;
        private static final int[] INDIVIDUAL_WEIGHTS_12;
        private static final int[] JURIDICAL_WEIGHTS;
        private static final int MOD_11 = 11;
        private static final int MOD_10 = 10;

        private static boolean checkChecksumPersonalINN(int[] digits) {
            int checkSum11 = INNValidationAlgorithmImpl.getCheckSum(digits, INDIVIDUAL_WEIGHTS_11);
            int checkSum12 = INNValidationAlgorithmImpl.getCheckSum(digits, INDIVIDUAL_WEIGHTS_12);
            boolean isCheckSum11Correct = checkSum11 == digits[digits.length - 2];
            boolean isCheckSum12Correct = checkSum12 == digits[digits.length - 1];
            return isCheckSum11Correct && isCheckSum12Correct;
        }

        private static boolean checkChecksumJuridicalINN(int[] digits) {
            int checkSum = INNValidationAlgorithmImpl.getCheckSum(digits, JURIDICAL_WEIGHTS);
            return digits[digits.length - 1] == checkSum;
        }

        private static int getCheckSum(int[] digits, int[] weights) {
            int sum = 0;
            for (int i = 0; i < weights.length; ++i) {
                sum += digits[i] * weights[i];
            }
            return sum % 11 % 10;
        }

        static {
            INDIVIDUAL_WEIGHTS_11 = new int[]{7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
            INDIVIDUAL_WEIGHTS_12 = new int[]{3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};
            JURIDICAL_WEIGHTS = new int[]{2, 4, 10, 3, 5, 9, 4, 6, 8};
        }
    }
}

