package com.example.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.model.PlantStage
import com.example.ui.theme.ForestGreen
import com.example.ui.theme.LightSage
import com.example.ui.theme.MintGreen
import com.example.ui.theme.PrimaryGreen

@Composable
fun PlantGrowthCard(
    currentStage: PlantStage,
    daysElapsed: Double,
    selectedSkinId: String = "skin_natural",
    lang: String = "en",
    modifier: Modifier = Modifier
) {
    val nextStage = PlantStage.getNextStage(currentStage)
    val progress = PlantStage.calculateProgress(daysElapsed)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "plantProgress"
    )

    // Calculate dynamic size based on growth stage
    val targetImageSizeDp: Dp = when (currentStage) {
        PlantStage.Seed -> 130.dp
        PlantStage.Sprout -> 180.dp
        PlantStage.Sapling -> 240.dp
        PlantStage.BigTree -> 310.dp
    }

    val animatedImageSize by animateDpAsState(
        targetValue = targetImageSizeDp,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "treeDynamicScale"
    )

    val displayedImageRes = when (selectedSkinId) {
        "skin_sakura" -> if (currentStage.stageLevel >= 3) com.example.R.drawable.realistic_sakura_tree_1786050918135 else currentStage.imageResId
        "skin_golden" -> if (currentStage.stageLevel >= 3) com.example.R.drawable.realistic_golden_bonsai_1786050929318 else currentStage.imageResId
        "skin_autumn" -> if (currentStage.stageLevel >= 3) com.example.R.drawable.realistic_autumn_maple_1786051408113 else currentStage.imageResId
        "skin_mystic" -> if (currentStage.stageLevel >= 3) com.example.R.drawable.realistic_mystic_crystal_tree_1786051397513 else currentStage.imageResId
        "skin_cyber" -> if (currentStage.stageLevel >= 3) com.example.R.drawable.realistic_cyberpunk_neon_tree_1786051417084 else currentStage.imageResId
        "skin_cosmic" -> if (currentStage.stageLevel >= 3) com.example.R.drawable.realistic_cosmic_starlight_tree_1786051424991 else currentStage.imageResId
        else -> currentStage.imageResId
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("plant_growth_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Stage Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = "Stage",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${com.example.util.AppStrings.get("stage_level", lang)} ${currentStage.stageLevel}/4",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }

                if (currentStage == PlantStage.BigTree) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.secondary,
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Master",
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = com.example.util.AppStrings.get("master_achieved", lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Plant Stage Image Artwork with Halo Glow
            Box(
                modifier = Modifier
                    .size(animatedImageSize + 20.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MintGreen.copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        )
                    )
                    .border(3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = displayedImageRes,
                    animationSpec = tween(500),
                    label = "PlantImageFade"
                ) { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = currentStage.getName(lang),
                        modifier = Modifier
                            .size(animatedImageSize)
                            .clip(CircleShape)
                            .shadow(8.dp, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stage Title
            Text(
                text = currentStage.getName(lang),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Stage Description
            Text(
                text = currentStage.getDescription(lang),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Progress Bar to Next Milestone
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (nextStage != null) {
                            "${com.example.util.AppStrings.get("next_stage", lang)}: ${nextStage.getName(lang)}"
                        } else {
                            com.example.util.AppStrings.get("max_growth_achieved", lang)
                        },
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (nextStage != null) {
                        "${com.example.util.AppStrings.get("goal_days", lang)}: ${nextStage.minDaysRequired} (${daysElapsed.toInt()})"
                    } else {
                        com.example.util.AppStrings.get("congrats_30_days", lang)
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    ),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
