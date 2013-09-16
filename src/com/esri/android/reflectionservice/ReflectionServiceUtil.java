package com.esri.android.reflectionservice;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.WARN;

public final class ReflectionServiceUtil {
    private static final String TAG = "ReflectionServiceUtil";
    private static final String MODIFIERS_FIELD = "modifiers";
    private static Method sLogDebug;
    private static Method sLogDebugWithThrowable;
    private static Method sLogWarn;
    private static Method sLogWarnWithThrowable;
    private static Method sLogError;
    private static Method sLogErrorWithThrowable;

    private ReflectionServiceUtil() {}

    static {
        setLogger(Log.class);
    }

    /**
     * Set the class that the reflection services should use for logging. Defaults to <code>android.util.Log</code>.
     * Any alternative class should have the same interface as <code>android.util.Log</code> for
     * {@link android.util.Log#DEBUG}, {@link android.util.Log#WARN} and {@link android.util.Log#ERROR} level
     * logging methods.
     *
     * @param logger
     */
    public static void setLogger(Class<?> logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Don't give me a null logger Class!");
        }

        Method logDebug;
        Method logDebugWithThrowable;
        Method logWarn;
        Method logWarnWithThrowable;
        Method logError;
        Method logErrorWithThrowable;

        try {
            logDebug = logger.getMethod("d", String.class, String.class);
            logDebugWithThrowable = logger.getMethod("d", String.class, String.class, Throwable.class);
            logWarn = logger.getMethod("w", String.class, String.class);
            logWarnWithThrowable = logger.getMethod("w", String.class, String.class, Throwable.class);
            logError = logger.getMethod("e", String.class, String.class);
            logErrorWithThrowable = logger.getMethod("e", String.class, String.class, Throwable.class);
        } catch (NoSuchMethodException e) {
            log(WARN, TAG, "The logger class provided doesn't implement the required methods, sticking with " +
                    "android.util.Log.");
            return;
        }

        sLogDebug = logDebug;
        sLogDebugWithThrowable = logDebugWithThrowable;
        sLogWarn = logWarn;
        sLogWarnWithThrowable = logWarnWithThrowable;
        sLogError = logError;
        sLogErrorWithThrowable = logErrorWithThrowable;
    }

    /**
     * Initializes a static, final field. Wait a second...
     *
     * @param field The static, final field you wish to set.
     * @param value The new value for the field.
     * @return true if the field was set, false otherwise
     * @throws IllegalAccessException
     */
    static boolean initializeStaticFinalField(Field field, Object value) {
        if (field == null) {
            throw new IllegalArgumentException("Don't give me a null field!");
        }

        if (!field.isAccessible()) {
            // private? ha!
            field.setAccessible(true);
        }

        Field modifiersField;

        try {
            modifiersField = Field.class.getDeclaredField(MODIFIERS_FIELD);
            modifiersField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            // improbable!
            return false;
        }

        try {
            // final? ha!
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            // set new value
            log(DEBUG, TAG, String.format("Setting %s.%s to %s", field.getDeclaringClass().getName(), field.getName(),
                    value.toString()));
            field.set(null, value);

            // revert to previous state... let's try to retain _some_ dignity.
            modifiersField.setInt(field, field.getModifiers() & Modifier.FINAL);
        } catch (IllegalAccessException e) {
            // improbable!
            return false;
        }

        // success
        return true;
    }

    /**
     * Takes a checked {@link Exception} and makes it unchecked, so that it can be thrown from a static
     * initialization block.
     *
     * @param e the checked {@link Exception}
     */
    static void throwUncheckedInitializerException(Exception e) {
        throw new ExceptionInInitializerError(e);
    }

    /**
     *
     *
     *
     * @param logLevel one of {@link android.util.Log#DEBUG}, {@link android.util.Log#WARN}
     *                 or {@link android.util.Log#ERROR}
     * @param tag
     */
    static void log(int logLevel, String tag, String msg) {
        try {
            switch (logLevel) {
                case DEBUG:
                    sLogDebug.invoke(null, tag, msg);
                    break;
                case WARN:
                    sLogWarn.invoke(null, tag, msg);
                    break;
                case ERROR:
                    sLogError.invoke(null, tag, msg);
                    break;
            }
        } catch (Exception e) {
            // well, can't exactly log that can we?
        }
    }

    /**
     *
     *
     * @param logLevel one of {@link android.util.Log#DEBUG}, {@link android.util.Log#WARN}
     *                 or {@link android.util.Log#ERROR}
     * @param tag
     * @param error
     */
    static void log(int logLevel, String tag, String msg, Throwable error) {
        try {
            switch (logLevel) {
                case DEBUG:
                    sLogDebugWithThrowable.invoke(null, tag, msg, error);
                    break;
                case WARN:
                    sLogWarnWithThrowable.invoke(null, tag, msg, error);
                    break;
                case ERROR:
                    sLogErrorWithThrowable.invoke(null, tag, msg, error);
                    break;
            }
        } catch (Exception e) {
            // well, can't exactly log that can we?
        }
    }
}
