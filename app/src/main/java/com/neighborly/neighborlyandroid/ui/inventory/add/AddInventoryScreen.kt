package com.neighborly.neighborlyandroid.ui.inventory.add

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.ui.LocalSnackbarHostState

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neighborly.neighborlyandroid.domain.model.AddItemDetails
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.ui.common.CategoriesDropdown
import com.neighborly.neighborlyandroid.ui.common.LoadingOverlay
import com.neighborly.neighborlyandroid.ui.common.SuccessOverlay
import com.neighborly.neighborlyandroid.ui.inventory.components.ImageUploadComponent
import com.neighborly.neighborlyandroid.ui.market.CategoryOption
import com.neighborly.neighborlyandroid.ui.inventory.components.DiscardDialog
import com.neighborly.neighborlyandroid.ui.inventory.components.SubmitDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddInventoryScreen(
    modifier: Modifier = Modifier,
    viewModel: AddInventoryViewModel,
    navigateToInventoryHome:()->Unit

) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    AddInventoryScreen(modifier = modifier,
        uiState = uiState,
        toggleNavigation = viewModel::toggleNavigation,
        snackbarHostState =LocalSnackbarHostState.current,
        navigateToInventoryHome = navigateToInventoryHome,
        submitItem = {item:AddItemDetails ->
            viewModel.submit(context,item)})
}
@Composable
private fun AddInventoryScreen(modifier: Modifier = Modifier,
                               uiState: AddInventoryScreenState,
                               toggleNavigation:()->Unit,
                               navigateToInventoryHome: () -> Unit,
                               submitItem:(item:AddItemDetails)->Unit,
                               snackbarHostState: SnackbarHostState){

    var categoryState: List<CategoryOption> by remember { mutableStateOf(Category.entries.map { category ->
        CategoryOption(category=category, isActive = false)
    }) }
    fun toggleCategory(category: Category, value:Boolean){
        categoryState = categoryState.map { categoryOption ->
            if (categoryOption.category == category) {
                categoryOption.copy(isActive = value) // Modify only the matching item
            } else {
                categoryOption // Keep other items unchanged
            }
        }
    }
    var showDiscardDialog by remember { mutableStateOf(false) }
    var showSubmitDialog by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var thumbnailUri by remember { mutableStateOf<Uri?>(null) }

    val scrollState = rememberScrollState()


    Scaffold(
        topBar = {
            AddInventoryTopBar(
                showDiscardDialog = { showDiscardDialog = true },
                showSubmitDialog = { showSubmitDialog = true },
                isSubmitEnabled =title.isNotBlank() && categoryState.any{category-> category.isActive} && price.isNotBlank() && thumbnailUri != null,
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {

            // Image Upload
            ImageUploadComponent(thumbnailUri, onPick = {thumbnailUri = it})

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Price
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(0.3f),
                    prefix = { Text("£") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                CategoriesDropdown(
                    options = categoryState, onOptionToggled = {category, value ->toggleCategory(category,value)}, modifier = Modifier
                        .fillMaxWidth()  // Half screen width
                )
            }
            if (showDiscardDialog){
                DiscardDialog(
                    hideDialog = {showDiscardDialog = false},
                    onClick = {navigateToInventoryHome()}
                )
            }
            if (showSubmitDialog){
                SubmitDialog(
                    hideDialog = {showSubmitDialog = false},
                    onClick = {submitItem(AddItemDetails(
                        title = title,
                        description = description,
                        thumbnailUri = thumbnailUri!!,
                        categories = categoryState.filter{it.isActive}.map{it.category}, // list of all active categories
                        dayCharge = price.toDouble()))}
                )
            }
        }
        when(uiState){
            is AddInventoryScreenState.Error -> {
                LaunchedEffect(uiState) {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            AddInventoryScreenState.Idle -> {}
            AddInventoryScreenState.Loading -> {
                LoadingOverlay()
            }
            AddInventoryScreenState.Success -> {
                SuccessOverlay()
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(500)
                    toggleNavigation() // callback from viewmodel to change uiState to NavigateToInventoryHome
                }

            }
            AddInventoryScreenState.NavigateToInventoryHome -> {
                navigateToInventoryHome()
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun PreviewAddInventoryScreen(){
    AddInventoryScreen(
        uiState = AddInventoryScreenState.Loading,
        toggleNavigation = {},
        snackbarHostState = SnackbarHostState(),
        navigateToInventoryHome = {},
        submitItem = { TODO() })
}




@Preview(showBackground = true, name = "Enabled Submit Button")
@Composable
private fun TopAppBarContentPreviewEnabled() {
    MaterialTheme {
        AddInventoryTopBar(
            showDiscardDialog = { /* Preview doesn't need actual implementation */ },
            showSubmitDialog = { /* Preview doesn't need actual implementation */ },
            isSubmitEnabled = true
        )
    }
}
