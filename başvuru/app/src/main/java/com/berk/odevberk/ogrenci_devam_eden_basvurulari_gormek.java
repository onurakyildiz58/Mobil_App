package com.berk.odevberk;

public class ogrenci_devam_eden_basvurulari_gormek
{
    String name;
    String url;
    String situation;

    public String getName() {
        String isim = name.substring(name.indexOf("_") + 1, name.lastIndexOf(""));
        String isimayar = isim.substring(isim.indexOf(""), isim.indexOf("_"));

        String isimbasharf = isimayar.substring(0, 1).toUpperCase();
        String buyukisim = isimbasharf + isimayar.substring(1);

        String soyisimayar = isim.substring(isim.indexOf("_"), isim.lastIndexOf("_") + 1);
        String soyisim = soyisimayar.substring(soyisimayar.indexOf("_") + 1, soyisimayar.lastIndexOf("_"));

        String soyisimbasharf = soyisim.substring(0, 1).toUpperCase();
        String buyuksoyisim = soyisimbasharf + soyisim.substring(1);


        String isim_sonuc = buyukisim + " " + buyuksoyisim;
        String adsoyad = isim_sonuc.substring(isim_sonuc.indexOf(""), isim_sonuc.lastIndexOf("_"));

        String basvurutipi = name.substring(name.lastIndexOf("_") + 1, name.indexOf("."));

        if (basvurutipi.equals("capbasvuruform")) {
            return adsoyad + "\n\nÇAP Baþvurusu";
        } else if (basvurutipi.equals("intibakbasvuruform")) {
            return adsoyad + "\n\nDers Ýntibaký Baþvurusu";
        } else if (basvurutipi.equals("yataygecis")) {
            return adsoyad + "\n\nYatay Geçiþ Baþvurusu";
        } else {
            return adsoyad + "\n\nYaz Okulu Baþvurusu";
        }
    }

    public String getUrl() {
        return url;
    }

    public String getSituation() {
        return situation;
    }
}
