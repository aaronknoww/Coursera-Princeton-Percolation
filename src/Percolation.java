import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{  
    //TODO: RESOLVER EL PROBLEMA DE QUE SE RESTA -1 A CADA COLUMNA Y FILA.
    private Nodo grid[][];
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
            grid[0][0]=new Nodo();
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
            grid[0][j]=new Nodo();         
            grid[0][j].name=counter;
            grid[0][j].open=false;
            grid[0][j].full=false;
            grid[0][j].top=true;
            grid[0][j].bottom=false;
            counter++;
        }

        for (int i = 1; i < n-1; i++) // This is for the body of the matrix.
        {
            for (int j = 0; j < n; j++)
            {
                grid[i][j]=new Nodo();
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
            grid[n-1][j]=new Nodo();
            grid[n-1][j].name=counter;
            grid[n-1][j].open=false;
            grid[n-1][j].full=false;
            grid[n-1][j].top=false;
            grid[n-1][j].bottom=true;
            counter++;
        }
        union = new WeightedQuickUnionUF(n*n);
    }   
    // // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
       checkRowsAndCol(row, col);                
       
       if(isOpen(row, col)) 
            return;
        
        // Open and union this node.    
        if(grid[row-1][col-1].top==true)
        {
            grid[row-1][col-1].open=true;
            grid[row-1][col-1].full=true;
            connectNeighbor(row-1,col-1);
            Nodo root = findNodo(union.find(grid[row-1][col-1].name));
            root.full=true;
            openSites++;
            return;
        }

        grid[row-1][col-1].open=true;
        connectNeighbor(row-1,col-1);
        Nodo root = findNodo(union.find(grid[row-1][col-1].name));
        if(root.full==true)
            grid[row-1][col-1].full=true;
        openSites++;
        if(grid[row-1][col-1].full==true && grid[row-1][col-1].bottom==true)
            percolate=true;
        return;
       
    }
    
    // // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        checkRowsAndCol(row, col);
        if(grid[row-1][col-1].open==true)
        return true;

        return false;
        
    }
    
    // // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        checkRowsAndCol(row, col);
     
        if(!isOpen(row, col)) // If the node its close.
            return false;

        if(grid[row-1][col-1].full==true)
            return true;
        else
        {
            Nodo root = findNodo(union.find(grid[row-1][col-1].name));
            if(root.full==true)
            {
                grid[row-1][col-1].full=true;
                percolate=(grid[row-1][col-1].bottom==true)?true:false;
                return true;
            }
            return false;
        }
    }
    
    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }
    
    // // does the system percolate?
    public boolean percolates()
    {
        if(percolate==true)
            return true;
        else
        {
            for (int j = 1; j <= rowsNCol; j++)
            {
              if(isFull(rowsNCol, j))
                return true;
            }
            return false;
        }
    }
    
    // // // test client (optional)
    // // public static void main(String[] args)

    ////////////////////////////////////////////////////////////////////////
    //                      PRIVATE FUNCIONS                             //
    ////////////////////////////////////////////////////////////////////// 

    private void checkRowsAndCol(int row, int col)
    {
        if (row<1) 
            throw new IndexOutOfBoundsException("The number of rows must be greater than 0");
        if(col<1)
            throw new IndexOutOfBoundsException("The number of columns must be greater than 0");
    }

    private void connectNeighbor(int row, int col)
    {
        //It has a neighbor on the top.
        if( (row-1>=0) && (isOpen(row-1+1, col+1)))
            union.union(grid[row][col].name, grid[row-1][col].name);
        //It has a neighbor at the bottom.
        if((row+1<rowsNCol) && (isOpen(row+1+1,col+1)))
            union.union(grid[row][col].name, grid[row+1][col].name);
        //It has a neighbor at the left
        if((col-1>=0) && (isOpen(row+1, col-1+1)))
            union.union(grid[row][col].name, grid[row][col-1].name);
        //It has a neighbor at the right
        if((col+1<rowsNCol) && (isOpen(row+1, col+1+1)))
            union.union(grid[row][col].name, grid[row][col+1].name);
        
    }
    private Nodo findNodo(int clave)
    {
        int fila = clave/rowsNCol;
        int columna = clave-(fila*rowsNCol);
        return grid[fila][columna];

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
