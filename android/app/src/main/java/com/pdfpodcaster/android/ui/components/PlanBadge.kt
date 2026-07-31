package com.pdfpodcaster.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pdfpodcaster.android.domain.model.PlanType
import com.pdfpodcaster.android.ui.theme.AppColors

@Composable
fun PlanBadge(plan: PlanType) {
    val (label, color) = when (plan) {
        PlanType.FREE -> "Free" to AppColors.FreePlan
        PlanType.BASIC -> "Basic" to AppColors.BasicPlan
        PlanType.PRO -> "Pro" to AppColors.ProPlan
        PlanType.BUSINESS -> "Business" to AppColors.BusinessPlan
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}