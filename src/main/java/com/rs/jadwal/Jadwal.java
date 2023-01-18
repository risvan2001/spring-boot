package com.rs.jadwal;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "jadwal")
public class Jadwal
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Length(min = 8, max = 20)
    private String name;

    @Column(nullable = false)
    @Length(min = 2, max = 10)
    private String hari_praktik;

    @Column(nullable = false)
    @Length(min = 8, max = 50)
    private String lokasi_praktik;

    public Jadwal()
    {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHari_praktik() {
        return hari_praktik;
    }

    public void setHari_praktik(String hari_praktik) {
        this.hari_praktik = hari_praktik;
    }

    public String getLokasi_praktik() {
        return lokasi_praktik;
    }

    public void setLokasi_praktik(String lokasi_praktik) {
        this.lokasi_praktik = lokasi_praktik;
    }
}
