package com.uaa.rotationfrenzy.graph;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.entity.Rotatable;

import java.awt.Shape;

public class BasicGraph {

    ShapeRenderer shapeRenderer;

    Array<Vector2> points;

    Vector2 penPosition;

    Rectangle rect;

    String title;

    float lastOmega;

    static String type = "alpha";


    public BasicGraph() {
        this(new Vector2(100, 100));
    }

    public BasicGraph(Vector2 position) {
        this(position, "Angle Graph (" + type + " vs time)");
    }

    public BasicGraph(Vector2 position, String graphTitle) {
        shapeRenderer = new ShapeRenderer();
        points = new Array<Vector2>();
        penPosition = position;
        this.title = graphTitle;
        int width = 200;
        rect = new Rectangle(position.x - width, position.y - 100, width, 200);
    }


    public void setGraphType(String type) {
        if(graphType)
    }


    public void addPoint(Vector2 point) {
        point.add(penPosition);

        if (points.size > 0) {
            Vector2 firstPoint = points.first();

            if (point.x != firstPoint.x) {
                points.add(point);
            }
        }
        else {
            points.add(point);
        }
    }

    private void pruneGraph() {
        float x = 0;
        while (points.size > 0) {
            if (points.first().x < this.rect.x) {
                points.removeIndex(0);
            }
            else {
                break;
            }
        }
    }

    private void scrollGraph(float delta) {
        for(Vector2 point: points) {
            point.x -= (delta * 50);
        }
    }

    int omega = 0;

    public void update(float delta, float y, float deltaV) {
        this.scrollGraph(delta);

        if (type.equalsIgnoreCase("omega")) {
            int omega = (int) (deltaV / delta);
            this.addPoint(new Vector2((int) delta, omega));
        }
        else if (type.equalsIgnoreCase("alpha")){
            int omega = (int)(deltaV / delta);
            int alpha = (int)((omega - lastOmega)/delta/1000);
            this.addPoint(new Vector2((int)delta, alpha));
            lastOmega = omega;
        }
        else {
            this.addPoint(new Vector2((int)delta, y));
        }

        this.pruneGraph();
    }

    public void draw(float delta, final RotationFrenzy game, Camera camera) {
        drawConnectedPoints(game.batch, camera);
        game.batch.begin();
        game.font.draw(game.batch, title, rect.x, rect.y + rect.height + 20, rect.width, Align.center, true);
        game.batch.end();
    }

    private void drawConnectedPoints(SpriteBatch batch, Camera camera) {
        if (points.size > 1) {
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.YELLOW);

            Vector2 start = points.get(0);
            Vector2 end;

            for (int i = 1; i < points.size; i++) {
                end = points.get(i);
                shapeRenderer.line(start, end);
                start = end;
            }

            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            shapeRenderer.end();
        }
    }
}