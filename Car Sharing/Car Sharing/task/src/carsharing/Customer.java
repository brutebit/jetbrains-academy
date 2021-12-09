package carsharing;

public class Customer extends Entity {
  private Integer rentedCarId = null;

  public Customer(int id, String name) {
    super(id, name);
  }

  public Integer getRentedCarId() {
    return rentedCarId;
  }

  public void setRentedCarId(Integer rentedCarId) {
    this.rentedCarId = rentedCarId;
  }
}
