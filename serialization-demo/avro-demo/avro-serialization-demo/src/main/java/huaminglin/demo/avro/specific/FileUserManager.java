package huaminglin.demo.avro.specific;

import example.avro.User;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

public final class FileUserManager {

  public static void writeUser(String filePath) throws IOException {
    User user = User.newBuilder()
        .setName("Charlie")
        .setFavoriteColor("blue")
        .setFavoriteNumber(null)
        .build();

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    Schema schema = User.SCHEMA$;
    DataFileWriter<User> writer = new DataFileWriter<>(new GenericDatumWriter<>());
    writer.create(schema, output);
    writer.append(user);
    writer.flush();
    FileOutputStream fos = new FileOutputStream(filePath);
    fos.write(output.toByteArray());
  }

  public static void readUser() throws IOException {
    InputStream is = FileUserManager.class.getClassLoader()
        .getResourceAsStream("data/user-schema-data.avro");
    DatumReader<User> reader = new SpecificDatumReader<>();
    DataFileStream<User> dataFileStream = new DataFileStream<>(is, reader);

    while (dataFileStream.hasNext()) {
      User user = dataFileStream.next(null);
      System.out.println(user);
    }
  }

  public static void readGenericData() throws IOException {
    // The data file is self-contained.
    InputStream is = FileUserManager.class.getClassLoader()
        .getResourceAsStream("data/user-schema-data.avro");
    DatumReader<GenericRecord> reader = new GenericDatumReader<>();
    DataFileStream<GenericRecord> dataFileStream = new DataFileStream<>(is, reader);

    while (dataFileStream.hasNext()) {
      GenericRecord user = dataFileStream.next(null);
      System.out.println(user);
    }
  }

  public static void main(String[] args) throws IOException {
    writeUser("/tmp/user-schema-data.avro");
    readUser();
    readGenericData();
  }
}
