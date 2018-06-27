package scraping;




import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Namespace;

// Las tristezas no se hicieron para las bestias,
// sino para los hombres; 
// pero si los hombres las sienten demasiado,  
// se vuelven bestias.

public class XMLBeast {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		  SAXBuilder builder = new SAXBuilder();
		  File xmlFile = new File("XBRL_Files/2016025878.xbrl");
          
		  try { 

			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			
			
			System.out.println(listaURI(rootNode,1));
			ipps(rootNode);
			List<Element> list = rootNode.getChildren();
            List<Element> elementos = rootNode.getChildren("xbrl");
			
            
			for (int i = 0; i < list.size(); i++) {
                
			   Element node = (Element) list.get(i);
			   
   
			   
			

			}

		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
		}
// Cada uno es como Dios le hizo
// y aún peor muchas veces.		

	public static String showMeAttributes(Element nodo){
	    String a ="I SHOW YOU ATTRIBUTES:";
	    List<Attribute> atributos = nodo.getAttributes();
	    for (int i=0;i<atributos.size();i++) {
	         
	        System.out.println("--"+atributos.get(i).toString());
	        
	    }
	    
	    
	    return a;
	}	
	
	static public String atributos(String nombre, Element node) {
	List<Attribute> atributos = node.getAttributes();
	String a ="I LIST THE ATTRIBUTES:";
	    String valor=""; 	   
			     for (int j=0; j < atributos.size(); j++){
			         //System.out.println("--Atributo: " + atributos.get(j).getName());
			           if (atributos.get(j).getName()==nombre){
			          valor = atributos.get(j).getValue();
			              
			         System.out.println("--Atributo: " + atributos.get(j).getName()+ "=" + atributos.get(j).getValue());
			          }
			     }
            return valor;
        }
        
        
        public static List<Attribute> listaAtributos(Element node) {
	List<Attribute> atributos = node.getAttributes();
	                     System.out.println(".. NOTE: "+""+ node.getName()+ " has de next attributes");
			     for (int j=0; j < atributos.size(); j++){
			         System.out.println("......."+atributos.get(j).getName());
			     }
			     return atributos;
            
        }
        
        public static List<Namespace> listaURI(Element node, int opcion) {
	             List <Namespace> intro_namespaces = node.getNamespacesIntroduced();
	             List <Namespace> scop_namespaces = node.getNamespacesInScope();
	             Namespace namespace = node.getNamespace();
	             String name = node.getName();
	             String URI = node.getNamespaceURI();
	             System.out.println("URIS DEL NODO RAIZ");
	             System.out.println("MOMBRE NODO RAIZ: "+name);
	             System.out.println("PREFIJO NODO RAIZ: "+namespace .getPrefix());
	             System.out.println("URI NODO RAIZ: "+URI);
	             System.out.println("-----------------------");
	             System.out.println("____ATRIBUTOS SCOPE DEL NODO : "+scop_namespaces.size());
	             for (int j=0; j < scop_namespaces.size(); j++){
			         
			         // String namespaceName    = namespaces.get(j).getNamespace();
			         String namespaceURI     = scop_namespaces.get(j).getURI();
			         String namespacePrefix  = scop_namespaces.get(j).getPrefix();
			         
			         System.out.println("......."+namespacePrefix+"  |  "+namespaceURI);
			     }
	             System.out.println("____NAMESPACES INTRODUCED DEL NODO : "+intro_namespaces.size());
			     for (int j=0; j < intro_namespaces.size(); j++){
			         // String namespaceName    = namespaces.get(j).getNamespace();
			         String namespaceURI     = intro_namespaces.get(j).getURI();
			         String namespacePrefix  = intro_namespaces.get(j).getPrefix();
			         
			         System.out.println("......."+namespacePrefix+"  |  "+namespaceURI);
			     }
			     
			       if (opcion==1){
			     return intro_namespaces;
                }
                else if(opcion==0) {
                  return scop_namespaces;
                }
                else 
                {return intro_namespaces;}
            
        }
        
        public static void ipps(Element  node){
            List <Element> intro_namespaces = node.getChildren();
             System.out.println("URIS DEL NODO RAIZ");
            for (int j=0; j < intro_namespaces.size(); j++){
			         // String namespaceName    = namespaces.get(j).getNamespace();
			         String namespaceURI     = intro_namespaces.get(j).getName();
			         String namespacePrefix  = intro_namespaces.get(j).getName();
			         
			         System.out.println("......."+namespacePrefix+"  |  "+namespaceURI);
			     }
            
        }
  }
