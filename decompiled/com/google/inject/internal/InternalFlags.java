/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.logging.Logger;

public final class InternalFlags {
    private static final Logger logger = Logger.getLogger(InternalFlags.class.getName());
    private static final IncludeStackTraceOption INCLUDE_STACK_TRACES = InternalFlags.getSystemOption("guice_include_stack_traces", IncludeStackTraceOption.ONLY_FOR_DECLARING_SOURCE);
    private static final CustomClassLoadingOption CUSTOM_CLASS_LOADING = InternalFlags.getSystemOption("guice_custom_class_loading", CustomClassLoadingOption.BRIDGE, CustomClassLoadingOption.OFF);
    private static final NullableProvidesOption NULLABLE_PROVIDES = InternalFlags.getSystemOption("guice_check_nullable_provides_params", NullableProvidesOption.ERROR);
    private static final BytecodeGenOption BYTECODE_GEN_OPTION = InternalFlags.getSystemOption("guice_bytecode_gen_option", BytecodeGenOption.ENABLED);
    private static final ColorizeOption COLORIZE_OPTION = InternalFlags.getSystemOption("guice_colorize_error_messages", ColorizeOption.OFF);

    public static IncludeStackTraceOption getIncludeStackTraceOption() {
        return INCLUDE_STACK_TRACES;
    }

    public static CustomClassLoadingOption getCustomClassLoadingOption() {
        return CUSTOM_CLASS_LOADING;
    }

    public static NullableProvidesOption getNullableProvidesOption() {
        return NULLABLE_PROVIDES;
    }

    public static boolean isBytecodeGenEnabled() {
        return BYTECODE_GEN_OPTION == BytecodeGenOption.ENABLED;
    }

    public static boolean enableColorizeErrorMessages() {
        return COLORIZE_OPTION.enabled();
    }

    private static <T extends Enum<T>> T getSystemOption(String name, T defaultValue) {
        return InternalFlags.getSystemOption(name, defaultValue, defaultValue);
    }

    private static <T extends Enum<T>> T getSystemOption(final String name, T defaultValue, T secureValue) {
        Class<T> enumType = defaultValue.getDeclaringClass();
        String value = null;
        try {
            value = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return System.getProperty(name);
                }
            });
            return value != null && value.length() > 0 ? Enum.valueOf(enumType, value) : defaultValue;
        }
        catch (SecurityException e) {
            return secureValue;
        }
        catch (IllegalArgumentException e) {
            logger.warning(value + " is not a valid flag value for " + name + ".  Values must be one of " + Arrays.asList((Enum[])enumType.getEnumConstants()));
            return defaultValue;
        }
    }

    private InternalFlags() {
    }

    public static enum ColorizeOption {
        AUTO{

            @Override
            boolean enabled() {
                return System.console() != null && System.getenv("TERM") != null;
            }
        }
        ,
        ON{

            @Override
            boolean enabled() {
                return true;
            }
        }
        ,
        OFF{

            @Override
            boolean enabled() {
                return false;
            }
        };


        abstract boolean enabled();
    }

    public static enum BytecodeGenOption {
        DISABLED,
        ENABLED;

    }

    public static enum NullableProvidesOption {
        IGNORE,
        WARN,
        ERROR;

    }

    public static enum CustomClassLoadingOption {
        OFF,
        ANONYMOUS,
        BRIDGE,
        CHILD;

    }

    public static enum IncludeStackTraceOption {
        OFF,
        ONLY_FOR_DECLARING_SOURCE;

    }
}

