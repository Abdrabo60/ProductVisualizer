package com.abdrabo60.productvisualizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abdrabo60.productvisualizer.products.domain.Product
import com.abdrabo60.productvisualizer.products.presentation.ProductScreen
import com.abdrabo60.productvisualizer.products.presentation.ProductViewModel
import com.abdrabo60.productvisualizer.products.presentation.ProductsScreenObject
import com.abdrabo60.productvisualizer.theme.ProductVisualizerTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductVisualizerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = HomeScreenObject
                ) {
                    composable<HomeScreenObject> {
                        HomeScreen(navController, this@MainActivity)
                    }
                    composable<ProductsScreenObject> {
                        val viewModel: ProductViewModel by viewModels()
                        ProductScreen(viewModel.state.value,
                            onAddNewProduct = { viewModel.addNewProduct(Product(it.id, it.name)) },
                            onToggleSelection = { viewModel.onToggleSelection(it) },
                            onClearAllSelection = { viewModel.onClearAllSelection() },
                            onDeleteProduct = { viewModel.onDeleteProduct() },
                            onEditProduct = {viewModel.onEditProduct(Product(it.id,it.name))},
                            onBackButton = { navController.navigate(HomeScreenObject) })
                    }
                }

            }
        }
    }
}
//composable<ProductsScreenObject> {
//    val args = it.toRoute<ProductsScreenObject>()
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//    }
//}

@Composable
fun HomeScreen(navController: NavController, mainActivity: MainActivity) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate(ProductsScreenObject)
            }) {
                Text(text = "Products")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Customers")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { mainActivity.finishAndRemoveTask() }) {
                Text(text = "Exit")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Serializable
object HomeScreenObject


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ProductVisualizerTheme {

//        Greeting("Android")
//    }
//}