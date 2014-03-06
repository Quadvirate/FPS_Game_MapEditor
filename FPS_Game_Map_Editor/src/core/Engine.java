package core;

import static convenience.Utility.*;
import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.TextureLoader;
import player.Player;

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
	 * n -> name -> enter : load map from name
	 * t + enter : teleport selection block to camera
	 */

	public Player testPlayer;

	private boolean initComplete;

	private ArrayList< String > textures = new ArrayList< String >();

	private ArrayList< HashMap< String, Byte >> map = new ArrayList< HashMap< String, Byte >>();

	private String mapName = "";
	private boolean acceptNameInput;

	private boolean saving, loading;

	private int xPos, yPos, zPos;

	private boolean uPressed, jPressed, hPressed, kPressed, oPressed, lPressed, returnPressed, leftPressed, rightPressed;

	private boolean loadedTextures;

	private int texSel = 0;

	public void start()
	{
		setNatives();
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
			Display.sync( 60 );
		}
		Display.destroy();
	}

	private void setNatives()
	{
		String os = System.getProperty( "os.name" );
		if( os.toLowerCase().indexOf( "windows" ) != -1 ) System.setProperty( "org.lwjgl.librarypath", System.getProperty( "user.dir" ) + File.separator + "lwjgl-2.9.0" + File.separator + "native" + File.separator + "windows" );
		else if( os.toLowerCase().indexOf( "mac" ) != -1 ) System.setProperty( "org.lwjgl.librarypath", System.getProperty( "user.dir" ) + File.separator + "lwjgl-2.9.0" + File.separator + "native" + File.separator + "macosx" );
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
		glCullFace( GL_BACK );
		glEnable( GL_CULL_FACE );
		glEnable( GL_TEXTURE_2D );

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

	private void loadMap( String mapName )
	{
		if( !loadedTextures )
		{
			loadTextures();
			loadedTextures = true;
		}
		for( int i = 0; i < textures.size(); i++ )
			map.set( i, new HashMap< String, Byte >() );
		//	load map into an array of Strings
		int on = 0;
		try
		{
			Scanner mapReader = new Scanner( new File( "res/" + mapName + ".txt" ) );
			while( mapReader.hasNextLine() )
			{
				//	load map
				String[] currStr = mapReader.nextLine().split( "," );
				switch( currStr[0].toCharArray()[0] )
				{
					case 'v':
						byte val = Byte.valueOf( currStr[1] );
						map.get( on ).put( currStr[2] + "," + currStr[3] + "," + currStr[4], val );
						break;
					case 't':
						on = textures.indexOf( currStr[1] );
						break;
				}
			}
			mapReader.close();
			System.out.println( "\"" + mapName + "\" map loaded" );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "failed to load \"" + mapName + "\" map" );
		}
	}

	private void loadTextures()
	{
		ArrayList< File > images = new ArrayList< File >();
		for( File f : new File( "res/" ).listFiles() )
			if( f.toString().substring( f.toString().length() - 4 ).equals( ".png" ) ) images.add( f );
		try
		{
			for( File f : images )
			{
				TextureLoader.getTexture( "PNG", new FileInputStream( f ) ).bind();
				textures.add( f.getName().substring( 0, f.getName().length() - 4 ) );
				map.add( new HashMap< String, Byte >() );
				System.out.println( textures.get( textures.size() - 1 ) + " loaded" );
			}
		}
		catch( FileNotFoundException e )
		{
			e.printStackTrace();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}

	private void init()
	{
		testPlayer = new Player();
		loadMap( "pinpoint_ish_fps" );
	}

	private void mainLoop()
	{
		//	background grey
		background( 100, 100, 100 );

		//	if not saving or loading the map
		if( !saving && !loading )
		{
			//	draw map
			drawMap();

			//	manage map textures
			manageTextures();

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

			//	check for loading
			if( keyPressed( "n" ) )
			{
				loading = true;
				acceptNameInput = false;
			}
		}
		else if( saving )
		{
			//	save menu
			drawSaveMenu();
		}
		else if( loading )
		{
			//	load menu
			drawLoadMenu();
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

		glLineWidth( 3 );

		//	go through map
		int at = 0;
		for( HashMap< String, Byte > m : map )
		{
			glColor3d( .8, .8, .8 );
			glBindTexture( GL_TEXTURE_2D, at + 1 );
			glBegin( GL_QUADS );
			for( String key : m.keySet() )
			{
				glPushMatrix();

				String[] currStr = key.split( "," );
				cubeOffsetX = Integer.valueOf( currStr[0] ) * 2;
				cubeOffsetY = Integer.valueOf( currStr[2] ) * 2;
				cubeOffsetZ = -Integer.valueOf( currStr[1] ) * 8 * 2;
				String directBinaryOf = Integer.toBinaryString( m.get( key ) + 128 );
				String binaryOf = String.format( "%08d", Integer.valueOf( directBinaryOf ) );

				for( char b : binaryOf.toCharArray() )
				{
					if( b == '1' )
					{
						cube();
					}
					glTranslated( 0, 0, -2 );
					cubeOffsetZ -= 2;
				}
			}
			glEnd();

			glBindTexture( GL_TEXTURE_2D, 0 );
			glColor3d( 0, 0, 0 );
			glBegin( GL_LINES );
			for( String key : m.keySet() )
			{
				glPushMatrix();

				String[] currStr = key.split( "," );
				cubeOffsetX = Integer.valueOf( currStr[0] ) * 2;
				cubeOffsetY = Integer.valueOf( currStr[2] ) * 2;
				cubeOffsetZ = -Integer.valueOf( currStr[1] ) * 8 * 2;
				String directBinaryOf = Integer.toBinaryString( m.get( key ) + 128 );
				String binaryOf = String.format( "%08d", Integer.valueOf( directBinaryOf ) );

				for( char b : binaryOf.toCharArray() )
				{
					if( b == '1' )
					{
						lineCube();
					}
					glTranslated( 0, 0, -2 );
					cubeOffsetZ -= 2;
				}
			}
			glEnd();
			at++ ;
			cubeOffsetX = cubeOffsetY = cubeOffsetZ = 0;
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
		glBegin( GL_LINES );
		lineCube();
		glEnd();
		glPopMatrix();

		if( Keyboard.isKeyDown( Keyboard.KEY_T ) && Keyboard.isKeyDown( Keyboard.KEY_RETURN ) )
		{
			xPos = -(int) ( testPlayer.xPos / 300. );
			yPos = -(int) ( testPlayer.yPos / 300. );
			zPos = (int) ( testPlayer.zPos / 300. );
		}

		//	blue box for every byte
		glColor3f( 0, 0, .4f );
		glLineWidth( 3 );
		for( HashMap< String, Byte > m : map )
		{
			for( String key : m.keySet() )
			{
				glPushMatrix();
				String[] currStr = key.split( "," );
				glTranslated( Integer.valueOf( currStr[0] ) * 2, Integer.valueOf( currStr[2] ) * 2, -Integer.valueOf( currStr[1] ) * 8 * 2 - 7 );
				glScaled( 1, 1, 8 );
				glBegin( GL_LINES );
				lineCube();
				glEnd();
				glPopMatrix();
			}
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
		//	if byte already exists
		boolean onByte = map.get( texSel ).get( xPos + "," + zPos / 8 + "," + yPos ) != null;

		//	if hit enter
		if( !returnPressed && Keyboard.isKeyDown( Keyboard.KEY_RETURN ) )
		{
			//	change value if it exists
			if( onByte && !keyPressed( "lshift" ) && xPos >= 0 && yPos >= 0 && zPos >= 0 )
			{
				String currByte = String.format( "%08d", Integer.valueOf( Integer.toBinaryString( map.get( texSel ).get( xPos + "," + zPos / 8 + "," + yPos ) + 128 ) ) );
				currByte = currByte.substring( 0, zPos % 8 ) + ( currByte.charAt( zPos % 8 ) == '0' ? 1 : 0 ) + currByte.substring( zPos % 8 + 1 );
				map.get( texSel ).put( xPos + "," + zPos / 8 + "," + yPos, (byte) ( Integer.parseInt( currByte, 2 ) - 128 ) );
			}
			//	remove bytes which are empty from any HashMap
			else if( keyPressed( "lshift" ) )
			{
				for( int i = 0; i < map.size(); i++ )
				{
					ArrayList< String > toRemove = new ArrayList< String >();
					for( String key : map.get( i ).keySet() )
						if( map.get( i ).get( key ) == -128 ) toRemove.add( key );
					for( String remove : toRemove )
						map.get( i ).remove( remove );
				}
			}
			//	add a byte with a new block
			else if( xPos >= 0 && yPos >= 0 && zPos >= 0 )
			{
				map.get( texSel ).put( xPos + "," + zPos / 8 + "," + yPos, (byte) ( Integer.parseInt( "00000000".substring( 0, zPos % 8 ) + "1" + "00000000".substring( zPos % 8 + 1 ), 2 ) - 128 ) );
			}

			//	only run once per key press
			returnPressed = true;
		}
		//	allow for next key press
		if( returnPressed && !Keyboard.isKeyDown( Keyboard.KEY_RETURN ) ) returnPressed = false;

		if( keyPressed( "c" ) && keyPressed( "return" ) ) for( int i = 0; i < map.size(); i++ )
			for( String key : map.get( i ).keySet() )
				map.get( i ).put( key, (byte) -128 );
	}

	private void manageTextures()
	{
		glPushMatrix();

		glTranslated( -Display.getWidth() / 2 + 50, Display.getHeight() - 50, 0 );
		glScaled( 50, -50, 1 );
		for( int i = 0; i < textures.size(); i++ )
		{
			glColor3d( texSel == i ? 1 : .5, texSel == i ? 1 : .5, texSel == i ? 1 : .5 );
			glBindTexture( GL_TEXTURE_2D, i + 1 );
			glBegin( GL_QUADS );
			glTexCoord2d( 0, 0 );
			glVertex2d( i * 1.2 + 1, 0 );
			glTexCoord2d( 1, 0 );
			glVertex2d( i * 1.2, 0 );
			glTexCoord2d( 1, 1 );
			glVertex2d( i * 1.2, 1 );
			glTexCoord2d( 0, 1 );
			glVertex2d( i * 1.2 + 1, 1 );
			//			cube();
			cubeOffsetX += 2.4;
			glEnd();
			glBindTexture( GL_TEXTURE_2D, 0 );

			glBegin( GL_LINES );
			glVertex2d( i * 1.2, 0 );
			glVertex2d( i * 1.2 + 1, 0 );
			glVertex2d( i * 1.2 + 1, 0 );
			glVertex2d( i * 1.2 + 1, 1 );
			glVertex2d( i * 1.2 + 1, 1 );
			glVertex2d( i * 1.2, 1 );
			glVertex2d( i * 1.2, 1 );
			glVertex2d( i * 1.2, 0 );
			glEnd();
		}

		glPopMatrix();

		//	logic

		if( !leftPressed && Keyboard.isKeyDown( Keyboard.KEY_LEFT ) )
		{
			leftPressed = true;
			texSel = texSel == 0 ? textures.size() - 1 : texSel - 1;
		}
		if( leftPressed && !Keyboard.isKeyDown( Keyboard.KEY_LEFT ) ) leftPressed = false;

		if( !rightPressed && Keyboard.isKeyDown( Keyboard.KEY_RIGHT ) )
		{
			rightPressed = true;
			texSel = texSel == textures.size() - 1 ? 0 : texSel + 1;
		}
		if( rightPressed && !Keyboard.isKeyDown( Keyboard.KEY_RIGHT ) ) rightPressed = false;
	}

	private void drawText()
	{
		glColor3d( .8, .8, .8 );
		glLineWidth( 1 );
		glPushMatrix();
		glTranslated( 250, 700, 0 );
		glScaled( 12, -8, 1 );
		basicText( "FPS - " + fps );
		glTranslated( 0, 7, 0 );
		basicText( String.format( "Player xPos %.2f yPos %.2f zPos %.2f", -testPlayer.xPos / 150., -testPlayer.yPos / 150., testPlayer.zPos / 150. ) );
		glTranslated( 0, 3.5, 0 );
		basicText( "Block xPos " + xPos + " yPos " + yPos + " zPos " + zPos );
		for( int i = 0; i < textures.size(); i++ )
		{
			glTranslated( 0, 3.5, 0 );
			basicText( "Map " + i + " - " + map.get( i ).size() );
		}
		glPopMatrix();
	}

	private void drawCrossHair()
	{
		//	cross hare
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
		basicText( "Enter filename to save" );
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
			for( int i = 0; i < map.size(); i++ )
			{
				if( map.get( i ).size() > 0 ) mapWriter.write( "t," + textures.get( i ) + "\n" );
				for( String key : map.get( i ).keySet() )
					mapWriter.write( "v," + map.get( i ).get( key ) + "," + key + "\n" );
			}
			mapWriter.close();
			System.out.println( "saved map to " + mapName + ".txt" );
		}
		catch( IOException e )
		{
			System.out.println( "failed to save map" );
		}
	}

	private void drawLoadMenu()
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
		basicText( "Enter filename to load" );
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
		}
		acceptNameInput = false;
		if( !anyKeyPressed() ) acceptNameInput = true;

		//	check if finished loading
		if( keyPressed( "return" ) )
		{
			loadMap( mapName );
			loading = false;
		}
	}

}
