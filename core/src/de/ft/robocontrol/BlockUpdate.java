package de.ft.robocontrol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.ft.robocontrol.utils.PositionSaver;
import jdk.tools.jaotc.Main;

import java.util.Timer;
import java.util.TimerTask;


public class BlockUpdate extends Thread {
Block block;
boolean toggle;



public Timer time;

    BlockUpdate(Block block) {
        this.block=block;

    }

    @Override
    public void run() {
        time = new Timer();

        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {


            toggle = de.ft.robocontrol.utils.CheckKollision.checkmousewithblock(block);


            if(de.ft.robocontrol.utils.CheckKollision.checkmousewithblock(block,Var.mousepressedold)&&Gdx.input.isButtonPressed(0) && Var.ismoving==false){
                block.setMarked(true);



                int feld=2;
                if(Math.abs(Var.mousepressedold.x-MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x)>feld  ||  Math.abs(Var.mousepressedold.y-MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).y)>feld){
                    if(block.isMoving()==false  && Var.ismoving==false) {
                        System.out.println("moved");
                        Var.unterschiedsave = new Vector2(MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x  -  block.getX(),MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).y  -  block.getY());

                        //block.setX(    (int)MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x );
                        System.out.println((int)MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x-block.getX());
                        block.setMoving(true);
                        Var.ismoving=true;
                    }




                }



            }

                if(block.isMoving()){
                    block.setX((int)(MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).x-Var.unterschiedsave.x));
                    block.setY((int)(MainGame.viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0)).y-Var.unterschiedsave.y));
                }


            if(de.ft.robocontrol.utils.CheckKollision.checkmousewithblock(block,Var.mousepressedold)==false&&Gdx.input.isButtonPressed(0)){
                block.setMarked(false);
                }







          //block.setIndex( MainGame.blocks.indexOf(block));

/*

            if(toggle && Gdx.input.isButtonPressed(0) && !Var.indraganddrop){
                block.setMarked(true);
                Var.markedblock = block;
                Var.indraganddrop = true;

            }


            if(!Gdx.input.isButtonPressed(0)) {
                Var.indraganddrop=false;
            }




            if(toggle&&!block.isMarked()) {
                Var.markedblock.setMarked(false);
                block.setMarked(true);
                Var.markedblock = block;
            }

            if(!toggle && Gdx.input.isButtonPressed(0)&&!Var.indraganddrop){
                block.setMarked(false);
                Var.markedblock = null;
            }
            */


                    //System.out.println(block.getIndex()+ " " +de.ft.robocontrol.utils.CheckKollision.checkmousewithcar(block));
                    //System.out.println(block.getIndex());


                            }
        }, 0,20);

    }

}
