package com.calc.test.framework.helpers;

import com.calc.test.framework.hooks.ScenarioHook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

/**
 * Created by Roshan
 */
public class ExcelHelper {

    public static String ReadExcelData(String sheetName, String ParmName) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/TestData.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet worksheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = worksheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0).getStringCellValue().equalsIgnoreCase(ParmName)) {
                    if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        return String.valueOf(row.getCell(1).getNumericCellValue());
                    } else {
                        return row.getCell(1).getStringCellValue();
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Failed to retrieve value from test data xlsx \n" + ex.getMessage());
        } finally {
            fileInputStream.close();
        }
        return null;
    }

    public static String ReadExcelData(String testDataFile , String  sheetName , String ParmName) throws Exception {
     FileInputStream fileInputStream = null;
      try {
          fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/"+testDataFile+".xlsx");
          XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
          XSSFSheet worksheet = workbook.getSheet(sheetName);
          Iterator<Row> rowIterator = worksheet.iterator();
          while (rowIterator.hasNext()) {
              Row row = rowIterator.next();
              if (row.getCell(0).getStringCellValue().equalsIgnoreCase(ParmName)) {
                  if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                      return String.valueOf(row.getCell(1).getNumericCellValue());
                  } else {
                      return row.getCell(1).getStringCellValue();
                  }
              }
          }
      } catch (Exception ex) {
          throw new Exception("Failed to retrieve value from test data xlsx \n" + ex.getMessage());
      } finally {
          fileInputStream.close();
      }
      return null;
  }

    /**
     * Author - Rakesh
     * To fetch the multiple values from excel
     *
     * @return String array
     * @throws ParseException
     * @throws IOException
     * @throws FileNotFoundException
     * @SheetName Name of the excel sheet
     * @ParmName parameter name in the excel sheet in single quote seperated by comma
     */
    public static String[] ReadExcelValues(String sheetName, String parmName) throws Exception {
//        java.sql.Connection Conn = null;
//        int arrLength = 1;
//        String[] parmCount = ParmName.split(",");
//        if(parmCount.length > 1){
//            arrLength = parmCount.length;
//        }
//        String[] Value = new String[arrLength];
//          try {
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//            Conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ=src\\test\\resources\\TestData.xlsx");
//            java.sql.Statement stmt = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//            String query = "SELECT * FROM [" + SheetName + "$] WHERE Parameter IN (" + ParmName + ");";
//            ResultSet rs = stmt.executeQuery(query);
//            int iterator = 0;
//            while (rs.next()) {
//                Value[iterator] = rs.getString(2);
//                iterator = iterator+1;
//            }
//        } catch (Exception ex) {
//            System.err.println(e);
//            return Value;
//        } finally {
//            try {
//                Conn.close();
//            } catch (Exception ex) {
//                System.err.println(e);
//            }
//        }
//        return Value;
        int arrLength = 1;
        String[] parmFields = parmName.split(",");
        if (parmFields.length > 1) {
            arrLength = parmFields.length;
        }
        String[] value = new String[arrLength];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/TestData.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet worksheet = workbook.getSheet(sheetName);
            for (int i = 0; i < arrLength; i++) {
                Iterator<Row> rowIterator = worksheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getCell(0).getStringCellValue().equalsIgnoreCase(parmFields[i].replaceAll("'", "").trim())) {
                        if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            value[i] = String.valueOf(row.getCell(1).getNumericCellValue());
                        } else {
                            value[i] = row.getCell(1).getStringCellValue();
                        }
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Failed to retrieve value from test data xlsx \n" + ex.getMessage());
        } finally {
            fileInputStream.close();
        }
        return value;
    }

    /**
     * Author - Rakesh
     * To Verify the Excel Value
     *
     * @param strfilePath, value(s) to be checked in the excel file seperated by '|' sysmbol
     * @return boolean
     */
    public static boolean VerifyExcelValue(String strfilePath, String strExpectedVal) {
        boolean Found = false;
        try {
            InputStream input = new FileInputStream(strfilePath);
            HSSFWorkbook XLWB = new HSSFWorkbook(input);
            HSSFSheet XLSheet = XLWB.getSheetAt(0);
            int iterator;
            String[] ExpVal = strExpectedVal.split("\\|");
            for (iterator = 0; iterator < ExpVal.length; iterator++) {
                ScenarioHook.getScenario().write("Verifying the Value : " + ExpVal[iterator] + "<br/>");
                Found = false;
                for (Row row1 : XLSheet) {
                    for (Cell cell1 : row1) {
                        if (cell1.getCellType() == Cell.CELL_TYPE_STRING) {
                            if (cell1.getRichStringCellValue().getString().trim().equals(ExpVal[iterator])) {
                                Found = true;
                                break;
                            }
                        }
                    }
                }
                if (!Found) {
                    ScenarioHook.getScenario().write("Error : Value NOT found in the Excel file : " + ExpVal[iterator] + "<br/>");
                    break;
                }
            }
        } catch (Exception ex) {
            Found = false;
        }
        return Found;
    }

    public static int GetExcelParmCount(String sheetName) throws Exception {
        FileInputStream fileInputStream = null;
        int count = 0;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/TestData.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet worksheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = worksheet.iterator();
            while (rowIterator.hasNext()) {
                rowIterator.next();
                count++;
            }
        } catch (Exception ex) {
            throw new Exception("Failed to retrieve value from test data xlsx \n" + ex.getMessage());
        } finally {
            fileInputStream.close();
        }
        return count;
    }



    public static String ReadExcelDataDownloaded(File file, String sheetName, String parmName) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
            HSSFSheet worksheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = worksheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0).getStringCellValue().equalsIgnoreCase(parmName)) {
                    if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        return String.valueOf(row.getCell(1).getNumericCellValue());
                    } else {
                        return row.getCell(1).getStringCellValue();
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception("Failed to retrieve value from file name :  " + file.getName() +" due to : "+ex.getMessage());
        } finally {
            fileInputStream.close();
        }
        return null;
    }


}
