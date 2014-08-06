#include <jni.h>
#include <stdlib.h>
#include <edu_performance_test_nativo_memoryoperation_MemoryOperationNative.h>
#include <stdio.h>


char * buffer;

void
Java_edu_performance_test_nativo_memoryoperation_MemoryOperationNative_testNallocMemory( JNIEnv* env,
        jobject thiz, jint level)
{
	int i,n;

	  //scanf ("%d", &i);
	  i = level;
	  //printf (&i);
	  buffer = (char*) malloc (i+1);
	  if (buffer==NULL) exit (1);

	  for (n=0; n<i; n++)
	    buffer[n]='t';
	  buffer[i]='\0';

	  printf ("Random string: %s\n",buffer);
	  //free (buffer);

}

void
Java_edu_performance_test_nativo_memoryoperation_MemoryOperationNative_testNfreeMemory( JNIEnv* env,
        jobject thiz)
{

	  free (buffer);

}


jstring
Java_edu_performance_test_nativo_memoryoperation_MemoryOperationNative_testNcopyMemory( JNIEnv* env,
        jobject thiz){
char * myname = 0;


   //using memcpy to copy string:
  memcpy ( &myname, &buffer, sizeof(buffer));

return   (env)->NewStringUTF(myname);
  // using memcpy to copy structure: /

}



