package sjsu.bhub.cityrun;

public class DrawerMenuVO {
    private int menuIconId;
    private String menuName;

    public DrawerMenuVO(int menuIconId, String menuName) {
        this.menuIconId = menuIconId;
        this.menuName = menuName;
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
}
