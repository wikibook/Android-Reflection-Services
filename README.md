Android-Reflection-Services
===========================
Want to reference functionality packaged in the Google-Play-Services jar without breaking the license agreement?

__This is a work in progress. Starting with `LocationClient` and friends.__

When installing Google Play Services via the SDK manager, you are presented with the same license agreement as for the Android SDK itself.  If you read the agreement, you may notice section 3.3 would prevent a developer from building their own SDK (to be used for the creation of Android apps) and packaging the Google Play Services jar in order build against it and take advantage of its features.  See section 3.3 (section 3.4 also may apply in some cases):

```
3.3 You may not use the SDK for any purpose not expressly permitted by this License Agreement.  Except to the extent
required by applicable third party licenses, you may not: (a) copy (except for backup purposes), modify, adapt,
redistribute, decompile, reverse engineer, disassemble, or create derivative works of the SDK or any part of the SDK; or
(b) load any part of the SDK onto a mobile handset or any other hardware device except a personal computer, combine any
part of the SDK with other software, or distribute any software or device incorporating a part of the SDK.
```

The mapping company (ESRI) that I work for wants to enable the sleek new `LocationClient` for devices that have access to Google Play Services (most of devices do), and failback to our own secret sauce when the services are not available.  Our SDK cannot include the jar, so we have to conjure it up with some reflection.  Oh the fun we'll have!! :frog::roller_coaster:
