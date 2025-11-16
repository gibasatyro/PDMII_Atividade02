package com.fatec.atividade02.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.remember
import com.fatec.atividade02.ui.model.Aluno

@Composable
fun AlunoScreen() {
    var nome by remember { mutableStateOf("") }
    var nota1 by remember { mutableStateOf("") }
    var nota2 by remember { mutableStateOf("") }
    var nota3 by remember { mutableStateOf("") }

    var mediaCalculada by remember { mutableStateOf<Double?>(null) }
    var resultadoTexto by remember { mutableStateOf("") }
    var situacaoTexto by remember { mutableStateOf("") }
    var corSituacao by remember { mutableStateOf(Color.Black) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Gerenciamento de Notas",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Aluno",
                    style = MaterialTheme.typography.headlineMedium
                )
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do Aluno") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Notas",
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp) // Espaçamento entre os campos
                ) {
                    OutlinedTextField(
                        value = nota1,
                        onValueChange = { nota1 = it },
                        label = { Text("Nota 1") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = nota2,
                        onValueChange = { nota2 = it },
                        label = { Text("Nota 2") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = nota3,
                        onValueChange = { nota3 = it },
                        label = { Text("Nota 3") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val notasDouble = listOf(nota1, nota2, nota3)
                            .mapNotNull { it.replace(',', '.').toDoubleOrNull() }

                        val aluno = Aluno(
                            nome = nome,
                            notas = notasDouble.toMutableList()
                        )

                        val media = aluno.calcularMedia()
                        val situacao = aluno.desempenho(media)

                        mediaCalculada = media
                        resultadoTexto = "Média ${aluno.nome}: %.2f".format(media)
                        situacaoTexto = situacao
                        corSituacao = when(situacao) {
                            "Reprovado" -> Color.Red
                            "Aprovado" -> Color(0xFF4CAF50) // Verde
                            "Ótimo Aproveitamento" -> Color(0xFF00B0FF) // Azul
                            else -> Color.Black
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Calcular Média", style = MaterialTheme.typography.titleLarge)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        mediaCalculada?.let { media ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = resultadoTexto,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = situacaoTexto,
                        style = MaterialTheme.typography.headlineSmall,
                        color = corSituacao
                    )
                }
            }
        }
    }
}