package de.ft.robocontrol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuBar;
import de.ft.robocontrol.UI.UI;
import de.ft.robocontrol.utils.JSONParaser;
import de.ft.robocontrol.utils.PositionSaver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.io.File;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.input;

public class MainGame extends ApplicationAdapter {
	ShapeRenderer shapeRenderer;
	public static SpriteBatch batch;
	public static Texture img_block;
	public static Texture img_mouseover;
	public static Texture img_marked;

	public static ArrayList<Block> blocks = new ArrayList<Block>();

	public static OrthographicCamera cam;
	public static Viewport viewport;
	public static Component saver;


	public static Stage stage;
	public static MenuBar menuBar;
	//BlockUpdate bu[] = new BlockUpdate[0];

	@Override
	public void create () {
		shapeRenderer=new ShapeRenderer();
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScreenViewport(cam);
		batch = new SpriteBatch();

		img_block=new Texture("block.png");
		img_mouseover=new Texture("block_mouseover.png");
		img_marked=new Texture("block_marked.png");

		VisUI.load(VisUI.SkinScale.X1);
		stage = new Stage(viewport);
		final Table root = new Table();
		root.setFillParent(true);
		stage.addActor(root);
		Gdx.input.setInputProcessor(stage);


		menuBar = new MenuBar();
		menuBar.setMenuListener(new MenuBar.MenuBarListener() {
			@Override
			public void menuOpened (Menu menu) {
				System.out.println("Opened menu: " + menu.getTitle());
			}

			@Override
			public void menuClosed (Menu menu) {
				System.out.println("Closed menu: " + menu.getTitle());
			}
		});

		root.add(menuBar.getTable()).expandX().fillX().row();
		root.add().expand().fill();
		UI.createMenus();

		/*
		for(int i=0;i < blocks.length;i=i+1){
			System.out.println("eine runde"+i);
			blocks[i]=new Block(i,i*100,100,100,30);
			bu[i]=new BlockUpdate(blocks[i]);
			bu[i].start();
			//blocks[i].setWH(100,30);
			//blocks[i].setPosition(i*blocks[i].getW(),100);

		}
		 */


for(int i=0;i<1;i=i+1) {
	blocks.add(new Block(i, i * 150, 100, 150, 70));


}
//blocks.get(0).setRight(blocks.get(1));


		cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);






	}




	@Override
	public void render () {


		PositionSaver.save();




		//System.out.println(Var.mousepressedold);
		//System.out.println(blocks.get(1).getLeft());
		cam.update();
		//Gdx.gl.glClearColor(1,1,1, 1);
		Gdx.gl.glClearColor(0,0,0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		stage.draw();
/*
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.end();

*/

		if(input.isKeyJustPressed(Input.Keys.INSERT)){
			blocks.add(new Block(blocks.size(), 100, 200, 150, 70));
			System.out.println(blocks.size());
		}

		if(input.isKeyJustPressed(Input.Keys.O)) {
			 Thread open = new Thread() {
				 @Override
				 public void run() {
					 JFileChooser fileChooser = new JFileChooser();
					 fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					 fileChooser.setFileFilter(new FileNameExtensionFilter("Projektdatei (.rac)", "rac"));
					 int result = fileChooser.showOpenDialog(saver);
					 if (result == JFileChooser.APPROVE_OPTION) {
						 File selectedFile = fileChooser.getSelectedFile();
						 System.out.println("Selected file: " + selectedFile.getAbsolutePath());
						 FileHandle handle = Gdx.files.internal(selectedFile.getAbsolutePath());
						 Var.path = selectedFile.getAbsolutePath();
						 JSONParaser.load(handle);

					 }
				 }
			 } ;
			open.start();
		}

		if(input.isKeyJustPressed(Input.Keys.S)) {
/*




 */

			Thread save = new Thread() {
				@Override
				public void run() {


					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Speichern unter...");

					int userSelection = fileChooser.showSaveDialog(saver);
					fileChooser.setMultiSelectionEnabled(false);


					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File fileToSave = fileChooser.getSelectedFile();
						System.out.println("Save as file: " + fileToSave.getAbsolutePath()+".rac");

						if(fileToSave.getAbsolutePath().contains(".rac")) {
							Var.path = fileToSave.getAbsolutePath();
							JSONParaser.writerarray(Gdx.files.absolute(fileToSave.getAbsolutePath()));

						}else{
							Var.path = fileToSave.getAbsolutePath()+".rac";
							JSONParaser.writerarray(Gdx.files.absolute(fileToSave.getAbsolutePath()+".rac"));
						}
						System.out.println(Var.path);
				}
			};
			//SAVE DATA



			};

			save.start();


			//GET FOLDER
			/*
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Projekt-Ordner wählen");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (fileChooser.showOpenDialog(MainGame) == JFileChooser.CUSTOM_DIALOG) {
				File file = fileChooser.getSelectedFile();
				// load from file
			}

			 */
		}

if(!Var.isloading) {
	Block Temp = null;
	for (int i = 0; i < blocks.size(); i = i + 1) {
		batch.begin();


		if (blocks.get(i).isMarked()) {
			Temp = blocks.get(i);
		} else {
			blocks.get(i).draw(batch,shapeRenderer);
		}

		batch.end();
		if (blocks.get(i).isMarked()) {


			if (input.isKeyJustPressed(Input.Keys.FORWARD_DEL)) {
				blocks.get(i).delete();
			}


		}


		if (Temp != null) {

			batch.begin();
			Temp.draw(batch,shapeRenderer);
			batch.end();
		}
		//System.out.println(blocks.get(i).isMarked() + "  id: "+blocks.get(i).getIndex());

	}
}
		/*

		for(int b=0;b<blocks.size();b=b+1) {
			Block block = blocks.get(b);
			if(!blocks.get(b).blockupdate.toggle) {
				batch.draw(img_block, block.getX(), block.getY(), block.getW(), block.getH());
			}else{
				batch.draw(img_selected, block.getX(), block.getY(), block.getW(), block.getH());
			}

		}
		*/



	}


	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		//blocks[0].delete();
		stage.getViewport().update(width, height, true);



		viewport.update(width, height);
	}


	@Override
	public void dispose () {
		batch.dispose();
		img_block.dispose();
	}
}
