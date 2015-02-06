#include <EGL/egl.h>
#include <GLES/gl.h>
#include <math.h>
#include <jni.h>
#include <stdlib.h>
#include <edu_performance_test_nativo_graphicoperation_twod_CircleOperationNative.h>
#include <stdio.h>
#include <string>
#include <test_library.h>
//#include <GL/glut.h>

void drawFilledCircle(GLfloat x, GLfloat y, GLfloat radius){
   glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Set background color to black and opaque
   glClear(GL_COLOR_BUFFER_BIT);         // Clear the color buffer

   // Draw a Red 1x1 Square centered at origin
   glBegin(GL_QUADS);              // Each set of 4 vertices form a quad
      glColor3f(1.0f, 0.0f, 0.0f); // Red
      glVertex2f(-0.5f, -0.5f);    // x, y
      glVertex2f( 0.5f, -0.5f);
      glVertex2f( 0.5f,  0.5f);
      glVertex2f(-0.5f,  0.5f);
   glEnd();

   glFlush();  // Render now
}/*
void drawFilledCircle(GLfloat x, GLfloat y, GLfloat radius){
	int i;
	int triangleAmount = 21; //# of triangles used to draw circle

	//GLfloat radius = 0.8f; //radius
	GLfloat twicePi = 2.0f * 3.14;

	glBegin(GL_TRIANGLE_FAN);
		glVertex2f(x, y); // center of circle
		for(i = 0; i <= triangleAmount;i++) {
			glVertex2f(
		            x + (radius * cos(i *  twicePi / triangleAmount)),
			    y + (radius * sin(i * twicePi / triangleAmount))
			);
		}
	glEnd();
}
*/
void
Java_edu_performance_test_nativo_graphicoperation_CircleOperationNative_testNCircleOperation( JNIEnv* env,
        jobject thiz, jint level)
{
	drawFilledCircle(2.0,1.0,3.0);
}


