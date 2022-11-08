package com.example.flappybird;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class GameEngine
{

    BackgroundImage backgroundImage;
    Bird bird;
    static int gameState;
    ArrayList<tube> tubes;
    Random random;
    int score;
    int scoringTube;
    Paint scorePaint;


    public GameEngine(){
        backgroundImage = new BackgroundImage();
        bird = new Bird();
        gameState = 0;
        tubes = new ArrayList<>();
        random = new Random();
        for(int i = 0;i < AppConstants.numberOfTubes; i++)
        {
            int tubeX = AppConstants.SCREEN_WIDTH + i * AppConstants.distanceBetweenTubes;
            int topTubeOffsetY = AppConstants.minTubeOffsetY + random.nextInt(AppConstants.maxTubeOffsetY - AppConstants.minTubeOffsetY);
            tube tube = new tube(tubeX, topTubeOffsetY);
            tubes.add(tube);
        }

        score = 0;
        scoringTube = 0;
        scorePaint = new Paint();
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(80);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    public void updateAndDrawTubes(Canvas canvas)
    {
        if(gameState == 1)
        {
            if((tubes.get(scoringTube).getTubeX() < bird.getX() + AppConstants.getBitmapBackground().getBirdHeight())
            && (tubes.get(scoringTube).getTopTubeOffsetY() > bird.getY() || tubes.get(scoringTube).getBottomTubeY() <
               (bird.getY() + AppConstants.getBitmapBackground().getBirdHeight())))
            {
                gameState = 2;
                AppConstants.getSoundBank().playHit();
                Context context = AppConstants.gameActivityContext;
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("puan", score);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
            else if(tubes.get(scoringTube).getTubeX() < bird.getX() - AppConstants.getBitmapBackground().getTubeWidth())
            {
                score++;
                scoringTube++;
                if(scoringTube > AppConstants.numberOfTubes -1)
                {
                    scoringTube = 0;
                }
                AppConstants.getSoundBank().playPoint();
            }
            for(int i = 0;i < AppConstants.numberOfTubes;i++)
            {
                if(tubes.get(i).getTubeX() <- AppConstants.getBitmapBackground().getTubeWidth())
                {
                    tubes.get(i).setTubeX(tubes.get(i).getTubeX() + AppConstants.numberOfTubes * AppConstants.distanceBetweenTubes);
                    int topTubeOffsetY = AppConstants.minTubeOffsetY + random.nextInt(AppConstants.maxTubeOffsetY - AppConstants.minTubeOffsetY + 1);
                    tubes.get(i).setTopTubeOffsetY(topTubeOffsetY);
                    tubes.get(i).setTubeColor();
                }
                tubes.get(i).setTubeX(tubes.get(i).getTubeX() - AppConstants.tubeVelocity);

                if(tubes.get(i).getTubeColor() == 0)
                {
                    canvas.drawBitmap(AppConstants.getBitmapBackground().getTubeTop(), tubes.get(i).getTubeX(), tubes.get(i).getTopTubeY(), null);
                    canvas.drawBitmap(AppConstants.getBitmapBackground().getTubeBottom(), tubes.get(i).getTubeX(), tubes.get(i).getBottomTubeY(), null);
                }
                else
                {
                    canvas.drawBitmap(AppConstants.getBitmapBackground().getRedTubeTop(), tubes.get(i).getTubeX(), tubes.get(i).getTopTubeY(), null);
                    canvas.drawBitmap(AppConstants.getBitmapBackground().getRedTubeBottom(), tubes.get(i).getTubeX(), tubes.get(i).getBottomTubeY(), null);
                }
            }
            canvas.drawText("puan: " + score, 20, 110, scorePaint);
        }
    }

    public void updateAndDrawableBackgroundImage(Canvas canvas)
    {
        backgroundImage.setX(backgroundImage.getX() - backgroundImage.getVelocity());
        if(backgroundImage.getX() <- AppConstants.getBitmapBackground().getBackgroundWidth())
        {
            backgroundImage.setX(0);
        }
        canvas.drawBitmap(AppConstants.getBitmapBackground().getBackground_game(), backgroundImage.getX(), backgroundImage.getY(), null);

        if(backgroundImage.getX() <- (AppConstants.getBitmapBackground().getBackgroundWidth()-AppConstants.SCREEN_WIDTH))
        {
            canvas.drawBitmap(AppConstants.getBitmapBackground().getBackground_game(), backgroundImage.getX() +
                    AppConstants.getBitmapBackground().getBackgroundWidth(),backgroundImage.getY(),null);
        }
    }

    public void updateAndDrawBird(Canvas canvas)
    {
        if(gameState == 1)
        {
            if (bird.getY() < (AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBackground().getBirdHeight()) || bird.getVelocity() < 0)
            {
                bird.setVelocity(bird.getVelocity() + AppConstants.gravity);
                bird.setY(bird.getY() + bird.getVelocity());
            }
        }
        int currentFrame = bird.getCurrentFrame();
        canvas.drawBitmap(AppConstants.getBitmapBackground().getBird(currentFrame), bird.getX(), bird.getY(), null);
        currentFrame++;
        if(currentFrame > bird.maxFrame)
        {
            currentFrame = 0;
        }
        bird.setCurrentFrame(currentFrame);
    }
}
