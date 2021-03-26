package Repository;

import Domain.Zbor;

import java.time.LocalDateTime;

public interface IZborRepository  extends ICRUDRepository<Integer, Zbor>
{
    Iterable<Zbor> getZboruriByDestinatieAndData(String destinatie, LocalDateTime data);

}
