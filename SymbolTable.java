import java.io.*;
import java.util.*;

public class SymbolTable
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("The assignment of values for elements wont work for arrays but not the decleration \n the when initilization and decleration is done in the same link as int a = 5....\nEnter the block\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		ArrayList<String> var = new ArrayList<String>();
		ArrayList<String> val = new ArrayList<String>();
		ArrayList<String> type = new ArrayList<String>();
		ArrayList<Long> addr = new ArrayList<Long>();
		ArrayList<Long> size = new ArrayList<Long>();

		long address = 1000000;
		String str, first, last, vars[];

		while(!(str = br.readLine()).equals("$"))
		{
			int sPos;
			if((sPos = str.indexOf(" ")) != -1 && getSize(str.substring(0, sPos)) > 0)
			{
				first = str.substring(0, str.indexOf(" "));
				last = str.substring(str.indexOf(" "));
				last = last.replaceAll(" ", "");
				vars = last.split(",");
				int varSize = getSize(first);
			
				for(String v : vars)
				{
					int pos, disp = 1;
					String tfirst = first;
					if((pos = v.indexOf('[')) != -1)
					{
						int pos2 = v.indexOf(']', pos);
						disp = Integer.parseInt(v.substring(pos+1, pos2));
						tfirst += "[]";
					}
					var.add(v);
					type.add(tfirst);
					size.add((long)varSize*disp);
					addr.add(address);
					address += varSize*disp;
					val.add("");
				}
			}
			else
			{
				str = str.replaceAll(" ", "");
				String[] assingn = str.split("=");
				int len = assingn.length;
				for(int i=0; i<len-1; i++)
					val.set(var.indexOf(assingn[i]),assingn[len-1]);
			}
		}

		int len = var.size();
		System.out.println("Variable\tData Type  Address\tSize  Value");
		for(int i=0; i<len; i++)
			System.out.println(var.get(i) + "\t\t" + type.get(i) + "\t   " + addr.get(i) + "\t" + size.get(i) + "\t" + val.get(i));
	}

	static int getSize(String first)
	{
		if(first.equals("byte") || first.equals("char"))
			return 2;
		if(first.equals("int") || first.equals("float"))
			return 8;
		if(first.equals("long") || first.equals("double"))
			return 16;
		return 0;
	}
}

/*int a, b
char c[20]
a = b= 5
$
Variable	Data Type  Address	Size  Value
a		int	   1000000	8	5
b		int	   1000008	8	5
c[20]		char[]	   1000016	40	
*/