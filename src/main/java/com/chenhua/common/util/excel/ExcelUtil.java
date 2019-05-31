package com.chenhua.common.util.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.chenhua.common.util.Reflections;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用来处理Excel
 *
 * @author chp
 */
public class ExcelUtil {
  public static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

  /**
   * 写excel文件
   *
   * @param list
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  public static HSSFWorkbook writeToExcel(List<? extends Object> list)
      throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    if (CollectionUtils.isEmpty(list)) {
      throw new NullPointerException("数据不能为空");
    }
    Class<? extends Object> clazz = list.get(0).getClass();
    ExcelSheet[] sheetAnons = clazz.getDeclaredAnnotationsByType(ExcelSheet.class);
    String sheetName = "sheet 1";
    if (sheetAnons != null && sheetAnons.length > 0) {
      sheetName = sheetAnons[0].title();
    }
    List<ExcelCellModel> allExcelCellList = getAllExcelCell(clazz);

    if (allExcelCellList == null || allExcelCellList.size() == 0) {
      throw new NullPointerException("未能找到excel 模型数据");
    }

    HSSFWorkbook workbook = new HSSFWorkbook();
    // 创建表头
    String[] titles = new String[allExcelCellList.size()];
    Integer[] widths = new Integer[allExcelCellList.size()];
    for (int i = 0; i < allExcelCellList.size(); i++) {
      titles[i] = allExcelCellList.get(i).getTitle();
      widths[i] = allExcelCellList.get(i).getWidth();
    }
    HSSFSheet sheet = ExcelUtil.createSheet(workbook, sheetName, titles, widths);
    // 创建数据
    for (int i = 0; i < list.size(); i++) {
      HSSFRow row = sheet.createRow(i + 1);
      for (int j = 0; j < allExcelCellList.size(); j++) {
        HSSFCell cell = row.createCell(j);
        AccessibleObject type = allExcelCellList.get(j).getType();
        String format = allExcelCellList.get(j).getFormat();
        if (type instanceof Field) {
          Object object = ((Field) type).get(list.get(i));
          if (object instanceof String) {
            cell.setCellValue((String) object);
          } else if (object instanceof Date) {

            if (StringUtils.isNotBlank(format)) {
              cell.setCellValue(DateUtil.format((Date) object, format));
            } else {
              cell.setCellValue(DateUtil.format((Date) object, "yyyy-MM-dd HH:mm:ss"));
            }

          } else if (object instanceof Number) {
            if (StringUtils.isNotBlank(format)) {
              cell.setCellValue(NumberUtil.decimalFormat(format, ((Number) object).doubleValue()));
            } else {
              cell.setCellValue(((Number) object).doubleValue());
            }
          }
        } else if (type instanceof java.lang.reflect.Method) {
          Object object = ((java.lang.reflect.Method) type).invoke(list.get(i));
          if (object instanceof String) {
            cell.setCellValue((String) object);
          } else if (object instanceof Date) {

            if (StringUtils.isNotBlank(format)) {
              cell.setCellValue(DateUtil.format((Date) object, format));
            } else {
              cell.setCellValue(DateUtil.format((Date) object, "yyyy-MM-dd HH:mm:ss"));
            }
          } else if (object instanceof Number) {

            if (StringUtils.isNotBlank(format)) {
              cell.setCellValue(NumberUtil.decimalFormat(format, ((Number) object).doubleValue()));
            } else {
              cell.setCellValue(((Number) object).doubleValue());
            }
          }
        }
      }
    }
    return workbook;
  }

  /**
   * 用于保存excel单元数据
   *
   * @author chenhuaping
   * @date 2018年6月20日 下午4:59:19
   */
  private static class ExcelCellModel {

    private int sort;

    private String title;

    private int width;

    private String format;

    private AccessibleObject type;

    public int getSort() {
      return sort;
    }

    public void setSort(int sort) {
      this.sort = sort;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public AccessibleObject getType() {
      return type;
    }

    public void setType(AccessibleObject type) {
      this.type = type;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
      this.format = format;
    }
  }

  /**
   * 获取所有的的属性
   *
   * @return
   */
  public static List<ExcelCellModel> getAllExcelCell(Class<?> clz) {

    List<ExcelCellModel> list = new ArrayList<>();

    for (Class<?> superClass = clz;
        superClass != Object.class;
        superClass = superClass.getSuperclass()) {
      // 获取属性
      Field[] fields = superClass.getDeclaredFields();
      for (Field f : fields) {
        ExcelCell cxcelCell = f.getDeclaredAnnotation(ExcelCell.class);
        if (cxcelCell != null) {
          Reflections.makeAccessible(f);
          ExcelCellModel model = new ExcelCellModel();
          model.setSort(cxcelCell.sort());
          model.setTitle(cxcelCell.title());
          model.setType(f);
          model.setWidth(cxcelCell.width());
          model.setFormat(cxcelCell.format());
          list.add(model);
        }
      }

      java.lang.reflect.Method[] methods = superClass.getDeclaredMethods();
      for (java.lang.reflect.Method method : methods) {
        ExcelCell cxcelCell = method.getDeclaredAnnotation(ExcelCell.class);
        if (cxcelCell != null) {
          Reflections.makeAccessible(method);
          ExcelCellModel model = new ExcelCellModel();
          model.setSort(cxcelCell.sort());
          model.setTitle(cxcelCell.title());
          model.setType(method);
          model.setWidth(cxcelCell.width());
          model.setFormat(cxcelCell.format());
          list.add(model);
        }
      }
    }

    Collections.sort(
        list,
        new Comparator<ExcelCellModel>() {

          @Override
          public int compare(ExcelCellModel o1, ExcelCellModel o2) {
            if (o1.getSort() == o2.getSort()) {
              return 0;
            } else if (o1.getSort() > o2.getSort()) {
              return 1;
            } else {
              return -1;
            }
          }
        });

    return list;
  }

  /**
   * Excel的具体参数
   *
   * @param workbook 工作表
   * @param sheetName 表名
   * @param titles 各栏目名称
   * @param widths 栏目宽度
   * @return
   */
  public static HSSFSheet createSheet(
      HSSFWorkbook workbook, String sheetName, String[] titles, Integer[] widths) {
    if (workbook == null
        || titles == null
        || titles.length == 0
        || widths == null
        || widths.length == 0) {
      return null;
    }
    if (StringUtils.isBlank(sheetName)) {
      sheetName = "表单信息";
    }
    HSSFSheet sheet = workbook.createSheet(sheetName);
    HSSFRow row = sheet.createRow(0);
    int rowIndex = 0;
    // 设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
    for (int width : widths) {
      sheet.setColumnWidth(rowIndex++, width * 256);
    }
    // 设置为居中加粗
    HSSFCellStyle style = workbook.createCellStyle();
    HSSFFont font = workbook.createFont();
    font.setBold(true);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setFont(font);
    int index = 0;
    HSSFCell cell;
    for (String title : titles) {
      cell = row.createCell(index++);
      cell.setCellValue(title);
      cell.setCellStyle(style);
    }
    return sheet;
  }

  /**
   * @param fileName
   * @return
   * @throws Exception
   */
  public static Workbook getWorkbook(String fileName) throws Exception {
    File excel = new File(fileName);
    if (!excel.exists()) {
      throw new FileNotFoundException("文件未能找到");
    }

    String[] split = excel.getName().split("\\.");

    Workbook wb;
    FileInputStream fis = new FileInputStream(excel); // 文件流对象
    // 根据文件后缀（xls/xlsx）进行判断
    if ("xls".equals(split[1])) {
      wb = new HSSFWorkbook(fis);
    } else if ("xlsx".equals(split[1])) {
      wb = new XSSFWorkbook(fis);
    } else {
      throw new IOException("文件类型错误,只支持xls和xlsx");
    }
    return wb;
  }

  /**
   * 获取字符串
   *
   * @param cell
   * @return
   */
  public static String getString(Cell cell) {
    DataFormatter formatter = new DataFormatter();
    return formatter.formatCellValue(cell);
  }

  /**
   * 获取trim字符串
   *
   * @param cell
   * @return
   */
  public static String getTrimString(Cell cell) {
    String str = getString(cell);
    if (str != null) {
      return str.trim();
    }
    return str;
  }

  /**
   * 获取时间
   *
   * @param cell
   * @return
   */
  public static Date getDate(Cell cell) {
    // 1、判断是否是数值格式
    if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
      short format = cell.getCellStyle().getDataFormat();
      SimpleDateFormat sdf = null;
      if (format == 14 || format == 31 || format == 57 || format == 58) {
        // 日期
        sdf = new SimpleDateFormat("yyyy-MM-dd");
      } else if (format == 20 || format == 32) {
        // 时间
        sdf = new SimpleDateFormat("HH:mm");
      }
      double value = cell.getNumericCellValue();
      Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
      return date;
    }
    return null;
  }

  /**
   * 获取合并单元格的值
   *
   * @param sheet
   * @param row
   * @param column
   * @return
   */
  public Cell getMergedRegionValue(Sheet sheet, int row, int column) {
    int sheetMergeCount = sheet.getNumMergedRegions();

    for (int i = 0; i < sheetMergeCount; i++) {
      CellRangeAddress ca = sheet.getMergedRegion(i);
      int firstColumn = ca.getFirstColumn();
      int lastColumn = ca.getLastColumn();
      int firstRow = ca.getFirstRow();
      int lastRow = ca.getLastRow();

      if (row >= firstRow && row <= lastRow) {
        if (column >= firstColumn && column <= lastColumn) {
          Row fRow = sheet.getRow(firstRow);
          Cell fCell = fRow.getCell(firstColumn);
          return fCell;
        }
      }
    }

    return sheet.getRow(row).getCell(column);
  }
}
