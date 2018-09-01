package br.americo.myutil;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("unused")
public class MySystem {

    /**
     * @return numero aleatorio
     */
    public static int randInt() {
        Random rand = new Random();

        return rand.nextInt((50000) + 1);
    }

    /**
     * @return id unico com base na data
     */
    public static String generateId() {
        return new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(new Date());
    }

    /**
     * @return codigo da versão do aplicativo
     */
    public static int getAppVersionCode(Context context) throws NameNotFoundException {
        PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

        return (pi.versionCode);
    }

    /**
     * @return nome da versão do aplicativo
     */
    public static String getAppVersionName(Context context) throws NameNotFoundException {
        PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

        return (pi.versionName);
    }

    /**
     * @return existe cartao de memoria?
     */
    public static boolean isSdCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @return possui conexao com internet?
     */
    public static boolean isInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else
            return false;
    }

    /**
     * @return possui conexao com internet via wifi?
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return networkInfo.isConnected();
        } else
            return false;
    }

    /**
     * @return internet esta habilitada?
     */
    public static boolean isInternetEnabled(Context context) {
        return isWifiEnabled(context) || isMobileEnabled(context);
    }

    /**
     * @return wifi esta habilitado?
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wifiInfo = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        return wifiInfo != null && wifiInfo.isWifiEnabled() && wifiInfo.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }

    /**
     * @return dados moveis esta habilitado?
     */
    public static boolean isMobileEnabled(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo mobileInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mobileInfo != null) {
                String reason = mobileInfo.getReason();

                return !(mobileInfo.getState() == NetworkInfo.State.DISCONNECTED && (reason == null || reason.equals("specificDisabled") || reason.equals("dataDisabled")));
            }
        }

        return false;
    }

    /**
     * verifica se o pacote existe
     */
    public static boolean isPackageExisted(Context context, String targetPackage) {
        List<PackageInfo> packages;
        PackageManager pm = context.getPackageManager();

        packages = pm.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }

        return false;
    }

    /**
     * @return remove acentuação e espaços
     */
    public static String trataCaracter(String trata) {
        if (!TextUtils.isEmpty(trata)) {
            trata = trata.trim();

            trata = Normalizer.normalize(trata, Normalizer.Form.NFD);
            trata = trata.replaceAll("[^\\p{ASCII}]", "");
            trata = trata.replaceAll(";", ".").toUpperCase();
        }

        return trata;
    }

    /**
     * Chama a calculadora do aparelho
     */
    public static void calculadora(Context context) {
        ArrayList<HashMap<String, Object>> items = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);

        for (PackageInfo pi : packs) {
            if (pi.packageName.toLowerCase(Locale.getDefault()).contains("calcul")) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }

        if (items.size() >= 1) {
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null)
                context.startActivity(i);
        }
    }

    /**
     * Deleta arquivos de um determinado diretorio
     */
    public static void deletaArquivos(String diretorio) throws Exception {
        File folder = new File(diretorio);

        if (folder.isDirectory()) {
            File[] files = folder.listFiles();

            for (File file : files) {
                if (file.exists() && !file.delete())
                    throw new Exception("Erro ao excluir arquivo " + file.getName());
            }
        }
    }

    /**
     * Deleta diretorio
     */
    public static void deletaDiretorio(String diretorio) throws Exception {
        File fileOrDirectory = new File(diretorio);

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deletaDiretorio(child.getAbsolutePath());
            }
        }

        if (fileOrDirectory.exists() && !fileOrDirectory.delete())
            throw new Exception("Erro ao excluir arquivo " + fileOrDirectory.getName());
    }

    /**
     * Faz uma copia do arquivo
     */
    public static void copiaArquivo(File arquivo, File destino) throws Exception {
        if (destino.exists() && !destino.delete())
            throw new Exception("Erro ao excluir arquivo " + destino.getName());

        if (destino.createNewFile()) {
            InputStream in = new FileInputStream(arquivo);
            OutputStream out = new FileOutputStream(destino);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        } else
            throw new Exception("Erro ao criar arquivo " + destino.getName());
    }

    /**
     * Gera um arquivo apartir de um InputStream.
     */
    public static void inputStreamToFile(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);

        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.close();
        in.close();
    }

    /**
     * Gera uma string apartir de um InputStream.
     */
    public static String inputStreamToString(InputStream stream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }

    /**
     * Converte arquivo para byte.
     */
    public static byte[] fileToByte(File file) throws Exception {
        byte[] bytesArray = new byte[(int) file.length()];

        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        return bytesArray;
    }

    /**
     * Comprime string.
     */
    public static String compress(String str) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(str.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(str.getBytes("UTF-8"));
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();

        return Base64.encodeToString(compressed, 0);
    }

    /**
     * Descomprime string
     */
    public static String decompress(String zipText) throws IOException {
        byte[] result = Base64.decode(zipText, 0);

        ByteArrayInputStream is = new ByteArrayInputStream(result);
        GZIPInputStream gis = new GZIPInputStream(is);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        for (int value = 0; value != -1; ) {
            value = gis.read();
            if (value != -1) {
                baos.write(value);
            }
        }

        gis.close();
        baos.close();

        return new String(baos.toByteArray(), "UTF-8");
    }

    /**
     * Descomprime arquivo zip
     */
    public static void decompressZip(String de, String para) throws Exception {
        FileInputStream fis = new FileInputStream(de);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;

        while ((entry = zis.getNextEntry()) != null) {
            int size;
            byte[] buffer = new byte[1024];

            FileOutputStream fos = new FileOutputStream(para + entry.getName());
            BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length);

            while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, size);
            }

            bos.flush();
            bos.close();
        }

        zis.close();
        fis.close();
    }

    /**
     * calcula numero de colunas de um GridLayoutManager
     */
    public static int calcularNumeroColunas(Context context, int tamanhoColuna) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / tamanhoColuna);
    }
}