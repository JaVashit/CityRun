package sjsu.bhub.cityrun.data;

public class DrawerMenuVO {
    private int statusIconId;
    private String statusName;
    private String statusUnit;
    private int status;

    public DrawerMenuVO(int statusIconId, String statusName, String statusUnit, int status) {
        this.statusIconId = statusIconId;
        this.statusName = statusName;
        this.statusUnit = statusUnit;
        this.status = status;
    }

    public int getStatusIconId() {
        return statusIconId;
    }

    public void setStatusIconId(int statusIconId) {
        this.statusIconId = statusIconId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusUnit() {
        return statusUnit;
    }

    public void setStatusUnit(String statusUnit) {
        this.statusUnit = statusUnit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
