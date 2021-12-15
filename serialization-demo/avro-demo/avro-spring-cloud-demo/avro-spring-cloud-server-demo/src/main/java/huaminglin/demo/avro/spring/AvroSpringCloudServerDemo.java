package huaminglin.demo.avro.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.EnableSchemaRegistryServer;

@SpringBootApplication
@EnableSchemaRegistryServer
public class AvroSpringCloudServerDemo {
  public static void main(String[] args) {
    SpringApplication.run(AvroSpringCloudServerDemo.class, args);
  }
}
