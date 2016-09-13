import java.io.*;
import java.util.*;

class loader3
{
 
  public static void main(String args[]) throws IOException
  {
    HashMap<String,String> hm=new HashMap<String,String>();
    try
    {
    String line="!",startaddr="",value="",startPrint="";
    String s="";
    int k=0;
    int min[]=new int[10];
    int max[]=new int[10];
    int hexInt=0,hexIntend=0;
    
    BufferedReader br=new BufferedReader(new FileReader("ssinput.txt"));
    while(line.charAt(0)!='E')
    {
      s="";
      line=br.readLine();
    if(line.charAt(0)!='T')
      
      continue;
    else           //to consider only the text records
    {
    String val[]=line.split("@");
    int i2=val.length;
    for(int i1=1;i1<i2;i1++)                   //takes out each record between '@'
    {
        value=val[i1];
        if(i1==1)                             //starting address part
        {
          startaddr=value;
         
          k++;
       
          startaddr="0x"+startaddr;
          hexInt = Integer.decode(startaddr);
          min[k-1]=hexInt;
        
        }
      else if(i1==2)                         //length part in txt record is skipped
      { continue;
      }
      else                                  //Each record is appended to a string
      {
        s=s+value; //System.out.println(s);
      }
    }
   
    int size=s.length();
    for(int i=0;i<size;i+=2)              //Each byte(2 digits)is taken from string and is mapped to their corresponding hexadecimal value in a Hashmap
    {
      
      startaddr=Integer.toHexString(hexInt);
  
       if(hm.containsValue(startaddr))
           hm.remove(startaddr);
      hm.put(startaddr,s.substring(i,i+2));
      hexInt++;

    }
    startaddr="0x"+startaddr;
    hexIntend = Integer.decode(startaddr);
       max[k-1]=hexIntend;     
  
//notes the last mapped value
    
    }
    }
                               
   /*
     Iterator<String> i = hm.keySet().iterator();
      while(i.hasNext()) {
         String key = i.next();
      System.out.print(key+ ": ");
         System.out.println(hm.get(key));
      }*/
      System.out.println("Success");
      
      
      File file = new File("ssoutput.txt");
   FileWriter fw = new FileWriter(file.getAbsoluteFile());
   BufferedWriter bw = new BufferedWriter(fw);
      int byt=0;
      for(int i3=0;i3<k;i3++)
      {
   
        byt=0;
       System.out.println();
        bw.write("\r\n");
      for(int i4=min[i3];i4<max[i3];i4++)
        {
        //write to file
        startPrint=Integer.toHexString(i4);
        if(byt<=3)
        {  byt++;
          System.out.print(startPrint+":"+hm.get(startPrint)+"\t");
          bw.write(startPrint+":"+hm.get(startPrint)+"\t");
        }
        else
        {byt=0;System.out.println(startPrint+":"+hm.get(startPrint)+"\t");bw.write(startPrint+":"+hm.get(startPrint)+"\t");bw.write("\r\n");

      }
      }
      }
      
  bw.close();
  }
  catch(Exception e)
  {System.out.println(e);
  }
  }
}