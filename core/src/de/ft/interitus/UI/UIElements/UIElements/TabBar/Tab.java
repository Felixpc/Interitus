/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.UI.UIElements.UIElements.TabBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.ft.interitus.ProgramingSpace;
import de.ft.interitus.UI.UIElements.UIElements.Button;


public class Tab {
    private Button TabButton;

    private int index = -1;
    private Button CloseButton;
    private Color tabcolor=new Color(100f/255, 100f/255f, 100f/255f,1f);
    private Color mouseovertabcolor=new Color(140f/255, 140f/255f, 140f/255f,1f);
    private Color selected=new Color(163f/255f, 163f/255f, 163f/255f,1);
    private int CloseButtonDiameter=10;

    public Tab() {
        TabButton = new Button();
        CloseButton = new Button();
    }

    public void draw(boolean selectedTabindex,int y){
        ProgramingSpace.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        if(getTabButton().isMouseover()) {
            ProgramingSpace.shapeRenderer.setColor(getMouseovertabcolor());
        }else{
            ProgramingSpace.shapeRenderer.setColor(getTabcolor());

        }
        if(selectedTabindex){
            ProgramingSpace.shapeRenderer.setColor(getSelected());
        }
        ProgramingSpace.shapeRenderer.rect(getTabButton().getX(),y,getW(),getTabButton().getH());
        if(selectedTabindex) {
            ProgramingSpace.shapeRenderer.setColor(86f/255f, 138f/255f, 242f/255f, 1);
            ProgramingSpace.shapeRenderer.rect(getTabButton().getX(), y, getW(), 3);
        }
        ProgramingSpace.shapeRenderer.end();


        getCloseButton().setX((int) (getTabButton().getX()+getTabButton().getW()+1f));
        getCloseButton().setY((int) (getTabButton().getY()+(getTabButton().getH()/2-CloseButtonDiameter/2)));
        getCloseButton().setH(CloseButtonDiameter);
        getCloseButton().setW(CloseButtonDiameter);

        getTabButton().draw();
        getCloseButton().draw();
    }

    public Button getTabButton() {
        return TabButton;
    }

    public void setTabButton(Button tabButton) {
        TabButton = tabButton;
    }

    public Button getCloseButton() {
        return CloseButton;
    }

    public void setCloseButton(Button closeButton) {
        CloseButton = closeButton;
    }



    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTabcolor(Color tabcolor) {
        this.tabcolor = tabcolor;
    }

    public Color getTabcolor() {
        return tabcolor;
    }

    public void setMouseovertabcolor(Color mouseovertabcolor) {
        this.mouseovertabcolor = mouseovertabcolor;
    }

    public Color getMouseovertabcolor() {
        return mouseovertabcolor;
    }

    public Color getSelected() {
        return selected;
    }

    public void setSelected(Color selected) {
        this.selected = selected;
    }

    public int getW() {
        return TabButton.getW()+CloseButton.getW()+7;
    }

    public int getX() {
        return TabButton.getX();
    }
    public void setH(int h){
        TabButton.setH(h);
        //CloseButton.setH(h);
    }
    public void setX(int x){
        TabButton.setX(x);
        CloseButton.setX(TabButton.getW()+ TabButton.getX());
    }
    public void setY(int y){
        TabButton.setY(y);
    }
}
