package com.nacatl.linkifysample.ui.linkify

import android.content.Intent
import android.text.SpannableString
import android.text.style.URLSpan
import android.text.util.Linkify
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.nacatl.linkifysample.ui.theme.BackgroundOpacityStateTokens

object LinkifyTextDefaults {

    val supportSchemes = listOf(
        "http://",
        "https://",
        "rtsp://",
        "ftp://",
    )

    @Composable
    fun baseSpanStyle(
        color: Color = MaterialTheme.colorScheme.primary,
        textDecoration: TextDecoration = TextDecoration.Underline,
    ): SpanStyle = SpanStyle(
        color = color,
        textDecoration = textDecoration,
    )

    @Composable
    fun textLinkStyles(
        color: Color = MaterialTheme.colorScheme.primary,
        textDecoration: TextDecoration = TextDecoration.Underline,
        baseStyle: SpanStyle = baseSpanStyle(
            color = color,
            textDecoration = textDecoration,
        ),
        hoveredStyle: SpanStyle = baseStyle.copy(
            background = color.copy(
                alpha = BackgroundOpacityStateTokens.HOVER_STATE_LAYER_OPACITY,
            ),
        ),
        focusedStyle: SpanStyle = baseStyle.copy(
            background = color.copy(
                alpha = BackgroundOpacityStateTokens.FOCUS_STATE_LAYER_OPACITY,
            ),
        ),
        pressedStyle: SpanStyle = baseStyle.copy(
            background = color.copy(
                alpha = BackgroundOpacityStateTokens.PRESSED_STATE_LAYER_OPACITY,
            ),
        ),
    ) = TextLinkStyles(
        style = baseStyle,
        hoveredStyle = hoveredStyle,
        focusedStyle = focusedStyle,
        pressedStyle = pressedStyle,
    )
}

/**
 * [Linkify.WEB_URLS] に対応するURLをユーザーアクション化して [AnnotatedString] に追加表示する[Text]
 *
 * @param text 元のString
 * @param enableLinkify URLをリンク化するかどうか。デフォルトではスキームを判定して[remember]する
 * @param linkInteractionListener リンクタップ時のコールバック。デフォルトでは[Intent.ACTION_VIEW]相当でURLを開く
 */
@Composable
fun LinkifyText(
    text: String,
    modifier: Modifier = Modifier,
    enableLinkify: Boolean = remember(text) {
        LinkifyTextDefaults.supportSchemes.any { scheme -> text.contains(scheme) }
    },
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    linkStyles: TextLinkStyles = LinkifyTextDefaults.textLinkStyles(),
    linkInteractionListener: LinkInteractionListener? = null,
) {
    if (enableLinkify) {
        val linkifyText = remember(text) {
            text.linkify(
                linkStyles = linkStyles,
                linkInteractionListener = linkInteractionListener,
            )
        }
        Text(
            text = linkifyText,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            inlineContent = inlineContent,
            onTextLayout = onTextLayout,
            style = style,
        )
    } else {
        Text(
            text = text,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
}

/**
 * [Linkify.WEB_URLS] に対応するURLをユーザーアクション化して [AnnotatedString] に追加表示する[Text]
 *
 * @param text 元のAnnotatedString。基本的に[Annotation]はマージされるが、該当範囲が被るとリンク用の[Annotation]で上書きされる
 * @param enableLinkify URLをリンク化するかどうか。デフォルトではスキームを判定して[remember]する
 * @param linkInteractionListener リンクタップ時のコールバック。デフォルトでは[Intent.ACTION_VIEW]相当でURLを開く
 */
@Composable
fun LinkifyText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    enableLinkify: Boolean = remember(text) {
        LinkifyTextDefaults.supportSchemes.any { scheme -> text.contains(scheme) }
    },
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    linkStyles: TextLinkStyles = LinkifyTextDefaults.textLinkStyles(),
    linkInteractionListener: LinkInteractionListener? = null,
) {
    if (enableLinkify) {
        val linkifyText = remember(text) {
            text.linkify(
                linkStyles = linkStyles,
                linkInteractionListener = linkInteractionListener,
            )
        }
        Text(
            text = linkifyText,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            inlineContent = inlineContent,
            onTextLayout = onTextLayout,
            style = style,
        )
    } else {
        Text(
            text = text,
            modifier = modifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            textAlign = textAlign,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = maxLines,
            minLines = minLines,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
}

private fun String.linkify(
    linkStyles: TextLinkStyles,
    linkInteractionListener: LinkInteractionListener?,
) = buildAnnotatedString {
    append(this@linkify)

    linkifyFromSpannable(
        spannable = SpannableString(this@linkify),
        linkStyles = linkStyles,
        linkInteractionListener = linkInteractionListener,
    )
}

private fun AnnotatedString.linkify(
    linkStyles: TextLinkStyles,
    linkInteractionListener: LinkInteractionListener?,
) = buildAnnotatedString {
    append(this@linkify)

    linkifyFromSpannable(
        spannable = SpannableString(this@linkify),
        linkStyles = linkStyles,
        linkInteractionListener = linkInteractionListener,
    )
}

// NOTE: Linkify.WEB_URLS専用。Linkify.MAIL_ADDRESSなどの対応が必要になったら追加対応
/**
 * 一度[SpannableString]に[Linkify]で追加したリンクを抽出し、[AnnotatedString]に貼り直す
 *
 * @param spannable 元となる[SpannableString]
 * @param linkStyles リンクテキストに充てる[TextLinkStyles]
 * @param linkInteractionListener リンクタップ時のコールバック。nullでは[Intent.ACTION_VIEW]相当でURLを開く
 */
private fun AnnotatedString.Builder.linkifyFromSpannable(
    spannable: SpannableString,
    linkStyles: TextLinkStyles,
    linkInteractionListener: LinkInteractionListener?,
) {
    Linkify.addLinks(
        spannable,
        Linkify.WEB_URLS,
    )
    val urlSpans = spannable.getSpans(
        0,
        spannable.length,
        URLSpan::class.java,
    )
    urlSpans.forEach { urlSpan ->
        val start = spannable.getSpanStart(urlSpan)
        val end = spannable.getSpanEnd(urlSpan)

        val linkAnnotation = LinkAnnotation.Url(
            url = urlSpan.url,
            styles = linkStyles,
            linkInteractionListener = linkInteractionListener,
        )
        addLink(
            url = linkAnnotation,
            start = start,
            end = end,
        )
    }
}
