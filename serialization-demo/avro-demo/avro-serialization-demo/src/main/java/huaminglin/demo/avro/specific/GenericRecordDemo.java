package huaminglin.demo.avro.specific;

import example.avro.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;

public final class GenericRecordDemo {
  public static void writeUserBytes() throws IOException {
    GenericRecord genericRecord = new Record(User.SCHEMA$);
    genericRecord.put("name", "Charlie");
    genericRecord.put("favoriteColor", "blue");

    GenericDatumWriter writer = new GenericDatumWriter(User.SCHEMA$);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    BinaryEncoder binaryEncoder = EncoderFactory.get().binaryEncoder(baos, null);
    writer.write(genericRecord, binaryEncoder);
    binaryEncoder.flush();
    Path path = Paths.get("/tmp/user-data-generic.avro");
    Files.write(path, baos.toByteArray());
  }

  public static void parseUserFromBytes() throws IOException {
    InputStream is = GenericRecordDemo.class.getClassLoader().getResourceAsStream("data/user-data-generic.avro");
    byte[] bytes = is.readAllBytes();

    GenericDatumReader reader = new GenericDatumReader(User.SCHEMA$);
    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    Object record = reader.read(null, decoder);
    System.out.println(record);
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue"}
  }

  public static void main(String[] args) throws IOException {
    writeUserBytes();
    parseUserFromBytes();
  }
}
