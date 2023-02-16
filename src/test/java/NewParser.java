import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

public class NewParser {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        CsvWriterSettings settings = new CsvWriterSettings();
        settings.getFormat().setDelimiter(',');
        CsvWriter writer = new CsvWriter(new FileWriter(new File("C:\\Users\\Vlad\\Downloads\\Parsed.csv")), settings);
        writer.writeHeaders("lvl", "name", "price");

        long startTime = System.currentTimeMillis();
        String serverName = "ragnaros";

        int[] itemId = {171415, 171417, 171412, 171418, 171416, 171413, 171419, 171414,
                172325, 172327, 172322, 172328, 172326, 172323, 172329, 172324,
                172317, 172319, 172314, 172320, 172318, 172315, 172321, 172316,
                173245, 173247, 173241, 173248, 173246, 173243, 173249, 173244, 173242,
                178926, 178927};

        int[] materialId = {187707, 186017, 173172, 173170, 173171, 173173, 171828,
                187700, 171833, 171830, 171831, 171832, 171829, 172231,
                172230, 172097, 187703, 173204, 187701, 172094, 172096};

        try {
            for (int i = 0; i < itemId.length; i++) {
                String url = String.format("https://theunderminejournal.com/#us/%s/item/%d", serverName, itemId[i]);
                driver.get(url);
                Thread.sleep(3400);
                String textName = driver.findElement(By.xpath("//div/a[@class='item']")).getText();
                try {
                    writer.writeRow(225, textName,
                            Math.round(Float.parseFloat(driver.findElement(
                                    By.xpath("//div//span[contains(text(), '225')]/parent::td/following-sibling::td[2]/span")).getText())));
                } catch (NoSuchElementException e) {
                    writer.writeRow(225, textName, 0);
                }
                try {
                    writer.writeRow(235, textName,
                            Math.round(Float.parseFloat(driver.findElement(
                                    By.xpath("//div//span[contains(text(), '235')]/parent::td/following-sibling::td[2]/span")).getText())));
                } catch (NoSuchElementException e) {
                    writer.writeRow(235, textName, 0);
                }
                try {
                    writer.writeRow(249, textName,
                            Math.round(Float.parseFloat(driver.findElement(
                                    By.xpath("//div//span[contains(text(), '249')]/parent::td/following-sibling::td[2]/span")).getText())));
                } catch (NoSuchElementException e) {
                    writer.writeRow(249, textName, 0);
                }
                try {
                    writer.writeRow(262, textName,
                            Math.round(Float.parseFloat(driver.findElement
                                    (By.xpath("//div//span[contains(text(), '262')]/parent::td/following-sibling::td[2]/span")).getText())));
                } catch (NoSuchElementException e) {
                    writer.writeRow(262, textName, 0);
                }
                try {
                    writer.writeRow(291, textName,
                            Math.round(Float.parseFloat(driver.findElement(
                                    By.xpath("//div//span[contains(text(), '291')]/parent::td/following-sibling::td[2]/span" + "\n")).getText())));
                } catch (NoSuchElementException e) {
                    writer.writeRow(291, textName, 0);
                }
            }

            for (int j = 0; j < materialId.length; j++) {
                String url = String.format("https://theunderminejournal.com/#us/%s/item/%d", serverName, materialId[j]);
                driver.get(url);
                Thread.sleep(2500);
                String textName = driver.findElement(By.xpath("//div/a[@class='item']")).getText();
                String textPrice = driver.findElement(By.xpath("//div[@id='item-page']/div/table/tr[@class='current-price']/td/span")).getText();
                writer.writeRow("0", textName, Math.round(Float.parseFloat(textPrice)));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            writer.close();
            driver.quit();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
