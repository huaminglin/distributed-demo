# Avro demo

## AvroGenerated

1. Convert raw bytes (without schema) from/to AvroGenerated SpecificRecord

AvroGenerated: public java.nio.ByteBuffer toByteBuffer()

AvroGenerated: public static User fromByteBuffer(java.nio.ByteBuffer b)

## Avro reader Schema and writer Schema

```
    // Schema provided as constructor parameter is the target Schema, which is used to generate the return value
    DatumReader<GenericRecord> reader = new GenericDatumReader<>(User.SCHEMA$);
    // The "setSchema()" is the from Schema, which is used to parse the source stream
    reader.setSchema(User.SCHEMA$);
```

## user-data-bytebuffer.avro and user-data-generic.avro

```
-rw-rw-r-- 1 user1 user1   25 Dec 11 18:09 user-data-bytebuffer.avro
-rw-rw-r-- 1 user1 user1   15 Dec 11 22:29 user-data-generic.avro
-rw-rw-r-- 1 user1 user1  268 Dec 11 18:06 user-schema-data.avro

hexdump user-data-bytebuffer.avro 
0000000 01c3 2948 c1fd 531c 7964 430e 6168 6c72
0000010 6569 0002 6208 756c 0065               
0000019

hexdump user-data-generic.avro 
0000000 430e 6168 6c72 6569 0002 6208 756c 0065
000000f
```

Note: user-data-bytebuffer.avro has a header "01c3 2948 c1fd 531c 7964".
The first two bytes are "byte[] V1_HEADER = new byte[]{-61, 1}"
The last 8 bytes are the "CRC-64-AVRO".

```
  java.lang.Thread.State: RUNNABLE
	  at org.apache.avro.message.BinaryMessageEncoder$V1MessageEncoder.encode(BinaryMessageEncoder.java:107)
	  at org.apache.avro.message.RawMessageEncoder.encode(RawMessageEncoder.java:94)
	  at org.apache.avro.message.BinaryMessageEncoder.encode(BinaryMessageEncoder.java:83)
	  at example.avro.User.toByteBuffer(User.java:62)
	  at huaminglin.demo.avro.specific.UserManager.writeUserBytes(UserManager.java:19)
	  at huaminglin.demo.avro.specific.UserManager.main(UserManager.java:36)
```

```
org.apache.avro.message.BinaryMessageEncoder.V1MessageEncoder.getWriteHeader
    private static byte[] getWriteHeader(Schema schema) {
      try {
        byte[] fp = SchemaNormalization.parsingFingerprint("CRC-64-AVRO", schema);
        byte[] ret = new byte[BinaryMessageEncoder.V1_HEADER.length + fp.length];
        System.arraycopy(BinaryMessageEncoder.V1_HEADER, 0, ret, 0, BinaryMessageEncoder.V1_HEADER.length);
        System.arraycopy(fp, 0, ret, BinaryMessageEncoder.V1_HEADER.length, fp.length);
        return ret;
      } catch (NoSuchAlgorithmException var3) {
        throw new AvroRuntimeException(var3);
      }
    }
  }
```

## Support to convert UserV2 to User or User to UserV2

A client/server can convert UserV2 to User or User to UserV2 when it can assess the two versions of Schema.

An ideal scenario is upgrade all the consumers first.
After all the consumers upgrade, the system is ready to accept the new version of data.
Then we can upgrade the producers to generate the new version of data.
In this scenario, we don't need a Schema server.
We can just package the latest 2/3 version of schema at the consumer side, and the latest schema for the producer side.

But a deployable unit can work as producer for one model and consumer for other model.
An upgrade can involve multiple models.
Then an old consumer need to parse a new version of data. Then we need a Schema server to for the client to fetch the new Schema.
