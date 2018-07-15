package org.busuanzi.spark;

import org.apache.spark.sql.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author wangxiaolei(王小雷)
 * @since 2018/7/11
 */
public class Spark2PostgreSQL {
    public static void main (String args[]) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
//                .config("spark.some.config.option", "some-value")
                .getOrCreate();


        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "busuanzi");
        connectionProperties.put("password", "wxl123");


        //read 读取数据库
        Dataset<Row> jdbcDF2 = spark.read()
                .jdbc("jdbc:postgresql://192.168.56.110:5432/busuanzidb", "public.top_projects", connectionProperties);

        jdbcDF2.show();
        System.out.println("Read Success!");

        //创建视图onev
        jdbcDF2.createOrReplaceTempView("onev");




        //增加数据
        TopNURL topNURL = new TopNURL();

        topNURL.setUsername("wangxiaolei");
        topNURL.setProjects("Spark-Cassandra");
        topNURL.setStar_number(99);
        topNURL.setComment("nice-job");

        //write 写入数据库
        List<TopNURL> aa = Arrays.asList(topNURL);
        Dataset<TopNURL> myDF2 = spark.createDataset(aa, Encoders.bean(TopNURL.class));
        myDF2.write()
                .mode(SaveMode.Append)
                .jdbc("jdbc:postgresql://192.168.56.110:5432/busuanzidb", "public.top_projects", connectionProperties);

        System.out.println("Write Success!");
        //创建视图twov
        myDF2.createOrReplaceTempView("twov");



        //Spark Join连接操作
        spark.sql("SELECT * FROM onev r JOIN twov s ON r.username = s.username")
                .show();
        System.out.println("Join Success!");


//        Dataset<Row> jdbcDF = spark.read()
//                .format("jdbc")
//                .option("url", "jdbc:postgresql:dbserver")
//                .option("dbtable", "public.top_n_url")
//                .option("user", "busuanzi")
//                .option("password", "wxl123")
//                .load();
//// Saving data to a JDBC source
//        jdbcDF.write()
//                .format("jdbc")
//                .option("url", "jdbc:postgresql:dbserver")
//                .option("dbtable", "schema.tablename")
//                .option("user", "username")
//                .option("password", "password")
//                .save();
//
//        jdbcDF2.write()
//                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);
//
//// Specifying create table column data types on write
//        jdbcDF.write()
//                .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
//                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);
    }
}
