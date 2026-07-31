package com.pdfpodcaster.android.data.remote.dto;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 62\u00020\u0001:\u000256B_\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0001\u0010\b\u001a\u00020\u0003\u0012\b\b\u0001\u0010\t\u001a\u00020\u0003\u0012\b\b\u0001\u0010\n\u001a\u00020\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rB=\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003JO\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\u0003H\u00d6\u0001J\t\u0010+\u001a\u00020,H\u00d6\u0001J&\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u00c1\u0001\u00a2\u0006\u0002\b4R\u001c\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0014\u0010\u0012R\u001c\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0016\u0010\u0012R\u001c\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0018\u0010\u0012R\u001c\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0019\u0010\u0010\u001a\u0004\b\u001a\u0010\u0012R\u001c\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u001c\u0010\u0012R\u001c\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001d\u0010\u0010\u001a\u0004\b\u001e\u0010\u0012\u00a8\u00067"}, d2 = {"Lcom/pdfpodcaster/android/data/remote/dto/AdminStatsDto;", "", "seen1", "", "totalUsers", "activeUsers", "totalJobs", "completedJobs", "totalGeminiUses", "totalTokensUsedToday", "remainingTokens", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(IIIIIIIILkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(IIIIIII)V", "getActiveUsers$annotations", "()V", "getActiveUsers", "()I", "getCompletedJobs$annotations", "getCompletedJobs", "getRemainingTokens$annotations", "getRemainingTokens", "getTotalGeminiUses$annotations", "getTotalGeminiUses", "getTotalJobs$annotations", "getTotalJobs", "getTotalTokensUsedToday$annotations", "getTotalTokensUsedToday", "getTotalUsers$annotations", "getTotalUsers", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$app_release", "$serializer", "Companion", "app_release"})
public final class AdminStatsDto {
    private final int totalUsers = 0;
    private final int activeUsers = 0;
    private final int totalJobs = 0;
    private final int completedJobs = 0;
    private final int totalGeminiUses = 0;
    private final int totalTokensUsedToday = 0;
    private final int remainingTokens = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.pdfpodcaster.android.data.remote.dto.AdminStatsDto.Companion Companion = null;
    
    public AdminStatsDto(int totalUsers, int activeUsers, int totalJobs, int completedJobs, int totalGeminiUses, int totalTokensUsedToday, int remainingTokens) {
        super();
    }
    
    public final int getTotalUsers() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "total_users")
    @java.lang.Deprecated()
    public static void getTotalUsers$annotations() {
    }
    
    public final int getActiveUsers() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "active_users")
    @java.lang.Deprecated()
    public static void getActiveUsers$annotations() {
    }
    
    public final int getTotalJobs() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "total_jobs")
    @java.lang.Deprecated()
    public static void getTotalJobs$annotations() {
    }
    
    public final int getCompletedJobs() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "completed_jobs")
    @java.lang.Deprecated()
    public static void getCompletedJobs$annotations() {
    }
    
    public final int getTotalGeminiUses() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "total_gemini_uses")
    @java.lang.Deprecated()
    public static void getTotalGeminiUses$annotations() {
    }
    
    public final int getTotalTokensUsedToday() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "total_tokens_used_today")
    @java.lang.Deprecated()
    public static void getTotalTokensUsedToday$annotations() {
    }
    
    public final int getRemainingTokens() {
        return 0;
    }
    
    @kotlinx.serialization.SerialName(value = "remaining_tokens")
    @java.lang.Deprecated()
    public static void getRemainingTokens$annotations() {
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.pdfpodcaster.android.data.remote.dto.AdminStatsDto copy(int totalUsers, int activeUsers, int totalJobs, int completedJobs, int totalGeminiUses, int totalTokensUsedToday, int remainingTokens) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic()
    public static final void write$Self$app_release(@org.jetbrains.annotations.NotNull()
    com.pdfpodcaster.android.data.remote.dto.AdminStatsDto self, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull()
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/pdfpodcaster/android/data/remote/dto/AdminStatsDto.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/pdfpodcaster/android/data/remote/dto/AdminStatsDto;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"})
    @java.lang.Deprecated()
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.pdfpodcaster.android.data.remote.dto.AdminStatsDto> {
        @org.jetbrains.annotations.NotNull()
        public static final com.pdfpodcaster.android.data.remote.dto.AdminStatsDto.$serializer INSTANCE = null;
        
        private $serializer() {
            super();
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.pdfpodcaster.android.data.remote.dto.AdminStatsDto deserialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        @java.lang.Override()
        public void serialize(@org.jetbrains.annotations.NotNull()
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull()
        com.pdfpodcaster.android.data.remote.dto.AdminStatsDto value) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/pdfpodcaster/android/data/remote/dto/AdminStatsDto$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/pdfpodcaster/android/data/remote/dto/AdminStatsDto;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlinx.serialization.KSerializer<com.pdfpodcaster.android.data.remote.dto.AdminStatsDto> serializer() {
            return null;
        }
    }
}