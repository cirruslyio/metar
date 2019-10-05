package io.cirrusly.metar.decoder;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import io.cirrusly.metar.ob.Metar;

public class MetarDecoderTest {

  /**
   * Represents a row in the METAR CSV.
   */
  static class TestRecord extends Metar {
    private final int id;

    /**
     * Initializes the record with the required immutable fields.
     *
     * @param id The row identifier.
     * @param encoded The encoded observation.
     */
    public TestRecord(int id, String encoded, String station) {
      super(encoded, station);
      this.id = id;
    }

    public int getId() {
      return id;
    }
  }

  @Test
  public void testMetars() throws IOException, DecoderError {
    for (TestRecord record : readCsvRecords()) {
      Metar metar = (Metar) DecoderFactory.decode(record.getEncoded());
      assertEquals(record.getEncoded(), metar.getEncoded());
      assertEquals(record.getStation(), metar.getStation());
      assertEquals(record.getDay(), metar.getDay());
      assertEquals(record.getTime(), metar.getTime());
      assertEquals(record.getReportModifier(), metar.getReportModifier());
      assertEquals(record.getWindDirection(), metar.getWindDirection());
      assertEquals(record.getWindSpeed(), metar.getWindSpeed());
      assertEquals(record.getWindGust(), metar.getWindGust());
    }
  }

  public List<TestRecord> readCsvRecords() throws IOException {
    List<TestRecord> records = new ArrayList<>();
    for (CSVRecord csvRecord : parseCsv()) {
      TestRecord record =
          new TestRecord(Integer.parseInt(csvRecord.get("id")), csvRecord.get("encoded"),
              csvRecord.get("station"));
      record.setDay(csvRecord.get("day"));
      record.setTime(csvRecord.get("time"));
      record.setReportModifier(emptyToNull(csvRecord.get("report_modifier")));
      record.setWindDirection(csvRecord.get("wind_direction"));
      record.setWindSpeed(csvRecord.get("wind_speed"));
      record.setWindGust(emptyToNull(csvRecord.get("wind_gust")));
      records.add(record);
    }
    return records;
  }

  private String emptyToNull(String value) {
    if (value != null && value.isEmpty()) {
      return null;
    }
    return value;
  }


  public CSVParser parseCsv() throws IOException {
    Reader rdr = new StringReader(readCsv());
    return CSVParser.parse(rdr, CSVFormat.EXCEL.withHeader());
  }

  public static String readCsv() throws IOException {
    return FileUtils.readFileToString(
        new File(MetarDecoderTest.class.getResource("metars.csv").getFile()), "UTF-8");
  }
}
