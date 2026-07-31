package com.pdfpodcaster.android.data.local;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u000fR\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t\u00a8\u0006\u0013"}, d2 = {"Lcom/pdfpodcaster/android/data/local/ThemePreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "appLanguageFlow", "Lkotlinx/coroutines/flow/Flow;", "", "getAppLanguageFlow", "()Lkotlinx/coroutines/flow/Flow;", "darkModeFlow", "getDarkModeFlow", "setAppLanguage", "", "lang", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDarkMode", "mode", "Keys", "app_release"})
public final class ThemePreferences {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> darkModeFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> appLanguageFlow = null;
    
    @javax.inject.Inject()
    public ThemePreferences(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getDarkModeFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setDarkMode(@org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> getAppLanguageFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setAppLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String lang, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u00c2\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\u00a8\u0006\n"}, d2 = {"Lcom/pdfpodcaster/android/data/local/ThemePreferences$Keys;", "", "()V", "APP_LANGUAGE", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getAPP_LANGUAGE", "()Landroidx/datastore/preferences/core/Preferences$Key;", "DARK_MODE", "getDARK_MODE", "app_release"})
    static final class Keys {
        @org.jetbrains.annotations.NotNull()
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> DARK_MODE = null;
        @org.jetbrains.annotations.NotNull()
        private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> APP_LANGUAGE = null;
        @org.jetbrains.annotations.NotNull()
        public static final com.pdfpodcaster.android.data.local.ThemePreferences.Keys INSTANCE = null;
        
        private Keys() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getDARK_MODE() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.String> getAPP_LANGUAGE() {
            return null;
        }
    }
}