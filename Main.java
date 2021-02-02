/** Welcome to the drawing board! User Instructions:
 * Hold the left mouse button and drag for free hand drawing
 * CLick the left mouse button twice to draw a straight line
 * Click the right mouse button to start at a new location
 * Press space bar to clear the board
 */

/**
 * author: Chelsea Lee
 * date: 1/29/21
 * version 1.0
 */

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
public class Main {

    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(1000, 600));
            Display.setTitle("DrawingBoard");
            //Display.setInitialBackground(256, 256, 256);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        //OpenGL Initialization
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, 1000, 600, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        // initialize two x-y coordinates
        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        int switchRecording = 0;  // this variable constantly records one of the two coordinates
        boolean start = false;

        while (!Display.isCloseRequested()) { // while the drawing board is open

            if(Mouse.isButtonDown(0)) {       // if the left mouse button is pressed
                if (switchRecording == 0) {   // toggle between the two coordinates (x1,y1)(x2,y2) for recording
                        x1 = Mouse.getX();
                        y1 = Mouse.getY();

                    switchRecording = 1;
                }
                else {
                        x2 = Mouse.getX();
                        y2 = Mouse.getY();

                    switchRecording = 0;
                    start = true;   //changes to true when both non-zero coordinates are recorded to start drawing
                }
            }


            /* if the right mouse button is pressed, the
               locations of the coordinates are cleared and you can't draw yet
               - resets the location of the new sequence of recordings
             */
            if(Mouse.isButtonDown(1)) {
                start = false;
                switchRecording = 0;
            }

            // Once you have the non-zero coordinates, the lines will be drawn
            if (start == true) {
                glBegin(GL_LINES);
                glVertex2i(x1, 600 - y1);
                glVertex2i(x2, 600 - y2);
                glEnd();
            }

            // Once you press the space bar, the screen will clear
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            }

            //Swaps the buffers and make what has been drawn visible
            Display.update();
            Display.sync(60);

        }
        Display.destroy(); //destroys the native Display and cleans up any resources used by it
        System.exit(0);
    }

    public static void main(String[] args) {
        new Main();
    }
}