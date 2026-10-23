package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MasterData
import com.example.model.TargetCategory
import com.example.model.VerifiedTarget
import com.example.ui.theme.*

@Composable
fun WhatsAppScreen(
    onNavigateToSandbox: (String) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(TargetCategory.ALL) }
    var selectedSlug by remember { mutableStateOf("sheltech") }
    var isBangla by remember { mutableStateOf(false) }
    var copiedMessage by remember { mutableStateOf<String?>(null) }
    var currency by remember { mutableStateOf("USD") }

    val activeTarget = remember(selectedSlug) {
        MasterData.verifiedTargets.find { it.slug == selectedSlug } ?: MasterData.verifiedTargets.first()
    }

    val filteredTargets = remember(selectedCategory) {
        if (selectedCategory == TargetCategory.ALL) {
            MasterData.verifiedTargets
        } else {
            MasterData.verifiedTargets.filter { it.category == selectedCategory }
        }
    }

    val currencyRates = mapOf("USD" to 120.0, "GBP" to 155.0, "AED" to 33.0)
    val priceRate = currencyRates[currency] ?: 120.0
    val approxForeign = (48500000L / priceRate).toLong()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("whatsapp_screen_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Experiential Demo Link Bar
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CyanGlowBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Blitz", tint = CyanAccent)
                        Text(
                            text = "Client-Branded Experiential Demo Link Generator",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                    }
                    Text(
                        text = "Auto-generates live ?demo=${activeTarget.slug} deep links for instant boardroom WhatsApp pitching.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                copiedMessage = "https://ais-command-center.app/?demo=${activeTarget.slug}"
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("copy_demo_link_button")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(if (copiedMessage != null) "Copied Link!" else "Copy Demo Link", color = ObsidianBg, fontWeight = FontWeight.Bold)
                        }
                        OutlinedButton(
                            onClick = { onNavigateToSandbox(activeTarget.slug) },
                            shape = RoundedCornerShape(10.dp),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(CyanAccent)),
                            modifier = Modifier.testTag("test_client_view_button")
                        ) {
                            Text("Open Sandbox", color = CyanAccent)
                        }
                    }
                }
            }
        }

        // 2. Diaspora Currency Conversion Simulator
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("NRB Diaspora Currency Rate", fontWeight = FontWeight.Bold, color = TextPrimary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf("USD", "GBP", "AED").forEach { cur ->
                                FilterChip(
                                    selected = currency == cur,
                                    onClick = { currency = cur },
                                    label = { Text(cur, fontSize = 12.sp) },
                                    modifier = Modifier.testTag("currency_chip_$cur")
                                )
                            }
                        }
                    }
                    Text(
                        text = "BDT Reference: ${activeTarget.bdtPrice}  ➜  Approx: $currency ${"%,d".format(approxForeign)} (Booking 30%: $currency ${"%,d".format((approxForeign * 0.3).toLong())})",
                        color = EmeraldSuccess,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // 3. Category Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TargetCategory.values().forEach { cat ->
                    val label = when (cat) {
                        TargetCategory.ALL -> "All Targets"
                        TargetCategory.TIER1 -> "Tier-1 RE"
                        TargetCategory.MID -> "Mid-RE"
                        TargetCategory.ECOM -> "E-Com COD"
                    }
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(label, fontSize = 12.sp) },
                        modifier = Modifier.testTag("filter_${cat.name.lowercase()}")
                    )
                }
            }
        }

        // 4. Target Directory
        item {
            Text(
                text = "Target Directory (${filteredTargets.size} Companies)",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
        }

        items(filteredTargets) { target ->
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (target.slug == selectedSlug) CyanAccent else CardBorder
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { selectedSlug = target.slug }
                    .testTag("target_card_${target.slug}")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(target.name, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 16.sp)
                        Surface(
                            color = when (target.currentStatus) {
                                "Locked & Mobilized" -> EmeraldSuccess.copy(alpha = 0.2f)
                                "Boardroom Scheduled" -> AmberWarning.copy(alpha = 0.2f)
                                else -> CyanAccent.copy(alpha = 0.2f)
                            },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = target.currentStatus,
                                color = when (target.currentStatus) {
                                    "Locked & Mobilized" -> EmeraldSuccess
                                    "Boardroom Scheduled" -> AmberWarning
                                    else -> CyanAccent
                                },
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Text("Flagship: ${target.flagshipProject}", color = CyanAccent, fontSize = 13.sp)
                    Text("Unit Spec: ${target.unitSpec}", color = TextSecondary, fontSize = 12.sp)
                    Text("Price: ${target.bdtPrice} • Deal Potential: ৳${target.dealValueLakh}L", color = EmeraldSuccess, fontSize = 12.sp)

                    Divider(color = CardBorder, thickness = 0.5.dp)

                    Text("Vulnerability: ${target.diagnosedVulnerability}", color = RoseDanger.copy(alpha = 0.9f), fontSize = 12.sp)
                    Text("Solution: ${target.customSolution}", color = TextSecondary, fontSize = 12.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        FilledTonalButton(
                            onClick = {
                                selectedSlug = target.slug
                                onNavigateToSandbox(target.slug)
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("sandbox_btn_${target.slug}")
                        ) {
                            Text("Launch 38s Sandbox", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 5. Embedded Open-Source NRB Diaspora Negotiation Simulator
        item {
            var simulatedInput by remember { mutableStateOf("") }
            var chatHistory by remember {
                mutableStateOf(
                    listOf(
                        Pair("buyer", "Calling from Queens, NY. If construction stalls on ${activeTarget.flagshipProject}, is my USD deposit 100% repatriable under Bangladesh Bank rules?"),
                        Pair("ai", "Assalamu Alaikum Sir. Under Bangladesh Bank FERA guidelines and GFET Chapter 14, funds remitted via an authorized Non-Resident Taka Account (NRTA) are 100% legally repatriable with accrued interest without requiring central bank sanction. Your 30% booking is held in our Trust Bank PLC Project Escrow (00110466000494).")
                    )
                )
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(EmeraldSuccess.copy(alpha = 0.5f))),
                modifier = Modifier.fillMaxWidth().testTag("nrb_simulator_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = EmeraldSuccess)
                            Text(
                                text = "Open-Source NRB Diaspora Simulator",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        Surface(
                            color = EmeraldSuccess.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                "100% Open-Source / Local",
                                fontSize = 10.sp,
                                color = EmeraldSuccess,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Simulate live negotiations with US, UK, and UAE diaspora buyers for ${activeTarget.name}.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    // Objection Pills
                    Text("Preset High-Friction Objections:", fontSize = 11.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "DAP 2022 Risk",
                            "Trust Bank Escrow",
                            "REDMA Delay 15%"
                        ).forEach { pill ->
                            FilterChip(
                                selected = false,
                                onClick = {
                                    val reply = when (pill) {
                                        "DAP 2022 Risk" -> "The building holds a pre-sanctioned RAJUK master approval grandfathered before the FAR reduction, with full Occupancy Certificate validity."
                                        "Trust Bank Escrow" -> "Funds are deposited into Trust Bank PLC (Account: 00110466000494, Routing: 240260494) and disbursed solely against verified construction milestone certificates."
                                        else -> "Under REDMA 2010 Section 15, any handover delay past the agreed milestone triggers a statutory 15% per annum compensation on collected buyer capital."
                                    }
                                    chatHistory = chatHistory + Pair("buyer", "Inquiry: $pill") + Pair("ai", reply)
                                },
                                label = { Text(pill, fontSize = 11.sp) },
                                modifier = Modifier.testTag("pill_${pill.take(4).lowercase()}")
                            )
                        }
                    }

                    // Chat messages
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDarkVariant, RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        chatHistory.takeLast(4).forEach { (sender, text) ->
                            Column {
                                Text(
                                    text = if (sender == "buyer") "🗽 Diaspora Buyer (New York):" else "🤖 AIS Open-Source Concierge (38s SLA):",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (sender == "buyer") CyanAccent else EmeraldSuccess
                                )
                                Text(text, fontSize = 12.sp, color = TextPrimary)
                            }
                        }
                    }

                    // Input
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = simulatedInput,
                            onValueChange = { simulatedInput = it },
                            placeholder = { Text("Ask a diaspora buyer question...", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).testTag("simulator_input_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedBorderColor = EmeraldSuccess,
                                unfocusedBorderColor = CardBorder
                            )
                        )
                        Button(
                            onClick = {
                                if (simulatedInput.isNotBlank()) {
                                    val prompt = simulatedInput
                                    simulatedInput = ""
                                    val reply = "Thank you for inquiring about ${activeTarget.flagshipProject}. Our Trust Bank digital escrow secures your wire transfer with full NRTA repatriation rights."
                                    chatHistory = chatHistory + Pair("buyer", prompt) + Pair("ai", reply)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("simulator_send_button")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = ObsidianBg)
                        }
                    }
                }
            }
        }
    }
}
