package kx.examples;
import kx.c;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io. * ;

public class Feed{
  private static final Logger LOGGER = Logger.getLogger(Feed.class.getName());
  private static final String QFUNC = ".u.upd";
  private static final String TABLENAME = "trade";
  public static final String delimiter = ",";
  /**
   * Example of 10 single row inserts to a table
   * @param kconn Connection that will be sent the inserts
   * @throws java.io.IOException when issue with KDB+ comms
   */
  static void rowInserts(c kconn,String csvFile) throws java.io.IOException{
    // Single row insert - not as efficient as bulk insert

        LOGGER.log(Level.INFO,"Populating 'mytable' on kdb server with 10 rows...");
        File file = new File(csvFile);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = " ";
        String[] tempArr;
        boolean skip = true;
                while ((line = br.readLine()) != null) {
                        if(skip) {
                                skip = false;
                                continue;
                                }
        tempArr = line.split(delimiter);
        String sym = tempArr[1];
        Double price = Double.parseDouble(tempArr[2]);
        int size = Integer.parseInt(tempArr[3]);
        Object[] row=new Object[]{new c.Timespan(),sym,price,size};
        kconn.ks(QFUNC,TABLENAME,row);
        }
        br.close();

  }

  /**
   * Example of bulk inserts to a table to improve throughput
   * @param kconn Connection that will be sent the inserts
   * @throws java.io.IOException when issue with KDB+ comms
   * @throws c.KException if request evaluation resulted in an error
   */
  static void bulkInserts(c kconn) throws java.io.IOException, c.KException{
    // Bulk row insert - more efficient
    String[]syms=new String[]{"ABC","DEF","GHI","JKL"}; // symbols to randomly choose from
    // Allocate one array per column
    c.Timespan[] time=new c.Timespan[10];
    String[] sym=new String[10];
    double[] price=new double[10];
    long[] size=new long[10];
    SecureRandom random = new SecureRandom();
    // populate the arrays with sample data
    for(int i=0;i<10;i++){
      time[i]=new c.Timespan();
      sym[i]=syms[random.nextInt(syms.length)]; // choose a random symbol
      price[i]=i;
      size[i]=i*10L;
    }
    // Note that we don't need to supply a flip with columns names for .u.upd.
    // Just the column data in the correct order is sufficient.
    LOGGER.log(Level.INFO,"Populating 'mytable' with a 10 row bulk insert (without column names)...");
    kconn.ks(QFUNC,TABLENAME,new Object[]{time,sym,price,size});
    // if we did want to supply a flip, it can be done as
    LOGGER.log(Level.INFO,"Populating 'mytable' with a 10 row bulk insert (using Flip with column names)...");
    kconn.ks(QFUNC,TABLENAME,new c.Flip(new c.Dict(new String[]{"time","sym","price","size"},new Object[]{time,sym,price,size})));
     kconn.k(""); // sync chase ensures the remote has processed all msgs
  }

  public static void main(String[] args){// example tick feed
    c c=null;
    try{
      c=new c("localhost",5011);
        String csvFile = "trade.csv";
      rowInserts(c,csvFile);
          }
    catch(Exception e){
      LOGGER.log(Level.SEVERE,e.toString());
    }finally{
      if(c!=null)
        try{c.close();}catch(java.io.IOException e){
          // ingnore exception
        }
    }
  }
}
