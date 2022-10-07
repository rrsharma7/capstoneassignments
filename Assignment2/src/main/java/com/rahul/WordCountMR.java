package com.rahul;

import com.rahul.util.Constants;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

import java.io.IOException;

public class WordCountMR {


    public static void main(String[] args) throws IOException {
        JobConf config = new JobConf(WordCountMR.class);

        config.setJobName("Word Count");

        config.setMapperClass(Mapper.Map.class);
        config.setReducerClass(Reducer.Reduce.class);

        config.setInputFormat(TextInputFormat.class);
        config.setOutputFormat(TextOutputFormat.class);

        config.setMapOutputKeyClass(Text.class);
        config.setMapOutputValueClass(IntWritable.class);

        config.setOutputKeyClass(Text.class);
        config.setOutputValueClass(IntWritable.class);

        String inputPath = Constants.rootUrl + Constants.inputUrl;
        String outputPath = Constants.rootUrl + Constants.outputUrl;

        FileInputFormat.addInputPath(config,new Path(inputPath));
        FileOutputFormat.setOutputPath(config,new Path(outputPath));

        JobClient.runJob(config);
    }


}
