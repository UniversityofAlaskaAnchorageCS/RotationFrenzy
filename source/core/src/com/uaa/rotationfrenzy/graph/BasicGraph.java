package com.uaa.rotationfrenzy.graph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.uaa.rotationfrenzy.RotationFrenzy;

// This class will be used to hold/display the graph information like omega vs time, etc
public class BasicGraph {

    ShapeRenderer shapeRenderer;
    Array<Vector2> points;
    Vector2 penPosition;
    Rectangle rect;

    String title;

    public BasicGraph() {
        this(new Vector2(100, 100));
    }

    public BasicGraph(Vector2 position) {
        this(position, "Fake graph title");
    }
    public BasicGraph(Vector2 position, String graphTitle){
        shapeRenderer = new ShapeRenderer();
        points = new Array<Vector2>();
        penPosition = position;
        this.title = graphTitle;

        int width = 200;
        rect = new Rectangle(position.x-width, position.y, width, 200);
    }

    public void addPoint(Vector2 point){
        point.add(penPosition);     // Adjust the drawing position based on where the graph is located.

        // If we have points, make sure we only add a new point whenever the "X" value changes
        // Reduces the size of our Array as we don't have as many elements in it
        if (points.size > 0) {
            Vector2 firstPoint = points.first();

            // As long as the X values are different (They were converted to INT), add the point
            if (point.x != firstPoint.x)
                points.add(point);

        }else{
            points.add(point);  // No points yet, add the first point
        }
    }

    // We need to remove points from the graph so it doesn't go on forever and cause memory errors.
    // Remove points outside th "Graph"
    private void pruneGraph(){
        float x = 0;
        while (points.size > 0)
            if (points.first().x < this.rect.x)
                points.removeIndex(0);
            else
                break;
    }

    // Move the points that graph uses to the left by 50 * change in time
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

    public void draw(float delta, final RotationFrenzy game){
        drawConnectedPoints(game.batch);
        game.batch.begin();
        game.font.draw(game.batch, title, rect.x,rect.y + rect.height + 20, rect.width, Align.center,true);
        game.batch.end();
    }

    // Draw all the points by connecting consecutive points in time.
    private void drawConnectedPoints(SpriteBatch batch){
        if (points.size > 1) {
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
