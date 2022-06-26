import java.io.Console;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats 
{
    private Percolation per;
    private int[] openSites;
    private double mean;
    private double dev;
    private double conLo;
    private double conHi;
   
    

    public PercolationStats(int n, int trials)
    {
        if(n<1 || trials<1)
            throw new IllegalArgumentException("Illigal Arguments");

            
            
            openSites=new int[trials];
            //StdStats       
            
        for (int i = 0; i < trials; i++)
        {
            per = new Percolation(n);
            while(!per.percolates())
            {                        
                per.open(StdRandom.uniform(1, n+1),StdRandom.uniform(1, n+1));          

            }
            openSites[i]=per.numberOfOpenSites();
           // StdOut.println("percolate" + "sitios abiertos "+ per.numberOfOpenSites()+ " de "+ n*n);
        }
        
        dev =StdStats.stddev(openSites)/(n*n);
        mean = StdStats.mean(openSites)/(n*n);
        conHi = mean+1.96*(dev/Math.sqrt(trials));
        conLo = mean-1.96*(dev/Math.sqrt(trials));    
    }

    public double mean()
    {
        return mean;
    }

    public double stddev()
    {
        return dev;
    }
        // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return conLo;

    }

        // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return conHi;

    }
    public static void main(String[] args)
    {
        PercolationStats stats = new PercolationStats(200,100);

        StdOut.println("\nMedia " + stats.mean() + " Desviacion "+ stats.stddev()+"\n");
        StdOut.println("Alto "+ stats.confidenceHi() + " Bajo "+ stats.confidenceLo()+"\n\n");


    }
            
}
