package org.folio.edge.sip2.parser;

import static java.lang.Character.valueOf;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.folio.edge.sip2.domain.messages.requests.EndPatronSession;
import org.junit.jupiter.api.Test;

class EndPatronSessionMessageParserTests {
  @Test
  void testParse() {
    EndPatronSessionMessageParser parser =
        new EndPatronSessionMessageParser(valueOf('|'));
    final ZonedDateTime transactionDate =
        ZonedDateTime.now().truncatedTo(SECONDS);
    final DateTimeFormatter formatter = DateTimeFormatter
        .ofPattern("yyyyMMdd    HHmmss");
    final String transactionDateString = formatter.format(transactionDate);
    final EndPatronSession endPatronSession = parser.parse(
        transactionDateString + "AApatron_id|AD1234|AC|AOuniversity_id|");

    assertEquals(transactionDate.getOffset(),
        endPatronSession.getTransactionDate().getOffset());
    assertEquals("university_id", endPatronSession.getInstitutionId());
    assertEquals("patron_id", endPatronSession.getPatronIdentifier());
    assertEquals("", endPatronSession.getTerminalPassword());
    assertEquals("1234", endPatronSession.getPatronPassword());
  }
}