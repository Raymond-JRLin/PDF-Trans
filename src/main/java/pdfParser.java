import com.levigo.jbig2.util.log.LoggerFactory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;



public class pdfParser {
    public static void main(String[] args) {
        File file = new File("/home/junrui/Downloads/BS/R&N CC Statements.pdf");
//        String txt = fileToTxt(file);
//        System.out.println(txt);
    }



}
