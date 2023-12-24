@JvmInline
value class EmployerId(val id: String) {
    fun asString() = id

    companion object {
        fun id(department: String, employer: String) = "employer-$department-$employer"
    }
}
