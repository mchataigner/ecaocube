package cube;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.HashMap;

import cube.Algorithme;

public class BaseAlgorithmes {
    
    private HashMap listeOll=new HashMap();
    private HashMap listePll=new HashMap();

    private HashMap listeOllForme=new HashMap();
    private HashMap listePllContour=new HashMap();
    
    public BaseAlgorithmes(String uri) throws SAXException, IOException {
        XMLReader saxReader = XMLReaderFactory.createXMLReader();//"org.apache.xerces.parsers.SAXParser");
        saxReader.setContentHandler(new ParserAlgorithme());
        saxReader.parse(uri);
        listeOll=((ParserAlgorithme)saxReader.getContentHandler()).getListeOll();
        listePll=((ParserAlgorithme)saxReader.getContentHandler()).getListePll();

        listeOllForme=((ParserAlgorithme)saxReader.getContentHandler()).getListeOllForme();
        listePllContour=((ParserAlgorithme)saxReader.getContentHandler()).getListePllContour();
    }
    
    public Algorithme obtenirOLL(int i)
    {
        return new Algorithme((String)listeOll.get(i));
    }
    
    public Algorithme obtenirPLL(int i)
    {
        return new Algorithme((String)listePll.get(i));
    }
    
    public Algorithme obtenirOLL(String forme)
    {
        String ollString = (String)listeOllForme.get(forme);
        Algorithme oll = null;
        if(ollString != null)
            oll = new Algorithme(ollString);
        return oll;
    }
    
    public Algorithme obtenirPLL(String contour)
    {
        String pllString = (String)listePllContour.get(contour);
        Algorithme pll = null;
        if(pllString != null)
            pll = new Algorithme(pllString);
        return pll;
    }
    
    public HashMap obtenirListeOLL()
    {
        return listeOllForme;
    }
    
    public HashMap obtenirListePLL()
    {
        return listePllContour;
    }


    public static void main(String[] args) {
        if (0 == args.length || 2 < args.length) {
            System.out.println("Usage : SimpleSaxParser uri [parserClassName]");
            System.exit(1);
        }

        String uri = args[0];

        String parserName = null;
        if (2 == args.length) {
            parserName = args[1];
        }

        try {
            BaseAlgorithmes parser = new BaseAlgorithmes(uri);
            System.out.println(parser.obtenirOLL(2));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
