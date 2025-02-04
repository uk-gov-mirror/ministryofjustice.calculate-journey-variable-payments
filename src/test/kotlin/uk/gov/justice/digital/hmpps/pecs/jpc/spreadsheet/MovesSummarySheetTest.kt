package uk.gov.justice.digital.hmpps.pecs.jpc.spreadsheet

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import uk.gov.justice.digital.hmpps.pecs.jpc.TestConfig
import uk.gov.justice.digital.hmpps.pecs.jpc.config.JPCTemplateProvider
import uk.gov.justice.digital.hmpps.pecs.jpc.move.MoveType
import uk.gov.justice.digital.hmpps.pecs.jpc.move.MovesSummary
import uk.gov.justice.digital.hmpps.pecs.jpc.move.defaultMoveDate10Sep2020
import uk.gov.justice.digital.hmpps.pecs.jpc.price.Supplier
import uk.gov.justice.digital.hmpps.pecs.jpc.service.MoveTypeSummaries

@SpringJUnitConfig(TestConfig::class)
internal class MovesSummarySheetTest(@Autowired private val template: JPCTemplateProvider) {

  private val workbook: Workbook = XSSFWorkbook(template.get())

  @Test
  internal fun `summary prices`() {

    val standardSummary = MovesSummary(MoveType.STANDARD, 10.0, 2, 1, 200)
    val longHaulSummary = MovesSummary(MoveType.LONG_HAUL, 50.0, 10, 10, 400)
    val summaries = MoveTypeSummaries(1, listOf(standardSummary, longHaulSummary, MovesSummary(), MovesSummary(), MovesSummary(), MovesSummary()))

    val sheet = SummarySheet(
      workbook,
      PriceSheet.Header(
        defaultMoveDate10Sep2020,
        ClosedRangeLocalDate(defaultMoveDate10Sep2020, defaultMoveDate10Sep2020),
        Supplier.SERCO
      )
    )
    sheet.writeSummaries(summaries)

    // Standard move summaries at row 9
    assertCellEquals(sheet, 9, 1, 10.0)
    assertCellEquals(sheet, 9, 2, 2.0)
    assertCellEquals(sheet, 9, 3, 1.0)
    assertCellEquals(sheet, 9, 4, 2.0)

    // Long haul summaries at row 12
    assertCellEquals(sheet, 12, 1, 50.0)

    // summary of summaries at row 25
    assertCellEquals(sheet, 28, 1, 60.0) // overall %
    assertCellEquals(sheet, 28, 2, 12.0) // overall volume
    assertCellEquals(sheet, 28, 3, 11.0) // overall volume unpriced
    assertCellEquals(sheet, 28, 4, 6.0) // overall total in £
  }
}
