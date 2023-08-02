package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public List<Country> getCountriesByPopulation()
    {
        List<Country> countries = new ArrayList<>();
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve country information ordered by population
            String strSelect = "SELECT Code, Name, Continent, Region, Population, Capital "
                    + "FROM country "
                    + "ORDER BY Population DESC";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            // Iterate through the result set and populate the list of countries
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("Code");
                country.name = rset.getString("Name");
                country.continent = rset.getString("Continent");
                country.region = rset.getString("Region");
                country.population = rset.getInt("Population");
                country.capital = rset.getString("Capital");
                countries.add(country);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country information");
        }
        return countries;
    }

    public void displayCountries(List<Country> countries)
    {
        // Print the header
        System.out.println("Code | Name | Continet | Region | Population | Capital");
        for (Country country : countries)
        {
            System.out.println(country.code + " | " + country.name + " | " + country.continent + " | " + country.region + " | " + country.population + " | " + country.capital);
        }
    }

    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Get countries ordered by population
        List<Country> countries = a.getCountriesByPopulation();

        // Display countries information
        a.displayCountries(countries);

        // Disconnect from database
        a.disconnect();
    }
}
