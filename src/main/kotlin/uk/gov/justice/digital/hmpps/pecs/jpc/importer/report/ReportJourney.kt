package uk.gov.justice.digital.hmpps.pecs.jpc.importer.report

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class ReportJourney(

        @get: NotBlank(message = "id cannot be blank")
        val id: String,

        @EventDateTime
        @Json(name = "updated_at")
        val updatedAt: LocalDateTime,

        @Json(name = "move_id")
        val moveId: String,

        val billable: Boolean,

        @get: NotBlank(message = "state cannot be blank")
        val state: String,

        @get: NotBlank(message = "supplier cannot be blank")
        val supplier: String,

        @EventDateTime
        @Json(name = "client_timestamp")
        val clientTimestamp: LocalDateTime? = null,

        @Json(name = "vehicle_registration")
        val vehicleRegistration: String?,

        @Json(name = "from_location")
        val fromNomisAgencyId: String,

        @Json(name = "to_location")
        val toNomisAgencyId: String? = null
)

{
    fun stateIsAnyOf(vararg states: JourneyState) = states.map{it.name}.contains(state.toUpperCase())

    companion object {
        fun fromJson(json: String): ReportJourney? {
            return Klaxon().
            fieldConverter(EventDateTime::class, dateTimeConverter).
            parse<ReportJourney>(json)
        }
    }
}

enum class JourneyState() {
    CANCELLED,
    COMPLETED;

    companion object{
        fun valueOfCaseInsensitive(value: String): JourneyState {
            return valueOf(value.toUpperCase())
        }
    }
}