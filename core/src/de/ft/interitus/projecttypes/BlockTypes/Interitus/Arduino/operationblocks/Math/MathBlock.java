/*
 * Copyright (c) 2020.
 * Copyright by Tim and Felix
 */

package de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.operationblocks.Math;

import com.badlogic.gdx.graphics.Texture;
import de.ft.interitus.Block.Parameter;
import de.ft.interitus.Block.ParameterType;
import de.ft.interitus.loading.AssetLoader;
import de.ft.interitus.projecttypes.Addons.Addon;
import de.ft.interitus.projecttypes.BlockTypes.BlockCategories;
import de.ft.interitus.projecttypes.BlockTypes.BlockTopParameter;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.ArduinoBlock;
import de.ft.interitus.projecttypes.BlockTypes.Interitus.Arduino.InitArduino;
import de.ft.interitus.projecttypes.BlockTypes.PlatformSpecificBlock;
import de.ft.interitus.projecttypes.ProjectTypes;
import de.ft.interitus.utils.ArrayList;

import java.awt.*;

public class MathBlock extends PlatformSpecificBlock {




    public MathBlock(ProjectTypes type, Addon addon) {
        super(type,addon);
        super.blockModis.add(new AdditionalModi());
        super.blockModis.add(new DifferenzModi());
        super.actBlockModiIndex = 0;



    }



    @Override
    public String getName() {
        return "Math";
    }

    @Override
    public String getdescription() {
        return "Zwei zahlen plus rechnen";
    }



    @Override
    public Color blockcolor() {
        return null;
    }

    @Override
    public BlockCategories getBlockCategoration() {
        return BlockCategories.Data_Operation;
    }

    @Override
    public Texture getSmallImage() {
        return AssetLoader.DigitalWrite_smallimage;
    }

    @Override
    public Texture getImageRight() {
        return AssetLoader.DigitalWrite_right;
    }

    @Override
    public Texture getImageLeft() {
        return AssetLoader.DigitalWrite_left;
    }

    @Override
    public Texture getImageCenter() {
        return AssetLoader.DigitalWrite_middle;
    }

    @Override
    public Texture getDescriptionImage() {
        return AssetLoader.DigitalWrite_description_image;
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
}
