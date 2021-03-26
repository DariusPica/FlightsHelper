package Repository;

import Domain.User;
import Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements IUserRepository
{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public User save(User entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("insert into Users(username,password) values (?,?)")){
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
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
    public User update(User entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("Update Users set password=? where username=?")){
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
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
    public User delete(User entity) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();


        try(PreparedStatement preStmt = con.prepareStatement("Delete from Users where username=?")){
            preStmt.setString(1, entity.getUsername());
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
    public User get(String s) {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        User user = null;
        try(PreparedStatement preStmt = con.prepareStatement("select * from Users where username=?")){
            preStmt.setString(1, s);
            try(ResultSet result = preStmt.executeQuery()){
                if (result.next()){
                    String username = result.getString("username");
                    String password = result.getString("password");
                    user=new User(username,password);
                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return user;
    }

    @Override
    public Iterable<User> getAll()
    {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();

        List<User> users = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Users")){
            try(ResultSet result = preStmt.executeQuery()){
                while (result.next()){
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(username,password);
                    users.add(user);
                }
            }

        }catch(SQLException ex){
            logger.error(ex);
            System.err.println("Error BD " + ex);
        }
        logger.traceExit();
        return users;
    }
}
