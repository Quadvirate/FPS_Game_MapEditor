package convenience;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

public class Utility
{
	
	public static boolean initFpsCounter;
	public static long lastFPS;
	public static int fps, fpsCounter;
	
	private static double textXOffset;

	//	input : http://www.lwjgl.org/javadoc/org/lwjgl/input/Keyboard.html
	public static boolean keyPressed( String key )
	{
		return Keyboard.isKeyDown( Keyboard.getKeyIndex( key.toUpperCase() ) );
	}

	public static boolean anyKeyPressed()
	{
		Keyboard.next();
		return Keyboard.getEventKeyState();
	}
	
	public static String getKeyPressed()
	{
		Keyboard.next();
		return Keyboard.getKeyName( Keyboard.getEventKey() );
	}
	
	public static int cubeOffsetX, cubeOffsetY, cubeOffsetZ;
	
	public static void cube()
	{
//		glBegin( GL_QUADS );

//		glNormal3d( 0, 1, 0 );
		//	top
		glTexCoord2d( 0, 0 );
		glVertex3f( 1.0f + cubeOffsetX, 1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 0 );
		glVertex3f( -1.0f + cubeOffsetX, 1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 1 );
		glVertex3f( -1.0f + cubeOffsetX, 1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 1 );
		glVertex3f( 1.0f + cubeOffsetX, 1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );

//		glNormal3d( 0, -1, 0 );
		//	bottom
		glTexCoord2d( 0, 0 );
		glVertex3f( 1.0f + cubeOffsetX, -1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 0 );
		glVertex3f( -1.0f + cubeOffsetX, -1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 1 );
		glVertex3f( -1.0f + cubeOffsetX, -1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 1 );
		glVertex3f( 1.0f + cubeOffsetX, -1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );

//		glNormal3d( 0, 0, 1 );
		//	near
		glTexCoord2d( 0, 0 );
		glVertex3f( 1.0f + cubeOffsetX, 1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 0 );
		glVertex3f( -1.0f + cubeOffsetX, 1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 1 );
		glVertex3f( -1.0f + cubeOffsetX, -1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 1 );
		glVertex3f( 1.0f + cubeOffsetX, -1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );

//		glNormal3d( 0, 0, -1 );
		//	far
		glTexCoord2d( 0, 1 );
		glVertex3f( 1.0f + cubeOffsetX, -1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 1 );
		glVertex3f( -1.0f + cubeOffsetX, -1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 0 );
		glVertex3f( -1.0f + cubeOffsetX, 1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 0 );
		glVertex3f( 1.0f + cubeOffsetX, 1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );

//		glNormal3d( -1, 0, 0 );
		//	left
		glTexCoord2d( 0, 0 );
		glVertex3f( -1.0f + cubeOffsetX, 1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 0 );
		glVertex3f( -1.0f + cubeOffsetX, 1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 1 );
		glVertex3f( -1.0f + cubeOffsetX, -1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 1 );
		glVertex3f( -1.0f + cubeOffsetX, -1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );

//		glNormal3d( 1, 0, 0 );
		//	right
		glTexCoord2d( 1, 0 );
		glVertex3f( 1.0f + cubeOffsetX, 1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 0 );
		glVertex3f( 1.0f + cubeOffsetX, 1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 0, 1 );
		glVertex3f( 1.0f + cubeOffsetX, -1.0f + cubeOffsetY, 1.0f + cubeOffsetZ );
		glTexCoord2d( 1, 1 );
		glVertex3f( 1.0f + cubeOffsetX, -1.0f + cubeOffsetY, -1.0f + cubeOffsetZ );

//		glEnd();

		
		
//		//	only set color for the lines ( push/popStyle in processing )
//		glPushAttrib( GL_CURRENT_BIT );
//		glColor3f( 0, 0, 0 );
//		
//		//	same as color but with the line width
//		glPushAttrib( GL_LINE_BIT );
//		glLineWidth( 5 );
//
//		glBegin( GL_LINES );
//
//		glVertex3f( -1.005f, -1.005f, -1.005f );
//		glVertex3f( -1.005f, -1.005f, 1.005f );
//		glVertex3f( -1.005f, -1.005f, 1.005f );
//		glVertex3f( 1.005f, -1.005f, 1.005f );
//		glVertex3f( 1.005f, -1.005f, 1.005f );
//		glVertex3f( 1.005f, -1.005f, -1.005f );
//		glVertex3f( 1.005f, -1.005f, -1.005f );
//		glVertex3f( -1.005f, -1.005f, -1.005f );
//		
//		glVertex3f( -1.005f, 1.005f, -1.005f );
//		glVertex3f( -1.005f, 1.005f, 1.005f );
//		glVertex3f( -1.005f, 1.005f, 1.005f );
//		glVertex3f( 1.005f, 1.005f, 1.005f );
//		glVertex3f( 1.005f, 1.005f, 1.005f );
//		glVertex3f( 1.005f, 1.005f, -1.005f );
//		glVertex3f( 1.005f, 1.005f, -1.005f );
//		glVertex3f( -1.005f, 1.005f, -1.005f );
//		
//		glVertex3f( -1.005f, -1.005f, -1.005f );
//		glVertex3f( -1.005f, 1.005f, -1.005f );
//		glVertex3f( -1.005f, -1.005f, 1.005f );
//		glVertex3f( -1.005f, 1.005f, 1.005f );
//		glVertex3f( 1.005f, -1.005f, 1.005f );
//		glVertex3f( 1.005f, 1.005f, 1.005f );
//		glVertex3f( 1.005f, -1.005f, -1.005f );
//		glVertex3f( 1.005f, 1.005f, -1.005f );
//
//		glEnd();
//		
//		glPopAttrib();
//		
//		glPopAttrib();
		
	}
	
	public static void lineCube()
	{
//		glBegin( GL_LINES );

		glVertex3f( -1.005f + cubeOffsetX, -1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, -1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, -1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, -1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, -1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, -1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, -1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, -1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		
		glVertex3f( -1.005f + cubeOffsetX, 1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, 1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, 1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, 1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, 1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, 1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, 1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, 1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		
		glVertex3f( -1.005f + cubeOffsetX, -1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, 1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, -1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( -1.005f + cubeOffsetX, 1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, -1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, 1.005f + cubeOffsetY, 1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, -1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );
		glVertex3f( 1.005f + cubeOffsetX, 1.005f + cubeOffsetY, -1.005f + cubeOffsetZ );

//		glEnd();
	}

	public static void basicText( Object s )
	{
		glPushMatrix();
		glTranslated( 0, -1, 0 );
		glBegin( GL_LINES );
		for( char a : s.toString().toCharArray() )
		{
			if( a == 'a' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.25 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.25 );
//				glEnd();
			}
			else if( a == 'b' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'c' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'd' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'e' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.5 );
				glVertex2d( 0.0 + textXOffset, 0.5 );
				glVertex2d( 0.5 + textXOffset, 0.5 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'f' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'g' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 );
				glVertex2d( 0.5 + textXOffset, 2.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 );
//				glEnd();
			}
			else if( a == 'h' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'i' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'j' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 3.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 3.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 3.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 3.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.5 - 1.0 );
//				glEnd();
			}
			else if( a == 'k' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'l' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'm' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.25 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'n' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.25 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'o' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.25 );
				glVertex2d( 0.0 + textXOffset, 0.25 );
				glVertex2d( 0.0 + textXOffset, 0.75 );
				glVertex2d( 0.0 + textXOffset, 0.75 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 );
				glVertex2d( 0.5 + textXOffset, 0.75 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
//				glEnd();
			}
			else if( a == 'p' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'q' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 );
//				glEnd();
			}
			else if( a == 'r' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.25 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.25 );
//				glEnd();
			}
			else if( a == 's' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 );
				glVertex2d( 0.0 + textXOffset, 0.5 );
				glVertex2d( 0.5 + textXOffset, 0.5 );
				glVertex2d( 0.5 + textXOffset, 0.5 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 't' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'u' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 0.75 );
				glVertex2d( 0.0 + textXOffset, 0.75 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'v' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
//				glEnd();
			}
			else if( a == 'w' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'x' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
//				glEnd();
			}
			else if( a == 'y' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 );
//				glEnd();
			}
			else if( a == 'z' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == 'A' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'B' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.75 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.75 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.25 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.25 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.25 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.25 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'C' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'D' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'E' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'F' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'G' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'H' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'I' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'J' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
//				glEnd();
			}
			else if( a == 'K' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'L' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'M' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'N' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'O' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
//				glEnd();
			}
			else if( a == 'P' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'Q' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'R' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'S' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'T' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'U' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'V' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'W' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.75 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'X' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'Y' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == 'Z' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == '0' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == '1' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.5 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 0.0 - 1.0 );
//				glEnd();
			}
			else if( a == '2' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.75 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.75 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.25 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.25 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == '3' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == '4' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == '5' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.25 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.25 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.75 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.75 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.25 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == '6' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == '7' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == '8' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
//				glEnd();
			}
			else if( a == '9' )
			{
//				glTranslated( 0 + textXOffset, -1 + textXOffset, 0 );
//				glBegin( GL_LINES );
				glVertex2d( 0.5 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 1.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 0.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.0 + textXOffset, 2.0 - 1.0 );
				glVertex2d( 0.5 + textXOffset, 2.0 - 1.0 );
//				glEnd();
			}
			else if( a == '-' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.0 );
				glVertex2d( 0.75 + textXOffset, 0.0 );
//				glEnd();
			}
			else if( a == '.' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, 0.5 );
				glVertex2d( 0.25 + textXOffset, 1.0 );
//				glEnd();
			}
			else if( a == '|' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.25 + textXOffset, -1.25 );
				glVertex2d( 0.25 + textXOffset, 1.75 );
//				glEnd();
			}
			else if( a == '_' )
			{
//				glBegin( GL_LINES );
				glVertex2d( 0.0 + textXOffset, 1.0 );
				glVertex2d( 0.5 + textXOffset, 1.0 );
//				glEnd();
			}
			textXOffset += .8;
		}
		glEnd();
		glPopMatrix();
		textXOffset = 0;
	}
	
	public static void manageFPS()
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
}
