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

    ByteBuffer bb = user.toByteBuffer();
    byte[] bytes = new byte[bb.remaining()];
    bb.get(bytes);

    Path path = Paths.get("/tmp/user-data-bytebuffer.avro");
    Files.write(path, bytes);
  }

  public static void parseUserFromBytes() throws IOException {
    InputStream is = UserManager.class.getClassLoader().getResourceAsStream("data/user-data-bytebuffer.avro");
    byte[] bytes = is.readAllBytes();
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    User user = User.fromByteBuffer(buffer);
    System.out.println(user);
  }

  public static void main(String[] args) throws IOException {
    writeUserBytes();
    parseUserFromBytes();
  }
}
