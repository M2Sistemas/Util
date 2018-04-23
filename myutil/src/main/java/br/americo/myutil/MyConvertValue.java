package br.americo.myutil;

import java.text.NumberFormat;
import java.util.List;

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
     * @param valor            Double com valor
     * @param casas            quantidade de casas decimais
     * @param tpArredondamento cima/baixo
     * @return String com valor
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