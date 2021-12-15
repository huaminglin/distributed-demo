/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package example.avro;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class User extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -8605981760480722296L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"User\",\"namespace\":\"example.avro\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"favoriteNumber\",\"type\":[\"int\",\"null\"]},{\"name\":\"favoriteColor\",\"type\":[\"string\",\"null\"]},{\"name\":\"sex\",\"type\":{\"type\":\"enum\",\"name\":\"Sex\",\"symbols\":[\"male\",\"female\"],\"default\":\"male\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<User> ENCODER =
      new BinaryMessageEncoder<User>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<User> DECODER =
      new BinaryMessageDecoder<User>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<User> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<User> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<User> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<User>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this User to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a User from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a User instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static User fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.lang.CharSequence name;
  private java.lang.Integer favoriteNumber;
  private java.lang.CharSequence favoriteColor;
  private example.avro.Sex sex;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public User() {}

  /**
   * All-args constructor.
   * @param name The new value for name
   * @param favoriteNumber The new value for favoriteNumber
   * @param favoriteColor The new value for favoriteColor
   * @param sex The new value for sex
   */
  public User(java.lang.CharSequence name, java.lang.Integer favoriteNumber, java.lang.CharSequence favoriteColor, example.avro.Sex sex) {
    this.name = name;
    this.favoriteNumber = favoriteNumber;
    this.favoriteColor = favoriteColor;
    this.sex = sex;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return name;
    case 1: return favoriteNumber;
    case 2: return favoriteColor;
    case 3: return sex;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: name = (java.lang.CharSequence)value$; break;
    case 1: favoriteNumber = (java.lang.Integer)value$; break;
    case 2: favoriteColor = (java.lang.CharSequence)value$; break;
    case 3: sex = (example.avro.Sex)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.CharSequence getName() {
    return name;
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.CharSequence value) {
    this.name = value;
  }

  /**
   * Gets the value of the 'favoriteNumber' field.
   * @return The value of the 'favoriteNumber' field.
   */
  public java.lang.Integer getFavoriteNumber() {
    return favoriteNumber;
  }


  /**
   * Sets the value of the 'favoriteNumber' field.
   * @param value the value to set.
   */
  public void setFavoriteNumber(java.lang.Integer value) {
    this.favoriteNumber = value;
  }

  /**
   * Gets the value of the 'favoriteColor' field.
   * @return The value of the 'favoriteColor' field.
   */
  public java.lang.CharSequence getFavoriteColor() {
    return favoriteColor;
  }


  /**
   * Sets the value of the 'favoriteColor' field.
   * @param value the value to set.
   */
  public void setFavoriteColor(java.lang.CharSequence value) {
    this.favoriteColor = value;
  }

  /**
   * Gets the value of the 'sex' field.
   * @return The value of the 'sex' field.
   */
  public example.avro.Sex getSex() {
    return sex;
  }


  /**
   * Sets the value of the 'sex' field.
   * @param value the value to set.
   */
  public void setSex(example.avro.Sex value) {
    this.sex = value;
  }

  /**
   * Creates a new User RecordBuilder.
   * @return A new User RecordBuilder
   */
  public static example.avro.User.Builder newBuilder() {
    return new example.avro.User.Builder();
  }

  /**
   * Creates a new User RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new User RecordBuilder
   */
  public static example.avro.User.Builder newBuilder(example.avro.User.Builder other) {
    if (other == null) {
      return new example.avro.User.Builder();
    } else {
      return new example.avro.User.Builder(other);
    }
  }

  /**
   * Creates a new User RecordBuilder by copying an existing User instance.
   * @param other The existing instance to copy.
   * @return A new User RecordBuilder
   */
  public static example.avro.User.Builder newBuilder(example.avro.User other) {
    if (other == null) {
      return new example.avro.User.Builder();
    } else {
      return new example.avro.User.Builder(other);
    }
  }

  /**
   * RecordBuilder for User instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<User>
    implements org.apache.avro.data.RecordBuilder<User> {

    private java.lang.CharSequence name;
    private java.lang.Integer favoriteNumber;
    private java.lang.CharSequence favoriteColor;
    private example.avro.Sex sex;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(example.avro.User.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.favoriteNumber)) {
        this.favoriteNumber = data().deepCopy(fields()[1].schema(), other.favoriteNumber);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.favoriteColor)) {
        this.favoriteColor = data().deepCopy(fields()[2].schema(), other.favoriteColor);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.sex)) {
        this.sex = data().deepCopy(fields()[3].schema(), other.sex);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing User instance
     * @param other The existing instance to copy.
     */
    private Builder(example.avro.User other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.name)) {
        this.name = data().deepCopy(fields()[0].schema(), other.name);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.favoriteNumber)) {
        this.favoriteNumber = data().deepCopy(fields()[1].schema(), other.favoriteNumber);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.favoriteColor)) {
        this.favoriteColor = data().deepCopy(fields()[2].schema(), other.favoriteColor);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.sex)) {
        this.sex = data().deepCopy(fields()[3].schema(), other.sex);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.CharSequence getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public example.avro.User.Builder setName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.name = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public example.avro.User.Builder clearName() {
      name = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'favoriteNumber' field.
      * @return The value.
      */
    public java.lang.Integer getFavoriteNumber() {
      return favoriteNumber;
    }


    /**
      * Sets the value of the 'favoriteNumber' field.
      * @param value The value of 'favoriteNumber'.
      * @return This builder.
      */
    public example.avro.User.Builder setFavoriteNumber(java.lang.Integer value) {
      validate(fields()[1], value);
      this.favoriteNumber = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'favoriteNumber' field has been set.
      * @return True if the 'favoriteNumber' field has been set, false otherwise.
      */
    public boolean hasFavoriteNumber() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'favoriteNumber' field.
      * @return This builder.
      */
    public example.avro.User.Builder clearFavoriteNumber() {
      favoriteNumber = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'favoriteColor' field.
      * @return The value.
      */
    public java.lang.CharSequence getFavoriteColor() {
      return favoriteColor;
    }


    /**
      * Sets the value of the 'favoriteColor' field.
      * @param value The value of 'favoriteColor'.
      * @return This builder.
      */
    public example.avro.User.Builder setFavoriteColor(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.favoriteColor = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'favoriteColor' field has been set.
      * @return True if the 'favoriteColor' field has been set, false otherwise.
      */
    public boolean hasFavoriteColor() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'favoriteColor' field.
      * @return This builder.
      */
    public example.avro.User.Builder clearFavoriteColor() {
      favoriteColor = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'sex' field.
      * @return The value.
      */
    public example.avro.Sex getSex() {
      return sex;
    }


    /**
      * Sets the value of the 'sex' field.
      * @param value The value of 'sex'.
      * @return This builder.
      */
    public example.avro.User.Builder setSex(example.avro.Sex value) {
      validate(fields()[3], value);
      this.sex = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'sex' field has been set.
      * @return True if the 'sex' field has been set, false otherwise.
      */
    public boolean hasSex() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'sex' field.
      * @return This builder.
      */
    public example.avro.User.Builder clearSex() {
      sex = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User build() {
      try {
        User record = new User();
        record.name = fieldSetFlags()[0] ? this.name : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.favoriteNumber = fieldSetFlags()[1] ? this.favoriteNumber : (java.lang.Integer) defaultValue(fields()[1]);
        record.favoriteColor = fieldSetFlags()[2] ? this.favoriteColor : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.sex = fieldSetFlags()[3] ? this.sex : (example.avro.Sex) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<User>
    WRITER$ = (org.apache.avro.io.DatumWriter<User>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<User>
    READER$ = (org.apache.avro.io.DatumReader<User>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.name);

    if (this.favoriteNumber == null) {
      out.writeIndex(1);
      out.writeNull();
    } else {
      out.writeIndex(0);
      out.writeInt(this.favoriteNumber);
    }

    if (this.favoriteColor == null) {
      out.writeIndex(1);
      out.writeNull();
    } else {
      out.writeIndex(0);
      out.writeString(this.favoriteColor);
    }

    out.writeEnum(this.sex.ordinal());

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.name = in.readString(this.name instanceof Utf8 ? (Utf8)this.name : null);

      if (in.readIndex() != 0) {
        in.readNull();
        this.favoriteNumber = null;
      } else {
        this.favoriteNumber = in.readInt();
      }

      if (in.readIndex() != 0) {
        in.readNull();
        this.favoriteColor = null;
      } else {
        this.favoriteColor = in.readString(this.favoriteColor instanceof Utf8 ? (Utf8)this.favoriteColor : null);
      }

      this.sex = example.avro.Sex.values()[in.readEnum()];

    } else {
      for (int i = 0; i < 4; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.name = in.readString(this.name instanceof Utf8 ? (Utf8)this.name : null);
          break;

        case 1:
          if (in.readIndex() != 0) {
            in.readNull();
            this.favoriteNumber = null;
          } else {
            this.favoriteNumber = in.readInt();
          }
          break;

        case 2:
          if (in.readIndex() != 0) {
            in.readNull();
            this.favoriteColor = null;
          } else {
            this.favoriteColor = in.readString(this.favoriteColor instanceof Utf8 ? (Utf8)this.favoriteColor : null);
          }
          break;

        case 3:
          this.sex = example.avro.Sex.values()[in.readEnum()];
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










