package com.rahul;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;

import org.apache.hadoop.mapreduce.Job;


import java.io.IOException;

import static com.rahul.util.Constants.*;


public class Main {
    public static void main(String[] args)
            throws IOException, InterruptedException, ClassNotFoundException {

        createTable(PEOPLE_BULK_UPLOAD_TABLE);
        Configuration config = HBaseConfiguration.create();

        config.set(DEFAULT_FS, HDFS_INPUT_URL);

        Path output = new Path(ASSIGNMENT2_OUTPUT_PATH);
        FileSystem hdfs = FileSystem.get(config);

        if (hdfs.exists(output)) {
            hdfs.delete(output, true);
        }

        Scan scan = new Scan();
        scan.setCaching(500);
        scan.setCacheBlocks(false);
        Job job = Job.getInstance(config);
        job.setJobName("MRTableReadWrite");
        job.setJarByClass(Main.class);

        TableMapReduceUtil.initTableMapperJob(
                PEOPLE_TABLE,
                scan,
                HbaseBulkLoadMapper.class,
                null,
                null,
                job
        );
        TableMapReduceUtil.initTableReducerJob(
                PEOPLE_BULK_UPLOAD_TABLE,
                null,
                job
        );

        job.setNumReduceTasks(0);
        boolean b = job.waitForCompletion(true);
        System.out.println(b);

        if(job.isSuccessful()){
            System.out.println("Successful");
        }
    }

    public static void createTable(String tableName) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        Admin hAdmin = connection.getAdmin();

        if (hAdmin.tableExists(TableName.valueOf(tableName))) {
            System.out.println(tableName + "  already exists");
            return;
        }
        TableName tname = TableName.valueOf(tableName);
        TableDescriptorBuilder tableDescBuilder = TableDescriptorBuilder.newBuilder(tname);

        tableDescBuilder.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("Name".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("Age".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("Company".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("Building_code".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("Phone_Number".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("Address".getBytes()).build())
                .build();

        hAdmin.createTable(tableDescBuilder.build());

        System.out.println(tableName + " table crated");

    }

}
