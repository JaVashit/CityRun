package sjsu.bhub.cityrun.data;

public class DrawerMenuVO {
    private int menuIconId;
    private String menuName;
    private String menuUnit;

    public DrawerMenuVO(int menuIconId, String menuName, String menuUnit) {
        this.menuIconId = menuIconId;
        this.menuName = menuName;
        this.menuUnit = menuUnit;
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

    public String getMenuUnit() {
        return menuUnit;
    }

    public void setMenuUnit(String menuUnit) {
        this.menuUnit = menuUnit;
    }
}
