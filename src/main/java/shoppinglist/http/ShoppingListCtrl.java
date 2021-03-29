/*
 * ShoppingListCtrl.java
 *
 * Created on Mar 22, 2021, 01.12
 */
package shoppinglist.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shoppinglist.entity.DaftarBelanja;
import shoppinglist.entity.DaftarBelanjaDetil;
import shoppinglist.service.ShoppingListService;
import shoppinglist.repository.DaftarBelanjaRepo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author irfin
 */
@RestController
public class ShoppingListCtrl
{
    @Autowired
    private ShoppingListService service;
    private DaftarBelanjaRepo repository;

    /**
     * Mengembalikan daftar objek DaftarBelanja utk pengaksesan HTTP GET.
     *
     * @return
     */
    @GetMapping
    public Iterable<DaftarBelanja> getAll()
    {
        return service.getAllData();
    }

    //Membaca sebuah objek DaftarBelanja berdasarkan ID.
    @GetMapping("/shoppinglist/get/{id}")
    public Optional<DaftarBelanja> getByID(@PathVariable("id") long id)
    {
        return repository.findById(id);
    }

    //Mencari daftar DaftarBelanja berdasarkan kemiripan string judul yg diberikan.
    @GetMapping("/shoppinglist/get/{judul}")
    public Iterable<DaftarBelanja> getByJudul(@RequestParam("judul") String judul)
    {
        try {
            List<DaftarBelanja> db = new ArrayList<DaftarBelanja>();
            repository.findByTitleContaining(judul).forEach(db::add);
            if (db.isEmpty()) {
                return (Iterable<DaftarBelanja>) new ResponseEntity<DaftarBelanja>(HttpStatus.NO_CONTENT);
            }
            return (Iterable<DaftarBelanja>) new ResponseEntity<DaftarBelanja>(HttpStatus.OK);
        } catch (Exception e) {
            return (Iterable<DaftarBelanja>) new ResponseEntity<DaftarBelanja>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Menyimpan sebuah objek DaftarBelanja ke tabel database.
    @PostMapping("shoppinglist/insert")
    public ResponseEntity<DaftarBelanja> insertList(@RequestBody DaftarBelanja db){
        try{
            DaftarBelanja DB = repository.save(new DaftarBelanja(db.getJudul(),db.getTanggal(),db.getDaftarBarang());
            return new ResponseEntity<>(DB, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ShoppingDataCreateDto json)
    {
        // Ubah data yg terkandung dlm JSON ke dalam objek yg bisa diterima oleh
        // Service.
        DaftarBelanja entity = new DaftarBelanja();
        entity.setJudul(json.getJudul());

        // Ubah java.util.Date ke LocalDateTime
        LocalDateTime tglLocalDateTime = json.getTanggal().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        entity.setTanggal(tglLocalDateTime);

        List<ShoppingDataCreateDto.DataBarang> listDataBarang = json.getListbarang();
        DaftarBelanjaDetil[] arrDetilBelanja = new DaftarBelanjaDetil[listDataBarang.size()];

        for (int i = 0; i < listDataBarang.size(); i++) {
            arrDetilBelanja[i] = new DaftarBelanjaDetil();
            arrDetilBelanja[i].setByk(listDataBarang.get(i).getByk());
            arrDetilBelanja[i].setMemo(listDataBarang.get(i).getMemo());
            arrDetilBelanja[i].setNamaBarang(listDataBarang.get(i).getNama());
            arrDetilBelanja[i].setSatuan(listDataBarang.get(i).getSatuan());
        }

        if (service.create(entity, arrDetilBelanja))
            return ResponseEntity.ok("Data tersimpan dengan ID: " + entity.getId());
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Data gagal tersimpan");
    }

    //Mengupdate sebuah objek DaftarBelanja ke tabel database.
    @PutMapping("shoppinglist/update/{id}")
    public ResponseEntity<?> updateList(@PathVariable("id") long id, @RequestBody DaftarBelanja db) {
        Optional<DaftarBelanja> dbData = repository.findById(id);
        if(dbData.isPresent()) {
            DaftarBelanja DB = dbData.get();
            DB.setJudul(db.getJudul());
            DB.setTanggal(db.getTanggal());
            return new ResponseEntity<>(repository.save(DB),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Menghapus objek DaftarBelanja berdasarkan ID yg diberikan.
    @DeleteMapping("shoppinglist/delete/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable("id") long id){
        try{
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
