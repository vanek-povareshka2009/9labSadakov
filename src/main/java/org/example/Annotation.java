package org.example;

import java.lang.reflect.Field;
import java.sql.*;

public class Annotation {

    public static void createTable(Class cls) throws SQLException {
        Table table = (Table) cls.getAnnotation(Table.class);
        StringBuilder sql;
        StringBuilder sqlDEL;
        if (table == null) {
            sql = new StringBuilder("CREATE TABLE wolf (");
            sqlDEL = new StringBuilder("DROP TABLE IF EXISTS wolf;");
        } else {
            sql = new StringBuilder("CREATE TABLE " + table.title() + " (");
            sqlDEL = new StringBuilder("DROP TABLE IF EXISTS " + table.title() + ";");
        }

        Field[] fields = cls.getDeclaredFields();
        // Inside the createTable method in the Annotation class
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                sql.append(field.getName()).append(" ");

                if (field.getType().getSimpleName().equals("int")) {
                    sql.append("INTEGER");
                } else if (field.getType().getSimpleName().equals("double")) {
                    sql.append("DOUBLE");
                } else if (field.getType().getSimpleName().equals("String")) {
                    if (field.getName().equals("name") && field.isAnnotationPresent(Column.class)) {
                        int maxLength = field.getAnnotation(Column.class).maxLength();
                        sql.append("VARCHAR(").append(maxLength).append(")");
                    } else {
                        sql.append("CHAR(10)");
                    }
                }
                if (field.getType().getSimpleName().equals("text")) {
                    sql.append("TEXT");
                }
                else if (field.getType().isEnum()) {
                    sql.append("TEXT");
                }

                sql.append(",");
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(");");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            Statement statement = connection.createStatement();

            statement.execute(sqlDEL.toString());
            statement.execute(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public static void insertIntoTable(Object obj) throws SQLException {
        String tableName;
        if (obj.getClass().getAnnotation(Table.class) == null) {
            tableName = "wolf";
        } else {
            tableName = obj.getClass().getAnnotation(Table.class).title();
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " (");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                query.append(field.getName()).append(",");
            }
        }
        query.deleteCharAt(query.length() - 1).append(") VALUES (");
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    if (field.getName().equals("name") && field.isAnnotationPresent(Column.class)) {
                        String nameValue = (String) field.get(obj);
                        int maxLength = field.getAnnotation(Column.class).maxLength();
                        if (nameValue.length() > maxLength) {
                            nameValue = nameValue.substring(0, maxLength);
                        }
                        query.append("'").append(nameValue).append("',");
                    } else {
                        query.append("'").append(field.get(obj)).append("',");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        query.deleteCharAt(query.length() - 1).append(")");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
            Statement statement = connection.createStatement();
            statement.execute(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
