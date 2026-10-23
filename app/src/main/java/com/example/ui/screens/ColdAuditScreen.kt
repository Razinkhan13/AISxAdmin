package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MasterData
import com.example.model.RedmaPreset
import com.example.ui.theme.*

@Composable
fun ColdAuditScreen() {
    // Slider States
    var unsoldSqft by remember { mutableStateOf(65000f) }
    var avgPriceSqft by remember { mutableStateOf(31800f) }
    var odRatePct by remember { mutableStateOf(14.0f) }
    var responseDelayHours by remember { mutableStateOf(72f) }

    // 8-Katha States
    var selectedPreset by remember { mutableStateOf(MasterData.redmaPresets.first()) }
    var katha by remember { mutableStateOf(selectedPreset.katha.toFloat()) }
    var unitSqft by remember { mutableStateOf(selectedPreset.sqft.toFloat()) }
    var pricePerSqft by remember { mutableStateOf(selectedPreset.priceSqft.toFloat()) }
    var cancelledUnits by remember { mutableStateOf(selectedPreset.cancelledUnits.toFloat()) }
    var stalledUnits by remember { mutableStateOf(selectedPreset.stalledUnits.toFloat()) }
    var bankOdCrore by remember { mutableStateOf(selectedPreset.bankOdCrore.toFloat()) }

    // Computations
    val trappedCapitalBDT = (unsoldSqft * avgPriceSqft).toDouble()
    val trappedCapitalCr = trappedCapitalBDT / 10000000.0
    val monthlyOdBleedBDT = (trappedCapitalBDT * (odRatePct / 100.0)) / 12.0
    val monthlyOdBleedCr = monthlyOdBleedBDT / 10000000.0
    val dailyBleedLakh = (monthlyOdBleedBDT / 30.0) / 100000.0
    val syndicateUnlockCr = trappedCapitalCr * 0.15

    val attritionPct = (15 + (responseDelayHours / 72f) * 63).coerceAtMost(85f)
    val lostPipelineMonthlyCr = (trappedCapitalCr * 0.08) * (attritionPct / 100.0)

    // 8-Katha Calculations
    val unitPriceCr = (unitSqft * pricePerSqft) / 10000000.0
    val paidMilestoneCr = unitPriceCr * 0.5
    val redmaSec14ShockCr = cancelledUnits * paidMilestoneCr * 0.90
    val redmaSec15PenaltyLakh = stalledUnits * paidMilestoneCr * 0.15 * 100
    val bankOdDragLakh = bankOdCrore * 0.14 * 100
    val displacementRentLakh = (katha / 8.0) * 12.0
    val siteSecurityDecayLakh = (katha / 8.0) * 18.0
    val annualCarryingBleedLakh = redmaSec15PenaltyLakh + bankOdDragLakh + displacementRentLakh + siteSecurityDecayLakh
    val totalSingleSiteBleedCr = (annualCarryingBleedLakh / 100.0) + redmaSec14ShockCr

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("cold_audit_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Cockpit Header & Metrics
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(RoseDanger.copy(alpha = 0.5f))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.TrendingDown, contentDescription = null, tint = RoseDanger)
                        Text(
                            text = "Executive Capital Relief Cockpit",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    // Key KPI Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(Modifier.padding(10.dp)) {
                                Text("Trapped Capital", fontSize = 11.sp, color = TextSecondary)
                                Text("৳${"%.2f".format(trappedCapitalCr)} Cr", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AmberWarning)
                            }
                        }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(Modifier.padding(10.dp)) {
                                Text("Monthly OD Bleed", fontSize = 11.sp, color = TextSecondary)
                                Text("৳${"%.2f".format(monthlyOdBleedCr)} Cr", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RoseDanger)
                            }
                        }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(Modifier.padding(10.dp)) {
                                Text("Daily Burn", fontSize = 11.sp, color = TextSecondary)
                                Text("৳${"%.1f".format(dailyBleedLakh)} L/day", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RoseDanger)
                            }
                        }
                    }

                    // Sliders
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Unsold Luxury Inventory: ${unsoldSqft.toInt()} sq ft", fontSize = 12.sp, color = TextPrimary)
                        Slider(
                            value = unsoldSqft,
                            onValueChange = { unsoldSqft = it },
                            valueRange = 10000f..150000f,
                            modifier = Modifier.testTag("slider_unsold_sqft")
                        )

                        Text("Avg Price / sq ft: ৳${avgPriceSqft.toInt()}", fontSize = 12.sp, color = TextPrimary)
                        Slider(
                            value = avgPriceSqft,
                            onValueChange = { avgPriceSqft = it },
                            valueRange = 15000f..45000f,
                            modifier = Modifier.testTag("slider_avg_price")
                        )

                        Text("Bank OD Interest Rate: ${"%.1f".format(odRatePct)}%", fontSize = 12.sp, color = TextPrimary)
                        Slider(
                            value = odRatePct,
                            onValueChange = { odRatePct = it },
                            valueRange = 10f..18f,
                            modifier = Modifier.testTag("slider_od_rate")
                        )

                        Text("Lead Response Delay: ${responseDelayHours.toInt()} Hours (Diaspora Attrition: ${attritionPct.toInt()}%)", fontSize = 12.sp, color = TextPrimary)
                        Slider(
                            value = responseDelayHours,
                            onValueChange = { responseDelayHours = it },
                            valueRange = 1f..72f,
                            modifier = Modifier.testTag("slider_delay_hours")
                        )
                    }

                    Surface(
                        color = EmeraldSuccess.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "⚡ 90-Day AIS Syndicate Unlock Target: ৳${"%.2f".format(syndicateUnlockCr)} Cr in released cash liquidity",
                            color = EmeraldSuccess,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            }
        }

        // 2. 8-Katha REDMA Liability Auditor
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AmberWarning.copy(alpha = 0.5f))),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = AmberWarning)
                        Text(
                            text = "8-Katha REDMA Sec 14/15 Liability Auditor",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Text("Presets:", fontSize = 12.sp, color = TextSecondary)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MasterData.redmaPresets.forEach { preset ->
                            FilterChip(
                                selected = selectedPreset.name == preset.name,
                                onClick = {
                                    selectedPreset = preset
                                    katha = preset.katha.toFloat()
                                    unitSqft = preset.sqft.toFloat()
                                    pricePerSqft = preset.priceSqft.toFloat()
                                    cancelledUnits = preset.cancelledUnits.toFloat()
                                    stalledUnits = preset.stalledUnits.toFloat()
                                    bankOdCrore = preset.bankOdCrore.toFloat()
                                },
                                label = { Text(preset.name.take(15), fontSize = 11.sp) },
                                modifier = Modifier.testTag("preset_${preset.name.take(6).lowercase()}")
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceDarkVariant, RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("• REDMA Sec 14 Mandatory 90d Refund Shock: ৳${"%.2f".format(redmaSec14ShockCr)} Cr", color = RoseDanger, fontSize = 12.sp)
                        Text("• REDMA Sec 15 Delay Penalty (15%/yr): ৳${"%.1f".format(redmaSec15PenaltyLakh)} Lakh", color = AmberWarning, fontSize = 12.sp)
                        Text("• Bank OD Drag (14%/yr): ৳${"%.1f".format(bankOdDragLakh)} Lakh", color = AmberWarning, fontSize = 12.sp)
                        Text("• Landowner Displacement Rent: ৳${"%.1f".format(displacementRentLakh)} Lakh", color = TextSecondary, fontSize = 12.sp)
                        Divider(color = CardBorder, thickness = 0.5.dp)
                        Text(
                            text = "Total Annual Single-Site Bleed: ৳${"%.2f".format(totalSingleSiteBleedCr)} Cr",
                            fontWeight = FontWeight.Bold,
                            color = RoseDanger,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // 3. Cognitive Battlecards
        item {
            Text("Boardroom Cognitive Battlecards", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
        }

        items(MasterData.cognitiveBattlecards.size) { index ->
            val card = MasterData.cognitiveBattlecards[index]
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth().testTag("battlecard_${card.id}")
            ) {
                Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(card.title, fontWeight = FontWeight.Bold, color = CyanAccent, fontSize = 14.sp)
                    Text("Quote: ${card.chairmanQuote}", color = TextSecondary, fontSize = 12.sp)
                    Text("Reality: ${card.economicReality}", color = AmberWarning, fontSize = 12.sp)
                    Text("AIS Counter: ${card.counterMove}", color = EmeraldSuccess, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                }
            }
        }

        // 4. Open-Source Argon Deal Strategist Assistant
        item {
            var strategistInput by remember { mutableStateOf("") }
            var strategistHistory by remember {
                mutableStateOf(
                    listOf(
                        Pair("rizum", "How do I break the Zamindar Fallacy when pitching a 12% syndicate discount?"),
                        Pair("argon", "Filial respect frame: Remind the Chairman that at 14.5% quarterly bank OD compounding, holding an unsold ৳4.85 Cr unit for 24 months burns ৳1.52 Cr in pure bank interest. A 12% syndicate release costs only ৳58 Lakh, saving ৳94 Lakh net cash while extinguishing loan liability.")
                    )
                )
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CyanAccent.copy(alpha = 0.5f))),
                modifier = Modifier.fillMaxWidth().testTag("argon_strategist_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Shield, contentDescription = null, tint = CyanAccent)
                            Text(
                                text = "Argon Open-Source Deal Strategist",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        Surface(
                            color = CyanAccent.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                "100% Free / Local",
                                fontSize = 10.sp,
                                color = CyanAccent,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Text(
                        text = "Real-time boardroom deal intelligence connected to your current ৳${"%.2f".format(trappedCapitalCr)} Cr trapped inventory and ৳${"%.2f".format(monthlyOdBleedCr)} Cr/mo OD bleed.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    // Strategic Prompts
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "OD Bleed vs Discount",
                            "Sec 14 Refund Risk",
                            "Trust Bank Escrow"
                        ).forEach { prompt ->
                            FilterChip(
                                selected = false,
                                onClick = {
                                    val reply = when (prompt) {
                                        "OD Bleed vs Discount" -> "At 14.5% annual OD rate, carrying ৳${"%.2f".format(trappedCapitalCr)} Cr burns ৳${"%.2f".format(monthlyOdBleedCr)} Cr monthly. Releasing 15% via an AIS syndicate saves immediate cash flow."
                                        "Sec 14 Refund Risk" -> "REDMA Section 14 mandates a 90% refund within 90 days of cancellation. For your current 8-katha footprint, refund shock is ৳${"%.2f".format(redmaSec14ShockCr)} Cr."
                                        else -> "Trust Bank PLC account 00110466000494 ring-fences buyer deposits in standalone digital escrow, eliminating pooling risks across stalled towers."
                                    }
                                    strategistHistory = strategistHistory + Pair("rizum", prompt) + Pair("argon", reply)
                                },
                                label = { Text(prompt, fontSize = 11.sp) },
                                modifier = Modifier.testTag("strategy_prompt_${prompt.take(4).lowercase()}")
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
                        strategistHistory.takeLast(4).forEach { (sender, text) ->
                            Column {
                                Text(
                                    text = if (sender == "rizum") "👔 Rizum Khan (Founder):" else "⚡ Argon Open-Source Co-Founder:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (sender == "rizum") EmeraldSuccess else CyanAccent
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
                            value = strategistInput,
                            onValueChange = { strategistInput = it },
                            placeholder = { Text("Ask a boardroom strategy question...", fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).testTag("strategist_input_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary,
                                focusedBorderColor = CyanAccent,
                                unfocusedBorderColor = CardBorder
                            )
                        )
                        Button(
                            onClick = {
                                if (strategistInput.isNotBlank()) {
                                    val prompt = strategistInput
                                    strategistInput = ""
                                    val reply = "Boardroom analysis: Present the 10-second audit showing the ৳${"%.2f".format(monthlyOdBleedCr)} Cr monthly OD drain. Close with our 100% risk-reversal covenant (5x fee in 60 days or 100% refund)."
                                    strategistHistory = strategistHistory + Pair("rizum", prompt) + Pair("argon", reply)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("strategist_send_button")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = null, tint = ObsidianBg)
                        }
                    }
                }
            }
        }
    }
}
