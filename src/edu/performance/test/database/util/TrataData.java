package edu.performance.test.database.util;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TrataData {

	public static GregorianCalendar trata(String dataDoXml) {
		GregorianCalendar data = null;
		int ano, mes, dia;

		if (dataDoXml != null && !dataDoXml.equals("")) {
			// Log.v("thiago", dataDoXml);
			ano = Integer.parseInt(dataDoXml.substring(0, 4));
			mes = Integer.parseInt(dataDoXml.substring(5, 7)) - 1;
			dia = Integer.parseInt(dataDoXml.substring(8, 10));

			data = new GregorianCalendar(ano, mes, dia);
		}

		return data;
	}

	public static String trataGregorian(GregorianCalendar dt) {

		if (dt == null)
			return null;

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

		return formato.format(dt.getTime());
	}

	public static String proRTM(String ddMMYYYY) {

		String ano, mes, dia;

		if (ddMMYYYY != null && !ddMMYYYY.equals("")) {

			dia = ddMMYYYY.substring(0, 2);
			mes = ddMMYYYY.substring(3, 5);
			ano = ddMMYYYY.substring(6, 10);

			return ano + "-" + mes + "-" + dia;
		}
		return "";
	}

	public static float deHoraParaFloat(String hora) {
		float resultado = 0;

		String frac;
		int ic;

		ic = hora.indexOf(":");
		if (ic > 0) {
			frac = hora.substring(ic + 1);

			resultado = (Float.valueOf(frac) / 60);
			resultado += Float.valueOf(hora.substring(0, ic));
		}
		return resultado;
	}

}
