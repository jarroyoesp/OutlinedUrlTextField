package com.jarroyo.outlinedurltextfield.ui.component

import android.util.Patterns
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString


fun String.buildAnnotatedStringWithUrlHighlighting(
    style: SpanStyle,
    onUrlClick: (url: String) -> Unit = {},
): AnnotatedString = buildAnnotatedString {
    val text = this@buildAnnotatedStringWithUrlHighlighting
    append(text)
    text.split("\\s+".toRegex())
        .filter { Patterns.WEB_URL.matcher(it).matches() }
        .forEach { url ->
            val startIndex = text.indexOf(url)
            val endIndex = startIndex + url.length
            addStyle(
                style = style,
                start = startIndex,
                end = endIndex,
            )
            addLink(
                clickable = LinkAnnotation.Clickable(
                    tag = url,
                    styles = TextLinkStyles(style),
                    linkInteractionListener = { onUrlClick(url) },
                ),
                start = startIndex,
                end = endIndex,
            )
        }
}
