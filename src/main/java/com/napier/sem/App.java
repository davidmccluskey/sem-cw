package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Create new Application
        int n;
        App a = new App();

        // Connect to database
        if (args.length < 1) {
            a.connect("localhost:33060");
        } else {
            a.connect(args[0]);
        }

        //Listing world population from largest to smallest
        System.out.println("\nListing population from largest to smallest");
        ArrayList<Country> countries = a.worldLtoS();
        a.displayCountries(countries);

        //Listing top N countries per continent
        n = 3;
        System.out.println("\nTOP " + n + " countries per continent:");
        ArrayList<Country> topNContinent = a.topNContinent(n);
        a.displayCountries(topNContinent);

        //Listing all countries in a region
        String region = "Eastern Europe";
        System.out.println("\nAll Countries in" + region + ": ");
        ArrayList<Country> AllCountriesInRegion = a.AllCountriesInRegion(region);
        a.displayCountriesByRegion(AllCountriesInRegion);

        //Listing top N countries in the world
        n = 5;
        System.out.println("\nTop " + n + " countries in the world.");
        ArrayList<Country> topNcountriesWorld = a.topNWorld(n);
        a.displayCountries(topNcountriesWorld);

        String continent = "Europe";
        System.out.println("Listing all countries on a continent.");
        ArrayList<Country> CountriesContinentsLtoS = a.CountriesContinentLtoS(continent);
        a.displayCountries(CountriesContinentsLtoS);

        //Listing all cities in the world
        System.out.println("\nListing all cities in the world");
        ArrayList<City> citiesInWorldLtoS = a.citiesInWorldLtoS();
        a.displayCities(citiesInWorldLtoS);

        //Listing all cities in a continent
        continent = "Africa";
        System.out.println("\nListing all cities in " + continent);
        ArrayList<City> citiesInContinentLtoS = a.CitiesOnContinentLtoS(continent);
        a.displayCities(citiesInContinentLtoS);

        //Listing all cities in a region
        region = "Eastern Europe";
        System.out.println("\nListing all cities in " + region);
        ArrayList<City> citiesInRegion = a.AllCityInRegionLtoS(region);
        a.displayCities(citiesInRegion);


        //Listing all cities in a country
        String country = "South Africa";
        System.out.println("\nListing all cities in " + country);
        ArrayList<City> CityInCountry = a.AllCityInCountryLtoS(country);
        a.displayCities(CityInCountry);

        n = 3;
        ArrayList<City> topNpopulatedCities = a.topNpopulatedCities(n);
        a.displayCities(topNpopulatedCities);

        n = 3;
        ArrayList<City> topNpopulatedCitiesInContinent = a.topNpopulatedCitiesInContinent(n);
        a.displayCities(topNpopulatedCitiesInContinent);


        //Listing all cities in a district
        n = 3;
        String district = "Western Cape";
        System.out.println("\nListing top " + n + " cities in " + district);
        ArrayList<City> topNCitiesDistrict = a.topNCitiesDistrict(n, district);
        a.displayCities(topNCitiesDistrict);

        //Listing all cities in a region
        region = "Eastern Europe";
        n = 3;
        System.out.println("\nListing Top " + n + " cities in " + region);
        ArrayList<City> topNCitiesRegion = a.topNCitiesRegion(n, region);
        a.displayCities(topNCitiesRegion);

        //Listing Top N Capital Cities
        n = 5;
        System.out.println("\nListing Top " + n + " capital cities in the world.");
        ArrayList<City> topNCitiesWorld = a.topNCapitalWorld(n);
        a.displayCities(topNCitiesWorld);

        System.out.println("\nListing all capital cities from largest population to smallest.");
        ArrayList<City> CapitalLtoS = a.CapitalsWorldLtoS();
        a.displayCities(CapitalLtoS);


        //Listing the capital city for each country in a continent from largest to smallest
        continent = "Europe";
        System.out.println("\nListing all capital cities in " + continent + " from largest population to smallest");
        ArrayList<City> ContCapLtoS = a.CapitalsContinentLtoS(continent);
        a.displayCitiesByCountry(ContCapLtoS);

        //Listing the capital city for each country in a region from largest to smallest
        region = "Western Europe";
        System.out.println("\nListing all capital cities in " + region + " from largest population to smallest");
        ArrayList<City> RegionCapLtoS = a.CapitalsRegionLtoS(region);
        a.displayCities(RegionCapLtoS);

        n=3;
        ArrayList<City> topNpopulatedCitiesCoun = a.topNpopulatedCitiesInCountry(n, "Germany");
        a.displayCities(topNpopulatedCitiesCoun);

        //Listing all cities in a district
        System.out.println("\nListing all cities in the district");
        ArrayList<City> AllCityInDistrictLtoS= a.AllCityInDistrictLtoS("Zuid-Holland");
        a.displayCities(AllCityInDistrictLtoS);

        n = 5;
        System.out.println("\nListing top N capital cities per continent.");
        ArrayList<City> topNCapitalContinent = a.topNCapitalContinent(n);
        a.displayCities(topNCapitalContinent);


        n = 5;
        System.out.println("\nListing top N capital cities in region");
        ArrayList<City> topNCapitalregion = a.TopNpopulatedCapitalCitiesInRegion(n,region);
        a.displayCities(topNCapitalregion);

        //Listing the population of people, people in cities, and people not living in cities in each continent
        System.out.println("\nListing the population of people, people in cities, and people not living in cities in each continent.");
        ArrayList<Population> populationPerContinent = a.populationPerContinent();
        a.displayPop(populationPerContinent);
        System.out.println("\nPrinting the info for Chinese, English, Spanish, Hindi and Arabic as well as their corresponding percentages in the world.");
        ArrayList<countryLanguage> languages = a.languages();
        a.DisplayLanguage(languages);


        System.out.println("\nListing the population of the world, a given continent, a given region, a given country, a given district and a given city.");
        Given given = a.populationContinentRegionCountryDistrictCity("Africa", "Eastern Europe", "Poland", "Scotland", "Tokyo");
        a.displayGivens(given);
        //Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location) {
        try {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
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
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Country> worldLtoS() {
        try {
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
            while (rset.next()) {
                Country country = new Country();
                country.Name = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Population = rset.getInt("Population");
                countries.add(country);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public void displayGivens(Given given)
    {
        System.out.println(String.format("%-10s %-15s %-20s %-25s %-30s %-35s", "World", given.continent, given.Region, given.country, given.district, given.city));
        String emp_string =
                String.format("%-10s %-15s %-20s %-25s %-30s %-35s",
                        given.worldPopulation, given.continentPopulation, given.regionPopulation, given.countryPopulation, given.districtPopulation, given.cityPopulation);
        System.out.println(emp_string);
    }

    /**
     * Prints a list of countries.
     *
     * @param countries The list of employees to print.
     */
    public void displayCountries(ArrayList<Country> countries) {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s", "Name", "Continent", "Population"));
        // Loop over all employees in the list
        for (Country country : countries) {
            String emp_string =
                    String.format("%-10s %-15s %-20s",
                            country.Name, country.Continent, country.Population);
            System.out.println(emp_string);
        }
    }

    public void displayCountriesByRegion(ArrayList<Country> countries) {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s", "Name", "Region", "Population"));
        // Loop over all employees in the list
        for (Country country : countries) {
            String emp_string =
                    String.format("%-10s %-15s %-20s",
                            country.Name, country.Region, country.Population);
            System.out.println(emp_string);
        }
    }

    public void displayCitiesByCountry(ArrayList<City> cities) {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s", "Name", "Country Code", "Population"));
        // Loop over all employees in the list
        for (City city : cities) {
            String emp_string =
                    String.format("%-10s %-15s %-20s",
                            city.name, city.CountryCode, city.population);
            System.out.println(emp_string);
        }
    }

    public void DisplayLanguage(ArrayList<countryLanguage> languages) {
        System.out.println(String.format("%-15s %-20s %-15s", "Language", "Percentage", "Population"));
        for (countryLanguage language : languages) {
            String emp_string =
                    String.format("%-15s %-20s %-15s",
                            language.Language, language.Percentage +"%", language.Population);
            System.out.println(emp_string);
        }
    }

    public void displayPop(ArrayList<Population> pops){
        System.out.println(String.format("%-15s %-20s %-15s %-20s","Continent", "Continent Pop", "City Pop", "Not City Pop"));
        for (Population pop : pops) {
            String emp_string =
                    String.format("%-15s %-20s %-15s %-20s",
                            pop.continent, pop.continentPopulation, pop.cityPopulation, pop.nonCityPopulation);
            System.out.println(emp_string);
        }
    }




    public ArrayList<Country> topNContinent(int n) {
        try {
            String[] continents = new String[]{"Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"};

            ArrayList<Country> countries = new ArrayList<Country>();

            for (String cont : continents) {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Name, Continent, Population "
                                + "FROM country "
                                + "WHERE Continent = '" + cont + "' "
                                + "ORDER BY Population DESC LIMIT " + n;
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information

                while (rset.next()) {
                    Country country = new Country();
                    country.Name = rset.getString("Name");
                    country.Continent = rset.getString("Continent");
                    country.Population = rset.getInt("Population");
                    countries.add(country);
                }
            }


            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<Country> AllCountriesInRegion(String region) {
        try {

            ArrayList<Country> countries = new ArrayList<Country>();

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =

                    "SELECT Name, Region, Population "
                            + "FROM country "
                            + "WHERE Region = '" + region + "' "
                            + "ORDER BY Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                Country country = new Country();
                country.Name = rset.getString("Name");
                country.Region = rset.getString("Region");
                country.Population = rset.getInt("Population");
                countries.add(country);
            }
            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> citiesInWorldLtoS() {

        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Name, District, Population "
                            + "FROM city "
                            + "ORDER BY Population DESC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<City> cities = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public void displayCities(ArrayList<City> cities) {
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s", "Name", "District", "Population"));
        // Loop over all employees in the list
        for (City city : cities) {
            String emp_string =
                    String.format("%-10s %-15s %-20s",
                            city.name, city.district, city.population);
            System.out.println(emp_string);
        }
    }

    public ArrayList<Country> topNWorld(int n) {
        try {
            Statement stmt = con.createStatement();
            String strtopNWorld =
                    "SELECT Name, Continent, Population "
                            + "FROM country "
                            + "ORDER BY Population DESC LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strtopNWorld);
            ArrayList<Country> topNcountries = new ArrayList<Country>();
            while (rset.next()) {
                Country country = new Country();
                country.Name = rset.getString("Name");
                country.Continent = rset.getString("Continent");
                country.Population = rset.getInt("Population");
                topNcountries.add(country);
            }
            return topNcountries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<Country> CountriesContinentLtoS(String passedCountry) {
        try {
            String[] continents = new String[]{"Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"};

            ArrayList<Country> countries = new ArrayList<Country>();

            for (String cont : continents) {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT Name, Continent, Population "
                                + "FROM country "
                                + "WHERE Continent = '" + passedCountry + "' "
                                + "ORDER BY Population DESC  ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information

                while (rset.next()) {
                    Country country = new Country();
                    country.Name = rset.getString("Name");
                    country.Continent = rset.getString("Continent");
                    country.Population = rset.getInt("Population");
                    countries.add(country);
                }
            }


            return countries;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }


    }

    public ArrayList<City> CitiesOnContinentLtoS(String continent) {
        try {
            String[] continents = new String[]{"Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"};

            ArrayList<City> cities = new ArrayList<>();

            for (String cont : continents) {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.Name, city.District, city.Population "
                                + "FROM city, country "
                                + "WHERE country.Continent = '" + continent + "' "
                                + "AND country.Code = city.CountryCode "
                                + "ORDER BY Population DESC  ";
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information

                while (rset.next()) {
                    City city = new City();
                    city.name = rset.getString("city.Name");
                    city.district = rset.getString("city.District");
                    city.population = rset.getInt("city.Population");
                    cities.add(city);
                }
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> AllCityInRegionLtoS(String region) {
        try {

            ArrayList<City> cities = new ArrayList<City>();

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =

                    "SELECT city.Name, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE country.Region = '" + region + "' "
                            + "AND  city.CountryCode = country.Code "
                            + "ORDER BY Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("city.Name");
                city.district = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> AllCityInCountryLtoS(String country) {
        try {

            ArrayList<City> cities = new ArrayList<City>();

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =

                    "SELECT city.Name, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE country.Name = '" + country + "' "
                            + "AND  city.CountryCode = country.Code "
                            + "ORDER BY Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("city.Name");
                city.district = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }


    public ArrayList<City> topNpopulatedCities(int n) {
        try {
            Statement stmt = con.createStatement();
            String strtopNWorld =
                    "SELECT Name, District,Population "
                            + "FROM city "
                            + "ORDER BY Population DESC LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strtopNWorld);
            ArrayList<City> topNpopulationCities = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                topNpopulationCities.add(city);
            }
            return topNpopulationCities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> topNCitiesDistrict(int n, String district) {
        try {
            Statement stmt = con.createStatement();
            String strtopNWorld =
                    "SELECT Name, District, Population "
                            + "FROM city "
                            + "WHERE District = '" + district + "' "
                            + "ORDER BY Population DESC LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strtopNWorld);
            ArrayList<City> topNcites = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                topNcites.add(city);
            }
            return topNcites;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> topNCapitalWorld(int n) {
        try {
            Statement stmt = con.createStatement();
            String strtopNWorld =
                    "SELECT city.Name, District, city.Population "
                            + "FROM country, city "
                            + "WHERE city.ID = country.Capital "
                            + "ORDER BY city.Population DESC LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strtopNWorld);
            ArrayList<City> topNCities = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                topNCities.add(city);
            }
            return topNCities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> topNCitiesRegion(int n, String region) {
        try {
            Statement stmt = con.createStatement();
            String strtopNWorld =
                    "SELECT city.Name, city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE Region = '" + region + "' "
                            + "AND city.CountryCode = country.Code "
                            + "ORDER BY Population DESC LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strtopNWorld);
            ArrayList<City> topNcites = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                topNcites.add(city);
            }
            return topNcites;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> CapitalsWorldLtoS() {
        try {
            Statement stmt = con.createStatement();
            String strLtoS =
                    "SELECT city.Name, District, city.Population "
                            + "FROM city, country "
                            + "WHERE city.ID = country.Capital "
                            + "ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(strLtoS);
            ArrayList<City> LtoS = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                LtoS.add(city);
            }
            return LtoS;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> CapitalsContinentLtoS(String cont) {
        try {
            Statement stmt = con.createStatement();
            String strLtoS =
                    "SELECT city.Name, CountryCode, city.Population "
                            + "FROM city,country "
                            + "WHERE Continent = '" + cont + "' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(strLtoS);
            ArrayList<City> LtoS = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.CountryCode = rset.getString("CountryCode");
                city.population = rset.getInt("Population");
                LtoS.add(city);
            }
            return LtoS;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> CapitalsRegionLtoS(String region) {
        try {
            Statement stmt = con.createStatement();
            String strLtoS =
                    "SELECT city.Name, District, city.Population "
                            + "FROM city,country "
                            + "WHERE Region = '" + region + "' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY Population DESC";

            ResultSet rset = stmt.executeQuery(strLtoS);
            ArrayList<City> LtoS = new ArrayList<City>();
            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                LtoS.add(city);
            }
            return LtoS;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City>topNpopulatedCitiesInContinent(int n)
    {
        try
        {

            String[] continents = new String[]{"Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"};
            ArrayList<City> topNpopulationCities = new ArrayList<City>();

            for (String cont : continents)
            {
                Statement stmt = con.createStatement();
                String strtopNWorld =
                        "SELECT city.Name, city.District, city.Population "
                                + "FROM city, country "
                                + "WHERE Continent = '" + cont +"' "
                                + "AND city.CountryCode = country.Code "
                                + "ORDER BY Population DESC LIMIT " + n;

                ResultSet rset = stmt.executeQuery(strtopNWorld);

                while (rset.next()) {
                    City city = new City();
                    city.name = rset.getString("Name");
                    city.district=rset.getString("District");
                    city.population = rset.getInt("Population");
                    topNpopulationCities.add(city);
                }
            }
            return topNpopulationCities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City>topNpopulatedCitiesInCountry(int n, String country)
    {
        try
        {


            ArrayList<City> topNpopulationCities = new ArrayList<City>();


                Statement stmt = con.createStatement();
                String strtopNWorld =
                        "SELECT city.Name,city.District, city.Population "
                                + "FROM city, country "
                                + "WHERE country.Name = '" + country+"' "
                                + "AND city.CountryCode = country.code "
                                + "ORDER BY Population DESC LIMIT " + n;

                ResultSet rset = stmt.executeQuery(strtopNWorld);

                while (rset.next()) {
                    City city = new City();
                    city.name = rset.getString("city.Name");
                    city.district=rset.getString("city.District");
                    city.population = rset.getInt("city.Population");
                    topNpopulationCities.add(city);
                }

            return topNpopulationCities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }


    public ArrayList<City>AllCityInDistrictLtoS(String district)
    {
        try {

            ArrayList<City> cities = new ArrayList<City>();

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =

                    "SELECT city.Name, city.District, city.Population "
                            + "FROM city "
                            + "WHERE city.district = '" + district +"' "
                            + "ORDER BY Population DESC ";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next())
            {
                City city = new City();
                city.name = rset.getString("city.Name");
                city.district = rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                cities.add(city);
            }
            return cities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City> topNCapitalContinent(int n)
    {
        try {
            String[] continents = new String[]{"Asia", "Europe", "North America", "Africa", "Oceania", "Antarctica", "South America"};

            ArrayList<City> cities = new ArrayList<City>();

            for (String cont : continents) {
                // Create an SQL statement
                Statement stmt = con.createStatement();
                // Create string for SQL statement
                String strSelect =
                        "SELECT city.Name, city.District, city.Population "
                                + "FROM city, country "
                                + "WHERE country.Continent = '" + cont +"' "
                                + "AND city.ID = country.Capital "
                                + "ORDER BY Population DESC LIMIT " + n;
                // Execute SQL statement
                ResultSet rset = stmt.executeQuery(strSelect);
                // Extract employee information

                while (rset.next()) {
                    City city = new City();
                    city.name = rset.getString("Name");
                    city.district = rset.getString("District");
                    city.population = rset.getInt("Population");
                    cities.add(city);
                }
            }


            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<City>TopNpopulatedCapitalCitiesInRegion(int n, String region)
    {
        try
        {


            ArrayList<City> topNpopulationCities = new ArrayList<City>();


            Statement stmt = con.createStatement();
            String strtopNWorld =
                    "SELECT city.Name,city.District, city.Population "
                            + "FROM city, country "
                            + "WHERE Region = '" + region+"' "
                            + "AND city.ID = country.Capital "
                            + "ORDER BY Population DESC LIMIT " + n;

            ResultSet rset = stmt.executeQuery(strtopNWorld);

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("city.Name");
                city.district=rset.getString("city.District");
                city.population = rset.getInt("city.Population");
                topNpopulationCities.add(city);
            }

            return topNpopulationCities;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }





    public ArrayList<Population> populationPerContinent(){
        try {

            ArrayList<Population> pops = new ArrayList<Population>();


            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT Continent, SUM(country.Population) AS 'Cont', SUM(city.Population) AS 'City', (SUM(country.Population) - SUM(city.Population)) AS 'Not City Pop' "
                            + "FROM country, city "
                            + "GROUP BY Continent";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information

            while (rset.next()) {
                Population pop = new Population();
                pop.continent =  rset.getString("Continent");
                pop.continentPopulation = rset.getString("Cont");
                pop.cityPopulation = rset.getString("City");
                pop.nonCityPopulation = rset.getString("Not City Pop");
                pops.add(pop);
            }



            return pops;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }

    public ArrayList<countryLanguage> languages() {
        //As an organisation, we want information on the number of people who speak Chinese, English, Hindi,
        // Spanish and Arabic, including the percentage of the world population that speaks these languages.
        try {
            String[] requiremets = new String[]{"Chinese", "English", "Hindi", "Spanish", "Arabic"};
            ArrayList<countryLanguage> languages = new ArrayList<countryLanguage>();
            for(String lang : requiremets)
            {
                Statement stmt = con.createStatement();
                String strSelect = "SELECT Language, (SUM((Population * Percentage)/100)/6078749450)*100 As Percentage,SUM((Population * Percentage)/100) As Population "
                        + "FROM countrylanguage, country "
                        + "WHERE Language = '" + lang + "' "
                        + "AND country.code = countrylanguage.CountryCode "
                        + "GROUP BY Language";
                ResultSet rset = stmt.executeQuery(strSelect);

                while (rset.next()) {
                    countryLanguage language = new countryLanguage();
                    language.Language = rset.getString("Language");
                    language.Percentage = rset.getInt("Percentage");
                    language.Population = rset.getInt("Population");
                    languages.add(language);
                }
            }
            return languages;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }
    public Given populationContinentRegionCountryDistrictCity(String continent, String region, String country, String district, String city)
    {
        Given given = new Given();
        try {
            Statement stmt = con.createStatement();
            String worldPop =
                    "SELECT SUM(country.Population) AS Population "
                    +"FROM country ";
            ResultSet world = stmt.executeQuery(worldPop);
            while (world.next())
            {
                given.worldPopulation = world.getLong("Population");
            }

            String continentPop =
                    "SELECT SUM(Population) As Population "
                    +"FROM country "
                    +"WHERE Continent = '" + continent + "' ";
            ResultSet rsetCont = stmt.executeQuery(continentPop);
            while (rsetCont.next())
            {
                given.continent = continent;
                given.continentPopulation = rsetCont.getLong("Population");
            }

            String regionPop =
                    "SELECT SUM(Population) As Population "
                            +"FROM country "
                            +"WHERE Region = '" + region + "' ";
            ResultSet rsetRegion = stmt.executeQuery(regionPop);
            while (rsetRegion.next())
            {
                given.Region = region;
                given.regionPopulation = rsetRegion.getInt("Population");
            }

            String countryPop =
                    "SELECT Population "
                            +"FROM country "
                            +"WHERE Name = '" + country + "' ";
            ResultSet rsetCountry = stmt.executeQuery(countryPop);
            while (rsetCountry.next())
            {
                given.country = country;
                given.countryPopulation = rsetCountry.getInt("Population");
            }

            String districtPop =
                    "SELECT SUM(Population) As Population "
                            +"FROM city "
                            +"WHERE District = '" + district + "' ";
            ResultSet rsetDistrict = stmt.executeQuery(districtPop);
            while (rsetDistrict.next())
            {
                given.district = district;
                given.districtPopulation = rsetDistrict.getInt("Population");
            }

            String cityPop =
                    "SELECT SUM(Population) As Population "
                            +"FROM city "
                            +"WHERE Name = '" + city + "' ";
            ResultSet rsetCity = stmt.executeQuery(cityPop);
            while (rsetCity.next())
            {
                given.city = city;
                given.cityPopulation = rsetCity.getInt("Population");
            }
            return given;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get details");
            return null;
        }
    }
}