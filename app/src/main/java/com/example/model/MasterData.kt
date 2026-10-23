package com.example.model

object MasterData {
    val corporateBrandName = "AIS Enterprise Systems (Army IBA Specialist)"
    val founderName = "Rizum Khan"
    val founderTitle = "Founder & Final Signatory"
    val hqAddress = "15 Mayakanon, Basaboo, Khilgaon, Dhaka-1214, Bangladesh"

    val trustBankName = "Trust Bank PLC"
    val trustBranch = "Jalalabad Cantonment Branch"
    val trustAccountName = "AIS Enterprise Systems"
    val trustAccountNumber = "00110466000494"
    val trustRoutingNumber = "240260494"

    val bkashMerchant = "01715219176"
    val nagadMerchant = "01708761298"

    val nbrBin = "004982103-0101"
    val nbrServiceCode = "S099.10"
    val nbrServiceDescription = "IT Enabled Services & Hosted ERP Software"
    val vatRatePct = 5.0

    val warranty = "If AIS does not cut inbound lead response time to sub-4 minutes (including 24/7 UK/US/Gulf NRB diaspora inquiries) and qualify at least 5x our fee in verified buyer pipeline within 60 days, we return 100% of our fee and you retain all installed workflows, code, and systems."

    val verifiedTargets = listOf(
        VerifiedTarget(
            id = "t1-1",
            slug = "sheltech",
            name = "Sheltech (Pvt.) Ltd.",
            established = 1988,
            projectsCount = 170,
            location = "Panthapath, Dhaka",
            category = TargetCategory.TIER1,
            phone = "+8801711590123",
            flagshipProject = "Sheltech Bithika (Banani/Gulshan)",
            unitSpec = "3,150 sq ft • Luxury Penthouse / Simplex",
            bdtPrice = "৳ 4.85 Cr",
            diagnosedVulnerability = "Cited for 25% booking cancellations; REDMA Sec 14 (90% refund in 90d) drains active site cash while Sec 15 adds 15% delay interest.",
            customSolution = "Ring-fence 1 condominium in Trust Bank Escrow; subdivide cancelled >2,000 sqft flats into 800–1,200 sqft units; 18-mo build.",
            defaultCurrency = "USD",
            currentStatus = "Boardroom Scheduled",
            dealValueLakh = 46.0
        ),
        VerifiedTarget(
            id = "t1-2",
            slug = "concord",
            name = "Concord Real Estate Ltd.",
            established = 1973,
            projectsCount = 1200,
            location = "Gulshan-2, Dhaka",
            category = TargetCategory.TIER1,
            phone = "+8801713002244",
            flagshipProject = "Concord Grand (Gulshan-2)",
            unitSpec = "3,300 sq ft • Ultra Luxury Tower",
            bdtPrice = "৳ 5.20 Cr",
            diagnosedVulnerability = "Capital trapped in large towers due to 20–40% DAP FAR cuts and 25–30% rebar/cement surge despite AA+ rating.",
            customSolution = "Pair Concord ready-mix with aluminum formwork; spin off Grade-A commercial floors into a Private REIT SPV.",
            defaultCurrency = "GBP",
            currentStatus = "Pitch Sent",
            dealValueLakh = 46.0
        ),
        VerifiedTarget(
            id = "t1-3",
            slug = "bti",
            name = "bti (Building Tech & Ideas)",
            established = 1984,
            projectsCount = 400,
            location = "Gulshan-2, Dhaka",
            category = TargetCategory.TIER1,
            phone = "+8801313401405",
            flagshipProject = "bti Celebration Point (Gulshan-2)",
            unitSpec = "3,200 sq ft • Smart Signature Suite",
            bdtPrice = "৳ 5.10 Cr",
            diagnosedVulnerability = "Velocity fell from 5–8 to 1–2 units/mo; holding unsold >2,200 sqft units costs BDT 1,800–3,200/sqft/yr in OD drag.",
            customSolution = "Bulk-liquidate 12–15 stagnant luxury units at 12–18% corporate discount; connect ISO CRM to NRB NRTA 75% mortgages.",
            defaultCurrency = "USD",
            currentStatus = "Pitch Sent",
            dealValueLakh = 46.0
        ),
        VerifiedTarget(
            id = "t1-4",
            slug = "dominno",
            name = "Dom-Inno Developers Ltd.",
            established = 2002,
            projectsCount = 253,
            location = "Gulshan-2, Dhaka",
            category = TargetCategory.TIER1,
            phone = "+8801711545678",
            flagshipProject = "Dom-Inno Terreno (Dhanmondi/Gulshan)",
            unitSpec = "2,650 sq ft • Boutique Residencies",
            bdtPrice = "৳ 3.70 Cr",
            diagnosedVulnerability = "Prime Dhanmondi/Uttara boutique sites squeezed by BDT 2Cr+ signing outlays & BDT 1–2L/mo displacement rent.",
            customSolution = "Deploy the 8-Katha Empirical Sandbox on 1 boutique site; cut build time 45% & save 38% displacement rent.",
            defaultCurrency = "GBP",
            currentStatus = "Locked & Mobilized",
            dealValueLakh = 12.0
        ),
        VerifiedTarget(
            id = "t1-5",
            slug = "sel",
            name = "The Structural Engineers (SEL)",
            established = 1983,
            projectsCount = 236,
            location = "Panthapath, Dhaka",
            category = TargetCategory.TIER1,
            phone = "+8801711698765",
            flagshipProject = "SEL Nibash (Dhanmondi/Panthapath)",
            unitSpec = "2,450 sq ft • Engineered Homes",
            bdtPrice = "৳ 3.40 Cr",
            diagnosedVulnerability = "2021–2023 fixed-price pre-sales eroded by rebar hitting Tk 108k/MT (+25–30% surge).",
            customSolution = "Execute factory-gate indexed supply contracts + deploy Phase IV Digital Escrow ERP (<2% waste).",
            defaultCurrency = "AED",
            currentStatus = "Pitch Sent",
            dealValueLakh = 46.0
        ),
        VerifiedTarget(
            id = "mid-1",
            slug = "edison",
            name = "Edison Real Estate Ltd.",
            established = 2011,
            projectsCount = 22,
            location = "Bashundhara R/A, Dhaka",
            category = TargetCategory.MID,
            phone = "+8801708080822",
            flagshipProject = "Edison Amrita (Bashundhara R/A)",
            unitSpec = "2,150 sq ft • Contemporary Living",
            bdtPrice = "৳ 2.45 Cr",
            diagnosedVulnerability = "High Facebook ad budget burn without instant sub-4m lead qualification; prospective buyers buy from nearby competitors.",
            customSolution = "Deploy AIS 38s Instant Qualification Webhook on FB Leads + Auto WhatsApp Catalog with 3D walkthrough.",
            defaultCurrency = "USD",
            currentStatus = "Pitch Sent",
            dealValueLakh = 1.4
        ),
        VerifiedTarget(
            id = "ecom-1",
            slug = "gorurghash",
            name = "Gorur Ghash",
            established = 2014,
            projectsCount = 1,
            location = "Turag, Dhaka (1,200+ Daily Parcels)",
            category = TargetCategory.ECOM,
            phone = "+8801713222653",
            flagshipProject = "Gen-Z Apparel & Streetwear COD Protection",
            unitSpec = "1,200+ Daily Dispatches • 18% COD Return Rate",
            bdtPrice = "৳ 25,000 Pilot",
            diagnosedVulnerability = "18% to 24% fake orders and customer refusal on delivery with courier services, burning return fees.",
            customSolution = "AIS E-Com COD Shield: Instant WhatsApp 1-tap OTP verification before dispatch, cutting returns by 40%.",
            defaultCurrency = "USD",
            currentStatus = "Locked & Mobilized",
            dealValueLakh = 0.35
        ),
        VerifiedTarget(
            id = "ecom-2",
            slug = "fabrilife",
            name = "Fabrilife",
            established = 2016,
            projectsCount = 1,
            location = "Mirpur-1 HQ, Dhaka (2,500+ Daily Parcels)",
            category = TargetCategory.ECOM,
            phone = "+8801332502900",
            flagshipProject = "High-Volume Apparel & Merchandise COD Shield",
            unitSpec = "2,500+ Daily Dispatches • BDT 4.5L/mo Return Losses",
            bdtPrice = "৳ 30,000 Pilot",
            diagnosedVulnerability = "High volume dispatch to Chittagong, Sylhet & rural Upazilas where COD rejection burns ৳150/parcel.",
            customSolution = "Automated WhatsApp Voice/Text confirmation bot that triggers Steadfast API webhook ONLY upon customer confirmation.",
            defaultCurrency = "USD",
            currentStatus = "Pitch Sent",
            dealValueLakh = 0.40
        )
    )

    val preloadedTransactions = listOf(
        TransactionRecord(
            id = "tx-1",
            txCode = "TX-901",
            clientName = "Sheltech (Pvt.) Ltd.",
            clientSlug = "sheltech",
            packageType = "50% Tier-1 Turnaround Retainer",
            amountBDT = 2300000L,
            serviceCategory = "Tier-1 RE",
            paymentMethod = "Trust Bank PLC (00110466000494)",
            status = "Pending Verification",
            referenceNote = "Sheltech Bithika Banani 1-Site Pilot • Awaiting Chairman Wire Transfer Sign-off"
        ),
        TransactionRecord(
            id = "tx-2",
            txCode = "TX-902",
            clientName = "Dom-Inno Developers Ltd.",
            clientSlug = "dominno",
            packageType = "50% 8-Katha Sandbox Retainer",
            amountBDT = 600000L,
            serviceCategory = "Tier-1 RE",
            paymentMethod = "Trust Bank PLC (00110466000494)",
            status = "Locked & Mobilized",
            lockedTimestamp = "2026-10-18 16:45 BD Time",
            referenceNote = "Dom-Inno Terreno JVA 8-Katha Sandbox • Mobilization Approved by Rizum Khan"
        ),
        TransactionRecord(
            id = "tx-3",
            txCode = "TX-903",
            clientName = "bti (Building Tech & Ideas)",
            clientSlug = "bti",
            packageType = "50% Tier-1 Turnaround Retainer",
            amountBDT = 2300000L,
            serviceCategory = "Tier-1 RE",
            paymentMethod = "Trust Bank PLC (00110466000494)",
            status = "Pending Verification",
            referenceNote = "Celebration Point Gulshan-2 Stagnant Unit Liquidation Desk • Board Meeting Thu"
        ),
        TransactionRecord(
            id = "tx-4",
            txCode = "TX-904",
            clientName = "Gorur Ghash",
            clientSlug = "gorurghash",
            packageType = "E-Com COD Shield Fast-Cash Pilot",
            amountBDT = 25000L,
            serviceCategory = "E-Com COD",
            paymentMethod = "bKash (01715219176)",
            status = "Locked & Mobilized",
            lockedTimestamp = "2026-10-19 11:20 BD Time",
            referenceNote = "Turag Hub 1,200 Daily Dispatches • 1-Tap WhatsApp OTP Activated"
        )
    )

    val redmaPresets = listOf(
        RedmaPreset(
            name = "Dom-Inno 8-Katha Baseline",
            katha = 8,
            sqft = 2200,
            priceSqft = 20000,
            cancelledUnits = 1,
            stalledUnits = 1,
            bankOdCrore = 3.1878,
            description = "Empirical model for prime Dhanmondi/Gulshan boutique site with typical 8-katha footprint."
        ),
        RedmaPreset(
            name = "Sheltech Bithika High-Rise",
            katha = 14,
            sqft = 3150,
            priceSqft = 22500,
            cancelledUnits = 2,
            stalledUnits = 3,
            bankOdCrore = 6.5,
            description = "Large-footprint luxury residential project with multi-unit cancellation exposure."
        ),
        RedmaPreset(
            name = "Navana Strand Waterfront",
            katha = 16,
            sqft = 3400,
            priceSqft = 25000,
            cancelledUnits = 2,
            stalledUnits = 2,
            bankOdCrore = 8.2,
            description = "High-ticket waterfront tower under heavy bank overdraft drag."
        )
    )

    val cognitiveBattlecards = listOf(
        CognitiveBattlecard(
            id = "bias-1",
            title = "The Zamindar Fallacy (জমিদার মানসিকতা)",
            chairmanQuote = "“I have been developing land for 35 years. I will never discount my units by even 1%. Real estate in Dhaka always goes up. I can wait 3 years for the right buyer.”",
            economicReality = "At 14.5% bank overdraft interest compounding quarterly, holding an unsold ৳4.85 Crore unit for 24 months burns over ৳1.52 Crore in pure interest bleed. A strategic 12% discount saves nearly ৳1 Crore net while extinguishing loan liability.",
            counterMove = "“Chairman Sir, holding isn’t free—the bank charges 14% while your concrete stands idle. An AIS syndicate liquidity block preserves your headline price while unlocking instant cash to clear expensive overdraft lines.”"
        ),
        CognitiveBattlecard(
            id = "bias-2",
            title = "The Cyclical Denial Trap (চক্রাকার অস্বীকৃতি)",
            chairmanQuote = "“This market slowdown is just temporary friction. In 6 months, everything will bounce back to 2021 levels.”",
            economicReality = "The structural shift is permanent: Bangladesh Bank repo rates went from 4.5% to 10.0%, Bank OD caps of 9% were abolished to 14–17%, and DAP 2022 permanently slashed built-up FAR by 20–40%. Hope is not a treasury strategy.",
            counterMove = "“The interest rate regime and FAR rules have fundamentally re-priced Dhaka real estate. Waiting without cash flow increases the risk of REDMA Section 14 statutory refund court orders.”"
        )
    )
}
