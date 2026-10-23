package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Folder
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
fun DocumentVaultScreen() {
    var selectedDocId by remember { mutableStateOf("msa") }
    var copied by remember { mutableStateOf(false) }

    val docs = listOf(
        Pair("manual", "Doc 1: Master Operating Manual (৳1Cr Math)"),
        Pair("dossier", "Doc 2: RE Turnaround Dossier (10 Giants)"),
        Pair("msa", "Doc 3: 1-Page Proposal & Binding MSA"),
        Pair("invoice", "Doc 4: Formal Mushak-6.3 Tax Specification")
    )

    val docContent = when (selectedDocId) {
        "manual" -> """
AIS ENTERPRISE SYSTEMS — MASTER OPERATING MANUAL
=================================================
Core Thesis:
Real estate developers in Dhaka face a 14% to 17% bank overdraft rate on unabsorbed luxury inventory.
By cutting lead response times from 72 hours down to 38 seconds for US/UK/Gulf diaspora buyers,
we unlock locked balance sheet inventory.

Revenue Targets:
• Tier-1 Developer Retainers (2 @ ৳23L): ৳46 Lakh
• 8-Katha Empirical Sandboxes (4 @ ৳6L): ৳24 Lakh
• Mid-Market Setup Fees (5 @ ৳1.4L): ৳7 Lakh
• E-Com COD Shields (5 @ ৳35k): ৳1.75 Lakh
• Success Fee Milestone (5% of liquidations): ৳25+ Lakh
Total 90-Day Cash Liquidity: ৳1.00+ Crore
        """.trimIndent()

        "dossier" -> """
TIER-1 BANGLADESH REAL ESTATE TURNAROUND DOSSIER
================================================
Target 1: Sheltech (Pvt.) Ltd.
• Vulnerability: 25% booking cancellations draining active cash under REDMA Sec 14.
• Solution: Ring-fence 1 condominium in Trust Bank Escrow, subdivide into 800-1200 sqft units.

Target 2: Concord Real Estate Ltd.
• Vulnerability: Trapped capital in large towers from 20-40% DAP FAR cuts.
• Solution: Pair precast plants with aluminum formwork; structure SPV for Grade-A floors.

Target 3: bti (Building Tech & Ideas)
• Vulnerability: Sales velocity dropped to 1-2 units/mo; holding costs ৳2,500/sqft/yr.
• Solution: Bulk-liquidate 12 stagnant units at 15% discount; connect ISO CRM to NRTA loans.
        """.trimIndent()

        "msa" -> """
1-PAGE COMMERCIAL PROPOSAL & MASTER SERVICE AGREEMENT (MSA)
============================================================
Parties: AIS Enterprise Systems & Corporate Client

Commercial Covenant:
1. Retainer: 50% mobilization retainer disbursed into Trust Bank PLC account (00110466000494).
2. Go-Live: System operational within 14 calendar days.
3. 60-Day Guarantee: If AIS does not cut response time to sub-4 minutes 24/7 and qualify at least 5x our fee in verified buyer pipeline within 60 days, we refund 100% of our fee and you retain all installed workflows and systems.

Statutory Compliance: NBR BIN 004982103-0101, Form Mushak-6.3 Tax Invoice issued.
Authorized Signatory: Rizum Khan (Founder & Final Signatory)
        """.trimIndent()

        else -> """
FORM MUSHAK-6.3 STATUTORY SPECIFICATION
========================================
National Board of Revenue (NBR) Bangladesh
Rule 40, Value Added Tax & Supplementary Duty Act 2012
Service Code S099.10: IT Enabled Services & Hosted ERP Software
Statutory Rate: 5.0% Value Added Tax
All tax invoices cryptographically sealed and registered under BIN 004982103-0101.
        """.trimIndent()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("document_vault_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CyanGlowBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Folder, contentDescription = null, tint = CyanAccent)
                        Text(
                            text = "AIS 4-in-1 Document Vault",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    // Selector Chips
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        docs.forEach { doc ->
                            FilterChip(
                                selected = selectedDocId == doc.first,
                                onClick = {
                                    selectedDocId = doc.first
                                    copied = false
                                },
                                label = { Text(doc.second, fontSize = 11.sp) },
                                modifier = Modifier.testTag("doc_chip_${doc.first}")
                            )
                        }
                    }
                }
            }
        }

        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth().testTag("vault_content_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Legal & Financial Text", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CyanAccent)
                        Button(
                            onClick = { copied = true },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("copy_vault_doc_btn")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(if (copied) "Copied!" else "Copy Document", color = ObsidianBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Text(
                        text = docContent,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = TextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}
