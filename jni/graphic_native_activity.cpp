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
#include <jni.h>
#include <android/native_window.h> // requires ndk r5 or newer
#include <android/native_window_jni.h> // requires ndk r5 or newer

#include "graphic_native_activity.h"

#include "renderer.h"



static ANativeWindow *window = 0;
static Renderer *renderer = 0;

JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnStart(JNIEnv* jenv, jobject obj)
{
    renderer = new Renderer();
    return;
}

JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnResume(JNIEnv* jenv, jobject obj)
{

    renderer->start();
    return;
}

JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnPause(JNIEnv* jenv, jobject obj)
{

    renderer->stop();
    return;
}

JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnStop(JNIEnv* jenv, jobject obj)
{

    delete renderer;
    renderer = 0;
    return;
}

JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeSetSurface(JNIEnv* jenv, jobject obj, jobject surface)
{
    if (surface != 0) {
        window = ANativeWindow_fromSurface(jenv, surface);

        renderer->setWindow(window);
    } else {

        ANativeWindow_release(window);
    }

    return;
}

