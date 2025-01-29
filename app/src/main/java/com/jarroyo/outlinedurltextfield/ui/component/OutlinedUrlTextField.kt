package com.jarroyo.outlinedurltextfield.ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jarroyo.outlinedurltextfield.ui.theme.OutlinedUrlTextFieldTheme

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ReusedModifierInstance")
@Composable
fun OutlinedUrlTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onUrlClick: (url: String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    val urlStyle = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        textDecoration = TextDecoration.Underline
    )
    val visualTransformation = UrlTransformation(urlStyle, onUrlClick)
    if (enabled && !readOnly) {
        androidx.compose.material3.OutlinedTextField(
            value = value,
            modifier = modifier,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            prefix = prefix,
            suffix = suffix,
            supportingText = supportingText,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors,
        )
    } else {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.padding(top = 8.dp),
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            decorationBox = {
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = {
                        Text(
                            text = value.buildAnnotatedStringWithUrlHighlighting(
                                style = urlStyle,
                                onUrlClick = onUrlClick,
                            ),
                        )
                    },
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    isError = isError,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    colors = colors,
                )
            },
        )
    }
}

private class UrlTransformation(
    private val style: SpanStyle,
    private val onUrlClick: (url: String) -> Unit,
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText = TransformedText(
        text = text.text.buildAnnotatedStringWithUrlHighlighting(style, onUrlClick),
        offsetMapping = OffsetMapping.Identity,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewEnabledCommonTextUrlOutlinedTextField() {
    OutlinedUrlTextFieldTheme {
        OutlinedUrlTextField(
            value = "input",
            onValueChange = {},
            onUrlClick = {},
            label = { Text("Label") },
            enabled = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNonEnabledCommonTextUrlOutlinedTextField() {
    OutlinedUrlTextFieldTheme {
        OutlinedUrlTextField(
            value = "input",
            onValueChange = {},
            onUrlClick = {},
            label = { Text("Label") },
            enabled = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEnabledUrlTextUrlOutlinedTextField() {
    OutlinedUrlTextFieldTheme {
        OutlinedUrlTextField(
            value = "https://www.url.com",
            onValueChange = {},
            onUrlClick = {},
            label = { Text("Label") },
            enabled = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewNonEnabledUrlTextUrlOutlinedTextField() {
    OutlinedUrlTextFieldTheme {
        OutlinedUrlTextField(
            value = "https://www.url.com",
            onValueChange = {},
            onUrlClick = {},
            label = { Text("Label") },
            enabled = false,
        )
    }
}
