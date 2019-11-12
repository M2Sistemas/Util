package br.americo.myutil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class MyConvertValue {

    public enum TpArredondamento {
        acima,
        baixo
    }

    /**
     * @param obj   (String ou Double)
     * @param casas quantidade de casas decimais
     * @return String com arrendondamento
     */
    public static String objectToString(Object obj, int casas) {
        double doubleToFormat = (Double) obj;

        NumberFormat numberformat = NumberFormat.getInstance();
        numberformat.setMaximumFractionDigits(casas);
        numberformat.setMinimumFractionDigits(casas);

        return numberformat.format(doubleToFormat);
    }

    /**
     * @param precoString string com valor
     * @return Double com valor
     */
    public static double stringToDouble(String precoString) {
        if (precoString.contains(".") && precoString.contains(","))
            precoString = precoString.replace(".", "").replace(",", ".");
        else
            precoString = precoString.replace(",", ".");

        return Double.valueOf(precoString);
    }

    /**
     * @param precoDouble Double com valor
     * @return String com valor
     */
    public static String doubleToString(double precoDouble) {
        float valorFloat = (float) precoDouble;
        String valor = Double.toString(Double.parseDouble(Float.toString(valorFloat)));

        String[] part = valor.split("[.]");

        if (part[1].equals("0"))
            valor = part[0];

        return valor;
    }

    /**
     * @param valor Double com valor
     * @param casas quantidade de casas decimais
     * @return String com valor
     */
    public static String doubleToString(double valor, int casas) {
        StringBuilder stb = new StringBuilder();
        stb.append("###,##0.");

        for (int x = 0; x < casas; x++) {
            stb.append("0");
        }

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(new Locale("pt", "BR"));

        DecimalFormat df = new DecimalFormat(stb.toString(), otherSymbols);

        return df.format(valor);
    }

    /**
     * @param valor  Double com valor
     * @param casas  quantidade de casas decimais
     * @param locate formato do valor
     * @return String com valor
     */
    public static String doubleToString(double valor, int casas, Locale locate) {
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(locate));
        df.setMaximumFractionDigits(casas);

        return df.format(valor);
    }

    /**
     * @param valor            Double com valor
     * @param casas            quantidade de casas decimais
     * @param tpArredondamento cima/baixo
     * @return Double com valor
     */
    public static double arredondar(double valor, int casas, TpArredondamento tpArredondamento) {
        double arredondado = valor;
        arredondado *= (Math.pow(10, casas));

        if (tpArredondamento == TpArredondamento.acima)
            arredondado = Math.ceil(arredondado);
        else
            arredondado = Math.floor(arredondado);

        arredondado /= (Math.pow(10, casas));

        return arredondado;
    }

    /**
     * @param valor Double com valor
     * @param casas quantidade de casas decimais
     * @return Double com valor
     */
    public static double arredondar(double valor, int casas) {
        BigDecimal bd = new BigDecimal(valor);

        bd = bd.setScale(casas, BigDecimal.ROUND_HALF_UP);

        return bd.doubleValue();
    }

    /**
     * @param values list com floats
     * @return array com floats
     */
    public static float[] getFloats(List<Float> values) {
        int length = values.size();
        float[] result = new float[length];

        for (int i = 0; i < length; i++) {
            result[i] = values.get(i);
        }

        return result;
    }
}