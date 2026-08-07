package com.example.ui.model

import com.example.R

sealed class PlantStage(
    val stageLevel: Int,
    val nameBn: String,
    val nameEn: String,
    val minDaysRequired: Int,
    val maxDaysTarget: Int, // Target days for this phase before graduating to next
    val descriptionBn: String,
    val descriptionEn: String,
    val quoteBn: String,
    val imageResId: Int
) {
    fun getName(lang: String): String = if (lang.lowercase() == "bn") nameBn else nameEn

    fun getDescription(lang: String): String = if (lang.lowercase() == "bn") descriptionBn else descriptionEn

    object Seed : PlantStage(
        stageLevel = 1,
        nameBn = "বীজ পর্যায় (Seed)",
        nameEn = "Seed Stage",
        minDaysRequired = 0,
        maxDaysTarget = 3,
        descriptionBn = "মাটির নিচে নতুন শুরুর প্রস্তুতি। ধৈর্য্য ধরুন এবং নতুন সংকল্পকে সংসিঞ্চিত করুন।",
        descriptionEn = "Preparing below the soil for a new beginning. Be patient and nurture your resolution.",
        quoteBn = "প্রতিটি বড় গাছের সূচনা একটি ছোট বীজ থেকেই হয়।",
        imageResId = R.drawable.realistic_seed_stage_1786050310695
    )

    object Sprout : PlantStage(
        stageLevel = 2,
        nameBn = "ছোট অঙ্কুর (Small Sprout)",
        nameEn = "Small Sprout Stage",
        minDaysRequired = 3,
        maxDaysTarget = 7,
        descriptionBn = "৩ দিনের আত্মনিয়ন্ত্রণ! মাটির বুক চিরে আলোর মুখ দেখেছে ছোট সবুজ অঙ্কুর।",
        descriptionEn = "3 days of self-control! A small green sprout emerges into the light.",
        quoteBn = "আপনার ইচ্ছাশক্তি এখন একটি নতুন সবুজ কুঁড়ির মতো বিকশিত হচ্ছে।",
        imageResId = R.drawable.realistic_sprout_stage_1786050325119
    )

    object Sapling : PlantStage(
        stageLevel = 3,
        nameBn = "চারা গাছ (Sapling)",
        nameEn = "Strong Sapling Stage",
        minDaysRequired = 7,
        maxDaysTarget = 30,
        descriptionBn = "৭ দিনের সাফল্য! চারা গাছটি শক্ত হচ্ছে এবং পাতা মেলছে। আপনার সংকল্প এখন সুদৃঢ়।",
        descriptionEn = "7 days of success! The sapling grows stronger with opening leaves.",
        quoteBn = "এক সপ্তাহের আত্মসংযম আপনাকে এনে দিয়েছে দারুণ এক মানসিক দৃঢ়তা।",
        imageResId = R.drawable.realistic_sapling_stage_1786050338909
    )

    object BigTree : PlantStage(
        stageLevel = 4,
        nameBn = "বড় গাছ / মহীরুহ (Big Tree)",
        nameEn = "Master Tree Stage",
        minDaysRequired = 30,
        maxDaysTarget = 100,
        descriptionBn = "৩০+ দিনের অভূতপূর্ব বিজয়! আপনার অভ্যাসমুক্ত জীবনের চারাটি আজ বিশাল ও সুদৃঢ় বৃক্ষে পরিণত হয়েছে।",
        descriptionEn = "30+ days milestone victory! Your habit-free life has grown into a majestic tree.",
        quoteBn = "আপনি নিজের জীবনের কাণ্ডারী হতে পেরেছেন। অদম্য সাহসের জন্য অভিনন্দন!",
        imageResId = R.drawable.realistic_tree_stage_1786050352181
    )

    companion object {
        fun getStageForDays(days: Double): PlantStage {
            return when {
                days >= 30.0 -> BigTree
                days >= 7.0 -> Sapling
                days >= 3.0 -> Sprout
                else -> Seed
            }
        }

        fun getNextStage(current: PlantStage): PlantStage? {
            return when (current) {
                Seed -> Sprout
                Sprout -> Sapling
                Sapling -> BigTree
                BigTree -> null
            }
        }

        fun calculateProgress(days: Double): Float {
            val stage = getStageForDays(days)
            val nextStage = getNextStage(stage) ?: return 1.0f

            val currentStageMin = stage.minDaysRequired.toDouble()
            val nextStageMin = nextStage.minDaysRequired.toDouble()

            val progressInStage = (days - currentStageMin) / (nextStageMin - currentStageMin)
            return progressInStage.coerceIn(0.0, 1.0).toFloat()
        }
    }
}
