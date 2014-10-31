package ro.pub.cs.aipi.lab02.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JoinRowSet;

import ro.pub.cs.aipi.lab02.dataaccess.DataBaseException;
import ro.pub.cs.aipi.lab02.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab02.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab02.entities.PriceRange;
import ro.pub.cs.aipi.lab02.entities.Referrence;
import ro.pub.cs.aipi.lab02.general.Constants;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.FilteredRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;
import java.util.Scanner;

public class BookStore {

    public int exercise02(String tableName) {
        DataBaseWrapper databaseWrapper = DataBaseWrapperImplementation.getInstance();
        int tableNumberOfRows = -1;
        try {
            tableNumberOfRows = databaseWrapper.getTableNumberOfRows(tableName);
        } catch (SQLException sqlException) {
            System.out.println("An exception has occured while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            databaseWrapper.releaseResources();
        }
        return tableNumberOfRows;
    }

    public void exercise03() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        ArrayList<ArrayList<String>> tableContent = null;
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("b.id");
        attributes.add("(SELECT COALESCE(GROUP_CONCAT(CONCAT(w.first_name, \' \', w.last_name)), '* * *') FROM author a, writer w WHERE a.book_id=b.id AND w.id=a.writer_id)");
        attributes.add("b.title");
        attributes.add("ph.name");
        attributes.add("b.printing_year");
        try {
            tableContent = dbWrapper.getTableContent("book b, publishing_house ph", attributes, "b.stockpile > 0 AND ph.id=b.publishing_house_id", "b.id", null);
            if (tableContent != null) {
                Charset charset = Charset.forName("UTF-8");
                BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("output/books.txt"), charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                for (int currentRow = 0; currentRow < tableContent.size(); currentRow++) {
                    String record = "";
                    for (int currentColumn = 0; currentColumn < tableContent.get(currentRow).size(); currentColumn++) {
                        record += tableContent.get(currentRow).get(currentColumn) + "\t";
                    }
                    record += "\n";
                    bufferedWriter.write(record, 0, record.length());
                }
                bufferedWriter.close();
            }
        } catch (IOException ioException) {
            System.out.println("Error opening / writing to file: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
    }

    public int exercise04() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        int result = -1;
        try {
            Scanner scanner = new Scanner(System.in);
            ArrayList<String> attributes = dbWrapper.getTableColumns("user");
            ArrayList<String> values = new ArrayList<>();
            for (String attribute : attributes) {
                System.out.print("insert " + attribute + " : ");
                String value = null;
                if (scanner.hasNext())
                    value = scanner.nextLine();
                values.add(value);
            }
            scanner.close();
            result = dbWrapper.insertValuesIntoTable("user", null, values, false);
        } catch (DataBaseException dataBaseException) {
            System.out.println("Error inserting record into table: " + dataBaseException.getMessage());
            if (Constants.DEBUG) {
                dataBaseException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
        return result;
    }

    public int exercise05() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        int result = -1;
        try {
            ArrayList<String> attributes = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            attributes.add("quantity");
            values.add("quantity*1.2");
            result = dbWrapper.updateRecordsIntoTable("supply_order_detail", attributes, values, "supply_order_id IN (SELECT id FROM supply_order WHERE publishing_house_id=\'50\' AND state=\'delivered\')");
        } catch (DataBaseException dataBaseException) {
            System.out.println("Error inserting record into table: " + dataBaseException.getMessage());
            if (Constants.DEBUG) {
                dataBaseException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
        return result;
    }

    public int exercise06() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        int result = -1;
        try {
            result = dbWrapper.deleteRecordsFromTable("publishing_house", null, null, "id NOT IN (SELECT DISTINCT publishing_house_id FROM book)");
        } catch (DataBaseException dataBaseException) {
            System.out.println("Error inserting record into table: " + dataBaseException.getMessage());
            if (Constants.DEBUG) {
                dataBaseException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
        return result;
    }

    public void exercise07() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add("personal_identifier");
            Charset charset = Charset.forName("UTF-8");
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("output/user_total_invoice_value.txt"), charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            ArrayList<ArrayList<String>> tableContent = dbWrapper.getTableContent("user", attributes, null, null, null);
            for (int currentIndex = 0; currentIndex < tableContent.size(); currentIndex++) {
                String personalIdentifier = tableContent.get(currentIndex).get(0).toString();
                ArrayList<String> parameterTypes = new ArrayList<>();
                parameterTypes.add("IN");
                parameterTypes.add("OUT");
                ArrayList<String> parameterValues = new ArrayList<>();
                parameterValues.add(personalIdentifier);
                ArrayList<Integer> parameterDataTypes = new ArrayList<>();
                parameterDataTypes.add(java.sql.Types.FLOAT);
                tableContent.get(currentIndex).add(dbWrapper.executeProcedure("calculate_user_total_invoice_value", parameterTypes, parameterValues, parameterDataTypes));
                String record = personalIdentifier + "\t" + tableContent.get(currentIndex).get(1) + "\n";
                bufferedWriter.write(record, 0, record.length());
            }
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error opening / writing to file: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
    }

    public void exercise08() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add("ph.name");
            attributes.add("COALESCE(SUM(calculate_supply_order_value(so.identification_number)), 0) AS total_supply_order_value");
            Charset charset = Charset.forName("UTF-8");
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("output/publishing_house_total_supply_order_value.txt"), charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            ArrayList<ArrayList<String>> tableContent = dbWrapper.getTableContent("publishing_house ph, supply_order so", attributes, "so.publishing_house_id=ph.id", "total_supply_order_value DESC, ph.id ASC", "so.publishing_house_id");
            for (int currentIndex = 0; currentIndex < tableContent.size(); currentIndex++) {
                String record = tableContent.get(currentIndex).get(0) + "\t" + tableContent.get(currentIndex).get(1) + "\n";
                bufferedWriter.write(record, 0, record.length());
            }
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error opening / writing to file: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
    }

    public void exercise09() {
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            Charset charset = Charset.forName("UTF-8");
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("output/foreign_key_constraints.txt"), charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            for (String tableName : dbWrapper.getTableNames()) {
                ArrayList<Referrence> referrences = dbWrapper.getReferrences(tableName);
                String result = "";
                for (Referrence referrence : referrences) {
                    result += referrence.toString() + "\n";
                }
                bufferedWriter.write(result, 0, result.length());
            }
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error opening / writing to file: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
    }

    public void exercise10() {
        JoinRowSet joinRowSet = null;
        CachedRowSet temporaryRowSet = null;
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            Charset charset = Charset.forName("UTF-8");
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("output/books_join.txt"), charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            joinRowSet = new JoinRowSetImpl();
            CachedRowSet books = new CachedRowSetImpl();
            books.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            books.setUsername(Constants.DATABASE_USERNAME);
            books.setPassword(Constants.DATABASE_PASSWORD);
            books.setCommand("SELECT * FROM book");
            books.setMatchColumn("publishing_house_id");
            books.execute();
            books.beforeFirst();
            CachedRowSet publishingHouses = new CachedRowSetImpl();
            publishingHouses.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            publishingHouses.setUsername(Constants.DATABASE_USERNAME);
            publishingHouses.setPassword(Constants.DATABASE_PASSWORD);
            publishingHouses.setCommand("SELECT * FROM publishing_house");
            publishingHouses.setMatchColumn("id");
            publishingHouses.execute();
            publishingHouses.beforeFirst();
            CachedRowSet series = new CachedRowSetImpl();
            series.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            series.setUsername(Constants.DATABASE_USERNAME);
            series.setPassword(Constants.DATABASE_PASSWORD);
            series.setCommand("SELECT * FROM series");
            series.setMatchColumn("id");
            series.execute();
            series.beforeFirst();
            CachedRowSet genres = new CachedRowSetImpl();
            genres.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            genres.setUsername(Constants.DATABASE_USERNAME);
            genres.setPassword(Constants.DATABASE_PASSWORD);
            genres.setCommand("SELECT * FROM genre");
            genres.setMatchColumn("id");
            genres.execute();
            genres.beforeFirst();

            joinRowSet.setFetchDirection(ResultSet.FETCH_FORWARD);

            joinRowSet.addRowSet(books);
            joinRowSet.addRowSet(publishingHouses);
            joinRowSet.beforeFirst();

            temporaryRowSet = joinRowSet.createCopyNoConstraints();
            temporaryRowSet.setMatchColumn("series_id");
            joinRowSet.close();
            joinRowSet = new JoinRowSetImpl();
            joinRowSet.addRowSet(temporaryRowSet);
            joinRowSet.addRowSet(series);
            joinRowSet.beforeFirst();

            temporaryRowSet = joinRowSet.createCopyNoConstraints();
            temporaryRowSet.setMatchColumn("genre_id");
            joinRowSet.close();
            joinRowSet = new JoinRowSetImpl();
            joinRowSet.addRowSet(temporaryRowSet);
            joinRowSet.addRowSet(genres);
            joinRowSet.beforeFirst();

            while (joinRowSet.next()) {
                String result = joinRowSet.getString(1) + "\t" + joinRowSet.getString(2) + "\t" + joinRowSet.getString(10) + "\t" + joinRowSet.getString(11) + "\t" + joinRowSet.getString(17) + "\t" + joinRowSet.getString(19) + "\n";
                bufferedWriter.write(result, 0, result.length());
            }
            joinRowSet.close();
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error opening / writing to file: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
    }

    public void exercise11() {
        JoinRowSet joinRowSet = null;
        CachedRowSet temporaryRowSet = null;
        FilteredRowSet filteredRowSet = null;
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            Charset charset = Charset.forName("UTF-8");
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("output/books_filtered.txt"), charset, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            joinRowSet = new JoinRowSetImpl();
            CachedRowSet books = new CachedRowSetImpl();
            books.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            books.setUsername(Constants.DATABASE_USERNAME);
            books.setPassword(Constants.DATABASE_PASSWORD);
            books.setCommand("SELECT * FROM book");
            books.setMatchColumn("publishing_house_id");
            books.execute();
            books.beforeFirst();
            CachedRowSet publishingHouses = new CachedRowSetImpl();
            publishingHouses.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            publishingHouses.setUsername(Constants.DATABASE_USERNAME);
            publishingHouses.setPassword(Constants.DATABASE_PASSWORD);
            publishingHouses.setCommand("SELECT * FROM publishing_house");
            publishingHouses.setMatchColumn("id");
            publishingHouses.execute();
            publishingHouses.beforeFirst();
            CachedRowSet series = new CachedRowSetImpl();
            series.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            series.setUsername(Constants.DATABASE_USERNAME);
            series.setPassword(Constants.DATABASE_PASSWORD);
            series.setCommand("SELECT * FROM series");
            series.setMatchColumn("id");
            series.execute();
            series.beforeFirst();
            CachedRowSet genres = new CachedRowSetImpl();
            genres.setUrl(Constants.DATABASE_CONNECTION + Constants.DATABASE_NAME);
            genres.setUsername(Constants.DATABASE_USERNAME);
            genres.setPassword(Constants.DATABASE_PASSWORD);
            genres.setCommand("SELECT * FROM genre");
            genres.setMatchColumn("id");
            genres.execute();
            genres.beforeFirst();

            joinRowSet.setFetchDirection(ResultSet.FETCH_FORWARD);

            joinRowSet.addRowSet(books);
            joinRowSet.addRowSet(publishingHouses);
            joinRowSet.beforeFirst();

            temporaryRowSet = joinRowSet.createCopyNoConstraints();
            temporaryRowSet.setMatchColumn("series_id");
            joinRowSet.close();
            joinRowSet = new JoinRowSetImpl();
            joinRowSet.addRowSet(temporaryRowSet);
            joinRowSet.addRowSet(series);
            joinRowSet.beforeFirst();

            temporaryRowSet = joinRowSet.createCopyNoConstraints();
            temporaryRowSet.setMatchColumn("genre_id");
            joinRowSet.close();
            joinRowSet = new JoinRowSetImpl();
            joinRowSet.addRowSet(temporaryRowSet);
            joinRowSet.addRowSet(genres);
            joinRowSet.beforeFirst();

            filteredRowSet = new FilteredRowSetImpl();
            filteredRowSet.populate(joinRowSet);
            PriceRange priceRange = new PriceRange(1000, 5000, "price");
            filteredRowSet.setFilter(priceRange);
            filteredRowSet.beforeFirst();
            while (filteredRowSet.next()) {
                String result = filteredRowSet.getString(1) + "\t" + filteredRowSet.getString(2) + "\t" + filteredRowSet.getString(10) + "\t" + filteredRowSet.getString(11) + "\t" + filteredRowSet.getString(17) + "\t" + filteredRowSet.getString(19) + "\n";
                bufferedWriter.write(result, 0, result.length());
            }
            filteredRowSet.close();
            bufferedWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error opening / writing to file: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } catch (SQLException sqlException) {
            System.out.println("An exception has occurred while handling database records: " + sqlException.getMessage());
            if (Constants.DEBUG) {
                sqlException.printStackTrace();
            }
        } finally {
            dbWrapper.releaseResources();
        }
    }
}
