data class Requirement(
    val id: RequirementId,
    val stakeholderId: StakeholderId,
    val title: String,
    val context: String,
    val collectedBy: EmployerId,
    val updatedBy: EmployerId,
)
