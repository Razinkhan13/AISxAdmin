package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.*
import com.example.ui.theme.*

enum class AppTab(val title: String, val icon: ImageVector) {
    WHATSAPP("WhatsApp", Icons.Default.Chat),
    AUDIT("Cold Audit", Icons.Default.TrendingDown),
    APPROVE("Approve", Icons.Default.Lock),
    INVOICE("Mushak-6.3", Icons.Default.ReceiptLong),
    SANDBOX("Sandbox", Icons.Default.FlashOn),
    VAULT("Vault", Icons.Default.Folder),
    RLS("RLS Engine", Icons.Default.Security)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AISCommandCenterTheme {
                MainAppShell()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppShell() {
    var currentTab by remember { mutableStateOf(AppTab.WHATSAPP) }
    var sandboxSlug by remember { mutableStateOf("sheltech") }

    // Android predictive back support
    BackHandler(enabled = currentTab != AppTab.WHATSAPP) {
        currentTab = AppTab.WHATSAPP
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            color = CyanAccent.copy(alpha = 0.2f),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("A", fontWeight = FontWeight.Bold, color = CyanAccent, fontSize = 14.sp)
                            }
                        }
                        Column {
                            Text(
                                text = "AIS Enterprise Systems",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "Executive Relief Cockpit • Army IBA Specialist",
                                style = MaterialTheme.typography.labelSmall,
                                color = CyanAccent
                            )
                        }
                    }
                },
                actions = {
                    Surface(
                        color = EmeraldSuccess.copy(alpha = 0.15f),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "Rizum Khan (Founder)",
                            color = EmeraldSuccess,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NavySurface,
                    titleContentColor = TextPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = NavySurface,
                modifier = Modifier.testTag("main_navigation_bar")
            ) {
                AppTab.values().forEach { tab ->
                    NavigationBarItem(
                        selected = currentTab == tab,
                        onClick = { currentTab = tab },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title, fontSize = 10.sp) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = CyanAccent,
                            selectedTextColor = CyanAccent,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary,
                            indicatorColor = ObsidianBg
                        ),
                        modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(ObsidianBg)
        ) {
            when (currentTab) {
                AppTab.WHATSAPP -> WhatsAppScreen(
                    onNavigateToSandbox = { slug ->
                        sandboxSlug = slug
                        currentTab = AppTab.SANDBOX
                    }
                )
                AppTab.AUDIT -> ColdAuditScreen()
                AppTab.APPROVE -> ApproveLockScreen()
                AppTab.INVOICE -> MushakInvoiceScreen()
                AppTab.SANDBOX -> ClientSandboxScreen(clientSlug = sandboxSlug)
                AppTab.VAULT -> DocumentVaultScreen()
                AppTab.RLS -> WebhookRLSScreen()
            }
        }
    }
}
