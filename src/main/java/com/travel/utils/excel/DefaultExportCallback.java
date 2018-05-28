package com.travel.utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface DefaultExportCallback<T> {
	String customTitle(Workbook arg0, Sheet arg1, Row arg2, Cell arg3, CellStyle arg4, String arg5);

	Object customData(Workbook arg0, Sheet arg1, Row arg2, Cell arg3, CellStyle arg4, String arg5, Object arg6);
}