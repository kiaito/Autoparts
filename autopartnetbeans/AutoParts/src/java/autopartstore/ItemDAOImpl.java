/*
/*****************************
* Hayden Kowalchuk - 900450331
* CIST 2372-60273
* Lab X: XXXXXXXXXXXXX	
* Description of file and lab.
* Copyright (C) 2018 Hayden Kowalchuk
***************************** */
package autopartstore;

import autopartstore.db.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Hayden Kowalchuk
 */
public class ItemDAOImpl implements ItemDAO {

    private Connection connection;

    String partTables[] = {"Engine", "Exterior", "Interior", "Maintenance"};

    public ItemDAOImpl(Connection connect) {
        connection = connect;
    }

    private Item extractItemFromResultSet(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setID(rs.getInt("id"));
        item.setName(rs.getString("partname"));
        item.setDept(rs.getString("dept"));
        item.setDesc(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        return item;
    }

    @Override
    public SortedSet<Item> getAllItems() {
        connection = ConnectionManager.getConnection();
        SortedSet<Item> items = new TreeSet<>(Comparator.comparing(Item::getName));
        try {
            for (String table : partTables) {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);

                while (rs.next()) {
                    Item item = extractItemFromResultSet(rs);
                    items.add(item);
                }
                rs.close();
                stmt.close();
            }

            return items;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Item getItemByID(int id) {
        connection = ConnectionManager.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID, dept, partName, description, price FROM Exterior WHERE ID=" + id
                    + " UNION ALL "
                    + "SELECT ID, dept, partName, description, price FROM Engine WHERE ID=" + id
                    + " UNION ALL "
                    + "SELECT ID, dept, partName, description, price FROM Interior WHERE ID=" + id
                    + " UNION ALL "
                    + "SELECT ID, dept, partName, description, price FROM Maintenance WHERE ID=" + id);
            if (rs.next()) {
                return extractItemFromResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Item> getItemsBySearchParam(String search) {
        connection = ConnectionManager.getConnection();
        Set<Item> items = new HashSet();
        try {
            for (String table : partTables) {
                String searchTerms[] = search.split(" ");
                for (String term : searchTerms) {
                    Statement stmt = connection.createStatement();
                    System.out.printf("Searching [%s] for '%s'\n", table, term);
                    String state = "SELECT * FROM " + table + " WHERE (UPPER(partName) LIKE UPPER('%" + term + "%') "
                            + "OR UPPER(description) LIKE UPPER('%" + term + "%'))";
                    ResultSet rs = stmt.executeQuery(state);
                    while (rs.next()) {
                        Item item = extractItemFromResultSet(rs);
                        items.add(item);
                    }
                    rs.close();
                    stmt.close();
                }
            }

            return items;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean insertItem(Item item) {
        int numUpdated = 0;
        connection = ConnectionManager.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO items VALUES (NULL, ?, ?, ?, ?)");
            stmt.setString(1, item.getDept());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDesc());
            stmt.setDouble(4, item.getPrice());
            numUpdated = stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numUpdated > 0;
    }

    @Override
    public boolean updateItem(Item item) {
        int numUpdated = 0;
        connection = ConnectionManager.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE ? SET dept=?, name=?, desc=?, price=? WHERE id=?");
            stmt.setString(1, item.getDept());
            stmt.setString(2, item.getDept());
            stmt.setString(3, item.getName());
            stmt.setString(4, item.getDesc());
            stmt.setDouble(5, item.getPrice());
            stmt.setInt(5, item.getID());
            numUpdated = stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numUpdated > 0;
    }

    @Override
    public boolean deleteItem(Item item) {
        int numUpdated = 0;
        connection = ConnectionManager.getConnection();
        try {
            Statement stmt = connection.createStatement();
            numUpdated = stmt.executeUpdate("DELETE FROM " + item.getDept() + " WHERE id=" + item.getID());
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numUpdated > 0;
    }

    @Override
    public Set<Item> getAllItemsByDept(String Dept) {
        connection = ConnectionManager.getConnection();
        Set<Item> items = new HashSet();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + Dept);
            while (rs.next()) {
                Item item = extractItemFromResultSet(rs);
                items.add(item);
            }
            rs.close();
            stmt.close();

            return items;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
