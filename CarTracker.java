///////////////////////////////////////////////////////////////////////////////
/////////////////////////////// CarTracker Class //////////////////////////////
///////////////////////////////////////////////////////////////////////////////

import java.util.*;

public class CarTracker {

  // The Database class contains two MinPQs to hold price and mileage data
  public static Database priorityQueue = new Database();

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);
    String vin_number = "";
    int choice = 0;

///////////////////////////////////////////////////////////////////////////////

    while (choice != 8) {

      System.out.println();
      System.out.println("------------ CarTracker Menu ------------");
      System.out.println();
      System.out.println("1. Add a car");
      System.out.println("2. Update a car");
      System.out.println("3. Remove a car");
      System.out.println("4. Get lowest price");
      System.out.println("5. Get lowest mileage");
      System.out.println("6. Get lowest price by make/model");
      System.out.println("7. Get lowest mileage by make/model");
      System.out.println("8. Exit");
      System.out.println();
      System.out.println("-----------------------------------------");
      System.out.println();
      System.out.print("Please select a numeric option: ");
      choice = input.nextInt();
      input.nextLine();
      System.out.println();

///////////////////////////////////////////////////////////////////////////////

      switch (choice) {

        case 1: // Adds a single car to each MinPQ in the Database class

          Car car = new Car();
          System.out.println();
          System.out.print("Enter VIN: ");
          car.setVIN(input.nextLine().toUpperCase());
          System.out.print("Enter Make: ");
          car.setMake(input.nextLine().toUpperCase());
          System.out.print("Enter Model: ");
          car.setModel(input.nextLine().toUpperCase());
          System.out.print("Enter Price: ");
          car.setPrice(input.nextInt());
          System.out.print("Enter Mileage: ");
          car.setMileage(input.nextInt());
          input.nextLine();
          System.out.print("Enter Color: ");
          car.setColor(input.nextLine().toUpperCase());
          System.out.println();
          priorityQueue.add(car);
          System.out.println("Car successfully added to database...");
          System.out.println();
          System.out.println(car);
          break;

///////////////////////////////////////////////////////////////////////////////

        case 2: // Updates a specified car and updates the associated MinPQs

          System.out.print("Enter VIN to Update: ");
          vin_number = input.nextLine().toUpperCase();
          System.out.println();
          System.out.println("----------- Update Menu -----------");
          System.out.println();
          System.out.println("1. Update Price");
          System.out.println("2. Update Mileage");
          System.out.println("3. Update Color");
          System.out.println("4. Exit");
          System.out.println();
          System.out.println("-----------------------------------");
          System.out.println();
          System.out.print("Please Select a Numeric Option: ");
          choice = input.nextInt();
          input.nextLine();
          System.out.println();
          System.out.print("Please Enter New Value: ");
          String value = input.nextLine().toUpperCase();
          System.out.println();

          if (choice == 1) { // Update the price
            priorityQueue.update(vin_number,Database.Fields.PRICE,value);
            break;
          }
          else if (choice == 2) { // Update the mileage
            priorityQueue.update(vin_number,Database.Fields.MILEAGE,value);
            break;
          }
          else if (choice == 3) { // Update the color
            priorityQueue.update(vin_number,Database.Fields.COLOR,value);
            break;
          }
          else if (choice == 4) {
            System.out.println("Exiting Update Menu...");
            break;
          }
          else {
            System.out.println("Invalid Selection...");
            break;
          }

///////////////////////////////////////////////////////////////////////////////

        case 3: // Remove a specified car from all associated MinPQs

          System.out.print("Enter VIN to remove: ");
          vin_number = input.nextLine().toUpperCase();
          priorityQueue.remove(vin_number);
          System.out.println();
          break;

///////////////////////////////////////////////////////////////////////////////

        case 4: // Retrieve the lowest priced car from the associated MinPQ

          Car lowestPricedCar = priorityQueue.minimumPrice();
          System.out.println("Lowest price: " + "\n\n" + lowestPricedCar.toString());
          break;

///////////////////////////////////////////////////////////////////////////////

        case 5: // Retrieve the lowest mileage car from the assocaited MinPQ

          Car lowestMileageCar = priorityQueue.minimumMileage();
          System.out.println("Lowest mileage: " + "\n\n" + lowestMileageCar.toString());
          break;

///////////////////////////////////////////////////////////////////////////////

        case 6: // Retrieve the lowest priced car based on a specified make and model

          System.out.print("Enter Make: ");
          String make = input.nextLine().toUpperCase();
          System.out.print("Enter Model: ");
          String model = input.nextLine().toUpperCase();
          System.out.println();
          Car lowestPricedCarByMakeModel = priorityQueue.priceMakeModel(make,model);
          System.out.println("Lowest price by make/model:" + "\n\n");
          System.out.println(lowestPricedCarByMakeModel);
          break;

///////////////////////////////////////////////////////////////////////////////

        case 7: // Retrieve the lowest mileage car based on a specified make and model

          System.out.print("Enter Make: ");
          make = input.nextLine().toUpperCase();
          System.out.print("Enter Model: ");
          model = input.nextLine().toUpperCase();
          System.out.println();
          Car lowestMileageCarByMakeModel = priorityQueue.mileageMakeModel(make,model);
          System.out.println("Lowest mileage by make/model:" + "\n\n");
          System.out.println(lowestMileageCarByMakeModel);
          break;

///////////////////////////////////////////////////////////////////////////////

        case 8: // Terminate program

          System.out.println("Exiting database...");
          System.out.println();
          System.exit(0);

///////////////////////////////////////////////////////////////////////////////

        default: // Reset menu on invalid selection

          System.out.println();
          System.out.println("Please enter a valid menu option...");
          break;
      }
    }
  }

///////////////////////////////////////////////////////////////////////////////
/////////////////////////////// Database Class ////////////////////////////////
///////////////////////////////////////////////////////////////////////////////

  public static class Database {

    public enum Fields { PRICE,
                         MILEAGE,
                         COLOR }

    // Two MinPQs to hold price and mileage data
    MinPQ priceQueue = new MinPQ();
    MinPQ mileageQueue = new MinPQ();

    ArrayList<Car> cars = new ArrayList<Car>();

    public int size = 0;

///////////////////////////////////////////////////////////////////////////////

    public Database() {
      int size = 0;
    }

///////////////////////////////////////////////////////////////////////////////

    // Add a car object to each MinPQ
    public void add(Car car) {

      String vin_number = car.getVIN();

      // Ensure VIN number follows specified conventions
      if (vin_number.length() != 17 || vin_number.contains("I") || vin_number.contains("O") || vin_number.contains("Q")) {
        System.out.println("Invalid VIN Number...");
      }

      if (isFound(vin_number)) {
        System.out.println("VIN Duplicate");
      }

      cars.add(car);

      Sort carPrice = new Sort(size,car.getPrice());
      priceQueue.insert(carPrice);

      Sort carMileage = new Sort(size,car.getMileage());
      mileageQueue.insert(carMileage);

      size++;
    }

///////////////////////////////////////////////////////////////////////////////

    // Retrieve the lowest mileage car in the MinPQ
    public Car minimumMileage() {
      int x = ((Sort)mileageQueue.min()).index;
      return cars.get(x);
    }

///////////////////////////////////////////////////////////////////////////////

    // Retrieve the lowest priced car in the MinPQ
    public Car minimumPrice() {
      int x = ((Sort)priceQueue.min()).index;
      return cars.get(x);
    }

///////////////////////////////////////////////////////////////////////////////

    // Remove a specified car from each MinPQ
    public void remove(String vin_number) {

      boolean check = false;
      int i = 0;

      // Stacks used for data manipulation
      Stack<Sort> temporaryPriceStack = new Stack<Sort>();
      Stack<Sort> temporaryMileageStack = new Stack<Sort>();

      for (Car x: cars) {
        if (vin_number.equals(x.getVIN())) {
          cars.set(i,null);
          check = true;
          break;
        }
        i++;
      }

      if (check == false) {
        System.out.println("Invalid VIN Number");
        return;
      }

      while (true) {
        Sort temporary = (Sort)priceQueue.delMin();
        if (temporary.index == i) {
          break;
        }
        temporaryPriceStack.add(temporary);
      }

      while (!temporaryPriceStack.isEmpty()) {
        priceQueue.insert(temporaryPriceStack.pop());
      }

      while (true) {
        Sort temporary = (Sort)mileageQueue.delMin();
        if (temporary.index == i) {
          break;
        }
        temporaryMileageStack.add(temporary);
      }

      while (!temporaryMileageStack.isEmpty()) {
        mileageQueue.insert(temporaryMileageStack.pop());
      }

      size -= 1;
    }

///////////////////////////////////////////////////////////////////////////////

    // Update a specified value of an added car
    public void update(String vin_number, Fields field, String value) {

      int i = 0;
      Car car = null;
      boolean check = false;

      for (Car x: cars) {
        if (vin_number.equals(x.getVIN())) {
          check = true;
          car = x;
          break;
        }
        i++;
      }

      if (check == false) {
        System.out.println("Invalid VIN Number");
        return;
      }

      Stack<Sort> temporaryStack = null;

      switch (field) {

        case PRICE:

          car.setPrice(Integer.parseInt(value));
          temporaryStack = new Stack<Sort>();

          while (true) {
            Sort sort = (Sort)priceQueue.delMin();
            if (sort.index == i) {
              sort.value = Integer.parseInt(value);
              temporaryStack.add(sort);
              break;
            }
            temporaryStack.add(sort);
          }

          while (!temporaryStack.isEmpty()) {
            priceQueue.insert(temporaryStack.pop());
          }
          break;

        case MILEAGE:

          car.setMileage(Integer.parseInt(value));
          temporaryStack = new Stack<Sort>();

          while (true) {
            Sort sort = (Sort)mileageQueue.delMin();
            if (sort.index == i) {
              sort.value = Integer.parseInt(value);
              temporaryStack.add(sort);
              break;
            }
            temporaryStack.add(sort);
          }

          while (!temporaryStack.isEmpty()) {
            mileageQueue.insert(temporaryStack.pop());
          }
          break;

        case COLOR:

          car.setColor(value);
          break;

      default:

        System.out.println("Invalid Field Update");
        break;
      }
    }

///////////////////////////////////////////////////////////////////////////////

    // Search MinPQ for lowest priced car based on specified make and model
    public Car priceMakeModel(String make, String model) {

      Car car = new Car();
      Stack<Sort> temporary_car = new Stack<Sort>();

      boolean check = false;
      int i = 0;

      while (i < size) {

        Sort sort = (Sort)priceQueue.delMin();
        Car x = cars.get(sort.index);

        if (x.getMake().equals(make) && x.getModel().equals(model)) {
          car = x;
          temporary_car.add(sort);
          check = true;
          break;
        }
        temporary_car.add(sort);
        i++;
      }

      if (check == false) {
        return null;
      }

      while (!temporary_car.isEmpty()) {
        priceQueue.insert(temporary_car.pop());
      }

      return car;
    }

///////////////////////////////////////////////////////////////////////////////

    // Search MinPQ for lowest mileage car based on specified make and model
    public Car mileageMakeModel(String make, String model) {

      Car car = new Car();
      Stack<Sort> temporary_car = new Stack<Sort>();

      boolean check = false;
      int i = 0;

      while (i < size) {

        Sort sort = (Sort)mileageQueue.delMin();
        Car x = cars.get(sort.index);

        if (x.getMake().equals(make) && x.getModel().equals(model)) {
          car = x;
          temporary_car.add(sort);
          check = true;
          break;
        }
        temporary_car.add(sort);
        i++;
      }

      if (check == false) {
        return null;
      }

      while (!temporary_car.isEmpty()) {
        mileageQueue.insert(temporary_car.pop());
      }

      return car;
    }

///////////////////////////////////////////////////////////////////////////////

    // Check if VIN number is already in use
    public boolean isFound(String vin_number) {

      for (Car car: cars) {
        if (vin_number.equals(car.getVIN())) {
          return true;
        }
      }
      return false;
    }
  }

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////// Car Class ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////

  public static class Car {

    public String vin_number;
    public String make;
    public String model;
    public int price;
    public int mileage;
    public String color;

///////////////////////////////////////////////////////////////////////////////

    public Car() {}

    public Car(String vin_number,String make,String model,int price,int mileage,String color) {
      this.vin_number = vin_number;
      this.make = make;
      this.model = model;
      this.price = price;
      this.mileage = mileage;
      this.color = color;
    }

///////////////////////////////////////////////////////////////////////////////

    public void setVIN(String vin_number) {
      this.vin_number = vin_number;
    }

    public String getVIN(){
      return this.vin_number;
    }

///////////////////////////////////////////////////////////////////////////////

    public void setMake(String make) {
      this.make = make;
    }

    public String getMake(){
      return this.make;
    }

///////////////////////////////////////////////////////////////////////////////

    public void setModel(String model) {
      this.model = model;
    }

    public String getModel(){
      return this.model;
    }

///////////////////////////////////////////////////////////////////////////////

    public void setPrice(int price) {
      this.price = price;
    }

    public int getPrice(){
      return this.price;
    }

///////////////////////////////////////////////////////////////////////////////

    public void setMileage(int mileage) {
      this.mileage = mileage;
    }

    public int getMileage(){
      return this.mileage;
    }

///////////////////////////////////////////////////////////////////////////////

    public void setColor(String color) {
      this.color = color;
    }

    public String getColor(){
      return this.color;
    }

///////////////////////////////////////////////////////////////////////////////

    public String toString() {
      return "VIN: " + this.vin_number + "\n" +
             "Make: " + this.make + "\n" +
             "Model: " + this.model + "\n" +
             "Price: " + this.price + "\n" +
             "Mileage: " + this.mileage + "\n" +
             "Color: " + this.color;
    }
  }

///////////////////////////////////////////////////////////////////////////////
///////////////////////////////// Sort Class ///////////////////////////////////
///////////////////////////////////////////////////////////////////////////////

  public static class Sort implements Comparable<Sort>{

    String vin_number;
    int value;
    int index;

///////////////////////////////////////////////////////////////////////////////

    public Sort() {}

    public Sort(int index, int value) {
      this.index = index;
      this.value = value;
    }

///////////////////////////////////////////////////////////////////////////////

    @Override public int compareTo(Sort car) {
      if (this.value == car.value) {
        return 0;
      }
      else if (this.value > car.value) {
        return 1;
      }
      else {
        return -1;
      }
    }
  }
}
