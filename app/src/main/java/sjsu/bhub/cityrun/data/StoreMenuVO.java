package sjsu.bhub.cityrun.data;

public class StoreMenuVO {
    private int menuIconId;
    private String menuName;
    private String menuPrice;

    public StoreMenuVO(int menuIconId, String menuName, String menuPrice) {
        this.menuIconId = menuIconId;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public int getMenuIconId() {
        return menuIconId;
    }

    public void setMenuIconId(int menuIconId) {
        this.menuIconId = menuIconId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }
}
