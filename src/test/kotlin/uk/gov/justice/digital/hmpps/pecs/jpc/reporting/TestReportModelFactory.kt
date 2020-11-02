package uk.gov.justice.digital.hmpps.pecs.jpc.reporting

import org.joda.time.DateTime
import uk.gov.justice.digital.hmpps.pecs.jpc.calculator.MovePriceType
import uk.gov.justice.digital.hmpps.pecs.jpc.location.LocationType
import uk.gov.justice.digital.hmpps.pecs.jpc.pricing.Supplier
import java.time.LocalDate
import java.time.LocalDateTime


val moveDate = LocalDate.of(2020, 9, 10)

fun moveModel(
        moveId: String = "M1",
        dropOffOrCancelledDateTime: LocalDateTime = moveDate.atStartOfDay().plusHours(10),
        journeys: MutableList<JourneyModel> = mutableListOf()
) = MoveModel(
        moveId = moveId,
        supplier = Supplier.SERCO,
        movePriceType = MovePriceType.STANDARD,
        status = MoveStatus.COMPLETED,
        reference = "REF1",
        moveDate = moveDate,
        fromNomisAgencyId = "WYI",
        fromSiteName = "from",
        fromLocationType = LocationType.PR,
        toNomisAgencyId = "GNI",
        toSiteName = "to",
        toLocationType = LocationType.PR,
        pickUpDateTime = moveDate.atStartOfDay(),
        dropOffOrCancelledDateTime = dropOffOrCancelledDateTime,
        notes = "some notes",
        prisonNumber = "PR101",
        vehicleRegistration = "reg100",
        journeys = journeys)

fun journeyModel(
        journeyId: String = "J1",
        fromNomisAgencyId: String = "WYI",
        toNomisAgencyId: String = "GNI",
        state: JourneyState = JourneyState.COMPLETED,
        billable: Boolean = true,
        pickUpDateTime: LocalDateTime? = moveDate.atStartOfDay(),
        dropOffDateTime: LocalDateTime? = moveDate.atStartOfDay().plusHours(10)
) = JourneyModel(
        journeyId = journeyId,
        state = state,
        moveId = moveModel().moveId,
        fromNomisAgencyId = fromNomisAgencyId,
        fromSiteName = "from",
        fromLocationType = LocationType.PR,
        toNomisAgencyId = toNomisAgencyId,
        toSiteName = "to",
        toLocationType = LocationType.PR,
        pickUpDateTime = pickUpDateTime,
        dropOffDateTime = dropOffDateTime,
        billable = billable,
        priceInPence = 100,
        vehicleRegistration = "REG200",
        notes = "some notes"
)