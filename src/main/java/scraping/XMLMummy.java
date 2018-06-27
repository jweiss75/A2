package scraping;

/// Mummy is the dealer of basic data for Monsters*/
// El valiente tiene miedo del contrario; el cobarde, de su propio temor
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

public class XMLMummy {

    List<Element> listaelemento;
    public List<Vector>  refyear;

    public String matchYearRef(String ref) {
        
        System.out.println("* this is matchYearRef");
        String year = "2018";
        

        // System.out.println("//" + this.refyear.size());

        for (Vector referencia : this.refyear) {
            if (ref.equals(referencia.get(0).toString())) {
                year = referencia.get(0).toString().substring(2,6);
                // System.out.println("&&&&&&&&&&&&&&&&&&&&&&" + referencia.get(0) + "&&&&&&&" + year);
                
            }

        }

        // System.out.println("&&&&&&&&&& AÑO:" +  year);
        return year;

    }

    public List<Vector> anualidadList() throws Exception {

        System.out.println("* This is anualidadList");
        // IN THIS LIST WE STORE THE REFERENCES AND YEAR 
        List<Vector> lista1 = new ArrayList<Vector>(); // Aqui guardaremos el array.
        XMLMonster monstruito = new XMLMonster();

        // Search the contexts and find their references.
        monstruito.setTagName("xbrli:context");
        List<Element> listaElementosContexto = monstruito.searchTags();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + listaelemento.size());
        for (Element contexto : this.listaelemento) {
            // find the context regerence.


            String id = getId(contexto);
            System.out.println("******************************");
            System.out.println("Contexto con referencia: " + id);
            System.out.println("******************************");


            // We look for the dates

            NodeList contextincludedtags = monstruito.getAllChildren(contexto);
            for (int i = 0; i < contextincludedtags.getLength(); i++) {

                // We look for the <xrbl:period tag> but the prefic coul be different
                String str2 = "period";
                System.out.println(contextincludedtags.item(i).getNodeName().toLowerCase().contains(str2.toLowerCase()));

                if (contextincludedtags.item(i).getNodeName().toLowerCase().contains(str2.toLowerCase()) == true) {
                    NodeList nodoPerido = contextincludedtags.item(i).getChildNodes();
                    System.out.println("Period found: " + contextincludedtags.item(i).getNodeName());
                    for (int j = 0; j < nodoPerido.getLength(); j++) {
                        String valorperiodo = nodoPerido.item(j).getNodeName() + ":" + nodoPerido.item(j).getTextContent();
                        // System.out.println(nodoPerido.item(j).getNodeName()+":"+nodoPerido.item(j).getTextContent());


                        Vector vec = new Vector();
                        vec.add(id);
                        vec.add(contextincludedtags.item(i).getNodeName());
                        vec.add(valorperiodo);
                        lista1.add(vec);

                    }

                }


            }

        }

        System.out.println(lista1);
        this.setRefYear(lista1);
        return lista1;

    }

    public static String getId(Element e) {

        String href = e.getAttribute("id");

        return href;
    }


    public List<Element> getListaElemento() {
        return listaelemento;
    }

    public void setListaElemento(List<Element> listaelemento) {
        this.listaelemento = listaelemento;
        System.out.println("-------------------------------->" + listaelemento);

    }

    public List<Vector> getRefYear() {
        return refyear;
    }

    public void setRefYear(List<Vector> refyear) {
        this.refyear = refyear;


    }

}
