package com.esri.android.reflectionservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.esri.android.reflectionservice.ReflectionServiceUtil.initializeStaticFinalField;
import static com.esri.android.reflectionservice.ReflectionServiceUtil.throwUncheckedInitializerException;

/** Utility class for verifying that the Google Play services APK is available and up-to-date on this device. */
public final class GooglePlayServicesUtil {
    private GooglePlayServicesUtil() {}

    private static final String THIS_CLASS_PACKAGE = "com.google.android.gms.common.GooglePlayServicesUtil";

    /** Package name for Google Play services. */
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = null;
    private static final String PACKAGE_FIELD_NAME = "GOOGLE_PLAY_SERVICES_PACKAGE";

    /**
     * Minimum Google Play services package version (declared in AndroidManifest.xml android:versionCode) in order
     * to be compatible with this client version.
     */
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = -1;
    private static final String VERSION_CODE_FIELD_NAME = "GOOGLE_PLAY_SERVICES_VERSION_CODE";

    /** Package name for Google Play services. */
    public static final String GOOGLE_PLAY_STORE_PACKAGE = null;
    private static final String STORE_PACKAGE_FIELD_NAME = "GOOGLE_PLAY_STORE_PACKAGE";

    private static Method sGetErrorDialogWithListener;
    private static Method sGetErrorDialog;
    private static Method sGetErrorPendingIntent;
    private static Method sGetErrorString;
    private static Method sGetOpenSourceSoftwareLicenseInfo;
    private static Method sGetRemoteContext;
    private static Method sGetRemoteResource;
    private static Method sIsGooglePlayServicesAvailable;
    private static Method sIsUserRecoverableError;

    static {
        try {
            Class<?> googlePlayServicesUtilClass = Class.forName(THIS_CLASS_PACKAGE);

            // set up static final values
            initializeStaticFinalField(GooglePlayServicesUtil.class.getField(PACKAGE_FIELD_NAME),
                    googlePlayServicesUtilClass.getField(PACKAGE_FIELD_NAME).get(null));
            initializeStaticFinalField(GooglePlayServicesUtil.class.getField(VERSION_CODE_FIELD_NAME),
                    googlePlayServicesUtilClass.getField(VERSION_CODE_FIELD_NAME).get(null));
            initializeStaticFinalField(GooglePlayServicesUtil.class.getField(STORE_PACKAGE_FIELD_NAME),
                    googlePlayServicesUtilClass.getField(STORE_PACKAGE_FIELD_NAME).get(null));

            // get refs to public methods
            sGetErrorDialogWithListener = googlePlayServicesUtilClass.getMethod("getErrorDialog", int.class,
                    Activity.class, int.class, DialogInterface.OnCancelListener.class);
            sGetErrorDialog = googlePlayServicesUtilClass.getMethod("getErrorDialog", int.class,
                    Activity.class, int.class);
            sGetErrorPendingIntent = googlePlayServicesUtilClass.getMethod("getErrorPendingIntent", int.class,
                    Context.class, int.class);
            sGetErrorString = googlePlayServicesUtilClass.getMethod("getErrorString", int.class);
            sGetOpenSourceSoftwareLicenseInfo = googlePlayServicesUtilClass.getMethod(
                    "getOpenSourceSoftwareLicenseInfo", Context.class);
            sGetRemoteContext = googlePlayServicesUtilClass.getMethod("getRemoteContext", Context.class);
            sGetRemoteResource = googlePlayServicesUtilClass.getMethod("getRemoteResource", Context.class);
            sIsGooglePlayServicesAvailable = googlePlayServicesUtilClass.getMethod("isGooglePlayServicesAvailable",
                    Context.class);
            sIsUserRecoverableError = googlePlayServicesUtilClass.getMethod("isUserRecoverableError", int.class);
        } catch (Exception e) {
            throwUncheckedInitializerException(e);
        }
    }

    /**
     * Returns a dialog to address the provided errorCode.
     *
     * @param errorCode
     * @param activity
     * @param requestCode
     * @param cancelListener
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Dialog getErrorDialog(int errorCode, Activity activity, int requestCode,
                                        DialogInterface.OnCancelListener cancelListener)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetErrorDialogWithListener == null) {
            return null;
        }

        return (Dialog) sGetErrorDialogWithListener.invoke(null, errorCode, activity, requestCode, cancelListener);
    }

    /**
     * Returns a dialog to address the provided errorCode.
     *
     * @param errorCode
     * @param activity
     * @param requestCode
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Dialog getErrorDialog(int errorCode, Activity activity, int requestCode)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetErrorDialog == null) {
            return null;
        }

        return (Dialog) sGetErrorDialog.invoke(null, errorCode, activity, requestCode);
    }

    /**
     * Returns a PendingIntent to address the provided errorCode.
     *
     * @param errorCode
     * @param context
     * @param requestCode
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static PendingIntent getErrorPendingIntent(int errorCode, Context context, int requestCode)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetErrorPendingIntent == null) {
            return null;
        }

        return (PendingIntent) sGetErrorPendingIntent.invoke(null, errorCode, context, requestCode);
    }

    /**
     * Returns a human-readable string of the error code returned from {@link #isGooglePlayServicesAvailable}.
     *
     * @param errorCode
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String getErrorString(int errorCode)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetErrorString == null) {
            return null;
        }

        return (String) sGetErrorString.invoke(null, errorCode);
    }

    /**
     * Returns the open source software license information for the Google Play services application, or null if
     * Google Play services is not available on this device.
     *
     * @param context
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static String getOpenSourceSoftwareLicenseInfo(Context context)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetOpenSourceSoftwareLicenseInfo == null) {
            return null;
        }

        return (String) sGetOpenSourceSoftwareLicenseInfo.invoke(null, context);
    }

    /**
     * This gets the Context object of the Buddy APK.
     *
     * @param context
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Context getRemoteContext(Context context)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetRemoteContext == null) {
            return null;
        }

        return (Context) sGetRemoteContext.invoke(null, context);
    }

    /**
     * This gets the Resources object of the Buddy APK.
     *
     * @param context
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Resources getRemoteResource(Context context)
            throws InvocationTargetException, IllegalAccessException {
        if (sGetRemoteResource == null) {
            return null;
        }

        return (Resources) sGetRemoteResource.invoke(null, context);
    }

    /**
     * Verifies that Google Play services is installed and enabled on this device, and that the version installed on
     * this device is no older than the one required by this client.
     *
     * @param context
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static int isGooglePlayServicesAvailable(Context context)
            throws InvocationTargetException, IllegalAccessException {
        if (sIsGooglePlayServicesAvailable == null) {
            return -1;
        }

        return (Integer) sIsGooglePlayServicesAvailable.invoke(context);
    }

    /**
     * Determines whether an error is user-recoverable.
     *
     * @param errorCode
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static boolean isUserRecoverableError(int errorCode)
            throws InvocationTargetException, IllegalAccessException {
        if (sIsUserRecoverableError == null) {
            return false;
        }

        return (Boolean) sIsUserRecoverableError.invoke(null, errorCode);
    }
}
