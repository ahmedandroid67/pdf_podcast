package com.pdfpodcaster.android.domain.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0081\u0002\u0018\u0000 \u000e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000eB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000f"}, d2 = {"Lcom/pdfpodcaster/android/domain/model/PipelineStep;", "", "label", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "QUEUED", "EXTRACTING", "SUMMARIZING", "SCRIPTING", "AUDIO", "DONE", "FAILED", "Companion", "app_debug"})
public enum PipelineStep {
    /*public static final*/ QUEUED /* = new QUEUED(null) */,
    /*public static final*/ EXTRACTING /* = new EXTRACTING(null) */,
    /*public static final*/ SUMMARIZING /* = new SUMMARIZING(null) */,
    /*public static final*/ SCRIPTING /* = new SCRIPTING(null) */,
    /*public static final*/ AUDIO /* = new AUDIO(null) */,
    /*public static final*/ DONE /* = new DONE(null) */,
    /*public static final*/ FAILED /* = new FAILED(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.pdfpodcaster.android.domain.model.PipelineStep.Companion Companion = null;
    
    PipelineStep(java.lang.String label) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.pdfpodcaster.android.domain.model.PipelineStep> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/pdfpodcaster/android/domain/model/PipelineStep$Companion;", "", "()V", "fromValue", "Lcom/pdfpodcaster/android/domain/model/PipelineStep;", "value", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.pdfpodcaster.android.domain.model.PipelineStep fromValue(@org.jetbrains.annotations.NotNull()
        java.lang.String value) {
            return null;
        }
    }
}