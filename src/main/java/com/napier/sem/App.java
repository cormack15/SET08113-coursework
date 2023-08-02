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
        System.out.println("Code | Name | Continent | Region | Population | Capital");
        for (Country country : countries)
        {
            System.out.println(country.code + " | " + country.name + " | " + country.continent + " | " + country.region + " | " + country.population + " | " + country.capital);
        }
    }

    public List<City> getCitiesByPopulation() {
        List<City> cities = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population "
                    + "FROM city "
                    + "JOIN country ON city.CountryCode = country.Code "
                    + "ORDER BY Population DESC";
            ResultSet rset = stmt.executeQuery(strSelect);
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("CountryName");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city information");
        }
        return cities;
    }

    public void displayCities(List<City> cities) {
        System.out.println("Name | Country | District | Population");
        for (City city : cities) {
            System.out.println(city.name + " | " + city.country + " | " + city.district + " | " + city.population);
        }
    }

    public List<City> getCapitalCitiesByPopulation()
    {
        List<City> capitalCities = new ArrayList<>();
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve capital city information ordered by population
            String strSelect = "SELECT city.Name, country.Name AS Country, city.Population "
                    + "FROM city "
                    + "INNER JOIN country ON city.CountryCode = country.Code "
                    + "WHERE city.ID = country.Capital "
                    + "ORDER BY city.Population DESC";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            // Iterate through the result set and populate the list of capital cities
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.population = rset.getInt("Population");
                capitalCities.add(city);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city information");
        }
        return capitalCities;
    }

    public List<City> getTopNCapitalCitiesByPopulation(int n)
    {
        List<City> topNCapitalCities = new ArrayList<>();
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve top N capital city information ordered by population
            String strSelect = "SELECT city.Name, country.Name AS Country, city.Population "
                    + "FROM city "
                    + "INNER JOIN country ON city.CountryCode = country.Code "
                    + "WHERE city.ID = country.Capital "
                    + "ORDER BY city.Population DESC "
                    + "LIMIT " + n;

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            // Iterate through the result set and populate the list of top N capital cities
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.population = rset.getInt("Population");
                topNCapitalCities.add(city);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get top N capital city information");
        }
        return topNCapitalCities;
    }

    public void displayCapitalCities(List<City> capitalCities)
    {
        // Print the header
        System.out.println("Name | Country | Population");
        for (City city : capitalCities)
        {
            System.out.println(city.name + " | " + city.country + " | " + city.population);
        }
    }

    public List<Population> getPopulations() {
        List<Population> populations = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve population details for each country
            String strSelect = "SELECT country.Name, country.Population, "
                    + "SUM(city.Population) AS PopulationInCities, "
                    + "(country.Population - SUM(city.Population)) AS PopulationNotInCities "
                    + "FROM country "
                    + "LEFT JOIN city ON country.Code = city.CountryCode "
                    + "GROUP BY country.Code "
                    + "ORDER BY country.Population DESC";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            // Iterate through the result set and populate the list of country population details
            while (rset.next()) {
                Population population = new Population();
                population.countryName = rset.getString("Name");
                population.totalPopulation = rset.getInt("Population");
                population.populationInCities = rset.getInt("PopulationInCities");
                population.populationNotInCities = rset.getInt("PopulationNotInCities");

                // Calculate percentages
                double percentageInCities = (double) population.populationInCities / population.totalPopulation * 100.0;
                double percentageNotInCities = (double) population.populationNotInCities / population.totalPopulation * 100.0;
                population.percentageInCities = Math.round(percentageInCities * 100.0) / 100.0; // Round to 2 decimal places
                population.percentageNotInCities = Math.round(percentageNotInCities * 100.0) / 100.0; // Round to 2 decimal places

                populations.add(population);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country population details");
        }
        return populations;
    }

    public void displayPopulations(List<Population> populations) {
        // Print the header
        System.out.println("Continent/Region/Country | Total Population | Population In Cities | Population Not In Cities | % In Cities | % Not In Cities");
        for (Population population : populations) {
            System.out.println(population.countryName + " | "
                    + population.totalPopulation + " | "
                    + population.populationInCities + " | "
                    + population.populationNotInCities + " | "
                    + population.percentageInCities + "% | "
                    + population.percentageNotInCities + "%");
        }
    }

    public long getWorldPopulation() {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve the population of the world
            String strSelect = "SELECT SUM(Population) AS WorldPopulation FROM country";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                return rset.getLong("WorldPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the population of the world");
        }
        return 0;
    }

    public void getContinentPopulation(String continentName) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve the population of the continent
            String strSelect = "SELECT SUM(Population) AS ContinentPopulation FROM country WHERE Continent = '" + continentName + "'";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                long continentPopulation = rset.getLong("ContinentPopulation");
                // Print the continent population
                System.out.println(continentName + " Population: " + continentPopulation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the population of the continent");
        }
    }

    public void getRegionPopulation(String regionName) {
        long regionPopulation = 0;
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve the population of the region
            String strSelect = "SELECT SUM(Population) AS RegionPopulation FROM country WHERE Region = '" + regionName + "'";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                regionPopulation = rset.getLong("RegionPopulation");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the population of the region");
        }
    }

    public void getCountryPopulation(String countryName) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve the population of the country
            String strSelect = "SELECT Population FROM country WHERE Name = '" + countryName + "'";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                long countryPopulation = rset.getLong("Population");
                // Print the country population
                System.out.println(countryName + " Population: " + countryPopulation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the population of the country");
        }
    }

    public void getDistrictPopulation(String districtName) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve the population of the district
            String strSelect = "SELECT Population FROM city WHERE District = '" + districtName + "'";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            long districtPopulation = 0;
            while (rset.next()) {
                districtPopulation += rset.getLong("Population");
            }
            // Print the district population
            System.out.println(districtName + " Population: " + districtPopulation);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the population of the district");
        }
    }

    public void getCityPopulation(String cityName) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query string to retrieve the population of the city
            String strSelect = "SELECT Population FROM city WHERE Name = '" + cityName + "'";

            // Execute the SQL statement and retrieve the result set
            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                long cityPopulation = rset.getLong("Population");
                // Print the city population
                System.out.println(cityName + " Population: " + cityPopulation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the population of the city");
        }
    }

    public void getChineseSpeakers() {
        long worldPopulation = getWorldPopulation();
        if (worldPopulation > 0) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();

                // Create the SQL query string to retrieve the total number of Chinese speakers
                String strSelect = "SELECT SUM(Percentage * Population / 100) AS ChineseSpeakers FROM countrylanguage cl " +
                        "JOIN country c ON cl.CountryCode = c.Code " +
                        "WHERE cl.Language = 'Chinese'";

                // Execute the SQL statement and retrieve the result set
                ResultSet rset = stmt.executeQuery(strSelect);

                if (rset.next()) {
                    long chineseSpeakers = rset.getLong("ChineseSpeakers");
                    double chinesePercentage = (double) chineseSpeakers / worldPopulation * 100.0;
                    chinesePercentage = Math.round(chinesePercentage * 10.0) / 10.0; // Round to 1 decimal place
                    System.out.println("Chinese Speakers: " + chineseSpeakers + " | " + chinesePercentage + "%");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get the number of Chinese speakers");
            }
        } else {
            System.out.println("Failed to get the world population");
        }
    }


    public void getEnglishSpeakers() {
        long worldPopulation = getWorldPopulation();
        if (worldPopulation > 0) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();

                // Create the SQL query string to retrieve the total number of English speakers
                String strSelect = "SELECT SUM(Percentage * Population / 100) AS EnglishSpeakers FROM countrylanguage cl " +
                        "JOIN country c ON cl.CountryCode = c.Code " +
                        "WHERE cl.Language = 'English'";

                // Execute the SQL statement and retrieve the result set
                ResultSet rset = stmt.executeQuery(strSelect);

                if (rset.next()) {
                    long englishSpeakers = rset.getLong("EnglishSpeakers");
                    double englishPercentage = (double) englishSpeakers / worldPopulation * 100.0;
                    englishPercentage = Math.round(englishPercentage * 10.0) / 10.0; // Round to 1 decimal place
                    System.out.println("English Speakers: " + englishSpeakers + " | " + englishPercentage + "%");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get the number of English speakers");
            }
        } else {
            System.out.println("Failed to get the world population");
        }
    }


    public void getArabicSpeakers() {
        long worldPopulation = getWorldPopulation();
        if (worldPopulation > 0) {
            try {
                // Create an SQL statement
                Statement stmt = con.createStatement();

                // Create the SQL query string to retrieve the total number of Arabic speakers
                String strSelect = "SELECT SUM(Percentage * Population / 100) AS ArabicSpeakers FROM countrylanguage cl " +
                        "JOIN country c ON cl.CountryCode = c.Code " +
                        "WHERE cl.Language = 'Arabic'";

                // Execute the SQL statement and retrieve the result set
                ResultSet rset = stmt.executeQuery(strSelect);

                if (rset.next()) {
                    long arabicSpeakers = rset.getLong("ArabicSpeakers");
                    double arabicPercentage = (double) arabicSpeakers / worldPopulation * 100.0;
                    arabicPercentage = Math.round(arabicPercentage * 10.0) / 10.0; // Round to 1 decimal place
                    System.out.println("Arabic Speakers: " + arabicSpeakers + " | " + arabicPercentage + "%");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Failed to get the number of Arabic speakers");
            }
        } else {
            System.out.println("Failed to get the world population");
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
        System.out.println("Country Report:");
        // Display countries information
        a.displayCountries(countries);

        // Get cities ordered by population
        List<City> cities = a.getCitiesByPopulation();
        System.out.println("\nCity Report:");
        a.displayCities(cities);

        // Get capital cities ordered by population
        List<City> capitalCities = a.getCapitalCitiesByPopulation();
        System.out.println("\nCapital City Report:");
        // Display capital cities information
        a.displayCapitalCities(capitalCities);

        int n = 10;
        // Get top N capital cities ordered by population
        List<City> topNCapitalCities = a.getTopNCapitalCitiesByPopulation(n);
        System.out.println("\nTop N Capital City Report:");
        // Display top N capital cities information
        a.displayCapitalCities(topNCapitalCities);

        // Get population details of people, people living in cities, and people not living in cities in each country
        List<Population> populations = a.getPopulations();
        System.out.println("\nCountry Population Report:");
        // Display country population details
        a.displayPopulations(populations);

        // Get the population of the world
        long worldPopulation = a.getWorldPopulation();
        System.out.println("World Population: " + worldPopulation);

        // Get the population of a continent
        String continentName = "Asia";
        a.getContinentPopulation(continentName);

        // Get the population of a region
        String regionName = "Eastern Asia";
        a.getRegionPopulation(regionName);

        // Get the population of a country
        String countryName = "China";
        a.getCountryPopulation(countryName);

        // Get the population of a district
        String districtName = "California";
        a.getDistrictPopulation(districtName);

        // Get the population of a city
        String cityName = "London";
        a.getCityPopulation(cityName);

        // Get the number of language speakers and their percentages
        System.out.println("\nLanguage Speakers Report:");
        a.getChineseSpeakers();
        a.getEnglishSpeakers();
        a.getArabicSpeakers();

        // Disconnect from database
        a.disconnect();
    }
}
