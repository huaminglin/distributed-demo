package huaminglin.demo.avro.specific;

import example.avro.User;
import example.avro.UserV2;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;

public class ParseNewDataDemo {
  public static byte[] writeUserV2Bytes() throws IOException {
    GenericRecord genericRecord = new Record(UserV2.SCHEMA$);
    genericRecord.put("name", "Charlie");
    genericRecord.put("favoriteColor", "blue");
//    genericRecord.put("favoriteMovie", "blue");

    GenericDatumWriter writer = new GenericDatumWriter(UserV2.SCHEMA$);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null);
    writer.write(genericRecord, binaryEncoder);
    binaryEncoder.flush();
    return baos.toByteArray();
  }

  public static void parseUserV2FromBytes(byte[] bytes) throws IOException {
    GenericDatumReader reader = new GenericDatumReader(UserV2.SCHEMA$);
    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    Object record = reader.read(null, decoder);
    System.out.println(record);
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "favoriteMovie": null}
    // The favoriteMovie is null; The default definition is used for schema upgrade from old to new.
  }

  public static void parseUserV1FromV2Bytes(byte[] bytes) throws IOException {
    GenericDatumReader reader = new GenericDatumReader(User.SCHEMA$);
    reader.setSchema(UserV2.SCHEMA$);
    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    Object record = reader.read(null, decoder);
    System.out.println(record);
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue"}
  }

  public static void main(String[] args) throws IOException {
    byte[] bytes = writeUserV2Bytes();
    parseUserV2FromBytes(bytes);
    parseUserV1FromV2Bytes(bytes);
  }
}
