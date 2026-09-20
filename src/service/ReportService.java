package service;

import database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    // SALES REPORT
    public List<Object[]> getSalesReport() {

        List<Object[]> sales = new ArrayList<>();

        String sql = """
                SELECT 
                    s.sale_id,
                    s.sale_date,
                    s.total_amount
                FROM sales s
                ORDER BY s.sale_date DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                sales.add(new Object[]{
                        resultSet.getInt("sale_id"),
                        resultSet.getTimestamp("sale_date"),
                        resultSet.getDouble("total_amount")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    // EXPIRY REPORT
    public List<Object[]> getExpiringMedicines() {

        List<Object[]> medicines = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate oneMonthFromNow = today.plusMonths(1);

        String sql = """
                SELECT 
                    medicine_id, name,company, medicine_type,expiry_date,quantity_in_stock
                FROM medicines
                WHERE expiry_date BETWEEN ? AND ?
                ORDER BY expiry_date ASC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setDate(1, Date.valueOf(today));
            statement.setDate(2, Date.valueOf(oneMonthFromNow));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                medicines.add(new Object[]{
                        resultSet.getInt("medicine_id"),
                        resultSet.getString("name"),
                        resultSet.getString("company"),
                        resultSet.getString("medicine_type"),
                        resultSet.getDate("expiry_date"),
                        resultSet.getInt("quantity_in_stock")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicines;
    }
}