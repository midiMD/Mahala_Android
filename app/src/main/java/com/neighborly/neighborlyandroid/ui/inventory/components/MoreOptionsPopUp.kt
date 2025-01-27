package com.neighborly.neighborlyandroid.ui.inventory.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun MoreOptionsButtonWithPopup(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var showPopup by remember { mutableStateOf(false) } // State to control popup visibility

    Box(modifier = modifier) {

        // Button to open the popup
        IconButton(onClick = { showPopup = true }) {
            Icon(
                imageVector = Icons.Default.MoreHoriz, // 3-dot menu icon
                contentDescription = "More Options"
            )
        }

        // Popup implementation
        if (showPopup) {
            Popup(
                onDismissRequest = { showPopup = false }
            ) {
                // Popup content
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
                            RoundedCornerShape(8.dp)
                        )
                ) {
                    Column {
                        // Edit Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showPopup = false
                                    onEditClick()
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Edit")
                        }

                        // Divider
                        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))

                        // Remove Button
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showPopup = false
                                    onDeleteClick()
                                }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remove",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Remove")
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewPopUp(){
    MoreOptionsButtonWithPopup(onEditClick = {}, onDeleteClick = {}
    )
}