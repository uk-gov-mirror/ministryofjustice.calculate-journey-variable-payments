package uk.gov.justice.digital.hmpps.pecs.jpc.output

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import uk.gov.justice.digital.hmpps.pecs.jpc.TestConfig
import uk.gov.justice.digital.hmpps.pecs.jpc.calculator.PriceCalculator
import uk.gov.justice.digital.hmpps.pecs.jpc.pricing.Supplier
import uk.gov.justice.digital.hmpps.pecs.jpc.reporting.FilterParams
import java.io.File
import java.time.Clock
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ContextConfiguration(classes = [TestConfig::class])
internal class PricesSpreadsheetGeneratorTest(@Autowired @Qualifier(value = "spreadsheet-template") template: File, @Autowired private val clock: Clock) {

    private val calculator: PriceCalculator = mock()

    private val generator: PricesSpreadsheetGenerator = PricesSpreadsheetGenerator(template, clock)

    @Test
    internal fun `standards moves are called for Serco`() {
        whenever(calculator.standardPrices(any())).thenReturn(sequenceOf())
        whenever(calculator.redirectionPrices(any())).thenReturn(sequenceOf())

        val filter = FilterParams(Supplier.SERCO, LocalDate.now(clock), LocalDate.now(clock).plusDays(1))

        generator.generate(filter, calculator)

        verify(calculator).standardPrices(eq(FilterParams(filter.supplier, filter.movesFrom, filter.movesTo)))
    }
}