# Add project specific ProGuard rules here.

# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keep interface com.pdfpodcaster.android.data.remote.api.** { *; }

# Keep generic signature of Call, Response, and Continuation (R8 full mode strips signatures from non-kept items)
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.pdfpodcaster.android.**$$serializer { *; }
-keepclassmembers class com.pdfpodcaster.android.** {
    *** Companion;
}
-keepclasseswithmembers class com.pdfpodcaster.android.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep @kotlinx.serialization.Serializable class * { *; }
-keepclassmembers class * {
    @kotlinx.serialization.Serializable <fields>;
}

# Keep Enums
-keepclassmembers enum * { *; }


# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# AndroidX Security / Google Tink (EncryptedSharedPreferences)
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**
-keep class com.google.crypto.tink.** { *; }
-dontwarn com.google.crypto.tink.**
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite { <fields>; }