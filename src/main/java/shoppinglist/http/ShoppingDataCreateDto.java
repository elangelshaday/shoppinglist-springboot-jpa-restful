/*
 * ShoppingDataCreateDto.java
 *
 * Created on Mar 22, 2021, 01.38
 */
package shoppinglist.http;

import java.util.Date;
import java.util.List;

/**
 * Merepresentasikan dokumen JSON untuk menyimpan objek DaftarBelanja.
 * @author irfin
 */
public class ShoppingDataCreateDto
{
    private String judul;
    private Date tanggal;
    private List<DataBarang> listbarang;

    public String getJudul()
    {
        return judul;
    }

    public void setJudul(String judul)
    {
        this.judul = judul;
    }

    public Date getTanggal()
    {
        return tanggal;
    }

    public void setTanggal(Date tanggal)
    {
        this.tanggal = tanggal;
    }

    public List<DataBarang> getListbarang()
    {
        return listbarang;
    }

    public void setListbarang(List<DataBarang> listbarang)
    {
        this.listbarang = listbarang;
    }

    static class DataBarang
    {
        private String nama;
        private float byk;
        private String satuan;
        private String memo;

        public String getNama()
        {
            return nama;
        }

        public void setNama(String nama)
        {
            this.nama = nama;
        }

        public float getByk()
        {
            return byk;
        }

        public void setByk(float byk)
        {
            this.byk = byk;
        }

        public String getSatuan()
        {
            return satuan;
        }

        public void setSatuan(String satuan)
        {
            this.satuan = satuan;
        }

        public String getMemo()
        {
            return memo;
        }

        public void setMemo(String memo)
        {
            this.memo = memo;
        }
    }
}
