-printmapping out.map
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes *Annotation*,EnclosingMethod,SourceFile,LineNumberTable,Signature,InnerClasses,Deprecated,Exceptions,RuntimeVisibleParameterAnnotations
-optimizations !class/unboxing/enum
-dontnote

# Preserve enumeration classes.
-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Preserve all .class method names.
-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

# Preserve Parcelable classes
-keep @kotlinx.parcelize.Parcelize class *

# Annotations
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

# App entities for navigation args
-keep class com.iadvize.conversation.sdk.demo.feature.product.Product { *; }

