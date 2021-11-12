package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestBody;

import model.Kisi;
import repository.KisiRepository;

@Service
public class KisiService {

	public static KisiRepository kisiRepository;

	@Autowired
	// Spring buna ihtiyaç duyduğunda (çalıştırıldığında, runtime), oluşturuyor,
	// normalde boşuna çalışmasın diye alttaki
	public KisiService(KisiRepository kisiRepository) {
		KisiService.kisiRepository = kisiRepository;
	}

	public List<Kisi> tumKisileriGetir() {
		return kisiRepository.findAll();
	}

	// Veritabanina Kisi ekleyen servis metodu
	public Kisi kisiEkle(Kisi kisi) {
		return kisiRepository.save(kisi);// repos. sayesinde database e depoluyor
	}

//	 id ile kisi getiren servis metotu
	public Optional<Kisi> idIleKisiGetir(Integer id) {
		return kisiRepository.findById(id);
	}

	public String idIleKisiSil(Integer id) {// şu id li kişi silinmiştir dedirteceğiz o yüzden String

		if (kisiRepository.findById(id) == null) {
			throw new EmptyResultDataAccessException(id);
		}
		kisiRepository.deleteById(id);
		return id + " li kisi silindi";
	}

	//  PUT
    //	PUT , kaynağın var olup olmadığını kontrol etmek içindir, ardından güncellemek ,
    //	aksi takdirde yeni kaynak oluşturmak içindir. PATCH her zaman sadece bir kaynağı güncellemek içindir.
    //  PUT için, id belirterek yazarız, bu id li kişi yoksa yenisini oluşturur
    //	  "id":77,
    //    "ad": "xxx",
    //    "soyad": "ver",
    //    "yas": 66}
	
	public Kisi kisiGuncelle(Kisi guncelKisi) {
		// değiştirmek istediğimiz kişinin idsini yazmalıyız, istediğimiz id li kişi
		// yoksa yeni kişi gibi ekliyor
		// kisiRepository.findById(guncelKisi.getId());

		return kisiRepository.save(guncelKisi);
	}

	// PATCH
	public Kisi idIleKismiGuncelle(Integer id, Kisi yeniKisi) {

		Kisi eskiKisi = kisiRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException(id + " li kisi bulunamadi"));
		// alıcı nesnenin durumu nedeniyle yapılan çağrının olağandışı olması durumunda
		// atılacak istisnadır.
		if (yeniKisi.getAd() != null) {
			eskiKisi.setAd(yeniKisi.getAd());
		}

		if (yeniKisi.getSoyad() != null) {
			eskiKisi.setSoyad(yeniKisi.getSoyad());
		}

		if (yeniKisi.getYas() != 0) {
			eskiKisi.setYas(yeniKisi.getYas());
		}

		return kisiRepository.save(eskiKisi);
	}

}