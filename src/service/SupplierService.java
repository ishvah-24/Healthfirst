package service;
import database.DatabaseConnection;
import model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierService {

    // ADD SUPPLIER
    public boolean addSupplier(
            String name,
            String contact_person,
            String phone,
            String email,
            String address) {

        String sql = "INSERT INTO suppliers " +
                "(name, contact_person, phone, email, address) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, contact_person);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, address);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // GET SUPPLIER BY ID
    public Supplier getSupplierById(int supplier_id) {

        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, supplier_id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Supplier(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact_person"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    // GET ALL SUPPLIERS
    public List<Supplier> getAllSuppliers() {

        String sql = "SELECT * FROM suppliers";

        List<Supplier> suppliers = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Supplier supplier = new Supplier(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact_person"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("address")
                );

                suppliers.add(supplier);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return suppliers;
    }


    // UPDATE SUPPLIER
    public boolean updateSupplier(
            int supplier_id,
            String name,
            String contact_person,
            String phone,
            String email,
            String address) {

        String sql = "UPDATE suppliers " +
                "SET name = ?, " +
                "contact_person = ?, " +
                "phone = ?, " +
                "email = ?, " +
                "address = ? " +
                "WHERE supplier_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, contact_person);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, address);
            statement.setInt(6, supplier_id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // DELETE SUPPLIER
    public boolean deleteSupplier(int supplier_id) {

        String sql = "DELETE FROM suppliers WHERE supplier_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, supplier_id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}