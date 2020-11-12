package uk.gov.justice.digital.hmpps.pecs.jpc.importer.report

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class EventTest {

    @Test
    fun `get latest by type returns the latest event`(){
        val moveCancelFirst = reportMoveEventFactory() // MOVE_CANCEL
        val moveCancelLast= moveCancelFirst.copy(id = "ME2", occurredAt = moveCancelFirst.occurredAt.plusHours(3))

        assertThat(Event.getLatestByType(listOf(moveCancelLast, moveCancelFirst), EventType.MOVE_CANCEL)).isEqualTo(moveCancelLast)
    }

    @Test
    fun `get latest by type returns null if event not present`(){
        val moveCancel = reportMoveEventFactory() // MOVE_CANCEL event

        assertThat(Event.getLatestByType(listOf(moveCancel), EventType.MOVE_COMPLETE)).isNull()
    }
}