package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
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
	private ArrayList<String> mapText;

	private boolean initComplete;
	
	private byte[][][] map;

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
			mainLoop();
			Display.update();
			Display.sync( 60 );
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
		glCullFace( GL_BACK );
		
		//	Invisible mouse
		Cursor emptyCursor = null;
		try
		{
			emptyCursor = new Cursor( 1, 1, 0, 0, 1, BufferUtils.createIntBuffer( 1 ), null );
			Mouse.setNativeCursor( emptyCursor );
		}
		catch( LWJGLException e1 ){}
	}

	private void loadMap()
	{
		//	load map into an array of Strings
		try
		{
			mapReader = new Scanner( new File( "res/map.txt" ) );
			mapText = new ArrayList<String>();
			while( mapReader.hasNextLine() ) mapText.add( mapReader.nextLine() );
			System.out.println( "map loaded" );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "failed to load map" );
		}
		String[] firstLine = mapText.get( 0 ).split( "," );
		map = new byte[Integer.valueOf( firstLine[1] )][Integer.valueOf( firstLine[2] )][Integer.valueOf( firstLine[3] )];
		
		//	load map
		for( int i = 1; i < mapText.size(); i++ )
		{
			String[] currStr = mapText.get( i ).split( "," );
			byte val = Byte.valueOf( currStr[1] );
			map[Integer.valueOf( currStr[2] )][Integer.valueOf( currStr[3] )][Integer.valueOf( currStr[4] )] = val;
		}
	}
	
	private void init()
	{
		testPlayer = new Player();
		loadMap();
	}

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
		
		//	go throgh map
		for( int i = 0; i < map.length; i++ )
		{
			for( int j = 0; j < map[0].length; j++ )
			{
				for( int k = 0; k < map[0][0].length; k++ )
				{
					glPushMatrix();
					
					glTranslated( i * 2, k * 2, -j * 8 * 2 );
					String directBinaryOf = Integer.toBinaryString( map[i][j][k] );
					String binaryOf = String.format( "%08d", Integer.valueOf( directBinaryOf ) );
					for( char b : binaryOf.toCharArray() )
					{
						if( b == '1' ) cube();
						glTranslated( 0, 0, -2 );
					}
					
					glPopMatrix();
				}
			}
		}
		
		glPopMatrix();
		
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

}
