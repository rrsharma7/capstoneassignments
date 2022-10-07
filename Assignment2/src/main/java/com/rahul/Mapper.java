package com.rahul;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

import java.io.IOException;
import java.util.StringTokenizer;

public class Mapper {

    public static class Map extends MapReduceBase implements
            org.apache.hadoop.mapred.Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        public void map(LongWritable key,
                        Text value,
                        OutputCollector<Text, IntWritable> outputCollector,
                        Reporter reporter) throws IOException {

            String singleLineData = value.toString();
            StringTokenizer st = new StringTokenizer(singleLineData, " ");
            while(st.hasMoreElements()) {
                String word = st.nextToken();
                outputCollector.collect(new Text(word), new IntWritable(1));
            }
        }
    }
}
