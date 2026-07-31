package com.pdfpodcaster.android.ui.screens.oauthcallback;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0011R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/oauthcallback/OAuthCallbackViewModel;", "Landroidx/lifecycle/ViewModel;", "tokenDataStore", "Lcom/pdfpodcaster/android/data/local/TokenDataStore;", "loginUseCase", "Lcom/pdfpodcaster/android/domain/usecase/LoginUseCase;", "(Lcom/pdfpodcaster/android/data/local/TokenDataStore;Lcom/pdfpodcaster/android/domain/usecase/LoginUseCase;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/oauthcallback/OAuthState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "processToken", "", "token", "", "setError", "message", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class OAuthCallbackViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.data.local.TokenDataStore tokenDataStore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.LoginUseCase loginUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthState> state = null;
    
    @javax.inject.Inject()
    public OAuthCallbackViewModel(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.local.TokenDataStore tokenDataStore, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.LoginUseCase loginUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.oauthcallback.OAuthState> getState() {
        return null;
    }
    
    public final void processToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    public final void setError(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
}