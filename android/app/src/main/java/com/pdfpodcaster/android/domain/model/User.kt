package com.pdfpodcaster.android.domain.model

enum class PlanType(val displayName: String, val color: Long) {
    FREE("Free", 0xFF9CA3AF),
    BASIC("Basic", 0xFF3B82F6),
    PRO("Pro", 0xFF8B5CF6),
    BUSINESS("Business", 0xFFF59E0B);

    companion object {
        fun fromValue(value: String): PlanType =
            entries.find { it.name.lowercase() == value.lowercase() } ?: FREE
    }
}

data class User(
    val id: String,
    val email: String,
    val plan: PlanType,
    val dailyQuota: Int,
    val usedToday: Int,
    val isActive: Boolean,
    val isAdmin: Boolean
) {
    val remainingQuota: Int get() = maxOf(0, dailyQuota - usedToday)
    val quotaPercentage: Float get() = if (dailyQuota > 0) usedToday.toFloat() / dailyQuota else 0f
}

data class PlanLimits(
    val dailyDocs: Int,
    val maxPages: Int,
    val maxFileSizeMb: Int,
    val canGeneratePodcast: Boolean,
    val maxStoredJobs: Int,
    val retentionDays: Int,
    val monthlyTokenLimit: Int
)

fun PlanType.limits() = when (this) {
    PlanType.FREE -> PlanLimits(1, 10, 5, false, 3, 1, 20_000)
    PlanType.BASIC -> PlanLimits(10, 50, 15, true, 30, 7, 150_000)
    PlanType.PRO -> PlanLimits(50, 150, 30, true, Int.MAX_VALUE, 30, 750_000)
    PlanType.BUSINESS -> PlanLimits(9999, 300, 100, true, Int.MAX_VALUE, Int.MAX_VALUE, 10_000_000)
}