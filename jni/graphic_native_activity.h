#ifndef Included_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity
#define Included_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity

extern "C" {
    JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnStart(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnResume(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnPause(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeOnStop(JNIEnv* jenv, jobject obj);
    JNIEXPORT void JNICALL Java_edu_performance_test_nativo_graphicoperation_GraphicNativeActivity_nativeSetSurface(JNIEnv* jenv, jobject obj, jobject surface);
};

#endif
