package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Kisi;
import service.KisiService;

//burası localhosta ne girilirse ona göre nereye gitmesi gerektiğini belirleyen class
@RestController
@RequestMapping("/kisiler") // reacta geçince
////frontend yayın portu
@CrossOrigin(origins = "http://localhost:8081") /// bunu reacta geçince
public class KisiController {

	public KisiService kisiService;

	@Autowired // biz yazınca değil lazım olduğunda oluştur. alttaki gibi yaparak sıkı sıkı
	// bağlanmalarını engelledik, yeri geldiğinde birbirinden bağımsız olsun diye.
	// dependency injection yaptık, controller ın içine service yi injekte ettik
	public KisiController(KisiService kisiService) {
		this.kisiService = kisiService;
	}

	@GetMapping // kisiservice nin içindeki tumkisilerigetir metoduna git
	public List<Kisi> kisileriGetir() {
		return kisiService.tumKisileriGetir();
	}

	@PostMapping(path = "/ekle") // localhost:8080/ekle dersek chrome da gözükür
	public Kisi yeniKisiEkle(@RequestBody Kisi kisi) {
		return kisiService.kisiEkle(kisi);
	}

	@GetMapping(path = "/ara/{id}")
	public Optional<Kisi> idIleKisiListele(@PathVariable Integer id) {
		return kisiService.idIleKisiGetir(id);
	}

    // RequestBody, pathVariable kullanmazsak yukarıdaki gibi kolay adres yazamayız (/kisiler/ekle gibi). 
    // @RequestBody:Açıklama bize isteğini teslim almak için izin verir. 
	// Daha sonra onu bir String olarak döndürebilir 
	// veya bir Plain Old Java Object (POJO) hale getirebiliriz .

	@DeleteMapping(path = "/sil/{id}")
	public String idIleKisiSil(@PathVariable Integer id) {
		return kisiService.idIleKisiSil(id);
	}

	@PutMapping(path = "/guncelle")
	public Kisi guncelle(@RequestBody Kisi yeniKisi) {
		return kisiService.kisiGuncelle(yeniKisi);
	}

	@PatchMapping(path = "/yenile/{id}")
	public Kisi idIleKismiGuncelle(@PathVariable Integer id, @Validated @RequestBody Kisi yeniKisi) {
		return kisiService.idIleKismiGuncelle(id, yeniKisi);
	}

}
