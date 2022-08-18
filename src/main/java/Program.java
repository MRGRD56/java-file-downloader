import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;

public class Program {
    public static void main(String[] args) throws Exception {
        var fileUrl = ObjectUtils.firstNonNull(ArrayUtils.get(args, 0), "http://speedtest.ftp.otenet.gr/files/test10Mb.db");
        var destinationPath = ObjectUtils.firstNonNull(ArrayUtils.get(args, 1), "C:\\Users\\SU\\Downloads\\10MB.db");

        var url = new URL(fileUrl);
        var connection = url.openConnection();
        connection.connect();

        int fileSize = connection.getContentLength();

        try (
                var inputStream = new BufferedInputStream(url.openStream());
                var outputStream = new FileOutputStream(destinationPath);
        ) {
            int totalBytesRead = 0;

            var buffer = new byte[inputStream.available()];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                System.out.printf("Progress: %d / %d - %.2f%%%n",
                        totalBytesRead, fileSize, Math.ceil((double) totalBytesRead / fileSize * 10000) / 100);
            }
        }
    }
}
