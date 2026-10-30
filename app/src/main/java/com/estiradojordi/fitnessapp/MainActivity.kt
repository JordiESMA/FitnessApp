    package com.estiradojordi.fitnessapp

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.tooling.preview.Preview
    import com.estiradojordi.fitnessapp.ui.screen.EditProfileScreen
    import com.estiradojordi.fitnessapp.ui.screen.ProfileScreen
    import com.estiradojordi.fitnessapp.ui.theme.FitnessAppTheme

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContent {
                FitnessAppTheme {
                    FitnessApp()
                }
            }
        }
    }

    @Composable
    fun FitnessApp() {
        var nom: String by remember { mutableStateOf("Jordi Estirado") }
        var gmail: String by remember { mutableStateOf("jordi.estirado@insbaixcamp.cat") }
        var dataNaixement: String by remember { mutableStateOf("03/01/2006") }
        var genere: String by remember { mutableStateOf("Masculí") }
        var alcada: Int by remember { mutableStateOf(180) }
        var pes: Double by remember { mutableStateOf(70.0) }
        var objectiu: String by remember { mutableStateOf("Augmentar pes") }
        var nivellActivitat: String by remember { mutableStateOf("Mitjà") }
        var pesObjectiu: Double by remember { mutableStateOf(70.0) }
        var editant: Boolean by remember { mutableStateOf(true) }

        fun guardar( nomNou: String, gmailNou: String, dataNaixementNova: String, genereNou: String, alcadaNova: Int, pesNou: Double, pesObjectiuNou: Double, objectiuNou: String, nivellActivitatNou: String
        ) {
            nom = nomNou
            gmail = gmailNou
            dataNaixement = dataNaixementNova
            genere = genereNou
            alcada = alcadaNova
            pes = pesNou
            pesObjectiu = pesObjectiuNou
            objectiu = objectiuNou
            nivellActivitat = nivellActivitatNou
            editant = false
        }

        if(editant){
            EditProfileScreen(nom, gmail, dataNaixement, genere, alcada,
                pes, pesObjectiu,objectiu, nivellActivitat,{nomN, emailN, dataNaixementN, genereN, alçadaN,pesN, pesObjectiuN, objectiuN, nivellActivitatN ->
                    nom = nomN
                    gmail = emailN
                    dataNaixement = dataNaixementN
                    genere = genereN
                    alcada = alçadaN
                    pes = pesN
                    pesObjectiu = pesObjectiuN
                    objectiu = objectiuN
                    nivellActivitat = nivellActivitatN
                    editant = false
                })
        }else{
            ProfileScreen(
                nom = nom,
                gmail = gmail,
                dataNaixement = dataNaixement,
                genere = genere,
                alcada = alcada,
                pes = pes,
                objectiu = objectiu,
                nivellActivitat = nivellActivitat,
                editar = { editant = true }
            )
        }

        /**
         * fun editar(){}editant=true
         */
    }

    @Preview(showBackground = true)
    @Composable
    fun FitnessPreview() {
        FitnessAppTheme {
            FitnessApp()

        }
    }

