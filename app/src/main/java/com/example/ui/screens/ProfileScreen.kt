package com.example.ui.screens

import com.example.util.AppStrings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.model.AvatarOption
import com.example.ui.model.PRESET_AVATARS
import com.example.ui.model.PRESET_PLANT_SKINS
import com.example.ui.model.PRESET_THEMES
import com.example.ui.model.PlantSkinOption
import com.example.ui.model.ThemeOption
import com.example.ui.model.UserProfile
import com.example.ui.viewmodel.HabitViewModel
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    viewModel: HabitViewModel,
    profile: UserProfile,
    stats: com.example.ui.viewmodel.StatisticsData,
    modifier: Modifier = Modifier
) {
    val lang = profile.language

    val context = androidx.compose.ui.platform.LocalContext.current
    val activity = context as? android.app.Activity

    var showEditDialog by remember { mutableStateOf(false) }
    var editNameInput by remember { mutableStateOf(profile.userName) }
    var editBioInput by remember { mutableStateOf(profile.userBio) }

    var itemToUnlockWithAd by remember { mutableStateOf<String?>(null) }

    val triggerWatchAd: (String) -> Unit = { itemId ->
        if (activity != null) {
            com.example.util.AdMobManager.loadAndShowRewardedAd(
                activity = activity,
                onRewardEarned = {
                    viewModel.unlockItemWithAd(itemId)
                    if (itemId.startsWith("avatar_")) {
                        viewModel.selectAvatar(itemId)
                    } else if (PRESET_THEMES.any { it.id == itemId }) {
                        viewModel.selectTheme(itemId)
                    } else if (PRESET_PLANT_SKINS.any { it.id == itemId }) {
                        viewModel.selectPlantSkin(itemId)
                    }
                },
                onAdFailedToLoadOrShow = {
                    itemToUnlockWithAd = itemId
                }
            )
        } else {
            itemToUnlockWithAd = itemId
        }
    }

    val currentAvatar = PRESET_AVATARS.find { it.id == profile.selectedAvatarId }
        ?: PRESET_AVATARS.first()
    val currentAvatarRes = currentAvatar.imageResId

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = {
                Text(
                    text = AppStrings.get("edit_profile_title", lang),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    OutlinedTextField(
                        value = editNameInput,
                        onValueChange = { editNameInput = it },
                        label = {
                            Text(AppStrings.get("your_name_label", lang))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = editBioInput,
                        onValueChange = { editBioInput = it },
                        label = {
                            Text(AppStrings.get("your_bio_label", lang))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateProfileNameAndBio(editNameInput, editBioInput)
                        showEditDialog = false
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("save_profile_button")
                ) {
                    Text(AppStrings.get("save_profile_btn", lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text(AppStrings.get("cancel_btn", lang))
                }
            }
        )
    }          "es" -> "Cancelar"
                            "hi" -> "रद्द करें"
                            "ar" -> "إلغاء"
                            else -> "Cancel"
                        }
                    )
                }
            }
        )
    }

    // Rewarded Ad Simulation Overlay
    itemToUnlockWithAd?.let { itemId ->
        SimulatedRewardedAdDialog(
            itemId = itemId,
            lang = lang,
            onAdCompleted = {
                viewModel.unlockItemWithAd(itemId)

                // Auto select unlocked item
                if (itemId.startsWith("avatar_")) {
                    viewModel.selectAvatar(itemId)
                } else if (PRESET_THEMES.any { it.id == itemId }) {
                    viewModel.selectTheme(itemId)
                } else if (PRESET_PLANT_SKINS.any { it.id == itemId }) {
                    viewModel.selectPlantSkin(itemId)
                }

                itemToUnlockWithAd = null
            },
            onDismiss = { itemToUnlockWithAd = null }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Card Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = currentAvatarRes),
                            contentDescription = "Profile Avatar",
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .border(
                                    3.dp,
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                ),
                            contentScale = ContentScale.Crop
                        )

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    editNameInput = profile.userName
                                    editBioInput = profile.userBio
                                    showEditDialog = true
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Profile",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = profile.userName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = profile.userBio,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats Banner inside profile
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = when (lang) {
                                    "bn" -> "সর্বোচ্চ রেকর্ড"
                                    "es" -> "Récord Más Largo"
                                    "hi" -> "सर्वश्रेष्ठ रिकॉर्ड"
                                    "ar" -> "أطول إنجاز"
                                    else -> "Best Record"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                            Text(
                                text = when (lang) {
                                    "bn" -> "${stats.longestStreakDays.toInt()} দিন"
                                    "es" -> "${stats.longestStreakDays.toInt()} días"
                                    "hi" -> "${stats.longestStreakDays.toInt()} दिन"
                                    "ar" -> "${stats.longestStreakDays.toInt()} أيام"
                                    else -> "${stats.longestStreakDays.toInt()} Days"
                                },
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = when (lang) {
                                    "bn" -> "বর্তমান স্ট্রাইক"
                                    "es" -> "Racha Actual"
                                    "hi" -> "वर्तमान स्ट्रीक"
                                    "ar" -> "السلسلة الحالية"
                                    else -> "Current Streak"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                            Text(
                                text = when (lang) {
                                    "bn" -> "${stats.currentStreakDays.toInt()} দিন"
                                    "es" -> "${stats.currentStreakDays.toInt()} días"
                                    "hi" -> "${stats.currentStreakDays.toInt()} दिन"
                                    "ar" -> "${stats.currentStreakDays.toInt()} أيام"
                                    else -> "${stats.currentStreakDays.toInt()} Days"
                                },
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
        }

        // Language Selector Card
        item {
            LanguageSelectionCard(
                currentLang = lang,
                onLanguageSelected = { newLang ->
                    viewModel.updateLanguage(newLang)
                }
            )
        }

        // Avatar Unlocking Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = when (lang) {
                                "bn" -> "প্রোফাইল পিকচার ও অবতারস"
                                "es" -> "Avatares de Perfil"
                                "hi" -> "प्रोफ़ाइल अवतार"
                                "ar" -> "الصورة الشخصية والرمزية"
                                else -> "Profile Avatars"
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Text(
                        text = when (lang) {
                            "bn" -> "রেকর্ড বা অ্যাড দেখে আনলক করুন"
                            "es" -> "Desbloquea con racha o anuncios"
                            "hi" -> "स्ट्रीक या विज्ञापन से अनलॉक करें"
                            "ar" -> "افتح بالإنجاز أو الإعلانات"
                            else -> "Unlock via streak or ad"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(PRESET_AVATARS) { avatar ->
                        val isUnlocked = profile.isItemUnlocked(
                            avatar.id,
                            avatar.requiredDays,
                            stats.longestStreakDays
                        )
                        val isSelected = profile.selectedAvatarId == avatar.id

                        AvatarCardItem(
                            avatar = avatar,
                            lang = lang,
                            isUnlocked = isUnlocked,
                            isSelected = isSelected,
                            onSelect = {
                                if (isUnlocked) {
                                    viewModel.selectAvatar(avatar.id)
                                }
                            },
                            onWatchAdToUnlock = {
                                triggerWatchAd(avatar.id)
                            }
                        )
                    }
                }
            }
        }

        // App Theme Unlocking Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Palette,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (lang) {
                            "bn" -> "অ্যাপ থিমসমূহ"
                            "es" -> "Temas de la Aplicación"
                            "hi" -> "ऐप थीम्स"
                            "ar" -> "سمات التطبيق"
                            else -> "App Themes"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PRESET_THEMES.forEach { theme ->
                        val isUnlocked = profile.isItemUnlocked(
                            theme.id,
                            theme.requiredDays,
                            stats.longestStreakDays
                        )
                        val isSelected = profile.selectedThemeId == theme.id

                        ThemeCardItem(
                            theme = theme,
                            lang = lang,
                            isUnlocked = isUnlocked,
                            isSelected = isSelected,
                            currentStreakDays = stats.longestStreakDays,
                            onSelect = {
                                if (isUnlocked) {
                                    viewModel.selectTheme(theme.id)
                                }
                            },
                            onWatchAdToUnlock = {
                                triggerWatchAd(theme.id)
                            }
                        )
                    }
                }
            }
        }

        // Seed & Plant Tree Themes Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (lang) {
                            "bn" -> "বীজ ও গাছের স্কিন থিমসমূহ"
                            "es" -> "Diseños de Semilla y Árbol"
                            "hi" -> "पौधे और बीज थीम"
                            "ar" -> "سمات النباتات والأشجار"
                            else -> "Seed & Tree Plant Skins"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    PRESET_PLANT_SKINS.forEach { skin ->
                        val isUnlocked = profile.isItemUnlocked(
                            skin.id,
                            skin.requiredDays,
                            stats.longestStreakDays
                        )
                        val isSelected = profile.selectedPlantSkinId == skin.id

                        PlantSkinCardItem(
                            skin = skin,
                            lang = lang,
                            isUnlocked = isUnlocked,
                            isSelected = isSelected,
                            currentStreakDays = stats.longestStreakDays,
                            onSelect = {
                                if (isUnlocked) {
                                    viewModel.selectPlantSkin(skin.id)
                                }
                            },
                            onWatchAdToUnlock = {
                                triggerWatchAd(skin.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LanguageSelectionCard(
    currentLang: String,
    onLanguageSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Language,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (currentLang) {
                        "bn" -> "🌐 অ্যাপের ভাষা (App Language)"
                        "es" -> "🌐 Idioma de la Aplicación"
                        "hi" -> "🌐 ऐप की भाषा (App Language)"
                        "ar" -> "🌐 لغة التطبيق"
                        else -> "🌐 App Language (Default: English)"
                    },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val languages = listOf(
                "en" to "🇺🇸 English",
                "bn" to "🇧🇩 বাংলা",
                "es" to "🇪🇸 Español",
                "hi" to "🇮🇳 हिंदी",
                "ar" to "🇸🇦 العربية",
                "fr" to "🇫🇷 Français",
                "de" to "🇩🇪 Deutsch",
                "zh" to "🇨🇳 中文",
                "pt" to "🇵🇹 Português",
                "ru" to "🇷🇺 Русский",
                "ja" to "🇯🇵 日本語",
                "ko" to "🇰🇷 한국어",
                "id" to "🇮🇩 Indonesia",
                "tr" to "🇹🇷 Türkçe",
                "ur" to "🇵🇰 اردو"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                languages.forEach { (code, label) ->
                    val isSelected = currentLang == code
                    FilterChip(
                        selected = isSelected,
                        onClick = { onLanguageSelected(code) },
                        label = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AvatarCardItem(
    avatar: AvatarOption,
    lang: String,
    isUnlocked: Boolean,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onWatchAdToUnlock: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable(enabled = isUnlocked) { onSelect() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.TopEnd) {
                Image(
                    painter = painterResource(id = avatar.imageResId),
                    contentDescription = avatar.getTitle(lang),
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                            CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )

                if (!isUnlocked) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White
                        )
                    }
                } else if (isSelected) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = avatar.getTitle(lang),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (isUnlocked) {
                Text(
                    text = if (isSelected) {
                        when (lang) {
                            "bn" -> "ব্যবহার হচ্ছে"
                            "es" -> "En uso"
                            "hi" -> "उपयोग में"
                            "ar" -> "قيد الاستخدام"
                            else -> "In Use"
                        }
                    } else {
                        when (lang) {
                            "bn" -> "আনলকড"
                            "es" -> "Desbloqueado"
                            "hi" -> "अनलाक्ड"
                            "ar" -> "مفتوح"
                            else -> "Unlocked"
                        }
                    },
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                )
            } else {
                Text(
                    text = when (lang) {
                        "bn" -> "${avatar.requiredDays} দিনের রেকর্ড"
                        "es" -> "${avatar.requiredDays}d de racha"
                        "hi" -> "${avatar.requiredDays} दिनों का रिकॉर्ड"
                        "ar" -> "سلسلة ${avatar.requiredDays} أيام"
                        else -> "${avatar.requiredDays}d Record"
                    },
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = onWatchAdToUnlock,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.OndemandVideo,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when (lang) {
                            "bn" -> "অ্যাড দেখুন"
                            "es" -> "Ver Anuncio"
                            "hi" -> "विज्ञापन देखें"
                            "ar" -> "شاهد إعلان"
                            else -> "Watch Ad"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun ThemeCardItem(
    theme: ThemeOption,
    lang: String,
    isUnlocked: Boolean,
    isSelected: Boolean,
    currentStreakDays: Double,
    onSelect: () -> Unit,
    onWatchAdToUnlock: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Color swatch
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            try {
                                Color(android.graphics.Color.parseColor(theme.primaryColorHex))
                            } catch (e: Exception) {
                                MaterialTheme.colorScheme.primary
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (!isUnlocked) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    } else if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = theme.getName(lang),
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = if (isUnlocked) {
                            if (isSelected) {
                                when (lang) {
                                    "bn" -> "বর্তমানে সক্রিয়"
                                    "es" -> "Tema Activo"
                                    "hi" -> "वर्तमान में सक्रिय"
                                    "ar" -> "السمة النشطة"
                                    else -> "Currently Active"
                                }
                            } else {
                                when (lang) {
                                    "bn" -> "ব্যবহার করতে ট্যাপ করুন"
                                    "es" -> "Toca para aplicar"
                                    "hi" -> "उपयोग करने के लिए टैप करें"
                                    "ar" -> "انقر للاستخدام"
                                    else -> "Tap to Apply"
                                }
                            }
                        } else {
                            when (lang) {
                                "bn" -> "প্রয়োজন: ${theme.requiredDays} দিনের রেকর্ড"
                                "es" -> "Requiere racha de ${theme.requiredDays}d"
                                "hi" -> "आवश्यक: ${theme.requiredDays} दिनों का रिकॉर्ड"
                                "ar" -> "يتطلب سلسلة ${theme.requiredDays} أيام"
                                else -> "Requires ${theme.requiredDays}d Streak"
                            }
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            if (!isUnlocked) {
                Button(
                    onClick = onWatchAdToUnlock,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.OndemandVideo,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (lang) {
                                "bn" -> "এড দেখে আনলক"
                                "es" -> "Ver Anuncio"
                                "hi" -> "विज्ञापन अनलॉक"
                                "ar" -> "فتح بإعلان"
                                else -> "Ad Unlock"
                            },
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlantSkinCardItem(
    skin: PlantSkinOption,
    lang: String,
    isUnlocked: Boolean,
    isSelected: Boolean,
    currentStreakDays: Double,
    onSelect: () -> Unit,
    onWatchAdToUnlock: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = skin.previewImageResId),
                contentDescription = skin.getName(lang),
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = skin.getName(lang),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = skin.getDescription(lang),
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                if (!isUnlocked) {
                    Text(
                        text = when (lang) {
                            "bn" -> "লকড: ${skin.requiredDays} দিন অথবা এড দেখুন"
                            "es" -> "Bloqueado: ${skin.requiredDays} días o ver anuncio"
                            "hi" -> "लॉक: ${skin.requiredDays} दिन या विज्ञापन देखें"
                            "ar" -> "مغلق: ${skin.requiredDays} أيام أو شاهد إعلان"
                            else -> "Locked: ${skin.requiredDays}d Streak or Watch Ad"
                        },
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary
                )
            } else if (isUnlocked) {
                OutlinedButton(
                    onClick = onSelect,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        when (lang) {
                            "bn" -> "ব্যবহার করুন"
                            "es" -> "Usar"
                            "hi" -> "उपयोग करें"
                            "ar" -> "استخدام"
                            else -> "Apply"
                        }
                    )
                }
            } else {
                Button(
                    onClick = onWatchAdToUnlock,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            when (lang) {
                                "bn" -> "এড দেখুন"
                                "es" -> "Ver Anuncio"
                                "hi" -> "विज्ञापन देखें"
                                "ar" -> "شاهد إعلان"
                                else -> "Watch Ad"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SimulatedRewardedAdDialog(
    itemId: String,
    lang: String = "en",
    onAdCompleted: () -> Unit,
    onDismiss: () -> Unit
) {
    var secondsLeft by remember { mutableIntStateOf(10) }
    var isAdDone by remember { mutableStateOf(false) }

    LaunchedEffect(itemId) {
        while (secondsLeft > 0) {
            delay(1000L)
            secondsLeft--
        }
        isAdDone = true
    }

    AlertDialog(
        onDismissRequest = {
            if (isAdDone) onDismiss()
        },
        shape = RoundedCornerShape(24.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.OndemandVideo,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = when (lang) {
                        "bn" -> "নন-স্কিপেবল রিওয়ার্ডেড এড"
                        "es" -> "Anuncio de Video Recompensado"
                        "hi" -> "गैर-स्किप करने योग्य पुरस्कृत विज्ञापन"
                        "ar" -> "إعلان فيديو مكافأة غیر قابل للتخطي"
                        else -> "Non-Skippable Rewarded Ad"
                    },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Video Frame Mock
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Stars,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = when (lang) {
                                "bn" -> "পুরস্কার আনলক স্পন্সরড ভিডিও"
                                "es" -> "Video Patrocinado para Recompensa"
                                "hi" -> "पुरस्कार अनलॉक प्रायोजित वीडियो"
                                "ar" -> "فيديو برعاية لفتح المكافأة"
                                else -> "Sponsored Reward Video"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f)
                        ) {
                            Text(
                                text = when (lang) {
                                    "bn" -> "🔒 নন-স্কিপেবল (সম্পূর্ণ দেখা বাধ্যতামূলক)"
                                    "es" -> "🔒 Obligatorio ver completo"
                                    "hi" -> "🔒 पूरा देखना अनिवार्य है"
                                    "ar" -> "🔒 مشاهذة كاملة إجبارية"
                                    else -> "🔒 Non-Skippable (Full watch required)"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (!isAdDone) {
                    LinearProgressIndicator(
                        progress = { (10f - secondsLeft) / 10f },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (lang) {
                            "bn" -> "পুরস্কার গ্রহণের জন্য $secondsLeft সেকেন্ড অপেক্ষা করুন..."
                            "es" -> "Espera $secondsLeft segundos para reclamar la recompensa..."
                            "hi" -> "पुरस्कार पाने के लिए $secondsLeft सेकंड प्रतीक्षा करें..."
                            "ar" -> "انتظر $secondsLeft ثانية للحصول على المكافأة..."
                            else -> "Please wait $secondsLeft seconds to claim reward..."
                        },
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    )
                } else {
                    Text(
                        text = when (lang) {
                            "bn" -> "🎉 অভিনন্দন! ভিডিও সম্পন্ন হয়েছে। আপনার পুরস্কার প্রস্তুত!"
                            "es" -> "🎉 ¡Felicidades! Video completado. ¡Tu recompensa está lista!"
                            "hi" -> "🎉 बधाई! वीडियो पूरा हुआ। आपका पुरस्कार तैयार है!"
                            "ar" -> "🎉 تهانينا! اكتمل الفيديو. مكافأتك جاهزة!"
                            else -> "🎉 Congratulations! Video finished. Your reward is unlocked!"
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onAdCompleted,
                enabled = isAdDone,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (isAdDone) {
                        when (lang) {
                            "bn" -> "পুরস্কার গ্রহণ ও আনলক করুন"
                            "es" -> "Reclamar y Aplicar"
                            "hi" -> "पुरस्कार दावा करें"
                            "ar" -> "استلام المكافأة"
                            else -> "Claim Reward & Apply"
                        }
                    } else {
                        when (lang) {
                            "bn" -> "ভিডিও চলছে (${secondsLeft}s)..."
                            "es" -> "Reproduciendo (${secondsLeft}s)..."
                            "hi" -> "चल रहा है (${secondsLeft}s)..."
                            "ar" -> "جاري العرض (${secondsLeft}s)..."
                            else -> "Playing Ad (${secondsLeft}s)..."
                        }
                    }
                )
            }
        },
        dismissButton = null
    )
}
