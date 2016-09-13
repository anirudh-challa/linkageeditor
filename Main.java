import java.io.*;
import java.util.*;

public class Main
{
	public static String split = "\\^";
	static LinkedList<ExtSymbolTable> symList = new LinkedList<ExtSymbolTable>();
	static LinkedList<RoadMap> mapList = new LinkedList<RoadMap>();
	static String fileName[] = {"input.txt"};
	static BufferedReader br;
	static BufferedWriter out;
	
	public static void main(String args[]) throws Exception
	{
		firstPass();
		ExtSymbolTable.printSymbolTable(symList);
		
		secondPass();
		RoadMap.printRoadMap(mapList);
	}
	
	static void firstPass() throws Exception
	{
		ExtSymbolTable temp;
		int i, len;
		
		for(String fn : fileName)
		{
			br = new BufferedReader(new FileReader(fn));
			String header[] = br.readLine().split(split);
			
			temp = new ExtSymbolTable();
			temp.name = header[1];
			temp.addr = header[2];
			temp.length = header[3];
			
			ArrayList<String> symName = new ArrayList<String>();
			ArrayList<String> symAddr = new ArrayList<String>();
			String sTemp = br.readLine(), define[];

			while(sTemp != null && sTemp.charAt(0) == 'D')
			{
				define = sTemp.split(split);
				len = define.length;
				i = 1;
				while(i<len)
				{
					symName.add(define[i++]);
					symAddr.add(define[i++]);
				}
				
				len = symName.size();
				temp.symName = new String[len];
				temp.symAddr = new String[len];
				for(i=0; i<len; i++)
				{
					temp.symName[i] = symName.get(i);
					temp.symAddr[i] = symAddr.get(i);
				}
				
				sTemp = br.readLine();
			}
			symList.add(temp);
			br.close();
		}
	}
	
	static void secondPass() throws Exception
	{
		RoadMap temp;
		for(String fn : fileName)
		{
			br = new BufferedReader(new FileReader(fn));
			out = new BufferedWriter(new FileWriter(fn + "map"));
			
			String sTemp = br.readLine();
			while(sTemp.charAt(0) == 'D' || sTemp.charAt(0) == 'H')
				sTemp = br.readLine();
			
			while(sTemp != null && sTemp.charAt(0) == 'T')
			{
				temp = new RoadMap();
				String text[] = sTemp.split(split);
				temp.addr = text[1];
				temp.len = text[2];

				temp.data = "";
				int len = text.length;
				for(int i=3; i<len; i++)
					temp.data += text[i];
				
				sTemp = br.readLine();
				mapList.add(temp);
			}
		}
	}
}

class RoadMap
{
	String addr, len;
	String data;
	
	static void printRoadMap(LinkedList<RoadMap> mapList) throws Exception
	{
		System.out.println("Length : " + mapList.size());
		for(RoadMap temp : mapList)
			System.out.println(temp.addr + "\t" + temp.len + "\t" + temp.data);
	}
}

class ExtSymbolTable
{
	String name, addr, length;
	String symName[], symAddr[];
	
	static void printSymbolTable(LinkedList<ExtSymbolTable> symList) throws Exception
	{
		BufferedWriter br = new BufferedWriter(new FileWriter("extSymTable.txt"));
		for(ExtSymbolTable temp : symList)
		{
			br.write("*" + temp.name + " " + temp.length + " " + temp.addr);
			br.newLine();
			int len = temp.symName.length;
			for(int j=0; j<len; j++)
			{
				br.write(temp.symName[j] + " " + temp.symAddr[j]);
				br.newLine();
			}
		}
		br.close();
	}
}