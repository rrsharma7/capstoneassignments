// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: building.proto

package proto.building;

public interface BuildingOrBuilder extends
    // @@protoc_insertion_point(interface_extends:proto.building.Building)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *building_code;total_floor;companies_in_the_building;cafeteria_code
   * </pre>
   *
   * <code>int32 building_code = 1;</code>
   * @return The buildingCode.
   */
  int getBuildingCode();

  /**
   * <code>int32 total_floor = 2;</code>
   * @return The totalFloor.
   */
  int getTotalFloor();

  /**
   * <code>int32 companies_in_the_building = 3;</code>
   * @return The companiesInTheBuilding.
   */
  int getCompaniesInTheBuilding();

  /**
   * <code>int32 cafeteria_code = 4;</code>
   * @return The cafeteriaCode.
   */
  int getCafeteriaCode();
}