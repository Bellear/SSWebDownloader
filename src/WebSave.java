import org.jsoup.Jsoup;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

public class WebSave {

    public static void PageDawnloader(final String URL) throws IOException {
        /*
        Принимает URL-ссылку, построчно анализирует html код web - страницы.
        Каждая строка web - страницы: считывается, записывается в .txt файл,
        очищается от html - тегов, разбивается пословно и добавляется в HashMap.
        Выводит содержимое HashMap в консоль.
        */
        String line = "", all = "";
        java.net.URL myUrl = null;
        BufferedReader in = null;
        try {
            myUrl = new URL(URL);
            in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

            String fileName = Long.toHexString(Double.doubleToLongBits(Math.random()));
            String dir = System.getProperty("user.dir") + System.getProperty("file.separator") + "DownloadedPages" + System.getProperty("file.separator") + fileName;

            HashMap<String, Integer> wordsCount = new HashMap<>();

            while ((line = in.readLine()) != null) {
                FileWriter writer = new FileWriter(dir, true);
                BufferedWriter bufferWriter = new BufferedWriter(writer);
                bufferWriter.write(line + System.lineSeparator());
                bufferWriter.close();

                String html = html2text(line) + System.lineSeparator();

                wordsCount(html, wordsCount);
            }
            for (String word : wordsCount.keySet()) {
                System.out.println(word + " - " + wordsCount.get(word));
            }
        } catch (UnknownHostException | MalformedURLException e){
            System.out.println("Wrong URL");
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static String html2text(String html) {
        /*
        Удаление из строки html - тегов
         */
        return Jsoup.parse(html).text();
    }

    public static HashMap<String, Integer> wordsCount (String string, HashMap<String, Integer> wordToCount){
        /*
        Разбивает принятую строку по словам и добавляет в HashMap
         */
        if (string == ""){
            System.out.println("No text");
        } else {
            String[] words = string.split("(?U)\\W+");
            for (String word : words) {
                if (!wordToCount.containsKey(word)) {
                    wordToCount.put(word, 0);
                }
                wordToCount.put(word, wordToCount.get(word) + 1);
            }
        }
        return wordToCount;
    }

    public static void start () throws IOException {
        /*
        Запрос ввода URL-ссылки и запуск анализа
         */
        System.out.print("Link: ");
        Scanner in = new Scanner(System.in);
        String site = in.nextLine();
        WebSave.PageDawnloader(site);
    }
}
