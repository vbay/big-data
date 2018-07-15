package org.busuanzi.spark;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.sql.*;


/**
 * @author wangxiaolei(王小雷)
 * @since 2018/7/11
 */
public class Spark2Cassandra {
    public static void main(String args[]) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
//            .config("spark.some.config.option", "some-value")
                .config("spark.cassandra.connection.host", "192.168.56.110")
                .config("spark.cassandra.auth.username", "busuanzi")
                .config("spark.cassandra.auth.password", "busuanzi.org")
                .config("spark.cassandra.connection.port", "9042")
                .getOrCreate();

        TopNURL topNURL = new TopNURL();

        topNURL.setUsername("wangxiaolei");
        topNURL.setProjects("Spark-Cassandra");
        topNURL.setStar_number(99);
        topNURL.setComment("nice-job");

        //write 查询数据
        List<TopNURL> aa = Arrays.asList(topNURL);
        Dataset<TopNURL> ds1 = spark.createDataset(aa, Encoders.bean(TopNURL.class));
        ds1.write()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>() {
                    {
                        put("keyspace", "busuanzi_org");
                        put("table", "top_n_url");
                    }
                }).mode("append").save();

        ds1.show();
        System.out.println("Cassandra Save success!");


        //read 读取数据
        Dataset<Row> ds = spark.read()
                .format("org.apache.spark.sql.cassandra")
                .options(new HashMap<String, String>(){
                    {
                        put("keyspace", "busuanzi_org");
                        put("table", "top_n_url");
                    }
                }).load();
        ds.show();
        System.out.println("Cassandra Read success!");

        ds.createOrReplaceTempView("dsv");



        //distinct 去重
        ds.select("username", "projects", "comment").distinct().show();
        System.out.println("Spark distinct success!");


        spark.stop();
    }
}
