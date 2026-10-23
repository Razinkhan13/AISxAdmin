package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
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
fun WebhookRLSScreen() {
    var activeTenant by remember { mutableStateOf("superadmin") }
    var offlineNotes by remember {
        mutableStateOf(
            """# Dhaka Traffic Boardroom Offline Notes
- Met with Sheltech Chairman at Panthapath.
- Discussed REDMA Sec 14 refund exposure on Bithika Banani.
- Chairman agreed in principle to 50% mobilization retainer (৳23L) via Trust Bank.
- Follow up WhatsApp sent with live sandbox: ?demo=sheltech#sandbox"""
        )
    }
    var copiedSchema by remember { mutableStateOf(false) }

    val sqlSchema = """
-- ==============================================================================
-- AIS ENTERPRISE SYSTEMS — MULTI-TENANT ROW-LEVEL SECURITY (RLS) SCHEMA
-- ==============================================================================
ALTER TABLE public.developers ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.project_assets ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.inbound_leads ENABLE ROW LEVEL SECURITY;

-- SuperAdmin Policy: Founder Rizum Khan sees all tenants
CREATE POLICY superadmin_full_access_leads ON public.inbound_leads
  FOR ALL
  USING (coalesce(auth.jwt() ->> 'role', '') = 'superadmin');

-- Tenant Isolation: Developer tenants can ONLY view their own records
CREATE POLICY tenant_isolation_leads ON public.inbound_leads
  FOR ALL
  USING (tenant_slug = current_setting('request.jwt.claims', true)::json->>'tenant_slug');
    """.trimIndent()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .padding(16.dp)
            .testTag("webhook_rls_scroll"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Tenant Isolation Header
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = NavySurface),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(EmeraldGlowBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Security, contentDescription = null, tint = EmeraldSuccess)
                        Text(
                            text = "Supabase PostgreSQL Row Level Security (RLS)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Text(
                        text = "Zero-Cost Multi-Tenant Data Quarantine: SuperAdmin Cockpit vs Isolated Developer Tenant.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = activeTenant == "superadmin",
                            onClick = { activeTenant = "superadmin" },
                            label = { Text("👑 SuperAdmin (Rizum Khan)", fontSize = 11.sp) },
                            modifier = Modifier.testTag("tenant_superadmin")
                        )
                        FilterChip(
                            selected = activeTenant == "sheltech",
                            onClick = { activeTenant = "sheltech" },
                            label = { Text("🏢 Sheltech Tenant", fontSize = 11.sp) },
                            modifier = Modifier.testTag("tenant_sheltech")
                        )
                    }
                }
            }
        }

        // 2. Offline Notes Pad
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = AmberWarning, modifier = Modifier.size(16.dp))
                        Text("Dhaka Boardroom Offline Draft Pad", fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp)
                    }
                    OutlinedTextField(
                        value = offlineNotes,
                        onValueChange = { offlineNotes = it },
                        modifier = Modifier.fillMaxWidth().height(120.dp).testTag("offline_pad_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = CardBorder
                        )
                    )
                }
            }
        }

        // 3. PostgreSQL RLS Schema
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkVariant),
                shape = RoundedCornerShape(14.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(CardBorder)),
                modifier = Modifier.fillMaxWidth().testTag("sql_schema_card")
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Production PostgreSQL RLS Schema", fontWeight = FontWeight.Bold, color = CyanAccent, fontSize = 13.sp)
                        Button(
                            onClick = { copiedSchema = true },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("copy_sql_schema_btn")
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = null, tint = ObsidianBg, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(if (copiedSchema) "Copied!" else "Copy SQL", color = ObsidianBg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Text(
                        text = sqlSchema,
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}
