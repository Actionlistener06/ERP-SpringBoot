package com.patika.shoppingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoppingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingAppApplication.class, args);
	}

}
//03.08.2023
//Customer product stok ve productlarin fiyati
//musterilerin siparileri var siparis sonucunda olusan fatura olsun
//update stock update price
//Customer aynisi
//sioparis versin siparis o anki fiyatini tutmamiz lazim
//siparis durumlari enum onay bekliyor onaylandi kargoda default onay beklemekte
//siparis guncelleme endpointi
//siparis onaylandiginda fatura olustur
//faturada urun bilgisi fiyat
//Ayar tablosu key value default olarak veritabani KDV orani Key KDV Value
//Siparis onaylandiginda faturaya kdv ekle
//fiyata kdv dahil mi degil mi boolean tut
//dahil degilse kdv hesapla ve ekle
//onaylanma asamasinda da stok kontrolu