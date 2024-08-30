package com.example.jornadaconquista

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jornadaconquista.ui.theme.JornadaConquistaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JornadaConquistaTheme()
        }
    }
}

@Composable
fun JornadaConquistaTheme() {
    var contadorClicks by remember { mutableStateOf(0) }
    var imagens by remember { mutableStateOf(R.drawable.comecou) }
    var totalClicks by remember { mutableStateOf((1..50).random()) }
    var mensagemParabens by remember { mutableStateOf(false) }
    var dialogoFinal by remember { mutableStateOf(false) }

    fun updateImage(clicks: Int) {
        when {
            clicks <= totalClicks * 0.33 -> imagens = R.drawable.comecou
            clicks <= totalClicks * 0.66 -> imagens = R.drawable.caminhando
            clicks < totalClicks -> imagens = R.drawable.chegando
            else -> {
                imagens = R.drawable.parabens
                mensagemParabens = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imagens),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clickable {
                    contadorClicks++
                    updateImage(contadorClicks)
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { dialogoFinal = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Desistir")
        }
    }

    if (mensagemParabens) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(text = "Parabéns!") },
            text = { Text("Você alcançou a conquista!") },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            contadorClicks = 0
                            totalClicks = (1..50).random()
                            imagens = R.drawable.comecou
                            mensagemParabens = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Jogar Novamente")
                    }
                }
            },
            dismissButton = {}
        )
    }

    if (dialogoFinal) {
        AlertDialog(
            onDismissRequest = { dialogoFinal = false },
            title = { Text(text = "Desistiu?") },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.desistiu),
                        contentDescription = "Imagem de Desistência",
                        modifier = Modifier
                            .size(300.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text("Deseja tentar novamente?")
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            contadorClicks = 0
                            totalClicks = (1..50).random()
                            imagens = R.drawable.comecou
                            dialogoFinal = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Sim")
                    }
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { dialogoFinal = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Não")
                    }
                }
            }
        )
    }
}
