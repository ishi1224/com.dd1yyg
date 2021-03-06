# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/QuestZhang/android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keepclassmembers class com.ddyyyg.shop.utils.PayJavaScript{
    public *;
}

-keepclassmembers class com.ddyyyg.shop.utils.SPUtils{
    public *;
}

-keep class com.ddyyyg.shop.utils.PayJavaScript.** { *; }
-keep class com.ddyyyg.shop.utils.SPUtils.** { *; }
-keep class com.ddyyyg.shop.ui.model.** { *; }
-keep class java.lang.reflect.** { *; }

-keep class com.thoughtworks.xstream.**{*;}#使用xstream重点

-dontwarn com.thoughtworks.xstream.**

-keepnames class * implements java.io.Serializable

#-keepclasseswithmembernames class* {#保持native方法不被混淆
#    native<methods>;
#}