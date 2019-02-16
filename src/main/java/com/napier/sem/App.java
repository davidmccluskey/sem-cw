package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Extract employee salary information
        ArrayList<Country> countries = a.populationLtoS();
        a.displayCountries(countries);

        //Displaying top N countries per continent
        int n = 3;
        System.out.println("TOP " + n + " countries per continent:");
        ArrayList<Country> topNContinent = a.topNContinent(n);
        a.displayCountries(topNContinent);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect() {
        try {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets all the current employees and salaries.
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Country> populationLtoS()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, Continent, Population "
                            + "FROM country "
                            + "ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Country> countries = new ArrayList<Country>();
            while (rset.next())
            {
                Country country = new Country();
                country.Name = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Population = rset.getInt("Population");
                countries.add(country);
            }
            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    /**
     * Prints a list of countries.
     * @param countries The list of employees to print.
     */
    public void displayCountries(ArrayList<Country> countries)
    {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s", "Name", "Continent", "Population"));
        // Loop over all employees in the list
        for (Country country : countries)
        {
            String emp_string =
                    String.format("%-10s %-15s %-20s",
                            country.Name, country.Continent, country.Population);
            System.out.println(emp_string);
        }
    }


    public ArrayList<Country> topNContinent(int n)
    {
        try
        {
            String [] continents = new String[] {"Asia","Europe","North America","Africa","Oceania","Antarctica","South America"};

            ArrayList<Country> countries = new ArrayList<Country>();

            for(String cont : continents){
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT TOP" + n + "Name, Continent, Population "
                                + "FROM country "
                                + "WHERE Continent = '" + cont +"'";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information

                while (rset.next())
                {
                    Country country = new Country();
                    country.Name = rset.getString("Name");
                    country.Continent = rset.getString("Continent");
                    country.Population = rset.getInt("Population");
                    countries.add(country);
                }
            }
            

            return countries;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }
}