package huaminglin.demo.avro.specific;

import example.avro.User;
import example.avro.UserV2;
import java.io.IOException;
import java.io.InputStream;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;

public final class ParseOldDataDemo {

  public static void pareOldDataWithBuiltInSchema() throws IOException {
    InputStream is = FileUserManager.class.getClassLoader()
        .getResourceAsStream("data/user-schema-data.avro");

    // Schema provided as constructor parameter is the target Schema
//    DatumReader<GenericRecord> reader = new GenericDatumReader<>(User.SCHEMA$);
    DatumReader<GenericRecord> reader = new GenericDatumReader<>(UserV2.SCHEMA$);

    // DataFileStream has the from schema,
    // and it calls "this.reader.setSchema(this.header.schema);" to set it as from
    DataFileStream<GenericRecord> dataFileStream = new DataFileStream<>(is, reader);

    while (dataFileStream.hasNext()) {
      GenericRecord user = dataFileStream.next(null);
      System.out.println(user);
    }
    // {"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "favoriteMovie": "007"}
    // So the old data is converted to v2 successfully.
  }

  public static void pareDataWith2Schema() throws IOException {
    InputStream is = FileUserManager.class.getClassLoader()
        .getResourceAsStream("data/user-data-generic.avro");
    byte[] bytes = is.readAllBytes();

    // Schema provided as constructor parameter is the target Schema, which is used to generate the return value
//    DatumReader<GenericRecord> reader = new GenericDatumReader<>(User.SCHEMA$);
    DatumReader<GenericRecord> reader = new GenericDatumReader<>(UserV2.SCHEMA$);
    // The "setSchema()" is the from Schema, which is used to parse the source stream
    reader.setSchema(User.SCHEMA$);

    BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
    GenericRecord record = reader.read(null, decoder);
    System.out.println(record);
  }

  public static void main(String[] args) throws IOException {
    // GenericDatumReader: expected Schema and actual Schema
    // A Reader can config two Schema

//    pareOldDataWithBuiltInSchema();
    pareDataWith2Schema();
  }
}
