package Bd;

/*
 * PruebaMySQL.java
 *
 * Programa de prueba para conexión a una base de datos de MySQL.
 * Presupone que el servidor de base de datos está arrancado, disponible,
 * en el puerto por defecto.
 * El usuario y password de conexión con la base de datos debe cambiarse.
 * En la base de datos se supone que hay una base de datos llamada prueba y que
 * tiene una tabla persona con tres campos, de esta manera:
 * mysql> create database prueba;
 * mysql> use prueba;
 * mysql> create table persona (id smallint auto_increment, nombre varchar(60), 
 *      nacimiento date, primary key(id)); 
 */



import java.sql.*;
import java.sql.PreparedStatement;

/**
 * Clase de prueba de conexión con una base de datos MySQL
 */
public class DBManage {
    
    Connection con = null;
    
    /** 
     * Crea una instancia de la clase MySQL y realiza todo el código 
     * de conexión, consulta y muestra de resultados.
     */
    
    public void cierra(){
         try
    {
        this.con.close();
     }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    }
    
    public void closeConnection(Connection con){
        
         try
    {
        con.close();
     }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
      e.printStackTrace();
    }     
        
        
    }
    
    
    
    
    
    
    public void insercion(Connection con1, String empresa, String estado, String parametro, String valor, String fecha){
        System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
      try
    {
      
      /*con1.setAutoCommit(false);
      Statement st = con1.createStatement();
      String query = "Insert into monster(empresa, parametro, valor, fecha) values('u','3','1','2');"; 
      // note that i'm leaving "date_created" out of this insert statement
      st.executeUpdate(query);
      con1.commit();
        // con1.close();*/
      
      String query = "Insert into monster(empresa, estado, parametro, valor, fecha) values (?,?,?,?,?);";
      
      PreparedStatement preparedStmt = con1.prepareStatement(query);
      preparedStmt.setString (1, empresa);
       preparedStmt.setString  (2,estado);
      preparedStmt.setString (3, parametro);
      preparedStmt.setString  (4,valor); 
      preparedStmt.setString  (5,fecha);
      preparedStmt.executeUpdate();
      System.out.println(query);
      System.out.println( preparedStmt);
      
      
    }
    catch (SQLException e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
      e.printStackTrace();
      e.getLocalizedMessage();
    }

      
      // execute the preparedstatement
      
        
    }    
    
    
    public Connection conection() {
        Connection conection=null;
        try
        {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        System.out.println("Experando por la conexión...");
        
        //conection = DriverManager.getConnection ("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7243941?characterEncoding=utf-8&","sql7243941", "npbF5u7qUJ");
        
        conection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/monster?useSSL=false&characterEncoding=Latin1&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","monster", "electroforesis");
        
        System.out.println("Haciendo la consulta...");   
   
         }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
             System.err.println("Got an exception!");
              System.err.println(e.getMessage());
             
        }
        
        return conection;
    }
    
    
    
    public void PruebaMySQL() 
    {
        // Se mete todo en un try por los posibles errores de MySQL
        try
        {
            // Se registra el Driver de MySQL
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            // Se obtiene una conexión con la base de datos. Hay que
            // cambiar el usuario "root" y la clave "la_clave" por las
            // adecuadas a la base de datos que estemos usando.
             System.out.println("Experando por la conexión...");
            Connection conexion = DriverManager.getConnection (
                // "jdbc:mysql://localhost/prueba","root", "la_clave");
           // "jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7243941?characterEncoding=utf-8","sql7243941", "npbF5u7qUJ");
            "jdbc:mysql://db4free.net:3306/monster?useSSL=false&characterEncoding=Latin1","monster", "electroforesis");
            // Se crea un Statement, para realizar la consulta
            System.out.println("Haciendo la consulta...");
            Statement s = conexion.createStatement();
            
            // Se realiza la consulta. Los resultados se guardan en el 
            // ResultSet rs
            System.out.println("Haciendo la consulta...");
            ResultSet rs = s.executeQuery ("select * from monster");
            System.out.println("Buscamos el result set");
            // Se recorre el ResultSet, mostrando por pantalla los resultados.
            
            System.out.println ("------------------------------------------");
            while (rs.next())
            {
                System.out.println("|" + rs.getInt ("Id") + "|" + rs.getString (2)+ 
                    "|" + rs.getString(2) + "|");
            }
            
            // Se cierra la conexión con la base de datos.
            System.out.println("Cerrando conexión...");
            conexion.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    
      public Connection getCon()
        {
                return con;
        }
        public void setCon(Connection con)
        {
                this.con = con;
        }
    
    
    /**
     * Método principal, instancia una clase PruebaMySQL
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        DBManage db = new DBManage();
        System.out.println("Conectando...");
        
        Connection con1= db.conection();
        db.insercion(con1,"E","e","r","y","Z");
        db.closeConnection(con1);
        db.PruebaMySQL();
    }
    
}
