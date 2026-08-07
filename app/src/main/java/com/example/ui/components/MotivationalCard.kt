package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberAccent

val ENGLISH_MOTIVATIONAL_QUOTES = listOf(
    "Your effort today is the strength of your new life tomorrow.",
    "Changing a habit is tough, but it is the first step to success.",
    "Falling once is not defeat; refusing to stand up is.",
    "You are the master of your mind, not its servant.",
    "Small consistent steps grow into a majestic oak.",
    "Be patient; time will reward your perseverance."
)

val BENGALI_MOTIVATIONAL_QUOTES = listOf(
    "আপনার আজকের কষ্টই আগামীকালের নতুন জীবনের শক্তি।",
    "অভ্যাস বদলানো কঠিন, কিন্তু এটি সফলতার প্রথম ধাপ।",
    "একবার পড়ে যাওয়া পরাজয় নয়, দাঁড়িয়ে না থাকাটাই পরাজয়।",
    "আপনার মন আপনার দাস নয়, আপনি আপনার মনের মনিব।",
    "ছোট ছোট পদক্ষেপ থেকেই একটি বড় মহীরুহের জন্ম হয়।",
    "ধৈর্য্য ধরুন, সময় আপনার কষ্টের প্রতিদান দেবে।"
)

val SPANISH_MOTIVATIONAL_QUOTES = listOf(
    "Tu esfuerzo de hoy es la fuerza de tu nueva vida mañana.",
    "Cambiar un hábito es difícil, pero es el primer paso al éxito.",
    "Caerse una vez no es la derrota; no levantarse sí lo es.",
    "Tú eres el dueño de tu mente, no su sirviente.",
    "Pequeños pasos constantes hacen crecer un gran árbol.",
    "Ten paciencia; el tiempo recompensará tu perseverancia."
)

@Composable
fun MotivationalCard(
    lang: String = "en",
    modifier: Modifier = Modifier
) {
    val quoteList = when (lang.lowercase()) {
        "bn" -> BENGALI_MOTIVATIONAL_QUOTES
        "es" -> SPANISH_MOTIVATIONAL_QUOTES
        else -> ENGLISH_MOTIVATIONAL_QUOTES
    }
    val quote = remember(lang) { quoteList.random() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("motivational_quote_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FormatQuote,
                contentDescription = "Quote",
                tint = AmberAccent,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = com.example.util.AppStrings.get("daily_thought", lang),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "“$quote”",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
