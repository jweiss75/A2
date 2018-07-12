import scraping.*;
import Bd.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;

    
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
// test de capacidad.

public class PHM {


    public static void main(String[] args) {

        // TODO Auto-generated method stub
        System.out.println("EL SUEÑO DE LA RAZON PRODUCE MONSTRUOS...");
        Navigate navigate = new Navigate();
        navigate.setRoute("XBRL_Files/");
        UnzipFile unzip = new UnzipFile();
        unzip.setRoute("XBRL_Files/");
        System.out.println("Ruta de navegación-->" + navigate.getRoute());
         try {
            
            List<String> listacotizadas= readData();             
            for (String cotizada:listacotizadas){
                
               System.out.println("BUSCANDO A .... "+ cotizada);   
               navigate.submittingForm(cotizada, navigate.getRoute());
               System.out.println("DESCOMPRIMIENDO A .... "+ cotizada);   
               unzip.setFileZip(cotizada+".zip");
               unzip.unzip();
                 
            }
            
            System.out.println("Los límites, como el miedo, son a menudo una ilusión.");
            
            serialBD();
            
         
            
            
            


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }



    public static void serialBD() throws IOException {
try

                    {
        System.out.print(" :))) Iniciando generación masiva de de la base de datos");
        File dir = new File("XBRL_Files/");
        String[] ficheros = dir.list();
        DBManage inserciondb = new DBManage();
        XMLHorror monster = new XMLHorror();
        monster.setConnection();
       
        for (String fichero : ficheros) {
            
            
            int j = fichero.lastIndexOf(".");
            System.out.println("encontrado..."+fichero+" con extensión " + fichero.substring(j + 1));
            
            
            if (j > 0) {
                String extension = fichero.substring(j + 1);
                 
                if (extension.equals("xbrl") == true) {
                    
                    System.out.print("Procesando en DB: " + fichero);
                    
                    monster.setSourceFile("XBRL_Files/" + fichero);
                    monster.setTagName("LegalNameValue");
                   
                    NodeList documento = monster.nodosDocumeto();
                    System.out.println("Cubo de fechas.....");
                    monster.refyear = monster.anualidadList(documento,"filtro");
                    System.out.println("Entidad.....");
                    monster.entidad= monster.BuscaEntidad(documento, "Entity" );
                    /*monster.searchTags(documento, "BalanceConsolidado" );*/
                    monster.searchTags(documento, "CuentaPerdidasGananciasConsolidada" );
                    
                    
                    
                    

                }

           
            }
            
            // monster.closeCon();
        }
} catch (Exception e) {
                        
                        e.printStackTrace();
                    }

    }

    class EvaluaExtension implements FilenameFilter {

        public boolean accept(File dir, String extension) {
            return dir.getName().endsWith(extension);
        }
    }


/////////////////////////// LISTA DE COTIZADAS /////////////////////////////

public static List<String> readData() throws IOException { 
    int count = 0;
    String file = "ListedCompanies/IBEX.csv";
    List<String> content = new ArrayList<>();
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line = "";
        while ((line = br.readLine()) != null) {
            content.add(line);
        }
    } catch (FileNotFoundException e) {
      //Some error logging
    }
    return content;
}



}
