package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    // DataProvider 1

    @DataProvider(name = "LoginData")
    public String[][] getData() throws IOException {

        // Taking Excel file from testData folder

        String path = System.getProperty("user.dir")
                + "\\testData\\Opencart_LoginDetails.xlsx";

        // Creating ExcelUtility object

        ExcelUtility xlutil = new ExcelUtility(path);

        int totalrows = xlutil.getRowCount("Sheet1");

        int totalcols = xlutil.getCellCount("Sheet1", 1);

        // Creating two-dimensional array

        String logindata[][] = new String[totalrows][totalcols];

        // Reading data from Excel

        for (int i = 1; i <= totalrows; i++) {

            for (int j = 0; j < totalcols; j++) {

                logindata[i - 1][j] =
                        xlutil.getCellData("Sheet1", i, j);
            }
        }

        return logindata;
    }
}

