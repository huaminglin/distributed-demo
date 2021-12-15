package huaminglin.demo.avro.spring;

import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.avro.AvroSchemaMessageConverter;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.util.MimeType;

@SpringBootApplication
@EnableBinding(Processor.class)
@EnableSchemaRegistryClient
public class AvroSpringCloudProducerDemo {

//  @Bean // Don't create AvroSchemaMessageConverter,
//  the auto config AvroSchemaRegistryClientMessageConverter instance is enough.
  public MessageConverter userMessageConverter() {
    AvroSchemaMessageConverter converter = new AvroSchemaMessageConverter(
        MimeType.valueOf("application/*+avro"));
    converter.setSchemaLocation(new ClassPathResource("avro/user.avsc"));
    return converter;
  }

  public static void main(String[] args) throws InterruptedException {
    ConfigurableApplicationContext context = SpringApplication
        .run(AvroSpringCloudProducerDemo.class, args);
    MyUserPublisher bean = context.getBean(MyUserPublisher.class);
    try {
      bean.publishUser();
    } catch (Exception e) {
      e.printStackTrace();
    }
    for (int i = 5; i > 0; i-=5) {
      System.out.println("Shutdown after " + i + " seconds: " + new Date());
      Thread.sleep(5 * 1000);
    }
    SpringApplication.exit(context);
  }
}
