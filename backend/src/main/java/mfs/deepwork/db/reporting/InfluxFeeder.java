package mfs.deepwork.db.reporting;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class InfluxFeeder {

    private InfluxDBConfig config;
    private final InfluxDB influxDB;

    public InfluxFeeder(@Autowired InfluxDBConfig config){
        this.config = config;
        influxDB  = InfluxDBFactory.connect(config.dbLocation, config.username, config.password);
        influxDB.setDatabase(config.database);
    }

    public void writeActivity(Activity activity, String userId){
        Point.Builder point = Point.measurement(config.table)
                .time(activity.getStarted().getTime(), TimeUnit.MILLISECONDS)
                .tag("user",userId)
                //.addField("stopped", activity.getStopped().getTime())
                .addField("activity."+activity.getName(), activity.getDuration());

        influxDB.write(point.build());
    }
}
