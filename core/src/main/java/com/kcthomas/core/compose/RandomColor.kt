package com.kcthomas.core.compose

import androidx.compose.ui.graphics.Color
import java.util.Random

fun getRandomColor() = when(Random().nextInt(12)) {
     0 -> Color(0xFFFF0000)
     1 -> Color(0xFFFF8000)
     2 -> Color(0xFFFFFF00)
     3 -> Color(0xFF80FF00)
     4 -> Color(0xFF00FF00)
     5 -> Color(0xFF00FF80)
     6 -> Color(0xFF00FFFF)
     7 -> Color(0xFF0080FF)
     8 -> Color(0xFF0000FF)
     9 -> Color(0xFF8000FF)
     10 -> Color(0xFFFF00FF)
     11 -> Color(0xFFFF0080)
     else -> Color.Gray
}