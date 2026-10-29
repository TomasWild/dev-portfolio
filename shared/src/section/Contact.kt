package dev.tomaswild.portfolio.section

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tomaswild.portfolio.adaptive.ScreenSize
import dev.tomaswild.portfolio.config.ContactInformation
import dev.tomaswild.portfolio.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Contact(
    screenSize: ScreenSize,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = when (screenSize) {
                    ScreenSize.Compact -> 24.dp
                    ScreenSize.Medium -> 40.dp
                    ScreenSize.Expanded -> 56.dp
                },
            ),
        verticalArrangement = Arrangement.spacedBy(space = 24.dp),
    ) {
        ContactSectionHeader(
            title = stringResource(resource = Res.string.contact_title),
        )

        if (screenSize == ScreenSize.Expanded) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                ContactForm(
                    onClick = onSendClick,
                    modifier = Modifier
                        .weight(weight = 1F)
                        .fillMaxHeight(),
                )

                Column(
                    modifier = Modifier.weight(weight = 1F),
                    verticalArrangement = Arrangement.spacedBy(space = 24.dp),
                ) {
                    ContactInformationCard()

                    AvailabilityCard()
                }
            }
        } else {
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 24.dp),
            ) {
                ContactForm(onClick = onSendClick)

                ContactInformationCard()

                AvailabilityCard()
            }
        }
    }
}

@Composable
private fun ContactSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun ContactForm(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var name by remember { mutableStateOf(value = "") }
    var email by remember { mutableStateOf(value = "") }
    var message by remember { mutableStateOf(value = "") }

    SectionCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(all = 32.dp),
            verticalArrangement = Arrangement.spacedBy(space = 20.dp),
        ) {
            FormField(
                label = stringResource(resource = Res.string.contact_name_label),
                value = name,
                onValueChange = { name = it },
                placeholder = stringResource(resource = Res.string.contact_name_placeholder),
            )

            FormField(
                label = stringResource(resource = Res.string.contact_email_label),
                value = email,
                onValueChange = { email = it },
                placeholder = stringResource(resource = Res.string.contact_email_placeholder),
            )

            FormField(
                label = stringResource(resource = Res.string.contact_message_label),
                value = message,
                onValueChange = { message = it },
                placeholder = stringResource(resource = Res.string.contact_message_placeholder),
                singleLine = false,
                fieldModifier = Modifier.heightIn(min = 146.dp),
            )

            Spacer(modifier = Modifier.height(height = 4.dp))

            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 60.dp)
                    .shadow(
                        elevation = 16.dp,
                        shape = CircleShape,
                        ambientColor = MaterialTheme.colorScheme.primary,
                        spotColor = MaterialTheme.colorScheme.primary,
                    ),
                enabled = false,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Text(
                    text = stringResource(resource = Res.string.contact_send_button),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                )

                Spacer(modifier = Modifier.width(width = 8.dp))

                Icon(
                    painter = painterResource(resource = Res.drawable.ic_send),
                    contentDescription = null,
                    modifier = Modifier.size(size = 18.dp),
                )
            }
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    fieldModifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = fieldModifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
            singleLine = singleLine,
            shape = RoundedCornerShape(size = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

@Composable
private fun ContactInformationCard(
    modifier: Modifier = Modifier,
) {
    SectionCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(all = 32.dp),
            verticalArrangement = Arrangement.spacedBy(space = 32.dp),
        ) {
            Text(
                text = stringResource(resource = Res.string.contact_details_title),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(space = 32.dp),
            ) {
                ContactInfoItem(
                    icon = Res.drawable.ic_mail,
                    label = stringResource(resource = Res.string.contact_email_heading),
                    value = ContactInformation.EMAIL,
                )

                ContactInfoItem(
                    icon = Res.drawable.ic_call,
                    label = stringResource(resource = Res.string.contact_phone_heading),
                    value = ContactInformation.PHONE,
                )

                ContactInfoItem(
                    icon = Res.drawable.ic_location_on,
                    label = stringResource(resource = Res.string.contact_location_heading),
                    value = ContactInformation.LOCATION,
                )
            }
        }
    }
}

@Composable
private fun ContactInfoItem(
    icon: DrawableResource,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        Box(
            modifier = Modifier
                .size(size = 48.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12F),
                    shape = RoundedCornerShape(size = 14.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(resource = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(size = 22.dp),
            )
        }

        Column {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
            )

            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 17.sp,
            )
        }
    }
}

@Composable
private fun AvailabilityCard(
    modifier: Modifier = Modifier,
) {
    SectionCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(all = 32.dp),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(size = 12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape,
                        ),
                )

                Text(
                    text = stringResource(resource = Res.string.contact_availability_status),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )
            }

            Text(
                text = stringResource(resource = Res.string.contact_availability_description),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                lineHeight = 20.sp,
            )
        }
    }
}

@Composable
private fun SectionCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(size = 32.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25F),
        ),
        content = content,
    )
}