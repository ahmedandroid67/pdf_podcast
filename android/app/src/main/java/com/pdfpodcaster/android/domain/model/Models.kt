package com.pdfpodcaster.android.domain.model

enum class AudioMode(val value: String, val label: String) {
    PODCAST("podcast", "Podcast"),
    DIALOGUE("dialogue", "Dialogue"),
    AUDIOBOOK("audiobook", "Audiobook")
}

enum class PodcastLanguage(
    val code: String,
    val displayName: String,
    val flag: String
) {
    ENGLISH("en", "English", "\uD83C\uDDFA\uD83C\uDDF8"),
    FRENCH("fr", "French", "\uD83C\uDDEB\uD83C\uDDF7"),
    ARABIC("ar", "Arabic", "\uD83C\uDDF8\uD83C\uDDE6"),
    SPANISH("es", "Spanish", "\uD83C\uDDEA\uD83C\uDDF8"),
    GERMAN("de", "German", "\uD83C\uDDE9\uD83C\uDDEA"),
    PORTUGUESE("pt", "Portuguese", "\uD83C\uDDE7\uD83C\uDDF7"),
    ITALIAN("it", "Italian", "\uD83C\uDDEE\uD83C\uDDF9"),
    CHINESE("zh", "Chinese", "\uD83C\uDDE8\uD83C\uDDF3"),
    JAPANESE("ja", "Japanese", "\uD83C\uDDEF\uD83C\uDDF5"),
    KOREAN("ko", "Korean", "\uD83C\uDDF0\uD83C\uDDF7"),
    RUSSIAN("ru", "Russian", "\uD83C\uDDF7\uD83C\uDDFA"),
    DUTCH("nl", "Dutch", "\uD83C\uDDF3\uD83C\uDDF1"),
    TURKISH("tr", "Turkish", "\uD83C\uDDF9\uD83C\uDDF7"),
    POLISH("pl", "Polish", "\uD83C\uDDF5\uD83C\uDDF1"),
    HINDI("hi", "Hindi", "\uD83C\uDDEE\uD83C\uDDF3"),
    VIETNAMESE("vi", "Vietnamese", "\uD83C\uDDFB\uD83C\uDDF3")
}

data class AdminStats(
    val totalUsers: Int,
    val activeUsers: Int,
    val totalJobs: Int,
    val completedJobs: Int,
    val totalGeminiUses: Int,
    val totalTokensUsedToday: Int,
    val remainingTokens: Int
)

data class AdminUser(
    val id: String,
    val email: String,
    val plan: PlanType,
    val dailyQuota: Int,
    val usedToday: Int,
    val tokensUsedToday: Int,
    val totalTokensUsed: Int,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val createdAt: String,
    val totalJobs: Int
)