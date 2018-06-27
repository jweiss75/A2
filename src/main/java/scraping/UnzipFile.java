package scraping;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {
	
	String route ="/XBRL_Files/";
	String fileZip ="";
	
	public static void UnzipFile() {}
	
   public static void main(String[] args) throws IOException {
       System.out.println("--------------------------------------------------");
	   UnzipFile unzipfile = new UnzipFile();
	   unzipfile.setRoute("XBRL_Files/");
	   unzipfile.setFileZip("");
	   // unzipfile.serialUnzip();
	   unzipfile.setFileZip("ENAGAS.zip");
	   unzipfile.unzip();
	   System.out.println("--------------------------------------------------");
   }
     
	public void unzip() throws IOException {
    	
    	
    	// String fileZip = "compressed.zip";
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(this.route+"/"+this.fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null){
            String fileName = zipEntry.getName();
            File newFile = new File(this.route+"/" + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
    
    public  void serialUnzip() throws IOException {
    	
    	File dir = new File(this.route);
    	String[] ficheros = dir.list();
    	
    	if (ficheros == null)
    		  System.out.println("No hay ficheros en el directorio especificado");
    		else { 
    		  for (int x=0;x<ficheros.length;x++) {
    		    System.out.println(  "Descomprimiendo... | " +this.route+"/"+ficheros[x]);
    			  
    			String ficheroADescomprimir = (String) ficheros[x];
    			  
    		  	this.setFileZip(ficheroADescomprimir);
    		    this.unzip();
    		  }
    		}
    	
    	
    }

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getFileZip() {
		return fileZip;
	}

	public void setFileZip(String fileZip) {
		this.fileZip = fileZip;
	}
    
    
    
    
    
}