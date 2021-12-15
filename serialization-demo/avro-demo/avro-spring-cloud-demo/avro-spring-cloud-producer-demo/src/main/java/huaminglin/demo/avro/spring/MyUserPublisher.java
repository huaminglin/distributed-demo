package huaminglin.demo.avro.spring;

import example.avro.Sex;
import example.avro.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MyUserPublisher {
  @Autowired
  private Processor processor;

  public void publishUser() {
    User user = User.newBuilder()
        .setName("Charlie")
        .setFavoriteColor("blue")
        .setFavoriteNumber(null)
        .setSex(Sex.female)
        .build();

    Message<User> message = MessageBuilder.withPayload(user)
        .setHeader(KafkaHeaders.MESSAGE_KEY, null)
        .build();
    processor.output().send(message);
  }

}
