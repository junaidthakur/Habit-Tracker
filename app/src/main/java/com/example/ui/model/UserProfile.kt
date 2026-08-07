package com.example.ui.model

import com.example.R

data class AvatarOption(
    val id: String,
    val titleEn: String,
    val titleBn: String,
    val requiredDays: Int,
    val imageResId: Int,
    val isUnlockedByDefault: Boolean = false
) {
    fun getTitle(lang: String): String = when(lang) {
        "bn" -> titleBn
        "es" -> when(id) {
            "avatar_sprout" -> "Brote Verde"
            "avatar_guardian" -> "Guardián del Bosque"
            "avatar_phoenix" -> "Fénix Dorado"
            "avatar_zen" -> "Maestro Zen"
            "avatar_champion" -> "Campeón Ecológico"
            "avatar_cosmic_dragon" -> "Dragón Cósmico"
            "avatar_crystal_lotus" -> "Loto de Cristal"
            else -> titleEn
        }
        "hi" -> when(id) {
            "avatar_sprout" -> "हरा अंकुर"
            "avatar_guardian" -> "वन रक्षक"
            "avatar_phoenix" -> "सुनहरा फीनिक्स"
            "avatar_zen" -> "ज़ेन मास्टर"
            "avatar_champion" -> "इको चैंपियन"
            "avatar_cosmic_dragon" -> "कॉस्मिक ड्रैगन"
            "avatar_crystal_lotus" -> "क्रिस्टल कमल"
            else -> titleEn
        }
        "ar" -> when(id) {
            "avatar_sprout" -> "برعم أخضر"
            "avatar_guardian" -> "حارس الغابة"
            "avatar_phoenix" -> "العنقاء الذهبية"
            "avatar_zen" -> "معلم الزين"
            "avatar_champion" -> "بطل البيئة"
            "avatar_cosmic_dragon" -> "تنين كوني"
            "avatar_crystal_lotus" -> "لوتس بلوري"
            else -> titleEn
        }
        else -> titleEn
    }
}

data class ThemeOption(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val requiredDays: Int,
    val primaryColorHex: String,
    val isUnlockedByDefault: Boolean = false
) {
    fun getName(lang: String): String = when(lang) {
        "bn" -> nameBn
        "es" -> when(id) {
            "vibrant" -> "Naturaleza Vibrante"
            "sunset" -> "Atardecer Dorado"
            "midnight" -> "Santuario de Medianoche"
            "sakura" -> "Flor de Cerezo Rosa"
            "mystic" -> "Púrpura Místico"
            "cyber" -> "Neón Cyberpunk"
            "ocean" -> "Brisa Marina"
            else -> nameEn
        }
        "hi" -> when(id) {
            "vibrant" -> "जीवंत प्राकृतिक"
            "sunset" -> "सुनहरा सूर्यास्त"
            "midnight" -> "मिडनाइट अभयारण्य"
            "sakura" -> "गुलाबी साकुरा"
            "mystic" -> "रहस्यमयी बैंगनी"
            "cyber" -> "साइबरपंक नियॉन"
            "ocean" -> "समुद्री हवा"
            else -> nameEn
        }
        "ar" -> when(id) {
            "vibrant" -> "طبيعة نابضة"
            "sunset" -> "غروب ذهبي"
            "midnight" -> "ملاذ منتصف الليل"
            "sakura" -> "زهرة الكرز الوردية"
            "mystic" -> "أرجواني غامض"
            "cyber" -> "سايبربانك نيون"
            "ocean" -> "نسيم المحيط"
            else -> nameEn
        }
        else -> nameEn
    }
}

data class PlantSkinOption(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val requiredDays: Int,
    val previewImageResId: Int,
    val isUnlockedByDefault: Boolean = false
) {
    fun getName(lang: String): String = when(lang) {
        "bn" -> nameBn
        "es" -> when(id) {
            "skin_natural" -> "Roble Natural"
            "skin_sakura" -> "Cerezo Sakura"
            "skin_golden" -> "Bonsái Dorado"
            "skin_autumn" -> "Membrillo de Otoño"
            "skin_mystic" -> "Árbol de Cristal Místico"
            "skin_cyber" -> "Árbol Neón Cyberpunk"
            "skin_cosmic" -> "Árbol Estelar Cósmico"
            else -> nameEn
        }
        "hi" -> when(id) {
            "skin_natural" -> "प्राकृतिक शाहबलूत वृक्ष"
            "skin_sakura" -> "गुलाबी साकुरा वृक्ष"
            "skin_golden" -> "सुनहरा बोन्साई"
            "skin_autumn" -> "शरद ऋतु का मेपल"
            "skin_mystic" -> "क्रिस्टल एमेथिस्ट वृक्ष"
            "skin_cyber" -> "साइबर नियॉन वृक्ष"
            "skin_cosmic" -> "कॉस्मिक स्टारलाइट वृक्ष"
            else -> nameEn
        }
        "ar" -> when(id) {
            "skin_natural" -> "شجرة البلوط الطبيعية"
            "skin_sakura" -> "شجرة الساكورا الوردية"
            "skin_golden" -> "بونساي ذهبي"
            "skin_autumn" -> "قيقب الخريف الذهبي"
            "skin_mystic" -> "شجرة الكريستال الأرجوانية"
            "skin_cyber" -> "شجرة النيون السايبربانك"
            "skin_cosmic" -> "شجرة النجوم الكونية"
            else -> nameEn
        }
        else -> nameEn
    }

    fun getDescription(lang: String): String = when(lang) {
        "bn" -> descriptionBn
        "es" -> when(id) {
            "skin_natural" -> "Roble verde natural realista"
            "skin_sakura" -> "Hermoso árbol romántico con flores de cerezo rosa"
            "skin_golden" -> "Exclusivo bonsái imperial con hojas doradas brillantes"
            "skin_autumn" -> "Suave árbol con hojas doradas de otoño cayendo"
            "skin_mystic" -> "Mágico árbol de amatista con hojas de cristal brillante"
            "skin_cyber" -> "Futurista árbol con venas luminosas cian y magenta"
            "skin_cosmic" -> "Árbol celestial brillando con estrellas galácticas"
            else -> descriptionEn
        }
        "hi" -> when(id) {
            "skin_natural" -> "प्राकृतिक हरा और यथार्थवादी पौधा"
            "skin_sakura" -> "सुंदर गुलाबी साकुरा फूलों का पौधा"
            "skin_golden" -> "चमकदार सुनहरी पत्तियों वाला शाही पौधा"
            "skin_autumn" -> "शरद ऋतु की सुनहरी पत्तियों वाला पौधा"
            "skin_mystic" -> "चमकदार बैंगनी क्रिस्टल पत्तियों वाला जादुई पौधा"
            "skin_cyber" -> "भविष्य की नीयन रोशनी वाला पौधा"
            "skin_cosmic" -> "आकाशीय तारों से जगमगाता पौधा"
            else -> descriptionEn
        }
        "ar" -> when(id) {
            "skin_natural" -> "شجرة بلوط خضراء طبيعية وواقعية"
            "skin_sakura" -> "شجرة رومانسي بأزهار الكرز الوردية الجميلة"
            "skin_golden" -> "بونساي ملكي فريد بأوراق ذهبية متألقة"
            "skin_autumn" -> "شجرة خريفية هادئة بأوراق ذهبية تسقط"
            "skin_mystic" -> "شجرة جمشت سحرية بأوراق كريستالية متلألئة"
            "skin_cyber" -> "شجرة مستقبلية بأوردة نيون متألقة"
            "skin_cosmic" -> "شجرة سماوية تتألق بنجوم المجرة"
            else -> descriptionEn
        }
        else -> descriptionEn
    }
}

val PRESET_AVATARS = listOf(
    AvatarOption(
        id = "avatar_sprout",
        titleEn = "Green Sprout",
        titleBn = "সবুজ অঙ্কুর",
        requiredDays = 0,
        imageResId = R.drawable.realistic_sprout_stage_1786050325119,
        isUnlockedByDefault = true
    ),
    AvatarOption(
        id = "avatar_guardian",
        titleEn = "Forest Guardian",
        titleBn = "বন রক্ষক",
        requiredDays = 3,
        imageResId = R.drawable.avatar_forest_guardian_1786050398261
    ),
    AvatarOption(
        id = "avatar_phoenix",
        titleEn = "Golden Phoenix",
        titleBn = "স্বর্ণালী ফিনিক্স",
        requiredDays = 7,
        imageResId = R.drawable.avatar_golden_phoenix_1786050410580
    ),
    AvatarOption(
        id = "avatar_zen",
        titleEn = "Zen Master",
        titleBn = "জৈন মাস্টার",
        requiredDays = 15,
        imageResId = R.drawable.avatar_zen_master_1786050422897
    ),
    AvatarOption(
        id = "avatar_champion",
        titleEn = "Eco Champion",
        titleBn = "ইকো চ্যাম্পিয়ন",
        requiredDays = 30,
        imageResId = R.drawable.avatar_eco_champion_1786050434889
    ),
    AvatarOption(
        id = "avatar_cosmic_dragon",
        titleEn = "Cosmic Dragon",
        titleBn = "কসমিক ড্রাগন",
        requiredDays = 20,
        imageResId = R.drawable.avatar_cosmic_dragon_1786051433196
    ),
    AvatarOption(
        id = "avatar_crystal_lotus",
        titleEn = "Crystal Lotus",
        titleBn = "ক্রিস্টাল পদ্ম",
        requiredDays = 25,
        imageResId = R.drawable.avatar_crystal_lotus_1786051443014
    )
)

val PRESET_THEMES = listOf(
    ThemeOption(
        id = "vibrant",
        nameEn = "Vibrant Natural",
        nameBn = "ভাইব্রেন্ট ন্যাচারাল (Vibrant Natural)",
        requiredDays = 0,
        primaryColorHex = "#386B01",
        isUnlockedByDefault = true
    ),
    ThemeOption(
        id = "sunset",
        nameEn = "Golden Sunset",
        nameBn = "সূর্যাস্ত স্বর্ণালী (Golden Sunset)",
        requiredDays = 5,
        primaryColorHex = "#D97706"
    ),
    ThemeOption(
        id = "midnight",
        nameEn = "Midnight Sanctuary",
        nameBn = "মিডনাইট স্যাংকচুয়ারি (Midnight Sanctuary)",
        requiredDays = 10,
        primaryColorHex = "#10B981"
    ),
    ThemeOption(
        id = "sakura",
        nameEn = "Sakura Pink",
        nameBn = "গোলাপী সাকুরা ব্লসম (Sakura Pink)",
        requiredDays = 7,
        primaryColorHex = "#E11D48"
    ),
    ThemeOption(
        id = "mystic",
        nameEn = "Mystic Purple",
        nameBn = "রহস্যময় বেগুনি ক্রিস্টাল (Mystic Purple)",
        requiredDays = 12,
        primaryColorHex = "#A855F7"
    ),
    ThemeOption(
        id = "cyber",
        nameEn = "Cyberpunk Neon",
        nameBn = "সাইবারপাঙ্ক নেয়ন নাইট (Cyberpunk Neon)",
        requiredDays = 15,
        primaryColorHex = "#06B6D4"
    ),
    ThemeOption(
        id = "ocean",
        nameEn = "Ocean Breeze",
        nameBn = "শান্ত মহাসাগর সায়ান (Ocean Breeze)",
        requiredDays = 20,
        primaryColorHex = "#0284C7"
    )
)

val PRESET_PLANT_SKINS = listOf(
    PlantSkinOption(
        id = "skin_natural",
        nameEn = "Natural Oak Tree",
        nameBn = "প্রাকৃতিক বনবৃক্ষ (Natural Oak Tree)",
        descriptionEn = "Realistically vibrant green seedling and mature oak tree",
        descriptionBn = "প্রাকৃতিক সবুজ ও সতেজ বাস্তবসম্মত চারাবৃক্ষ",
        requiredDays = 0,
        previewImageResId = R.drawable.realistic_tree_stage_1786050352181,
        isUnlockedByDefault = true
    ),
    PlantSkinOption(
        id = "skin_sakura",
        nameEn = "Sakura Cherry Blossom",
        nameBn = "গোলাপী সাকুরা (Sakura Cherry Blossom)",
        descriptionEn = "Beautiful romantic pink cherry blossom flower tree",
        descriptionBn = "মনোরম গোলাপী সাকুরা ফুলের রোমান্টিক থিমযুক্ত গাছ",
        requiredDays = 7,
        previewImageResId = R.drawable.realistic_sakura_tree_1786050918135
    ),
    PlantSkinOption(
        id = "skin_golden",
        nameEn = "Golden Bonsai Tree",
        nameBn = "স্বর্ণালী বোন্সায় (Golden Bonsai Tree)",
        descriptionEn = "Royal golden bonsai tree with glowing amber leaves",
        descriptionBn = "অনন্য উজ্জ্বল সোনালী পাতার রাজকীয় বৃক্ষ থিম",
        requiredDays = 14,
        previewImageResId = R.drawable.realistic_golden_bonsai_1786050929318
    ),
    PlantSkinOption(
        id = "skin_autumn",
        nameEn = "Autumn Golden Maple",
        nameBn = "স্বর্ণালী শরৎ ম্যাপল (Autumn Golden Maple)",
        descriptionEn = "Warm serene autumn maple tree with golden falling leaves",
        descriptionBn = "শরতের সোনালী ঝরে পড়া পাতার স্নিগ্ধ প্রাকৃতিক গাছ",
        requiredDays = 10,
        previewImageResId = R.drawable.realistic_autumn_maple_1786051408113
    ),
    PlantSkinOption(
        id = "skin_mystic",
        nameEn = "Mystic Crystal Tree",
        nameBn = "ক্রিস্টাল আমেথিস্ট বৃক্ষ (Mystic Crystal Tree)",
        descriptionEn = "Enchanted amethyst tree with glowing purple crystal leaves",
        descriptionBn = "জ্যোতির্ময় বেগুনি ক্রিস্টাল ও স্ফটিক পাতার জাদুকরী গাছ",
        requiredDays = 18,
        previewImageResId = R.drawable.realistic_mystic_crystal_tree_1786051397513
    ),
    PlantSkinOption(
        id = "skin_cyber",
        nameEn = "Cyberpunk Neon Tree",
        nameBn = "সাইবার নেয়ন বৃক্ষ (Cyberpunk Neon Tree)",
        descriptionEn = "Futuristic glowing cyan and magenta neon light tree",
        descriptionBn = "ভবিষ্যৎপ্রজন্মের উজ্জ্বল সায়ান ও গোলাপী নেয়ন বৃক্ষ",
        requiredDays = 25,
        previewImageResId = R.drawable.realistic_cyberpunk_neon_tree_1786051417084
    ),
    PlantSkinOption(
        id = "skin_cosmic",
        nameEn = "Cosmic Starlight Tree",
        nameBn = "কসমিক স্টারলাইট বৃক্ষ (Cosmic Starlight Tree)",
        descriptionEn = "Celestial galaxy tree illuminated by sparkling starlight",
        descriptionBn = "মহাজাগতিক নক্ষত্রের ছোঁয়ায় তৈরি নীল তারকারাজির বৃক্ষ",
        requiredDays = 30,
        previewImageResId = R.drawable.realistic_cosmic_starlight_tree_1786051424991
    )
)

data class UserProfile(
    val userName: String = "Green Voyager",
    val userBio: String = "Committed to breaking bad habits & growing a healthier life.",
    val selectedAvatarId: String = "avatar_sprout",
    val selectedThemeId: String = "vibrant",
    val selectedPlantSkinId: String = "skin_natural",
    val language: String = "en", // Default language is English ("en")
    val unlockedItemIds: Set<String> = setOf("avatar_sprout", "vibrant", "skin_natural")
) {
    fun isItemUnlocked(itemId: String, requiredDays: Int, currentStreakDays: Double): Boolean {
        return unlockedItemIds.contains(itemId) || currentStreakDays >= requiredDays
    }
}
