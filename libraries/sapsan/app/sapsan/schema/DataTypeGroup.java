package sapsan.schema;


public enum DataTypeGroup {
    Unknown,
    /**
     * Булев тип
     */
    Boolean,
    /**
     * Byte, Short, Integer, Int (Scala), Long,
     */
    Integer,
    /**
     * Float, Double
     */
    Float,
    /**
     * String, StringBuffer, StringBuilder, CharSequence
     */
    String, Text, Password,
    /**
     * Date, Time
     */
    Timestamp,
    /**
     * byte[], ByteBuffer
     */
    Blob,
    Enumerated, /** Set, */

    /**
     * Related fields
     */
    OneToOne, OneToMany, ManyToOne, ManyToMany,
    /**
     * Key Fields
     */
    Primary,

    // TODO Jelly: Expression, Image, Slug, Polymorphic
}
