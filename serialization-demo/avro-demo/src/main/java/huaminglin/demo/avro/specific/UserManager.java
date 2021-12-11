package huaminglin.demo.avro.specific;

import example.avro.User;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class UserManager {
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
    InputStream is = UserManager.class.getClassLoader().getResourceAsStream("data/user-data.avro");
    byte[] bytes = is.readAllBytes();
    User user = UserManager.parseUserFromBytes(bytes);
    System.out.println(user);
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
