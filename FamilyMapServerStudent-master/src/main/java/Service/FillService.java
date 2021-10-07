package Service;

import Result.FillResult;
import Dao.*;
import Model.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.*;
import java.sql.Connection;

/**
 * Fill class in service package
 */
public class FillService {
    private Database db;
    private Connection conn;
    private EventDao eDao;
    private PersonDao pDao;
    private UserDao uDao;
    private Persons person;
    private Events bEvent;
    private String[] lastNameList;
    private String[] menFirstNameList;
    private String[] womenFirstNameList;
    private Location[] locationList;
    private FillResult result;
    Location bLocation;
 //   private String userName;
 //   private String personID;
    private int numP;
    private int numE;
    private int userBirth = 1990;
    Random random;
    boolean success;

    public FillService() throws FileNotFoundException {
        numP = 0;
        numE = 0;
        success = false;
        random = new Random();
        generateLocations();
        generateNames();
        result = new FillResult();
    }
    /**
     * @param associatedUsername
     * @param generation
     * @return
     */
    public FillResult fill(String associatedUsername, int generation) {
        db = new Database();
        try {
            if (generation < 0) {
                return new FillResult("error - Invalid generation. Must be 0 or positive integer number");
            }
            conn = db.openConnection();
            pDao = new PersonDao(conn);
            eDao = new EventDao(conn);
            uDao = new UserDao(conn);
            Users user = uDao.findUser(associatedUsername);
            if (user == null) {
                db.closeConnection(false);
                return new FillResult("error - Invalid associatedUsername");
            }

            //Delete Person and Event related to the associatedUsername
            eDao.deleteEvent(associatedUsername);
            pDao.deletePerson(associatedUsername);
            //uDao.clearUser();
            //pDao.deletePerson(associatedUsername);
            //eDao.deleteEvent(associatedUsername);

            //Create Person
            person = new Persons(user.getPersonID(), associatedUsername, user.getFirstName(), user.getLastName(), user.getGender());
            numP++;
  //          pDao.insert(person);
  //          numP++;

            //Birthday event
            bLocation = locationList[random.nextInt(locationList.length)];
            String eventID = UUID.randomUUID().toString();
            bEvent = new Events(eventID, user.getUsername(), user.getPersonID(), bLocation.getLatitude(),
                    bLocation.getLongitude(), bLocation.getCountry(), bLocation.getCity(), "Birth", userBirth);
            eDao.insert(bEvent);
            numE++;
            db.closeConnection(true);
            success = true;
            createGeneration(person, generation, userBirth);
            return new FillResult("Successfully added " + numP + " persons and " + numE + " events to the database.", success);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
        }
        return new FillResult("error - Internal error Encountered while filling");
    }

    // Ancestor helper//
    private void createGeneration(Persons person, int gen, int byear) throws DataAccessException {
        random = new Random();
        if (gen == 0) {
            return;
        }
        db = new Database();
        try {
            String fatherName;
            String motherName;
            String lastName = "";
            int birthYear = byear - 26;
            int deathYear = birthYear + 65;
            int marriageYear = birthYear + 22;
            int updateGen = 0;
            conn = db.openConnection();
            pDao = new PersonDao(conn);
            eDao = new EventDao(conn);

            //Generate lastName//
            /*if (person.getGender() == "m" || person.getGender() == "M") {
                lastName = person.getLastName();
            }
            else if (person.getGender() == "f" || person.getGender() == "F") {
                lastName = lastNameList[random.nextInt(lastNameList.length)];
            }*/
         /* else {
                db.closeConnection(false);
                throw new DataAccessException("Error - Invalid input for gender");
            } */

            String pID;

            //Create father//
            random = new Random();
            fatherName = menFirstNameList[random.nextInt(menFirstNameList.length)];
            Persons father = new Persons(person.getAssociatedUsername(), fatherName, lastName, "m");
            father.setLastName(person.getLastName());
            pID = UUID.randomUUID().toString();
            father.setPersonID(pID);

            //Create mother//
            random = new Random();
            motherName = womenFirstNameList[random.nextInt(womenFirstNameList.length)];
            Persons mother = new Persons(person.getAssociatedUsername(), motherName, lastName, "f");
            mother.setLastName(lastNameList[random.nextInt(lastNameList.length)]);
            pID = UUID.randomUUID().toString();
            mother.setPersonID(pID);

            //Set and get fID and mID//
            father.setSpouseID(mother.getPersonID());
            person.setFatherID(father.getPersonID());
            mother.setSpouseID(father.getPersonID());
            person.setMotherID(mother.getPersonID());
            pDao.insert(person);
            numP += 2;

            //Generate birth and death Event//
            //Father's event
            Location fatherLoc = locationList[random.nextInt(locationList.length)];
            Events fatherBirth = new Events(UUID.randomUUID().toString(), father.getAssociatedUsername(), father.getPersonID(),
                    fatherLoc.getLatitude(), fatherLoc.getLongitude(), fatherLoc.getCountry(), fatherLoc.getCity(), "Birth", birthYear - 2);
            fatherLoc = locationList[random.nextInt(locationList.length)];
            Events fatherDeath = new Events(UUID.randomUUID().toString(), father.getAssociatedUsername(), father.getPersonID(),
                    fatherLoc.getLatitude(), fatherLoc.getLongitude(), fatherLoc.getCountry(), fatherLoc.getCity(), "Death", deathYear - 7);

            //Mother's event
            Location motherLoc = locationList[random.nextInt(locationList.length)];
            Events motherBirth = new Events(UUID.randomUUID().toString(), mother.getAssociatedUsername(), mother.getPersonID(),
                    motherLoc.getLatitude(), motherLoc.getLongitude(), motherLoc.getCountry(), motherLoc.getCity(), "Birth", birthYear);
            motherLoc = locationList[random.nextInt(locationList.length)];
            Events motherDeath = new Events(UUID.randomUUID().toString(), mother.getAssociatedUsername(), mother.getPersonID(),
                    motherLoc.getLatitude(), motherLoc.getLongitude(), motherLoc.getCountry(), motherLoc.getCity(), "Death", deathYear);

            //Inserting events
            eDao.insert(fatherBirth);
            eDao.insert(fatherDeath);
            eDao.insert(motherBirth);
            eDao.insert(motherDeath);
            numE += 4;
            db.closeConnection(true);

            //Generate Marriage Event//
            generateMarriage(father, mother, marriageYear);
            updateGen = gen - 1;
            if (updateGen >= 1) {
                createGeneration(father, updateGen, birthYear);
                createGeneration(mother, updateGen, birthYear);
            }
            else if (updateGen == 0) {
                conn = db.openConnection();
                pDao = new PersonDao(conn);
                pDao.insert(father);
                pDao.insert(mother);
                db.closeConnection(true);
            }
        } catch (DataAccessException e) {
            //db.closeConnection(false);
            throw new DataAccessException("error - Program did not generate createGeneration method", e);
        }
    }

    private void generateMarriage(Persons man, Persons woman, int marriageYear) throws DataAccessException {
        try {
            conn = db.openConnection();
            EventDao eDao = new EventDao(conn);
            random = new Random();
            Location marryLoc = locationList[random.nextInt(locationList.length)];
            String mMarryID = UUID.randomUUID().toString();
            String fMarryID = UUID.randomUUID().toString();

            //Creating man marriage event
            Events mEvent = new Events(mMarryID, man.getAssociatedUsername(), man.getPersonID(), marryLoc.getLatitude(), marryLoc.getLongitude(),
                    marryLoc.getCountry(), marryLoc.getCity(), "Marriage", marriageYear);
            eDao.insert(mEvent);
            numE++;

            //Creating woman marriage event
            Events fEvent = new Events(fMarryID, woman.getAssociatedUsername(), woman.getPersonID(), marryLoc.getLatitude(), marryLoc.getLongitude(),
                    marryLoc.getCountry(), marryLoc.getCity(), "Marriage", marriageYear);
            eDao.insert(fEvent);
            numE++;
            db.closeConnection(true);
        } catch (DataAccessException e) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e2) {
                e2.printStackTrace();
            }
            throw new DataAccessException("error - Program did not generate marriage method", e);
        }
    }

    // Name helpers //
    private void generateNames() throws FileNotFoundException {
        File menNames = new File("passoffFiles/mnames.json");
        File womenNames = new File("passoffFiles/fnames.json");
        File lastNames = new File("passoffFiles/snames.json");

        try {
            Gson gson = new Gson();

            FileReader fileReader = new FileReader(menNames);
            JsonReader reader = new JsonReader(fileReader);
            ReadingNames mNames = gson.fromJson(reader, ReadingNames.class);
            menFirstNameList = mNames.getData();

            fileReader = new FileReader(womenNames);
            reader = new JsonReader(fileReader);
            gson = new Gson();
            ReadingNames fNames = gson.fromJson(reader, ReadingNames.class);
            womenFirstNameList = fNames.getData();

            fileReader = new FileReader(lastNames);
            reader = new JsonReader(fileReader);
            gson = new Gson();
            ReadingNames sNames = gson.fromJson(reader, ReadingNames.class);
            lastNameList = sNames.getData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class ReadingNames {
        private String[] data;

        public String[] getData() { return data; }

        public void setData(String[] data) { this.data = data; }

        public ReadingNames(String[] lists) { this.data = lists; }
    }


    // Location helpers //
    public class Location {
        private String country;
        private String city;
        private double latitude;
        private double longitude;

        public String getCountry() { return country; }

        public void setCountry(String country) { this.country = country; }

        public String getCity() { return city; }

        public void setCity(String city) { this.city = city; }

        public double getLatitude() { return latitude; }

        public void setLatitude(double latitude) { this.latitude = latitude; }

        public double getLongitude() { return longitude; }

        public void setLongitude(double longitude) { this.longitude = longitude; }

        public Location() {}
    }

    private void generateLocations() throws FileNotFoundException {
        File fileName = new File("passoffFiles/locations.json");

        try {
            Gson gson = new Gson();
            FileReader fileReader = new FileReader(fileName);
            JsonReader reader = new JsonReader(fileReader);
            ReadingLocations locations = gson.fromJson(reader, ReadingLocations.class);
            locationList = locations.getLists();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public class ReadingLocations {
        private Location[] data;

        public Location[] getLists() { return data; }

        public void setLists(Location[] data) { this.data = data; }

        public ReadingLocations(Location[] data) { this.data = data; }
    }
}
