package org.busuanzi.spark;





import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Date;


import org.apache.commons.lang3.StringUtils;

import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.sql.Dataset;


/**
 * @author wangxiaolei(王小雷)
 * @version 1.0
 * @apiNote
 * @since 2018/7/10
 */
public class SparkSQL {
    private transient SparkConf conf;
    private SparkSQL(SparkConf conf) {
        this.conf = conf;
    }
    private void run() {
        SparkConf conf = new SparkConf();

        JavaSparkContext sc = new JavaSparkContext(conf);
        ReadFromCassandra(sc);

        sc.stop();
    }

    public void ReadFromCassandra(JavaSparkContext sc) {

    JavaRDD<String> cassandraRowsRDD = javaFunctions(sc).cassandraTable("ks", "people")
            .map(new Function<CassandraRow, String>() {
                @Override
                public String call(CassandraRow cassandraRow) throws Exception {
                    return cassandraRow.toString();
                }
            });

    System.out.println("Data as CassandraRows: \n" + StringUtils.join(cassandraRowsRDD.toString(), "\n"));

    }

    public static void main(String[] args) {
//        if (args.length != 2) {
//            System.err.println("Syntax: com.datastax.spark.demo.JavaDemo <Spark Master URL> <Cassandra contact point>");
//            System.exit(1);
//        }

        SparkConf conf = new SparkConf()
                .setAppName("busuanzi").setMaster("localhost")
                .set("spark.cassandra.connection.host", "192.168.56.110")
                .set("spark.cassandra.connection.native.port", "9142")
                .set("spark.cassandra.connection.rpc.port", "9171");
//        conf.setAppName("Java API demo");
//        conf.setMaster(args[0]);
//        conf.set("spark.cassandra.connection.host", args[1]);

        SparkSQL app = new SparkSQL(conf);
        app.run();
    }

}
