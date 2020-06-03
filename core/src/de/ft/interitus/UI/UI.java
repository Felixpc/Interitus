package de.ft.interitus.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kotcrab.vis.ui.widget.MenuBar;
import com.kotcrab.vis.ui.widget.MenuItem;
import de.ft.interitus.DisplayErrors;
import de.ft.interitus.ProgrammingSpace;
import de.ft.interitus.Settings;
import de.ft.interitus.UI.inputfields.check.InputManager;
import de.ft.interitus.UI.settings.SettingsUI;
import de.ft.interitus.UI.setup.SetupWindow;
import de.ft.interitus.Var;
import de.ft.interitus.data.programm.Data;
import de.ft.interitus.data.user.changes.SaveChanges;
import de.ft.interitus.data.user.experience.ExperienceManager;
import de.ft.interitus.utils.RoundRectangle;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static de.ft.interitus.UI.MenuBar.createSubMenu;

public class UI {
    static final Table root = new Table();
    public static Stage stage;
    private static final Vector2 lastframecamposition = new Vector2(ProgrammingSpace.cam.position.x, ProgrammingSpace.cam.position.y);

    protected static MenuItem recent;
    protected static MenuItem revert;
    protected static MenuItem redo;
    protected static MenuItem copy;
    protected static MenuItem paste;
    protected static MenuBar menuBar;
    public static SettingsUI set;

    private static boolean issettingsuiopend = false;
    private static boolean issetupuiopend = false;
    Vector3 pos = new Vector3();

    public static void userresize(){
        if(Gdx.input.getX()>UIVar.BlockBarW+UIVar.abstandvonRand && Gdx.input.getX()<UIVar.BlockBarW+UIVar.abstandvonRand*2){
            System.out.println("mouse over");
        }
    }

    public static void updatedragui(ShapeRenderer renderer, boolean flaeche, SpriteBatch batch) {
        userresize();
       // Var.w=Gdx.graphics.getWidth();
        //Var.h=Gdx.graphics.getHeight();
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        if (Settings.darkmode) {
            renderer.setColor(Colors.darkmode_middle);
        } else {
            renderer.setColor(Colors.whitearea);
        }


        if (flaeche == true) {
            RoundRectangle.abgerundetesRechteck(renderer, UIVar.abstandvonRand, UIVar.untenhohe + UIVar.abstandvonRand, Gdx.graphics.getWidth() - UIVar.abstandvonRand * 2, Gdx.graphics.getHeight() - UIVar.untenhohe + UIVar.abstandvonRand - 45 - UIVar.abstandvonRand, UIVar.radius);
        } else {
            if (Settings.darkmode) {
                renderer.setColor(Colors.darkmode_blockbar);

            } else {
                renderer.setColor(Colors.whitearea);
            }
            UIVar.BlockBarX=UIVar.abstandvonRand;
            UIVar.BlockBarY=UIVar.abstandvonRand;
            UIVar.BlockBarW=Gdx.graphics.getWidth() - UIVar.abstandvonRand * 2 - UIVar.unteneinteilung;
            UIVar.BlockBarH=UIVar.untenhohe - UIVar.abstandvonRand;
            RoundRectangle.abgerundetesRechteck(renderer, UIVar.abstandvonRand, UIVar.abstandvonRand, UIVar.BlockBarW, UIVar.BlockBarH, UIVar.radius);
            if (Settings.darkmode) {
                renderer.setColor(Colors.darkmode_connections);
            } else {
                renderer.setColor(Colors.whitearea);
            }
            RoundRectangle.abgerundetesRechteck(renderer, Gdx.graphics.getWidth() - UIVar.unteneinteilung, UIVar.abstandvonRand, UIVar.unteneinteilung - UIVar.abstandvonRand, UIVar.untenhohe - UIVar.abstandvonRand, UIVar.radius);
        }
        renderer.end();





    }

    public static void init() {


        stage = new Stage(ProgrammingSpace.UIviewport, ProgrammingSpace.UIbatch);


        root.setFillParent(true);
        stage.addActor(root);

        InputManager.addProcessor(stage);
        InputManager.updateMultiplexer();

        set = new SettingsUI();

        menuBar = new MenuBar();


        root.add(menuBar.getTable()).expandX().fillX().row();
        root.add().expand().fill().row();


        de.ft.interitus.UI.MenuBar.createMenus();


        Thread UIthread = new Thread() {

            @Override
            public void run() {
                Timer time = new Timer();
                time.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        try {

                            ////////////recent//////////////////////////


                            for (int i = 0; i < Data.path.size(); i++) {
                                if (!(new File(Data.path.get(i)).exists())) {
                                    Data.path.remove(i);
                                    Data.filename.remove(i);
                                }
                            }

                            recent.setDisabled(Data.path.size() == 0);


                            ///////////////////////////////////


                            /////////////revert//////////////

                            revert.setDisabled(SaveChanges.checkstack());

                            ///////Redo//////////////


                            redo.setDisabled(SaveChanges.checkredostack());

                        } catch (Exception e) {
                            DisplayErrors.error = e;
                            e.printStackTrace(); //for debug to find errors
                        }


                        //Check íf Settings is open///////////
                        if (!issettingsuiopend && SettingsUI.isopend()) {

                            issettingsuiopend = true;
                            ExperienceManager.settingstimetemp = (double) System.currentTimeMillis() / (double) 3600000;

                        }

                        if (issettingsuiopend && !SettingsUI.isopend()) {

                            issettingsuiopend = false;
                            ExperienceManager.settingsthistime = ExperienceManager.settingsthistime + (double) System.currentTimeMillis() / (double) 3600000 - ExperienceManager.settingstimetemp;
                        }

                        ////////////////////////////////

                        //Check if Setup is open///

                        if (!issetupuiopend && SetupWindow.isopend()) {

                            issetupuiopend = true;
                            ExperienceManager.setuptimetemp = (double) System.currentTimeMillis() / (double) 3600000;

                        }

                        if (issetupuiopend && !SetupWindow.isopend()) {

                            issetupuiopend = false;
                            ExperienceManager.setupthistime = ExperienceManager.setupthistime + (double) System.currentTimeMillis() / (double) 3600000 - ExperienceManager.setuptimetemp;
                        }


                        ///////////////////Check if shortcut is pressed//////////


                        ///////////////////////////
                    }
                }, 0, 500);
            }
        };


        UIthread.start();
        CheckShortcuts.loadArrayList();

    }

    public static void update() {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));

        //root.setPosition(0,0);


        root.setPosition(ProgrammingSpace.UIcam.position.x - ((float) Gdx.graphics.getWidth()) / 2, ProgrammingSpace.UIcam.position.y - ((float) Gdx.graphics.getHeight()) / 2);
        stage.draw();

        recent.setSubMenu(createSubMenu(Data.filename.size(), GetStringArray(Data.filename)));

    }


    public static String[] GetStringArray(ArrayList<String> arr) {

        // declaration and initialise String Array
        String[] str = new String[arr.size()];

        // ArrayList to Array Conversion
        for (int j = 0; j < arr.size(); j++) {


            // Assign each value to String array
            str[j] = arr.get(j);
        }

        return str;
    }

    public static void updateView(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
