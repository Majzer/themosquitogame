package hu.csanysoft.mosquitogame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanysoft.mosquitogame.GlobalClasses.Assets;
import hu.csanysoft.mosquitogame.MyBaseClasses.Scene2D.MyStage;
import hu.csanysoft.mosquitogame.MyBaseClasses.Scene2D.ShapeType;

public class GameStage extends MyStage {

    float posA, posB, length, mosquitoWidth, travelLength, speedMan, speedMosquito, lengthToStart;
    Calcuations c;
    ManActor manActor1, manActor2;
    MosquitoActor mosquitoActor;
    boolean canGo, end;

    public GameStage(Batch batch, TheMosquitoGame game) {
        super(new ExtendViewport(1024f,768f), batch, game);

        c = new Calcuations();

        posA=100;
        length = 300;
        mosquitoWidth= 20;
        speedMan= .1f;
        speedMosquito= 2;
        posB=posA+length;
        travelLength=1000;

        manActor1 = new ManActor(Assets.manager.get(Assets.MAN_TEXTURE),posA,speedMan);
        manActor1.setId((short)0);
        manActor2 = new ManActor(Assets.manager.get(Assets.MAN_TEXTURE), posB+manActor1.getWidth(), 0-speedMan);
        manActor2.setId((short)1);
        manActor2.setFlip(true, false);
        mosquitoActor = new MosquitoActor(Assets.manager.get(Assets.MOSQUITO_TEXTURE),posA,speedMosquito, mosquitoWidth);
        canGo=false;  end = false;
        if(!canGo)mosquitoActor.setSpeed(0);


        addActor(manActor1);
        addActor(manActor2);
        addActor(mosquitoActor);

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                System.out.println("x = " + x);
                System.out.println("y = " + y);
            }
        });

        lengthToStart = c.getLenghtToStart(mosquitoWidth,speedMan,speedMosquito,travelLength);
    }

    @Override
    public void init() {

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(!canGo) {
            mosquitoActor.setX(manActor1.getX());
        }
        length = manActor2.getX()-manActor1.getX();
        if(length<=lengthToStart){
            canGo=true;
        }
        //System.out.println("canGo = " + canGo);
        //System.out.println("speedMosquito = " + speedMosquito);
        System.out.println("lengthToStart = " + lengthToStart);
        System.out.println("length = " + length);

        if(length<=mosquitoWidth+manActor1.getWidth()) {
            manActor1.setSpeed(0);
            manActor2.setSpeed(0);
            mosquitoActor.setSpeed(0);
            end = true;
        }
    }
}
