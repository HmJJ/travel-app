package com.travel.utils.excel;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.travel.utils.StringUtils;
import com.travel.utils.time.DateUtils;

public class ExcelUtils<T> {
	protected static final Log logger = LogFactory.getLog(ExcelUtils.class);
	public static final String FILENAME_ENCODING_ISO88591 = "ISO-8859-1";
	public static final String FILENAME_ENCODING_UTF8 = "UTF-8";
	@SuppressWarnings("unused")
	private static final int RECORD_LIMIT = 60000;
	@SuppressWarnings("unused")
	private static final int TITLE_ROW = 0;
	public static final String RECORD_PREFIX = "new ";
	public static final String RECORD_SUFFIX = "()";

	@SuppressWarnings("rawtypes")
	public static synchronized <T> Workbook writeExcel(Workbook book, Map<String, String> title, List<T> datas,
			DefaultWriteExcelCallback<T> callback)
			throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (null != title && !title.isEmpty() && null != datas) {
			Set keys = title.keySet();
			int size = datas.size();
			int loop = (int) Math.ceil((double) size / 60000.0D);
			Sheet sheet = null;
			Row row = null;
			Cell cell = null;
			List data = null;
			if (datas.isEmpty()) {
				loop = 2;
			}

			for (int i = 1; i < loop + 1; ++i) {
				sheet = book.createSheet();
				row = sheet.createRow(0);
				int start = 0;

				for (Iterator end = title.entrySet().iterator(); end.hasNext(); ++start) {
					Entry j = (Entry) end.next();
					cell = row.createCell(start);
					sheet.setColumnWidth(start, 4040);
					if (callback != null) {
						callback.writeTitle(book, sheet, row, cell, (String) j.getValue());
					} else {
						cell.setCellValue((String) j.getValue());
					}
				}

				if (datas.isEmpty()) {
					break;
				}

				start = (i - 1) * '';
				int arg19 = i * '' > size ? size : i * '';
				data = datas.subList(start, arg19);

				for (int arg20 = 0; arg20 < data.size(); ++arg20) {
					Object entity = data.get(arg20);
					row = sheet.createRow(arg20 + 1);
					Iterator iterator = keys.iterator();

					for (int k = 0; iterator.hasNext(); ++k) {
						String key = (String) iterator.next();
						cell = row.createCell(k);
						Object value = PropertyUtils.getProperty(entity, key);
						if (callback != null) {
							callback.writeData(book, sheet, row, cell, key, value);
						} else {
							setCellValue(cell, value);
						}
					}
				}
			}

			return book;
		} else {
			return book;
		}
	}

	public static <T> Workbook writeExcel(Map<String, String> title, List<T> datas, DefaultExportCallback<T> callback) {
      try {
         XSSFWorkbook e = new XSSFWorkbook();
         return writeExcel(e, title, datas, new DefaultWriteExcelCallback<T>() {

			@Override
			public void writeTitle(Workbook arg0, Sheet arg1, Row arg2, Cell arg3, String arg4) {
				
			}

			@Override
			public void writeData(Workbook arg0, Sheet arg1, Row arg2, Cell arg3, String arg4, Object arg5) {
				
			}
		});
      } catch (Exception arg3) {
         logger.error("Write Excel Exception:", arg3);
         return null;
      }
   }

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public static <T> List<T> readExcel(int index, InputStream stream, String suffix, Class<T> clazz,
			Map<Integer, String> map, DefaultReadExcelCallback<T> callback) throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
		LinkedList retval = new LinkedList();
		Object book = null;

		try {
			if (suffix.equals("xls")) {
				book = new HSSFWorkbook(stream);
			} else {
				book = new XSSFWorkbook(stream);
			}
		} catch (IOException arg18) {
			logger.error("Read Excel Exception:", arg18);
			throw arg18;
		} finally {
			if (stream != null) {
				stream.close();
			}

		}

		for (int i = 0; i < ((Workbook) book).getNumberOfSheets(); ++i) {
			Sheet sheet = ((Workbook) book).getSheetAt(i);
			int j;
			Row row;
			Object object;
			if (index > -1 && callback != null) {
				for (j = 0; j < index; ++j) {
					row = sheet.getRow(j);
					object = callback.readTitle(j, clazz, map, row, sheet, (Workbook) book);
					if (object != null) {
						retval.add(object);
					}
				}
			}

			for (j = index < 0 ? sheet.getFirstRowNum() : index; j < sheet.getLastRowNum() + 1; ++j) {
				row = sheet.getRow(j);
				if (null != row) {
					if (callback != null) {
						retval.add(callback.readData(clazz, map, row, sheet, (Workbook) book));
					} else {
						object = Class.forName(clazz.getName()).newInstance();

						for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); ++k) {
							Cell cell = row.getCell(k);
							if (null != cell && map.get(Integer.valueOf(k)) != null && 3 != cell.getCellType()) {
								if (0 == cell.getCellType() && HSSFDateUtil.isCellDateFormatted(cell)) {
									PropertyUtils.setProperty(object, (String) map.get(Integer.valueOf(k)),
											cell.getDateCellValue());
								} else {
									try {
										cell.setCellType(1);
										PropertyUtils.setProperty(object, (String) map.get(Integer.valueOf(k)),
												StringUtils.trim(cell.getStringCellValue()));
									} catch (Exception arg19) {
										logger.error("Read Excel Exception: " + j + " | " + cell.getStringCellValue());
										arg19.printStackTrace();
									}
								}
							}
						}

						retval.add(object);
					}
				}
			}
		}

		return retval;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> readExcel(int index, File file, Class<T> clazz, Map<Integer, String> map,
			DefaultReadExcelCallback<T> callback) throws IOException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException, ClassNotFoundException {
		if (null != file && file.exists()) {
			if (null == clazz) {
				return null;
			} else {
				LinkedList list = new LinkedList();
				Object book = null;
				FileInputStream stream = null;

				try {
					String i = file.getName();
					int sheet = i.indexOf(".");
					String j = "";
					if (sheet != -1) {
						j = i.substring(sheet + 1);
					}

					stream = new FileInputStream(file);
					if (j.equals("xls")) {
						book = new HSSFWorkbook(stream);
					} else {
						book = new XSSFWorkbook(stream);
					}
				} catch (IOException arg17) {
					logger.error("Read Excel Exception:", arg17);
					throw arg17;
				} finally {
					if (stream != null) {
						stream.close();
					}

				}

				if (book != null) {
					for (int arg19 = 0; arg19 < ((Workbook) book).getNumberOfSheets(); ++arg19) {
						Sheet arg20 = ((Workbook) book).getSheetAt(arg19);
						Row row;
						Object object;
						int arg21;
						if (index > -1 && callback != null) {
							for (arg21 = 0; arg21 < index; ++arg21) {
								row = arg20.getRow(arg21);
								object = callback.readTitle(arg21, clazz, map, row, arg20, (Workbook) book);
								if (object != null) {
									list.add(object);
								}
							}
						}

						for (arg21 = index < 0 ? arg20.getFirstRowNum() : index; arg21 < arg20.getLastRowNum()
								+ 1; ++arg21) {
							row = arg20.getRow(arg21);
							if (callback != null) {
								list.add(callback.readData(clazz, map, row, arg20, (Workbook) book));
							} else {
								object = Class.forName(clazz.getName()).newInstance();

								for (int k = row.getFirstCellNum(); k < row.getLastCellNum(); ++k) {
									Cell cell = row.getCell(k);
									Object data = null;
									if (cell.getStringCellValue() != null) {
										data = cell.getStringCellValue();
									} else if (cell.getDateCellValue() != null) {
										data = cell.getDateCellValue();
									} else {
										data = Double.valueOf(cell.getNumericCellValue());
									}

									PropertyUtils.setProperty(object, (String) map.get(Integer.valueOf(k)), data);
								}

								list.add(object);
							}
						}
					}
				}

				return list;
			}
		} else {
			return null;
		}
	}

	public static final ByteArrayInputStream outExcelInputStream(Workbook workbook) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		workbook.write(stream);
		return new ByteArrayInputStream(stream.toByteArray());
	}

	public static final ByteArrayInputStream outExcelInputStream(File file) throws IOException {
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
		byte[] b = new byte[stream.available()];
		stream.read(b);
		stream.close();
		return new ByteArrayInputStream(b);
	}

	private static void setCellValue(Cell cell, Object value) {
		if (null == value) {
			cell.setCellValue("");
		} else if (value.getClass().isAssignableFrom(Boolean.TYPE)) {
			cell.setCellValue(((Boolean) value).booleanValue());
		} else if (value instanceof Boolean) {
			cell.setCellValue(((Boolean) value).booleanValue());
		} else if (value instanceof BigDecimal) {
			cell.setCellValue(((BigDecimal) value).doubleValue());
		} else if (value.getClass().isAssignableFrom(Double.TYPE)) {
			cell.setCellValue(((Double) value).doubleValue());
		} else if (value instanceof Double) {
			cell.setCellValue(((Double) value).doubleValue());
		} else if (value.getClass().isAssignableFrom(Long.TYPE)) {
			cell.setCellValue((double) ((Long) value).longValue());
		} else if (value instanceof Long) {
			cell.setCellValue((double) ((Long) value).longValue());
		} else if (value.getClass().isAssignableFrom(Integer.TYPE)) {
			cell.setCellValue((double) ((Integer) value).intValue());
		} else if (value instanceof Integer) {
			cell.setCellValue((double) ((Integer) value).intValue());
		} else if (value instanceof Date) {
			cell.setCellValue(DateUtils.format((Date) value, "yyyy-MM-dd HH:mm:ss"));
		} else if (value instanceof java.sql.Date) {
			cell.setCellValue(DateUtils.format((java.sql.Date) value, "yyyy-MM-dd HH:mm:ss"));
		} else if (value instanceof Timestamp) {
			cell.setCellValue(DateUtils.format((Timestamp) value, "yyyy-MM-dd HH:mm:ss"));
		} else if (value instanceof Calendar) {
			cell.setCellValue((Calendar) value);
		} else if (value instanceof String) {
			cell.setCellValue(StringUtils.trimToEmpty(String.valueOf(value)));
		} else {
			cell.setCellValue(StringUtils.trimToEmpty(value.toString()));
		}

	}
}
