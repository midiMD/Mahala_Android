package com.neighborly.neighborlyandroid.ui.inventory.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neighborly.neighborlyandroid.domain.model.InventoryItem
import com.neighborly.neighborlyandroid.domain.model.InventoryItemDetail
import com.neighborly.neighborlyandroid.ui.common.image.BrokenItemImage
import com.neighborly.neighborlyandroid.ui.common.image.DetailScreenMainImage
import com.neighborly.neighborlyandroid.ui.common.image.ItemImage
import com.neighborly.neighborlyandroid.ui.inventory.components.InventoryItemDetailFooter
import com.neighborly.neighborlyandroid.ui.inventory.components.MoreOptionsButtonWithPopup
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun InventoryItemDetailScreen(modifier: Modifier = Modifier,
                              item: InventoryItem,
                              detail: InventoryItemDetail?) {

    Column (modifier = modifier.fillMaxSize().padding(1.dp),
        verticalArrangement = Arrangement.SpaceBetween){
        DetailScreenMainImage(thumbnailUrl = item.thumbnailUrl, modifier = Modifier.align(Alignment.CenterHorizontally))
        InventoryItemDetailFooter(item,detail = detail,modifier = Modifier.height(200.dp))

        if (detail != null) {
            detail.dateAdded?.let {
                DateAdded(modifier = Modifier.align(Alignment.End),
                    it
                )
            }
        }
        MoreOptionsButtonWithPopup(modifier = Modifier.align(Alignment.End).fillMaxHeight(),onRemoveClick = {}, onEditClick = {})
    }
}

@Composable
fun DateAdded(modifier:Modifier = Modifier,dateTime:ZonedDateTime){
    val localTime: LocalDateTime = dateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    Box(modifier = modifier){
        Text(
           text = "Date Added: ${localTime.toLocalDate()}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewInventoryDetailScreen() {
    // Sample InventoryItem data
    val sampleItem = InventoryItem(
        id = 1L,
        title = "Mountain Bike",
        dayCharge = 15.99,
        category = null,
        thumbnailUrl = null
    )

    // Sample InventoryItemDetail data
    val sampleDetail = InventoryItemDetail(
        description = "A rugged mountain bike perfect for off-road trails.",
        dateAdded = ZonedDateTime.now()
    )

    // Call the composable with sample data
    InventoryItemDetailScreen(
        item = sampleItem,
        detail = sampleDetail,
        modifier = Modifier
            .padding(0.dp) // Add some padding to the preview
    )
}

