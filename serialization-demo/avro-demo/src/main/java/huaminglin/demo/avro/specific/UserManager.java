package huaminglin.demo.avro.specific;

import example.avro.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class UserManager {

  public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] buffer = new byte[0xFFFF];
    for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
      os.write(buffer, 0, len);
    }
    return os.toByteArray();
  }

  public static void writeUserBytes() throws IOException {
    User user = User.newBuilder()
        .setName("Charlie")
        .setFavoriteColor("blue")
        .setFavoriteNumber(null)
        .build();
    byte[] userBytes = UserManager.getUserBytes(user);
    Path path = Paths.get("/tmp/user-data.avro");
    Files.write(path, userBytes);
  }

  public static void parseUserFromBytes() throws IOException {
    InputStream stream = UserManager.class.getClassLoader()
        .getResourceAsStream("user-data.avro");
    byte[] bytes = getBytesFromInputStream(stream);
    User user = UserManager.parseUserFromBytes(bytes);
    System.out.println(user.getName());
  }

  public static byte[] getUserBytes(User user) throws IOException {
    ByteBuffer bb = user.toByteBuffer();
    byte[] bytes = new byte[bb.remaining()];
    bb.get(bytes);
    return bytes;
  }

  public static User parseUserFromBytes(byte[] bytes) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    User user = User.fromByteBuffer(buffer);
    return user;
  }

  public static void main(String[] args) throws IOException {
    writeUserBytes();
    parseUserFromBytes();
  }
}
