package com.example.util

import android.content.Context
import android.content.SharedPreferences
import com.example.ui.model.UserProfile

class PreferencesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("habit_app_user_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_BIO = "user_bio"
        private const val KEY_AVATAR_ID = "selected_avatar_id"
        private const val KEY_THEME_ID = "selected_theme_id"
        private const val KEY_THEME_MODE = "theme_mode" // "system", "light", "dark"
        private const val KEY_SKIN_ID = "selected_plant_skin_id"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_UNLOCKED_ITEMS = "unlocked_item_ids"
    }

    fun loadUserProfile(): UserProfile {
        val userName = prefs.getString(KEY_USER_NAME, "Green Voyager") ?: "Green Voyager"
        val userBio = prefs.getString(
            KEY_USER_BIO,
            "Committed to breaking bad habits & growing a healthier life."
        ) ?: "Committed to breaking bad habits & growing a healthier life."
        val selectedAvatarId = prefs.getString(KEY_AVATAR_ID, "avatar_sprout") ?: "avatar_sprout"
        val selectedThemeId = prefs.getString(KEY_THEME_ID, "vibrant") ?: "vibrant"
        val themeMode = prefs.getString(KEY_THEME_MODE, "system") ?: "system"
        val selectedPlantSkinId = prefs.getString(KEY_SKIN_ID, "skin_natural") ?: "skin_natural"
        val language = prefs.getString(KEY_LANGUAGE, "en") ?: "en"
        val unlockedItemsString = prefs.getStringSet(
            KEY_UNLOCKED_ITEMS,
            setOf("avatar_sprout", "vibrant", "skin_natural")
        ) ?: setOf("avatar_sprout", "vibrant", "skin_natural")

        return UserProfile(
            userName = userName,
            userBio = userBio,
            selectedAvatarId = selectedAvatarId,
            selectedThemeId = selectedThemeId,
            themeMode = themeMode,
            selectedPlantSkinId = selectedPlantSkinId,
            language = language,
            unlockedItemIds = unlockedItemsString.toSet()
        )
    }

    fun saveUserProfile(profile: UserProfile) {
        prefs.edit().apply {
            putString(KEY_USER_NAME, profile.userName)
            putString(KEY_USER_BIO, profile.userBio)
            putString(KEY_AVATAR_ID, profile.selectedAvatarId)
            putString(KEY_THEME_ID, profile.selectedThemeId)
            putString(KEY_THEME_MODE, profile.themeMode)
            putString(KEY_SKIN_ID, profile.selectedPlantSkinId)
            putString(KEY_LANGUAGE, profile.language)
            putStringSet(KEY_UNLOCKED_ITEMS, profile.unlockedItemIds)
            apply()
        }
    }
}
