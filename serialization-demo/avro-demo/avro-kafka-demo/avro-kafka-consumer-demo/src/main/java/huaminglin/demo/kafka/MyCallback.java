package huaminglin.demo.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class MyCallback implements Callback {

  @Override
  public void onCompletion(RecordMetadata metadata, Exception exception) {
    System.out.println(metadata);
    System.out.println(exception);
  }
}
