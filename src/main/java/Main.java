import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

import com.codecool.shop.controller.OrderController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.db.ConnectionHandler;
import com.codecool.shop.model.*;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class Main {

    private static void executeQuery(String query) {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        try (Connection connection = connectionHandler.getConnection();
             Statement statement =connection.createStatement();
        ){
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void test() throws SQLException {

        String query = "INSERT INTO todos (id, title, status) VALUES ('1111', 'kkkk', 'xxx');";
        executeQuery(query);

    }



    public static Integer userId = 0;

    public static void main(String[] args) throws SQLException {

        test();

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        populateData();

        // Always start with more specific routes
        get("/hello", (req, res) -> "Hello World");

        // Always add generic routes to the end
        //get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());
        // Equivalent with above

        get("/", (Request req, Response res) -> {
           return new ThymeleafTemplateEngine().render( ProductController.renderProducts(req, res, userId) );
        });

        get("/filter", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( ProductController.renderProductsByFilter(req, res) );
                });

        post("/addToCart", (Request req, Response res) -> {
            return OrderController.renderOrder(req, res, userId);
        });
        // Add this line to your project to enable the debug screen

        get("/shoppingCart", (Request req, Response res) -> {
            return new ThymeleafTemplateEngine().render( OrderController.renderModal(req, res, userId) );
        });

        enableDebugScreen();
    }

    public static void populateData() {

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple", "Electronic devices");
        supplierDataStore.add(apple);
        Supplier nokia = new Supplier("Nokia", "Electronic devices");
        supplierDataStore.add(nokia);


        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A phone computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(phone);
        ProductCategory computer = new ProductCategory("Computer", "Hardware", "A computer is a  computer");
        productCategoryDataStore.add(computer);


        //setting up products and printing it
        Product product = new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon);
        productDataStore.add(product);
        product = new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo);
        productDataStore.add(product);
        product = new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon);
        productDataStore.add(product);
        product = new Product("Apple Iphone 5S",178,"USD", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.", phone, apple);
        productDataStore.add(product);
        product = new Product("Apple MacBook Air", 898,"USD", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.", computer, apple);
        productDataStore.add(product);
        product = new Product("Apple Ipad", 311, "USD", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.", tablet, apple);
        productDataStore.add(product);
        product = new Product("Nokia 3310", 60, "USD", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.", phone, nokia);
        productDataStore.add(product);

        //setting up default order
        Order order = new Order(userId);
    }


}
