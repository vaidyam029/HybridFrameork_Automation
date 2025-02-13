package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider 1
	
	@DataProvider (name = "LoginData")   //name always different according to data provider
	public String[][] getData() throws IOException
	{
		String path =".\\testData\\TestData.xlsx";  //taking xl file from testData
		
		ExcelUtility xlutil = new ExcelUtility(path);  ///creating an object for XLUtility
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1",1);
		
		String logindata[][] = new String [totalrows][totalcols]; //created for two dimension array which can stored
		
		for (int i=1; i<=totalrows;i++)   //1  //read the data from xl storing in two dimensional array
		{
			for(int j=0;j<totalcols;j++)
			{
				//here we i-1 bcoz array index start from zero and we store the fata in logindata
				logindata[i-1][j]= xlutil.getCellData("Sheet1", i, j);
			}
		}
		return logindata;   //returning two dimension array
		
		
	}
	
	//DataProvider 2
	
	

}
