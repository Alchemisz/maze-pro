package com.promaze.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class OurButton {

    private int x,y,width,height,border_thickness = 3,textOffsetY = 0;
    private Color border,content;
    private Color bright = new Color(0.2f,0.2f,0.2f,1f);
    private String text;
    public boolean isActive = true, isEnabled = false;

    public void setText(String text) {
        this.text = text;
    }

    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public void draw(SpriteBatch batch)
    {
        if(!isActive)return;

        Color temp = new Color();
        Color content2 = new Color().add(content);
        temp.add(content2);
        temp.add(bright);

        if(isEnabled) {
            content2.add(bright);
            temp.add(bright);
        }

            shapeRenderer.rect(x,y ,width,height,border,border,border,border);
        if(isHighlighted()) shapeRenderer.rect(x +border_thickness,y + border_thickness,width - border_thickness * 2,height - border_thickness * 2,
                temp,temp,temp,temp);
        else shapeRenderer.rect(x +border_thickness,y + border_thickness,width - border_thickness * 2,height - border_thickness * 2,
                content2,content2,content2,content2);

        shapeRenderer.end();
        batch.begin();
        font.draw(batch,text,x - border_thickness,y + border_thickness + height/2 + textOffsetY,width,1,false);
        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    public OurButton(int x, int y, int width, int height, Color border, Color content, String text, ShapeRenderer shapeRenderer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.border = border;
        this.content = content;
        this.text = text;
        this.shapeRenderer = shapeRenderer;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isPressed()
    {
        if(!isActive)return false;
        return isHighlighted() && Gdx.input.justTouched();
    }

    public void setTextOffsetY(int textOffsetY) {
        this.textOffsetY = textOffsetY;
    }

    public boolean isHighlighted()
    {
        float x2 = x+width;
        float y2 = y+height;
        float mouseX = Gdx.input.getX();
        float mouseY = 720 - Gdx.input.getY();

        if(x<mouseX && mouseX<x2)
            if(y<mouseY && mouseY<y2)
                return true;
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public OurButton(int x, int y, int width, int height,String text, ShapeRenderer shapeRenderer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        border = new Color(0.3f,0.3f,0.3f,1);
        content = new Color(0.5f,0.5f,0.5f,1);
        this.text = text;
        this.shapeRenderer = shapeRenderer;
        font = new BitmapFont();
    }
}
