package service;

import database.DatabaseConnection;
import model.Sale;
import model.SaleItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesService {

    // CREATE SALE
    public int createSale(double total_amount, int user_id) {

        String sql = "INSERT INTO sales (total_amount, user_id) " +
                "VALUES (?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            statement.setDouble(1, total_amount);
            statement.setInt(2, user_id);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }


    // ADD ITEM TO SALE
    public boolean addSaleItem(
            int sale_id,
            int medicine_id,
            int quantity_sold,
            double price_at_sale) {

        String sql = "INSERT INTO sale_items " +
                "(sale_id, medicine_id, quantity_sold, price_at_sale) " +
                "VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sale_id);
            statement.setInt(2, medicine_id);
            statement.setInt(3, quantity_sold);
            statement.setDouble(4, price_at_sale);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // GET SALE BY ID
    public Sale getSaleById(int sale_id) {

        String sql = "SELECT * FROM sales WHERE sale_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sale_id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getTimestamp("sale_date"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getInt("user_id")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    // GET ALL SALES
    public List<Sale> getAllSales() {

        String sql = "SELECT * FROM sales ORDER BY sale_date DESC";

        List<Sale> sales = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Sale sale = new Sale(
                        resultSet.getInt("sale_id"),
                        resultSet.getTimestamp("sale_date"),
                        resultSet.getDouble("total_amount"),
                        resultSet.getInt("user_id")
                );

                sales.add(sale);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sales;
    }


    // GET ITEMS FOR A SALE
    public List<SaleItem> getSaleItems(int sale_id) {

        String sql = "SELECT * FROM sale_items WHERE sale_id = ?";

        List<SaleItem> items = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sale_id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                SaleItem item = new SaleItem(
                        resultSet.getInt("sale_item_id"),
                        resultSet.getInt("sale_id"),
                        resultSet.getInt("medicine_id"),
                        resultSet.getInt("quantity_sold"),
                        resultSet.getDouble("price_at_sale")
                );

                items.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }


    // DELETE SALE ITEM
    public boolean deleteSaleItem(int sale_item_id) {

        String sql = "DELETE FROM sale_items WHERE sale_item_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sale_item_id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}