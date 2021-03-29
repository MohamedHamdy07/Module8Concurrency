import java.util.Random;

class sums extends Thread {

	private int[] x;

	private int l, h, p;
//sets array length and height 
	public sums(int[] x, int l, int h)

		{

			this.x = x;

			this.l = l;

			this.h = Math.min(h, x.length);

		}
 //gets the partial sum of thread
	public int getSum()

		{

			return p;

		}

	public void run()

		{

			p = sum(x, l, h);

		}
//gets sum using single thread
	public static int sum(int[] x)

		{

				return sum(x, 0, x.length);

		}

	public static int sum(int[] x, int l, int h)

		{

			int total = 0;
//gets sum of array
			for (int i = l; i < h; i++) 
				{

					total += x[i];

				}

			return total;

		}
//gets the sum using parallel threads
	public static int parallelSum(int[] x)

		{

				return parallelSum(x, Runtime.getRuntime().availableProcessors());

		}

	public static int parallelSum(int[] x, int t)

		{

			int size = (int) Math.ceil(x.length * 1.0 / t);

			sums[] sums = new sums[t];

			for (int i = 0; i < t; i++) 
				{

					sums[i] = new sums(x, i * size, (i + 1) * size);

						sums[i].start();

				}

			try 
				{

					for (sums sum : sums) 
					{

						sum.join();

					}

				} 
			catch (InterruptedException e) { }

			int total = 0;

			for (sums sum : sums) 
				{

					total += sum.getSum();

				}

			return total;

		}

	}

public class concurrency {

public static void main(String[] args)

	{
//creates and fills array with random numbers
		Random r = new Random();

		int[] x = new int[200000000];

		for (int i = 0; i < x.length; i++) 
		{

			x[i] = r.nextInt(10) + 1;

		}

		long st = System.currentTimeMillis();

		System.out.println("Sum for Single: " + sums.sum(x));
		
//calculates runtime
		System.out.println("Single Thread Time: " + (System.currentTimeMillis() - st));

		st = System.currentTimeMillis();

		System.out.println("Sum for Parallel: " + sums.parallelSum(x));

		System.out.println("Parallel Thread Time: " + (System.currentTimeMillis() - st));

	}

}