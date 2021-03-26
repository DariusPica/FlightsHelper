package Repository;

import Domain.Bilet;
import Domain.User;
import Domain.Zbor;
import Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BiletRepository implements IBiletRepository
{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public BiletRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Bilet save(Bilet entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("insert into Bilete(numeClient,numeTuristi,adresa,nrLocuri,zbor) values (?,?,?,?,?)")){
            preStmt.setString(1, entity.getNumeClient());
            preStmt.setString(2, entity.getNumeTuristi());
            preStmt.setString(3, entity.getAdresa());
            preStmt.setInt(4, entity.getNrLocuri());
            preStmt.setInt(5, entity.getZbor().getId());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }

        logger.traceExit();
        return entity;
    }

    @Override
    public Bilet update(Bilet entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("update Bilete set numeClient=?, numeTuristi=?,adresa=?,nrLocuri=?,zbor=? where id=?")){
            preStmt.setString(1, entity.getNumeClient());
            preStmt.setString(2, entity.getNumeTuristi());
            preStmt.setString(3, entity.getAdresa());
            preStmt.setInt(4, entity.getNrLocuri());
            preStmt.setInt(5, entity.getZbor().getId());
            preStmt.setInt(6,entity.getId());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }

        logger.traceExit();
        return entity;
    }

    @Override
    public Bilet delete(Bilet entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from Bilete where id=?")){
            preStmt.setInt(1,entity.getId());
            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }

        logger.traceExit();
        return entity;
    }

    @Override
    public Bilet get(Integer integer) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Bilet bilet = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Bilete B,Zboruri Z where B.zbor=Z.id and B.id=?")){
            preStmt.setInt(1, integer);
            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    String numeClient = result.getString("numeClient");
                    String numeTuristi = result.getString("numeTuristi");
                    String adresa=result.getString("adresa");
                    Integer nrLocuri=result.getInt("nrLocuri");

                    String destinatie=result.getString("destinatie");
                    LocalDateTime date = result.getTimestamp("oraDataPlecare").toLocalDateTime();
                    String aeroport=result.getString("aeroport");
                    Integer locuriDisponibile=result.getInt("locuriDisponibile");
                    Zbor zbor=new Zbor(destinatie,date,aeroport,locuriDisponibile);
                    bilet.setNumeClient(numeClient);
                    bilet.setNumeTuristi(numeTuristi);
                    bilet.setAdresa(adresa);
                    bilet.setNrLocuri(nrLocuri);
                    bilet.setZbor(zbor);

                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return bilet;
    }

    @Override
    public Iterable<Bilet> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        List<Bilet> bilete = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Bilete ,Zboruri  where Bilete.zbor=Zboruri.id")){
            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    String numeClient = result.getString("numeClient");
                    String numeTuristi = result.getString("numeTuristi");
                    String adresa=result.getString("adresa");
                    Integer nrLocuri=result.getInt("nrLocuri");

                    String destinatie=result.getString("destinatie");
                    LocalDateTime date = result.getTimestamp("oraDataPlecare").toLocalDateTime();
                    String aeroport=result.getString("aeroport");
                    Integer locuriDisponibile=result.getInt("locuriDisponibile");
                    Zbor zbor=new Zbor(destinatie,date,aeroport,locuriDisponibile);

                    Bilet bilet = new Bilet(numeClient,numeTuristi,adresa,nrLocuri,zbor);
                    bilete.add(bilet);
                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return bilete;
    }
}
