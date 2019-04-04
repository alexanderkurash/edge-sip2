package org.folio.edge.sip2.parser;

import static java.lang.Character.valueOf;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.folio.edge.sip2.domain.messages.requests.ItemInformation;
import org.junit.jupiter.api.Test;

class ItemInformationMessageParserTests {
  @Test
  void testParse() {
    ItemInformationMessageParser parser =
        new ItemInformationMessageParser(valueOf('|'));
    final ZonedDateTime transactionDate =
        ZonedDateTime.now().truncatedTo(SECONDS);
    final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyyMMdd    HHmmss");
    final String transactionDateString = formatter.format(transactionDate);
    final ItemInformation itemInformation = parser.parse(
        transactionDateString + "ABSomeBook|AOuniversity_id|");

    assertEquals(transactionDate.getOffset(),
        itemInformation.getTransactionDate().getOffset());
    assertEquals("university_id", itemInformation.getInstitutionId());
    assertEquals("SomeBook", itemInformation.getItemIdentifier());
    assertNull(itemInformation.getTerminalPassword());
  }
}
