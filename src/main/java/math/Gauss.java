package math;

public class Gauss {
	public double[][] a;
	public double[] b, roots;
	int[] trueIndexes;

	public int[] getTrueIndexes() {
		return trueIndexes;
	}

	public String Det;
	public String Gauss;
	public static String Obr;

	public String getDet() {
		return Det;
	}

	public double[][] getA() {
		return a;
	}

	public void setA(double[][] a) {
		this.a = a;
	}

	public double[] getB() {
		return b;
	}

	public void setB(double[] b) {
		this.b = b;
	}

	// ///////////////////////////////////////
	// определитель

	public double det(double[][] a) {
		double det = 1, d = 0;
		int n = a.length, count = 0;
		String DETit = "";
		Det = "<h2><center>Нахождение определителя методом Гаусса</center></h2> <p><h3>Дана матрица A:</h3></p>";
		Det+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Det += "<tr>";
			for (int j = 0; j < n; j++) {
				Det += "<td>";
				Det += (a[i][j] + " ");
				Det += "</td>";
			}
			Det += "</tr>";
		}
		Det+="</table>";
		//проверим, надо ли менять местами строки
		int q=0;
		for (int i = 0; i < n; i++) {
			if (a[i][i] == 0) {// значит надо и это надо добавить в отчет
				count = lineExchange(a);q++;// это теперь наша исходная матрица
			}
		}if(q>0){
			Det+="<br /><h3>Меняем строки таблицы:</h3>";
		Det+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Det += "<tr>";
			for (int j = 0; j < n; j++) {
				Det += "<td>";
				Det += (a[i][j] + " ");
				Det += "</td>";
			}
			Det += "</tr>";
		}
		Det+="</table>";
		}
		for (int i = 0; i < n; i++) {
			Det += "<h3><i>Итерация № " + (i + 1) + "</i></h3>";

			for (int j = i + 1; j < n; j++) {
				a[i][j] /= a[i][i];
			}

			d = Math.round(a[i][i] * 100);
			double z = Math.round(a[i][i] * 100);
			double x = Math.round(det * 100);
			if (i == 0)
				DETit += (z /= 100);
			else
				DETit += " * " + (z /= 100);
			det *= a[i][i];
			System.out.println("Определитель = " + det);
			Det += "<br />Делим строку " + (i + 1) + " на элемент A[" + (i + 1)
					+ "][" + (i + 1) + "]<br />";
			if (i + 1 < n) {
				Det += "Домножаем строку " + (i + 1) + " на элемент А["
						+ (i + 2) + "][" + (i + 1) + "]<br />";
				Det += "Отнимаем от строки " + (i + 2) + " строку " + (i + 1)
						+ "<br />";
			}
			a[i][i] = 1;
			for (int k = i + 1; k < n; k++) {
				for (int j = i + 1; j < n; j++) {
					a[k][j] -= a[i][j] * a[k][i];
				}
				a[k][i] = 0;

			}
			Det+="<table border=1>";
			for (int k = 0; k < n; k++) {
				Det+="<tr>";
				for (int j = 0; j < n; j++) {
					Det+="<td>";
					d = Math.round(a[k][j] * 100);
					Det += (d / 100) + " ";
					Det+="</td>";
				}
				Det+="</tr>";
			
			}
			Det+="</table>";
		}
		Det += "<br /><h3>Определитель = " + DETit + " = " + det + "</h3>";
		Det += "<p></p>";
		if (count % 2 != 0)
			return 0 - det;
		else
			return det;
	}

	// ////////////////////////////////////////

	/**
	 * @return the gauss
	 */
	public String getGauss() {
		return Gauss;
	}

	// обратная матрица
	public double[][] reverseMatrix(double[][] a) {
		int n = a.length;
		Obr = "<h2><center>Нахождение обратной матрицы методом Гаусса</center></h2> <p><h3>Дана матрица A:</h3></p>";
		Obr+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Obr+="<td>";
				Obr += (a[i][j] + " ");
				Obr+="</td>";
			}
			Obr+="</tr>";
		}
		Obr+="</table>";
		double[] b = new double[n];
		double[][] revA = new double[n][n];
		for (int i = 0; i < n; i++) {
			double[][] copy = new double[n][n];
			for (int t = 0; t < n; t++)
				System.arraycopy(a[t], 0, copy[t], 0, n);
			double[] xij = new double[n];
			b[i] = 1;
			for (int j = 0; j < n; j++)
				if (j != i)
					b[j] = 0;
			xij = this.solve(copy, b);
			for (int k = 0; k < n; k++) {
				revA[k][i] = xij[k];
			}
			copy = null;
		}
		Obr += "<br /><h3>Обратная матрица:</h3><br />";
		Obr+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Obr+="<td>";
				double y1 = Math.round(revA[i][j] * 10000);
				Obr += ((y1 / 10000) + "   ");
				Obr+="</td>";
			}
			Obr+="</tr>";
		}
		Obr+="</table>";
		Obr += "<br /><h3>Проверка:</h3>";
		Obr += "<br />Умножим исходную матрицу<br />";
		Obr+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Obr+="<td>";
				double y1 = Math.round(a[i][j] * 10000);
				Obr += ((y1 / 10000) + "   ");
				Obr+="</td>";
			}
			Obr+="</tr>";
			//Obr += "<br>";
		}
		Obr+="</table>";
		Obr += "<br />На обратную матрицу<br />";
		Obr+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Obr+="<td>";
				double y1 = Math.round(revA[i][j] * 10000);
				Obr += ((y1 / 10000) + "   ");
				Obr+="</td>";
			}
			Obr+="</tr>";
			//Obr += "<br>";
		}
		Obr+="</table>";
		double[][]check = GaussSeidel.matrixMultiplication(a, revA);
		Obr += "<br />И получим:<br />";
		Obr+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Obr+="<td>";
				double y1 = Math.round(check[i][j] * 10000);
				Obr += ((y1 / 10000) + "   ");
				Obr+="</td>";
			}
			Obr+="</tr>";
			//Obr += "<br>";
		}
		Obr+="</table>";
		//для проверки(для отчета) вывести исходную а, рядом с ней revA - типа
		//мы их перемножаем и рядом следующую:
		
		
		return revA;
	}

	// //////////////////////////////////////
	// меняет местами строки, реализует выбор ведущго элемента

	/**
	 * @return the obr
	 */
	public String getReverse() {
		return Obr;
	}

	public int lineExchange(double[][] a, double[] b) {

		int n = a.length;
		System.out.println("before line exhange");
		for (int ii = 0; ii < n; ii++) {
			for (int j = 0; j < n; j++)
				System.out.println("a" + ii + j + " : " + a[ii][j]);
		}
		for (int j = 0; j < n; j++)
			System.out.println("b" + j + " : " + b[j]);

		if (!thereAreZeros(a)) {
			// this.trueIndexes = new int[n];
			int count = 0;
			double[][] ac = new double[n][n];
			for (int i = 0; i < n; i++)
				System.arraycopy(a[i], 0, ac[i], 0, n);
			double[] bc = new double[n];
			System.arraycopy(b, 0, bc, 0, n);
			double max;
			int maxInd;
			for (int j = 0; j < n; j++) {
				max = 0;
				maxInd = j;
				for (int i = 0; i < n; i++) {
					if (ac[i] == null)
						continue;
					if (Math.abs(ac[i][j]) > max) {
						max = Math.abs(ac[i][j]);
						maxInd = i;
						count++;
					}
				}
				System.arraycopy(ac[maxInd], 0, a[j], 0, n);
				b[j] = bc[maxInd];
				ac[maxInd] = null;
				// trueIndexes[maxInd] = j;
			}
			System.out.println("after line exhange");
			for (int ii = 0; ii < n; ii++) {
				for (int k = 0; k < n; k++)
					System.out.println("a" + ii + k + " : " + a[ii][k]);
			}
			for (int k = 0; k < n; k++)
				System.out.println("b" + k + " : " + b[k]);
			return count;
		} else
			return carefulLineExchange(a, b);

	}

	public boolean thereAreZeros(double[][] a) {
		int n = a.length;
		for (int ii = 0; ii < n; ii++) {
			for (int k = 0; k < n; k++)
				if (a[ii][k] == 0)
					return true;
		}
		return false;
	}

	// максимальный по модулю элемент массива
	public int selectMax(double[] ai) {
		double max = 0;
		int mi = 0;
		for (int i = 0; i < ai.length; i++) {
			if (Math.abs(ai[i]) > max) {
				max = Math.abs(ai[i]);
				mi = i;
			}
		}
		return mi;
	}

	public boolean notIn(int j, int[] zs, int until) {
		for (int i = 0; i < until; i++) {
			if (j == zs[i])
				return false;
		}
		return true;
	}

	// правда ли что в столбике все числа нули
	public boolean allZeros(double[][] a, int j, int except) {
		for (int i = 0; i < a.length; i++) {
			if (j < a[i].length)
				if (i != except && a[i][j] != 0.0)
					return false;
		}
		return true;
	}

	public int carefulLineExchange(double[][] a, double[] b) {
		int n = a.length;
		int count = 0;
		double[][] ac = new double[n][n];
		for (int i = 0; i < n; i++)
			System.arraycopy(a[i], 0, ac[i], 0, n);
		double[] bc = new double[n];
		System.arraycopy(b, 0, bc, 0, n);

		int[][] zInd = new int[n][n]; // массив индексов нулевых элементов
		int zIndCount[] = new int[n]; // количествo нулей в строках
		int[] zcDown = new int[n]; // очередь индексов по убыванию количества
		// нулей
		// ///////////////////////////////
		// заполняем zInd и zIndCount
		for (int i = 0; i < n; i++) {
			zIndCount[i] = 0;
			for (int j = 0; j < n; j++) {
				if (a[i][j] == 0) {
					zInd[i][zIndCount[i]] = j;
					zIndCount[i]++;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++)
				System.out.println("zInd " + i + j + " " + zInd[i][j]);
			System.out.println("zIndCount " + i + " " + zIndCount[i]);
		}
		// ///////////////////////////////
		// заполняем zcDown
		int[] copy = new int[n];
		System.arraycopy(zIndCount, 0, copy, 0, n);
		for (int t = 0; t < n; t++) 
			System.out.println("copy " + t + " " + copy[t]);
		for (int i = 0; i < n; i++) {
			int mv = -1, ind = 0;
			for (int j = 0; j < n; j++) {
				if (copy[j] > mv) {
					mv = copy[j];
					ind = j;
				}
			}
			copy[ind] = -1;
			zcDown[i] = ind;
			for (int t = 0; t < n; t++) 
				System.out.println("copy " + t + " " + copy[t]);
		}
		for (int i = 0; i < n; i++) {
			System.out.println("zcDown " + i + " " + zcDown[i]);
			for (int j = 0; j < n; j++)
				System.out.println("a" + zcDown[i] + j + " : " + a[zcDown[i]][j]);
		}
		// ////////////////////////////////
		int[] taken = new int[n];
		int ct = 0;
		for (int iii = 0; iii < n; iii++) {
			for (int j = 0; j < n; j++)
				a[iii][j] = 0;
			b[iii] = 0;
		}
		for (int ii = 0; ii < n; ii++) {
			int i = zcDown[ii];
			if (zIndCount[i] != 0) { // если в строке есть нули
				for (int j = 0; j < n; j++) { // пошли по ее элементам
					if (notIn(j, zInd[i], zIndCount[i]) && notIn(j, taken, ct)) // если не ноль
						if (allZeros(a, j, i)) { // и все кроме него в столбце
							// нули
							System.arraycopy(ac[i], 0, a[j], 0, n);
							b[j] = bc[i];
							taken[ct] = j;
							ct++;
							ac[i] = null;
							if (i != j)
								count++;
							System.out.println("one");
//							if (ct == n)
//								return count;
						}
				}
				if (ac[i] != null) {
					int j = selectMax(ac[i]);
					if (notIn(j, taken, ct)) {
						System.arraycopy(ac[i], 0, a[j], 0, n);
						b[j] = bc[i];
						taken[ct] = j;
						ct++;
						ac[i] = null;
						if (i != j)
							count++;
						System.out.println("two");
//						if (ct == n)
//							return count;
					} else
						for (int k = 0; k < n; k++) {
							if (ac[i] == null && ac[i][k] != 0 && notIn(k, taken, ct)) {
								System.arraycopy(ac[i], 0, a[k], 0, n);
								b[k] = bc[i];
								taken[ct] = k;
								ct++;
								ac[i] = null;
								if (i != j)
									count++;
								System.out.println("three");
//								if (ct == n)
//									return count;
							}
						}
				}
			} else {
				if (ct == n)
					return count;
				else {
					int j = selectMax(ac[i]);
					if (notIn(j, taken, ct)) {
						System.arraycopy(ac[i], 0, a[j], 0, n);
						b[j] = bc[i];
						taken[ct] = j;
						ct++;
						ac[i] = null;
						if (i != j)
							count++;
						System.out.println("four");
//						if (ct == n)
//							return count;
					} else
						for (int k = 0; k < n; k++) {
							if (ac[i] != null && ac[i][k] != 0 && notIn(k, taken, ct)) {
								System.arraycopy(ac[i], 0, a[k], 0, n);
								b[k] = bc[i];
								taken[ct] = k;
								ct++;
								ac[i] = null;
								if (i != k)
									count++;
								System.out.println("five");
//								if (ct == n)
//									return count;
							}
						}
				}
			}
			for (int j = 0; j < n; j++)
				System.out.println("taken" + j + " : " + taken[j]);
			System.out.println("ct " + ct);
		}

		return count;
	}

	public static int lineExchange(double[][] a) {
		int n = a.length;
		int count = 0;
		double[][] ac = new double[n][n];
		for (int i = 0; i < n; i++)
			System.arraycopy(a[i], 0, ac[i], 0, n);
		double max;
		int maxInd;
		for (int j = 0; j < n; j++) {
			max = 0;
			maxInd = j;
			for (int i = 0; i < n; i++) {
				if (ac[i] == null)
					continue;
				if (Math.abs(ac[i][j]) > max) {
					max = Math.abs(ac[i][j]);
					maxInd = i;
					count++;
				}
			}
			System.arraycopy(ac[maxInd], 0, a[j], 0, n);
			ac[maxInd] = null;
		}
		return count;
	}

	// ////////////////////////////////////////
	// решение СЛУ методом Гаусса
	public double[] solve(double[][] a, double[] b) {
		int n = a.length;
		double[][]ishA=new double[n][n];
		double[]ishB=new double[n];
		// прямой ход
		Gauss = "<h2><center>Решение СЛАУ методом Гаусса</center></h2> <h3>Даны матрицы A и В:</h3>";
		Gauss+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Gauss+="<tr>";
			for (int j = 0; j < n; j++) {
				Gauss+="<td>";
				ishA[i][j]=a[i][j];
				Gauss += (a[i][j] + "*X" + (j + 1) + " ");
				Gauss+="</td>";
			}
			Gauss+="<td>";
			ishB[i]=b[i];
			Gauss += "= " + b[i];
			Gauss+="</td>";
			Gauss+="</tr>";
		}
		Gauss+="</table>";
		
		 //в конце отчета вывести в виде проверки то же самое
		//но подставляя вместо Х цифры из массива ответов
		//вывести дважды - в виде строки и не в виде строки(чтобы произведения посчитались и 
		//получилось цифра=цифра)
		lineExchange(a, b); // тут поменялись местами строчки, реализован выбор
		// ведущих элементов
		Gauss += "<br /><h3>Реализуем выбор ведущих элементов</h3>\n";
		Obr += "<br /><h3>Реализуем выбор ведущих элементов</h3>\n";
		Gauss+="<table border=1>";
		Obr+="<table border=1>";
		for (int i = 0; i < n; i++) {
			Gauss+="<tr>";
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Gauss+="<td>";
				Obr+="<td>";
				Gauss += (a[i][j] + "*X" + (j + 1) + " ");
				Obr += (a[i][j] + "*X" + (j + 1) + " ");
				Gauss+="</td>";
				Obr+="</td>";
			}
			Gauss+="<td>";
			Gauss += "= " + b[i] ;
			Gauss+="</td>";
			Gauss+="</tr>";
			Obr+="</tr>";
			//Obr += "= " + b[i];
		}
		Gauss+="</table>";
		Obr+="</table>";
		Gauss += "<h3>Прямой ход</h3>";
		Obr += "<h3>Прямой ход</h3>";
		for (int i = 0; i < n; i++) {

			Gauss += "<i><b>Итерация № " + (i + 1) + "</b></i><br />";
			Obr += "<i><b>Итерация № " + (i + 1) + "</b></i><br />";
			// Gauss+=a[i][i]+"*X1"+" ";
			for (int j = i + 1; j < n; j++) {
				a[i][j] /= a[i][i];

			}

			b[i] /= a[i][i];

			a[i][i] = 1;

			Gauss += "<br />Делим строку " + (i + 1) + " на элемент A[" + (i + 1)
					+ "][" + (i + 1) + "]<br />";
			Obr += "<br />Делим строку " + (i + 1) + " на элемент A[" + (i + 1)
					+ "][" + (i + 1) + "]<br />";
			if (i + 1 < n) {
				Gauss += "Домножаем строку " + (i + 1) + " на элемент А["
						+ (i + 2) + "][" + (i + 1) + "]<br />";
				Gauss += "Отнимаем от строки " + (i + 2) + " строку " + (i + 1);
//						+ "<p>";
				Obr += "Домножаем строку " + (i + 1) + " на элемент А["
						+ (i + 2) + "][" + (i + 1) + "]<br />";
				Obr += "Отнимаем от строки " + (i + 2) + " строку " + (i + 1);
//						+ "<p>";
			}
			for (int k = i + 1; k < n; k++) {
				b[k] -= b[i] * a[k][i];
				for (int j = i + 1; j < n; j++) {
					a[k][j] -= a[i][j] * a[k][i];
				}
				a[k][i] = 0;
			}
			Gauss+="<table border=1>";
			Obr+="<table border=1>";
			for (int t = 0; t < n; t++) {
				Gauss+="<tr>";
				Obr+="<tr>";
				for (int j = 0; j < n; j++) {
					Gauss+="<td>";
					Obr+="<td>";
					double k1 = Math.round(a[t][j] * 100);
					Gauss += (k1 / 100 + "*X" + (j + 1) + " ");
					Obr += (k1 / 100 + "*X" + (j + 1) + " ");
					Gauss+="</td>";
					Obr+="</td>";
				}
				Gauss+="<td>";
				//Obr+="<td>";
				double f = Math.round(b[t] * 100);
				Gauss += "= " + f / 100;
				Gauss+="</td>";
				Gauss+="</tr>";
				Obr+="</tr>";
			}
			Gauss+="</table>";
			Obr+="</table>";
			Gauss += "<br />";
			Obr += "<br />";
		}
		// обратный ход

		double[] x = new double[n];
		Gauss += "<h3>Обратный ход</h3>";
		Obr += "<h3>Обратный ход</h3>";
		for (int i = n - 1; i != -1; i--) {
			for (int j = i - 1; j != -1; j--) {
				b[j] -= b[i] * a[j][i];
				a[j][i] -= a[i][i] * a[j][i];
			}
		}
		Gauss+="<table border=1>";
		Obr+="<table border=1>";
		for (int t = 0; t < n; t++) {
			Gauss+="<tr>";
			Obr+="<tr>";
			for (int j = 0; j < n; j++) {
				Gauss+="<td>";
				Obr+="<td>";
				double k1 = Math.round(a[t][j] * 100);
				Gauss += ((k1 / 100) + "*X" + (j + 1) + " ");
				Obr += ((k1 / 100) + "*X" + (j + 1) + " ");
				Gauss+="</td>";
				Obr+="</td>";
			}
			Gauss+="<td>";
			//Obr+="<td>";
			double f = Math.round(b[t] * 100);
			Gauss += "= " + f / 100 ;
			Gauss+="</td>";
			Gauss+="</tr>";
			Obr+="</tr>";
			//Obr += "= " + f / 100 ;
		}
		Gauss+="</table>";
		Obr+="</table>";
		Gauss += "<br /><h3>Матрица решений:</h3><br />";
		Gauss+="<table border=1>";
		for (int i = 0; i < n; i++)
			x[i] = b[i];
			Gauss+="<tr>";
		for (int i = 0; i < n; i++) {
			Gauss+="<td>";
			Gauss += "X" + i + " = " + x[i];
			Gauss+="</td>";
			System.out.println("x" + i + " : " + x[i]);
		}
		Gauss+="</tr>";
		Gauss+="</table>";
		Gauss += "<br /><br />";
		this.roots = x;
		Gauss += "<br /><h3>Проверка:</h3>";
		Gauss+="<table border=0>";
		for (int i = 0; i < n; i++) {
			Gauss+="<tr>";
			for (int j = 0; j < n; j++) {
				Gauss+="<td>";
				double k1 = Math.round(x[j] * 100);
				if(j==(n-1))Gauss += ("("+ishA[i][j] + ") * (" + (k1/100) + ") ");
				else Gauss += ("("+ishA[i][j] + ") * (" + (k1/100) + ") + ");
				Gauss+="</td>";
				
			}
			Gauss+="<td>";
			Gauss+=" = ";
			Gauss+="</td>";
			for (int j = 0; j < n; j++) {
				Gauss+="<td>";
				double k1 = Math.round(x[j] * 100);
				double k11=Math.round((ishA[i][j]*(k1/100))*1000);
				
				if(k11<0){
				if(j==(n-1))Gauss += "("+(k11/1000) + ") ";
				else Gauss += "("+((k11/1000) + ") + ");
				}
				else{
					if(j==(n-1))Gauss += ((k11/1000) + " ");
					else Gauss += ((k11/1000) + " + ");
				}
				Gauss+="</td>";
			}
			Gauss+="<td>";
			Gauss += "= " + ishB[i];
			Gauss+="</td>";
			Gauss+="</tr>";
		}
		Gauss+="</table>";
		// System.out.println(Gauss);
		
		return x;
	}

	// возвращает корни системы в последовательности, в которой они бы
	// находились,
	// если бы мы не переставляли местами строки (нужно для Крылова)
	// public double[] nativeRootSequence() {
	// double[] nrs = new double[this.roots.length];
	//
	// return nrs;
	// }

	public static void main(String[] args) {
		Gauss g = new Gauss();
		double a[][] = { { 0, 0, 3, 2 }, { 0, 5, 0, 1 }, { 0, 1, 4, 2 },
				{ 2, 3, 0, 0 } };
		double[] b = { 1, 2, 3, 4 };
		int n = a.length;
		int bla = g.carefulLineExchange(a, b);
		for (int ii = 0; ii < n; ii++) {
			for (int j = 0; j < n; j++)
				System.out.println("a" + ii + j + " : " + a[ii][j]);
		}
		for (int j = 0; j < n; j++)
			System.out.println("b" + j + " : " + b[j]);
	}
}
