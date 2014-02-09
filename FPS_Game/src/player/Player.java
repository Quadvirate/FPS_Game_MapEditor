package player;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import org.lwjgl.opengl.Display;
import static convenience.Utility.*;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.lwjgl.opengl.GL11.*;

public class Player
{

	private double xPos, yPos, zPos, camAngX, camAngY, turnSensitivity, turnSpeed, mouseSensitivity, moveSpeed;

	private Robot mouseControl;

	public Player()
	{
		xPos = yPos = zPos = camAngX = camAngY = 0;
		
		//	inverse increasing
		turnSensitivity = 1; // default 1, recommended to stay such
		turnSpeed = 15; // default 20 ( how fast everything moves )
		mouseSensitivity = 0; // default 20 ( box for mouse to move in )
		
		//	regular
		moveSpeed = 60; // default 50

		try
		{
			mouseControl = new Robot();
		}
		catch( AWTException e )
		{}
	}

	public void move()
	{
		//	turning algorithm
		double camAng = toRadians( camAngX + 90 );
		if( keyPressed( "w" ) )
		{
			zPos += ( sin( camAng ) ) * moveSpeed;
			xPos += cos( camAng ) * moveSpeed;
		}
		if( keyPressed( "a" ) )
		{
			zPos += ( sin( camAng - PI / 2 ) ) * moveSpeed;
			xPos += cos( camAng - PI / 2 ) * moveSpeed;
		}
		if( keyPressed( "s" ) )
		{
			zPos -= ( sin( camAng ) ) * moveSpeed;
			xPos -= cos( camAng ) * moveSpeed;
		}
		if( keyPressed( "d" ) )
		{
			zPos -= ( sin( camAng - PI / 2 ) ) * moveSpeed;
			xPos -= cos( camAng - PI / 2 ) * moveSpeed;
		}
		
		//	moving algorithm
		double absMouseX = MouseInfo.getPointerInfo().getLocation().x;
		double absMouseY = MouseInfo.getPointerInfo().getLocation().y;
		if( abs( - ( Display.getX() + Display.getWidth() / 2 - absMouseX ) / mouseSensitivity ) > turnSensitivity ) camAngX += - ( Display.getX() + Display.getWidth() / 2 - absMouseX ) / turnSpeed;
		if( abs( - ( Display.getY() + Display.getHeight() / 2 - absMouseY ) / mouseSensitivity ) > turnSensitivity ) camAngY += - ( Display.getY() + Display.getHeight() / 2 - absMouseY ) / turnSpeed / 2;
		if( absMouseX >= Display.getX() + Display.getWidth() / 2 + mouseSensitivity ) mouseControl.mouseMove( Display.getX() + Display.getWidth() / 2 + (int)mouseSensitivity, MouseInfo.getPointerInfo().getLocation().y );
		if( absMouseX <= Display.getX() + Display.getWidth() / 2 - mouseSensitivity ) mouseControl.mouseMove( Display.getX() + Display.getWidth() / 2 - (int)mouseSensitivity, MouseInfo.getPointerInfo().getLocation().y );
		if( absMouseY >= Display.getY() + Display.getHeight() / 2 + mouseSensitivity ) mouseControl.mouseMove( MouseInfo.getPointerInfo().getLocation().x, Display.getY() + Display.getHeight() / 2 + (int)mouseSensitivity );
		if( absMouseY <= Display.getY() + Display.getHeight() / 2 - mouseSensitivity ) mouseControl.mouseMove( MouseInfo.getPointerInfo().getLocation().x, Display.getY() + Display.getHeight() / 2 - (int)mouseSensitivity );
		
		glRotated( camAngY, 1, 0, 0 );
		glRotated( camAngX, 0, 1, 0 );
		glTranslated( xPos, yPos, zPos );
	}

}
