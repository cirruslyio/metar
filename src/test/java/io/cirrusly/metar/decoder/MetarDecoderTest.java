package io.cirrusly.metar.decoder;

import static org.junit.Assert.assertEquals;
import io.cirrusly.metar.ob.Metar;
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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
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

    public String getDescription() {
      return "Row " + id + " " + getStation() + " " + getDay() + getTime();
    }
  }

  private TestRecord record;

  public MetarDecoderTest(TestRecord record) {
    this.record = record;
  }

  @Test
  public void testMetars() throws IOException, DecoderError {
    Metar metar = (Metar) DecoderFactory.decode(record.getEncoded());
    assertEquals(record.getDescription(), record.getEncoded(), metar.getEncoded());
    assertEquals(record.getDescription(), record.getStation(), metar.getStation());
    assertEquals(record.getDescription(), record.getDay(), metar.getDay());
    assertEquals(record.getDescription(), record.getTime(), metar.getTime());
    assertEquals(record.getDescription(), record.getReportModifier(), metar.getReportModifier());
    assertEquals(record.getDescription(), record.getWindDirection(), metar.getWindDirection());
    assertEquals(record.getDescription(), record.getWindSpeed(), metar.getWindSpeed());
    assertEquals(record.getDescription(), record.getWindGust(), metar.getWindGust());
    assertEquals(record.getDescription(), record.getWindVariability(), metar.getWindVariability());
    assertEquals(record.getDescription(), record.getVisibility(), metar.getVisibility());
  }

  @Parameters
  public static List<TestRecord> readCsvRecords() throws IOException {
    List<TestRecord> records = new ArrayList<>();
    for (CSVRecord csvRecord : parseCsv()) {
      TestRecord record =
          new TestRecord(Integer.parseInt(csvRecord.get("id")), csvRecord.get("encoded"),
              csvRecord.get("station"));
      record.setDay(csvRecord.get("day"));
      record.setTime(csvRecord.get("time"));
      record.setReportModifier(emptyToNull(csvRecord.get("report_modifier")));
      record.setWindDirection(emptyToNull(csvRecord.get("wind_direction")));
      record.setWindSpeed(emptyToNull(csvRecord.get("wind_speed")));
      record.setWindGust(emptyToNull(csvRecord.get("wind_gust")));
      record.setWindVariability(emptyToNull(csvRecord.get("wind_variability")));
      record.setVisibility(emptyToNull(csvRecord.get("visibility")));
      records.add(record);
    }
    return records;
  }

  private static String emptyToNull(String value) {
    if (value != null && value.isEmpty()) {
      return null;
    }
    return value;
  }


  public static CSVParser parseCsv() throws IOException {
    Reader rdr = new StringReader(readCsv());
    return CSVParser.parse(rdr, CSVFormat.EXCEL.withHeader());
  }

  public static String readCsv() throws IOException {
    return FileUtils.readFileToString(
        new File(MetarDecoderTest.class.getResource("metars.csv").getFile()), "UTF-8");
  }
}
