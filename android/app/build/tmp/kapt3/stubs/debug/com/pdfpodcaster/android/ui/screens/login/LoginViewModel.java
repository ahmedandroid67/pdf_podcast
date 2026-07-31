package com.pdfpodcaster.android.ui.screens.login;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0006\u0010\u0010\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0013R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0016"}, d2 = {"Lcom/pdfpodcaster/android/ui/screens/login/LoginViewModel;", "Landroidx/lifecycle/ViewModel;", "loginUseCase", "Lcom/pdfpodcaster/android/domain/usecase/LoginUseCase;", "checkAuthUseCase", "Lcom/pdfpodcaster/android/domain/usecase/CheckAuthUseCase;", "(Lcom/pdfpodcaster/android/domain/usecase/LoginUseCase;Lcom/pdfpodcaster/android/domain/usecase/CheckAuthUseCase;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/pdfpodcaster/android/ui/screens/login/LoginState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "checkExistingAuth", "", "login", "onEmailChange", "email", "", "onPasswordChange", "password", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LoginViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.LoginUseCase loginUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.pdfpodcaster.android.domain.usecase.CheckAuthUseCase checkAuthUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.pdfpodcaster.android.ui.screens.login.LoginState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.login.LoginState> state = null;
    
    @javax.inject.Inject()
    public LoginViewModel(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.LoginUseCase loginUseCase, @org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.domain.usecase.CheckAuthUseCase checkAuthUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.pdfpodcaster.android.ui.screens.login.LoginState> getState() {
        return null;
    }
    
    private final void checkExistingAuth() {
    }
    
    public final void onEmailChange(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void onPasswordChange(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void login() {
    }
}