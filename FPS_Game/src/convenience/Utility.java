package convenience;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Color;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

public class Utility
{

	public static boolean keyPressed( String key )
	{
		return Keyboard.isKeyDown( Keyboard.getKeyIndex( key.toUpperCase() ) );
	}

	public static void cube()
	{
		glBegin( GL_QUADS );

		glNormal3d( 0, 1, 0 );
		glVertex3f( 1.0f, 1.0f, -1.0f );
		glVertex3f( -1.0f, 1.0f, -1.0f );
		glVertex3f( -1.0f, 1.0f, 1.0f );
		glVertex3f( 1.0f, 1.0f, 1.0f );

		glNormal3d( 0, -1, 0 );
		glVertex3f( 1.0f, -1.0f, 1.0f );
		glVertex3f( -1.0f, -1.0f, 1.0f );
		glVertex3f( -1.0f, -1.0f, -1.0f );
		glVertex3f( 1.0f, -1.0f, -1.0f );

		glNormal3d( 0, 0, 1 );
		glVertex3f( 1.0f, 1.0f, 1.0f );
		glVertex3f( -1.0f, 1.0f, 1.0f );
		glVertex3f( -1.0f, -1.0f, 1.0f );
		glVertex3f( 1.0f, -1.0f, 1.0f );

		glNormal3d( 0, 0, -1 );
		glVertex3f( 1.0f, -1.0f, -1.0f );
		glVertex3f( -1.0f, -1.0f, -1.0f );
		glVertex3f( -1.0f, 1.0f, -1.0f );
		glVertex3f( 1.0f, 1.0f, -1.0f );

		glNormal3d( -1, 0, 0 );
		glVertex3f( -1.0f, 1.0f, 1.0f );
		glVertex3f( -1.0f, 1.0f, -1.0f );
		glVertex3f( -1.0f, -1.0f, -1.0f );
		glVertex3f( -1.0f, -1.0f, 1.0f );

		glNormal3d( 1, 0, 0 );
		glVertex3f( 1.0f, 1.0f, -1.0f );
		glVertex3f( 1.0f, 1.0f, 1.0f );
		glVertex3f( 1.0f, -1.0f, 1.0f );
		glVertex3f( 1.0f, -1.0f, -1.0f );

		glEnd();

		
		
		//	only set color for the lines ( push/popStyle in processing )
		glPushAttrib( GL_CURRENT_BIT );
		glColor3f( 0, 0, 0 );
		
		//	same as color but with the line width
		glPushAttrib( GL_LINE_BIT );
		glLineWidth( 5 );

		glBegin( GL_LINES );

		glVertex3f( -1.005f, -1.005f, -1.005f );
		glVertex3f( -1.005f, -1.005f, 1.005f );
		glVertex3f( -1.005f, -1.005f, 1.005f );
		glVertex3f( 1.005f, -1.005f, 1.005f );
		glVertex3f( 1.005f, -1.005f, 1.005f );
		glVertex3f( 1.005f, -1.005f, -1.005f );
		glVertex3f( 1.005f, -1.005f, -1.005f );
		glVertex3f( -1.005f, -1.005f, -1.005f );
		
		glVertex3f( -1.005f, 1.005f, -1.005f );
		glVertex3f( -1.005f, 1.005f, 1.005f );
		glVertex3f( -1.005f, 1.005f, 1.005f );
		glVertex3f( 1.005f, 1.005f, 1.005f );
		glVertex3f( 1.005f, 1.005f, 1.005f );
		glVertex3f( 1.005f, 1.005f, -1.005f );
		glVertex3f( 1.005f, 1.005f, -1.005f );
		glVertex3f( -1.005f, 1.005f, -1.005f );
		
		glVertex3f( -1.005f, -1.005f, -1.005f );
		glVertex3f( -1.005f, 1.005f, -1.005f );
		glVertex3f( -1.005f, -1.005f, 1.005f );
		glVertex3f( -1.005f, 1.005f, 1.005f );
		glVertex3f( 1.005f, -1.005f, 1.005f );
		glVertex3f( 1.005f, 1.005f, 1.005f );
		glVertex3f( 1.005f, -1.005f, -1.005f );
		glVertex3f( 1.005f, 1.005f, -1.005f );

		glEnd();
		
		glPopAttrib();
		
		glPopAttrib();
		
	}

}
