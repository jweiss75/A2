package structure;

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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XMLGolum {
    public String     entidad;
    public String     anualidad;
    String            tagname       = "*";
    String            sourcefile    = "schemas/ipp_en_2016-06-01-label.xml";
    List<Vector>      matrizrefyear = null;
    public Connection con           = null;
    public List<Vector> refyear     =null;

    public static void main(String[] args) throws Exception {
        
       // Instancia
        System.out.println("Instanciando Golum");
        XMLGolum monster = new XMLGolum();
        monster.setConnection();
        // monster.ClearTable("esquema");
        monster.ClearTable("golumValues");
        NodeList documento = monster.nodosDocumeto();
        // monster.searchTags(documento, "BalanceConsolidado" );
        
        
        monster.setSourceFile("XBRL_Files/2017022463.xbrl");
        documento = monster.nodosDocumeto();        
        monster.anualidad = monster.anualidad(documento);
        monster.entidad=monster.BuscaEntidad(documento,"entity");
        System.out.println(monster.entidad);
        monster.searchValues(documento, "BalanceConsolidado" );
        // monster.closeCon();
        
        
        monster.setSourceFile("XBRL_Files/2017023504.xbrl");
        documento = monster.nodosDocumeto();        
        monster.anualidad = monster.anualidad(documento);
        monster.entidad=monster.BuscaEntidad(documento,"entity");
        System.out.println(monster.entidad);
        monster.searchValues(documento, "BalanceConsolidado" );
        
        
        
          monster.setSourceFile("XBRL_Files/2017023616.xbrl");
        documento = monster.nodosDocumeto();        
        monster.anualidad = monster.anualidad(documento);
        monster.entidad=monster.BuscaEntidad(documento,"entity");
        System.out.println(monster.entidad);
        monster.searchValues(documento, "BalanceConsolidado" );
        
        
        
        monster.setSourceFile("XBRL_Files/2018023181.xbrl");
        documento = monster.nodosDocumeto();        
        monster.anualidad = monster.anualidad(documento);
        monster.entidad=monster.BuscaEntidad(documento,"entity");
        System.out.println(monster.entidad);
        monster.searchValues(documento, "BalanceConsolidado" );
        
        
        monster.setSourceFile("XBRL_Files/2018023181.xbrl");
        documento = monster.nodosDocumeto();        
        monster.anualidad = monster.anualidad(documento);
        monster.entidad=monster.BuscaEntidad(documento,"entity");
        System.out.println(monster.entidad);
        monster.searchValues(documento, "BalanceConsolidado" );
       
        monster.closeCon();
        
    }


    public String BuscaEntidad (NodeList entries, String tagname) throws Exception {
        
          String buscaentidad="";
          List<Element> listadetags = new ArrayList<Element>();
          System.out.print("Buscando Entidad -> longitud del nodo:" + entries.getLength()+ " :");
        
         try {
          
            for (int i = 0; i < entries.getLength(); i++) {
                
                  Element element = (Element)entries.item(i);
                  String nombre = element.getNodeName();
                  if (element.getChildNodes().getLength()>1){  
               // System.out.println(element.getNodeName()+":"+ element.getChildNodes().getLength());
                  }
                
                if (nombre.toLowerCase().indexOf(tagname.toLowerCase()) > -1 || tagname == "*") {
                    
                    
                    
                    for (int y=1; y<element.getChildNodes().getLength()-1;y++)   {
                         Node nodo = element.getChildNodes().item(y);
                         if(nodo.getNodeName().toLowerCase().indexOf("identifier")>-1){
                               buscaentidad= nodo.getTextContent();
                         
                        }
                     }       

                    
                }
                 
            }
                  
         }
         catch (Exception e){ buscaentidad="error";}
         System.out.println("Empresa: "+buscaentidad);
         return buscaentidad; 
        
         
        
}

    public List<Element> searchTags(NodeList entries, String tagname) throws Exception {
        System.out.println("SERAHCING TAGS");
        List<Element> listadetags = new ArrayList<Element>();
        System.out.println("longitud del nodo:" + entries.getLength());
        System.out.println("longitud del nodo:" + entries.getLength());
        try {
            for (int i = 0; i < entries.getLength(); i++) {
               
               Node element = entries.item(i);
                
                            System.out.println(i+" \r");
                            NamedNodeMap atributos = element.getAttributes();
                            String contenido = element.getTextContent();
                           // System.out.println(atributos);
                             
                            try{
                            String atribute = atributos.getNamedItem("xlink:label").getNodeValue();
                             if (contenido.trim()=="" && atribute.length()<6){
                                                        i++;
                                                        element = entries.item(i);
                                                        contenido = element.getTextContent();
                                                        System.out.println((100*i/entries.getLength())+"% ^"+atribute+"^^^:"+contenido+" \r");
                                                        this.insercion2(atribute,"-"+contenido);
                                                 } 
                            }catch(Exception e){}

          

           

            }
        } catch (Exception e) {

            System.out.println("MONSTER  ERROR:" + e.getMessage());
            e.printStackTrace();
        }
        
        return listadetags;

    }


 public List<Element> searchValues(NodeList entries, String tagname) throws Exception {
     
        System.out.println("BUSCANDO VALORES");
        List<Element> listadetags = new ArrayList<Element>();
        System.out.println("longitud del nodo:" + entries.getLength());
        
        try {
            for (int i = 0; i < entries.getLength(); i++) {
               
               Node element = entries.item(i);
                             
                // que el elemento empieze por ipp_ge:I1161
                
                if (element.getNodeName().toLowerCase().indexOf("ipp_ge:")>-1){
                    
                    String nombrenodo=element.getNodeName()+"00000000000000";
                    nombrenodo= nombrenodo.substring(7,12);
                     Double valornodo = 0.0;
                     String per = "pre";
                   
 try {               valornodo = Double.parseDouble(element.getTextContent());
                     // String decimales = element.getAttributes().getNamedItem("decimals").getNodeValue();
                    /*int decimales = Integer.parseInt(element.getAttributes().getNamedItem("decimals").getNodeValue());*/
                   double decimales = Double.parseDouble(element.getAttributes().getNamedItem("decimals").getNodeValue());//;.getNodeName();
                    
                    
                   if (element.getAttributes().getNamedItem("contextRef").getNodeValue().indexOf("Icur")>-1){
                       
                       per = "cur";
                       
                       
                   }
                   
                   
                    
                    
                    if (decimales>0){
                        
                        valornodo = valornodo/(Math.pow(10,decimales));
                        
                    }
                    
                    if (valornodo<1000000000){
                    
                    System.out.println((100*i/entries.getLength())+"% |NAME: "+nombrenodo+" PER: "+per+" VALUE:"+ valornodo+" DECIMALES: "+decimales+"\r");
                    insercionGolum(nombrenodo,per,valornodo.toString());
                     }

                                 
                    } catch (Exception e) { /*e.printStackTrace();*/}
                    
                    
                }
                
                
                
                       /*     System.out.print(".");
                            NamedNodeMap atributos = element.getAttributes();
                            String contenido = element.getTextContent();
                           // System.out.println(atributos);
                             
                            try{
                            String atribute = atributos.getNamedItem("xlink:label").getNodeValue();
                             if (contenido.trim()=="" && atribute.length()<6){
                                                        i++;
                                                        element = entries.item(i);
                                                        contenido = element.getTextContent();
                                                        System.out.println("^^^"+atribute+"^^^:"+contenido);
                                                        this.insercion2(atribute,"-"+contenido);
                                                 } 
                          }catch(Exception e){}*/

          

           

            }
        } catch (Exception e) {

            System.out.println("MONSTER  ERROR:" + e.getMessage());
            e.printStackTrace();
        }
        
        return listadetags;
     
 }



/////////////////////////////////////////////////////////// CUBO DE ANUALIDAD ///////////////////////////////

    public String anualidad(NodeList documento){
         
        int ano =0;
        
        
            for (int i = 0; i < documento.getLength(); i++) {
                
                
            try {   
               Node element = documento.item(i);
               
            if (element.getNodeName().indexOf("instant")>-1 ){
                
                int ano1 = Integer.parseInt(element.getTextContent().substring(0,4));
                
                if (ano1>ano){ano = ano1;}
                
                System.out.println("BUSCANDO ANUALIDAD: "+element.getTextContent()+" \r");
            }
             
            
        } catch(Exception e){}


            }

    
        
        
         System.out.println("BUSCANDO ANUALIDAD: "+ano);
        return Integer.toString(ano);
    }
    
    public String matchYearRef(String ref, String contextoConsulta) {
        
        // System.out.println("* this is matchYearRef");
        String year = "0000";
        String contexto = "sinContexto";

       // System.out.println("//" + this.refyear.size());
      
        for (Vector referencia:this.refyear) {
            
            
                   
                    
            
            if (ref.toString().equals(referencia.get(0).toString())) {
                
                String cadena1 = referencia.get(2).toString();
                contexto = cadena1.substring(cadena1.indexOf(":")+1,cadena1.lastIndexOf(":"));
                // System.out.println("ggggggggggggg"+contexto+"&&&&&"+contextoConsulta+"ggggg"+contexto.equals(contextoConsulta));
                if (contexto.equals(contextoConsulta)){
                year = cadena1.substring(cadena1.lastIndexOf(":")+1,cadena1.length());
                }

                // System.out.println(ref+"-----"+referencia.get(0).toString());
            }
         
        }

        
        return year;

    }
    
    
    
    public String matchContextRef(String ref) {
        
        // System.out.println("* this is matchYearRef");
        String year = "2018";
        

        // System.out.println("//" + this.refyear.size());
      
        for (Vector referencia:this.refyear) {
            
            
                   
                    
            
            if (ref.toString().equals(referencia.get(0).toString())) {
                
                String cadena1 = referencia.get(2).toString();
                
                year = cadena1.substring(cadena1.indexOf(":")+1,cadena1.lastIndexOf(":"));
                
                // System.out.println(ref+"-----"+referencia.get(0).toString());
            }
         
        }

        
        return year;

    }
    
    
    


    public List<Vector> anualidadList(NodeList entries, String filtro) throws Exception {
        // Lo primero detectar el sistema de esquema de 
        
        
        System.out.println("Generando su cubo de fechas....");
        List<Element> listadetags = new ArrayList<Element>();
        List<Vector> lista1 = new ArrayList<Vector>(); // Aqui guardaremos el array.
        int tipo;
    try {
          
            for (int i = 0; i < entries.getLength(); i++) {
                
                  Element element = (Element)entries.item(i);
                 
                  String nombre = element.getNodeName();

                  if (element.getChildNodes().getLength()>1){  
                     // System.out.println(element.getNodeName()+":"+ element.getChildNodes().getLength());
                  }
                   
                if (nombre.toLowerCase().indexOf("context") > -1 &&  element.getAttributes().getNamedItem("id").toString().length()>5) {
                    
                    // sacar el id para analizarlos 
                    
                    String refcontext= element.getAttribute("id");   
                    // System.out.println( "#######  " + refcontext + "  ---    "+ refcontext.indexOf("Icur"));
                    
                    if (refcontext.indexOf("Icur")>-1 /*&& refcontext.indexOf(filtro) > -1*/){
                        System.out.println( "####### " + refcontext + " TIPO1");
                        listadetags.add(element);
                        tipo = 1; // XBRL moderno > 2016  // GOLUM SABE QUE EL INFORME ES MAYOR DE 2016
                    }
                    else if(refcontext.indexOf("Icur")==-1) 
                    
                    {
                        
                        tipo = 2; // XBRL antiguo < 2016 // GOLUM SABE QUE EL INFORME ES MENOR DE 2016
                        listadetags.add(element);
                    };
                    
                   
                    
                }
              
            }
        
         }
         catch (Exception e){ System.out.println(e.getMessage());}
        
        

        // Search the contexts and find their references.
       
        List<Element> listaElementosContexto = listadetags;
        // System.out.println("!!!!!!!!!!!!!!!!!!!!!!" +  listaElementosContexto.size());
        
        for (Element contexto : listaElementosContexto) {
            // find the context regerence.
            // System.out.println(contexto.getAttributes().getNamedItem("id"));
            String id = contexto.getAttributes().getNamedItem("id").getNodeValue();
           /* System.out.println("******************************");
            System.out.println("Contexto con referencia: " + id);
            System.out.println("******************************");*/
            
            NodeList contextincludedtags = contexto.getChildNodes();

            for (int i = 0; i < contextincludedtags.getLength(); i++) {

                // We look for the <xrbl:period tag> but the prefix could be different
                String str2 = "period";
                // System.out.println(contextincludedtags.item(i).getNodeName().toLowerCase().contains(str2.toLowerCase()));

                if (contextincludedtags.item(i).getNodeName().toLowerCase().contains(str2.toLowerCase()) == true) {
                    NodeList nodoPerido = contextincludedtags.item(i).getChildNodes();
                    /*System.out.print("Period found: " + contextincludedtags.item(i).getNodeName());*/
                    for (int j = 0; j < nodoPerido.getLength(); j++) {
                        String valorperiodo = nodoPerido.item(j).getNodeName() + ":" + nodoPerido.item(j).getTextContent();
                       
                        if (valorperiodo.length()>20 ){
                        
                        Vector vec = new Vector();
                        vec.add(id);
                        vec.add(contextincludedtags.item(i).getNodeName());
                        vec.add(valorperiodo);
                        lista1.add(vec);
                        
                         System.out.println("|"+id+"|"+contextincludedtags.item(i).getNodeName()+"|"+valorperiodo);
                        
                    }


                    }

                }


            }

        }

        
       for (Vector linea:lista1){
           
        // System.out.println("|"+linea.get(0)+"|"+linea.get(1)+"|"+linea.get(2)+"|");       
       }
        return lista1;

    }











    /////////////////////////////////// LECTURA DEL DOCUMENTO /////////////////////////////

    public NodeList nodosDocumeto() throws Exception {

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(this.sourcefile);
            FileInputStream fis = new FileInputStream(file);
            Document doc = db.parse(fis);
            NodeList entries = doc.getElementsByTagName("*");
            return entries;
        } catch (Exception e) {

            System.out.println("MONSTER  ERROR:" + e.getMessage());
            e.printStackTrace();
            return null;
        }


    }



    ////////////////////////////////// INSERCIONES DE LA BASE DE DATOS ///////////////////////	
    
    public void insercion(String tabla ,String empresa, String estado, String parametro, String valor, String fecha, String fecha1) {
        System.out.print(".");
        try {

            /*con1.setAutoCommit(false);
            Statement st = con1.createStatement();
            String query = "Insert into monster(empresa, parametro, valor, fecha) values('u','3','1','2');"; 
            // note that i'm leaving "date_created" out of this insert statement
            st.executeUpdate(query);
            con1.commit();
              // con1.close();*/

            String query = "Insert into "+tabla+"(empresa, estado, parametro, valor, fecha, fecha1) values (?,?,?,?,?,?);";
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(query);
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, empresa);
            preparedStmt.setString(2, estado);
            preparedStmt.setString(3, parametro);
            preparedStmt.setString(4, valor);
            preparedStmt.setString(5, fecha);
            preparedStmt.setString(6, fecha1);
            preparedStmt.executeUpdate();
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(preparedStmt);
            
            


        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        // execute the preparedstatement


    }



public void insercion2(String referencia, String concepto) {
        System.out.print(".");
        try {

            /*con1.setAutoCommit(false);
            Statement st = con1.createStatement();
            String query = "Insert into monster(empresa, parametro, valor, fecha) values('u','3','1','2');"; 
            // note that i'm leaving "date_created" out of this insert statement
            st.executeUpdate(query);
            con1.commit();
              // con1.close();*/

            String query = "Insert into esquema(referencia, concepto) values (?,?);";
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(query);
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, referencia);
            preparedStmt.setString(2, concepto);
           
            preparedStmt.executeUpdate();
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(preparedStmt);


        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        // execute the preparedstatement


    }


public void insercionGolum(String ref, String per, String value) {
        System.out.print(".");
        try {

            /*con1.setAutoCommit(false);
            Statement st = con1.createStatement();
            String query = "Insert into monster(empresa, parametro, valor, fecha) values('u','3','1','2');"; 
            // note that i'm leaving "date_created" out of this insert statement
            st.executeUpdate(query);
            con1.commit();
              // con1.close();*/

            String query = "Insert into golumValues(entidad, ref, ref2,  value, fecha) values ('"+this.entidad+"',?,?,?,?);";
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(query);
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setString(1, ref);
            preparedStmt.setString(2, per);
            preparedStmt.setString(3, value);
            preparedStmt.setString(4, this.anualidad);
            preparedStmt.executeUpdate();
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(preparedStmt);


        } catch (SQLException e) {
            System.err.print("Got an exception! :");
            System.err.println(e.getMessage());
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        // execute the preparedstatement


    }


///////////////////////////////DELETE TABLE /////////////

public void ClearTable(String tabla) {
        System.out.print(".");
        try {

            /*con1.setAutoCommit(false);
            Statement st = con1.createStatement();
            String query = "Insert into monster(empresa, parametro, valor, fecha) values('u','3','1','2');"; 
            // note that i'm leaving "date_created" out of this insert statement
            st.executeUpdate(query);
            con1.commit();
              // con1.close();*/

            String query = "delete from "+tabla+";";
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(query);
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
//            preparedStmt.setString(1, referencia);
//            preparedStmt.setString(2, concepto);
           
            preparedStmt.executeUpdate();
            // System.out.println(empresa + estado + parametro + valor + fecha);
            // System.out.println(preparedStmt);


        } catch (SQLException e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        // execute the preparedstatement


    }

    ////////////////////////////////// CONEXIONES A LA BASE DE DATOS ////////////////////////	


    public void closeCon() {
        try {
            this.con.close();
            System.out.println("Closing the connection of this Monster....");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            e.getMessage();
        }

    }

    public void setConnection() {
        Connection conection = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            System.out.println("Experando la conexión con la base de datos...");

            //conection = DriverManager.getConnection ("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7243941?characterEncoding=utf-8","sql7243941", "npbF5u7qUJ");

            conection =
                        DriverManager.getConnection("jdbc:mysql://db4free.net/monster?useSSL=false&characterEncoding=Latin1&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","monster", "electroforesis");
                                                    


// "jdbc:mysql://db4free.net:3306/monster?useSSL=false&characterEncoding=Latin1", "monster", "electroforesis"
            System.out.println("Conexión Correcta");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.err.println("NO PUDE CONECTAR!");
            System.err.println(e.getMessage());

        }

        this.con = conection;
    }


    ////////////////////////// SETTERS Y GETTERS /////////////////////////////	


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
