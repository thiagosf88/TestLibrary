#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include <edu_performance_test_nativo_floatoperation_FloatOperationNative.h>
#include <random.h>
#include <test_library.h>
#include <math.h>


void Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationSqrt(
		JNIEnv* env, jobject thiz, jdouble level) {

	// read in the command-line argument
	//float level = Float.parseDouble(args[0]);
	double epsilon = (double) 1e-15;    // relative error tolerance
	double t = level;              // estimate of the square root of c

	// repeatedly apply Newton update step until desired precision is achieved
	while (abs(t - level / t) > epsilon * t) {
		t = (double) ((level / t + t) / 2.0);
	}

	// print out the estimate of the square root of c
	LOGD("Estimate square root of %f: %f", level, t);
}
void Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationSine(
		JNIEnv* env, jobject thiz, jdouble level) {
	double sine = sin(level);
	LOGD("Sine of %f: %f", level, sine);
}

void Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationMonteCarlo(
		JNIEnv* env, jobject thiz, jdouble level) {

	 Random R = new_Random_seed(113);


	 int under_curve = 0;
	 int count;

	 for (count=0; count < level; count++)
	 {
	 double x= Random_nextDouble(R);
	 double y= Random_nextDouble(R);
	 if ( x*x + y*y <= 1.0)
	 under_curve ++;

	 }

	 Random_delete(R);

	 LOGD("%f", (((double) under_curve / level) * 4.0));
}

double abs(const double d) {
	return (d >= 0) ? d : -d;
}


void daxpy(const int n, const double da, const double *dx, const int dx_off,
		const int incx, double *dy, const int dy_off, const int incy) {
	int i, ix, iy;

	if ((n > 0) && (da != 0)) {
		if (incx != 1 || incy != 1) {

			// code for unequal increments or equal increments not equal to
			// 1

			ix = 0;
			iy = 0;
			if (incx < 0)
				ix = (-n + 1) * incx;
			if (incy < 0)
				iy = (-n + 1) * incy;
			for (i = 0; i < n; i++) {
				dy[iy + dy_off] += da * dx[ix + dx_off];
				ix += incx;
				iy += incy;
			}
			return;
		} else {

			// code for both increments equal to 1

			for (i = 0; i < n; i++)
				dy[i + dy_off] += da * dx[i + dx_off];
		}
	}
}

const double matgen(double **a, const int n, double *b) {
	double norma;
	int init, i, j;

	init = 1325;
	norma = 0.0;
	/*
	 * Next two for() statements switched. Solver wants matrix in column
	 * order. --dmd 3/3/97
	 */

	for (i = 0; i < n; i++) {
		for (j = 0; j < n; j++) {
			init = 3125 * init % 65536;
			a[j][i] = (init - 32768.0) / 16384.0;
			norma = (a[j][i] > norma) ? a[j][i] : norma;
		}

	}

	for (i = 0; i < n; i++) {
		b[i] = 0.0;
	}
	for (j = 0; j < n; j++) {
		for (i = 0; i < n; i++) {
			b[i] += a[j][i];
		}
	}
	return norma;
}

double ddot(const int n, const double *dx, const int dx_off, const int incx,
		const double *dy, const int dy_off, const int incy) {
	double dtemp;
	int i, ix, iy;

	dtemp = 0;

	if (n > 0) {
		if (incx != 1 || incy != 1) {
			// code for unequal increments or equal increments not equal to
			// 1

			ix = 0;
			iy = 0;
			if (incx < 0)
				ix = (-n + 1) * incx;
			if (incy < 0)
				iy = (-n + 1) * incy;
			for (i = 0; i < n; i++) {
				dtemp += dx[ix + dx_off] * dy[iy + dy_off];
				ix += incx;
				iy += incy;
			}
		} else {
			// code for both increments equal to 1
			for (i = 0; i < n; i++)
				dtemp += dx[i + dx_off] * dy[i + dy_off];
		}
	}
	return (dtemp);
}

void dscal(const int n, const double da, double *dx, const int dx_off,
		const int incx) {
	int i, nincx;

	if (n > 0) {
		if (incx != 1) {

			// code for increment not equal to 1

			nincx = n * incx;
			for (i = 0; i < nincx; i += incx)
				dx[i + dx_off] *= da;
		} else {

			// code for increment equal to 1

			for (i = 0; i < n; i++)
				dx[i + dx_off] *= da;
		}
	}
}

int idamax(const int n, const double *dx, const int dx_off, const int incx) {
	double dmax, dtemp;
	int i, ix, itemp = 0;

	if (n < 1) {
		itemp = -1;
	} else if (n == 1) {
		itemp = 0;
	} else if (incx != 1) {

		// code for increment not equal to 1

		dmax = abs(dx[dx_off]);
		ix = 1 + incx;
		for (i = 1; i < n; i++) {
			dtemp = abs(dx[ix + dx_off]);
			if (dtemp > dmax) {
				itemp = i;
				dmax = dtemp;
			}
			ix += incx;
		}
	} else {

		// code for increment equal to 1

		itemp = 0;
		dmax = abs(dx[dx_off]);
		for (i = 1; i < n; i++) {
			dtemp = abs(dx[i + dx_off]);
			if (dtemp > dmax) {
				itemp = i;
				dmax = dtemp;
			}
		}
	}
	return (itemp);
}

/*
 * dgefa factors a double precision matrix by gaussian elimination.
 *
 * dgefa is usually called by dgeco, but it can be called directly with a
 * saving in time if rcond is not needed. (time for dgefa) .
 *
 * on entry
 *
 * a double precision[n][lda] the matrix to be factored.
 *
 * lda integer the leading dimension of the array a .
 *
 * n integer the order of the matrix a .
 *
 * on return
 *
 * a an upper triangular matrix and the multipliers which were used to
 * obtain it. u where l is a product of permutation and unit lower
 * triangular matrices and u is upper triangular.
 *
 * ipvt integer[n] an integer vector of pivot indices.
 *
 * info integer = 0 normal value. = k if u[k][k] .eq. 0.0 . this is not an
 * error condition for this subroutine, but it does indicate that dgesl or
 * dgedi will divide by zero if called. use rcond in dgeco for a reliable
 * indication of singularity.
 *
 * linpack. this version dated 08/14/78. cleve moler, university of new
 * mexico, argonne national lab.
 *
 * functions
 *
 * blas daxpy,dscal,idamax
 */
int dgefa(double **a,  int n, int *ipvt) {
	double * col_k, * col_j;
	double t;
	int j, k, kp1, l, nm1;
	int info;

	// gaussian elimination with partial pivoting

	info = 0;
	nm1 = n - 1;
	if (nm1 >= 0) {
		for (k = 0; k < nm1; k++) {
			col_k = a[k];
			kp1 = k + 1;

			// find l = pivot index

			l = idamax(n - k, col_k, k, 1) + k;
			ipvt[k] = l;

			// zero pivot implies this column already triangularized

			if (col_k[l] != 0) {

				// interchange if necessary

				if (l != k) {
					t = col_k[l];
					col_k[l] = col_k[k];
					col_k[k] = t;
				}

				// compute multipliers

				t = -1.0 / col_k[k];
				dscal(n - (kp1), t, col_k, kp1, 1);

				// row elimination with column indexing

				for (j = kp1; j < n; j++) {
					col_j = a[j];
					t = col_j[l];
					if (l != k) {
						col_j[l] = col_j[k];
						col_j[k] = t;
					}
					daxpy(n - (kp1), t, col_k, kp1, 1, col_j, kp1, 1);
				}
			} else {
				info = k;
			}
		}
	}
	ipvt[n - 1] = n - 1;
	if (a[(n - 1)][(n - 1)] == 0)
		info = n - 1;

	return info;
}

/*
 * dgesl solves the double precision system x = b or trans(a) * x = b using
 * the factors computed by dgeco or dgefa.
 *
 * on entry
 *
 * a double precision[n][lda] the output from dgeco or dgefa.
 *
 * lda integer the leading dimension of the array a .
 *
 * n integer the order of the matrix a .
 *
 * ipvt integer[n] the pivot vector from dgeco or dgefa.
 *
 * b double precision[n] the right hand side vector.
 *
 * job integer x = b , x = b where trans(a) is the transpose.
 *
 * on return
 *
 * b the solution vector x .
 *
 * error condition
 *
 * a division by zero will occur if the input factor contains a zero on the
 * diagonal. technically this indicates singularity but it is often caused
 * by improper arguments or improper setting of lda . it will not occur if
 * the subroutines are called correctly and if dgeco has set rcond .gt. 0.0
 * or dgefa has set info .eq. 0 .
 *
 * c where c is a matrix with p columns dgeco(a,lda,n,ipvt,rcond,z) if
 * (!rcond is too small){ for (j=0,j
 * <p,j++) dgesl(a,lda,n,ipvt,c[j][0],0); }
 *
 * linpack. this version dated 08/14/78 . cleve moler, university of new
 * mexico, argonne national lab.
 *
 * functions
 *
 * blas daxpy,ddot
 */
 void dgesl(double **a,  int n,  int *ipvt, double *b,
		int job) {
	double t;
	int k, kb, l, nm1, kp1;

	nm1 = n - 1;
	if (job == 0) {

		// job = 0 , solve a * x = b. first solve l*y = b

		if (nm1 >= 1) {
			for (k = 0; k < nm1; k++) {
				l = ipvt[k];
				t = b[l];
				if (l != k) {
					b[l] = b[k];
					b[k] = t;
				}
				kp1 = k + 1;
				daxpy(n - (kp1), t, a[k], kp1, 1, b, kp1, 1);
			}
		}

		// now solve u*x = y

		for (kb = 0; kb < n; kb++) {
			k = n - (kb + 1);
			b[k] /= a[k][k];
			t = -b[k];
			daxpy(k, t, a[k], 0, 1, b, 0, 1);
		}
	} else {

		// job = nonzero, solve trans(a) * x = b. first solve trans(u)*y =
		// b

		for (k = 0; k < n; k++) {
			t = ddot(k, a[k], 0, 1, b, 0, 1);
			b[k] = (b[k] - t) / a[k][k];
		}

		// now solve trans(l)*x = y

		if (nm1 >= 1) {
			for (kb = 1; kb < nm1; kb++) {
				k = n - (kb + 1);
				kp1 = k + 1;
				b[k] += ddot(n - (kp1), a[k], kp1, 1, b, kp1, 1);
				l = ipvt[k];
				if (l != k) {
					t = b[l];
					b[l] = b[k];
					b[k] = t;
				}
			}
		}
	}
}

double epslon(const double x) {
	const double a = 4.0e0 / 3.0e0;
	double b, c, eps = 0;

	while (eps == 0) {
		b = a - 1.0;
		c = b + b + b;
		eps = abs(c - 1.0);
	}
	return (eps * abs(x));
}

void dmxpy(int n1, double *y, const int n2, double *x,
		 double **m) {
	int j, i;

	// cleanup odd vector
	for (j = 0; j < n2; j++) {
		for (i = 0; i < n1; i++) {
			y[i] += x[j] * m[j][i];
		}
	}
}

void Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationLinpack(
		JNIEnv* env, jobject thiz, jdouble level) {
double mflops_result = 0.0;
double residn_result = 0.0;
double time_result = 0.0;
double eps_result = 0.0;
int level_n = level <= 5000? level : 5000;
int n2 = level_n * 2;
double ** a;
int n = level_n, i;

if ((a = new double *[n2]) == NULL) {
	LOGE("Allocation error 1 - Float Linpack c++");
}

for (i = 0; i < (n2); i++) {
	/* x_i here is the size of given row, no need to
	 * multiply by sizeof( char ), it's always 1
	 */
	if ((a[i] = new double[n2]) == NULL) {
		LOGE("Allocation error 2 - Float Linpack c++");
	}

	/* probably init the row here */
}

double * b
 = new double[n2];
double * x
 = new double[n2];
double ops, total, norma, normx;
double resid, time;

int * ipvt = new int[n2];

//double mflops_result;
//double residn_result;
//double time_result;
//double eps_result;

n = level_n;

ops = (2.0e0 * (n * n * n)) / 3.0 + 2.0 * (n * n);

norma = matgen(a, n, b);
//time = second();

dgefa(a, n, ipvt);
dgesl(a, n, ipvt, b, 0);

//total = second() - time;

for (i = 0; i < n; i++) {
	x[i] = b[i];
}
norma = matgen(a, n, b);
for (i = 0; i < n; i++) {
	b[i] = -b[i];
}

dmxpy(n, b, n, x, a);
resid = 0.0;
normx = 0.0;
for (i = 0; i < n; i++) {
	resid = (resid > abs(b[i])) ? resid : abs(b[i]);
	normx = (normx > abs(x[i])) ? normx : abs(x[i]);
}

eps_result = epslon(1.0);
/*
 *
 * norma*normx*eps_result ); time_result = total; total);
 *
 * return ("Mflops/s: " + mflops_result + " Time: " + time_result + "
 * secs" + " Norm Res: " + residn_result + " Precision: " +
 * eps_result);
 */

residn_result = resid / (n * norma * normx * eps_result);
residn_result += 0.005; // for rounding
residn_result = (int) (residn_result * 100);
residn_result /= 100;

/*time_result = total;
time_result += 0.005; // for rounding
time_result = (int) (time_result * 100);
time_result /= 100;

mflops_result = ops / (1.0e6 * total);
mflops_result += 0.0005; // for rounding
mflops_result = (int) (mflops_result * 1000);
mflops_result /= 1000;*/
//delete a[];
delete [] b;
delete  [] x;
delete [] ipvt;
delete [] a;

LOGD("Mflops/s: %f  Time: %f secs  Norm Res: %f Precision: %f", mflops_result,
		time_result, residn_result, eps_result);
}

void Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationCubic(JNIEnv* env, jobject thiz, jdouble level) {
		double a = level, b = level * 3, c = level * 4, d = level * 5;

		double * x;

		double a1 = b / a, a2 = c / a, a3 = d / a;
		double Q = (a1 * a1 - 3.0 * a2) / 9.0;
		double R = (2.0 * a1 * a1 * a1 - 9.0 * a1 * a2 + 27.0 * a3) / 54.0;
		double R2_Q3 = R * R - Q * Q * Q;

		double theta;

		if (R2_Q3 <= 0) {
			x = new double[3];
			theta = acos(R / sqrt(Q * Q * Q));
			x[0] = -2.0 * sqrt(Q) * cos(theta / 3.0) - a1 / 3.0;
			x[1] = -2.0 * sqrt(Q)
					* cos((theta + 2.0 * M_PI) / 3.0) - a1 / 3.0;
			x[2] = -2.0 * sqrt(Q)
					* cos((theta + 4.0 * M_PI) / 3.0) - a1 / 3.0;
		} else {
			x = new double[1];
			x[0] = pow(sqrt(R2_Q3) + fabs(R), 1 / 3.0);
			x[0] += Q / x[0];
			x[0] *= (R < 0.0) ? 1 : -1;
			x[0] -= a1 / 3.0;
		}

	}


jdouble Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationrad2deg(JNIEnv* env, jobject thiz, jdouble level) {
		return (180.0 * level / (M_PI));
	}


jdouble Java_edu_performance_test_nativo_floatoperation_FloatOperationNative_testNFloatOperationdeg2rad(JNIEnv* env, jobject thiz, jdouble level) {
		return (M_PI * level / 180.0);
	}


