package com.neighborly.neighborlyandroid.ui.inventory.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neighborly.neighborlyandroid.domain.model.AddItemDetails
import com.neighborly.neighborlyandroid.domain.model.Category
import com.neighborly.neighborlyandroid.ui.common.CategoriesDropdown
import com.neighborly.neighborlyandroid.ui.common.image.ListViewImage
import com.neighborly.neighborlyandroid.ui.market.CategoryOption
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.time.delay

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
            TopAppBarContent(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarContent(
    showDiscardDialog: () -> Unit,
    showSubmitDialog: () -> Unit,
    isSubmitEnabled: Boolean
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Add",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        navigationIcon = {
            TextButton(
                onClick = showDiscardDialog,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Discard")
            }
        },
        actions = {
            Button(
                onClick = showSubmitDialog,
                enabled = isSubmitEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Submit")
            }
        }
    )
}
@Composable
private fun DiscardDialog(hideDialog:()->Unit,
                          onClick:()->Unit){
    AlertDialog(
        onDismissRequest = hideDialog,
        title = { Text("Discard Changes?") },
        text = { Text("All unsaved changes will be lost.") },
        confirmButton = {
            TextButton(
                onClick = {
                    hideDialog()
                    onClick()
                }
            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(
                onClick = hideDialog
            ) {
                Text("Cancel")
            }
        }
    )
}
@Composable
private fun SubmitDialog(hideDialog:()->Unit,
                          onClick:()->Unit){
    AlertDialog(
        onDismissRequest = hideDialog,
        title = { Text("Submit?") },
        text = { },
        confirmButton = {
            TextButton(
                onClick = {
                    hideDialog()
                    onClick()
                }
            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = hideDialog
            ) {
                Text("Cancel")
            }
        }
    )
}
@Composable
fun ImageUploadComponent(thumbnailUri:Uri?,onPick:(uri:Uri)->Unit ){
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri->
            if (uri != null) {
                onPick(uri)
            }
        }
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        if (thumbnailUri == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add photo"
                )
                Text("Add Photo")
            }
        } else {
            ListViewImage(thumbnailUri =thumbnailUri.toString() )
        }
    }
}
@Composable
fun LoadingOverlay() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
            .clickable(enabled = false, onClick = {}), // Block clicks while loading
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.3f),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 6.dp
            )
        }
    }
}
@Composable
fun SuccessOverlay() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
            .clickable(enabled = false, onClick = {}), // Block clicks while loading
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandIn(),
            exit = fadeOut() + shrinkOut()
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Success",
                modifier = Modifier.fillMaxSize(0.3f),
                tint = Color.Green,
            )
        }
    }
}


@Preview(showBackground = true, name = "Enabled Submit Button")
@Composable
private fun TopAppBarContentPreviewEnabled() {
    MaterialTheme {
        TopAppBarContent(
            showDiscardDialog = { /* Preview doesn't need actual implementation */ },
            showSubmitDialog = { /* Preview doesn't need actual implementation */ },
            isSubmitEnabled = true
        )
    }
}
