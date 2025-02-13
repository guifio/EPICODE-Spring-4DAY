package com.example.controller;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.service.CloudinaryService;


//@RestController
//@RequestMapping("/files")
public class FileControllerConfig {

//	@Autowired
	CloudinaryService service;
	
//	@Autowired
	Cloudinary cloudinaryConfig;

	@PostMapping(value="/uploadUrlConfig")
	public ResponseEntity<String> uploadUrl(){
		
// 		TEST SUI PARAMETRI INSERITI NEL BEAN INIETTATO AUTOWIRED
		System.out.println("Il nome del cloud : " +cloudinaryConfig.config.cloudName);
		System.out.println("L'api key : " +cloudinaryConfig.config.apiKey);
		System.out.println("L'api secret: " +cloudinaryConfig.config.apiSecret);

		
		try {
			
			System.out.println("Upload immagine..");
			Map params1 = ObjectUtils.asMap(
			    "use_filename", true,
			    "unique_filename", false,
			    "overwrite", true
			);

			System.out.println(cloudinaryConfig.uploader().upload("https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", params1));
						
			System.out.println("Dettagli dell'immagine");
			Map params2 = ObjectUtils.asMap(
			        "quality_analysis", true
			);

			System.out.println(cloudinaryConfig.api().resource("coffee_cup", params2));
			
			return new ResponseEntity<>("Immagine inserita correttamente",HttpStatus.OK);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	
	@PostMapping(value="/uploadConfig", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces="application/json")
	public ResponseEntity<Map> upload(@RequestPart("file") MultipartFile file){
		
// 		TEST SUI PARAMETRI INSERITI NEL FILE .ENV
		System.out.println("Il nome del cloud : " +cloudinaryConfig.config.cloudName);
		System.out.println("L'api key : " +cloudinaryConfig.config.apiKey);
		System.out.println("L'api secret: " +cloudinaryConfig.config.apiSecret);

// 		TEST SUI PARAMETRI DEL FILE
		System.out.println("Dimensione file : " +file.getSize());
		System.out.println("Nome file : " +file.getOriginalFilename());
		System.out.println("Tipologia file "+file.getContentType());
		
		try {
		
			Map mappa = cloudinaryConfig.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
			
			return new ResponseEntity<>(mappa, HttpStatus.OK);
			
			// per recuperare solo l'url dell'immagine caricata
			//return mappa.get("url").toString();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
		
				
	
	}
	
	
	
	
	@PostMapping(value="/uploadConfigService", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces="application/json")
	public ResponseEntity<String> uploadService(@RequestPart("file") MultipartFile file){
		
// 		TEST SUI PARAMETRI DEL FILE
		System.out.println("Dimensione file : " +file.getSize());
		System.out.println("Nome file : " +file.getOriginalFilename());
		System.out.println("Tipologia file "+file.getContentType());
		
		try {
			
			// l'upload lo fa il service e ritorna l'url
			String url =service.uploadFile(file);
			
			return new ResponseEntity<>(url , HttpStatus.OK);
						
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}	
		
				
	
	}
	
}
