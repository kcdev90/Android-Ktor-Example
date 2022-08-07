package com.kcthomas.photoviewer.ui.photolist.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kcthomas.core.R
import com.kcthomas.core.compose.getRandomColor
import com.kcthomas.core.compose.theme.PhotoViewerTheme
import com.kcthomas.domain.Photo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PhotoCard(
    photo: Photo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(Modifier.padding(12.dp)) {
                val color = remember { getRandomColor() }
                AsyncImage(
                    model = photo.download_url,
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    error = painterResource(R.drawable.ic_error),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.photo),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, color, CircleShape)
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 3.dp),
                    text = photo.author,
                    fontSize = 15.sp,
                    style = MaterialTheme.typography.button
                )
            }
        }

        Divider()
    }
}

@Preview
@Composable
fun PreviewPhotoCard() {
    PhotoViewerTheme {
        PhotoCard(
            Photo(
                id = "",
                author = "KC Thomas",
                width = 100,
                height = 100,
                url = "https://unsplash.com/photos/yC-Yzbqy7PY",
                download_url = ""
            )
        ) {}
    }
}
