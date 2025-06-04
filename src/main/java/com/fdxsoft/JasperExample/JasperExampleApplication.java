package com.fdxsoft.JasperExample;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@SpringBootApplication
public class JasperExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasperExampleApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init() {
		return args -> {
			String pdfDestination = "src" + File.separator +
									"main" + File.separator + 
									"resources" + File.separator + 
									"static" + File.separator +
									"pdf" + File.separator +
									"voucher.pdf";
			String pdfOrigin = "src" + File.separator +
								"main" + File.separator + 
								"resources" + File.separator + 
								"templates" + File.separator +
								"reports" + File.separator + 
								"voucher.jrxml";
			
			Map<String, Object> params = new HashMap<String, Object>();
			LocalDateTime hoy = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
			
			
			params.put("voucher_id", "0000001234");
			params.put("current_date", formatter.format(hoy));
			params.put("amount_payed", NumberFormat.getCurrencyInstance().format(new BigDecimal(30500.55)));
			params.put("payment_method", "CASH");
			params.put("parent_name", "Federico Martinez");
			params.put("child_name", "Valeria Martinez");
			params.put("img_path", "src/main/resources/static/img/");
			
			try {
				//Ahora si procedemos a crear el reporte
				
				JasperReport report = JasperCompileManager.compileReport(pdfOrigin);
				JasperPrint print =  JasperFillManager.fillReport(report, params, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(print, pdfDestination);
				System.out.println("Report Created Successfully!");
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			
			
		};
	}
}
