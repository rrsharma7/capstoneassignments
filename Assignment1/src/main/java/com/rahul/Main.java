package com.rahul;

import com.rahul.util.Constants;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        HBaseWriter obj = new HBaseWriter();
        FileSystem fs = FileSystem.get(new URI(Constants.hdfsUrl),obj.config);
        String uri = Constants.hdfsUrl + Constants.internalUrl;
        obj.createTable(Constants.TABLE_NAME);
        obj.storeIntoHBase(fs , uri);
    }
}
