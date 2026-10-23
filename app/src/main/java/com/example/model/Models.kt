package com.example.model

enum class Language {
    EN, BN
}

enum class TargetCategory {
    ALL, TIER1, MID, ECOM
}

data class VerifiedTarget(
    val id: String,
    val slug: String,
    val name: String,
    val established: Int,
    val projectsCount: Int,
    val location: String,
    val category: TargetCategory,
    val phone: String,
    val flagshipProject: String,
    val unitSpec: String,
    val bdtPrice: String,
    val diagnosedVulnerability: String,
    val customSolution: String,
    val defaultCurrency: String,
    val currentStatus: String,
    val dealValueLakh: Double
)

data class BoardroomScript(
    val id: String,
    val title: String,
    val titleBn: String,
    val targetRole: String,
    val englishScript: String,
    val banglaScript: String,
    val strategicNote: String
)

data class TransactionRecord(
    val id: String,
    val txCode: String,
    val clientName: String,
    val clientSlug: String,
    val packageType: String,
    val amountBDT: Long,
    val serviceCategory: String,
    val paymentMethod: String,
    val status: String,
    val lockedTimestamp: String? = null,
    val referenceNote: String
)

data class RedmaPreset(
    val name: String,
    val katha: Int,
    val sqft: Int,
    val priceSqft: Int,
    val cancelledUnits: Int,
    val stalledUnits: Int,
    val bankOdCrore: Double,
    val description: String
)

data class CognitiveBattlecard(
    val id: String,
    val title: String,
    val chairmanQuote: String,
    val economicReality: String,
    val counterMove: String
)

data class InboundLead(
    val id: String,
    val developerSlug: String,
    val leadName: String,
    val diasporaLocation: String,
    val currency: String,
    val budgetLocal: String,
    val budgetBdt: String,
    val preferredProject: String,
    val responseTimeSec: Int,
    val aiScore: Int,
    val status: String,
    val timestamp: String
)

data class ProjectAsset(
    val id: String,
    val developerSlug: String,
    val projectName: String,
    val location: String,
    val unsoldSqft: Long,
    val avgPriceSqft: Long,
    val odRatePct: Double,
    val monthlyOdBleedCr: Double,
    val status: String
)
