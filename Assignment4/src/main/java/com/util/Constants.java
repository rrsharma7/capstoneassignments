package Assignment4b.util;

import org.apache.hadoop.hbase.util.Bytes;

public class Constants {

    public static byte[] EMPLOYEE_TABLE_BYTES = Bytes.toBytes("employees");
    public static byte[] BUILDING_TABLE_BYTES = Bytes.toBytes("buildings");
    public static final String EMPLOYEE = "employee";
    public static final String EMPLOYEE_DETAILS = "employee_details";
    public static final String BUILDING = "building";
    public static final String BUILDING_DETAILS = "building_details";
    public static final String SCAN_ATTRIBUTES_TABLE_NAME = "scan.attributes.table.name";
    public static final String EMPLOYEE_TABLE = "employees";
    public static final String BUILDING_TABLE = "buildings";
    public static final String EMPLOYEES_WITH_CAFETRIA_CODE_TABLE = "employeesWithCafetriaCode";
    public static final byte[] EMPLOYEES_WITH_CAFETRIA_CODE_FAMILY = Bytes.toBytes("employeeCafeteriaCode");
    public static final String HDFS_OUTPUT_PATH = "hdfs://192.168.0.103:9000/output6";
}
