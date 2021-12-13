package huaminglin.demo.avro.specific;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.Schema.Parser;

public class AvroDowngradeDemo {
  public static byte[] writeWithNewSchema() throws IOException {
    InputStream inputStream = AvroDowngradeDemo.class.getClassLoader()
        .getResourceAsStream("avro/v2/userV2.avsc");
    Schema newSchema = new Parser().parse(inputStream);
    GenericRecord genericRecord = new Record(newSchema);
    genericRecord.put("name", "Charlie");
    genericRecord.put("favoriteColor", "blue");
    GenericData.EnumSymbol symbol = new GenericData.EnumSymbol(newSchema, "third");
    genericRecord.put("sex", symbol);
//    genericRecord.put("favoriteMovie", "blue");

    GenericDatumWriter writer = new GenericDatumWriter(newSchema);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null);
    writer.write(genericRecord, binaryEncoder);
    binaryEncoder.flush();
    return baos.toByteArray();
  }

  public static void readToNewSchema(byte[] bytes) throws IOException {
    InputStream inputStream = AvroDowngradeDemo.class.getClassLoader()
        .getResourceAsStream("avro/v2/userV2.avsc");
    Schema newSchema = new Parser().parse(inputStream);
    GenericDatumReader reader = new GenericDatumReader(newSchema);
    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    Object record = reader.read(null, decoder);
    System.out.println(record);
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "favoriteMovie": null}
    // The favoriteMovie is null; The default definition is used for schema upgrade from old to new.
  }

  public static void readToOldSchema(byte[] bytes) throws IOException {
    Schema newSchema;
    {
      InputStream inputStream = AvroDowngradeDemo.class.getClassLoader()
          .getResourceAsStream("avro/v2/userV2.avsc");
      newSchema = new Parser().parse(inputStream);
    }
    Schema oldSchema;
    {
      InputStream inputStream = AvroDowngradeDemo.class.getClassLoader()
          .getResourceAsStream("avro/user.avsc");
      oldSchema = new Parser().parse(inputStream);
    }
    GenericDatumReader reader = new GenericDatumReader(oldSchema);
    reader.setSchema(newSchema);
    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    Object record = reader.read(null, decoder);
    System.out.println(record);
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue"}
  }

  public static void main(String[] args) throws IOException {
    byte[] bytes = writeWithNewSchema();
    readToNewSchema(bytes);
    readToOldSchema(bytes);
  }
}
