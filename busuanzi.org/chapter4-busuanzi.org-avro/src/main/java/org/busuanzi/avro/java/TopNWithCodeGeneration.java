package org.busuanzi.avro.java;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

/**
 * @author wangxiaolei(王小雷)
 * @version 1.0
 * @apiNote
 * @since 2018/7/2
 */
public class TopNWithCodeGeneration {
    public static void main(String[] args) throws IOException {
        // 此处TopNUrl需要通过java -jar avro-tools-1.8.2.jar compile schema TopNUrl.avsc . 自动生成TopNUrl.java
        TopNUrl TopNUrl1 = new TopNUrl();
        TopNUrl1.setUrl("https://github.com/lycheeman/big-data");
        TopNUrl1.setStarNumber(1000);
        // Leave favorite color null

        // Alternate constructor
        TopNUrl TopNUrl2 = new TopNUrl("https://blog.csdn.net/dream_an/article/details/80854827", 100, 30);

        // Construct via builder
        TopNUrl TopNUrl3 = TopNUrl.newBuilder()
                .setUrl("https://blog.csdn.net/dream_an")
                .setStarNumber(10000)
                .setCommentNumber(null)
                .build();

        // Serialize TopNUrl1 and TopNUrl2 to disk
        File file = new File("TopNUrls.avro");
        DatumWriter<TopNUrl> TopNUrlDatumWriter = new SpecificDatumWriter<TopNUrl>(TopNUrl.class);
        DataFileWriter<TopNUrl> dataFileWriter = new DataFileWriter<TopNUrl>(TopNUrlDatumWriter);
        dataFileWriter.create(TopNUrl1.getSchema(), file);
        dataFileWriter.append(TopNUrl1);
        dataFileWriter.append(TopNUrl2);
        dataFileWriter.append(TopNUrl3);
        dataFileWriter.close();

        // Deserialize TopNUrls from disk
        DatumReader<TopNUrl> TopNUrlDatumReader = new SpecificDatumReader<TopNUrl>(TopNUrl.class);
        DataFileReader<TopNUrl> dataFileReader = new DataFileReader<TopNUrl>(file, TopNUrlDatumReader);
        TopNUrl TopNUrl = null;
        while (dataFileReader.hasNext()) {
            // Reuse TopNUrl object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            TopNUrl = dataFileReader.next(TopNUrl);
            System.out.println(TopNUrl);
        }

    }
}
