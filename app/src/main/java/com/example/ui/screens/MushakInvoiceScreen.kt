package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
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

@Composable
fun MushakInvoiceScreen() {
    var selectedSlug by remember { mutableStateOf("sheltech") }
    var selectedAmount by remember { mutableStateOf(2300000L) }
    var packageName by remember { mutableStateOf("50% Mobilization Retainer — Tier-1 Flagship Turnaround Cockpit") }
    var copiedInvoice by remember { mutableStateOf(false) }

    val currentClient = remember(selectedSlug) {
        MasterData.verifiedTargets.find { it.slug == selectedSlug } ?: MasterData.verifiedTargets.first()
    }

    val packages = listOf(
        Pair("50% 8-Katha Sandbox Retainer", 600000L),
        Pair("Vault MSA Legal Token Deposit", 110000L),
        Pair("Full Turnkey 8-Katha Sandbox", 1200000L),
        Pair("50% Tier-1 Flagship Retainer", 2300000L),
        Pair("E-Com COD Shield Fast Pilot", 25000L)
    )

    val baseValue = selectedAmount
    val vatRate = MasterData.vatRatePct
    val vatAmount = (baseValue * (vatRate / 100.0)).toLong()
    val totalPayable = baseValue + vatAmount

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("mushak_invoice_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Selector Bar
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CyanGlowBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Description, contentDescription = null, tint = CyanAccent)
                        Text(
                            text = "Form Mushak-6.3 VAT Tax Invoice Generator",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Text(
                        text = "National Board of Revenue (NBR) Bangladesh • Service Code S099.10 (5% Statutory VAT)",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    // Client Selection Chips
                    Text("Select Recipient Client:", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        MasterData.verifiedTargets.take(4).forEach { target ->
                            FilterChip(
                                selected = selectedSlug == target.slug,
                                onClick = { selectedSlug = target.slug },
                                label = { Text(target.name.take(12), fontSize = 11.sp) },
                                modifier = Modifier.testTag("client_chip_${target.slug}")
                            )
                        }
                    }

                    // Package Selection
                    Text("Select Service Scope / Package:", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        packages.forEach { pkg ->
                            FilterChip(
                                selected = selectedAmount == pkg.second,
                                onClick = {
                                    selectedAmount = pkg.second
                                    packageName = pkg.first
                                },
                                label = { Text("${pkg.first} (৳${"%,d".format(pkg.second)})", fontSize = 11.sp) },
                                modifier = Modifier.testTag("pkg_chip_${pkg.second}")
                            )
                        }
                    }
                }
            }
        }

        // 2. Printable Official Mushak-6.3 Document Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth().testTag("mushak_invoice_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "GOVERNMENT OF THE PEOPLE'S REPUBLIC OF BANGLADESH",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "NATIONAL BOARD OF REVENUE — FORM MUSHAK-6.3",
                        fontSize = 13.sp,
                        color = CyanAccent,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "[See Clauses (c) and (f) of Sub-rule (1) of Rule 40]",
                        fontSize = 10.sp,
                        color = TextMuted
                    )

                    Divider(color = CardBorder, thickness = 0.5.dp)

                    Text("Supplier Details:", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("• Name: ${MasterData.corporateBrandName}", fontSize = 11.sp, color = TextSecondary)
                    Text("• BIN: ${MasterData.nbrBin} | Service Code: ${MasterData.nbrServiceCode}", fontSize = 11.sp, color = TextSecondary)
                    Text("• Address: ${MasterData.hqAddress}", fontSize = 11.sp, color = TextSecondary)

                    Divider(color = CardBorder, thickness = 0.5.dp)

                    Text("Buyer / Recipient Details:", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text("• Client: ${currentClient.name}", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    Text("• Flagship Asset: ${currentClient.flagshipProject}", fontSize = 11.sp, color = TextSecondary)
                    Text("• Service Description: $packageName", fontSize = 11.sp, color = CyanAccent)

                    Divider(color = CardBorder, thickness = 0.5.dp)

                    // Financial Breakdown
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Base Service Retainer Value:", fontSize = 12.sp, color = TextSecondary)
                            Text("৳ ${"%,d".format(baseValue)} BDT", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Statutory 5% VAT (S099.10):", fontSize = 12.sp, color = TextSecondary)
                            Text("৳ ${"%,d".format(vatAmount)} BDT", fontSize = 12.sp, color = AmberWarning, fontWeight = FontWeight.SemiBold)
                        }
                        Divider(color = CardBorder, thickness = 0.5.dp)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Total Tax-Inclusive Payable:", fontSize = 14.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                            Text("৳ ${"%,d".format(totalPayable)} BDT", fontSize = 15.sp, color = EmeraldSuccess, fontWeight = FontWeight.Bold)
                        }
                    }

                    Surface(
                        color = NavySurface,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Wire Destination: ${MasterData.trustBankName} (Jalalabad Cantonment)", fontSize = 11.sp, color = TextPrimary)
                            Text("A/C: ${MasterData.trustAccountNumber} • Routing: ${MasterData.trustRoutingNumber}", fontSize = 11.sp, color = CyanAccent, fontWeight = FontWeight.SemiBold)
                            Text("Signatory: ${MasterData.founderName} (${MasterData.founderTitle})", fontSize = 11.sp, color = TextSecondary)
                        }
                    }

                    Button(
                        onClick = { copiedInvoice = true },
                        colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().testTag("copy_invoice_btn")
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(if (copiedInvoice) "Invoice Copied!" else "Copy Mushak-6.3 Invoice Text", color = ObsidianBg, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
