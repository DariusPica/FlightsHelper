package Domain;

public class Bilet extends Entity<Integer>
{
    private String NumeClient;
    private String NumeTuristi;
    private String Adresa;
    private Integer NrLocuri;
    private Zbor Zbor;

    public Bilet(String numeClient, String numeTuristi, String adresa, Integer nrLocuri,Zbor zbor) {
        NumeClient = numeClient;
        NumeTuristi = numeTuristi;
        Adresa = adresa;
        NrLocuri = nrLocuri;
        Zbor=zbor;
    }

    public String getNumeClient() {
        return NumeClient;
    }

    public void setNumeClient(String numeClient) {
        NumeClient = numeClient;
    }

    public String getNumeTuristi() {
        return NumeTuristi;
    }

    public void setNumeTuristi(String numeTuristi) {
        NumeTuristi = numeTuristi;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public Integer getNrLocuri() {
        return NrLocuri;
    }

    public void setNrLocuri(Integer nrLocuri) {
        NrLocuri = nrLocuri;
    }

    public void setZbor(Domain.Zbor zbor) {
        Zbor = zbor;
    }

    public Domain.Zbor getZbor() {
        return Zbor;
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "NumeClient='" + NumeClient + '\'' +
                ", NumeTuristi='" + NumeTuristi + '\'' +
                ", Adresa='" + Adresa + '\'' +
                ", NrLocuri=" + NrLocuri +
                ", Zbor=" + Zbor +
                '}';
    }
}
