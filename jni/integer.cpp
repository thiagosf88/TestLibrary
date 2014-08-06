#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include <edu_performance_test_nativo_integeroperation_IntegerOperationNative.h>
#include <android/log.h>
#include <test_library.h>


jint
Java_edu_performance_test_nativo_integeroperation_IntegerOperationNative_testNIntegerOperationSieve( JNIEnv* env,
        jobject thiz, jint level)
{


			int N = level;

			// initially assume all integers are prime
			bool * isPrime = new bool[N + 1];
			for (int i = 2; i <= N; i++) {
				isPrime[i] = true;
			}

			// mark non-primes <= N using Sieve of Eratosthenes
			for (int i = 2; i*i <= N; i++) {

				// if i is prime, then mark multiples of i as nonprime
				// suffices to consider mutiples i, i+1, ..., N/i
				if (isPrime[i]) {
					for (int j = i; i*j <= N; j++) {
						isPrime[i*j] = false;
					}
				}
			}

			// count primes
			int primes = 0;
			for (int i = 2; i <= N; i++) {
				if (isPrime[i]) primes++;
			}

			delete [] isPrime;

			return primes;
}

void
Java_edu_performance_test_nativo_integeroperation_IntegerOperationNative_testNIntegerOperationFibonacci( JNIEnv* env,
        jobject thiz, jint level)
{
	LOGE("entrando");
	int n = level > 30 ? 30 : level;
	int i = 1;
						int j = 0;
						int t;
						for(int k = 1; k <= n; k++){
							t = i + j;
							i = j;
							j = t;
							LOGE( "%d",j);
						}

						LOGE("Fibonacci number = %d",j);

}

long ackermann(long m, long n) {
    if (m == 0) return n + 1;
    if (n == 0) return ackermann(m - 1, 1);
    return ackermann(m - 1, ackermann(m, n - 1));
 }

void
Java_edu_performance_test_nativo_integeroperation_IntegerOperationNative_testNIntegerOperationAckermann( JNIEnv* env,
        jobject thiz, jint level)
{
	//level = level > 2 ? 2 : level;
	level = 2;
	long result = ackermann(level, level);
 LOGE("ackermann number: %ld", result);
}






