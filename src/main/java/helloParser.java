import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
//import sun.jvm.hotspot.oops.Metadata;
import org.apache.tika.metadata.Metadata;

import java.io.*;

public class helloParser {
    public static void main(String[] args) throws IOException, SAXException, TikaException {
//        String path = "/home/junrui/IdeaProjects/pdfTrans/src/main/java/test.txt";
//        String path = "/home/junrui/Downloads/BS/R&N CC Statements.pdf";
//        String path = "/home/junrui/Downloads/BS/Jacob & Co BS\'s.pdf";

//        String path = "/home/junrui/Downloads/BS/MM Events Jul BS\'s.pdf";
        String path = "/home/junrui/Downloads/BS/VILLAGE JUNCTION BAKERY & Cafe BS\'s.pdf";
        File file = new File(path);
        String name = file.getName().substring(0,7);

        System.out.println("method1: ");
        writeFile("method1" + name, method1(path));

        System.out.println("method2: ");
        writeFile("method2" + name, method2(path));

        System.out.println("method3: ");
        writeFile("method3" + name, method3(path));

        System.out.println("method4: ");
        writeFile("method4" + name, method4(path));

        System.out.println("method5: ");
        writeFile("method5" + name, method5(path));

    }

    private static void writeFile(String name, String content) {
//        FileOutputStream fos = null;
        BufferedWriter bw;
        try {
//            fos = new FileOutputStream(name);
//            DataOutputStream outStream2 = new DataOutputStream(new BufferedOutputStream(fos));
//            outStream2.writeUTF(content);
//            outStream2.close();
            bw = new BufferedWriter(new FileWriter((name)));
            bw.write(content);
            bw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String method1(String path) throws SAXException, TikaException {
//        System.out.println("method1: ");
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        try {
            FileInputStream inputstream = new FileInputStream(new File(path));
            ParseContext pcontext = new ParseContext();

            //parsing the document using PDF parser
            Parser pdfparser = new AutoDetectParser();
            TesseractOCRConfig config = new TesseractOCRConfig();
            String tPath = "/home/junrui/Downloads/tesseract-ocr-3.02.grc/tesseract-ocr/tessdata/grc.traineddata";
            config.setTesseractPath(tPath);
            PDFParserConfig pdfConfig = new PDFParserConfig();
            pdfConfig.setExtractInlineImages(true);
            pdfConfig.setExtractUniqueInlineImagesOnly(false); // set to false if pdf contains multiple images.
            ParseContext parseContext = new ParseContext();
            parseContext.set(TesseractOCRConfig.class, config);
            parseContext.set(PDFParserConfig.class, pdfConfig);
            //need to add this to make sure recursive parsing happens!
            parseContext.set(Parser.class, pdfparser);
            pdfparser.parse(inputstream, handler, metadata,pcontext);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //getting metadata of the document
        System.out.println("Metadata of the PDF:");
        String[] metadataNames = metadata.names();
//
        for(String name : metadataNames) {
            System.out.println(name + ":" + metadata.get(name));
        }

        return handler.toString();
    }

        private static String method2(String path) {
            //1、创建一个parser
            Parser parser = new AutoDetectParser();
            InputStream is = null;
            File f = new File(path);
            try {
                Metadata metadata = new Metadata();
                metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
                is = new FileInputStream(f);
                ContentHandler handler = new BodyContentHandler();
                ParseContext context = new ParseContext();
                context.set(Parser.class,parser);
                //2、执行parser的parse()方法。
                parser.parse(is,handler, metadata,context);
                for(String name:metadata.names()) {
                    System.out.println(name+":"+metadata.get(name));
                }
                return handler.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (TikaException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(is!=null) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    private static String method3(String path) throws IOException, SAXException, TikaException{
        //构建InputStream来读取数据
        InputStream  input=new FileInputStream(new File(path));//可以写文件路径，pdf，word，html等
        BodyContentHandler textHandler=new BodyContentHandler();
        Metadata matadata=new Metadata();//Metadata对象保存了作者，标题等元数据
        Parser parser=new  AutoDetectParser();//当调用parser，AutoDetectParser会自动估计文档MIME类型，此处输入pdf文件，因此可以使用PDFParser
        ParseContext context=new ParseContext();
        parser.parse(input, textHandler, matadata, context);//执行解析过程
        input.close();
        System.out.println("Title: "+matadata.get(Metadata.TITLE));
        System.out.println("Type: "+matadata.get(Metadata.TYPE));
//        System.out.println("Body: "+textHandler.toString());//从textHandler打印正文
        return textHandler.toString();
    }

    private static String method4(String path) throws IOException, TikaException {
        Tika tika = new Tika();//自动根据文件类型选择Parse类
//        System.out.println(tika.parseToString(new URL("http://www.google.com")));
        File file = new File(path);
        return tika.parseToString(file);
    }

    private static String method5(String path) {
        FileInputStream inputstream;
        try {
            inputstream = new FileInputStream(new File(path));
            Parser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            parser.parse(inputstream, handler, metadata, context);
            for (String name :
                    metadata.names()) {
                System.out.println(name+":"+metadata.get(name));
            }
            return  handler.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}