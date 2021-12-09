package carsharing;

public class Car extends Entity {
  private int companyId;

  public Car(int id, String name, int companyId) {
    super(id, name);
    this.companyId = companyId;
  }

  public int getCompanyId() {
    return companyId;
  }

  public void setCompanyId(int companyId) {
    this.companyId = companyId;
  }
}
