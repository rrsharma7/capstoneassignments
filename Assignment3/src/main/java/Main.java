import util.Constant;

public class Main {

    public static void main(String[] args) {

        CSVToProto csVtoProto= new CSVToProto();
        csVtoProto.printData(Constant.employeeCSVPath , true);
        csVtoProto.printData(Constant.buildingCSVPath ,false);
    }
}