package huaminglin.demo.avro.spring;

import example.avro.UserV2;
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableBinding(Processor.class)
@EnableSchemaRegistryClient
public class AvroSpringCloudConsumerDemo {
  @StreamListener(Processor.INPUT)
  public void consumeUserDetails(UserV2 user) {
    // The producer uses old schema; the consumer uses new schema.
    System.out.println(user);
  }

  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext context = SpringApplication
        .run(AvroSpringCloudConsumerDemo.class, args);
    for (int i = 60; i > 0; i-=5) {
      System.out.println("Shutdown after " + i + " seconds: " + new Date());
      Thread.sleep(5 * 1000);
    }
    SpringApplication.exit(context);
  }
}
