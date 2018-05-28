package com.travel.utils.excel;

import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface DefaultReadExcelCallback<T> {
	T readTitle(int arg0, Class<T> arg1, Map<Integer, String> arg2, Row arg3, Sheet arg4, Workbook arg5);

	T readData(Class<T> arg0, Map<Integer, String> arg1, Row arg2, Sheet arg3, Workbook arg4);
}
