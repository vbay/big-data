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
import org.apache.spark.sql.Row;


/**
 * @author wangxiaolei(王小雷)
 * @version 1.0
 * @apiNote
 * @since 2018/7/10
 */
public class SparkSQL {

    public static void main(String[] args) {


        SparkConf conf = new SparkConf()
                .setAppName("busuanzi")
//                .setMaster("master")
                .set("spark.cassandra.connection.host", "192.168.56.110")
                .set("spark.cassandra.auth.username", "cassandra")
                .set("spark.cassandra.auth.password", "cassandra")
                .set("spark.cassandra.connection.port", "9042");
//                .set("spark.cassandra.connection.rpc.port", "9160");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> cassandraRowsRDD = javaFunctions(sc).cassandraTable("busuanzi_org", "top_n_url")
                .map(new Function<CassandraRow, String>() {
                    @Override
                    public String call(CassandraRow cassandraRow) throws Exception {
                        return cassandraRow.toString();
                    }
                });
        cassandraRowsRDD.countByValue();

        System.out.println(cassandraRowsRDD.countByValue());
        sc.stop();
    }

}
