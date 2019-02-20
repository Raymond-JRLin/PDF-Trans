
import org.apache.commons.lang.time.StopWatch;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.xmlbeans.impl.tool.PrettyPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class PdfExtractor {
    private static Logger logger = LoggerFactory.getLogger(PdfExtractor.class);

    public static void main(String args[]) throws Exception {
        StopWatch sw = new StopWatch();
        List<String> files = new ArrayList<>();
//        String path = "/home/junrui/IdeaProjects/pdfTrans/src/main/java/test.txt";
//        String path = "/home/junrui/Downloads/BS/R&N CC Statements.pdf";
//        String path = "/home/junrui/Downloads/BS/Jacob & Co BS\'s.pdf";

//        String path = "/home/junrui/Downloads/BS/MM Events Jul BS\'s.pdf";
//        String path = "/home/junrui/Downloads/BS/VILLAGE JUNCTION BAKERY & Cafe BS\'s.pdf";
        files.add("/home/junrui/Downloads/BS/VILLAGE JUNCTION BAKERY & Cafe BS\'s.pdf");
//        files.add("C:/Users/m/Downloads/20170215.pdf");
//        files.add("C:/Users/m/Downloads/20170315.pdf");
//        files.add("C:/Users/m/Downloads/20170415.pdf");
//        files.add("C:/Users/m/Downloads/20170515.pdf");

        InputStream is;
        List<PdfParser.Record> full = new ArrayList<>();
        for (String fileName : files) {
            logger.info("Now processing " + fileName);
            is = new FileInputStream(fileName);
            ContentHandler contenthandler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            PDFParser pdfparser = new PDFParser();
            pdfparser.parse(is, contenthandler, metadata, new ParseContext());
            String data = contenthandler.toString();
            List<PdfParser.Record> chaseRecords = PdfParser.parse(data);
            full.addAll(chaseRecords);
            is.close();
        }
//        logger.info("Total processing time: " + PrettyPrinter.toMsSoundsGood(sw.getTime()));
        full.forEach(cr -> System.out.println(cr.date + "|" + cr.desc + "|" + cr.amount));
    }
}
