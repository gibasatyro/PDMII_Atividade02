package com.fatec.atividade02.ui.model

data class Aluno(
    val nome: String,
    val notas: MutableList<Double> = mutableListOf()
) {
    fun calcularMedia(): Double {
        if (notas.isEmpty()) return 0.0
        return notas.sum() / notas.size
    }

    fun desempenho(media: Double): String {
        return when {
            media > 9.0 -> "Ótimo Aproveitamento"
            media >= 6.0 -> "Aprovado"
            else -> "Reprovado"
        }
    }
}

