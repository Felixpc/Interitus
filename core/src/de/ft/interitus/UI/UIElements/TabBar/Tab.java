package de.ft.interitus.UI.UIElements.TabBar;

import de.ft.interitus.UI.UIElements.Button;

public class Tab {
    Button TabButton;
    Object objekt=null;
    int index=-1;
    public Tab(){
        TabButton=new Button();
    }

    public Button getTabButton() {
        return TabButton;
    }

    public void setTabButton(Button tabButton) {
        TabButton = tabButton;
    }

    public void setObjekt(Object objekt) {
        this.objekt = objekt;
    }

    public Object getObjekt() {
        return objekt;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
