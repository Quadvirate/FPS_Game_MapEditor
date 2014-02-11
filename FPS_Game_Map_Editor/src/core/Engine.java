package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import player.Player;
import static org.lwjgl.opengl.GL11.*;
import static convenience.Utility.*;

public class Engine
{

	public Player testPlayer;

	private Scanner mapReader;
	private ArrayList< String > mapText;

	private boolean initComplete;

	private HashMap<String, Byte> map = new HashMap<String, Byte>();
	
	public int fps;
	private int fpsCounter;
	private long lastFPS;
	private boolean initFpsCounter;

	public void start()
	{
		setUpDisplay();
		setUpConfig();
		//	display until close requested or esc is pressed
		while( !Display.isCloseRequested() && !keyPressed( "escape" ) )
		{
			if( !initComplete )
			{
				init();
				initComplete = true;
			}
			manageFPS();
			mainLoop();
			Display.update();
			//	try to get as good as fps as possible for testing purposes
			Display.sync( 90 );
		}
		Display.destroy();
	}

	private void setUpDisplay()
	{
		try
		{
			for( DisplayMode current : Display.getAvailableDisplayModes() )
			{
				if( ( current.getWidth() == Display.getDesktopDisplayMode().getWidth() ) && ( current.getHeight() == Display.getDesktopDisplayMode().getHeight() ) )
				{
					System.out.println( "creating display" );
					Display.setDisplayMode( current );
					Display.setFullscreen( true );
					Display.create();
					System.out.println( "display created" );
					break;
				}
			}
		}
		catch( LWJGLException e )
		{
			System.out.println( "failed to enter fullscreen" );
			System.exit( 1 );
		}
	}

	private void setUpConfig()
	{
		glMatrixMode( GL_PROJECTION );
		glLoadIdentity();
		glFrustum( -Display.getWidth() / 2, Display.getWidth() / 2, -Display.getHeight() / 2, Display.getHeight() / 2, Display.getWidth() * 2, Display.getWidth() * 100 );
		glTranslated( 0, -Display.getHeight() / 2, -Display.getWidth() * 2 - .1 );
		glMatrixMode( GL_MODELVIEW );
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA );
		glEnable( GL_DEPTH_TEST );
		glDepthFunc( GL_LEQUAL );

		//	Invisible mouse
		Cursor emptyCursor = null;
		try
		{
			emptyCursor = new Cursor( 1, 1, 0, 0, 1, BufferUtils.createIntBuffer( 1 ), null );
			Mouse.setNativeCursor( emptyCursor );
		}
		catch( LWJGLException e1 )
		{}
	}
	
	private void manageFPS()
	{
		if( !initFpsCounter ) { lastFPS = (Sys.getTime() * 1000) / Sys.getTimerResolution(); initFpsCounter = true; }
		
	    if ( ( Sys.getTime() * 1000 ) / Sys.getTimerResolution() - lastFPS > 1000 )
	    {
	        fps = fpsCounter;
	        fpsCounter = 0;
	        lastFPS += 1000;
	    }
	    fpsCounter++;
	}

	private void loadMap()
	{
		//	load map into an array of Strings
		try
		{
			mapReader = new Scanner( new File( "res/map.txt" ) );
			mapText = new ArrayList< String >();
			while( mapReader.hasNextLine() )
				mapText.add( mapReader.nextLine() );
			System.out.println( "map loaded" );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "failed to load map" );
		}

		//	load map
		for( int i = 0; i < mapText.size(); i++ )
		{
			String[] currStr = mapText.get( i ).split( "," );
			byte val = Byte.valueOf( currStr[1] );
			map.put( currStr[2] + "," + currStr[3] + "," + currStr[4], val );
		}
	}

	private void init()
	{
		testPlayer = new Player();
		loadMap();
	}

	public int xPos, yPos, zPos;
	public boolean uPressed, jPressed, hPressed, kPressed, oPressed, lPressed, returnPressed;

	private void mainLoop()
	{
		//	background red
		glClearColor( .4f, .4f, .4f, 1 );
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

		//	draw map
		glPushMatrix();

		//	move player
		testPlayer.move();

		//	block size
		glScaled( 150, 150, 150 );

		glColor3f( 0, .3f, .3f );

		//	go through map
		for( String key : map.keySet() )
		{
			glPushMatrix();

			String[] currStr = key.split( "," );
			glTranslated( Integer.valueOf( currStr[0] ) * 2, Integer.valueOf( currStr[2] ) * 2, -Integer.valueOf( currStr[1] ) * 8 * 2 );
			String directBinaryOf = Integer.toBinaryString( map.get( key ) + 128 );
			String binaryOf = String.format( "%08d", Integer.valueOf( directBinaryOf ) );
			for( char b : binaryOf.toCharArray() )
			{
				if( b == '1' ) cube();
				glTranslated( 0, 0, -2 );
			}

			glPopMatrix();
		}

		//	selection cube
		glColor3f( 1, 0, 0 );
		glPushMatrix();

		if( !keyPressed( "u" ) && uPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) zPos++;
											 else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) xPos++;
											 else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) zPos--;
											 else xPos--;
		if( !keyPressed( "j" ) && jPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) zPos--;
											 else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) xPos--;
											 else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) zPos++;
											 else xPos++;
		if( !keyPressed( "h" ) && hPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) xPos--;
											 else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) zPos++;
											 else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) xPos++;
											 else zPos--;
		if( !keyPressed( "k" ) && kPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) xPos++;
											 else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) zPos--;
											 else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) xPos--;
											 else zPos++;
		if( !keyPressed( "o" ) && oPressed ) yPos++;
		if( !keyPressed( "l" ) && lPressed ) yPos--;
		uPressed = keyPressed( "u" );
		jPressed = keyPressed( "j" );
		hPressed = keyPressed( "h" );
		kPressed = keyPressed( "k" );
		oPressed = keyPressed( "o" );
		lPressed = keyPressed( "l" );

		glTranslated( xPos * 2, yPos * 2, -zPos * 2 );
		glLineWidth( 10 );
		lineCube();
		glPopMatrix();
		
		//	box for every byte
		glColor3f( 0, 0, .4f );
		glLineWidth( 3 );
		for( String key : map.keySet() )
		{
			glPushMatrix();
			String[] currStr = key.split( "," );
			glTranslated( Integer.valueOf( currStr[0] ) * 2, Integer.valueOf( currStr[2] ) * 2, -Integer.valueOf( currStr[1] ) * 8 * 2 - 7 );
			glScaled( 1, 1, 8 );
			lineCube();
			glPopMatrix();
		}

		glPopMatrix();

		//	block adding / removal logic
		boolean inByte = map.get( xPos + "," + zPos / 8 + "," + yPos ) != null;
		if( !keyPressed( "return" ) && returnPressed )
		{
			if( inByte && !keyPressed( "lshift" ) )
			{
				String currByte = String.format( "%08d", Integer.valueOf( Integer.toBinaryString( map.get( xPos + "," + zPos / 8 + "," + yPos ) + 128 ) ) );
				currByte = currByte.substring( 0, zPos % 8 ) + ( currByte.charAt( zPos % 8 ) == '0' ? 1 : 0 ) + currByte.substring( zPos % 8 + 1 );
				map.put( xPos + "," + zPos / 8 + "," + yPos, (byte) ( Integer.parseInt( currByte, 2 ) - 128 ) );
			}
			else if( inByte )
			{
				ArrayList<String> toRemove = new ArrayList<String>();
				for( String key : map.keySet() ) if( map.get( key ) == -128 ) toRemove.add( key );
				for( String remove : toRemove ) map.remove( remove );
			}
			else if( xPos >= 0 &&  yPos >= 0 && zPos >= 0 )
			{
				map.put( xPos + "," + zPos / 8 + "," + yPos, (byte) ( Integer.parseInt( "00000000".substring( 0, zPos % 8 ) + "1" + "00000000".substring( zPos % 8 + 1 ), 2 ) - 128 ) );
			}
		}
		returnPressed = keyPressed( "return" );
		
		//	display text and such
		glColor3f( 1, 1, 1 );
		glLineWidth( 1 );
		glPushMatrix();
		
		glTranslated( 250, 700, 0 );
		glScaled( 12, -8, 1 );
		basicText( "FPS - " + fps );
		glTranslated( 0, 7, 0 );
		basicText( String.format( "Player xPos %.2f yPos %.2f zPos %.2f", -testPlayer.xPos / 150., -testPlayer.yPos / 150., testPlayer.zPos / 150. ) );
		glTranslated( 0, 3.5, 0 );
		basicText( "Block xPos " + xPos + " yPos " + yPos + " zPos " + zPos );
		glTranslated( 0, 3.5, 0 );
		basicText( "Bytes " + map.size() );
		
		glPopMatrix();

		//	cross hair
		glColor3f( 0, 0, 0 );
		glBegin( GL_QUADS );

		glVertex2d( -10, Display.getHeight() / 2 - 1 );
		glVertex2d( 10, Display.getHeight() / 2 - 1 );
		glVertex2d( 10, Display.getHeight() / 2 + 1 );
		glVertex2d( -10, Display.getHeight() / 2 + 1 );

		glVertex2d( -1, Display.getHeight() / 2 - 10 );
		glVertex2d( 1, Display.getHeight() / 2 - 10 );
		glVertex2d( 1, Display.getHeight() / 2 + 10 );
		glVertex2d( -1, Display.getHeight() / 2 + 10 );

		glEnd();
	}

}
