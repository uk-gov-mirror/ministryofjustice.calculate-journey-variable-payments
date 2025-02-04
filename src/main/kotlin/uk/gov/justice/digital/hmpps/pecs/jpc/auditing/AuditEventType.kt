package uk.gov.justice.digital.hmpps.pecs.jpc.auditing

enum class AuditEventType(val label: String) {
  DOWNLOAD_SPREADSHEET("Download spreadsheet"),
  JOURNEY_PRICE("Journey price"),
  JOURNEY_PRICE_BULK_UPDATE("Journey price bulk update"),
  LOCATION("Location"),
  LOG_IN("Log in"),
  LOG_OUT("Log out"),
  REPORTING_DATA_IMPORT("Reporting data import");

  companion object {
    /**
     * Attempts to map the supplied value to the supported audit event types.  Returns null if no match found.
     */
    fun map(value: String): AuditEventType? =
      values().firstOrNull { it.label.toUpperCase() == value.toUpperCase().trim() }
  }
}
