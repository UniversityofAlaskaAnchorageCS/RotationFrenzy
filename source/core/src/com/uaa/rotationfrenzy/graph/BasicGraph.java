package com.uaa.rotationfrenzy.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

import javax.print.StreamPrintService;

// This class will be used to hold/display the graph information like omega vs time, etc
public class BasicGraph {

    ShapeRenderer shapeRenderer;

    Array<Vector2> points;

    Vector2 penPosition;

    Rectangle rect;

    public BasicGraph() {
        this(new Vector2(100, 100));
    }

    public BasicGraph(Vector2 position) {
        shapeRenderer = new ShapeRenderer();
        points = new Array<Vector2>();
        penPosition = position;

        rect = new Rectangle(position.x-150, position.y-50, position.x+50, position.y+50);
    }

    public void addPoint(Vector2 point){
        point.add(penPosition);

        if (points.size > 0) {
            Vector2 firstPoint = points.first();

            if (point.x != firstPoint.x) {
                points.add(point);
                //System.out.println(point);
            }

        }else{
            points.add(point);
        }
    }

    private void pruneGraph(){
        float x = 0;
        while (points.size > 0)
            if (points.first().x < this.rect.x)
                points.removeIndex(0);
            else
                break;
    }

    private void scrollGraph(float delta){
        for (Vector2 point: points){
            point.x -= (delta * 50);
        }
    }

    public void update(float delta, float y){
        this.scrollGraph(delta);
        this.addPoint(new Vector2((int)delta, y));
        this.pruneGraph();
    }

    public void draw(float delta, SpriteBatch batch){
        int x = 100, y=100, y2=150,x2=150, width = 20, height = 20, radius = 50;

        drawConnectedPoints(batch);


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(1, 1, 0, 1);
//        shapeRenderer.line(x, y, x2, y2);
//        shapeRenderer.rect(x, y, width, height);
//        shapeRenderer.circle(x, y, radius);
//        shapeRenderer.end();
    }

    private void drawConnectedPoints(SpriteBatch batch){
        if (points.size > 1) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 1, 0, 1);

            Vector2 start = points.get(0);
            Vector2 end;
            for (int i = 1; i < points.size; i++) {
                end = points.get(i);
                shapeRenderer.line(start, end);
                start = end;
            }

            shapeRenderer.end();
        }
    }
}
