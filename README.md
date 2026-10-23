# AIS Enterprise Systems — Executive Command Center (Android)

An executive relief cockpit and liquidity mobilization platform built with **Kotlin** and **Jetpack Compose (Material 3)**.

## Project Structure & Architecture
- **Root Gradle & Version Catalog**: Modern Gradle Kotlin DSL (`settings.gradle.kts`, `build.gradle.kts`, `gradle/libs.versions.toml`).
- **Android App Module (`/app`)**:
  - `applicationId`: `com.aistudio.aiscommandcenter.qzkvtm`
  - `namespace`: `com.example`
  - Compile SDK 35, Min SDK 26, Target SDK 35
  - Jetpack Compose with Material 3 Dark theme tokens
  - Edge-to-edge support with insets handling

## Core Modules & Features
1. **WhatsApp Blitz (`WhatsAppScreen.kt`)**:
   - Client-branded experiential demo link generator
   - Verified target developer directory with financial metrics & live pitch generation
   - Diaspora currency calculator (USD, GBP, AED)
   - Category filtering (Tier-1 RE, Mid-Market RE, E-Com COD)
2. **Executive Capital Relief Cockpit & 8-Katha REDMA Audit (`ColdAuditScreen.kt`)**:
   - Interactive financial sliders (Unsold Sqft, Avg Price/Sqft, Bank Overdraft Interest Rate, Response Delay Hours)
   - Trapped capital, monthly OD bleed, daily burn rate, and 90-day syndicate unlock projections
   - 8-Katha REDMA Section 14 (90-day refund shock) and Section 15 (15% delay penalty) statutory liability auditor
   - Boardroom cognitive bias battlecards
3. **90-Day ৳1.00 Crore Deal Queue & Signature Lock (`ApproveLockScreen.kt`)**:
   - Deal pipeline tracking with progress towards ৳1.00 Crore target
   - 1-tap wire reconciliation and retainer mobilization
   - Corporate wire transfer destination details (Trust Bank PLC, bKash, Nagad)
4. **Form Mushak-6.3 Tax Invoice Generator (`MushakInvoiceScreen.kt`)**:
   - Official National Board of Revenue (NBR) Form Mushak-6.3 tax invoice compliance
   - Service Code S099.10 (5% statutory VAT calculation)
   - Cryptographically verifiable corporate BIN (004982103-0101)
5. **38-Second Diaspora Turnaround Sandbox (`ClientSandboxScreen.kt`)**:
   - Autonomous multi-step lead qualification simulation
   - Real-time milestone execution (Meta Cloud Webhook, AI Intent Classification, 4K Media Dispatch, 48-Hour Unit Hold)
6. **Document Vault (`DocumentVaultScreen.kt`)**:
   - 4-in-1 repository with Master Operating Manual, RE Turnaround Dossier, 1-Page MSA, and Mushak-6.3 specification
7. **Multi-Tenant Row Level Security (`WebhookRLSScreen.kt`)**:
   - PostgreSQL RLS schema simulation with SuperAdmin and isolated tenant access
   - Offline boardroom notes draft pad
