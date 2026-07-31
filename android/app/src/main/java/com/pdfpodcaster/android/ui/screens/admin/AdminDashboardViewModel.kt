package com.pdfpodcaster.android.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdfpodcaster.android.domain.model.AdminStats
import com.pdfpodcaster.android.domain.model.AdminUser
import com.pdfpodcaster.android.domain.model.PlanType
import com.pdfpodcaster.android.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminState(
    val stats: AdminStats? = null,
    val users: List<AdminUser> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedUser: AdminUser? = null,
    val showEditDialog: Boolean = false,
    val showCreateDialog: Boolean = false,
    val showDeleteConfirm: Boolean = false,
    val showResetPasswordDialog: Boolean = false,
    val dialogEmail: String = "",
    val dialogPlan: String = "free",
    val dialogQuota: Int = 1,
    val dialogIsAdmin: Boolean = false,
    val dialogPassword: String = "",
    val dialogSuccess: String? = null,
    val dialogError: String? = null,
    val isDialogLoading: Boolean = false
)

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val getAdminStatsUseCase: com.pdfpodcaster.android.domain.usecase.GetAdminStatsUseCase,
    private val getAdminUsersUseCase: com.pdfpodcaster.android.domain.usecase.GetAdminUsersUseCase,
    private val createAdminUserUseCase: com.pdfpodcaster.android.domain.usecase.CreateAdminUserUseCase,
    private val updateAdminUserUseCase: com.pdfpodcaster.android.domain.usecase.UpdateAdminUserUseCase,
    private val toggleAdminUserStatusUseCase: com.pdfpodcaster.android.domain.usecase.ToggleAdminUserStatusUseCase,
    private val resetAdminPasswordUseCase: com.pdfpodcaster.android.domain.usecase.ResetAdminPasswordUseCase,
    private val deleteAdminUserUseCase: com.pdfpodcaster.android.domain.usecase.DeleteAdminUserUseCase,
    private val bulkDeleteOldUsersUseCase: com.pdfpodcaster.android.domain.usecase.BulkDeleteOldUsersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AdminState())
    val state: StateFlow<AdminState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getAdminStatsUseCase()
                .onSuccess { stats ->
                    _state.value = _state.value.copy(stats = stats)
                }
            getAdminUsersUseCase()
                .onSuccess { users ->
                    _state.value = _state.value.copy(users = users, isLoading = false)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(isLoading = false, error = e.message)
                }
        }
    }

    fun showCreateDialog() {
        _state.value = _state.value.copy(
            showCreateDialog = true,
            dialogEmail = "",
            dialogPassword = "",
            dialogPlan = "free",
            dialogQuota = 1,
            dialogIsAdmin = false,
            dialogSuccess = null,
            dialogError = null
        )
    }

    fun hideDialog() {
        _state.value = _state.value.copy(
            showEditDialog = false,
            showCreateDialog = false,
            showDeleteConfirm = false,
            showResetPasswordDialog = false,
            dialogSuccess = null,
            dialogError = null
        )
    }

    fun showEditDialog(user: AdminUser) {
        _state.value = _state.value.copy(
            selectedUser = user,
            showEditDialog = true,
            dialogEmail = user.email,
            dialogPlan = user.plan.name.lowercase(),
            dialogQuota = user.dailyQuota,
            dialogIsAdmin = user.isAdmin,
            dialogSuccess = null,
            dialogError = null
        )
    }

    fun showDeleteConfirm(user: AdminUser) {
        _state.value = _state.value.copy(
            selectedUser = user,
            showDeleteConfirm = true
        )
    }

    fun showResetPasswordDialog(user: AdminUser) {
        _state.value = _state.value.copy(
            selectedUser = user,
            showResetPasswordDialog = true,
            dialogPassword = "",
            dialogSuccess = null,
            dialogError = null
        )
    }

    fun onDialogEmailChange(email: String) {
        _state.value = _state.value.copy(dialogEmail = email)
    }

    fun onDialogPlanChange(plan: String) {
        _state.value = _state.value.copy(dialogPlan = plan)
    }

    fun onDialogQuotaChange(quota: Int) {
        _state.value = _state.value.copy(dialogQuota = quota)
    }

    fun onDialogIsAdminChange(isAdmin: Boolean) {
        _state.value = _state.value.copy(dialogIsAdmin = isAdmin)
    }

    fun onDialogPasswordChange(password: String) {
        _state.value = _state.value.copy(dialogPassword = password)
    }

    fun createUser() {
        val email = _state.value.dialogEmail
        if (email.isBlank()) {
            _state.value = _state.value.copy(dialogError = "Email is required")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isDialogLoading = true)
            createAdminUserUseCase(
                email = email,
                password = _state.value.dialogPassword.ifBlank { null },
                plan = _state.value.dialogPlan,
                dailyQuota = _state.value.dialogQuota,
                isAdmin = _state.value.dialogIsAdmin
            ).onSuccess {
                _state.value = _state.value.copy(
                    isDialogLoading = false,
                    dialogSuccess = "User created successfully"
                )
                loadData()
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    isDialogLoading = false,
                    dialogError = e.message ?: "Failed to create user"
                )
            }
        }
    }

    fun updateUser() {
        val user = _state.value.selectedUser ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(isDialogLoading = true)
            updateAdminUserUseCase(
                userId = user.id,
                email = _state.value.dialogEmail,
                plan = _state.value.dialogPlan,
                dailyQuota = _state.value.dialogQuota,
                isAdmin = _state.value.dialogIsAdmin
            ).onSuccess {
                _state.value = _state.value.copy(
                    isDialogLoading = false,
                    dialogSuccess = "User updated successfully"
                )
                loadData()
            }.onFailure { e ->
                _state.value = _state.value.copy(
                    isDialogLoading = false,
                    dialogError = e.message ?: "Failed to update user"
                )
            }
        }
    }

    fun toggleUserStatus(user: AdminUser) {
        viewModelScope.launch {
            toggleAdminUserStatusUseCase(user.id)
            loadData()
        }
    }

    fun resetPassword() {
        val user = _state.value.selectedUser ?: return
        val password = _state.value.dialogPassword
        if (password.length < 8) {
            _state.value = _state.value.copy(dialogError = "Password must be at least 8 characters")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isDialogLoading = true)
            resetAdminPasswordUseCase(user.id, password)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isDialogLoading = false,
                        dialogSuccess = "Password reset successfully"
                    )
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(
                        isDialogLoading = false,
                        dialogError = e.message ?: "Failed to reset password"
                    )
                }
        }
    }

    fun deleteUser() {
        val user = _state.value.selectedUser ?: return
        viewModelScope.launch {
            deleteAdminUserUseCase(user.id)
            hideDialog()
            loadData()
        }
    }

    fun bulkDeleteOldUsers() {
        viewModelScope.launch {
            bulkDeleteOldUsersUseCase()
            loadData()
        }
    }
}