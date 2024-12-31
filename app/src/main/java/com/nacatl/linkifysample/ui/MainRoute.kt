package com.nacatl.linkifysample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nacatl.linkifysample.ui.linkify.LinkifyText

private val DEFAULT_TEXT = """
    テストテキスト
    https://developer.android.com/
    です。
    """
    .trimIndent()

@Composable
internal fun MainRoute(
    modifier: Modifier = Modifier,
) {
    MainScreen(
        modifier = modifier,
    )
}

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf(DEFAULT_TEXT) }
    var enableLinkify by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
            value = text,
            onValueChange = { text = it },
            supportingText = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = "Enable Linkify",
                    )
                    Checkbox(
                        checked = enableLinkify,
                        onCheckedChange = { enableLinkify = it },
                    )
                }
            }
        )
        Spacer(
            modifier = Modifier.padding(
                vertical = 16.dp,
            ),
        )
        LinkifyText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
            text = text,
            enableLinkify = enableLinkify,
        )
    }
}
