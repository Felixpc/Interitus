package de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.Block.Parameter;
import de.ft.interitus.Block.ParameterType;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.BlockTypes.BlockCategories;
import de.ft.interitus.projecttypes.BlockTypes.BlockTopParameter;
import de.ft.interitus.projecttypes.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.projecttypes.ProjectTypes;
import de.ft.interitus.utils.ArrayList;

import java.awt.*;

public class TestOutput extends PlatformSpecificBlock implements ArduinoBlock{
    ArrayList<Parameter> parameters = new ArrayList<>();
    Parameter waitdauer;


    public TestOutput(ProjectTypes arduino) {
        super(arduino);


        waitdauer = new Parameter(0, AssetLoader.img_WaitBlock_warteZeit_Parameter, "Warte-Zeit", "Die Zeit die abgewartet werden soll", "ms",new ParameterType("int",true,false), true);


        parameters.add(waitdauer);

    }


    @Override
    public ArrayList<Parameter> getBlockParameter() {
        return parameters;
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public String getdescription() {
        return null;
    }

    @Override
    public ArrayList<BlockTopParameter> getblocktopparamter() {
        return null;
    }

    @Override
    public Color blockcolor() {
        return null;
    }

    @Override
    public BlockCategories getBlockCategoration() {
        return BlockCategories.Programm_Sequence;
    }

    @Override
    public Texture getSmallImage() {
        return AssetLoader.WaitBlock_smallimage;
    }

    @Override
    public Texture getImageRight() {
        return AssetLoader.WaitBlock_right;
    }

    @Override
    public Texture getImageLeft() {
        return AssetLoader.WaitBlock_left;
    }

    @Override
    public Texture getImageCenter() {
        return AssetLoader.WaitBlock_middle;
    }

    @Override
    public Texture getDescriptionImage() {
        return AssetLoader.WaitBlock_description_image;
    }


    @Override
    public int getWidth() {
        return 80;
    }

    @Override
    public boolean canbedeleted() {
        return true;
    }

    @Override
    public boolean canhasrightconnector() {
        return true;
    }

    @Override
    public boolean canhasleftconnector() {
        return true;
    }

    @Override
    public String getCode() {

        if( parameters.get(0).getDatawire()!=null){
            return parameters.get(0).getDatawire().varName+ " = 1+1;";

        }else {
            return "1+1";
        }
    }
}
