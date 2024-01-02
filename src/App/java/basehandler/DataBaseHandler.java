package basehandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import usersClassesMySQL.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }
    public String getRole(Account account){
        String select = "SELECT role FROM account WHERE login =? AND password =?";
        String role = null;
        ResultSet resultSet;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, account.getLogin());
            prSt.setString(2, account.getPassword());
            resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                role = resultSet.getString(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return role;
    }
    public List<String> getAllBrands(){
        List<String> list = new ArrayList<>();
        String select = "SELECT name FROM brand_auto";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }
    public List<String> getAllModelsByBrand(String brand){
        int brand_id = getIdByMarkAuto(brand);
        List<String> list = new ArrayList<>();
        String select = "SELECT name FROM model_auto WHERE brand_id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, brand_id);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }
    public boolean containsLogin(String login){
        List<String> allLogins = new ArrayList<String>();
        String select = "SELECT login FROM account";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                allLogins.add(resultSet.getString("login"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return allLogins.contains(login);
    }
    public void createNewAccount(Account account){
        String insert = "INSERT INTO account (login, password, role) VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, account.getLogin());
            prSt.setString(2, account.getPassword());
            prSt.setString(3, account.getRole());
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void createNewCustomer(Customer customer){
        String insert = "INSERT INTO customer (account_id, customer_type_id, full_name, phone, email) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, customer.getAccount_id());
            prSt.setInt(2, customer.getCustomerType_id());
            prSt.setString(3, customer.getFullName());
            prSt.setString(4, customer.getPhone());
            prSt.setString(5, customer.getEmail());
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public int getIdAccount(String login){
        String select = "SELECT account.id FROM account WHERE login=?";
        int id = 0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return id;

    }
    public int getIdCustomerByLogin(String login){
        int account_id = getIdAccount(login);
        int customer_id = 0;
        String select = "SELECT id FROM customer WHERE account_id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, account_id);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
               customer_id = resultSet.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return customer_id;
    }
    public int getCustomerTypeId(String typeCustomer){
        String select = "SELECT id FROM customer_type WHERE customer_type.name =?";
        int id = 0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, typeCustomer);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return id;
    }
    public List<String> getAllTypesOfServices(){
        List<String> list = new ArrayList<>();
        String select = "SELECT name FROM type_of_service";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return list;
    }
    public int getTypeServiceId(String type){
        String select = "SELECT id FROM type_of_service WHERE name = ?";
        int id = 0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, type);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return id;
    }
    public ObservableList<Service> getAllServicesByCategory(String type){
        ObservableList<Service> services = FXCollections.observableArrayList();
        int id = getTypeServiceId(type);
        String select = "SELECT name, price FROM service WHERE service.id_type=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                services.add(new Service(resultSet.getInt("price"), resultSet.getString("name")));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return services;
    }
    public List<String> getAllNamesServicesByCategory(String type){
        List<String> services = new ArrayList<>();
        int id = getTypeServiceId(type);
        String select = "SELECT name FROM service WHERE service.id_type=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                services.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return services;
    }
    public List<String> getAllTypesOfCustomers(){
        List<String> types = new ArrayList<>();
        String select = "SELECT name FROM customer_type";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                types.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return types;
    }
    public ObservableList<Order> getAllOrdersForCustomer(String currLogin){
        ObservableList<Order> orders = FXCollections.observableArrayList();
        int customer_id = getIdCustomerByLogin(currLogin);
        String select = "SELECT * FROM `order` WHERE customer_id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, customer_id);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                orders.add(new Order(resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("price"),
                        resultSet.getInt("model_auto_id")));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return orders;
    }
    public ObservableList<Order> getAllOrder(){
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String select = "SELECT * FROM `order`";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                orders.add(new Order(resultSet.getInt("id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getInt("price"),
                        resultSet.getInt("model_auto_id")));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return orders;
    }
    public ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String select = "SELECT * FROM customer";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                customers.add(new Customer(resultSet.getString("full_name"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getInt("account_id"),
                        resultSet.getInt("customer_type_id"),
                        resultSet.getInt("id")));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return customers;
    }
    public ObservableList<Account> getAllAccountsForAdmin(){
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        String select = "SELECT * FROM account where role !='client'";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                accounts.add(new Account(resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role")));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return accounts;
    }
    public String getNameServiceById(int service_id){
        StringBuilder str = new StringBuilder();
        String select = "SELECT name FROM service WHERE id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, service_id);
            ResultSet resultSet = prSt.executeQuery();
            if (resultSet.next()){
                str.append(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    public ObservableList<Service> getAllServicesInOrder(int order_id){
        String select = "SELECT * FROM service_in_order WHERE order_id=?";
        ObservableList<Service> services = FXCollections.observableArrayList();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, order_id);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                services.add(new Service(resultSet.getInt("price"),
                       getNameServiceById(resultSet.getInt("id_service"))));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return services;
    }
    public int getIdByMarkAuto(String brand){
        String select = "SELECT id FROM brand_auto where name=?";
        int id =0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, brand);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return id;
    }
    public int getIdModelAuto(String model){
        int id = 0;
        String select = "SELECT id FROM model_auto WHERE name=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, model);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return id;
    }
    public void createOrder(Order order){
        String insert ="INSERT INTO `order` (customer_id, start_date, model_auto_id) VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, order.getCustomer_id());
            prSt.setDate(2, order.getStartDate());
            prSt.setInt(3, order.getModelAutoId());
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public String getFullNameAuto(int model_id){
        StringBuilder str = new StringBuilder();
        int brand_id = 0;
        String model ="";
        String select = "SELECT brand_id, name FROM model_auto WHERE id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, model_id);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                model +=resultSet.getString("name");
                brand_id = resultSet.getInt("brand_id");
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        select = "SELECT name FROM brand_auto where id =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, brand_id);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                str.append(resultSet.getString("name"));
                str.append(" ");
                str.append(model);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return str.toString();
    }
    public String getNameCustomerById(int id){
        String select = "SELECT full_name FROM customer WHERE id = ?";
        StringBuilder str = new StringBuilder();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1,id);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                 str.append(resultSet.getString(1));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return str.toString();
    }
    public void updateOrder(int idOrder, Date endDate){
        String update = "UPDATE `order` SET end_date=? WHERE id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setDate(1, endDate);
            prSt.setInt(2, idOrder);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public ObservableList<TypeOfService> getAllTypeOfService(){
        ObservableList<TypeOfService> typeOfServices = FXCollections.observableArrayList();
        String select = "SELECT * FROM type_of_service";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()) {
                typeOfServices.add(new TypeOfService(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description")));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return typeOfServices;
    }
    public int getServiceIdByName(String name){
        String select = "SELECT * FROM service where name =?";
        int id =0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, name);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return id;
    }
    public int getPriceServiceByName(String name){
        String select = "SELECT * FROM service where name =?";
        int price =0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, name);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                price = resultSet.getInt("price");
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return price;
    }
    public void addServiceInOrder(int order_id, String nameService){
        String insert = "INSERT INTO service_in_order (order_id, id_service, price) VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, order_id);
            prSt.setInt(2, getServiceIdByName(nameService));
            prSt.setInt(3, getPriceServiceByName(nameService));
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void updateServiceInOrder(String nameService, int price, int order_id){
        String update = "UPDATE service_in_order SET price=? WHERE id_service= ? and order_id =?";
        int idService = getServiceIdByName(nameService);
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, price);
            prSt.setInt(2, idService);
            prSt.setInt(3, order_id);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public boolean containsThisService(String serviceName, int order_id){
        int idService = getServiceIdByName(serviceName);
        String select = "SELECT id_service FROM service_in_order WHERE order_id=?";
        ArrayList<Integer> arrayList = new ArrayList<>();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, order_id);
            ResultSet resultSet = prSt.executeQuery();
            while (resultSet.next()){
                arrayList.add(resultSet.getInt("id_service"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return arrayList.contains(idService);
    }
    public void updatePriceOrder(int price, int order_id){
        String update = "UPDATE `order` SET price= ? WHERE id= ?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, price);
            prSt.setInt(2, order_id);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void deleteServiceInOrder(int id_order, int id_service){
        String delete = "DELETE FROM service_in_order WHERE service_in_order.order_id =? and service_in_order.id_service =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id_order);
            prSt.setInt(2, id_service);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public double getDiscountByCustomerId(int id_customer) {
        String select = "SELECT discount FROM customer_type WHERE customer_type.id = (SELECT customer_type_id FROM customer WHERE customer.id = ?)";
        double ret = 0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id_customer);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                ret = resultSet.getDouble(1);
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return ret;
    }
    public String getStrStatusCustomer(int id_type){
        StringBuilder str = new StringBuilder();
        String select = "SELECT name FROM customer_type WHERE id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, id_type);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next()) {
                str.append(resultSet.getString(1));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return str.toString();
    }
    public void updateCustomerStatus(int id_customer, int newStatus_id){
        String update = "UPDATE customer SET customer_type_id=? WHERE id =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setInt(1, newStatus_id);
            prSt.setInt(2, id_customer);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void createNewService(String name, int price, int typeService){
        String insert = "INSERT INTO service (price, name, id_type) VALUES (?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, price);
            prSt.setString(2, name);
            prSt.setInt(3,typeService);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void updateAccount(int id, String newLog, String newPass, String newRole){
        String update = "UPDATE account  SET login =?, password=?, role=? WHERE id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, newLog);
            prSt.setString(2, newPass);
            prSt.setString(3, newRole);
            prSt.setInt(4, id);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void updateRoleAccount(int id, String newRole){
        String update = "UPDATE account  SET role=? WHERE id=?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(update);
            prSt.setString(1, newRole);
            prSt.setInt(2, id);
            prSt.executeUpdate();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void deleteAccount(int id){
        String delete = "DELETE FROM account WHERE id =?";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(delete);
            prSt.setInt(1, id);
            prSt.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int getPayedMoney(int customer_id){
        String select = "SELECT price FROM `order` WHERE customer_id =?";
        int sum =0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setInt(1, customer_id);
            ResultSet resultSet = prSt.executeQuery();
            while(resultSet.next()) {
                sum += resultSet.getInt("price");
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return sum;
    }
    public List<String> getOrderedTypesServices(){
        String select ="SELECT type_of_service.name, service_in_order.price FROM service, service_in_order,type_of_service WHERE id_service = service.id and id_type = type_of_service.id";
        List<String> list = new ArrayList<>();
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        list = list.stream().distinct().toList();
        return list;
    }
    public int getSumOfCategoryService(String name){
        String select ="SELECT type_of_service.name, service_in_order.price FROM service, service_in_order,type_of_service WHERE id_service = service.id and id_type = type_of_service.id";
        int sum = 0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while(resultSet.next()) {
                if (name.equals(resultSet.getString("name"))){
                    sum += resultSet.getInt("price");
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return sum;
    }
    public List<String> getOrderedMarkAuto(){
        List<String> list = new ArrayList<>();
        String select = "SELECT brand_auto.name, price FROM `order`, model_auto, brand_auto WHERE `order`.model_auto_id = model_auto.id and model_auto.brand_id = brand_auto.id";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while(resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        list = list.stream().distinct().toList();
        return list;
    }
    public int getSumOfMarkAutoBuName(String name){
        String select = "SELECT brand_auto.name, price FROM `order`, model_auto, brand_auto WHERE `order`.model_auto_id = model_auto.id and model_auto.brand_id = brand_auto.id";
        int sum =0;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            while(resultSet.next()) {
                if (name.equals(resultSet.getString("name"))){
                    sum += resultSet.getInt("price");
                }
            }
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return sum;
    }

}
