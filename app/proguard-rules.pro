# Add project specific ProGuard rules here.

# AndroidUSBCamera library
-keep class com.jiangdg.** { *; }
-dontwarn com.jiangdg.**

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}
