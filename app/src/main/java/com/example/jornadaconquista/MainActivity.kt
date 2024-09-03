package com.example.jornadaconquista

//Este é um projeto desenvolvido com função vistas tanto em aula quanto
//tambem com auxilio do material de outras aulas.
//Certifique-se que as imagens estão importadas correamente!

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jornadaconquista.ui.theme.JornadaConquistaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JornadaConquista()
        }
    }
}

@Composable
fun JornadaConquista() {
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
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "A Jornada da Conquista",
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = imagens),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(300.dp).clickable {
                    contadorClicks++
                    updateImage(contadorClicks)
                }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = { dialogoFinal = true },modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Desistir", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Clique na imagem para avançar na jornada",
            color = Color.Black,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(300.dp))
    }

    if (mensagemParabens) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(text = "Parabéns!") },
            text = { Text("Você alcançou a conquista!", fontSize = 20.sp) },
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
                        Text("Jogar Novamente", fontSize = 20.sp)
                    }
                }
            },
            dismissButton = {}
        )
    }

    if (dialogoFinal) {
        AlertDialog(
            onDismissRequest = { dialogoFinal = false },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Desistiu?", fontSize = 25.sp)
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.desistiu),
                        contentDescription = "Imagem de Desistência",
                        modifier = Modifier
                            .size(300.dp)
                            .padding(bottom = 30.dp)
                    )
                    Text("Deseja tentar novamente?", fontSize = 20.sp)
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
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
                        Text("Sim", fontSize = 20.sp)
                    }

                    Button(
                        onClick = { dialogoFinal = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Não", fontSize = 20.sp)
                    }
                }
            }
        )
    }
}

