package org.busuanzi.avro.java;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.sun.xml.internal.ws.api.ResourceLoader;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

/**
 * @author wangxiaolei(王小雷)
 * @version 1.0
 * @apiNote
 * @since 2018/7/2
 */
public class TopNWithOutCodeGeneration {
    public static void main(String[] args) throws IOException {

        //获取TopNUrl.avsc资源路径
        URL topNUrlResource = TopNWithOutCodeGeneration.class.getResource("/TopNUrl.avsc");

        Schema schema = new Parser().parse(new File(topNUrlResource.getPath()));

        GenericRecord topNUrl1 = new GenericData.Record(schema);
        topNUrl1.put("url", "https://github.com/wangxiaoleiAI/big-data");
        topNUrl1.put("star_number", 100);
        // Leave comment_number color null

        GenericRecord topNUrl2 = new GenericData.Record(schema);
        topNUrl2.put("url", "https://blog.csdn.net/dream_an/article/details/80854827");
        topNUrl2.put("star_number", 1000);
        topNUrl2.put("comment_number", 100);

        GenericRecord topNUrl3 = new GenericData.Record(schema);
        topNUrl3.put("url", "https://blog.csdn.net/dream_an");
        topNUrl3.put("star_number", 90);
        topNUrl3.put("comment_number", 1000);

        // Serialize topNUrl1 and topNUrl2 to disk
        File file = new File("topNUrls.avro");
        // 此处是通用的GenericRecord，而不是事先生成特定的"topNUrl"类
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(topNUrl1);
        dataFileWriter.append(topNUrl2);
        dataFileWriter.append(topNUrl3);
        dataFileWriter.close();

        // Deserialize topNUrls from disk
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
        GenericRecord topNUrl = null;
        while (dataFileReader.hasNext()) {
            // Reuse topNUrl object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            topNUrl = dataFileReader.next(topNUrl);
            System.out.println(topNUrl);
        }

    }
}
