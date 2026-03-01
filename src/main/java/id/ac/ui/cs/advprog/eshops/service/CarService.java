package id.ac.ui.cs.advprog.eshops.service;
import id.ac.ui.cs.advprog.eshops.model.Car;
import java.util.List;
public interface CarService {
    public Car create(Car car);
    public List<Car>findAll();
    Car findById(String carId);
    public void update (String carId, Car car);
    public void deleteCarbyId(String carId);
}
