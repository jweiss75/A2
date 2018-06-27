
import scraping.Navigate;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

// import javax.swing.text.html.HTML;

import org.apache.commons.logging.LogFactory;
import org.junit.*;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.host.dom.Node;
import com.gargoylesoftware.htmlunit.javascript.host.dom.NodeList;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// import net.sourceforge.htmlunit.*;
import net.sourceforge.htmlunit.corejs.javascript.annotations.*;

public class Main {

	String route;

	public static void Navigate() {
	}

	public static void main(String[] args) {
		
		
		
		
		// TODO Auto-generated method stub
		Navigate navigate = new Navigate();
		navigate.setRoute("/A1/src/test/");
		
		try {
			// navigate.submittingForm("VIDRALA");
			navigate.listOfCompanies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void submittingForm(String company, String route) throws Exception {
		// *try (final WebClient webClient = new WebClient()) {
		// LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
		// "org.apache.commons.logging.impl.NoOpLog");
		
		
		System.out.println("INICIO NAVIGATE ___________________________________");
		// Get the first page
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
		
		WebClient wc = new WebClient(BrowserVersion.CHROME);

		WebClient webClient = new WebClient();
		wc.getOptions().setJavaScriptEnabled(false);
		wc.getOptions().setUseInsecureSSL(true);
		wc.setAjaxController(new NicelyResynchronizingAjaxController());

		// Boton de entidades emisoras on, agrega un boton de envío y envía
		final HtmlPage page1 = wc.getPage("https://www.cnmv.es/ipps/default.aspx?vista=2");
		HtmlRadioButtonInput radiobutton = (HtmlRadioButtonInput) page1.getElementById("wDescargas_rbTipoBusqueda_3");
		radiobutton.setDefaultChecked(true);
		// create a submit button - it doesn't work with 'input'
		HtmlElement button = (HtmlElement) page1.createElement("button");
		button.setAttribute("type", "submit");
		// append the button to the form
		HtmlElement form = (HtmlElement) page1.getFormByName("form1");
		form.appendChild(button);
		// submit the form
		HtmlPage page2 = button.click();
		wc.waitForBackgroundJavaScript(1000000);
		// System.out.println(page2.getWebResponse().getContentAsString());

		// HtmlSelect selector = (HtmlSelect)
		// page1.getElementById("wDescargas_rbTipoBusqueda_3");

		// Seleccionamos el select de empresa.
		HtmlSelect emisor = (HtmlSelect) page2.getElementById("wDescargas_drpEntidades");
		HtmlOption optionEmisor = emisor.getOptionByText(company);
		emisor.setSelectedAttribute(optionEmisor, true);
		wc.waitForBackgroundJavaScript(1000000);
		HtmlSelect periodo = (HtmlSelect) page2.getElementById("wDescargas_drpPeriodos");
		HtmlOption optionPeriodo = periodo.getOptionByValue("4");
		periodo.setSelectedAttribute(optionPeriodo, true);
		wc.waitForBackgroundJavaScript(1000000);

		System.out.println("Descargando.... | "+company+" | ");
		// System.out.println(emisor.getNodeValue().toString());
		

		HtmlInput buscar = (HtmlInput) page2.getElementById("wDescargas_btnBuscar");
		HtmlPage page3 = buscar.click();
		wc.waitForBackgroundJavaScript(1000000);

		// System.out.println(page3.getWebResponse().getContentAsString());

		// Descargar

		HtmlSubmitInput descargar = (HtmlSubmitInput) page3.getElementById("wDescargas_Listado_btnDescargar");
		wc.waitForBackgroundJavaScript(1000000);
		System.out.println(descargar.asText());

		UnexpectedPage up = descargar.click();
		wc.waitForBackgroundJavaScript(1000000);

		InputStream in = up.getInputStream();
		System.out.println(up.getWebResponse().getContentType());
		FileOutputStream output = new FileOutputStream(new File(route + "/" + company + ".zip"));
		int lenght;
		byte[] buffer = new byte[1024];
		while ((lenght = in.read(buffer)) > 0) {
			output.write(buffer, 0, lenght);
		}
		output.flush();
		output.close();
		in.close();

		System.out.println("END OF DOWNLOAD");

	}

	public void listOfCompanies() throws Exception {

		System.out.println("STARTING READING FULL LIST");
		// Get the first page

		WebClient wc = new WebClient(BrowserVersion.CHROME);

		WebClient webClient = new WebClient();
		wc.getOptions().setJavaScriptEnabled(false);
		wc.getOptions().setUseInsecureSSL(true);
		wc.setAjaxController(new NicelyResynchronizingAjaxController());
		// Boton de entidades emisoras on, agrega un boton de envío y envía
		final HtmlPage page1 = wc.getPage("https://www.cnmv.es/ipps/default.aspx?vista=2");
		HtmlRadioButtonInput radiobutton = (HtmlRadioButtonInput) page1.getElementById("wDescargas_rbTipoBusqueda_3");
		radiobutton.setDefaultChecked(true);
		// create a submit button - it doesn't work with 'input'
		HtmlElement button = (HtmlElement) page1.createElement("button");
		button.setAttribute("type", "submit");
		// append the button to the form
		HtmlElement form = (HtmlElement) page1.getFormByName("form1");
		form.appendChild(button);
		// submit the form
		HtmlPage page2 = button.click();
		wc.waitForBackgroundJavaScript(1000000);
		// System.out.println(page2.getWebResponse().getContentAsString());
		HtmlSelect selectOfCompanies = (HtmlSelect) page2.getElementById("wDescargas_drpEntidades");

		// System.out.println(selectOfCompanies.getTextContent());
		Iterator<DomElement> optionIter = null;
		int counter=0;
		for (optionIter = selectOfCompanies.getChildElements().iterator(); optionIter.hasNext();) {
            counter=counter+1;
            System.out.println("_");
            System.out.println("#"+counter);
			String company = optionIter.next().getTextContent();
			try {
				this.submittingForm(company, this.route);
			} catch (Exception e) {

				System.out.println("ERROR EN CIF:" + optionIter.next().getNodeValue() + " / NOMBRE:"
						+ optionIter.next().getTextContent());
				
				System.out.println(e.getMessage()+e.getLocalizedMessage());;
			}
		}

	}
	
	
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

}
