//
// Copyright 2011 Tero Saarni
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

#include <stdint.h>
#include <unistd.h>
#include <pthread.h>
#include <android/native_window.h> // requires ndk r5 or newer
#include <EGL/egl.h> // requires ndk r5 or newer
#include <GLES/gl.h>
#include <math.h>

#include "renderer.h"
#include "test_library.h"

void circle();
static GLint vertices[][3] = { { -0x10000, -0x10000, -0x10000 }, { 0x10000,
		-0x10000, -0x10000 }, { 0x10000, 0x10000, -0x10000 }, { -0x10000,
		0x10000, -0x10000 }, { -0x10000, -0x10000, 0x10000 }, { 0x10000,
		-0x10000, 0x10000 }, { 0x10000, 0x10000, 0x10000 }, { -0x10000, 0x10000,
		0x10000 } };

static GLint colors[][4] = { { 0x00000, 0x00000, 0x00000, 0x10000 }, { 0x10000,
		0x00000, 0x00000, 0x10000 }, { 0x10000, 0x10000, 0x00000, 0x10000 }, {
		0x00000, 0x10000, 0x00000, 0x10000 }, { 0x00000, 0x00000, 0x10000,
		0x10000 }, { 0x10000, 0x00000, 0x10000, 0x10000 }, { 0x10000, 0x10000,
		0x10000, 0x10000 }, { 0x00000, 0x10000, 0x10000, 0x10000 } };

GLubyte indices[] = { 0, 4, 5, 0, 5, 1, 1, 5, 6, 1, 6, 2, 2, 6, 7, 2, 7, 3, 3,
		7, 4, 3, 4, 0, 4, 7, 6, 4, 6, 5, 3, 0, 1, 3, 1, 2 };

Renderer::Renderer() :
		_msg(MSG_NONE), _display(0), _surface(0), _context(0), _angle(0) {

	pthread_mutex_init(&_mutex, 0);
	return;
}

Renderer::~Renderer() {

	pthread_mutex_destroy(&_mutex);
	return;
}

void Renderer::start() {

	pthread_create(&_threadId, 0, threadStartCallback, this);
	return;
}

void Renderer::stop() {

	// send message to render thread to stop rendering
	pthread_mutex_lock(&_mutex);
	_msg = MSG_RENDER_LOOP_EXIT;
	pthread_mutex_unlock(&_mutex);

	pthread_join(_threadId, 0);

	return;
}

void Renderer::setWindow(ANativeWindow *window) {
	// notify render thread that window has changed
	pthread_mutex_lock(&_mutex);
	_msg = MSG_WINDOW_SET;
	_window = window;
	pthread_mutex_unlock(&_mutex);

	return;
}

void Renderer::renderLoop() {
	bool renderingEnabled = true;

	while (renderingEnabled) {

		pthread_mutex_lock(&_mutex);

		// process incoming messages
		switch (_msg) {

		case MSG_WINDOW_SET:
			initialize();
			break;

		case MSG_RENDER_LOOP_EXIT:
			renderingEnabled = false;
			destroy();
			break;

		default:
			break;
		}
		_msg = MSG_NONE;

		if (_display) {
			drawFrame();
			if (!eglSwapBuffers(_display, _surface)) {
				LOGE("eglSwapBuffers() returned error %d", eglGetError());
			}
		}

		pthread_mutex_unlock(&_mutex);
	}

	return;
}

bool Renderer::initialize() {
	const EGLint attribs[] = { EGL_SURFACE_TYPE, EGL_WINDOW_BIT, EGL_BLUE_SIZE,
			8, EGL_GREEN_SIZE, 8, EGL_RED_SIZE, 8, EGL_NONE };
	EGLDisplay display;
	EGLConfig config;
	EGLint numConfigs;
	EGLint format;
	EGLSurface surface;
	EGLContext context;
	EGLint width;
	EGLint height;
	GLfloat ratio;

	if ((display = eglGetDisplay(EGL_DEFAULT_DISPLAY)) == EGL_NO_DISPLAY) {
		LOGE("eglGetDisplay() returned error %d", eglGetError());
		return false;
	}
	if (!eglInitialize(display, 0, 0)) {
		LOGE("eglInitialize() returned error %d", eglGetError());
		return false;
	}

	if (!eglChooseConfig(display, attribs, &config, 1, &numConfigs)) {
		LOGE("eglChooseConfig() returned error %d", eglGetError());
		destroy();
		return false;
	}

	if (!eglGetConfigAttrib(display, config, EGL_NATIVE_VISUAL_ID, &format)) {
		LOGE("eglGetConfigAttrib() returned error %d", eglGetError());
		destroy();
		return false;
	}

	ANativeWindow_setBuffersGeometry(_window, 0, 0, format);

	if (!(surface = eglCreateWindowSurface(display, config, _window, 0))) {
		LOGE("eglCreateWindowSurface() returned error %d", eglGetError());
		destroy();
		return false;
	}

	if (!(context = eglCreateContext(display, config, 0, 0))) {
		LOGE("eglCreateContext() returned error %d", eglGetError());
		destroy();
		return false;
	}

	if (!eglMakeCurrent(display, surface, surface, context)) {
		LOGE("eglMakeCurrent() returned error %d", eglGetError());
		destroy();
		return false;
	}

	if (!eglQuerySurface(display, surface, EGL_WIDTH, &width)
			|| !eglQuerySurface(display, surface, EGL_HEIGHT, &height)) {
		LOGE("eglQuerySurface() returned error %d", eglGetError());
		destroy();
		return false;
	}

	_display = display;
	_surface = surface;
	_context = context;

	glDisable (GL_DITHER);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST);
	glClearColor(0, 0, 0, 0);
	glEnable (GL_CULL_FACE);
	glShadeModel (GL_SMOOTH);
	glEnable (GL_DEPTH_TEST);

	glViewport(0, 0, width, height);

	ratio = (GLfloat) width / height;
	glMatrixMode (GL_PROJECTION);
	glLoadIdentity();
	glFrustumf(-ratio, ratio, -1, 1, 1, 10);

	return true;
}

void Renderer::destroy() {

	eglMakeCurrent(_display, EGL_NO_SURFACE, EGL_NO_SURFACE, EGL_NO_CONTEXT);
	eglDestroyContext(_display, _context);
	eglDestroySurface(_display, _surface);
	eglTerminate(_display);

	_display = EGL_NO_DISPLAY;
	_surface = EGL_NO_SURFACE;
	_context = EGL_NO_CONTEXT;

	return;
}

void Renderer::drawCubes(float x, float y, float z) {

	glLoadIdentity();
	glTranslatef(x, y, z);
	glRotatef(_angle, 0, 1, 0);
	glRotatef(_angle * 0.25f, 1, 0, 0);

	glEnableClientState (GL_VERTEX_ARRAY);
	glEnableClientState (GL_COLOR_ARRAY);

	glFrontFace (GL_CW);
	glVertexPointer(3, GL_FIXED, 0, vertices);
	glColorPointer(4, GL_FIXED, 0, colors);
	glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, indices);


}

void Renderer::drawFrame() {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glMatrixMode (GL_MODELVIEW);

	float magicNumber = 5, x = -magicNumber-2, y = 8, z = -10.0f;
	_angle += 1.2f;
	for (int i = 0; i < 100; i++) {
		//drawCubes(x , 3, z);
		drawCubes(x+=2 , y , z);
		if (x >= magicNumber) {
			x = -magicNumber-2;
			y -= 2;
		}
	}
}
void circle() {

	int vertexCount = 30;
	float radius = 1.0f;
	float center_x = 0.0f;
	float center_y = 0.0f;

	//create a buffer for vertex data
	float buffer[60]; // = new float[vertexCount*2]; // (x,y) for each vertex
	int idx = 0;

	//center vertex for triangle fan
	buffer[idx++] = center_x;
	buffer[idx++] = center_y;

	//outer vertices of the circle
	int outerVertexCount = vertexCount - 1;

	for (int i = 0; i < outerVertexCount; ++i) {
		float percent = (i / (float) (outerVertexCount - 1));
		float rad = percent * 2 * 3.14;

		//vertex position
		float outer_x = center_x + radius * cos(rad);
		float outer_y = center_y + radius * sin(rad);

		buffer[idx++] = outer_x;
		buffer[idx++] = outer_y;
	}
	glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCount);
}

void* Renderer::threadStartCallback(void *myself) {
	Renderer *renderer = (Renderer*) myself;

	renderer->renderLoop();
	pthread_exit(0);

	return 0;
}

