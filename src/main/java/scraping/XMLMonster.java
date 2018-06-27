package scraping;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;

import Bd.DBManage;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import java.sql.Connection;

// El sueño de la razón produce monstruos

public class XMLMonster {
    String            entidad;
    String            anualidad;
    String            tagname       = "*";
    String            sourcefile    = "/A1/XBRL_Files/2016025878.xbrl";
    List<Vector>      matrizrefyear = null;
    public Connection con           = null;

    // Acciona 2010026427
    // PHM 2016025878
    public static void main(String[] args) throws Exception {
        System.out.println("-------------------El sueño de la razón produce monstruos-----------------------");


        XMLMonster monster = new XMLMonster();


        System.out.println("Murcielagos para:" + monster.sourcefile);

        monster.setTagName("LegalNameValue");
        monster.searchTags();
        monster.setTagName("xbrli:context");
        System.out.println("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||1");
        XMLMummy mummy = new XMLMummy();
        mummy.setListaElemento(monster.searchTags());
        monster.setMatrizRefYear(mummy.anualidadList());
        System.out.println(mummy.anualidadList());
        System.out.println("mummy.size():" + mummy.refyear.size());
        monster.setMatrizRefYear(mummy.refyear);


        // monster.setMatrizRefYear(monster.detectaAnualidad());

        // monster.setTagName("ipp-gen:BalanceIndividual");
        // monster.searchTags();
        // monster.setTagName("ipp-gen:CuentaPerdidasGananciasIndividual");
        // monster.searchTags();
        monster.setTagName("BalanceConsolidado");
        monster.searchTags();
        // monster.setTagName("ipp-gen:CuentaPerdidasGananciasConsolidado");
        // monster.searchTags();


    }

    //


    public NodeList getAllChildren(Element e) {
        NodeList allElements = e.getChildNodes();
        DBManage inserciondb = new DBManage();


        // inserciondb.setCon (inserciondb.conection());
        for (int i = 0; i < allElements.getLength(); i++) {
            try {


                String node = "";
                String ref = "";
                String value = "";
                String date = "";


                if (allElements.item(i).getTextContent().trim().length() != 0 ) {

                    Node attref = allElements.item(i).getAttributes().getNamedItem("contextRef");

                    node = allElements.item(i).getNodeName();
                    ref = attref.getTextContent();
                    value = allElements.item(i).getTextContent().trim();
                    if (attref != null) {

                        // System.out.println("Podemos buscar el año ---------------------"+allElements.item(i).getAttributes().getNamedItem("contextRef").getNodeValue());

                        XMLMummy mummy = new XMLMummy();
                        // System.out.println("#################################"+this.matrizrefyear.size());
                        mummy.setRefYear(this.matrizrefyear);

                        date = mummy.matchYearRef(attref.getTextContent());
                        // System.out.println("#################################"+fecha);


                        // matchYearRef(allElements.item(i).getAttributes().getNamedItem("contextRef").getNodeValue());
                        // System.out.println(allElements.item(i).getAttributes().getNamedItem("contextRef").getNodeValue());
                        // this.matchYearRef(allElements.item(i).getAttributes().getNamedItem("contextRef").getNodeValue());
                        // this.detectaAnualidad(allElements.item(i).getAttributes().getNamedItem("contextRef").getNodeValue());
                    }


                    System.out.println(" |");
                    System.out.println(" |");
                    System.out.println(" |------->Empresa : " + this.entidad);
                    System.out.println(" |------->Nodo    : " + node);
                    if (attref != null) {
                        System.out.println(" |------->ref     : " + ref);
                    } else {
                        System.out.println(" |------->ref     :null");
                    }
                    System.out.println(" |------->Valor   : " + value);
                    System.out.println(" |------->Año     : " + date);


                    System.out.println(" Intentando inserdatr dtos.... " + date);
                    inserciondb.insercion(this.con, this.entidad, this.tagname, node, value, date);


                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(">>>> MONSTER genero error:" + ex.getMessage());
                System.out.println("Linea: de error :" + ex.getStackTrace()[0].getLineNumber());
                // System.out.println(" ------->Valor: " + allElements.item(i).getNodeName().trim());
                if (allElements.item(i).getNodeName() == "xbrli:identifier") {
                    this.entidad = allElements.item(i).getTextContent().trim();
                    System.out.println("Se detecto la entidad: " + entidad);
                }
            }

        }


        return allElements;
    }


    public static String getContextRef(Element e) {

        String href = e.getAttribute("contextRef");

        return href;
    }

    public static String getId(Element e) {

        String href = e.getAttribute("id");

        return href;
    }


    public List<Element> searchTags() throws Exception {


        List<Element> listadetags = new ArrayList<Element>();

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(this.sourcefile);
            FileInputStream fis = new FileInputStream(file);
            Document doc = db.parse(fis);

            NodeList entries = doc.getElementsByTagName("*");

            for (int i = 0; i < entries.getLength(); i++) {
                Element element = (Element)entries.item(i);

                String nombre = element.getNodeName();
                NamedNodeMap schema = element.getAttributes();


                if (nombre.toLowerCase().indexOf(this.tagname.toLowerCase()) > -1 || this.tagname == "*") {
                    System.out.println("ELEMENTO    : " + element.getTagName());
                    System.out.println("TIPO ESQUEMA: " + element.getSchemaTypeInfo());
                    System.out.println("ATTRIBUTES  : " + schema);
                    System.out.println("URI         : " + element.getNodeName());
                    System.out.println("ElementRef  : " + getContextRef(element));
                    System.out.println("Valor  : " + element.getTextContent().trim());

                    if (nombre.toLowerCase().indexOf("legalnamevalue") > -1) {
                        this.entidad = element.getTextContent().trim();

                    }

                    NodeList nodos = getAllChildren(element);


                    listadetags.add(element);
                    System.out.println("____________________________");
                }


            }
        } catch (Exception e) {

            System.out.println("MONSTER  ERROR:" + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("LISTA De TAGS is" + listadetags.size());
        return listadetags;

    }


    public void closeCon() {
        try {
            this.con.close();
            System.out.println("Closing the connection of this Monster....");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
    }


    public String getTagName() {
        return tagname;
    }

    public void setTagName(String tagname) {
        this.tagname = tagname;
    }

    public String getSourceFile() {
        return sourcefile;
    }

    public void setSourceFile(String sourcefile) {
        this.sourcefile = sourcefile;
    }

    public List<Vector> getMatrizRefYear() {
        return matrizrefyear;
    }

    public void setMatrizRefYear(List<Vector> matrizrefyear) {
        this.matrizrefyear = matrizrefyear;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        System.out.println("Opening BD Connection por this Monster");
        this.con = con;

    }


}
