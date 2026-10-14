/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.hv;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.internal.util.Contracts;

public class ISBNValidator
implements ConstraintValidator<ISBN, CharSequence> {
    private static Pattern NOT_DIGITS_OR_NOT_X = Pattern.compile("[^\\dX]");
    private ISBNValidationAlgorithm isbnValidationAlgorithm;

    @Override
    public void initialize(ISBN constraintAnnotation) {
        this.isbnValidationAlgorithm = ISBNValidationAlgorithm.from(constraintAnnotation.type());
    }

    @Override
    public boolean isValid(CharSequence isbn, ConstraintValidatorContext context) {
        if (isbn == null) {
            return true;
        }
        String digits = NOT_DIGITS_OR_NOT_X.matcher(isbn).replaceAll("");
        if (!this.isbnValidationAlgorithm.isValidLength(digits.length())) {
            return false;
        }
        return this.isbnValidationAlgorithm.isValidChecksum(digits);
    }

    private static interface ISBNValidationAlgorithm {
        public boolean isValidLength(int var1);

        public boolean isValidChecksum(String var1);

        public static ISBNValidationAlgorithmImpl from(ISBN.Type type) {
            Contracts.assertNotNull((Object)type);
            switch (type) {
                case ISBN_10: {
                    return ISBNValidationAlgorithmImpl.ISBN_10;
                }
                case ISBN_13: {
                    return ISBNValidationAlgorithmImpl.ISBN_13;
                }
            }
            return ISBNValidationAlgorithmImpl.ANY;
        }
    }

    private static enum ISBNValidationAlgorithmImpl implements ISBNValidationAlgorithm
    {
        ISBN_10{

            @Override
            public boolean isValidChecksum(String isbn) {
                return ISBNValidationAlgorithmImpl.checkChecksumISBN10(isbn);
            }

            @Override
            public boolean isValidLength(int length) {
                return 10 == length;
            }
        }
        ,
        ISBN_13{

            @Override
            public boolean isValidChecksum(String isbn) {
                return ISBNValidationAlgorithmImpl.checkChecksumISBN13(isbn);
            }

            @Override
            public boolean isValidLength(int length) {
                return 13 == length;
            }
        }
        ,
        ANY{

            @Override
            public boolean isValidLength(int length) {
                return 10 == length || 13 == length;
            }

            @Override
            public boolean isValidChecksum(String isbn) {
                int length = isbn.length();
                if (length == 10) {
                    return ISBNValidationAlgorithmImpl.checkChecksumISBN10(isbn);
                }
                if (length == 13) {
                    return ISBNValidationAlgorithmImpl.checkChecksumISBN13(isbn);
                }
                throw new IllegalStateException("Invalid/unsupported isbn value length");
            }
        };


        private static boolean checkChecksumISBN10(String isbn) {
            int sum = 0;
            for (int i = 0; i < isbn.length() - 1; ++i) {
                sum += (isbn.charAt(i) - 48) * (10 - i);
            }
            return (sum += isbn.charAt(9) == 'X' ? 10 : isbn.charAt(9) - 48) % 11 == 0;
        }

        private static boolean checkChecksumISBN13(String isbn) {
            int sum = 0;
            for (int i = 0; i < isbn.length(); ++i) {
                sum += (isbn.charAt(i) - 48) * (i % 2 == 0 ? 1 : 3);
            }
            return sum % 10 == 0;
        }
    }
}

