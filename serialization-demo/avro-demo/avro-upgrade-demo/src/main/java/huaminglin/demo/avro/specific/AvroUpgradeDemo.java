package huaminglin.demo.avro.specific;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;

public final class AvroUpgradeDemo {

  public static byte[] writeDataWithOldSchema() throws IOException {
    Schema oldSchema;
    {
      InputStream inputStream = AvroUpgradeDemo.class.getClassLoader()
          .getResourceAsStream("avro/user.avsc");
      oldSchema = new Parser().parse(inputStream);
    }
    GenericRecord genericRecord = new Record(oldSchema);
    genericRecord.put("name", "Charlie");
    genericRecord.put("favoriteColor", "blue");
    GenericData.EnumSymbol symbol = new GenericData.EnumSymbol(oldSchema, "male");
    genericRecord.put("sex", symbol);

    GenericDatumWriter writer = new GenericDatumWriter(oldSchema);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null);
    writer.write(genericRecord, binaryEncoder);
    binaryEncoder.flush();
    return baos.toByteArray();
  }

  public static void parseOldDataWithNewSchema(byte[] bytes) throws IOException {
    Schema newSchema;
    {
      InputStream inputStream = AvroUpgradeDemo.class.getClassLoader()
          .getResourceAsStream("avro/v2/userV2.avsc");
      newSchema = new Parser().parse(inputStream);
    }
    Schema oldSchema;
    {
      InputStream inputStream = AvroUpgradeDemo.class.getClassLoader()
          .getResourceAsStream("avro/user.avsc");
      oldSchema = new Parser().parse(inputStream);
    }
    DatumReader<GenericRecord> reader = new GenericDatumReader<>(newSchema);
    reader.setSchema(oldSchema);

    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    GenericRecord record = reader.read(null, decoder);
    System.out.println(record);
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "male", "favoriteMovie": "007"}
    // The new field "favoriteMovie" gets the default value as expected.
  }

  public static void main(String[] args) throws IOException {
    byte[] bytes = writeDataWithOldSchema();
    parseOldDataWithNewSchema(bytes);
  }
}
