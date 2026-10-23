package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MasterData
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun ClientSandboxScreen(
    clientSlug: String = "sheltech"
) {
    var selectedSlug by remember { mutableStateOf(clientSlug) }
    var currentStep by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    val activeClient = remember(selectedSlug) {
        MasterData.verifiedTargets.find { it.slug == selectedSlug } ?: MasterData.verifiedTargets.first()
    }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            currentStep = 0
            while (currentStep < 4) {
                delay(900)
                currentStep += 1
            }
            isRunning = false
        }
    }

    val steps = listOf(
        Pair("Step 1: Meta Cloud API Ingested (0.2s)", "Inbound prospect lead payload received from Meta Graph API v21.0 via edge gateway. Sender: +1-646-555-0198 (New York, USA)."),
        Pair("Step 2: Autonomous AI Scoring (0.8s)", "Parsed intent, budget ($410k USD), source of funds, and central bank NRTA eligibility in JSON format. Match: ${activeClient.flagshipProject}."),
        Pair("Step 3: 4K Walkthrough & EMI Dispatched (1.4s)", "Personalized WhatsApp media message delivered to prospect mobile with live currency conversion ($410,000 USD / ৳4.85 Cr, 36 Mo @ $7,972/mo)."),
        Pair("Step 4: 48-Hour Unit Hold & Escrow (2.1s)", "Unit temporarily locked with QR-coded Saf-Kabala milestone voucher. Trust Bank Escrow account attached.")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("sandbox_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Simulation Header Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(EmeraldGlowBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.FlashOn, contentDescription = null, tint = EmeraldSuccess)
                            Text(
                                text = "38-Second NRI/NRB Turnaround Cockpit",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }

                    Text(
                        text = "Autonomous high-ticket lead qualification pipeline simulating instant US/UK/Gulf diaspora response.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    // Client Selector Chips
                    Text("Select Target Developer Prototype:", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MasterData.verifiedTargets.take(4).forEach { target ->
                            FilterChip(
                                selected = selectedSlug == target.slug,
                                onClick = {
                                    selectedSlug = target.slug
                                    currentStep = 0
                                    isRunning = false
                                },
                                label = { Text(target.name.take(12), fontSize = 11.sp) },
                                modifier = Modifier.testTag("sandbox_client_chip_${target.slug}")
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { isRunning = true },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                            shape = RoundedCornerShape(10.dp),
                            enabled = !isRunning,
                            modifier = Modifier.weight(1f).testTag("trigger_simulation_button")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(if (isRunning) "Processing 38s Pipeline..." else "Run 38s Pipeline Test", color = ObsidianBg, fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = {
                                currentStep = 0
                                isRunning = false
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("reset_simulation_button")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        // 2. Interactive Steps
        items(steps.size) { index ->
            val step = steps[index]
            val isDone = currentStep > index
            val isCurrent = currentStep == index && isRunning

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isDone) SurfaceDarkVariant else NavySurface
                ),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        when {
                            isDone -> EmeraldSuccess
                            isCurrent -> CyanAccent
                            else -> CardBorder
                        }
                    )
                ),
                modifier = Modifier.fillMaxWidth().testTag("sandbox_step_$index")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(step.first, fontWeight = FontWeight.Bold, color = if (isDone) EmeraldSuccess else TextPrimary, fontSize = 14.sp)
                        if (isDone) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(18.dp))
                        } else if (isCurrent) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = CyanAccent, strokeWidth = 2.dp)
                        }
                    }
                    Text(step.second, fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}
