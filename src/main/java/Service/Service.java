package Service;

import Domain.Bilet;
import Domain.User;
import Domain.Zbor;
import Repository.*;

import java.time.LocalDate;
import java.util.List;

public class Service
{
    private IBiletRepository repoBilet;
    private IUserRepository repoUser;
    private IZborRepository repoZbor;

    public Service(IBiletRepository repoBilet, IUserRepository repoUser, IZborRepository repoZbor) {
        this.repoBilet = repoBilet;
        this.repoUser = repoUser;
        this.repoZbor = repoZbor;
    }

    public User getLoginRequest(String username, String password)
    {
        User utilizator= repoUser.get(username);
        if(utilizator!=null&& utilizator.getPassword().equals(password))
            return utilizator;
        return null;
    }

    public Iterable<Zbor> getZboruri()
    {
        return repoZbor.getAll();
    }

    public Iterable<Zbor> getZboruriSearch(String destinatie, LocalDate data)
    {
        return repoZbor.getZboruriByDestinatieAndData(destinatie,data.atStartOfDay());
    }

    public void saveBilet(String clientString, String pasageriString, String adresaString, Integer locuriInteger, Zbor zborSelected)
    {
        Bilet bilet=new Bilet(clientString,pasageriString,adresaString,locuriInteger,zborSelected);
        repoBilet.save(bilet);
        repoBilet.getAll().forEach(System.out::println);
    }

    public void updateZbor(Zbor zbor)
    {
        repoZbor.update(zbor);
    }
}
