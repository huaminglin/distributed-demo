# Spring Cloud Schema Registry

http://127.0.0.1:8990

## CommandLineRunner and producer

```
Caused by: org.springframework.messaging.MessageDeliveryException: Dispatcher has no subscribers for channel 'application.output'.; nested exception is org.springframework.integration.MessageDispatchingException: Dispatcher has no subscribers, failedMessage=GenericMessage [payload={"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "female"}, headers={id=b3a54ecb-782a-9827-1fcb-71463c8ef3f0, timestamp=1639411123643}]
	at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:76) ~[spring-integration-core-5.5.6.jar:5.5.6]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:317) ~[spring-integration-core-5.5.6.jar:5.5.6]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:272) ~[spring-integration-core-5.5.6.jar:5.5.6]
	at huaminglin.demo.avro.spring.AvroSpringCloudClientDemo.run(AvroSpringCloudClientDemo.java:40) ~[classes/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:808) ~[spring-boot-2.4.10.jar:2.4.10]
```

You can't start consuming from the CommandLineRunner.
That's still early in the application context lifecycle.

## How to involve org.springframework.messaging.converter.MessageConverter to Kafka publisher

```
Exception in thread "main" org.springframework.messaging.MessageHandlingException: error occurred in message handler [org.springframework.cloud.stream.binder.kafka.KafkaMessageChannelBinder$ProducerConfigurationMessageHandler@17e0933c]; nested exception is org.apache.kafka.common.errors.SerializationException: Can't convert value of class example.avro.User to class org.apache.kafka.common.serialization.ByteArraySerializer specified in value.serializer, failedMessage=GenericMessage [payload={"name": "Charlie", "favoriteNumber": null, "favoriteColor": "blue", "sex": "female"}, headers={id=030548d7-e132-15c6-bc63-c5f8e9693638, timestamp=1639416623179}]
	at org.springframework.integration.support.utils.IntegrationUtils.wrapInHandlingExceptionIfNecessary(IntegrationUtils.java:191)
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:65)
	at org.springframework.cloud.stream.binder.AbstractMessageChannelBinder$SendingHandler.handleMessageInternal(AbstractMessageChannelBinder.java:1074)
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:56)
	at org.springframework.integration.dispatcher.AbstractDispatcher.tryOptimizedDispatch(AbstractDispatcher.java:115)
	at org.springframework.integration.dispatcher.UnicastingDispatcher.doDispatch(UnicastingDispatcher.java:133)
	at org.springframework.integration.dispatcher.UnicastingDispatcher.dispatch(UnicastingDispatcher.java:106)
	at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:72)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:317)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:272)
	at huaminglin.demo.avro.spring.MyUserPublisher.publishUser(MyUserPublisher.java:28)
	at huaminglin.demo.avro.spring.AvroSpringCloudClientDemo.main(AvroSpringCloudClientDemo.java:39)
Caused by: org.apache.kafka.common.errors.SerializationException: Can't convert value of class example.avro.User to class org.apache.kafka.common.serialization.ByteArraySerializer specified in value.serializer
	at org.apache.kafka.clients.producer.KafkaProducer.doSend(KafkaProducer.java:932)
	at org.apache.kafka.clients.producer.KafkaProducer.send(KafkaProducer.java:889)
	at org.springframework.kafka.core.DefaultKafkaProducerFactory$CloseSafeProducer.send(DefaultKafkaProducerFactory.java:984)
	at org.springframework.kafka.core.KafkaTemplate.doSend(KafkaTemplate.java:649)
	at org.springframework.kafka.core.KafkaTemplate.send(KafkaTemplate.java:429)
	at org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler.handleRequestMessage(KafkaProducerMessageHandler.java:513)
	at org.springframework.integration.handler.AbstractReplyProducingMessageHandler.handleMessageInternal(AbstractReplyProducingMessageHandler.java:136)
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:56)
	... 10 more
Caused by: java.lang.ClassCastException: class example.avro.User cannot be cast to class [B (example.avro.User is in unnamed module of loader 'app'; [B is in module java.base of loader 'bootstrap')
	at org.apache.kafka.common.serialization.ByteArraySerializer.serialize(ByteArraySerializer.java:19)
	at org.apache.kafka.common.serialization.Serializer.serialize(Serializer.java:62)
	at org.apache.kafka.clients.producer.KafkaProducer.doSend(KafkaProducer.java:929)
	... 17 more
```

## org.springframework.cloud.stream.binding.MessageConverterConfigurer.configureMessageChannel

```
		if (this.isNativeEncodingNotSet(producerProperties, consumerProperties, inbound)) {
```

If use "useNativeEncoding=true", it will skip some configuration.

After switching to "useNativeEncoding=false", AvroSchemaMessageConverter is involved.

## org.springframework.cloud.schema.registry.avro.AvroSchemaMessageConverter

```
	at org.springframework.messaging.converter.CompositeMessageConverter.toMessage(CompositeMessageConverter.java:82)
	at org.springframework.cloud.stream.binding.MessageConverterConfigurer$OutboundContentTypeConvertingInterceptor.doPreSend(MessageConverterConfigurer.java:280)
	at org.springframework.cloud.stream.binding.MessageConverterConfigurer$AbstractContentTypeInterceptor.preSend(MessageConverterConfigurer.java:312)
	at org.springframework.integration.channel.AbstractMessageChannel$ChannelInterceptorList.preSend(AbstractMessageChannel.java:469)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:309)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:272)
	at huaminglin.demo.avro.spring.MyUserPublisher.publishUser(MyUserPublisher.java:28)
	at huaminglin.demo.avro.spring.AvroSpringCloudClientDemo.main(AvroSpringCloudClientDemo.java:41)
```


## org.springframework.messaging.converter.CompositeMessageConverter.toMessage(java.lang.Object, org.springframework.messaging.MessageHeaders)

```
	public Message<?> toMessage(Object payload, @Nullable MessageHeaders headers) {
		for (MessageConverter converter : getConverters()) {
			Message<?> result = converter.toMessage(payload, headers);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
```

org.springframework.cloud.schema.registry.avro.AbstractAvroMessageConverter
org.springframework.cloud.schema.registry.avro.AvroSchemaMessageConverter
org.springframework.cloud.schema.registry.avro.AvroSchemaRegistryClientMessageConverter

If the AvroSchemaMessageConverter is used to convert the message, AvroSchemaRegistryClientMessageConverter has no chance.

Python consumers
```
org.springframework.cloud.schema.registry.avro.AvroSchemaMessageConverter:
ConsumerRecord(topic='user-details', partition=0, offset=0, timestamp=1639574133721, timestamp_type=0, key=None, value=b'\x0eCharlie\x02\x00\x08blue\x02', headers=[('contentType', b'"application/*+avro"'), ('spring_json_header_types', b'{"contentType":"java.lang.String"}')], checksum=None, serialized_key_size=-1, serialized_value_size=16, serialized_header_size=89)

org.springframework.cloud.schema.registry.avro.AvroSchemaRegistryClientMessageConverter:
ConsumerRecord(topic='user-details', partition=0, offset=4, timestamp=1639586134670, timestamp_type=0, key=None, value=b'\x0eCharlie\x02\x00\x08blue\x02', headers=[('contentType', b'application/vnd.user.v1+avro'), ('spring_json_header_types', b'{"contentType":"java.lang.String"}')], checksum=None, serialized_key_size=-1, serialized_value_size=16, serialized_header_size=97)
```
Note: ('contentType', b'application/vnd.user.v1+avro') is the registry id.

## AvroMessageConverterAutoConfiguration and SchemaRegistryClientConfiguration setup AvroSchemaRegistryClientMessageConverter automatically

```

org.springframework.cloud.schema.registry.avro.AvroMessageConverterAutoConfiguration
@Bean @ConditionalOnMissingBean(AvroSchemaRegistryClientMessageConverter.class) public AvroSchemaRegistryClientMessageConverter avroSchemaMessageConverter(

org.springframework.cloud.schema.registry.client.config.SchemaRegistryClientConfiguration
@Bean @ConditionalOnMissingBean public SchemaRegistryClient schemaRegistryClient(SchemaRegistryClientProperties schemaRegistryClientProperties, RestTemplateBuilder restTemplateBuilder)
```

Kafka message header 'contentType', b'application/vnd.user.v1+avro' is used to register the schema.

http://localhost:8990
