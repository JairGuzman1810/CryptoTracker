package com.app.cryptotracker.crypto.presentation.coin_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.cryptotracker.R
import com.app.cryptotracker.ui.theme.CryptoTrackerTheme

/**
 * InfoCard is a composable function that displays a card with an icon, a title, and formatted text.
 *
 * This composable is used to display information in a structured card format,
 * such as details about a coin in the coin detail screen.
 *
 * @param title The title of the information card.
 * @param formattedText The formatted text to display in the card.
 * @param icon The icon to display in the card.
 * @param modifier The modifier to apply to the card.
 * @param contentColor The color of the text and icon content.
 */
@Composable
fun InfoCard(
    title: String,
    formattedText: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    // Defines the default text style for the formatted text.
    val defaultTextStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = contentColor
    )

    // Card composable to display the information.
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 15.dp,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            ),
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = contentColor
        )
    ) {
        // AnimatedContent for the icon.
        AnimatedContent(
            targetState = icon,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            label = "IconAnimation"
        ) { icon ->
            // Icon composable to display the icon.
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(75.dp)
                    .padding(top = 16.dp),
                tint = contentColor
            )
        }

        // Spacer for vertical spacing.
        Spacer(modifier = Modifier.height(8.dp))

        // AnimatedContent for the formatted text.
        AnimatedContent(
            targetState = formattedText,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            ),
            label = "ValueAnimation"
        ) { formattedText ->
            // Text composable to display the formatted text.
            Text(
                text = formattedText,
                style = defaultTextStyle,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Spacer for vertical spacing.
        Spacer(modifier = Modifier.height(8.dp))

        // Text composable to display the title.
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = contentColor
        )
    }
}

/**
 * InfoCardPreview is a preview function for the InfoCard composable.
 *
 * This function allows you to preview the InfoCard in the IDE.
 */
@PreviewLightDark
@Composable
private fun InfoCardPreview() {
    CryptoTrackerTheme {
        InfoCard(
            title = "Price",
            formattedText = "$123.45",
            icon = ImageVector.vectorResource(id = R.drawable.dollar)
        )
    }
}