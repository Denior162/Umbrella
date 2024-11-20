//package com.example.parasol.ui.skinType
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.RadioButtonColors
//import androidx.compose.material3.RadioButtonDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import com.example.parasol.ui.theme.LocalSkinTypeExtendedColorScheme
//
//@Composable
//fun SkinTypeSelectionScreen() {
//    Scaffold(topBar = {
//        TODO()
//    }) { innerPadding ->
//        ListOfSkinTypes(
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
//}
//
//data class SkinType(val name: String, val description: String)
//
//@Composable
//fun ListOfSkinTypes(
//    modifier: Modifier = Modifier
//) {
//    LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
//        items(skinTypes.size) { index ->
//            SkinTypeElement(
//                selectedSkinType = selectedSkinTypeIndex == index,
//                skinTypeName = skinTypes[index].name,
//                skinTypeDescription = skinTypes[index].description,
//                selectSkinType = { onSkinTypeSelected(index) },
//                onSkinTypeColorContainer = RadioButtonDefaults.colors()
//            )
//        }
//    }
//}
//
//@Composable
//fun SkinTypeElement(
//    selectedSkinType: Boolean,
//    skinTypeName: String,
//    skinTypeDescription: String,
//    selectSkinType: (() -> Unit)?,
//    onSkinTypeColorContainer: RadioButtonColors
//) {
//    Card(modifier = Modifier.padding(vertical = 4.dp)) {
//        Row(
//            modifier = Modifier.padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            RadioButton(
//                selected = selectedSkinType,
//                onClick = selectSkinType,
//                colors = onSkinTypeColorContainer
//            )
//            Column(modifier = Modifier.padding(start = 8.dp)) {
//                Text(text = skinTypeName)
//                Text(text = skinTypeDescription)
//            }
//        }
//    }
//}
//
//@Preview
//@Composable
//fun SkinTypeCardPreview() {
//    val skinTypeColorScheme = LocalSkinTypeExtendedColorScheme.current
//
//    // Создаем RadioButtonColors с использованием вашего цвета
//    val radioButtonOncontainerColor = RadioButtonDefaults.colors(
//        selectedColor = skinTypeColorScheme.skinType5.onColorContainer,
//        unselectedColor = De
//    )
//
//    SkinTypeElement(
//        selectedSkinType = false,
//        skinTypeName = "Тип V",
//        skinTypeDescription = "Коричневая или темно-коричневая кожа с темными волосами и глазами.",
//        selectSkinType = {},
//        onSkinTypeColorContainer = radioButtonOncontainerColor
//    )
//}