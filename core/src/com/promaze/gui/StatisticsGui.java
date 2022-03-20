package com.promaze.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.promaze.statistics.StatisticItem;
import com.promaze.statistics.Statistics;
import com.promaze.tools.stopwatch.TimeCycle;

public class StatisticsGui {

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private Statistics statistics;
    private int height, width;
    private Color border = new Color(0.3f,0.3f,0.3f,1.0f),content = new Color(0.5f,0.5f,0.5f,1.0f);
    private int border_thickness = 5;
    private BitmapFont font;

    public StatisticsGui(ShapeRenderer shapeRenderer, SpriteBatch batch, Statistics statistics, int height, int width) {
        this.shapeRenderer = shapeRenderer;
        this.batch = batch;
        this.statistics = statistics;
        this.height = height;
        this.width = width;
        font = new BitmapFont();

    }

    public void draw(int x, int y)
    {
        int spaceBetweenTimes = 70;

        shapeRenderer.rect(x,y ,width,height,border,border,border,border);
        shapeRenderer.rect(x +border_thickness,y + border_thickness,width - border_thickness * 2,height - border_thickness * 2,
                content,content,content,content);
        shapeRenderer.rect(x,y + height - 40,width,border_thickness,border,border,border,border);
        shapeRenderer.rect(x,y + height - 65,width,border_thickness,border,border,border,border);

        shapeRenderer.end();
        batch.begin();
        font.draw(batch,"Statistics",x - border_thickness,y - border_thickness* 3 + height,width,1,false);
        y-=30;
        int temp_y = y;
        x+=spaceBetweenTimes;
        font.draw(batch,"Time (mm:ss:ms)",x - border_thickness,y - border_thickness* 3 + height,width,1,false);
        y-=25;
        for(StatisticItem item : statistics.getStatisticItems())
        {
            font.draw(batch,convertTimeCycle(item),x - border_thickness,y - border_thickness* 3 + height,width,1,false);
            y-=27;
        }
        y = temp_y;
        x-=spaceBetweenTimes*2;
        font.draw(batch,"Maze Size",x - border_thickness,y - border_thickness* 3 + height,width,1,false);
        y-=25;
        for(StatisticItem item : statistics.getStatisticItems())
        {
            font.draw(batch,item.getMazeSize()+"",x - border_thickness,y - border_thickness* 3 + height,width,1,false);
            y-=27;
        }
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    private String convertTimeCycle(StatisticItem item)
    {
        String temp = "";
        temp+= (item.getTimeCycle().getMinutes() >=10)?(item.getTimeCycle().getMinutes()):('0'+item.getTimeCycle().getMinutes().toString());
        temp+=':';
        temp+= (item.getTimeCycle().getSeconds() >=10)?(item.getTimeCycle().getSeconds()):('0'+item.getTimeCycle().getSeconds().toString());
        temp+=':';
        temp+= (item.getTimeCycle().getMilliseconds() >=10)?(item.getTimeCycle().getMilliseconds()):('0'+item.getTimeCycle().getMilliseconds().toString());
        return temp;
    }
}
