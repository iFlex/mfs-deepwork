package mfs.deepwork.db.reporting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
    @Value("${db.location:http://127.0.0.1:8086}")
    public String dbLocation;
    @Value("${db.username:root}")
    public String username;
    @Value("${db.password:root}")
    public String password;
    @Value("${db.database:deepwork}")
    public String database;
    @Value("${db.table:activities}")
    public String table;

    public void setDbLocation(String dbLocation) {
        this.dbLocation = dbLocation;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
