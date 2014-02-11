package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	
	/* CONTROLS : 
	 * wasd rf : moving
	 * mouse : turning
	 * wasd rf + shift : moving double speed
	 * uhjk ol : moving editing block 
	 * enter : add / remove block
	 * shift + enter : remove all bytes that are empty
	 * m -> name -> enter : save map to any name
	 * c + enter : clear all bytes
	 */
	
	/*
	 * TODO :
	 * load map by pressing n
	 */
	
	public Player testPlayer;

	private Scanner mapReader;
	private ArrayList< String > mapText;

	private boolean initComplete;

	private HashMap< String, Byte > map = new HashMap< String, Byte >();

	private int fps;
	private int fpsCounter;
	private long lastFPS;
	private boolean initFpsCounter;
	private String mapName;
	private boolean acceptNameInput;

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
			Display.sync( Integer.MAX_VALUE );
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
		if( !initFpsCounter )
		{
			lastFPS = ( Sys.getTime() * 1000 ) / Sys.getTimerResolution();
			initFpsCounter = true;
		}

		if( ( Sys.getTime() * 1000 ) / Sys.getTimerResolution() - lastFPS > 1000 )
		{
			fps = fpsCounter;
			fpsCounter = 0;
			lastFPS += 1000;
		}
		fpsCounter++ ;
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
		saving = false;
		mapName = "default";
		acceptNameInput = false;
	}

	public int xPos, yPos, zPos;
	public boolean uPressed, jPressed, hPressed, kPressed, oPressed, lPressed, returnPressed;

	private boolean saving;

	private void mainLoop()
	{
		//	background grey
		background( 100, 100, 100 );

		//	if not saving the map
		if( !saving )
		{
			//	draw map
			drawMap();

			//	block adding / removal logic
			editingLogic();

			//	display text
			drawText();

			//	cross hair
			drawCrossHair();

			//	check for saving
			if( keyPressed( "m" ) )
			{
				saving = true;
				acceptNameInput = false;
			}
		}
		else
		{
			//	save menu
			drawSaveMenu();
		}

	}

	private void background( double r, double g, double b )
	{
		glClearColor( (float) ( r / 255. ), (float) ( g / 255. ), (float) ( b / 255. ), 1 );
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
	}

	private void drawMap()
	{
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

		if( !keyPressed( "u" ) && uPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) zPos++ ;
		else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) xPos++ ;
		else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) zPos-- ;
		else xPos-- ;
		if( !keyPressed( "j" ) && jPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) zPos-- ;
		else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) xPos-- ;
		else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) zPos++ ;
		else xPos++ ;
		if( !keyPressed( "h" ) && hPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) xPos-- ;
		else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) zPos++ ;
		else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) xPos++ ;
		else zPos-- ;
		if( !keyPressed( "k" ) && kPressed ) if( testPlayer.camAngX >= 315 || testPlayer.camAngX <= 45 ) xPos++ ;
		else if( testPlayer.camAngX <= 135 && testPlayer.camAngX >= 45 ) zPos-- ;
		else if( testPlayer.camAngX <= 225 && testPlayer.camAngX >= 135 ) xPos-- ;
		else zPos++ ;
		if( !keyPressed( "o" ) && oPressed ) yPos++ ;
		if( !keyPressed( "l" ) && lPressed ) yPos-- ;
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

		//	blue box for every byte
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
		
		//	red lines for axes
		glColor3f( .4f, 0, 0 );
		glBegin( GL_LINES );
		glVertex3d( 0, 0, 0 );
		glVertex3d( 0, 0, -10000 );
		glVertex3d( 0, 0, 0 );
		glVertex3d( 0, 10000, 0 );
		glVertex3d( 0, 0, 0 );
		glVertex3d( 10000, 0, 0 );
		glEnd();

		glPopMatrix();
	}

	private void editingLogic()
	{
		boolean inByte = map.get( xPos + "," + zPos / 8 + "," + yPos ) != null;
		//	if enter is pressed
		if( !keyPressed( "return" ) && returnPressed )
		{
			//	change value of byte if it already exists
			if( inByte && !keyPressed( "lshift" ) && xPos >= 0 && yPos >= 0 && zPos >= 0 )
			{
				String currByte = String.format( "%08d", Integer.valueOf( Integer.toBinaryString( map.get( xPos + "," + zPos / 8 + "," + yPos ) + 128 ) ) );
				currByte = currByte.substring( 0, zPos % 8 ) + ( currByte.charAt( zPos % 8 ) == '0' ? 1 : 0 ) + currByte.substring( zPos % 8 + 1 );
				map.put( xPos + "," + zPos / 8 + "," + yPos, (byte) ( Integer.parseInt( currByte, 2 ) - 128 ) );
			}
			//	remove bytes which are empty
			else if( keyPressed( "lshift" ) )
			{
				ArrayList< String > toRemove = new ArrayList< String >();
				for( String key : map.keySet() )
					if( map.get( key ) == -128 ) toRemove.add( key );
				for( String remove : toRemove )
					map.remove( remove );
			}
			//	add a byte with a new block
			else if( xPos >= 0 && yPos >= 0 && zPos >= 0 )
			{
				map.put( xPos + "," + zPos / 8 + "," + yPos, (byte) ( Integer.parseInt( "00000000".substring( 0, zPos % 8 ) + "1" + "00000000".substring( zPos % 8 + 1 ), 2 ) - 128 ) );
			}
		}
		returnPressed = keyPressed( "return" );
		
		if( keyPressed( "c" ) && keyPressed( "return" ) ) map.clear();
	}

	private void drawText()
	{
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
	}

	private void drawCrossHair()
	{
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

	private void drawSaveMenu()
	{
		//	draw rectangle
		glColor3f( .2f, .2f, .2f );
		glBegin( GL_QUADS );
		glVertex2d( -300, 600 );
		glVertex2d( 300, 600 );
		glVertex2d( 300, 400 );
		glVertex2d( -300, 400 );
		glEnd();

		//	draw text
		glColor3f( .8f, .8f, .8f );
		glPushMatrix();
		glTranslated( -280, 580, 0 );
		glScaled( 20, -5, 1 );
		basicText( "Enter filename" );
		glTranslated( 11, 30, 0 );
		basicText( "Press enter when done" );
		glPopMatrix();

		//	draw input rectangle
		glColor3f( .8f, .8f, .8f );
		glLineWidth( 5 );
		glBegin( GL_LINE_LOOP );
		glVertex2d( -200, 530 );
		glVertex2d( 200, 530 );
		glVertex2d( 200, 480 );
		glVertex2d( -200, 480 );
		glEnd();
		glLineWidth( 1 );

		//	draw inputted name for map
		glPushMatrix();
		glTranslated( -180, 500, 0 );
		glScaled( 20, -8, 1 );
		basicText( mapName );
		glTranslated( ( mapName.length() - 1 ) * .8, 1, 0 );
		basicText( "_" );

		glPopMatrix();

		//	get input for name
		if( anyKeyPressed() && acceptNameInput && !getKeyPressed().equals( "RETURN" ) && !getKeyPressed().equals( "LSHIFT" ) && !getKeyPressed().equals( "RSHIFT" ) && !getKeyPressed().equals( "PERIOD" ) && !getKeyPressed().equals( "COMMA" ) )
		{
			if( getKeyPressed().equals( "SPACE" ) ) mapName += "_";
			else mapName = getKeyPressed().equals( "BACK" ) ? mapName.length() == 0 ? "" : mapName.substring( 0, mapName.length() - 1 ) : keyPressed( "lshift" ) || keyPressed( "rshift" ) ? mapName + getKeyPressed() : mapName + getKeyPressed().toLowerCase();
			acceptNameInput = false;
		}
		if( !anyKeyPressed() ) acceptNameInput = true;
		
		//	check if finished saving
		if( keyPressed( "return" ) )
		{
			save();
			saving = false;
		}
	}

	private void save()
	{
		//	saves to mapName.txt
		try
		{
			BufferedWriter mapWriter = new BufferedWriter( new FileWriter( "res/" + mapName + ".txt" ), 256000 );
			for( String key : map.keySet() )
				mapWriter.write( "v," + map.get( key ) + "," + key + "\n" );
			mapWriter.close();
			System.out.println( "saved map to " + mapName + ".txt" );
		}
		catch( IOException e )
		{
			System.out.println( "failed to save map" );
		}
	}
	
}
