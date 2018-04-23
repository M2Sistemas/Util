package br.americo.myutil;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyConvertDate {

    public enum TpDate {
        banco,
        display
    }

    /**
     * @param tipo banco: yyyy-MM-dd, display: dd/MM/yyyy
     */
    public static String getDate(TpDate tipo) {
        SimpleDateFormat dtFormat;

        switch (tipo) {
            case banco:
                dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                return dtFormat.format(new Date());
            case display:
                dtFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                return dtFormat.format(new Date());
        }

        return null;
    }

    /**
     * @param tipo banco: yyyy-MM-dd HH:mm:ss, display: dd/MM/yyyy HH:mm:ss
     */
    public static String getDateTime(TpDate tipo) {
        SimpleDateFormat dtFormat;

        switch (tipo) {
            case banco:
                dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                return dtFormat.format(new Date());
            case display:
                dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

                return dtFormat.format(new Date());
        }

        return null;
    }

    /**
     * @param tipo banco: yyyy-MM-dd para display, display: dd/MM/yyyy para banco
     */
    public static String dateConvert(String date, TpDate tipo) {
        StringBuilder stbData = new StringBuilder();

        if (!TextUtils.isEmpty(date)) {
            switch (tipo) {
                case banco:
                    stbData.append(date.substring(8, 10)).append("/");
                    stbData.append(date.substring(5, 7)).append("/");
                    stbData.append(date.substring(0, 4));
                    break;
                case display:
                    stbData.append(date.substring(6, 10)).append("-");
                    stbData.append(date.substring(3, 5)).append("-");
                    stbData.append(date.substring(0, 2));
                    break;
            }
        }

        return stbData.toString();
    }

    /**
     * @param tipo banco: yyyy-MM-dd HH:mm:ss para display, display: dd/MM/yyyy HH:mm:ss para banco
     */
    public static String dateTimeConvert(String dateTime, TpDate tipo) {
        StringBuilder stbData = new StringBuilder();

        if (!TextUtils.isEmpty(dateTime)) {
            switch (tipo) {
                case banco:
                    stbData.append(dateTime.substring(8, 10)).append("/");
                    stbData.append(dateTime.substring(5, 7)).append("/");
                    stbData.append(dateTime.substring(0, 4));
                    stbData.append(" ");
                    stbData.append(dateTime.substring(11, 19));
                    break;
                case display:
                    stbData.append(dateTime.substring(6, 10)).append("-");
                    stbData.append(dateTime.substring(3, 5)).append("-");
                    stbData.append(dateTime.substring(0, 2));
                    stbData.append(" ");
                    stbData.append(dateTime.substring(11, 19));
                    break;
            }
        }

        return stbData.toString();
    }

    /**
     * @param dataInicial data inicial
     * @param dataFinal   data final
     * @return dtInicial <= dataFinal
     */
    public static boolean dateCompare(String dataInicial, String dataFinal) throws Exception {
        DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date dtInicial = dtFormat.parse(dataInicial);
        Date dtFinal = dtFormat.parse(dataFinal);

        return (dtInicial.compareTo(dtFinal) <= 0);
    }

    /**
     * @param dataInicial data inicial
     * @param dataMeio    data meio
     * @param dataFinal   data final
     * @return dataMeio >= dataInicial && dataMeio <= dataFinal
     */
    public static boolean dateCompareInterval(String dataInicial, String dataMeio, String dataFinal) throws Exception {
        DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date dtInicial = dtFormat.parse(dataInicial);
        Date dtMeio = dtFormat.parse(dataMeio);
        Date dtFinal = dtFormat.parse(dataFinal);

        return (dtMeio.compareTo(dtInicial) >= 0 && dtMeio.compareTo(dtFinal) <= 0);
    }

    /**
     * @param date data inicial
     * @param dias quantidade de dias
     * @return data final
     */
    public static String dateSumDays(String date, int dias) throws Exception {
        Calendar calendar = Calendar.getInstance();
        DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date dtUm = dtFormat.parse(date);

        calendar.setTime(dtUm);
        calendar.add(Calendar.DATE, dias);

        return dtFormat.format(calendar.getTime());
    }

    /**
     * @param dataInicial data inicial
     * @param dataFinal   data final
     * @return quantidade de dias entre as datas
     */
    public static int dateGetDays(String dataInicial, String dataFinal) throws Exception {
        DateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Calendar day1 = Calendar.getInstance();
        Calendar day2 = Calendar.getInstance();
        day1.setTime(dtFormat.parse(dataInicial));
        day2.setTime(dtFormat.parse(dataFinal));

        return day2.get(Calendar.DAY_OF_YEAR) - day1.get(Calendar.DAY_OF_YEAR);
    }
}