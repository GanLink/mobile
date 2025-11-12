package com.ganlink.pe.features.farmmanagement.presentation.models

enum class MainActivity(val code: Int, val label: String) {
    CARNE(0, "Carne"),
    LECHE(1, "Leche"),
    GENERICA(2, "Generica");

    companion object {
        fun fromCode(code: Int): MainActivity? = values().firstOrNull { it.code == code }
    }
}
