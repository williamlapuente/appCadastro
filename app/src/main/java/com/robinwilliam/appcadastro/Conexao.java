package com.robinwilliam.appcadastro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Conexao {

    public static String enviarDados(String uri, String postData) {
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Configurar a conexão para método POST
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            // Escrever os dados no corpo da requisição
            OutputStream os = httpURLConnection.getOutputStream();
            byte[] input = postData.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            // Ler a resposta da requisição
            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String linha;

            while ((linha = bufferedReader.readLine()) != null) {
                stringBuilder.append(linha).append("\n");
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
