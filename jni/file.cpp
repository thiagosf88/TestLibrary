#include <jni.h>
#include <stdlib.h>
#include <edu_performance_test_nativo_fileoperation_FileOperationNative.h>
#include <stdio.h>
#include <fstream>
#include <iostream>
#include <string>
#include <test_library.h>


JNIEXPORT void JNICALL Java_edu_performance_test_nativo_fileoperation_FileOperationNative_testNreadSequentialAcessFile
  (JNIEnv *env, jobject thiz, jstring filePath, jint level)
{


	const char *nFilePath = env->GetStringUTFChars(filePath, 0);
	 //assert(NULL != nFilePath);
	//LOGE("tentando");
	FILE* file = fopen(nFilePath,"r");
	if (file == NULL){

		LOGE("The file was not found");
		exit(1);
	}
	 char buffer[10000];
	  char *pbuff;

	  freopen(nFilePath,"r",stdin);

	  while(!feof(stdin)) {
		  if (fgets(buffer, sizeof buffer, stdin)) {
		          //LOGE("%s\n", buffer);
		      }
	  }

	  fclose(file);
	  env->ReleaseStringUTFChars(filePath, nFilePath);
	  return;

}


JNIEXPORT void JNICALL Java_edu_performance_test_nativo_fileoperation_FileOperationNative_testNreadRandomAcessFile
  (JNIEnv * env, jobject thiz, jstring filePath, jint position, jint level)
{
	/*const char *nFilePath = env->GetStringUTFChars(filePath, 0);
	std::ofstream myfile(nFilePath);
	  if (myfile.is_open())
	  {
	    myfile << "This is a line.\n";
	    myfile << "This is another line.\n";
	    myfile.close();
	  }
	  env->ReleaseStringUTFChars(filePath, nFilePath);*/
	int c = 0;
	const char *nFilePath = env->GetStringUTFChars(filePath, 0);
	std::ifstream inf(nFilePath);

	    // If we couldn't open the input file stream for reading
	    if (!inf)
	    {
	        // Print an error and exit
	        std::cerr << "Uh oh, Sample.dat could not be opened for reading!" << std::endl;
	        exit(1);
	    }

	    std::string buff;

	    inf.seekg(position);
	    while(std::getline(inf, buff) ) {//|| (buff.size() < level)) verificar level
	    //LOGD("%s", buff.c_str());
	    	//c++;
	    //LOGD("%i", c);
	    }
	    inf.close();
}


JNIEXPORT void JNICALL Java_edu_performance_test_nativo_fileoperation_FileOperationNative_testNwriteRandomAcessFile
  (JNIEnv * env, jobject thiz, jstring filePath, jint position, jstring stretch)
{
	const char *nFilePath = env->GetStringUTFChars(filePath, 0);
	const char *nStretch = env->GetStringUTFChars(stretch, 0);
	int size = strlen(nStretch);
		std::ofstream out(nFilePath);

		    // If we couldn't open the input file stream for reading
		    if (!out)
		    {
		        // Print an error and exit
		        std::cerr << "Uh oh, " << nFilePath << " could not be opened for writing!" << std::endl;
		        exit(1);
		    }



		    out.seekp(position, std::ios_base::beg);
		    out.write(nStretch, size);
		    out.close();

		      env->ReleaseStringUTFChars(filePath, nFilePath);
		      env->ReleaseStringUTFChars(stretch, nStretch);
		    //while(std::getline(out, buff) || (buff.size() < level))
		    //LOGD("%s", buff.c_str());

		    //LOGD("%s", buff);
}
