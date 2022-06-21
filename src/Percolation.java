import java.sql.Struct;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{  
    private Nodo[][] grid;
    private int openSites;
    private boolean percolate;
    private int rowsNCol;
    WeightedQuickUnionUF union; 

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        rowsNCol = n;
        if(n<1)
        throw new IndexOutOfBoundsException("number of n must be greater than 0");
        
        grid=new Nodo[n][n];
        openSites = 0;

        if (n==1) 
        {
                grid[0][0].name=0;
                grid[0][0].open=false;
                grid[0][0].full=false;
                grid[0][0].top=true;
                grid[0][0].bottom=true;
                return;            
        }
        
        int counter = 0;

        for(int j=0; j<n; j++) //This is only for the first row of the matrix.
        {            
            grid[0][j].name=counter;
            grid[0][j].open=false;
            grid[0][j].full=false;
            grid[0][j].top=true;
            grid[0][j].bottom=false;
            counter++;
        }

        for (int i = 1; i < n-1; i++) // This is for the body of the matrix.
        {
            for (int j = 1; j < n; j++)
            {
                grid[i][j].name=counter;
                grid[i][j].open=false;
                grid[i][j].full=false;
                grid[i][j].top=false;
                grid[i][j].bottom=false;
                counter++;                
            }
            
        }
        for (int j = 0; j < n; j++)
        {
            grid[0][j].name=counter;
            grid[0][j].open=false;
            grid[0][j].full=false;
            grid[0][j].top=false;
            grid[0][j].bottom=true;
            counter++;
        }
        union = new WeightedQuickUnionUF(n*n+1);// Its because it's necessary to have one more space
                                                // to know when the system percolates, representing the top of the system.
        
    }   
    // // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
       checkRowsAndCol(row, col);
       
        //TODO: cuando se abra un nodo hay que unirlo a algun conjunto si es que es posible.
       
       row-=1;
       col-=1;

       if(isOpen(row, col))
            return;
        
        if(grid[row][col].top==true)
        {
            grid[row][col].open=true;
            grid[row][col].full=true;
            connectNeighbor(row,col);
            // TODO: al nodo raiz cambiarle el valor de full.
            union.find(grid[row][col].name);
        }
                   
            grid[row][col].open = true;
            openSites++;
       
    }
    
    // // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        checkRowsAndCol(row, col);
        if(grid[row][col].open==true)
        return true;

        return false;
        
    }
    
    // // is the site (row, col) full?
    // public boolean isFull(int row, int col)
    
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }
    
    // // does the system percolate?
    // public boolean percolates()
    
    // // // test client (optional)
    // // public static void main(String[] args)

    private void checkRowsAndCol(int row, int col)
    {
        if (row<1) 
            throw new IndexOutOfBoundsException("The number of rows must be greater than 0");
        if(col<1)
            throw new IndexOutOfBoundsException("The number of columns must be greater than 0");
    }

    private void connectNeighbor(int row, int col)
    {
        //top
        if( (row-1>=0) && (isOpen(row-1, col)))
            union.union(grid[row][col].name, grid[row-1][col].name);
        //bottom
        if((row+1<=rowsNCol) && (isOpen(row+1,col)))
            union.union(grid[row][col].name, grid[row+1][col].name);
        //left
        if((col-1>=0) && (isOpen(row, col-1)))
            union.union(grid[row][col].name, grid[row][col-1].name);
        //right
        if((col+1<=rowsNCol) && (isOpen(row, col+1)))
            union.union(grid[row][col].name, grid[row][col-1].name);
        
    }
    
    private class Nodo
    {
        public int     name;
        public boolean open;
        public boolean full;
        public boolean top;
        public boolean bottom;
    
    };
       
    
}
