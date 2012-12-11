package cube;

import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;
import java.util.HashMap;
public class ParserAlgorithme implements ContentHandler
{
    private Locator locator;
    private boolean test=true;
    private String type="";
    private String name="";
    private String contour="";
    private String forme="";
    private Integer id;
    private String algo="";

    private HashMap listeAlgoOll=new HashMap();
    private HashMap listeAlgoPll=new HashMap();
    private HashMap listeAlgoOllForme=new HashMap();
    private HashMap listeAlgoPllContour=new HashMap();

    public ParserAlgorithme()
    {
        super();
        locator=new LocatorImpl();
    }
    public void setDocumentLocator(Locator l)
    {
        locator=l;
    }
    public void startDocument() throws SAXException
    {
        //System.err.println("début!!");
    }

    public HashMap getListeOll()
    {
        return listeAlgoOll;
    }
    public HashMap getListePll()
    {
        return listeAlgoPll;
    }

    public HashMap getListeOllForme()
    {
        return listeAlgoOllForme;
    }
    public HashMap getListePllContour()
    {
        return listeAlgoPllContour;
    }

    public void endDocument() throws SAXException
    {
        //System.err.println("fin!!");
    }
    public void startPrefixMapping(String prefix,String URI) throws SAXException
    {
        //System.err.println("Traitement de l'espace de nommage : " + URI + ", prefixe choisi : " + prefix);
    }
    public void endPrefixMapping(String prefix) throws SAXException {
        //System.err.println("Fin de traitement de l'espace de nommage : " + prefix);
    }
    public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException {
        //if(localName.equals("liste-oll"))
        //test=false;
        if(localName.equals("oll")||localName.equals("pll"))
            type=localName;
        else if(!type.equals("oll")&&!type.equals("pll"))
            type="";
        if(test)
            {
                //System.err.println("<" + localName+">");
                
                if ( ! "".equals(nameSpaceURI)) { // espace de nommage particulier
                    //System.err.println("  appartenant a l'espace de nom : "  + nameSpaceURI);
                }
                
                //System.err.println("  Attributs de la balise : ");
                
                for (int index = 0; index < attributs.getLength(); index++) { // on parcourt la liste des attributs
                    if((type.equals("oll")||type.equals("pll"))&&attributs.getLocalName(index).equals("index"))
                        id=new Integer(attributs.getValue(index));
                    if(type.equals("oll")&&attributs.getLocalName(index).equals("forme"))
                        forme=attributs.getValue(index).trim();
                    if(type.equals("pll")&&attributs.getLocalName(index).equals("contour"))
                        contour=attributs.getValue(index).trim();
                    //System.err.println("     - " +  attributs.getLocalName(index) + " = " + attributs.getValue(index));
                }
            }
    }
    
    public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException {
        //if(test)
            //System.err.print("</" + localName+">");
        if(type.equals("oll")||type.equals("pll"))
            {
                id=null;
                type="";
                contour="";
                forme="";
            }
        if ( ! "".equals(nameSpaceURI)) { // name space non null
            //System.err.print("appartenant a l'espace de nommage : " + localName);
        }
        if(localName.equals("liste-oll"))
            test=true;
    }
    public void characters(char[] ch, int start, int end) throws SAXException {
        //if(test)
            //System.err.println("algo : #PCDATA : " + new String(ch, start, end));
        //if(type.equals("oll")||type.equals("pll"))
            //System.err.println(type);
        if(type.equals("oll"))
            {
                listeAlgoOll.put(id,new String(ch,start,end));
                if(forme!="")
                    listeAlgoOllForme.put(forme,new String(ch,start,end));
            }
        if(type.equals("pll"))
            {
                listeAlgoPll.put(id,new String(ch,start,end));
                if(contour!="")
                    listeAlgoPllContour.put(contour,new String(ch,start,end));
            }

    }
    public void ignorableWhitespace(char[] ch, int start, int end) throws SAXException {
        //System.err.println("espaces inutiles rencontres : ..." + new String(ch, start, end) +  "...");
    }
    public void processingInstruction(String target, String data) throws SAXException {
        //System.err.println("Instruction de fonctionnement : " + target);
        //System.err.println("  dont les arguments sont : " + data);
    }
    public void skippedEntity(String arg0) throws SAXException {
        // Je ne fais rien, ce qui se passe n'est pas franchement normal.
        // Pour eviter cet evenement, le mieux est quand meme de specifier une dtd pour vos
        // documents xml et de les faire valider par votre parser.              
    }

    

}