package de.savefeelix.database.enums;

public enum DataBaseValueType {
    // Numbers
    TinyInt("TINYINT"),
    SmallInt("SMALLINT"),
    MediumInt("MEDIUMINT"),
    Int("INT"),
    BigInt("BIGINT"),

    Decimal("DECIMAL"),
    Float("FLOAT"),
    Double("DOUBLE"),
    Real("REAL"),

    Bit("BIT"),
    Boolean("BOOLEAN"),
    Serial("SERIAL"),


    Date("DATE"),
    DateTime("DATE"),
    TimeStamp("TIMESTAMP"),
    Time("TIME"),
    Year("YEAR"),


    Char("CHAR"),
    VarChar("VARCHAR"),

    TinyText("TINYTEXT"),
    Text("TEXT"),
    MediumText("MEDIUMTEXT"),
    LongText("LONGTEXT"),

    Binary("BINARY"),
    VarBinary("VARBINARY"),

    TinyBlob("TINYBLOB"),
    Blob("BLOB"),
    MediumBlob("MEDIUMBLOB"),
    LongBlob("LONGBLOB"),

    Enum("ENUM"),
    Set("SET"),


    Geometry("GEOMETRY"),
    Point("POINT"),
    LineString("LINESTRING"),
    PolyGron("POLYGRON"),
    MultiPoint("MULTIPOINT"),
    MultiLineString("MULTILINESTRING"),
    MultiPolyGron("MULTIPOLYGRON"),
    GeometryCollection("GEOMETRYCOLLECTION"),


    Json("JSON");

    private final String typeName;

    DataBaseValueType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}