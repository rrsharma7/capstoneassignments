package com.rahul;

import com.rahul.util.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;

public class HBaseWriter {

    public Configuration config;

    public HBaseWriter() {
        config = new Configuration();
    }

    public void createTable(String tableName) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        Admin hAdmin = connection.getAdmin();



        if (hAdmin.tableExists(TableName.valueOf(tableName))) {
            System.out.println(tableName + "  already exists");
            return;
        }
        TableName tname = TableName.valueOf(tableName);
        TableDescriptorBuilder tableDescBuilder = TableDescriptorBuilder.newBuilder(tname);

        tableDescBuilder.setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("building_code".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("total_floor".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("companies_in_the_building".getBytes()).build())
                .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder("cafeteria_code".getBytes()).build())
                .build();

        hAdmin.createTable(tableDescBuilder.build());

        System.out.println(tableName + " table crated");

    }
    private HashMap<Integer, String> getColumnMapping() {
        HashMap<Integer, String> hm = new HashMap<>();
        hm.put(0, "building_code");
        hm.put(1, "total_floor");
        hm.put(2, "companies_in_the_building");
        hm.put(3, "cafeteria_code");

        return hm;
    }

    private void insertDataToHbase(String[] record, int rowId) throws IOException {
        Table table = null;
        Connection connection = null;
        HashMap<Integer, String> hm = getColumnMapping();
        try
        {
            Configuration conf = HBaseConfiguration.create();
            connection = ConnectionFactory.createConnection(conf);
            table = connection.getTable(TableName.valueOf(Constants.TABLE_NAME));
            Put p = new Put(Bytes.toBytes(String.valueOf(rowId)));
            for(int i = 0; i < record.length; ++ i) {
                String qualifier = hm.get(i);
                if(qualifier != null)
                    p.addColumn(Bytes.toBytes("building_details"),
                            Bytes.toBytes(qualifier),
                            Bytes.toBytes(record[i]));
            }

            table.put(p);

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(table != null)
                table.close();
            if(connection != null)
                connection.close();
        }
    }

    public void storeIntoHBase(FileSystem hdfs, String uri) throws IOException {

        config.set("fs.defaultFS", Constants.hdfsUrl);
        FileStatus[] fileStatus = hdfs.listStatus(new Path(uri));
        Path[] paths = FileUtil.stat2Paths(fileStatus);
        FileSystem fileSystem = FileSystem.get(config);
        int rowId = 1;
        for (Path path : paths) {
            FSDataInputStream inputStream = fileSystem.open(path);
            String line = IOUtils.toString(inputStream, Constants.encoding).split("\n")[0];
            String[] record = line.split(",");
            insertDataToHbase(record, rowId);
            rowId++;
            inputStream.close();
        }
        fileSystem.close();
    }

}
