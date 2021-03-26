package Domain;

import java.time.LocalDateTime;

public class Zbor extends Entity<Integer>
{

    private String Destinatie;
    private LocalDateTime OraDataPlecare;
    private String Aeroport;
    private Integer LocuriDisponibile;

    public Zbor(String destinatie, LocalDateTime oraDataPlecare, String aeroport, Integer locuriDisponibile) {
        Destinatie = destinatie;
        OraDataPlecare = oraDataPlecare;
        Aeroport = aeroport;
        LocuriDisponibile = locuriDisponibile;
    }

    public Zbor(Integer id,String destinatie, LocalDateTime oraDataPlecare, String aeroport, Integer locuriDisponibile) {

        super.setId(id);
        Destinatie = destinatie;
        OraDataPlecare = oraDataPlecare;
        Aeroport = aeroport;
        LocuriDisponibile = locuriDisponibile;
    }

    public String getDestinatie() {
        return Destinatie;
    }

    public void setDestinatie(String destinatie) {
        Destinatie = destinatie;
    }

    public LocalDateTime getOraDataPlecare() {
        return OraDataPlecare;
    }

    public void setOraDataPlecare(LocalDateTime oraDataPlecare) {
        OraDataPlecare = oraDataPlecare;
    }

    public String getAeroport() {
        return Aeroport;
    }

    public void setAeroport(String aeroport) {
        Aeroport = aeroport;
    }

    public Integer getLocuriDisponibile() {
        return LocuriDisponibile;
    }

    public void setLocuriDisponibile(Integer locuriDisponibile) {
        LocuriDisponibile = locuriDisponibile;
    }

    @Override
    public String toString() {
        return "Zbor{" +
                "Destinatie='" + Destinatie + '\'' +
                ", OraDataPlecare=" + OraDataPlecare +
                ", Aeroport='" + Aeroport + '\'' +
                ", LocuriDisponibile=" + LocuriDisponibile +
                '}';
    }
}
