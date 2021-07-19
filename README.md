# EasyDatabase

<hr />

## API

### Maven

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml

<dependencies>
    <dependency>
        <groupID>com.github.SaveFeelix</groupID>
        <artifactID>EasyDatabase</artifactID>
        <version>7c839e721b</version>
    </dependency>
</dependencies>
```

<hr />

### Gradle

```groovy
repositories {
    maven { url = 'https://jitpack.io' }
}
```

```groovy
dependencies {
    implementation 'com.github.SaveFeelix:EasyDatabase:7c839e721b'
}
```

<hr />

### Java

#### Create own DatabaseClass

```java
import de.savefeelix.database.interfaces.DataBase;

public class MyDatabaseClass implements DataBase {
    @Override
    public boolean openConnection(@NotNull Information information) {
        return false;
    }

    @Override
    public void closeConnection(String... errorMessages) {

    }

    @Override
    public void createTable(@NotNull String tableName) {

    }

    @Override
    public void addColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull) {

    }

    @Override
    public void addPrimaryColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull) {

    }

    @Override
    public void addForeignColumn(@NotNull String tableName, @NotNull String columnName, @NotNull DataBaseValueType type, @NotNull Boolean isNotNull, @NotNull PrimaryKeyInformation primaryKeyInformation) {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
```

#### Create Class using MySQL/MariaDB

```java
import de.savefeelix.database.MySQL;

public class MyDatabaseClass extends MySQL {
    @Override
    public void createDefaultTables() {

    }

    @Override
    public void createDefaultColumns() {

    }
}
```
or <br />
```java
import de.savefeelix.database.MariaDB;

public class MyDatabaseClass extends MariaDB {
    @Override
    public void createDefaultTables() {

    }

    @Override
    public void createDefaultColumns() {

    }
}
```

#### Create DatabaseInformation Class

```java
import de.savefeelix.database.abstracts.Information;

public class DatabaseInformationClass extends Information {
    @Override
    public @NotNull
    Boolean isEnabled() {
        return null;
    }

    @Override
    public @NotNull
    String getHost() {
        return null;
    }

    @Override
    public @NotNull
    Integer getPort() {
        return null;
    }

    @Override
    public @NotNull
    String getDatabase() {
        return null;
    }

    @Override
    public @NotNull
    String getUser() {
        return null;
    }

    @Override
    public @NotNull
    String getPassword() {
        return null;
    }
}
```

#### Create PrimaryKeyInformation Class

```java
import de.savefeelix.database.interfaces.information.PrimaryKeyInformation;

public class PrimaryKeyInformationClass implements PrimaryKeyInformation {
    @Override
    public @NotNull
    String getTableName() {
        return null;
    }

    @Override
    public @NotNull
    String getColumnName() {
        return null;
    }

    @Override
    public @NotNull
    ReferenceTypes onUpdate() {
        return null;
    }

    @Override
    public @NotNull
    ReferenceTypes onDelete() {
        return null;
    }
}
```

#### Create Instance & Open Connection

```java
import de.savefeelix.database.MySQL;
import de.savefeelix.database.interfaces.DataBase;

public class MyClass {
    public static void main(String[] args) {
        // Create Instance
        DataBase dataBase = new MyDatabaseClass();
        
        // Open Connection
        if (dataBase.openConnection(new DatabaseInformationClass())) {
            System.out.println("Connected");
        }
    }
}
```
