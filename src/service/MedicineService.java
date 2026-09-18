package service;

import database.DatabaseConnection;
import model.Medicine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineService {

    // ADD MEDICINE
    public boolean addMedicine(
            String name,
            String company,
            String medicine_type,
            double price,
            int quantity_in_stock,
            int reorder_level,
            Date expiry_date,
            int supplierId) {

        String sql = "INSERT INTO medicines " +
                "(name, company, medicine_type, price, quantity_in_stock, " +
                "reorder_level, expiry_date, supplier_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, company);
            statement.setString(3, medicine_type);
            statement.setDouble(4, price);
            statement.setInt(5, quantity_in_stock);
            statement.setInt(6, reorder_level);
            statement.setDate(7, expiry_date);
            statement.setInt(8, supplierId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // GET MEDICINE BY ID
    public Medicine getMedicineById(int medicine_id) {

        String sql = "SELECT * FROM medicines WHERE medicine_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, medicine_id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Medicine(
                        resultSet.getInt("medicine_id"),
                        resultSet.getString("name"),
                        resultSet.getString("company"),
                        resultSet.getString("medicine_type"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getInt("reorder_level"),
                        resultSet.getDate("expiry_date"),
                        resultSet.getInt("supplier_id")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    // GET ALL MEDICINES
    public List<Medicine> getAllMedicines() {

        String sql = "SELECT * FROM medicines";

        List<Medicine> medicines = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Medicine medicine = new Medicine(
                        resultSet.getInt("medicine_id"),
                        resultSet.getString("name"),
                        resultSet.getString("company"),
                        resultSet.getString("medicine_type"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("quantity_in_stock"),
                        resultSet.getInt("reorder_level"),
                        resultSet.getDate("expiry_date"),
                        resultSet.getInt("supplier_id")
                );

                medicines.add(medicine);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return medicines;
    }


    // DELETE MEDICINE
    public boolean deleteMedicine(int medicineID) {

        String sql = "DELETE FROM medicines WHERE medicine_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, medicineID);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // UPDATE MEDICINE
    public boolean updateMedicine(
            int medicineID,
            String name,
            String company,
            String medicine_type,
            double price,
            int quantity_in_stock,
            int reorder_level,
            Date expiry_date,
            int supplierId) {

        String sql = "UPDATE medicines " +
                "SET name = ?, " +
                "company = ?, " +
                "medicine_type = ?, " +
                "price = ?, " +
                "quantity_in_stock = ?, " +
                "reorder_level = ?, " +
                "expiry_date = ?, " +
                "supplier_id = ? " +
                "WHERE medicine_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, company);
            statement.setString(3, medicine_type);
            statement.setDouble(4, price);
            statement.setInt(5, quantity_in_stock);
            statement.setInt(6, reorder_level);
            statement.setDate(7, expiry_date);
            statement.setInt(8, supplierId);
            statement.setInt(9, medicineID);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}