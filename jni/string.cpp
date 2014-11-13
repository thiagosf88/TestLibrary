#include <jni.h>
#include <stdlib.h>
#include <edu_performance_test_nativo_stringoperation_StringOperationNative.h>
#include <stdio.h>
#include <string>
#include <test_library.h>




void Java_edu_performance_test_nativo_stringoperation_StringOperationNative_testTNMsearchString
  (JNIEnv * env, jobject thiz, jstring  jsearchable, jstring jstretch){
	std::string searchable (env->GetStringUTFChars(jsearchable, 0));
	std::string stretch (env->GetStringUTFChars(jstretch, 0));

	LOGE("%i",searchable.find(stretch));

//TODO verificar se está funcionando string nativo
	//env->ReleaseStringUTFChars(jsearchable, searchable);
	//env->ReleaseStringUTFChars(jstretch, stretch);
}
