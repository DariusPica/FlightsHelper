package Repository;


import Domain.Zbor;
import Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ZborRepository implements IZborRepository
{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public ZborRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Iterable<Zbor> getZboruriByDestinatieAndData(String destinatie, LocalDateTime data) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        List<Zbor> zboruri = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Zboruri where destinatie=? and oraDataPlecare between ? and ?")){
            preStmt.setString(1, destinatie);
            preStmt.setTimestamp(2, Timestamp.valueOf(data));
            preStmt.setTimestamp(3, Timestamp.valueOf(data.plusDays(1)));
            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    Integer id=result.getInt("id");
                    String destinatie1=result.getString("destinatie");
                    LocalDateTime date = result.getTimestamp("oraDataPlecare").toLocalDateTime();
                    String aeroport=result.getString("aeroport");
                    Integer locuriDisponibile=result.getInt("locuriDisponibile");
                    Zbor zbor=new Zbor(id,destinatie1,date,aeroport,locuriDisponibile);
                    zboruri.add(zbor);

                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return zboruri;
    }

    @Override
    public Zbor save(Zbor entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("insert into Zboruri(id,destinatie,oraDataPlecare,aeroport,locuriDisponibile) values (?,?,?,?,?)")){
            preStmt.setInt(1, entity.getId());
            preStmt.setString(2, entity.getDestinatie());
            preStmt.setTimestamp(3, Timestamp.valueOf(entity.getOraDataPlecare()));
            preStmt.setString(4, entity.getAeroport());
            preStmt.setInt(5, entity.getLocuriDisponibile());
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
    public Zbor update(Zbor entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("update Zboruri set destinatie=?,oraDataPlecare=?,aeroport=?,locuriDisponibile=? where id=?")){
            preStmt.setString(1, entity.getDestinatie());
            preStmt.setTimestamp(2, Timestamp.valueOf(entity.getOraDataPlecare()));
            preStmt.setString(3, entity.getAeroport());
            preStmt.setInt(4, entity.getLocuriDisponibile());
            preStmt.setInt(5, entity.getId());
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
    public Zbor delete(Zbor entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preStmt = con.prepareStatement("delete from Zboruri where id=?")){
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
    public Zbor get(Integer integer) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        Zbor zbor = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Zboruri where id=?")){
            preStmt.setInt(1, integer);
            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    String destinatie=result.getString("destinatie");
                    LocalDateTime date = result.getTimestamp("oraDataPlecare").toLocalDateTime();
                    String aeroport=result.getString("aeroport");
                    Integer locuriDisponibile=result.getInt("locuriDisponibile");
                    zbor.setId(integer);
                    zbor.setAeroport(aeroport);
                    zbor.setDestinatie(destinatie);
                    zbor.setLocuriDisponibile(locuriDisponibile);
                    zbor.setOraDataPlecare(date);
                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return zbor;
    }

    @Override
    public Iterable<Zbor> getAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        List<Zbor> zboruri = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Zboruri")){
            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    String destinatie=result.getString("destinatie");
                    LocalDateTime date = result.getTimestamp("oraDataPlecare").toLocalDateTime();
                    String aeroport=result.getString("aeroport");
                    Integer locuriDisponibile=result.getInt("locuriDisponibile");
                    Zbor zbor=new Zbor(destinatie,date,aeroport,locuriDisponibile);
                    zboruri.add(zbor);

                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return zboruri;
    }
}
