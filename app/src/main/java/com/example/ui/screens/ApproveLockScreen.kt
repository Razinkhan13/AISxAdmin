package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MasterData
import com.example.model.TransactionRecord
import com.example.ui.theme.*

@Composable
fun ApproveLockScreen() {
    var transactions by remember { mutableStateOf(MasterData.preloadedTransactions) }
    var showDepositDialog by remember { mutableStateOf(false) }

    // Dialog state
    var clientName by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("2300000") }
    var selectedMethod by remember { mutableStateOf("Trust Bank PLC") }

    val lockedTotal = transactions
        .filter { it.status == "Locked & Mobilized" }
        .sumOf { it.amountBDT }

    val pendingTotal = transactions
        .filter { it.status == "Pending Verification" }
        .sumOf { it.amountBDT }

    val goalTotal = 10000000L // 1.0 Crore BDT
    val progressFraction = (lockedTotal.toFloat() / goalTotal.toFloat()).coerceIn(0f, 1f)

    fun formatBDT(amt: Long): String {
        return if (amt >= 10000000L) {
            "৳ ${"%.2f".format(amt / 10000000.0)} Cr"
        } else {
            "৳ ${"%.1f".format(amt / 100000.0)} Lakh"
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("approve_lock_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Executive Deal Queue Overview
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CyanGlowBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Rizum Khan — 90-Day Deal Queue",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "1-Tap Wire Reconcile & Retainer Mobilization",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                        Button(
                            onClick = { showDepositDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("record_wire_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Record Wire", color = ObsidianBg, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    // Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("৳1.00 Crore Goal Progress", fontSize = 11.sp, color = TextSecondary)
                            Text("${(progressFraction * 100).toInt()}% Mobilized", fontSize = 11.sp, color = EmeraldSuccess, fontWeight = FontWeight.Bold)
                        }
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier.fillMaxWidth().height(8.dp),
                            color = EmeraldSuccess,
                            trackColor = SurfaceDarkVariant
                        )
                    }

                    // KPI Counters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(Modifier.padding(10.dp)) {
                                Text("Verified & Locked", fontSize = 11.sp, color = EmeraldSuccess)
                                Text(formatBDT(lockedTotal), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldSuccess)
                            }
                        }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(Modifier.padding(10.dp)) {
                                Text("Pending Wire", fontSize = 11.sp, color = AmberWarning)
                                Text(formatBDT(pendingTotal), fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AmberWarning)
                            }
                        }
                    }
                }
            }
        }

        // 2. Corporate Bank Details Card
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.AccountBalance, contentDescription = null, tint = CyanAccent, modifier = Modifier.size(18.dp))
                        Text("Verified Corporate Wire Destination", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp)
                    }
                    Text("• Bank: ${MasterData.trustBankName} (${MasterData.trustBranch})", fontSize = 12.sp, color = TextSecondary)
                    Text("• Account: ${MasterData.trustAccountName} | No: ${MasterData.trustAccountNumber}", fontSize = 12.sp, color = CyanAccent, fontWeight = FontWeight.SemiBold)
                    Text("• Routing No: ${MasterData.trustRoutingNumber} | BIN: ${MasterData.nbrBin}", fontSize = 12.sp, color = TextSecondary)
                    Text("• MFS Merchant: bKash (${MasterData.bkashMerchant}) | Nagad (${MasterData.nagadMerchant})", fontSize = 12.sp, color = EmeraldSuccess)
                }
            }
        }

        // 3. Transactions List
        item {
            Text("Inbound Wire Queue (${transactions.size} Deals)", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
        }

        items(transactions) { tx ->
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (tx.status == "Locked & Mobilized") EmeraldSuccess else AmberWarning.copy(alpha = 0.5f)
                    )
                ),
                modifier = Modifier.fillMaxWidth().testTag("transaction_card_${tx.txCode}")
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(tx.clientName, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 15.sp)
                            Text(tx.packageType, fontSize = 12.sp, color = CyanAccent)
                        }
                        Text(formatBDT(tx.amountBDT), fontWeight = FontWeight.Bold, color = EmeraldSuccess, fontSize = 16.sp)
                    }

                    Text("Note: ${tx.referenceNote}", fontSize = 11.sp, color = TextSecondary)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (tx.status == "Locked & Mobilized") "🔒 ${tx.status}" else "⏳ ${tx.status}",
                            color = if (tx.status == "Locked & Mobilized") EmeraldSuccess else AmberWarning,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (tx.status == "Pending Verification") {
                            Button(
                                onClick = {
                                    transactions = transactions.map {
                                        if (it.id == tx.id) it.copy(
                                            status = "Locked & Mobilized",
                                            lockedTimestamp = "Just now"
                                        ) else it
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("lock_btn_${tx.txCode}")
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Lock & Mobilize", color = ObsidianBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDepositDialog) {
        AlertDialog(
            onDismissRequest = { showDepositDialog = false },
            title = { Text("Record Inbound Wire", color = TextPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("Client Name") },
                        modifier = Modifier.fillMaxWidth().testTag("input_client_name")
                    )
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = { amountText = it },
                        label = { Text("Amount (BDT)") },
                        modifier = Modifier.fillMaxWidth().testTag("input_wire_amount")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val parsed = amountText.toLongOrNull() ?: 500000L
                        val newTx = TransactionRecord(
                            id = "tx-${System.currentTimeMillis()}",
                            txCode = "TX-${(100..999).random()}",
                            clientName = clientName.ifBlank { "Sheltech Pilot" },
                            clientSlug = "custom",
                            packageType = "50% Retainer",
                            amountBDT = parsed,
                            serviceCategory = "Tier-1 RE",
                            paymentMethod = "Trust Bank PLC (00110466000494)",
                            status = "Pending Verification",
                            referenceNote = "Direct Wire Record by Founder"
                        )
                        transactions = listOf(newTx) + transactions
                        showDepositDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldSuccess),
                    modifier = Modifier.testTag("dialog_confirm_wire")
                ) {
                    Text("Record Wire", color = ObsidianBg)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDepositDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            },
            containerColor = NavySurface
        )
    }
}
